package vip.xiaonuo.biz.modular.crm.contractrr.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/4 8:57
 */

@Getter
@Setter
public class ContractrrPageInfoVo {
    /** 主键ID（历史表ContractRR.Id） */
    @ApiModelProperty(value = "主键ID（历史表ContractRR.Id）", position = 1)
    private String id;

    /** 流水号SN（历史表ContractRR.SN） */
    @ApiModelProperty(value = "流水号SN（历史表ContractRR.SN）", position = 2)
    private String sn;

    /** 登记人（历史表ContractRR.Opt） */
    @ApiModelProperty(value = "登记人（历史表ContractRR.Opt）", position = 3)
    private String opt;

    /** 登记日期（历史表ContractRR.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表ContractRR.OptDate）", position = 4)
    private Date optDate;

    /** 合同编号（历史表ContractRR.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表ContractRR.ContractNo）", position = 5)
    private String contractNo;

    /** 收款时间（历史表ContractRR.CollectDate） */
    @ApiModelProperty(value = "收款时间（历史表ContractRR.CollectDate）", position = 6)
    private Date collectDate;

    /** 收款金额（历史表ContractRR.CollectAmount） */
    @ApiModelProperty(value = "收款金额（历史表ContractRR.CollectAmount）", position = 7)
    private BigDecimal collectAmount;

    /** 银行回单（历史表ContractRR.Attachment） */
    @ApiModelProperty(value = "银行回单（历史表ContractRR.Attachment）", position = 8)
    private String attachments;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 14)
    private String contractName;

    /** 责任部门-来源于hetong.CompanyOrDepartment  */
    @ApiModelProperty(value = "责任部门-来源于hetong.CompanyOrDepartment ", position = 15)
    private String companyOrDepartment;

}
