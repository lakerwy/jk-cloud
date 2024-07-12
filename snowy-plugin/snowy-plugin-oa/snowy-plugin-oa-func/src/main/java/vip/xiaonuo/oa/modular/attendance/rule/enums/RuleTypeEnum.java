package vip.xiaonuo.oa.modular.attendance.rule.enums;

import lombok.Getter;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/11 14:11
 */
@Getter
public enum RuleTypeEnum {

    /**
     * 1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到
     */

    ADJUSTMENT_RULE(1,"补卡"),

    APPLY_RULE(2,"请假"),

    OVER_WORD_RULE(3,"加班"),

    OUT_RULE(4,"外出"),

    BUSINESS_TRIP_RULE(5,"出差"),

    CHECK_IN_RULE(6,"签到");



    private int type;

    private String value;

    RuleTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String fromType(int type) {
        for (RuleTypeEnum e : values()) {
            if (e.type==type) {
                return e.getValue();
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    public static List<Map<String, Object>> getAllEnumValues() {
        List<Map<String, Object>> enumValues = new ArrayList<>();
        for (RuleTypeEnum e : values()) {
            Map<String, Object> enumValueMap = new HashMap<>();
            enumValueMap.put("type", e.getType());
            enumValueMap.put("value", e.getValue());
            enumValues.add(enumValueMap);
        }
        return enumValues;
    }
}
