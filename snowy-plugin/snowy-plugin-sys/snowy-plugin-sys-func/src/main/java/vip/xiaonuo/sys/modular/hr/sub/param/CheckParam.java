package vip.xiaonuo.sys.modular.hr.sub.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckParam {
    /** 分公司组织Id */
    @ApiModelProperty(value = "分公司组织Id", position = 2)
    private String orgId;

    /** 工资发放年度 */
    @ApiModelProperty(value = "工资发放年度", position = 4)
    private String year;

    /** 工资发放月 */
    @ApiModelProperty(value = "工资发放月", position = 5)
    private String month;

}
