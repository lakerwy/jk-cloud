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
package vip.xiaonuo.sys.modular.hr.fileMoves.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 业务表-人员异动主表实体
 *
 * @author mayike
 * @date  2023/07/10 16:56
 **/
@Getter
@Setter
@TableName("PERSONNEL_FILE_MOVES")
public class PersonnelFileMoves {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 人员档案ID */
    @ApiModelProperty(value = "人员档案ID", position = 2)
    private String fileId;

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

    /** 转正时间 */
    @ApiModelProperty(value = "转正时间", position = 11)
    private Date positiveTime;

    /** 现部门ID */
    @ApiModelProperty(value = "现部门ID", position = 12)
    private String origDeptId;

    /** 现岗位ID */
    @ApiModelProperty(value = "现岗位ID", position = 13)
    private String origDutyId;

    /** 新部门 */
    @ApiModelProperty(value = "新部门", position = 14)
    private String nowDept;

    /** 新岗位 */
    @ApiModelProperty(value = "新岗位", position = 15)
    private String nowDuty;

    /** 状态表更 */
    @ApiModelProperty(value = "状态表更", position = 16)
    private String stateChange;

    /** 离职时间 */
    @ApiModelProperty(value = "离职时间", position = 17)
    private Date livetime;

    /** 现部门 */
    @ApiModelProperty(value = "现部门", position = 18)
    private String origDept;

    /** 现岗位 */
    @ApiModelProperty(value = "现岗位", position = 19)
    private String origDuty;

    /** 新岗位ID */
    @ApiModelProperty(value = "新岗位ID", position = 20)
    private String nowDeptId;

    /** 新部门ID */
    @ApiModelProperty(value = "新部门ID", position = 21)
    private String nowDutyId;
}
