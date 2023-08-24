package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/24 9:51
 */

@Getter
@Setter
public class RecruitPlanPageDto {
    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 编号 */
    @ApiModelProperty(value = "编号")
    private String sn;

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String orgId;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private String status;

    /** 年度 */
    @ApiModelProperty(value = "年度")
    private String year;
}
