package vip.xiaonuo.sys.modular.hr.honorAward.enums;

import lombok.Getter;

/**
 * 项目成果枚举
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
@Getter
public enum HonorAwardEnum {

    /** 项目成果 */
    PRJ_RESULT("项目成果");

    private final String value;

    HonorAwardEnum(String value) {
        this.value = value;
    }
}
