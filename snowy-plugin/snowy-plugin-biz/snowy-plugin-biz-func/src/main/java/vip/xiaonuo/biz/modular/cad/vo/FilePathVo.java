package vip.xiaonuo.biz.modular.cad.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 根据文件Id,获取自定义文件路径
 */

@Getter
@Setter
public class FilePathVo {

    /** 路径 - 自定义文件夹路径 */
    @ApiModelProperty(value = " 路径 - 文件夹路径 ", position = 1)
    private String filePathDir;

    /** 路径 - 文件名 */
    @ApiModelProperty(value = " 路径 - 文件名 ", position = 2)
    private String fileName;
}
