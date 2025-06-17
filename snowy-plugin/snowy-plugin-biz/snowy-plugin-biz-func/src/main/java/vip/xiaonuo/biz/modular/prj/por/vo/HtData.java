package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;

@Getter
@Setter
public class HtData extends HetongDetailVo {

    /** 合同类型 */
    @ApiModelProperty(value = "合同类型", position = 80)
    private String contractType;
}
