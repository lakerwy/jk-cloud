package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司-五险一金 - 社保|公积金明细编辑参数
 *
 * @author yong.ye
 * @date  2025/02/06 09:13
 **/
@Getter
@Setter
public class SubFundItemEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 五险一金流程主键id */
    @ApiModelProperty(value = "五险一金流程主键id", position = 2)
    private String subFundId;

    /** 明细类别：社保|公积金 */
    @ApiModelProperty(value = "明细类别：社保|公积金", position = 3)
    private String type;

    /** 所属月份 */
    @ApiModelProperty(value = "所属月份", position = 4)
    private String yM;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 6)
    private String idCardNumber;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 7)
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 8)
    private String duty;

    /** 用工性质 */
    @ApiModelProperty(value = "用工性质", position = 9)
    private String nature;

    /** 应缴金额（单位部分） */
    @ApiModelProperty(value = "应缴金额（单位部分）", position = 10)
    private BigDecimal yjUnit;

    /** 应缴金额（个人部分） */
    @ApiModelProperty(value = "应缴金额（个人部分）", position = 11)
    private BigDecimal yjPer;

    /** 应缴金额（合计） */
    @ApiModelProperty(value = "应缴金额（合计）", position = 12)
    private BigDecimal yjTotal;

    /** 实缴金额（单位部分） */
    @ApiModelProperty(value = "实缴金额（单位部分）", position = 13)
    private BigDecimal sjUnit;

    /** 实缴金额（个人部分） */
    @ApiModelProperty(value = "实缴金额（个人部分）", position = 14)
    private BigDecimal sjPer;

    /** 实缴金额（合计） */
    @ApiModelProperty(value = "实缴金额（合计）", position = 15)
    private BigDecimal sjTotal;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 16)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 17)
    private String attach;




    /** 校验结果 - 错误信息 */
    @ApiModelProperty(value = "校验结果 - 错误信息", position = 100)
    private String errMsg;
}
