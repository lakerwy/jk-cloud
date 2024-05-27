package vip.xiaonuo.dev.modular.sealApply.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 云玺申请单用印请求基础参数
 * @author hxw
 * @date 2024/5/23 9:00
 */
@Setter
@Getter
public class SealApply {

    /** 应用标识 **/
    @ApiModelProperty(value = "应用标识")
    private String appKey;

    /** 接口标识 **/
    @ApiModelProperty(value = "接口标识")
    private String apiKey;

    /** 时间戳 **/
    @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    /** 业务标识 **/
    @ApiModelProperty(value = "业务标识")
    private String bizId;

    /** 租户标识 **/
    @ApiModelProperty(value = "租户标识")
    private String tenant;

    /** 云玺申请单用印请求参数实体 **/
    @ApiModelProperty(value = "云玺申请单用印请求参数实体")
    private String params;
}
