package vip.xiaonuo.biz.modular.recruit.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.recruit.entity.BizRecruitPlan;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/24 9:23
 */

@Getter
@Setter
public class BizRecruitPlanVo {
    /** 年度 */
    @ApiModelProperty(value = "年度", position = 1)
    private String year;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 2)
    private String orgName;

    /** 部门id */
    @ApiModelProperty(value = "部门id", position = 3)
    private String orgId;

    /** 计划编制（最新） */
    @ApiModelProperty(value = "计划编制（最新）", position = 4)
    private Integer newPlanNums;

    /** 实际编制（最新） */
    @ApiModelProperty(value = "实际编制（最新）", position = 5)
    private Integer newRealNums;

    /** 部门招聘计划集合 */
    @ApiModelProperty(value = "部门招聘计划集合", position = 6)
    private List<BizRecruitPlanDetailVo> planList;

}
