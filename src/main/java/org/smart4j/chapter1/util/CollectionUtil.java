package org.smart4j.chapter1.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName CollectionUtil
 * @Description 集合工具类
 * @Author jeremy.lai
 * @Date 2021/11/30 4:30 下午
 * @Version 1.0
 **/
public final class CollectionUtil {

    /**
     * @return boolean
     * @MethodName isEmpty
     * @Description 判断集合是否为空
     * @Param [collection]
     * @Author jeremy.lai
     * @Date 4:34 下午 2021/11/30
     **/
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }


    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }


    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }


}
