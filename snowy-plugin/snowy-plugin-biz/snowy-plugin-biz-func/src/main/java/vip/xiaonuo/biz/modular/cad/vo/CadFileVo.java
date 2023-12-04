package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.cad.entity.CadFile;


/**
 * 【CAD-插件端 文件树数据结构】
 */
@Getter
@Setter
public class CadFileVo extends CadFile {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 父级Id */
    @ApiModelProperty(value = "父级Id", position = 3)
    private String parentId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 4)
    private String name;

    /** 文件类型:文件夹|文件|批注 */
    @ApiModelProperty(value = "文件类型:文件夹|文件|批注", position = 5)
    private String type;

//    /** 状态 */
//    @ApiModelProperty(value = "状态", position = 6)
//    private String status;
//
//    /** 操作人 */
//    @ApiModelProperty(value = "操作人", position = 7)
//    private String oprUser;
//
//    /** 操作时间 */
//    @ApiModelProperty(value = "操作时间", position = 8)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date oprTime;

    /** 存储云线轮廓的坐标点集 */
    @ApiModelProperty(value = "存储云线轮廓的坐标点集", position = 9)
    private String cloudPoints;

    /** 存储批注文字的坐标点集 */
    @ApiModelProperty(value = "存储批注文字的坐标点集", position = 10)
    private String notePoints;

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

}
