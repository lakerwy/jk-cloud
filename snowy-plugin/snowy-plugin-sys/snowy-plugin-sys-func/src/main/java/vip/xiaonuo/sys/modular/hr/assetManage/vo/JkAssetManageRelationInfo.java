package vip.xiaonuo.sys.modular.hr.assetManage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JkAssetManageRelationInfo extends JkAssetManageRelationVo{

    /** 资产标题 */
    @ApiModelProperty(value = "资产标题", position = 2)
    private String assetTitle;

    /** 资产图片(多数据)，逗号隔开 */
    @ApiModelProperty(value = "资产图片(多数据)，分号隔开", position = 3)
    private String assetFileIds;

    /** 资产类别：资质；荣誉奖项；房产资料；专利；著作权 */
    @ApiModelProperty(value = "资产类别：资质；荣誉奖项；房产资料；专利；著作权", position = 4)
    private String assetType;

    /** 资产简介 */
    @ApiModelProperty(value = "资产简介", position = 5)
    private String assetIntroduce;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String remark;

}
