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
package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CAD-文件历史版本记录表添加参数
 *
 * @author yong.ye
 * @date  2023/12/01 15:34
 **/
@Getter
@Setter
public class CadFileHistoryAddParam {

    /** 外键：文件Id -> BIZ_CAD_FILE.ID */
    @ApiModelProperty(value = "外键：文件Id -> BIZ_CAD_FILE.ID", position = 3)
    private String fileId;

    /** 版本号 */
    @ApiModelProperty(value = "版本号", position = 4)
    private Integer version;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小", position = 5)
    private Long fileSize;

    /** 是否重要版本 */
    @ApiModelProperty(value = "是否重要版本", position = 6)
    private String isImportant;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 7)
    private String status;

    /** 下载地址 */
    @ApiModelProperty(value = "下载地址", position = 8)
    private String downLoadUrl;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

}
