package vip.xiaonuo.sys.modular.hr.goods.enums;

import lombok.Getter;

/**
 * 物资领用申请（附表） - 物资清单+退回清单 枚举
 *
 * @author yy
 * @date  2025/05/23 14:35
 **/
@Getter
public enum GoodsTypeEnum {

    /** 物资申请清单 */
    GOODS_APPLY("物资申请"),

    /** 物资退回清单 */
    GOODS_BACK("物资退回");

    private final String value;

    GoodsTypeEnum(String value) {
        this.value = value;
    }
}
