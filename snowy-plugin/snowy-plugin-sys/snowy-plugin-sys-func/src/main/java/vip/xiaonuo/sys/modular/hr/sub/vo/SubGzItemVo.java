package vip.xiaonuo.sys.modular.hr.sub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGzItem;

@Getter
@Setter
public class SubGzItemVo extends SubGzItem {
    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 100)
    private String region;

    /** 部门Id - 分公司Id */
    @ApiModelProperty(value = "部门Id - 分公司Id", position = 200)
    private String orgId;

    /** 部门 - 分公司 */
    @ApiModelProperty(value = "部门 - 分公司", position = 300)
    private String orgName;

    /** 流程记录 - 所属年月 */
    @ApiModelProperty(value = "流程记录 - 所属年月", position = 400)
    private String flwYM;
}
