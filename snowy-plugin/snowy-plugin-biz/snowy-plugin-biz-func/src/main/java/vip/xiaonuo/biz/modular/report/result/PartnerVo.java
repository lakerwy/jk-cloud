package vip.xiaonuo.biz.modular.report.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/7/28 8:46
 */

@Getter
@Setter
public class PartnerVo {
    @ApiModelProperty(value = "ID(主键)", position = 1)
    private String id;

    /** 合作伙伴 主键ID */
    @ApiModelProperty(value = "合作伙伴ID", position = 2)
    private String orgId;

    @ApiModelProperty(value = "伙伴编号",position = 3)
    private String sn;

    @ApiModelProperty(value = "伙伴名称",position = 4)
    private String name;

    @ApiModelProperty(value = "伙伴类型", position = 5)
    private String partnerType;
}
