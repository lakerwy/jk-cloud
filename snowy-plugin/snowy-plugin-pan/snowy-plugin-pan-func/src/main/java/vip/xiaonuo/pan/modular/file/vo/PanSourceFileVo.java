package vip.xiaonuo.pan.modular.file.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PanSourceFileVo {

    /** 文件名称 */
    @ApiModelProperty(value = "文件名称", position = 1)
    private String fileName;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 2)
    private String filePath;

    /** 文件ID */
    @ApiModelProperty(value = "文件ID", position = 3)
    private String fileId;

    public PanSourceFileVo(){}

    public PanSourceFileVo(String fileName,String fileId){
        this.fileName = fileName;
        this.fileId = fileId;
    }

}
