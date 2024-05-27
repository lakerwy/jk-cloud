package vip.xiaonuo.dev.modular.sealApply.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取云玺所有的印章信息返回实体
 * @Author hxw
 * @Date 2024/5/27 9:24
 */
@Getter
@Setter
public class UserSealApplyVo {

    /** 印章id */
    @ApiModelProperty(value = "设备标识")
    private String uuid;

    /** 用印部门 */
    @ApiModelProperty(value = "设备名称")
    private String name;
}
