package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司-工资发放（流程）编辑参数
 *
 * @author yong.ye
 * @date  2025/02/06 09:09
 **/
@Getter
@Setter
public class SubGzEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 部门Id */
    @ApiModelProperty(value = "部门Id", position = 2)
    private String orgId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 3)
    private String orgName;

    /** 所属月份 */
    @ApiModelProperty(value = "所属月份", position = 4)
    private String yM;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 5)
    private String region;

    /** 大区经理id */
    @ApiModelProperty(value = "大区经理id", position = 6)
    private String managerId;

    /** 大区经理 */
    @ApiModelProperty(value = "大区经理", position = 7)
    private String managerName;

    /** 工资应发人数 */
    @ApiModelProperty(value = "工资应发人数", position = 8)
    private Integer gzYfNum;

    /** 工资实发人数 */
    @ApiModelProperty(value = "工资实发人数", position = 9)
    private Integer gzSfNum;

    /** 应发工资合计 */
    @ApiModelProperty(value = "应发工资合计", position = 10)
    private BigDecimal yfGzTotal;

    /** 实发工资合计 */
    @ApiModelProperty(value = "实发工资合计", position = 11)
    private BigDecimal sfGzTotal;

    /** 奖金应发人数 */
    @ApiModelProperty(value = "奖金应发人数", position = 12)
    private Integer jjYfNum;

    /** 奖金实发人数 */
    @ApiModelProperty(value = "奖金实发人数", position = 13)
    private Integer jjSfNum;

    /** 应发奖金合计 */
    @ApiModelProperty(value = "应发奖金合计", position = 14)
    private BigDecimal yfJjTotal;

    /** 实发奖金合计 */
    @ApiModelProperty(value = "实发奖金合计", position = 15)
    private BigDecimal sfJjTotal;

    /** 工资应扣税额合计 */
    @ApiModelProperty(value = "工资应扣税额合计", position = 16)
    private BigDecimal gzTaxTotal;

    /** 奖金应扣税额合计 */
    @ApiModelProperty(value = "奖金应扣税额合计", position = 17)
    private BigDecimal jjTaxTotal;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 18)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 19)
    private String memo;

    /** SN */
    @ApiModelProperty(value = "SN", position = 27)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 28)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 29)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 30)
    private String status;

}
