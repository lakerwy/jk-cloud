package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/4 17:39
 */
@Setter
@Getter
public class MultipartFileParam {

    @ApiModelProperty(value = "任务ID", position = 1)
    private String id;

    @ApiModelProperty(value = "总分片数量", position = 2)
    private int chunks;

    @ApiModelProperty(value = "当前为第几块分片", position = 3)
    private int chunk;

    @ApiModelProperty(value = "当前分片大小", position = 4)
    private long size = 0L;

    @ApiModelProperty(value = "文件名", position = 5)
    private String name;

    @ApiModelProperty(value = "分片对象", position = 6)
    private MultipartFile file;

    @ApiModelProperty(value = "文件的MD5", position = 7)
    private String md5;

    @ApiModelProperty(value = "分片对象列表", position = 6)
    private MultipartFile[] files;

    @ApiModelProperty(value = "文件上传的目录id", position = 8)
    private String parentId;
}
