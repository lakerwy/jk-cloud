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
package vip.xiaonuo.biz.modular.epc.safety.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EPC-现场人员台账信息表实体
 *
 * @author yong.ye
 * @date  2023/09/05 14:54
 **/
@Getter
@Setter
@TableName("EPC_PERSONNEL_LEDGER")
public class EpcPersonnelLedger {

    /** 主键Id|编号 */
    @TableId
    @ApiModelProperty(value = "主键Id|编号", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 项目ID(外键) */
    @ApiModelProperty(value = "项目ID(外键)", position = 3)
    private String projectId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 5)
    private String idCard;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 6)
    private String gender;

    /** 年龄 */
    @ApiModelProperty(value = "年龄", position = 7)
    private String age;

    /** 联系电话 */
    @ApiModelProperty(value = "联系电话", position = 8)
    private String phone;

    /** 在职状态 */
    @ApiModelProperty(value = "在职状态", position = 9)
    private String hireStatus;

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位", position = 10)
    private String belongCompany;

    /** 职务工种 */
    @ApiModelProperty(value = "职务工种", position = 11)
    private String workType;

    /** 进场时间 */
    @ApiModelProperty(value = "进场时间", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enterTime;

    /** 退场时间 */
    @ApiModelProperty(value = "退场时间", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date outTime;

    /** 是否在保 */
    @ApiModelProperty(value = "是否在保", position = 14)
    private String isInsured;

    /** 保额（元） */
    @ApiModelProperty(value = "保额（元）", position = 15)
    private BigDecimal insureAmount;

    /** 保险单据 */
    @ApiModelProperty(value = "保险单据", position = 16)
    private String insureAttach;

    /** 是否进行了三级安全教育 */
    @ApiModelProperty(value = "是否进行了三级安全教育", position = 17)
    private String isSafety;

    /** 三级安全教育试卷 */
    @ApiModelProperty(value = "三级安全教育试卷", position = 18)
    private String safetyAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 19)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
