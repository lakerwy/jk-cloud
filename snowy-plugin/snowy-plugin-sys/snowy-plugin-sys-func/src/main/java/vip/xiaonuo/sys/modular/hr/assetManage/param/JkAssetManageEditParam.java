/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.assetManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 建科资产管理表编辑参数
 *
 * @author ljy
 * @date  2024/04/10 08:47
 **/
@Getter
@Setter
public class JkAssetManageEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 资产标题 */
    @ApiModelProperty(value = "资产标题", position = 2)
    private String assetTitle;

    /** 资产图片(多数据)，逗号隔开 */
    @ApiModelProperty(value = "资产图片(多数据)，逗号隔开", position = 3)
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

}
