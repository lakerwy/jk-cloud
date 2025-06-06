package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResult {

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 1)
    private String contractNo;

    /** 开票单位 */
    @ApiModelProperty(value = "开票单位", position = 2)
    private String unitName;


    /** 错误描述 */
    @ApiModelProperty(value = "错误描述", position = 3)
    private String errorMsg;
}
