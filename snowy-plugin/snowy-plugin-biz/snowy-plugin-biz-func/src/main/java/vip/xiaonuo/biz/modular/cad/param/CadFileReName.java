package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义文件重命名
 */
@Getter
@Setter
public class CadFileReName {

    /** 文件|文件夹|批注 - 主键ID */
    @ApiModelProperty(value = "文件|文件夹|批注 - 主键ID", position = 1)
    private String fId;

    /** 文件|文件夹|批注 - 文件名称 */
    @ApiModelProperty(value = "文件|文件夹|批注 - 文件名称", position = 1)
    private String newName;

}
