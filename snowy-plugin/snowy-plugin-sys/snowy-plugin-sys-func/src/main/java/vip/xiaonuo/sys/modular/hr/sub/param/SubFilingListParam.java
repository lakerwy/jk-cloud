package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分包单位备案查询参数
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Getter
@Setter
public class SubFilingListParam {

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

//    /** 用印部门 */
//    @ApiModelProperty(value = "用印部门")
//    private String sealDept;

    /** 用印部门ID */
    @ApiModelProperty(value = "用印部门ID")
    private String sealDeptId;

}
