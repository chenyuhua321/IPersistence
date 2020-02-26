package com.cyh.pojo;

import com.cyh.enums.SqlCommandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chenyuhua
 * @date 2020/2/22 17:05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement {

    /**
     * id标识
     */
    private String id;

    /**
     * 返回值类型
     */
    private String resultType;

    /**
     * 参数值类型
     */
    private String paramterType;

    /**
     * sql语句
     */
    private String sql;
    /**
     *SQL 命令类型
     */
    private SqlCommandType sqlCommandType;
}
