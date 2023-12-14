package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 校审区 - 收到的校审|已处理的校审数据实体结构
 */

@Getter
@Setter
public class CadAuditInfo {

    /** 收到的校审请求 */
    @ApiModelProperty(value = "收到的校审请求", position = 1)
    private List<CadProofreadVo> receiveList;

    /** 已处理的校审请求 */
    @ApiModelProperty(value = "已处理的校审请求", position = 2)
    private List<CadProofreadVo> dealList;

}
