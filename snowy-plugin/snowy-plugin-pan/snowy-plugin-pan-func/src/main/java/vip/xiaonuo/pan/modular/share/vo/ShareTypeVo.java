package vip.xiaonuo.pan.modular.share.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/11 8:35
 */

@Getter
@Setter
public class ShareTypeVo {

    /** 分享类型(0公共,1私密,2好友) */
    @ApiModelProperty(value = "分享类型(0公共,1私密,2好友)", position = 1)
    private Integer shareType;
}
