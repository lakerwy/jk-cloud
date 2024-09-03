package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司评级编辑参数
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
@Setter
public class BranchRatingEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 区域 */
    @ApiModelProperty(value = "区域", position = 3)
    private String area;

    /** 分公司名称/经营范围 */
    @ApiModelProperty(value = "分公司名称/经营范围", position = 4)
    private String name;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 5)
    private String areaManager;

    /** 年份(如：2024) */
    @ApiModelProperty(value = "年份(如：2024)", position = 6)
    private String year;

    /** 季度(上半年、下半年) */
    @ApiModelProperty(value = "季度(上半年、下半年)", position = 7)
    private String quarter;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 8)
    private String totalScore;

    /** 级别(A、B、C、D、-) */
    @ApiModelProperty(value = "级别(A、B、C、D、-)", position = 9)
    private String level;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

}
