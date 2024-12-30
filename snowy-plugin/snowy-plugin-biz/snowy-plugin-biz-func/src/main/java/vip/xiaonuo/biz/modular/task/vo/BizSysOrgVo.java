package vip.xiaonuo.biz.modular.task.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/7 16:26
 */

@Getter
@Setter
public class BizSysOrgVo {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 21)
    private String orgName;

    /** 主管id */
    @ApiModelProperty(value = "主管id", position = 30)
    private String directorId;

}
