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
package vip.xiaonuo.sys.modular.hr.sub.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * HR-分公司-工资社保转入|转出总部添加参数
 *
 * @author yong.ye
 * @date  2024/01/18 09:41
 **/
@Getter
@Setter
public class HrSubTransferAddParam {

    /** 类型：1 工资社保转入总部| 2 工资社保转出总部 */
    @ApiModelProperty(value = "类型：1 工资社保转入总部| 2 工资社保转出总部", position = 2)
    private String type;

    /** 申请单位（XX分公司） */
    @ApiModelProperty(value = "申请单位（XX分公司）", position = 3)
    private String applyUnit;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 4)
    private String regionManager;

    /** 转入单位|社保转入地 */
    @ApiModelProperty(value = "转入单位|社保转入地", position = 5)
    private String transferUnit;

    /** 转入月份：XX年XX月 */
    @ApiModelProperty(value = "转入月份：XX年XX月", position = 6)
    private String transferMonth;

    /** 审批事项 */
    @ApiModelProperty(value = "审批事项", position = 7)
    private String auditItems;

    /** 停缴月份：XX年XX月 */
    @ApiModelProperty(value = "停缴月份：XX年XX月", position = 8)
    private String stopMonth;

    /** 转入|转出原因 */
    @ApiModelProperty(value = "转入|转出原因", position = 9)
    private String transferReason;

    /** 申请单位ID(部门ID|组织ID) */
    @ApiModelProperty(value = "申请单位ID(部门ID|组织ID)", position = 10)
    private String deptId;

    /** 分公司人员ID(HR_SUB_PERSONNEL.ID) */
    @ApiModelProperty(value = "分公司人员ID(HR_SUB_PERSONNEL.ID)", position = 11)
    private String personnelId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 12)
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 13)
    private String idNumber;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 14)
    private String duty;

    /** 入职时间 */
    @ApiModelProperty(value = "入职时间", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 职称专业 */
    @ApiModelProperty(value = "职称专业", position = 16)
    private String highTitleProfession;

    /** 职称等级 */
    @ApiModelProperty(value = "职称等级", position = 17)
    private String highTitleLever;

    /** 职称资格证 */
    @ApiModelProperty(value = "职称资格证", position = 18)
    private String highTitleNo;

    /** 联系电话|手机 */
    @ApiModelProperty(value = "联系电话|手机", position = 19)
    private String phone;

    /** 银行卡号 */
    @ApiModelProperty(value = "银行卡号", position = 20)
    private String bankNo;

    /** 银行卡开户行名称 */
    @ApiModelProperty(value = "银行卡开户行名称", position = 21)
    private String bankName;

    /** 上传相关证件 */
    @ApiModelProperty(value = "上传相关证件", position = 22)
    private String attach;

    /** 上传承诺书 */
    @ApiModelProperty(value = "上传承诺书", position = 23)
    private String promiseAttach;

    /** 代缴工资 */
    @ApiModelProperty(value = "代缴工资", position = 24)
    private String behalfPay;

    /** 代缴社保基数 */
    @ApiModelProperty(value = "代缴社保基数", position = 25)
    private String behalfInsBase;

    /** 是否代缴公积金 */
    @ApiModelProperty(value = "是否代缴公积金", position = 26)
    private String isBehalfGjj;

    /** 代缴公积金基数 */
    @ApiModelProperty(value = "代缴公积金基数", position = 27)
    private String behalfGjjBase;

    /** 代缴起止时间 */
    @ApiModelProperty(value = "代缴起止时间", position = 28)
    private String behalfStartEnd;

    /** 费用结算周期 */
    @ApiModelProperty(value = "费用结算周期", position = 29)
    private String expenseCycle;

    /** 付款方式 */
    @ApiModelProperty(value = "付款方式", position = 30)
    private String payMethod;

    /** 费用所属期 */
    @ApiModelProperty(value = "费用所属期", position = 31)
    private String payBelong;

    /** 代缴工资合计 */
    @ApiModelProperty(value = "代缴工资合计", position = 32)
    private String payTotal;

    /** 代缴社保公积金合计 */
    @ApiModelProperty(value = "代缴社保公积金合计", position = 33)
    private String insGjjTotal;

    /** 残保金合计 */
    @ApiModelProperty(value = "残保金合计", position = 34)
    private String insuTotal;

    /** 总费用 */
    @ApiModelProperty(value = "总费用", position = 35)
    private String total;

    /** 缴费凭证 */
    @ApiModelProperty(value = "缴费凭证", position = 36)
    private String payAttach;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 37)
    private String extJson;

//    /** 数据权限控制字段 */
//    @ApiModelProperty(value = "数据权限控制字段", position = 44)
//    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 45)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 46)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 47)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 48)
    private String status;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 49)
    private String memo;

}
