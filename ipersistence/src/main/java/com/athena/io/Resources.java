package com.athena.io;

import java.io.InputStream;

/**
 * @Author: xiaoxiang.zhang
 * @Description:资源加载类
 * @Date: Create in 4:38 PM 2020/2/23
 */
public class Resources {

    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
