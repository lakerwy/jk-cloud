package vip.xiaonuo.pan.modular.file.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/7 14:44
 */
@Data
public class UploadFileVo {

    @ApiModelProperty(value = "时间戳", example = "123123123123")
    private String timeStampName;

    @ApiModelProperty(value = "跳过上传", example = "true")
    private boolean skipUpload;

    @ApiModelProperty(value = "是否需要合并分片", example = "true")
    private boolean needMerge;

    @ApiModelProperty(value = "已经上传的分片", example = "[1,2,3]")
    private List<Integer> uploaded;
}
