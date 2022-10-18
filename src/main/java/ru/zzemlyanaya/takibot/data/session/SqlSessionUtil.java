package ru.zzemlyanaya.takibot.data.session;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/* created by zzemlyanaya on 04/11/2022 */

public class SqlSessionUtil {

    private SqlSessionFactory factory = null;

    private String environment = "development";

    public SqlSessionUtil() {
        initFactory();
    }

    public SqlSessionUtil(String env) {
        environment = env;
        initFactory();
    }

    private void initFactory() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            factory = new SqlSessionFactoryBuilder().build(inputStream, environment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlSession getSqlSession() throws NullPointerException {
        return factory.openSession();
    }

}
