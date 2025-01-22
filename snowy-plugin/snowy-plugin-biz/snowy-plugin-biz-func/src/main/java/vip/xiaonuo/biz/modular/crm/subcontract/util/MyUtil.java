package vip.xiaonuo.biz.modular.crm.subcontract.util;

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
}
