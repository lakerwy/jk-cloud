package vip.xiaonuo.biz.modular.crm.subcontract.enums;

import lombok.Getter;

/**
 * 招采平台接口枚举
 *
 * @author pzh
 * @date  2025/01/22 15:52
 **/
@Getter
public enum ProcureApiEnum {

    /**
     * 获取供应商信息分页
     */
    SUPPLIER_PAGE_LIST("/openApi/backend/supplier/page"),
    ;

    private final String value;

    ProcureApiEnum(String value) {
        this.value = value;
    }
}
