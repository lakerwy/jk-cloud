package vip.xiaonuo.pan.modular.share.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/11 8:48
 */

@Getter
@Setter
public class ShareExtractionCodeDto {

    /** 提取码 */
    @ApiModelProperty(value = "提取码", position = 2)
    private String extractionCode;

    /** 分享批次号 */
    @ApiModelProperty(value = "分享批次号", position = 1)
    private String shareBatchNum;
}
