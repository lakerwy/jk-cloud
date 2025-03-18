package vip.xiaonuo.sys.modular.hr.icManage.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工商管理实体
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
@Getter
@Setter
@TableName("HR_IC_MANAGE")
public class IcManage {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 2)
    private String companyName;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码", position = 4)
    private String corpCode;

    /** 注册资本(元) */
    @ApiModelProperty(value = "注册资本(元)", position = 5)
    private BigDecimal registeredCapital;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围", position = 6)
    private String businessScope;

    /** 实缴资本(元) */
    @ApiModelProperty(value = "实缴资本(元)", position = 7)
    private BigDecimal paidinCapital;

    /** 住所 */
    @ApiModelProperty(value = "住所", position = 8)
    private String address;

    /** 成立日期 */
    @ApiModelProperty(value = "成立日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date establishTime;

    /** 法定代表人 */
    @ApiModelProperty(value = "法定代表人", position = 10)
    private String legalRepresentative;

    /** 法定代表人身份信息 */
    @ApiModelProperty(value = "法定代表人身份信息", position = 11)
    private String legalRepresentativeId;

    /** 经营期限类型 */
    @ApiModelProperty(value = "经营期限类型（长期|有期限时间）", position = 11)
    private String businessTermType;

    /** 成立日期 */
    @ApiModelProperty(value = "经营期限-开始时间", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessTermStart;

    /** 成立日期 */
    @ApiModelProperty(value = "经营期限-结束时间", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessTermEnd;

    /** 股东信息JSON */
    @ApiModelProperty(value = "股东信息JSON", position = 14)
    private String shareholderInfoJson;

    /** 主要人员JSON */
    @ApiModelProperty(value = "主要人员JSON", position = 15)
    private String mainPersonnelJson;

    /** 工商变更记录JSON */
    @ApiModelProperty(value = "工商变更记录JSON", position = 16)
    private String icUpdateRecordJson;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 20)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 21)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 22)
    private String attachments;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 23)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 24)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 25)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
