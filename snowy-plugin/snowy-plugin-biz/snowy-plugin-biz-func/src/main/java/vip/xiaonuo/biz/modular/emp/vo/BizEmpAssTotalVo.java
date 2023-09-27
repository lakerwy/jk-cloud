package vip.xiaonuo.biz.modular.emp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/26 17:01
 */

@Getter
@Setter
public class BizEmpAssTotalVo {

    /** 年度(如：2023) */
    @ApiModelProperty(value = "年度(如：2023)", position = 5)
    private String year;

    /** 季度(如:1) */
    @ApiModelProperty(value = "季度(如:1)", position = 6)
    private String season;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 4)
    private String orgId;

    @ApiModelProperty(value = "机构名称", position = 4)
    private String orgName;

    /** 本年、本季度、本组织 参与考核人数总和  */
    @ApiModelProperty(value = "本年、本季度、本组织 参与考核人数总和 ", position = 4)
    private Integer empAssNums;

    /** 本年、本季度、本组织 各等级 人数  */
    @ApiModelProperty(value = "本年、本季度、本组织 各等级 人数  ", position = 4)
    private List<BizEmpAssVo> empAssVoList;
}
