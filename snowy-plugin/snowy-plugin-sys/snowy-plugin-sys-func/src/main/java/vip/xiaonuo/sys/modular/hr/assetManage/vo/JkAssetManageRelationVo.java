package vip.xiaonuo.sys.modular.hr.assetManage.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JkAssetManageRelationVo {

    /** ID */
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID", position = 2)
    private String userId;

    /** 申请的资产ID */
    @ApiModelProperty(value = "申请的资产ID", position = 3)
    private String assetId;

    /** 当前资产是否审核通过 */
    @ApiModelProperty(value = "当前资产是否审核通过：0 不通过，1 通过", position = 4)
    private String isPass;

    /** 本次申请ID */
    @ApiModelProperty(value = "本次申请ID", position = 5)
    private String flowId;

}
