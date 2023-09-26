package vip.xiaonuo.pan.modular.file.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/6 11:33
 */
@Setter
@Getter
public class FileCheckVo {

    @ApiModelProperty(value = "检查结果标识值", position = 1,example = "0、未上传过（新上传） 1、部分已上传（断点续传） 2、已上传（秒传）")
    private Integer checkStatus;

    @ApiModelProperty(value = "未上传的分块部分", position = 2)
    private List<String>  missChunkList;

    @ApiModelProperty(value = "相同文件的文件地址", position = 2)
    private String pathValue;

    public FileCheckVo(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public FileCheckVo(Integer checkStatus, List<String> missChunkList) {
        this.checkStatus = checkStatus;
        this.missChunkList = missChunkList;
    }

    public FileCheckVo(Integer checkStatus, String pathValue) {
        this.checkStatus = checkStatus;
        this.pathValue = pathValue;
    }
}
