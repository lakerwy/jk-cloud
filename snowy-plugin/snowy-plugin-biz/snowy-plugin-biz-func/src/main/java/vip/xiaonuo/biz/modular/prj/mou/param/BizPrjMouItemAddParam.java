package vip.xiaonuo.biz.modular.prj.mou.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目承接备忘录 - 项目生产内部分工添加参数
 *
 * @author yy
 * @date  2025/06/06 11:43
 **/
@Getter
@Setter
public class BizPrjMouItemAddParam {

    /** 项目备忘录Id */
    @ApiModelProperty(value = "项目备忘录Id", position = 2)
    private String bizPrjMouId;

    /** 承包类型 */
    @ApiModelProperty(value = "承包类型", position = 3)
    private String mouType;

    /** 经办部门Id */
    @ApiModelProperty(value = "经办部门Id", position = 4)
    private String deptId;

    /** 经办部门 */
    @ApiModelProperty(value = "经办部门", position = 5)
    private String deptName;

    /** 工作内容 */
    @ApiModelProperty(value = "工作内容", position = 6)
    private String content;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String attachs;
}
