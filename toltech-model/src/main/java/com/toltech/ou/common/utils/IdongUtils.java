package com.toltech.ou.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


import java.util.stream.IntStream;


/**
 * @Author: ouyang
 * @Date: 2019/6/18 16:49
 * @Version 1.0
 */
@Slf4j
public class IdongUtils {

    /**
     * 驼峰转下划线
     * @param value
     * @return
     */
    public static String camelToUnderscore(String value){
        if (StringUtils.isBlank(value)){
            return value;
        }
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0){
            return value;
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0,arr.length).forEach(i->{
            if (i != arr.length - 1){
                result.append(arr[i]).append(StringPool.UNDERSCORE);
            }else {
                result.append(arr[i]);
            }
        });
        return StringUtils.lowerCase(result.toString());
    }
}
