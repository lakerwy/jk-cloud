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

    /** 证书类别：注册类别|培训类别 */
    @ApiModelProperty(value = "证书类别：注册类别|培训类别")
    private String certType;

    /** 级别 */
    @ApiModelProperty(value = "级别")
    private String level;

    /** 专业123 */
    @ApiModelProperty(value = "专业123")
    private String major;
}
