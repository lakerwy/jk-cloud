package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体 - 操作 - 工具类
 */
public class EntityUtil {
    /**
     * 校验 字段 是否 为 实体内部属性
     * @param tClass 类名
     * @param columns 待更新字段列
     * @return 错误提示
     */
    public static String checkFields(Class<?> tClass, List<String> columns){
        Field[] fields = tClass.getDeclaredFields();
        //tClass中包含的字段名集合
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使字段可访问
                fieldNames.add(field.getName());
            }catch (Exception e){ }
        }
        //校验待更新字段
        List<String> errorColumns = new ArrayList<>();
        for (String column: columns) {
            if(!fieldNames.contains(column)){
                errorColumns.add(column);
            }
        }
        return CollUtil.isNotEmpty(errorColumns) ? StrUtil.join("，",errorColumns) : null;
    }

    /**
     * 复制时，忽略的字段集合
     * @param tClass 类名
     * @param columns 待更新的字段列
     * @return 忽略copy的字段集合
     */
    public static List<String> copyIgnoreFields(Class<?> tClass, List<String> columns){
        Field[] fields = tClass.getDeclaredFields();
        //tClass中包含的字段名集合
        List<String> ignoreFields = new ArrayList<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使字段可访问
                if(!columns.contains(field.getName())){
                    ignoreFields.add(field.getName());
                }
            }catch (Exception e){ }
        }
        return ignoreFields;
    }
}
