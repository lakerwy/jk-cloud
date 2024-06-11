package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 生成工资表-实体
 */
@Data
public class GzExcelInfo {

    /** 返回模式：文件流 stream |文件 file */
    @ApiModelProperty(value = "返回模式：文件流|文件", position = 2)
    private String type;

    /** 文件名 带后缀 */
    @ApiModelProperty(value = "文件名 带后缀 ", position = 2)
    private String fileName;

    /** 生成的临时文件路径 | 生成的文件流 */
    @ApiModelProperty(value = "生成的临时文件路径 | 生成的文件流", position = 3)
    private Object fileObj;

    public GzExcelInfo(String type,String fileName, Object fileObj) {
        this.type = type;
        this.fileName = fileName;
        this.fileObj = fileObj;
    }
}
