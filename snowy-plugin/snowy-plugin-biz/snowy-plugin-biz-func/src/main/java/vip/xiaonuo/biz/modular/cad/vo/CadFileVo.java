package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 【CAD-插件端 文件树数据结构】
 */
@Getter
@Setter
public class CadFileVo extends PrjStageSubProfVo{

    /** 文件Id - 主键ID */
    @ApiModelProperty(value = "文件Id - 主键ID", position = 1)
    private String fId;

    /** 父级Id */
    @ApiModelProperty(value = "父级Id", position = 3)
    private String fParentId;

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 4)
    private String fName;

    /** 文件类型:文件夹|文件|批注 */
    @ApiModelProperty(value = "文件类型:文件夹|文件|批注", position = 5)
    private String fType;

    /** 存储云线轮廓的坐标点集 */
    @ApiModelProperty(value = "存储云线轮廓的坐标点集", position = 9)
    private String fCloudPoints;

    /** 存储批注文字的坐标点集 */
    @ApiModelProperty(value = "存储批注文字的坐标点集", position = 10)
    private String fNotePoints;

    /** 文件类型:文件夹|文件|批注 状态 */
    @ApiModelProperty(value = "文件类型:文件夹|文件|批注", position = 10)
    private String fStatus;

    /** 源始数据 - 目录主键Id  用于批注，写回最开始的文件下 【CadFile.Id | CadStorage.OldFID】 */
    @ApiModelProperty(value = "源始数据 - 目录主键Id  用于批注，写回最开始的文件下 【CadFile.Id | CadStorage.OldFID】", position = 11)
    private String oldFId;
}
