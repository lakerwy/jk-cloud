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
public class SealDevice {

    /** 页码 **/
    @ApiModelProperty(value = "页码")
    private int pageNum = 1;

    /** 每页条数 **/
    @ApiModelProperty(value = "每页条数")
    private int pageSize = 2000;

}
