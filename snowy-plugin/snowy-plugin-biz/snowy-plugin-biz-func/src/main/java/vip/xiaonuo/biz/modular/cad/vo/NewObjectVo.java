package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 新的对象实体
 */

@Getter
@Setter
public class NewObjectVo {

    public NewObjectVo(String prjId, String stageId, String subItemId, String professionId, String prjName, String stageName, String subItemName, String professionName) {
        this.prjId = prjId;
        this.stageId = stageId;
        this.subItemId = subItemId;
        this.professionId = professionId;
        this.prjName = prjName;
        this.stageName = stageName;
        this.subItemName = subItemName;
        this.professionName = professionName;
    }

    public NewObjectVo(String prjId, String stageId, String prjName, String stageName) {
        this.prjId = prjId;
        this.stageId = stageId;
        this.prjName = prjName;
        this.stageName = stageName;
    }

    public NewObjectVo(String prjId, String stageId, String subItemId, String prjName, String stageName, String subItemName) {
        this.prjId = prjId;
        this.stageId = stageId;
        this.subItemId = subItemId;
        this.prjName = prjName;
        this.stageName = stageName;
        this.subItemName = subItemName;
    }

    /** 外键：项目Id -> BIZ_CAD_PROJECT.ID */
    @ApiModelProperty(value = "外键：项目Id -> BIZ_CAD_PROJECT.ID")
    private String prjId;

    /** 外键：阶段Id -> BIZ_CAD_STAGE.ID */
    @ApiModelProperty(value = "外键：阶段Id -> BIZ_CAD_STAGE.ID")
    private String stageId;

    /** 外键：子项Id -> BIZ_CAD_SUB_ITEM.ID */
    @ApiModelProperty(value = "外键：子项Id -> BIZ_CAD_SUB_ITEM.ID")
    private String subItemId;

    /** 外键：cad专业Id -> BIZ_CAD_PROFESSION.ID */
    @ApiModelProperty(value = "外键：cad专业Id -> BIZ_CAD_PROFESSION.ID")
    private String professionId;

    /** 项目 - 名称 */
    @ApiModelProperty(value = " 项目 - 名称 ")
    private String prjName;

    /** 阶段 - 名称 */
    @ApiModelProperty(value = " 阶段 - 名称 ")
    private String stageName;

    /** 子项 - 名称 */
    @ApiModelProperty(value = " 子项 - 名称 ")
    private String subItemName;

    /** 专业 - 名称 */
    @ApiModelProperty(value = "专业 - 名称")
    private String professionName;

}
