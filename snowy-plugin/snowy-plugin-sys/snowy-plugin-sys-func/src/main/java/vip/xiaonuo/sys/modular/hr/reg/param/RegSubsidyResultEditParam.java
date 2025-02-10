package vip.xiaonuo.sys.modular.hr.reg.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册补贴（新版）- 结果反馈编辑参数
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
@Getter
@Setter
public class RegSubsidyResultEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 外键：REG_SUBSIDY.ID */
    @ApiModelProperty(value = "外键：REG_SUBSIDY.ID", position = 2)
    private String regSubsidyId;

    /** 注册等级 */
    @ApiModelProperty(value = "注册等级", position = 3)
    private String level;

    /** 注册类别 */
    @ApiModelProperty(value = "注册类别", position = 4)
    private String type;

    /** 注册专业 */
    @ApiModelProperty(value = "注册专业", position = 5)
    private String major;

    /** 补贴标准 */
    @ApiModelProperty(value = "补贴标准", position = 6)
    private String standard;

    /** 系数 */
    @ApiModelProperty(value = "系数", position = 7)
    private String rate;

    /** 补贴金额 */
    @ApiModelProperty(value = "补贴金额", position = 8)
    private BigDecimal amount;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 9)
    private String feeType;

    /** 费用归属部门 */
    @ApiModelProperty(value = "费用归属部门", position = 10)
    private String belongType;

    /** 是否通过审核 */
    @ApiModelProperty(value = "是否通过审核", position = 11)
    private String isPass;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 申请人：花名册主键id */
    @ApiModelProperty(value = "申请人：花名册主键id", position = 13)
    private String personnelId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 14)
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 15)
    private String idCardNumber;

    /** 注册成功日期 */
    @ApiModelProperty(value = "注册成功日期", position = 30)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
}
