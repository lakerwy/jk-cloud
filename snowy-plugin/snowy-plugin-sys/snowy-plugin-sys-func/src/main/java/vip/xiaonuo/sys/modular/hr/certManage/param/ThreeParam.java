package vip.xiaonuo.sys.modular.hr.certManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/***
 * 证书类别|等级|专业 三级联动查询数据
 */

@Getter
@Setter
public class ThreeParam {

    /** 证书类别：注册类别|培训类别 -- 字典Id*/
    @ApiModelProperty(value = "证书类别：注册类别|培训类别 - 字典id")
    private String certTypeId;

    /** 级别 - 字典id*/
    @ApiModelProperty(value = "级别- 字典id")
    private String levelId;

    /** 专业123 */
    @ApiModelProperty(value = "专业123- 字典id")
    private String majorId;
}
