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
package vip.xiaonuo.pan.modular.share.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 网盘-分享表编辑参数
 *
 * @author yong.ye
 * @date  2023/09/07 15:55
 **/
@Getter
@Setter
public class PanShareEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 3)
    private String userId;

    /** 分享时间 */
    @ApiModelProperty(value = "分享时间", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shareTime;

    /** 失效时间 */
    @ApiModelProperty(value = "失效时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 提取码 */
    @ApiModelProperty(value = "提取码", position = 6)
    private String extractionCode;

    /** 分享批次号 */
    @ApiModelProperty(value = "分享批次号", position = 7)
    private String shareBatchNum;

    /** 分享类型(0公共,1私密,2好友) */
    @ApiModelProperty(value = "分享类型(0公共,1私密,2好友)", position = 8)
    private Integer shareType;

    /** 分享状态(0正常,1已失效,2已撤销) */
    @ApiModelProperty(value = "分享状态(0正常,1已失效,2已撤销)", position = 9)
    private Integer shareStatus;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

}
