package vip.xiaonuo.flw.modular.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用 Map<String, Object> 转 Map<String, String>
 * @Author hxw
 * @Date 2024/4/24 17:33
 */
public class HandleCarbonUtil {
    public static Map<String, String> convertMap(Map<String, Object> originalMap) {
        Map<String, String> convertedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                convertedMap.put(key, (String) value);
            } else {
                convertedMap.put(key, String.valueOf(value));
            }
        }
        return convertedMap;
    }
}
