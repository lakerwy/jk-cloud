package vip.xiaonuo.sys.modular.hr.branchRating.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchRating;

import java.util.List;

/**
 * 分公司评级Vo
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
@Setter
public class BranchRatingVo {

    /** 区域 */
    @ApiModelProperty(value = "区域", position = 1)
    private String area;

    /** 区域 */
    @ApiModelProperty(value = "区域分公司数总计", position = 2)
    private Long areaTotal;

    /** 分公司评级列表 */
    @ApiModelProperty(value = "分公司评级列表", position = 3)
    private List<BranchRating> branchRatingList;

}
