package com.cyh.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chenyuhua
 * @date 2020/2/22 17:11
 */
@Data
public class Configuration {

    private DataSource dataSource;

    /**
     * key: statementId value:封装好的manppedStatement对象
     */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<>();
}
