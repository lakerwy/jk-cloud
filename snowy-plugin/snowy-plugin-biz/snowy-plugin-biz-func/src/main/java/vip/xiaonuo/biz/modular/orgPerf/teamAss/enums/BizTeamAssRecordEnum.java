package vip.xiaonuo.biz.modular.orgPerf.teamAss.enums;

import lombok.Getter;

/**
 * 班子考核记录枚举
 *
 * @author pzh
 * @date  2025/06/17 17:08
 **/
@Getter
public enum BizTeamAssRecordEnum {

    /** 测试 */
    TEST("TEST");

    private final String value;

    BizTeamAssRecordEnum(String value) {
        this.value = value;
    }
}
