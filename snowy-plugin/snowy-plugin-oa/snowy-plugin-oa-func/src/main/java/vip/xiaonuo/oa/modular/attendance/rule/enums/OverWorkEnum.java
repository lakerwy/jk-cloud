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
 */
@Getter
public enum OverWorkEnum {

    OVER_WORK_APPLY("APPLY","加班必须审批,依打卡为准,但是不能超过审批单时长"),

    OVER_WORD_UN_APPLY("UNAPPLY","加班无需审批,根据打卡时间计算加班时长"),

    OVER_WORD_APPLY_TIME("APPLY_TIME","加班必须审批,加班时长已审批单为准");


    private String type;

    private String value;

    OverWorkEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String fromType(String type) {
        for (OverWorkEnum e : values()) {
            if (e.type.equals(type)) {
                return e.getValue();
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    public static List<Map<String, String>> getAllEnumValues() {
        List<Map<String, String>> enumValues = new ArrayList<>();
        for (OverWorkEnum e : values()) {
            Map<String, String> enumValueMap = new HashMap<>();
            enumValueMap.put("type", e.getType());
            enumValueMap.put("value", e.getValue());
            enumValues.add(enumValueMap);
        }
        return enumValues;
    }
}
