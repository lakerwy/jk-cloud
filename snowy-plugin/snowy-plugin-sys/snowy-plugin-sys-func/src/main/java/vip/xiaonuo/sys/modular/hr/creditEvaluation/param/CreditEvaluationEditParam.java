package vip.xiaonuo.sys.modular.hr.creditEvaluation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 信用评价编辑参数
 *
 * @author pzh
 * @date  2024/10/18 10:49
 **/
@Getter
@Setter
public class CreditEvaluationEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 评价城市 */
    @ApiModelProperty(value = "评价城市", position = 2)
    private String city;

    /** 信息来源 */
    @ApiModelProperty(value = "信息来源", position = 3)
    private String source;

    /** 查询账号 */
    @ApiModelProperty(value = "查询账号", position = 4)
    private String account;

    /** 查询密码 */
    @ApiModelProperty(value = "查询密码", position = 4)
    private String password;

    /** 信用分 */
    @ApiModelProperty(value = "信用分", position = 5)
    private String creditScore;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 6)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

}
