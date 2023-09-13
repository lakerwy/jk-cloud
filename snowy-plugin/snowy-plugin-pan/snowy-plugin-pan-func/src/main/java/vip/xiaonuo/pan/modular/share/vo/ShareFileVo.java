package vip.xiaonuo.pan.modular.share.vo;

import lombok.Getter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/7 17:09
 */

@Getter
@Setter
public class ShareFileVo {

    /** 提取码 */
    @ApiModelProperty(value = "提取码", position = 6)
    private String extractionCode;

    /** 分享批次号 */
    @ApiModelProperty(value = "分享批次号", position = 7)
    private String shareBatchNum;
}
