package vip.xiaonuo.flw.modular.flwbiz.project.util;

import cn.hutool.core.util.ObjectUtil;

import java.util.Random;

/**
 * 对比工具类型
 */
public class MyUtil {

    /**
     *   返回新字符串
     *   1. 去掉文本中的空格
     *   2. 不区分大小写
     *   3. 去掉特殊字符
     *
     * @param str 原字符串
     * @return
     */
    public static String removeSpcChar(String str){
        //去空格、转小写
        String input = str.trim().toLowerCase();
        StringBuilder result = new StringBuilder();
        //循环校验，比对，返回结果集
        for (char c : input.toCharArray()) {
            if (isAllowed(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    // 判断字符是否在允许范围内
    private static boolean isAllowed(char c) {
        // 判断是否为中文、字母、数字
        return isChinese(c) || Character.isLetterOrDigit(c);
    }

    // 判断是否为中文字符
    private static boolean isChinese(char c) {
        return c >= '\u4e00' && c <= '\u9fa5';
    }

    /**
     * 返回第一个不允许的字符，之前的内容
     * @param str
     * @return
     */
    public static String getFirstStr(String str){
        StringBuilder result = new StringBuilder();
        //循环校验，比对，返回结果集
        for (char c : str.toCharArray()) {
            if (isAllowed(c)) {
                result.append(c);
            }else{
                return result.toString();
            }
        }
        return null;
    }


    /**
     * 获取字符串前面部分
     * @param s
     * @param f1
     * @param f2
     * @return
     */
    public static String getRandomSubstring(String s,Float f1,Float f2) {
        if(ObjectUtil.isEmpty(s)) return s;

        Random random = new Random();
        int length = s.length();

        // 计算60%和80%对应的位置（向下取整）
        int minLen = (int) Math.floor(length * f1);
        int maxLen = (int) Math.floor(length * f2);

        // 随机选择一个长度（从minLen到maxLen之间）
        int subLength = random.nextInt(maxLen - minLen + 1) + minLen;

        // 从字符串开头开始截取 特殊 处理 0
        if(subLength < 1 || subLength > length){
            return s;
        }
        return s.substring(0, subLength);
    }



    /**
     * 获取字符串前面部分
     * @param s
     * @param f1 截取比例
     * @return
     */
    public static String getRandomSubstring2(String s,Float f1) {
        if(ObjectUtil.isEmpty(s)) return s;

        Random random = new Random();
        int length = s.length();

        // 计算60%和80%对应的位置（向下取整）
        int subLength = (int) Math.floor(length * f1);

        // 从字符串开头开始截取 特殊 处理 0
        if(subLength < 1 || subLength > length){
            return s;
        }
        return s.substring(0, subLength);
    }

}
