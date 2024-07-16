package vip.xiaonuo.oa.modular.attendance.group.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.modular.attendance.group.entity.Group;
import vip.xiaonuo.oa.modular.attendance.rule.entity.AdjustmentRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWorkRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/12 15:01
 */
@Setter
@Getter
public class GroupDetailVo extends Group {

    @ApiModelProperty(value = "补卡规则类", position = 100)
    private Rule<AdjustmentRule> adjustment;

    @ApiModelProperty(value = "加班规则类", position = 101)
    private Rule<OverWorkRule> overWork;
}
