package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/26 14:39
 */


/**
 * 更新季度评分等级
 */
@Getter
@Setter
public class EmpAssUpdateParam {

    /** 绩效评分 - 主键 - Id  (来源：BIZ_EMP_PERFORMANCE.Id)*/
    @ApiModelProperty(value = "绩效评分 -主键 - Id (来源：BIZ_EMP_PERFORMANCE.Id)", position = 6)
    private String epId;

    /** 更新字段名 (评分等级：empSeasonLevel) */
    @ApiModelProperty(value = "更新字段名 (评分等级：empSeasonLevel)", position = 6)
    private String colName;

    /** 更新字段值 */
    @ApiModelProperty(value = "更新字段值", position = 6)
    private String colValue;

}
