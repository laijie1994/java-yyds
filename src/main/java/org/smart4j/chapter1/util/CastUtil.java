package org.smart4j.chapter1.util;

/**
 * @ClassName CastUtil
 * @Description 转型操作工具类
 * @Author jeremy.lai
 * @Date 2021/11/30 2:52 下午
 * @Version 1.0
 **/
public final class CastUtil {

    /**
     * @return java.lang.String
     * @MethodName castString
     * @Description 转为String类型
     * @Param [obj]
     * @Author jeremy.lai
     * @Date 2:57 下午 2021/11/30
     **/
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * @return java.lang.String
     * @MethodName castString
     * @Description 转为String类型，提供默认值
     * @Param [obj, defaultValue]
     * @Author jeremy.lai
     * @Date 2:57 下午 2021/11/30
     **/
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }


    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue) {
        // 先转成String类型，再转成int
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = CastUtil.castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException exception) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValue) {
        // 先转成String类型，再转成long
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = CastUtil.castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException exception) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    public static double castDouble(Object obj, double defaultValue) {
        // 先转成String类型，再转成long
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = CastUtil.castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException exception) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }

}
