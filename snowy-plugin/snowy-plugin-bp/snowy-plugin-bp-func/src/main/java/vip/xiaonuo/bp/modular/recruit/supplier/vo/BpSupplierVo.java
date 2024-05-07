package vip.xiaonuo.bp.modular.recruit.supplier.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 供应商查询列表返回参数
 */
@Getter
@Setter
public class BpSupplierVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 2)
    private String companyName;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围", position = 3)
    private String businessScope;

    /** 来源类型 */
    @ApiModelProperty(value = "来源类型", position = 4)
    private String source;

    /** 业务负责人姓名 */
    @ApiModelProperty(value = "业务负责人姓名", position = 5)
    private String businessadminName;

    /** 业务负责人手机号 */
    @ApiModelProperty(value = "业务负责人手机号", position = 6)
    private String businessadminPhone;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 审核状态 */
    @ApiModelProperty(value = "审核状态", position = 8)
    private String status;

    /** 黑名单原因 */
    @ApiModelProperty(value = "黑名单原因", position = 9)
    private String blacklistReason;

    /** 黑名单标记 */
    @ApiModelProperty(value = "黑名单标记", position = 10)
    private String isBlacklist;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 11)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 12)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 13)
    private String processInstanceId;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 14)
    private String createUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    /** 供应商ID */
    @ApiModelProperty(value = "供应商ID", position = 16)
    private String supplierId;
}
