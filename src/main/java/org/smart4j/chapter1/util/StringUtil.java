package org.smart4j.chapter1.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName StringUtil
 * @Description 字符串工具类（封装StringUtils类的一些方法）
 * @Author jeremy.lai
 * @Date 2021/11/30 4:25 下午
 * @Version 1.0
 **/
public final class StringUtil {

    /**
     * @return boolean
     * @MethodName isEmpty
     * @Description 判断字符串是否为空
     * @Param [str]
     * @Author jeremy.lai
     * @Date 4:27 下午 2021/11/30
     **/
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * @return boolean
     * @MethodName isNotEmpty
     * @Description 判断字符串是否非空
     * @Param [str]
     * @Author jeremy.lai
     * @Date 4:27 下午 2021/11/30
     **/
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

}
