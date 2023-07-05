package vip.xiaonuo.biz.modular.crm.invoiceregister.vo;

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
 * @date 2023/7/4 9:34
 */

@Getter
@Setter
public class InvoiceRegisterPageInfoVo {


    /** 主键ID（历史表InvoiceRegister.Id） */
    @ApiModelProperty(value = "主键ID（历史表InvoiceRegister.Id）", position = 1)
    private String id;

    /** 流水号SN（历史表InvoiceRegister.SN） */
    @ApiModelProperty(value = "流水号SN（历史表InvoiceRegister.SN）", position = 2)
    private String sn;

    /** 登记人（历史表InvoiceRegister.Opt） */
    @ApiModelProperty(value = "登记人（历史表InvoiceRegister.Opt）", position = 3)
    private String opt;

    /** 登记日期（历史表InvoiceRegister.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表InvoiceRegister.OptDate）", position = 4)
    private Date optDate;

    /** 合同编号（历史表InvoiceRegister.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表InvoiceRegister.ContractNo）", position = 5)
    private String contractNo;

    /** 开票时间（历史表InvoiceRegister.MakeTime） */
    @ApiModelProperty(value = "开票时间（历史表InvoiceRegister.MakeTime）", position = 6)
    private Date makeTime;

    /** 开票金额（历史表InvoiceRegister.MakeAmount） */
    @ApiModelProperty(value = "开票金额（历史表InvoiceRegister.MakeAmount）", position = 7)
    private BigDecimal makeAmount;

    /** 票号或备注（历史表InvoiceRegister.Attachment） */
    @ApiModelProperty(value = "票号或备注（历史表InvoiceRegister.Attachment）", position = 8)
    private String note;

    /** 发票扫描件（历史表InvoiceRegister.Attachments） */
    @ApiModelProperty(value = "发票扫描件（历史表InvoiceRegister.Attachments）", position = 9)
    private String attachments;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 15)
    private String contractName;

    /** 责任部门-来源于hetong.CompanyOrDepartment  */
    @ApiModelProperty(value = "责任部门-来源于hetong.CompanyOrDepartment ", position = 16)
    private String companyOrDepartment;
}
