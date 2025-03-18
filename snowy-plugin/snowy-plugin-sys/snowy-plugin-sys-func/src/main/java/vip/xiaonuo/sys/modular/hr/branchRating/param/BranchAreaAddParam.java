package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司评级（二期）- 区域经济修正系数 添加参数
 *
 * @author yong.ye
 * @date  2025/03/18 08:47
 **/
@Getter
@Setter
public class BranchAreaAddParam {

    /** 分公司id */
    @ApiModelProperty(value = "分公司id", position = 2)
    private String branchId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String branchName;

    /** 分公司所在城市 */
    @ApiModelProperty(value = "分公司所在城市", position = 4)
    private String branchCity;

    /** 分公司上一年度所在市GDP（万亿元） */
    @ApiModelProperty(value = "分公司上一年度所在市GDP（万亿元）", position = 5)
    private BigDecimal branchCityGdp;

    /** 武汉市上一季度市GDP（万亿元） */
    @ApiModelProperty(value = "武汉市上一季度市GDP（万亿元）", position = 6)
    private BigDecimal wuhanCityGdp;

    /** 直接系数 */
    @ApiModelProperty(value = "直接系数", position = 7)
    private BigDecimal directRate;

    /** 调整系数 */
    @ApiModelProperty(value = "调整系数", position = 8)
    private BigDecimal adjustRate;

    /** 区域经济修正系数 */
    @ApiModelProperty(value = "区域经济修正系数", position = 9)
    private BigDecimal areaRate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    /** 导入 - 校验错误信息 */
    @ApiModelProperty(value = "导入 - 校验错误信息", position = 100)
    private String errorMsg;

}
