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
package vip.xiaonuo.biz.modular.epc.progress.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * EPC-施工影像表编辑参数
 *
 * @author yong.ye
 * @date  2023/09/05 14:50
 **/
@Getter
@Setter
public class EpcBuildImageEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 项目ID(外键) */
    @ApiModelProperty(value = "项目ID(外键)", position = 3)
    private String projectId;

    /** 施工影像类型：关键部位|总体 */
    @ApiModelProperty(value = "施工影像类型：关键部位|总体", position = 4)
    private String type;

    /** 拍摄日期 */
    @ApiModelProperty(value = "拍摄日期", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date shootTime;

    /** 拍摄部位 */
    @ApiModelProperty(value = "拍摄部位", position = 6)
    private String shootPosition;

    /** 施工内容 */
    @ApiModelProperty(value = "施工内容", position = 7)
    private String content;

    /** 整体形象进度 */
    @ApiModelProperty(value = "整体形象进度", position = 8)
    private String stage;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

}
