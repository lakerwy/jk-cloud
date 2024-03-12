package vip.xiaonuo.sys.modular.hr.salary.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * 为 实体 特定字段类型，若为空，设置默认值，工具类
 */
public class DefaultValueUtil {
    // 给实体类字段赋默认值
    public static <T> T defaultValues(T entity) {
        if (entity == null) {
            return null;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使字段可访问
                if((field.getType().equals(Integer.class) || field.getType().equals(int.class)) && field.get(entity) == null){
                    field.set(entity, 0);
                }else if((field.getType().equals(Double.class) || field.getType().equals(double.class)) && field.get(entity) == null){
                    field.set(entity, 0.0D);
                }else if((field.getType().equals(Float.class) || field.getType().equals(float.class)) && field.get(entity) == null){
                    field.set(entity, 0.0F);
                }else if((field.getType().equals(Long.class) || field.getType().equals(long.class)) && field.get(entity) == null){
                    field.set(entity, 0L);
                }else if((field.getType().equals(Short.class) || field.getType().equals(short.class)) && field.get(entity) == null){
                    field.set(entity, 0);
                }else if(field.getType().equals(BigDecimal.class) && field.get(entity) == null){
                    field.set(entity, BigDecimal.ZERO);
                }
            } catch (IllegalAccessException e) {
                // 处理异常
                // e.printStackTrace();
            }
        }
        return entity;
    }

}
