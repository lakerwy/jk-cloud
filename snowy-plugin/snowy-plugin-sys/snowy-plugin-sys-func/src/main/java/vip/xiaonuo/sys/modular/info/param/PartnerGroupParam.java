package vip.xiaonuo.sys.modular.info.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/6 10:41
 */

@Getter
@Setter
public class PartnerGroupParam {

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 伙伴类型集合（默认：分公司） */
    @ApiModelProperty(value = "伙伴类型集合（默认：分公司）")
    private List<String> partnerTypes;

    /** 大区 */
    @ApiModelProperty(value = "大区")
    private String region;

    /** 伙伴状态集合（默认：协议伙伴） */
    @ApiModelProperty(value = "伙伴状态集合（默认：协议伙伴）")
    private List<String> partnerStatus;
}
