package vip.xiaonuo.biz.modular.cad.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目结构
 */
@Getter
@Setter
public class PrjStageSubProfVo {

    /** 外键：项目Id -> BIZ_CAD_PROJECT.ID */
    @ApiModelProperty(value = "外键：项目Id -> BIZ_CAD_PROJECT.ID", position = 11)
    private String prjId;

    /** 外键：阶段Id -> BIZ_CAD_STAGE.ID */
    @ApiModelProperty(value = "外键：阶段Id -> BIZ_CAD_STAGE.ID", position = 12)
    private String stageId;

    /** 外键：子项Id -> BIZ_CAD_SUB_ITEM.ID */
    @ApiModelProperty(value = "外键：子项Id -> BIZ_CAD_SUB_ITEM.ID", position = 11)
    private String subItemId;

    /** 外键：cad专业Id -> BIZ_CAD_PROFESSION.ID */
    @ApiModelProperty(value = "外键：cad专业Id -> BIZ_CAD_PROFESSION.ID", position = 12)
    private String professionId;

    /** 项目 - 名称 */
    @ApiModelProperty(value = " 项目 - 名称 ", position = 20)
    private String prjName;

    /** 阶段 - 名称 */
    @ApiModelProperty(value = " 阶段 - 名称 ", position = 20)
    private String stageName;

    /** 子项 - 名称 */
    @ApiModelProperty(value = " 子项 - 名称 ", position = 20)
    private String subItemName;

    /** 专业 - 名称 */
    @ApiModelProperty(value = "专业 - 名称", position = 20)
    private String professionName;

    /** 专业排序 */
    @ApiModelProperty(value = "专业排序", position = 20)
    private Integer professionIndex;

    /** 阶段排序 */
    @ApiModelProperty(value = "阶段排序", position = 20)
    private Integer stageIndex;
}
