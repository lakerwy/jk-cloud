package vip.xiaonuo.biz.modular.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/26 11:07
 */
@Getter
@Setter
public class EmpAssInfo {

    /** 考核对象-用户Id */
    @ApiModelProperty(value = "考核对象-用户Id", position = 8)
    private String userId;

    /** 考核对象-用户名 */
    @ApiModelProperty(value = "考核对象-用户名", position = 9)
    private String userName;

    /** 考核对象-组织Id */
    @ApiModelProperty(value = "考核对象-组织Id", position = 10)
    private String orgId;

    /** 考核对象-组织名称 */
    @ApiModelProperty(value = "考核对象-组织名称", position = 11)
    private String orgName;

    /**一级部门id*/
    @ApiModelProperty(value = "一级部门Id", position = 15)
    private String firstOrgId;

    /**一级部门名称*/
    @ApiModelProperty(value = "一级部门名称", position = 16)
    private String firstOrgName;

}
