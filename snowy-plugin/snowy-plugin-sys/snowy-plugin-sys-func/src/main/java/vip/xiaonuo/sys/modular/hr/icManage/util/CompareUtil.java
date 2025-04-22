package vip.xiaonuo.sys.modular.hr.icManage.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CompareUtil {
    public static Map<String,String> compareEntities(Object entityA, Object entityB) {
        Map<String,String> result = new HashMap<>();
        try {
            if (entityA == null || entityB == null) {
                throw new IllegalArgumentException("Entities cannot be null");
            }

            if (!entityA.getClass().equals(entityB.getClass())) {
                throw new IllegalArgumentException("Entities must be of the same type");
            }

            Field[] fields = entityA.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // 允许访问私有字段
                Object valueA = field.get(entityA);
                Object valueB = field.get(entityB);

                if (valueA == null && valueB == null) {
                    continue; // 两者都为 null，视为相同
                }

                if ((valueA == null && valueB != null) || !valueA.equals(valueB)) {
                    result.put(field.getName(),valueA + " -> " + valueB);
                }
            }
        }catch (Exception e){
            log.error("实体比较："+e.getMessage());
        }
        return result;
    }
}
