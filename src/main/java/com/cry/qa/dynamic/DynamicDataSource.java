package com.cry.qa.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * @Author: Chen ruoyu
 * @Description: 配置动态数据源,继承springframework: AbstractRoutingDataSource
 * 来决定使用哪个数据源
 * @Date Created in:  2021-02-07 12:13
 * @Modified By:
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDBType();
    }
}
