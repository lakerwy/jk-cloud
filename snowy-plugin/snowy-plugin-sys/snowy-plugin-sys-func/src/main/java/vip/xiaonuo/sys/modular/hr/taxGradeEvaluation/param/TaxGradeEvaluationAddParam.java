package vip.xiaonuo.sys.modular.hr.taxGradeEvaluation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 纳税等级评价添加参数
 *
 * @author pzh
 * @date  2024/11/27 14:45
 **/
@Getter
@Setter
public class TaxGradeEvaluationAddParam {

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 纳税人名称 */
    @ApiModelProperty(value = "纳税人名称", position = 3)
    private String taxpayerName;

    /** 统一社会信用码（纳税人识别号） */
    @ApiModelProperty(value = "统一社会信用码（纳税人识别号）", position = 4)
    private String unifiedSocialCreditCode;

    /** 纳税信用评价得分 */
    @ApiModelProperty(value = "纳税信用评价得分", position = 5)
    private String taxCreditScore;

    /** 年度评价结果 */
    @ApiModelProperty(value = "年度评价结果", position = 6)
    private String annualEvaluationResult;

    /** 出具时间 */
    @ApiModelProperty(value = "出具时间", position = 7)
    private Date issueDate;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 8)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

}
