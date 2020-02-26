package com.cyh.utils;

import lombok.Data;

/**
 * @author Chenyuhua
 * @date 2020/2/23 14:01
 */
@Data
public class ParameterMapping {
    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }
}
