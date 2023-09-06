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
package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 文件编辑参数
 *
 * @author mayike
 * @date  2023/09/06 21:15
 **/
@Getter
@Setter
public class PanFileEditParam {

    /** ID */
    @ApiModelProperty(value = "ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小", position = 2)
    private Long fileSize;

    /** 文件状态(0-失效，1-生效) */
    @ApiModelProperty(value = "文件状态(0-失效，1-生效)", position = 3)
    private Integer fileStatus;

    /** 文件url */
    @ApiModelProperty(value = "文件url", position = 4)
    private String fileUrl;

    /** md5唯一标识 */
    @ApiModelProperty(value = "md5唯一标识", position = 5)
    private String identifier;

    /** 存储类型 */
    @ApiModelProperty(value = "存储类型", position = 6)
    private String storageType;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 7)
    private String extJson;

}
