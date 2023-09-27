package vip.xiaonuo.pan.modular.share.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/11 8:37
 */

@Getter
@Setter
public class ShareParam {

    /** 分享批次号 */
    @ApiModelProperty(value = "分享批次号", position = 1)
    private String shareBatchNum;

}
