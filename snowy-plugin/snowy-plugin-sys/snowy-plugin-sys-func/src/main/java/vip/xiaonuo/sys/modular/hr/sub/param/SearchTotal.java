package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SearchTotal {

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 分公司Id */
    @ApiModelProperty(value = "分公司Id")
    private String orgId;

    /** 分公司 */
    @ApiModelProperty(value = "分公司")
    private String orgName;

    /** 时间 */
    @ApiModelProperty(value = "时间",required = true)
    @NotBlank(message = "时间必填")
    private String yM;

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区")
    private String region;
}
