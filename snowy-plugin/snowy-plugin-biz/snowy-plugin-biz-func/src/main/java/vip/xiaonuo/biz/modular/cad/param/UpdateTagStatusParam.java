package vip.xiaonuo.biz.modular.cad.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 更新批注状态
 */
@Getter
@Setter
public class UpdateTagStatusParam {

    /** CadFile.Id 设计区文件目录Id主键 */
    @ApiModelProperty(value = "CadFile.Id 设计区文件目录Id主键", position = 1)
    private String fileId;

    /** 批注状态 -1 : 删除 0 :未修改  1：已修改 */
    @ApiModelProperty(value = "批注状态", position = 2)
    private String status;
}
