package com.mf.dal.dbcp;

import com.mf.dal.configuration.DBRole;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Properties;


/**
 * Created by shixin on 3/11/14.
 */
public class DBConnectionPool extends GenericObjectPool<Connection> {
    private static final Logger logger = Logger.getLogger(DBConnectionPool.class);

    private String url;
    private DBRole dbRole;

    public DBConnectionPool(String url, String user, String password, DBRole dbRole, Properties properties) {
        super(new DBConnectionFactory(url, user, password));
        setup(properties);
        this.url = url;
        this.dbRole = dbRole;
    }

    public DBConnectionPool(String url, String user, String password, DBRole dbRole, GenericObjectPoolConfig config, Properties properties) {
        super(new DBConnectionFactory(url, user, password), config);
        setup(properties);
        this.url = url;
        this.dbRole = dbRole;
    }

    private void setup(Properties properties) {
        /*
        * maxActive: 链接池中最大连接数 默认8
        * maxIdle: 链接池中最大空闲的连接数,默认为8
        * minIdle: 连接池中最少空闲的连接数,默认为0.
        * testOnBorrow: 向调用者输出“链接”资源时，是否检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取。默认为false。建议保持默认值.
        * testOnReturn:  向连接池“归还”链接时，是否检测“链接”对象的有效性。默认为false。建议保持默认值.
        * testWhileIdle:  向调用者输出“链接”对象时，是否检测它的空闲超时；默认为false。如果“链接”空闲超时，将会被移除。建议保持默认值
        * timeBetweenEvictionRunsMillis:  “空闲链接”检测线程，检测的周期，毫秒数。如果为负值，表示不运行“检测线程”。默认为-1.
        * whenExhaustedAction: 当“连接池”中active数量达到阀值时，即“链接”资源耗尽时，连接池需要采取的手段, 默认为1：
             0:抛出异常;1:阻塞，直到有可用链接资源;2:强制创建新的链接资源
        * */
        this.setLifo(false);
        this.setTestOnBorrow(true);
        this.setTestOnCreate(false);
        this.setTestOnReturn(false);
        this.setTestWhileIdle(true);
        this.setMinEvictableIdleTimeMillis(10 * 60 * 1000);
        this.setTimeBetweenEvictionRunsMillis(12 * 60 * 1000);

        this.setMaxIdle(Integer.parseInt(properties.getProperty("max.idle", "5")));
        this.setMaxTotal(Integer.parseInt(properties.getProperty("max.total", "50")));
    }

    public void close() {
        logger.info("Closing db connection pool for url:" + this.url);
        super.close();
    }

    public DBRole getDbRole() {
        return this.dbRole;
    }
}
