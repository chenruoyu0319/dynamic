package com.cry.qa.dynamic;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Chen ruoyu
 * @Description: 新建一个holder来存放相应的datasource
 * @Date Created in:  2021-02-07 12:17
 * @Modified By:
 */
@Slf4j
public class DynamicDataSourceHolder {

    /**
     * 创建一个本地线程
     */
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE_1 = "slave_1";
    public static final String DB_SLAVE_2 = "slave_2";

    public static String getDBType() {
        String db = contextHolder.get();
        if (db == null) {
            db = DB_MASTER;
        }
        return db;
    }

    public static void setDBType(String str) {
        log.info("数据源为" + str);
        contextHolder.set(str);
    }

    public static void clearDBType() {
        contextHolder.remove();
    }

}
