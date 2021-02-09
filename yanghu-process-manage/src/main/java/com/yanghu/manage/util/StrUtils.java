package com.yanghu.manage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Desc 描述信息
 * @Author: wangqiong
 * @Date: 2019/4/29
 * @Version: 1.0
 * @Last Modified by: wangqiong
 * @Last Modified time: 2019/4/29
 */
public class StrUtils extends org.apache.commons.lang3.StringUtils {
    private static final Logger log = LoggerFactory.getLogger(StrUtils.class);
    private static final char SEPARATOR = '_';

    public StrUtils() {
    }

    public static String toEncodeString(final byte[] bytes, final String charsetName) {
        if (bytes == null) {
            return null;
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException var3) {
                log.error("StringUtils.toEncodeString error.", var3);
                return "";
            }
        }
    }

    public static String convertToString(final Collection<String> collection, String prefix, String suffix) {
        StringBuilder builder = new StringBuilder();
        Iterator var4 = collection.iterator();

        while (var4.hasNext()) {
            String o = (String) var4.next();
            builder.append(prefix).append(o).append(suffix);
        }

        return builder.toString();
    }


    public static Integer toInteger(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException var2) {
                log.error("StringUtils.toInteger(String) error.", var2);
                return null;
            }
        }
    }

    public static Short toShort(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            try {
                return Short.parseShort(str);
            } catch (NumberFormatException var2) {
                log.error("StringUtils.toShort(String) error.", var2);
                return null;
            }
        }
    }

    public static Long toLong(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException var2) {
                log.error("StringUtils.toLong(String) error.", var2);
                return null;
            }
        }
    }

    public static Byte toByte(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            try {
                return Byte.parseByte(str);
            } catch (NumberFormatException var2) {
                log.error("StringUtils.toByte(String) error.", var2);
                return null;
            }
        }
    }

    public static Float toFloat(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException var2) {
                log.error("StringUtils.toFloat(String) error.", var2);
                return null;
            }
        }
    }

    public static Double toDouble(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException var2) {
                log.error("StringUtils.toDouble(String) error.", var2);
                return null;
            }
        }
    }

    public static Boolean toBoolean(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            str = str.toLowerCase(Locale.ENGLISH);
            if (!"y".equals(str) && !"yes".equals(str) && !"true".equals(str) && !"是".equals(str)) {
                return !"n".equals(str) && !"no".equals(str) && !"false".equals(str) && !"否".equals(str) ? null : false;
            } else {
                return true;
            }
        }
    }

    /**
     * 空值检查，包括null 、 “”
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * obj转换成字符，直接调用toString()
     *
     * @param obj
     * @return
     */
    public static String objToStr(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }
    public static String listToString(List<String> list) {
        String str = "";

        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                str += list.get(i);
            } else {
                str = str + list.get(i) + ",";
            }
        }
        return str;
    }



    /***
     * 将map中的null 修改未 “”
     */
    public static void modifyMapNull(Map map) {
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> stringStringEntry : set) {
            if (stringStringEntry.getValue() == null) {
                map.put(stringStringEntry.getKey(), "");
            }
        }
    }
}