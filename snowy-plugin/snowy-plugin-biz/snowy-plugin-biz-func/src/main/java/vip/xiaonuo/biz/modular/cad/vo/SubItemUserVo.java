package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.cad.entity.CadSubItemUser;

/**
 * 子项人员关系设置
 */

@Getter
@Setter
public class SubItemUserVo extends CadSubItemUser {

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 20)
    private String userName;

    /** 专业排序 */
    @ApiModelProperty(value = "专业排序", position = 20)
    private Integer professionIndex;

    /** 专业名称 */
    @ApiModelProperty(value = "专业名称", position = 20)
    private String professionName;

}
