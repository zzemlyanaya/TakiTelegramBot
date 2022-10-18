package ru.zzemlyanaya.takibot.core.dao;

/* created by zzemlyanaya on 22/11/2022 */

import org.apache.ibatis.session.SqlSession;
import ru.zzemlyanaya.takibot.data.session.SqlSessionUtil;

import java.util.function.Function;

public abstract class BaseDao<DAO> {

    private String environment = "development";
    private final SqlSessionUtil sqlSessionUtil;
    private final Class<DAO> daoClass;

    public BaseDao(Class<DAO> daoClass) {
        this.daoClass = daoClass;

        sqlSessionUtil = new SqlSessionUtil(environment);
    }

    public BaseDao(Class<DAO> daoClass, String environment) {
        this.environment = environment;
        this.daoClass = daoClass;

        sqlSessionUtil =  new SqlSessionUtil(environment);
    }

    protected <T> T execute(Function<DAO, T> operation) {
        try (SqlSession session = sqlSessionUtil.getSqlSession()) {
            DAO mapper = session.getMapper(daoClass);
            T result = operation.apply(mapper);

            session.commit();
            return result;
        }
    }

    protected <T> T select(Function<DAO, T> operation) {
        try (SqlSession session = sqlSessionUtil.getSqlSession()) {
            DAO mapper = session.getMapper(daoClass);
            return operation.apply(mapper);
        }
    }
}
