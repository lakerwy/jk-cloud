package vip.xiaonuo.biz.modular.epc.provider.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/25 16:45
 */


@Getter
@Setter
public class EpcProviderAssCheckParam {

    /** 外键-供应商Id */
    @ApiModelProperty(value = "外键-供应商Id", position = 3)
    private String prvId;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 4)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 5)
    private String season;

    /** 考核类型：1 供货 2 分包 */
    @ApiModelProperty(value = "考核类型：1 供货 2 分包", position = 6)
    private String assType;

}
