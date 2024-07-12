package vip.xiaonuo.oa.modular.attendance.rule.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/11 14:11
 * 补卡类型
 */
@Getter
public enum AdjustmentTypeEnum {

    /**
     * 1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到
     */

    MISS_CLOCK_IN(1,"缺卡"),

    LATE(2,"迟到"),

    EARLY_LEAVE(3,"早退");


    private int type;

    private String value;

    AdjustmentTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String fromType(int type) {
        for (AdjustmentTypeEnum e : values()) {
            if (e.type==type) {
                return e.getValue();
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    public static List<Map<String, Object>> getAllEnumValues() {
        List<Map<String, Object>> enumValues = new ArrayList<>();
        for (AdjustmentTypeEnum e : values()) {
            Map<String, Object> enumValueMap = new HashMap<>();
            enumValueMap.put("type", e.getType());
            enumValueMap.put("value", e.getValue());
            enumValues.add(enumValueMap);
        }
        return enumValues;
    }

    public static String fromTypes(int[] types ){
        StringBuilder typeValue = new StringBuilder();
        for (int type : types) {
            for (AdjustmentTypeEnum e : values()) {
                if (e.type==type) {
                    typeValue.append(e.getValue());
                    typeValue.append(",");
                }
            }
        }
        if (typeValue.length() > 0) {
            typeValue.deleteCharAt(typeValue.length() - 1); // 删除最后一个逗号
        }
        return typeValue.toString();
    }
}
