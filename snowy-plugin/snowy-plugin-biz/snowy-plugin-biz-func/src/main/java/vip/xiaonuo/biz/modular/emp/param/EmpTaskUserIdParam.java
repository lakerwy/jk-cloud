package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/19 14:37
 */

@Getter
@Setter
public class EmpTaskUserIdParam {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 1)
    private String userId;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 3)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 4)
    private String season;

    /** 状态 - 默认（审核通过） */
    @ApiModelProperty(value = "状态 - 默认（审核通过）", position = 5)
    private String status;

}
