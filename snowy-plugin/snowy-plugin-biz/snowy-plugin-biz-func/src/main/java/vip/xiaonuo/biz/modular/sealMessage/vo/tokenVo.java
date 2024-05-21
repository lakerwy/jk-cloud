package vip.xiaonuo.biz.modular.sealMessage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取token返回参数
 * @Author hxw
 * @Date 2024/5/17 9:50
 */
@Getter
@Setter
public class tokenVo {

    /** 时间戳 */
    @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    /** 返回状态 0：成功；非0：失败 */
    @ApiModelProperty(value = "返回状态 0：成功；非0：失败")
    private int status;

    /** 返回信息 */
    @ApiModelProperty(value = "返回信息")
    private String message;

    /** 返回数据 */
    @ApiModelProperty(value = "返回数据")
    private Object data;

    /** 服务端生成的一串字符串（令牌） */
    @ApiModelProperty(value = "服务端生成的一串字符串（令牌）")
    private String token;
}
