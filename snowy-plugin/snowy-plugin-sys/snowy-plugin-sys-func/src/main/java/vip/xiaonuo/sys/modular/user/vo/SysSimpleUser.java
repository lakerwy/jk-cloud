package vip.xiaonuo.sys.modular.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/10 14:35
 */
@Setter
@Getter
public class SysSimpleUser{

    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 7)
    private String name;
}
