package vip.xiaonuo.biz.modular.prj.exp.enums;

import lombok.Getter;

/**
 * 费用报销数据版本枚举
 *
 * @author pzh
 * @date  2025/06/16 17:36
 **/
@Getter
public enum BizFormVersionEnum {

    /** 旧版 */
    ZERO("0"),

    /** 新版 */
    ONE("1");

    private final String value;

    BizFormVersionEnum(String value) {
        this.value = value;
    }
}
