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
package vip.xiaonuo.biz.modular.school.param;

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
 * 校招-简历操作记录编辑参数
 *
 * @author yong.ye
 * @date  2023/11/15 08:55
 **/
@Getter
@Setter
public class ResumeRecordEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 外键-简历Id */
    @ApiModelProperty(value = "外键-简历Id", position = 3)
    private String resumesId;

    /** 操作类型 */
    @ApiModelProperty(value = "操作类型", position = 4)
    private String actionType;

    /** 操作名称 */
    @ApiModelProperty(value = "操作名称", position = 5)
    private String operation;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date oprTime;

    /** 操作人 */
    @ApiModelProperty(value = "操作人", position = 7)
    private String oprName;

    /** 面试官 */
    @ApiModelProperty(value = "面试官", position = 8)
    private String interviewers;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 附件ID */
    @ApiModelProperty(value = "附件ID", position = 10)
    private String attachIds;

    /** 操作结果 */
    @ApiModelProperty(value = "操作结果", position = 11)
    private String result;

    /** 面试时间 */
    @ApiModelProperty(value = "面试时间", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actionTime;

    /** 求职者 */
    @ApiModelProperty(value = "求职者", position = 13)
    private String jobHunter;

    /** 拒绝原因 */
    @ApiModelProperty(value = "拒绝原因", position = 14)
    private String reason;

    /** 面试方式 */
    @ApiModelProperty(value = "面试方式", position = 15)
    private Integer type;

}
