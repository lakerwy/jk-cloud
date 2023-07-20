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
package vip.xiaonuo.sys.modular.hr.fileMoves.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 业务表-人员异动主表添加参数
 *
 * @author mayike
 * @date  2023/07/10 16:56
 **/
@Getter
@Setter
public class PersonnelFileMovesAddParam {

    /** 人员档案ID */
    @ApiModelProperty(value = "人员档案ID", position = 2)
    private String fileId;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 经办日期 */
    @ApiModelProperty(value = "经办日期", position = 3)
    private Date optDate;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 4)
    private String opt;

    /** SN */
    @ApiModelProperty(value = "SN", position = 5)
    private String sn;

    /** 审批单号 */
    @ApiModelProperty(value = "审批单号", position = 6)
    private String approvalNo;

    /** 异动类型（字典） */
    @ApiModelProperty(value = "异动类型（字典）", position = 7)
    private String moveType;

    /** 异动事项 */
    @ApiModelProperty(value = "异动事项", position = 8)
    private String moveItem;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attachments;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 10)
    private String useNature;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 11)
    private Date positiveTime;

    /** 现部门ID */
    @ApiModelProperty(value = "现部门ID", position = 12)
    private String origDeptId;

    /** 现岗位ID */
    @ApiModelProperty(value = "现岗位ID", position = 13)
    private String origDutyId;

    /** 现部门 */
    @ApiModelProperty(value = "现部门", position = 14)
    private String origDept;

    /** 现岗位 */
    @ApiModelProperty(value = "现岗位", position = 15)
    private String origDuty;

    /** 状态表更 */
    @ApiModelProperty(value = "状态表更", position = 16)
    private String stateChange;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 离职时间 */
    @ApiModelProperty(value = "离职时间", position = 17)
    private Date livetime;

    /** 新岗位ID */
    @ApiModelProperty(value = "新岗位ID", position = 18)
    private String nowDeptId;

    /** 新部门ID */
    @ApiModelProperty(value = "新部门ID", position = 19)
    private String nowDutyId;

    /** 新部门 */
    @ApiModelProperty(value = "新部门", position = 20)
    private String nowDept;

    /** 新岗位 */
    @ApiModelProperty(value = "新岗位", position = 21)
    private String nowDuty;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 22)
    private String extJson;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 29)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 30)
    private String processInstanceId;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 31)
    private String applyUser;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 32)
    private Date applyTime;

    /** 试用期考核结果 */
    @ApiModelProperty(value = "试用期考核结果", position = 33)
    private String probationaryResult;

    /** 试用期薪酬 */
    @ApiModelProperty(value = "试用期薪酬", position = 34)
    private String probationaryCompensation;

    /** 转正薪酬 */
    @ApiModelProperty(value = "转正薪酬", position = 35)
    private String positiveCompensation;

//    /** 试用期职称 */
//    @ApiModelProperty(value = "试用期职称", position = 36)
//    private String probationaryJobTitle;
//
//    /** 转正职称 */
//    @ApiModelProperty(value = "转正职称", position = 37)
//    private String positiveJobTitle;

    /** 直接上级工作评价/工作评价/离职原因 */
    @ApiModelProperty(value = "直接上级工作评价/工作评价/离职原因", position = 38)
    private String directWorkOpinion;

    /** 部门第一负责人意见 */
    @ApiModelProperty(value = "部门第一负责人意见", position = 39)
    private String leaderWorkOpinion;

    /** 人力资源中心意见 */
    @ApiModelProperty(value = "人力资源中心意见", position = 40)
    private String hrOpinion;

    /** 董事长意见 */
    @ApiModelProperty(value = "董事长意见", position = 41)
    private String chairmanOpinion;

}
