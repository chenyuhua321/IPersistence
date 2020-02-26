package com.cyh.sqlSession;

import com.cyh.utils.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenyuhua
 * @date 2020/2/23 12:35
 */
@Data
@AllArgsConstructor
public class BoundSql {

    private String sqlText;

    private List<ParameterMapping> parameterMappings;
}
