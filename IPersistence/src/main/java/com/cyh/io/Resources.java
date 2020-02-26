package com.cyh.io;

import java.io.InputStream;

/**
 * @author Chenyuhua
 * @date 2020/2/22 15:53
 */
public class Resources {

    public static InputStream getResourceAsStream(String path){
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
