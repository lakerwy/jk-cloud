package vip.xiaonuo.flw.core.seal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *@Author hxw
 *@Date 2024/5/23 9:13
 */
@Setter
@Getter
public class UsedSealParam {

    /** 实际用印人账号 **/
    @ApiModelProperty(value = "实际用印人账号")
    private String sealerUserName;

    /** 印章id **/
    @ApiModelProperty(value = "印章id")
    private String sealId;

    /** 用印数量 **/
    @ApiModelProperty(value = "用印数量")
    private String applyCount;

}
