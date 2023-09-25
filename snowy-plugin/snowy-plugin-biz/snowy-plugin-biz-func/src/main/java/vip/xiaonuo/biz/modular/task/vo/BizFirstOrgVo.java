package vip.xiaonuo.biz.modular.task.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/25 14:01
 *
 * 组织架构 一级部门
 */

@Getter
@Setter
public class BizFirstOrgVo {

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 21)
    private String orgName;

    /** 父id */
    @ApiModelProperty(value = "父id", position = 3)
    private String parentId;

    /** 主管id */
    @ApiModelProperty(value = "主管id", position = 4)
    private String directorId;
}
