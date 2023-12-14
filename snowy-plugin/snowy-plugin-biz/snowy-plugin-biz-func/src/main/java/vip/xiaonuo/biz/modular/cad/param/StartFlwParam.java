package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 发起提资|校审 - 参数实体
 */

@Getter
@Setter
public class StartFlwParam {

    /** 需要归档的文件历史记录集合【支持多个】 -- 备注：是操作归档时，当做一次历史，CadFileHistory.Id */
    @ApiModelProperty(value = "需要归档的文件历史记录集合【支持多个】 -- 备注：是操作归档时，当做一次历史，CadFileHistory.Id", position = 1)
    private List<CadFileHistoryAddParam> fileHisList;

    /** 选择的存放目录的目录Id -- 备注：目录来源于设计区当前文件目录,文件树CadFile.Id */
    @ApiModelProperty(value = "选择的存放目录的目录Id -- 备注：目录来源于设计区当前文件目录,文件树CadFile.Id", position = 2)
    private String storageId;

    /** 当前操作人 */
    @ApiModelProperty(value = "当前操作人", position = 3)
    private String oprId;

    /** 外键：项目Id -> BIZ_CAD_PROJECT.ID */
    @ApiModelProperty(value = "外键：项目Id -> BIZ_CAD_PROJECT.ID", position = 4)
    private String prjId;

    /** 外键：阶段Id -> BIZ_CAD_STAGE.ID */
    @ApiModelProperty(value = "外键：阶段Id -> BIZ_CAD_STAGE.ID", position = 5)
    private String stageId;

    /** 外键：子项Id -> BIZ_CAD_SUB_ITEM.ID */
    @ApiModelProperty(value = "外键：子项Id -> BIZ_CAD_SUB_ITEM.ID", position = 6)
    private String subItemId;

    /** 外键：cad专业Id -> BIZ_CAD_PROFESSION.ID */
    @ApiModelProperty(value = "外键：cad专业Id -> BIZ_CAD_PROFESSION.ID", position = 7)
    private String professionId;

    /** 设置的校审人集合  */
    @ApiModelProperty(value = "设置的校审人集合 ", position = 8)
    private List<ProofreadParam> proofreadList;

}
