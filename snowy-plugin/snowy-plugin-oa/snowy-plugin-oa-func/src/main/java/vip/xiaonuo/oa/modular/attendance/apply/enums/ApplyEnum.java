package vip.xiaonuo.oa.modular.attendance.apply.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤申请管理表枚举
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
@Getter
public enum ApplyEnum {

    /** 申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到 */
    ADJUSTMENT_APPLY(1,"补卡"),

    HOLIDAY_APPLY(2,"请假"),

    OVER_WORK_APPLY(3,"加班"),

    OUT_APPLY(4,"外出"),

    BUSINESS_TRIP_APPLY(5,"出差"),

    CHECK_IN_APPLY(6,"签到");

    private final Integer type;

    private final String value;

    ApplyEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String fromType(int type) {
        for (ApplyEnum e : values()) {
            if (e.type==type) {
                return e.getValue();
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    public static List<Map<String, Object>> getAllEnumValues() {
        List<Map<String, Object>> enumValues = new ArrayList<>();
        for (ApplyEnum e : values()) {
            Map<String, Object> enumValueMap = new HashMap<>();
            enumValueMap.put("type", e.getType());
            enumValueMap.put("value", e.getValue());
            enumValues.add(enumValueMap);
        }
        return enumValues;
    }
}
