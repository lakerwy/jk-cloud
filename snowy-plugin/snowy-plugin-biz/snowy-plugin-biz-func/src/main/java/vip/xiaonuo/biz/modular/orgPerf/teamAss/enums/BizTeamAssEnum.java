package vip.xiaonuo.biz.modular.orgPerf.teamAss.enums;

import lombok.Getter;

/**
 * 班子考核枚举
 *
 * @author pzh
 * @date  2025/06/17 16:23
 **/
@Getter
public enum BizTeamAssEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizTeamAssEnum(String value) {
        this.value = value;
    }
}
