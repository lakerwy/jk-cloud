package vip.xiaonuo.pan.modular.file.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/7 14:29
 */
@Setter
@Getter
public class UploadFile {

    //切片上传相关参数
    @ApiModelProperty(value = "第几个切片", position = 1)
    private int chunkNumber;

    @ApiModelProperty(value = "切片大小", position = 2)
    private long chunkSize;

    @ApiModelProperty(value = "切片数量", position = 3)
    private int totalChunks;

    @ApiModelProperty(value = "md5唯一标识", position = 4)
    private String identifier;

    @ApiModelProperty(value = "文件总大小", position = 5)
    private long totalSize;

    @ApiModelProperty(value = "当前切片大小", position = 6)
    private long currentChunkSize;
}
