package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.cad.entity.CadProofread;

@Getter
@Setter
public class CadProofreadVo extends CadProofread {

    /** 项目 - 名称 */
    @ApiModelProperty(value = " 项目 - 名称 ", position = 20)
    private String prjName;

    /** 阶段 - 名称 */
    @ApiModelProperty(value = " 阶段 - 名称 ", position = 20)
    private String stageName;

    /** 子项 - 名称 */
    @ApiModelProperty(value = " 子项 - 名称 ", position = 20)
    private String subItemName;

    /** 专业 - 名称 */
    @ApiModelProperty(value = "专业 - 名称", position = 20)
    private String professionName;

    /** 提交人 */
    @ApiModelProperty(value = "提交人", position = 20)
    private String createUserName;

    /** 文件名 */
    @ApiModelProperty(value = "文件名", position = 20)
    private String fileName;

    /** 源目录Id(比如：对文件进行批注，这就是原Id) */
    @ApiModelProperty(value = "源目录Id(比如：对文件进行批注，这就是原Id)", position = 20)
    private String oldFId;
}
