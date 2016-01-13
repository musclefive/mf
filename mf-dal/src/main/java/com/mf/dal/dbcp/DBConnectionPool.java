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
        * maxActive: ���ӳ������������ Ĭ��8
        * maxIdle: ���ӳ��������е�������,Ĭ��Ϊ8
        * minIdle: ���ӳ������ٿ��е�������,Ĭ��Ϊ0.
        * testOnBorrow: ���������������ӡ���Դʱ���Ƿ���������Ч�������Ч������ӳ����Ƴ��������Ի�ȡ������ȡ��Ĭ��Ϊfalse�����鱣��Ĭ��ֵ.
        * testOnReturn:  �����ӳء��黹������ʱ���Ƿ��⡰���ӡ��������Ч�ԡ�Ĭ��Ϊfalse�����鱣��Ĭ��ֵ.
        * testWhileIdle:  ���������������ӡ�����ʱ���Ƿ������Ŀ��г�ʱ��Ĭ��Ϊfalse����������ӡ����г�ʱ�����ᱻ�Ƴ������鱣��Ĭ��ֵ
        * timeBetweenEvictionRunsMillis:  ���������ӡ�����̣߳��������ڣ������������Ϊ��ֵ����ʾ�����С�����̡߳���Ĭ��Ϊ-1.
        * whenExhaustedAction: �������ӳء���active�����ﵽ��ֵʱ���������ӡ���Դ�ľ�ʱ�����ӳ���Ҫ��ȡ���ֶ�, Ĭ��Ϊ1��
             0:�׳��쳣;1:������ֱ���п���������Դ;2:ǿ�ƴ����µ�������Դ
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
