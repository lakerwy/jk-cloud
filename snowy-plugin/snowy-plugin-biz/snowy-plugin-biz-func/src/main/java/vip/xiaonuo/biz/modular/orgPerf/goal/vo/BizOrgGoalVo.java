package vip.xiaonuo.biz.modular.orgPerf.goal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoalItem;

import java.util.List;

/**
 * 组织绩效考核-详情vo
 *
 * @author pzh
 * @date  2025/06/24 17:15
 **/
@Getter
@Setter
public class BizOrgGoalVo {

    /** 基本项指标 */
    @ApiModelProperty(value = "基本项指标-集合", position = 15)
    private List<BizOrgGoalItem> itemList;

}
