package vip.xiaonuo.sys.modular.info.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/6 10:36
 */

@Getter
@Setter
public class PartnerGroupVo {

    @ApiModelProperty(value = "所属大区", position = 1)
    private String region;

    @ApiModelProperty(value = "合计数", position = 2)
    private Integer nums;

    @ApiModelProperty(value = "伙伴列表", position = 2)
    private List<PartnerInfo> infoList;

}
