package vip.xiaonuo.biz.modular.recruit.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author yong.ye
 * @date 2023/8/24 11:30
 */

@Getter
@Setter
public class RecruitPlanGroupDto {

    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String orgId;

    /** 年度 */
    @ApiModelProperty(value = "年度")
    private String year;

    public RecruitPlanGroupDto(String year, String orgId) {
        this.orgId = orgId;
        this.year = year;
    }
}
