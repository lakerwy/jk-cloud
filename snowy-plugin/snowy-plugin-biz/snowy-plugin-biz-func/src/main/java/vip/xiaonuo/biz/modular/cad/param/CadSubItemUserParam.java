package vip.xiaonuo.biz.modular.cad.param;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 子项用户关系配置
 */

@Getter
@Setter
public class CadSubItemUserParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 外键：子项Id -> BIZ_CAD_SUB_ITEM.ID */
    @ApiModelProperty(value = "外键：子项Id -> BIZ_CAD_SUB_ITEM.ID", position = 3)
    private String subItemId;

    /** 外键：cad用户Id -> BIZ_CAD_USER.ID */
    @ApiModelProperty(value = "外键：cad用户Id -> BIZ_CAD_USER.ID", position = 4)
    private String cadUserId;

    /** 外键：cad专业Id -> BIZ_CAD_PROFESSION.ID */
    @ApiModelProperty(value = "外键：cad专业Id -> BIZ_CAD_PROFESSION.ID", position = 5)
    private String professionId;

    /** 是否专业负责人 */
    @ApiModelProperty(value = "是否专业负责人", position = 6)
    private String isMajor;

    /** 是否审定 */
    @ApiModelProperty(value = "是否审定", position = 7)
    private String isExamine;

    /** 是否审核 */
    @ApiModelProperty(value = "是否审核", position = 8)
    private String isAudit;

    /** 是否校对 */
    @ApiModelProperty(value = "是否校对", position = 9)
    private String isProofread;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;
}
