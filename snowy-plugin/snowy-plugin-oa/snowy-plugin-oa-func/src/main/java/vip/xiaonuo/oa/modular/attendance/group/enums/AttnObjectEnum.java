package vip.xiaonuo.oa.modular.attendance.group.enums;

import lombok.Getter;
import vip.xiaonuo.oa.modular.attendance.rule.enums.RuleTypeEnum;

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
public enum AttnObjectEnum {
    /**
     * 0 公司 1 部门 2 角色 3 员工 4、考勤组
     */

    COMPANY_OBJECT(0,"公司"),

    DEPT_OBJECT(1,"部门"),

    USER_OBJECT(2,"角色"),

    ROLE_OBJECT(3,"用户"),

    GROUP_OBJECT(4,"考勤组");


    private int type;

    private String value;

    AttnObjectEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static String fromType(int type) {
        for (AttnObjectEnum e : values()) {
            if (e.type==type) {
                return e.getValue();
            }
        }
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    public static List<Map<String, Object>> getAllEnumValues() {
        List<Map<String, Object>> enumValues = new ArrayList<>();
        for (AttnObjectEnum e : values()) {
            Map<String, Object> enumValueMap = new HashMap<>();
            enumValueMap.put("type", e.getType());
            enumValueMap.put("value", e.getValue());
            enumValues.add(enumValueMap);
        }
        return enumValues;
    }
}
