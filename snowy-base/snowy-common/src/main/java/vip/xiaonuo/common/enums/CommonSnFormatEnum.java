package vip.xiaonuo.common.enums;

import lombok.Getter;

/**
 * 系统流水号Sn 格式枚举
 *
 * @author yong.ye
 * @date 2023/7/3 9:12
 */

@Getter
public enum CommonSnFormatEnum {
    /**
     * Sn 格式 : [前缀][yyyyMM][0001]  每月从0001开始，每次自增1
     * 示例 : Sn = HT2023070001
     */
    SNYYYYMM0001("SNYYYYMM0001"),

    /**
     * Sn 格式 : [前缀][0001]  全体从0001开始，每次自增1
     * 示例 : Sn = ZY0001
     */
    SN0001("SN0001"),

    /**
     * Sn 格式 : [前缀][yyyy][001] 每年从001开始，每次自增1
     * 示例 : Sn = CA2023001
     */
    SNYYYY001("SNYYYY001");

    private final String value;

    CommonSnFormatEnum(String value) {
        this.value = value;
    }
}
