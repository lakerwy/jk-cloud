package vip.xiaonuo.flw.modular.carboncopy.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HandleCarbonUtil
 * @Author hxw
 * @Date 2024/4/24 15:10
 */
public class HandleCarbonUtil {
    public static Map<String, String> convertMap(Map<String, Object> originalMap) {
        Map<String, String> convertedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            if (entry.getValue() instanceof String) {
                convertedMap.put(entry.getKey(), (String) entry.getValue());
            } else {
                throw new ClassCastException("Map value is not of type String");
            }
        }
        return convertedMap;
    }
}
