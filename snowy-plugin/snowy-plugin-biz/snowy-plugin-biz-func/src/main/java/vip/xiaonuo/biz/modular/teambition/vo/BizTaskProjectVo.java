package vip.xiaonuo.biz.modular.teambition.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizTaskProjectVo {

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称")
    private String proName;

    /** 项目简介 */
    @ApiModelProperty(value = "项目简介")
    private String proDesc;

    /** 项目源主键ID */
    @ApiModelProperty(value = "项目源主键ID")
    private String proSourceId;

    /** 第三方任务来源流水号 */
    @ApiModelProperty(value = "第三方任务来源流水号")
    private String sn;
}
