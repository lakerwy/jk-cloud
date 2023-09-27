package vip.xiaonuo.biz.modular.emp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/26 16:16
 */

@Getter
@Setter
public class BizEmpAssVo {

    /** 考核等级 A B S  */
    @ApiModelProperty(value = "考核等级 A B S ", position = 4)
    private String empSeasonLevel;

    /** 考核等级 - 人数  */
    @ApiModelProperty(value = "考核等级 A B S ", position = 4)
    private Integer empSeasonLevelNums;

}
