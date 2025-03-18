package vip.xiaonuo.sys.modular.hr.branchRating.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchArea;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class BranchAreaVo {


    /** X轴信息 */
    @ApiModelProperty(value = "X轴信息")
    private List<String> xAxis ;

    /** 分公司上一年度所在市GDP（万亿元） */
    @ApiModelProperty(value = "分公司上一年度所在市GDP（万亿元）")
    private List<BigDecimal> branchCityGdp ;

    /** 区域经济修正系数 */
    @ApiModelProperty(value = "区域经济修正系数")
    private List<BigDecimal> areaRate ;

    /** 区域经济修正系数列表 */
    @ApiModelProperty(value = "区域经济修正系数列表")
    List<BranchArea> branchAreaList;

}
