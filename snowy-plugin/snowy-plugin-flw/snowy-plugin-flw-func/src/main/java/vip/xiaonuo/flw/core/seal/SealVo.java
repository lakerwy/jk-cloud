package vip.xiaonuo.flw.core.seal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 群杰获取授权返回实体
 * @author hxw
 * @date 2024/5/23 9:00
 */
@Setter
@Getter
public class SealVo {

    /** 印章id **/
    @ApiModelProperty(value = "印章id")
    private String sealId;

    /** 授权码 **/
    @ApiModelProperty(value = "授权码")
    private String safeCode;

    /** 印章次数 **/
    @ApiModelProperty(value = "印章次数")
    private String applyCount;
}
