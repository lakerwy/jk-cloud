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
package vip.xiaonuo.biz.modular.crm.rollinout.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-合作伙伴人员转入转出编辑参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:56
 **/
@Getter
@Setter
public class RollinoutEditParam {

    /** 主键ID（历史表RollInOut.Id） */
    @ApiModelProperty(value = "主键ID（历史表RollInOut.Id）", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 合作伙伴人员信息Id主键（历史表RollInOut.FPID） */
    @ApiModelProperty(value = "合作伙伴人员信息Id主键（历史表RollInOut.FPID）", position = 2)
    private String fpId;

    /** 流水号SN（历史表RollInOut.SN） */
    @ApiModelProperty(value = "流水号SN（历史表RollInOut.SN）", position = 3)
    private String sn;

    /** 登记人（历史表RollInOut.Opt） */
    @ApiModelProperty(value = "登记人（历史表RollInOut.Opt）", position = 4)
    private String opt;

    /** 登记日期（历史表RollInOut.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表RollInOut.OptDate）", position = 5)
    private Date optDate;

    /** 操作（历史表RollInOut.Operate） */
    @ApiModelProperty(value = "操作（历史表RollInOut.Operate）", position = 6)
    private String operate;

    /** 社保（历史表RollInOut.Socail） */
    @ApiModelProperty(value = "社保（历史表RollInOut.Socail）", position = 7)
    private String social;

    /** 公积金（历史表RollInOut.Fund） */
    @ApiModelProperty(value = "公积金（历史表RollInOut.Fund）", position = 8)
    private String fund;

    /** 备注（历史表RollInOut.Note） */
    @ApiModelProperty(value = "备注（历史表RollInOut.Note）", position = 9)
    private String note;

    /** 钉钉单号（历史表RollInOut.OddNo） */
    @ApiModelProperty(value = "钉钉单号（历史表RollInOut.OddNo）", position = 10)
    private String oddNo;

    /** 姓名（历史表RollInOut.Name） */
    @ApiModelProperty(value = "姓名（历史表RollInOut.Name）", position = 11)
    private String name;

    /** 部门（历史表RollInOut.Dept） */
    @ApiModelProperty(value = "部门（历史表RollInOut.Dept）", position = 12)
    private String dept;

    /** 职称（历史表RollInOut.Rank） */
    @ApiModelProperty(value = "职称（历史表RollInOut.Rank）", position = 13)
    private String rank;

    /** 社保缴纳期限（历史表RollInOut.Limit） */
    @ApiModelProperty(value = "社保缴纳期限（历史表RollInOut.Limit）", position = 14)
    private String limit;

    /** 专业（历史表RollInOut.Majors） */
    @ApiModelProperty(value = "专业（历史表RollInOut.Majors）", position = 15)
    private String majors;

}
