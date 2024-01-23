package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/7 14:48
 */
@Getter
@Setter
public class UploadFileParam {

    @ApiModelProperty(value = "文件路径",required = true)
    private String filePath;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "切片数量")
    private int chunkNumber;

    @ApiModelProperty(value = "切片大小")
    private long chunkSize;
    @ApiModelProperty(value = "相对路径")
    private String relativePath;

    @ApiModelProperty(value = "所有切片")
    private int totalChunks;

    @ApiModelProperty(value = "总大小")
    private long totalSize;

    @ApiModelProperty(value = "当前切片大小")
    private long currentChunkSize;

    @ApiModelProperty(value = "md5码")
    private String identifier;

    @ApiModelProperty(value = "父目录id")
    private String parentId;

    @ApiModelProperty(value = "0个人文件夹内上传 1 群组内文件上传")
    private int type = 0;

    @ApiModelProperty(value = "权限校验类型：GROUP 群组  COMMON 共享 注：（不传或不在指定范围） 不做权限校验")
    private String rightType;

    @ApiModelProperty(value = "0个人文件夹内上传 1 群组内文件上传")
    private Integer foldType;

    @ApiModelProperty(value = "是否来自业务上传")
    private Boolean formService = Boolean.FALSE;

}
