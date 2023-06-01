package com.ibm.rpc_starter.common.util;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/06/01/14:55
 * @Description: String 工具类
 */
public class StringUtil {
    public static boolean isBlank(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        //对于每个字符，通过Character.isWhitespace()方法判断是否为空白字符（包括空格、制表符、换行符等）。
        // 如果不是空白字符，则表示字符串不仅包含空白字符，返回false。
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
