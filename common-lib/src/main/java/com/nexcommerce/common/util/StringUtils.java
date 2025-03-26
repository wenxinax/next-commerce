package com.nexcommerce.common.util;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 提供常用的字符串操作方法
 */
public class StringUtils {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    
    /**
     * 检查字符串是否为空（null或空字符串）
     *
     * @param str 待检查的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 检查字符串是否非空
     *
     * @param str 待检查的字符串
     * @return 是否非空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * 生成UUID（无连字符）
     *
     * @return UUID字符串
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 检查是否为有效的电子邮件格式
     *
     * @param email 电子邮件地址
     * @return 是否有效
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * 检查是否为有效的手机号码格式（中国大陆）
     *
     * @param phone 手机号码
     * @return 是否有效
     */
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * 将字符串截断到指定长度，并在末尾添加省略号
     *
     * @param str 原字符串
     * @param maxLength 最大长度
     * @return 截断后的字符串
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }
    
    /**
     * 格式化金额（保留两位小数）
     *
     * @param amount 金额
     * @return 格式化后的金额字符串
     */
    public static String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }
    
    /**
     * 驼峰命名转下划线命名
     *
     * @param str 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String camelToUnderscore(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
    
    /**
     * 下划线命名转驼峰命名
     *
     * @param str 下划线命名字符串
     * @param capitalizeFirstLetter 首字母是否大写
     * @return 驼峰命名字符串
     */
    public static String underscoreToCamel(String str, boolean capitalizeFirstLetter) {
        if (isEmpty(str)) {
            return str;
        }
        
        StringBuilder result = new StringBuilder();
        boolean nextToUpper = capitalizeFirstLetter;
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                nextToUpper = true;
            } else {
                if (nextToUpper) {
                    result.append(Character.toUpperCase(c));
                    nextToUpper = false;
                } else {
                    result.append(c);
                }
            }
        }
        
        return result.toString();
    }
}
