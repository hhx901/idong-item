package com.toltech.ou.common.utils;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;
/**
 * @Author: ouyang
 * @Date: 2019/7/3 15:11
 * @Version 1.0
 */
@Slf4j
public class CommonUtils {

    public static final String CHARSET_UTF8 = "utf-8";

    //编码
    public static String toUtf8Encode(String s){
        String r=null;
        if(s != null){
            try {
                r=URLEncoder.encode(s,CHARSET_UTF8);
            } catch (Exception e) {
                log.error("字符转换utf-8编码异常",e);
            }
        }
        return r;
    }
}
