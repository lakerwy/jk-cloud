package vip.xiaonuo.dev.modular.sealApply.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 授权请求参数实体
 * @Author hxw
 * @Date 2024/5/27 9:08
 */

@Setter
@Getter
public class SealImpower {

    /** 申请单id **/
    @ApiModelProperty(value = "申请单id")
    private String applicationId;

    /** 设备标识 **/
    @ApiModelProperty(value = "设备标识")
    private String uuid;

    /** 过期时间 **/
    @ApiModelProperty(value = "过期时间")
    private Long expireTime;
}
