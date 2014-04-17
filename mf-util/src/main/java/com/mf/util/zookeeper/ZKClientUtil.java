package com.mf.util.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by shixin on 4/17/14.
 * TODO : add functions.
 */
public class ZKClientUtil {
    private static final Logger logger = Logger.getLogger(ZKClientUtil.class);

    private static class ZKWatcher implements Watcher {
        private Object lock = new Object();
        private volatile boolean connected = false;

        @Override
        public void process(WatchedEvent watchedEvent) {
            Event.EventType eventType = watchedEvent.getType();
            switch (eventType) {
                case None:
                    Event.KeeperState state = watchedEvent.getState();
                    switch (state) {
                        case SyncConnected:
                            logger.info("ZooKeeper client connected to the server!");
                            synchronized (lock) {
                                connected = true;
                                lock.notifyAll();
                            }
                            break;
                        case Disconnected:
                            logger.info("ZooKeeper connection lost!");
                            synchronized (lock) {
                                connected = false;
                                lock.notifyAll();
                            }
                            break;
                        case Expired:
                            logger.info("ZooKeeper Session has expired!");
                            synchronized (lock) {
                                connected = false;
                                lock.notifyAll();
                            }
                            break;
                        default:
                            logger.info("Meet unsupported zookeeper state : " + state);
                            break;
                    }
                    break;
                case NodeCreated:
                    logger.info("Node created, path : " + watchedEvent.getPath());
                    break;
                case NodeDeleted:
                    logger.info("Node deleted, path : " + watchedEvent.getPath());
                    break;
                case NodeDataChanged:
                    logger.info("Node data changed, path : " + watchedEvent.getPath());
                    break;
                case NodeChildrenChanged:
                    logger.info("Node children changed, path : " + watchedEvent.getPath());
                    break;
                default:
                    logger.error("Unknown event type : " + eventType);
            }
        }
    }

    public static ZooKeeper initZKClient(String connectStr, int sessionTimeout) throws IOException {
        ZKWatcher zkWatcher = new ZKWatcher();
        ZooKeeper zk = new ZooKeeper(connectStr, sessionTimeout, zkWatcher);

        synchronized (zkWatcher.lock) {
            while (!zkWatcher.connected) {
                try {
                    logger.info("Awaiting for lock notification!");
                    zkWatcher.lock.wait();
                    logger.info("Lock notification, connected = " + zkWatcher.connected);
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        }

        return zk;
    }
}
