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

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 4)
    private String orgId;

    @ApiModelProperty(value = "机构名称", position = 4)
    private String orgName;

    /** 本年、本季度、本组织 参与考核人数总和  */
    @ApiModelProperty(value = "本年、本季度、本组织 参与考核人数总和 ", position = 4)
    private Integer empAssNums;

    /** 考核等级 A B S  */
    @ApiModelProperty(value = "考核等级 A B S ", position = 4)
    private String empSeasonLevel;

    /** 考核等级 - 人数  */
    @ApiModelProperty(value = "考核等级 A B S ", position = 4)
    private Integer empSeasonLevelNums;

}
