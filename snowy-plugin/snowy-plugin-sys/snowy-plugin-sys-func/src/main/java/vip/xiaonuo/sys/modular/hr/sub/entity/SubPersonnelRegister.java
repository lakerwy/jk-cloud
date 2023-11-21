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
package vip.xiaonuo.sys.modular.hr.sub.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * HR-分公司- 签约信息实体
 *
 * @author yong.ye
 * @date  2023/11/21 11:18
 **/
@Getter
@Setter
@TableName("HR_SUB_PERSONNEL_REGISTER")
public class SubPersonnelRegister {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 外键 - 档案ID（HR_SUB_PERSONNEL.Id） */
    @ApiModelProperty(value = "外键 - 档案ID（HR_SUB_PERSONNEL.Id）", position = 2)
    private String fileId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;

    /** 起始时间 */
    @ApiModelProperty(value = "起始时间", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /** 到期时间 */
    @ApiModelProperty(value = "到期时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;

    /** 签约单位 */
    @ApiModelProperty(value = "签约单位", position = 6)
    private String signUnit;

    /** 合同附件 */
    @ApiModelProperty(value = "合同附件", position = 7)
    private String attachments;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 8)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 15)
    private String dataScopeId;
}
