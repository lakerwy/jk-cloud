package vip.xiaonuo.flw.core.sealApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 云玺获取授权返回实体
 * @author hxw
 * @date 2024/5/27 10:00
 */
@Setter
@Getter
public class SealApplyVo {

    /** 申请单id **/
    @ApiModelProperty(value = "申请单id")
    private String applicationId;

    /** 授权码 **/
    @ApiModelProperty(value = "授权码")
    private String authorizationCode;

    /** 过期时间 **/
    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    /** 设备uuid **/
    @ApiModelProperty(value = "设备uuid")
    private String uuid;
}
