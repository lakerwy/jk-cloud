package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司-五险一金（流程）编辑参数
 *
 * @author yong.ye
 * @date  2025/02/06 09:12
 **/
@Getter
@Setter
public class SubFundEditParam {

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

    /** 应缴五险人数 */
    @ApiModelProperty(value = "应缴五险人数", position = 8)
    private Integer yjWxNum;

    /** 实缴五险人数 */
    @ApiModelProperty(value = "实缴五险人数", position = 9)
    private Integer sjWxNum;

    /** 应缴五险总额 */
    @ApiModelProperty(value = "应缴五险总额", position = 10)
    private BigDecimal yjWxTotal;

    /** 实缴五险总额 */
    @ApiModelProperty(value = "实缴五险总额", position = 11)
    private BigDecimal sjWxTotal;

    /** 应缴公积金人数 */
    @ApiModelProperty(value = "应缴公积金人数", position = 12)
    private Integer yjGjjNum;

    /** 实缴公积金人数 */
    @ApiModelProperty(value = "实缴公积金人数", position = 13)
    private Integer sjGjjNum;

    /** 应缴公积金总额 */
    @ApiModelProperty(value = "应缴公积金总额", position = 14)
    private BigDecimal yjGjjTotal;

    /** 实缴公积金总额 */
    @ApiModelProperty(value = "实缴公积金总额", position = 15)
    private BigDecimal sjGjjTotal;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 16)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 17)
    private String memo;

    /** SN */
    @ApiModelProperty(value = "SN", position = 25)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 26)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 27)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 28)
    private String status;

    /** 社保附件 */
    @ApiModelProperty(value = "社保附件", position = 31)
    private String insAttach;

    /** 公积金附件 */
    @ApiModelProperty(value = "公积金附件", position = 32)
    private String gjjAttach;
}
