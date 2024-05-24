package vip.xiaonuo.flw.core.seal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 群杰获取授权实体
 * @author hxw
 * @date 2024/5/23 9:00
 */
@Setter
@Getter
public class SealParam {

    /** 标题 **/
    @ApiModelProperty(value = "标题")
    private String title;

    /** 申请人账号 **/
    @ApiModelProperty(value = "申请人账号")
    private String applyerUserName;

    /** 回调url路径 **/
    @ApiModelProperty(value = "回调url路径")
    private String callBackUrl;

    /** 申请明细信息 **/
    @ApiModelProperty(value = "申请明细信息")
    private List usedSealParamList;
}
