package vip.xiaonuo.biz.modular.crm.subcontract.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.Map;

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
     * 判断json中是否存在某个键值对
     * @param json
     * @param targetKey 目标key
     * @param targetValue 目标value
     * @return true-表示存在该键值对
     */
    public static boolean hasKeyValuePair(JSON json, String targetKey, Object targetValue) {
        if (json instanceof JSONObject) {
            JSONObject obj = (JSONObject) json;
            // 检查当前对象
            if (obj.containsKey(targetKey)  && obj.get(targetKey).equals(targetValue))  {
                return true;
            }
            // 递归子节点
            for (Map.Entry<String, Object> entry : obj.entrySet())  {
                Object value = entry.getValue();
                if (value instanceof JSON) {
                    if (hasKeyValuePair((JSON) value, targetKey, targetValue)) {
                        return true;
                    }
                }
            }
        } else if (json instanceof JSONArray) {
            JSONArray array = (JSONArray) json;
            // 遍历数组元素
            for (Object item : array) {
                if (item instanceof JSON) {
                    if (hasKeyValuePair((JSON) item, targetKey, targetValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String jsonStr = "[{\"tableListG\":[{\"collectionNumber\":1,\"subcontractNumber\":\"FB2023020063\",\"subcontractName\":\"融通农业发展（广州）有限责任公司台山九围美丽渔场项目\",\"subcontractPartyB\":\"湖北建科国际工程有限公司岭南分公司\",\"subcontractMoney\":[275,343.75],\"filingDate\":\"2023-02-13 00:00:00\",\"moneyNature\":\"外部产值\",\"totalCostMoney\":1,\"costUnpaidMoney\":1}],\"thisApplyAmount\":1,\"payee\":\"1\",\"purchaser\":\"1\",\"bankName\":\"1\",\"dutyAttach\":\"202412206363964611\"}]";
        JSON json = JSONUtil.parse(jsonStr);
        System.out.println(MyUtil.hasKeyValuePair(json, "subcontractNumber", "FB2023020063"));
    }
}
