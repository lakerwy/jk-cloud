package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 费用报销 - 财务收单编码库添加参数
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 **/
@Getter
@Setter
public class BizFinCodeAddParam {

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 2)
    private String company;

    /** 公司码 */
    @ApiModelProperty(value = "公司码", position = 3)
    private String codePre;

    /** 年 */
    @ApiModelProperty(value = "年", position = 4)
    private String codeYear;

    /** 月 */
    @ApiModelProperty(value = "月", position = 5)
    private String codeMonth;

    /** 单据顺序号 */
    @ApiModelProperty(value = "单据顺序号", position = 6)
    private String codeNo;

    /** 最终形成的编码 */
    @ApiModelProperty(value = "最终形成的编码", position = 7)
    private String code;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 15)
    private String extJson;

}
