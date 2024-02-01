package vip.xiaonuo.sys.modular.person.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ActionParam {

    /** 问题|建议 - 主键ID */
    @ApiModelProperty(value = "问题|建议 - 主键ID", required = true, position = 1)
    @NotBlank(message = "问题|建议 - 主键ID不能为空")
    private String surveyId;

    /** 评价|回复 内容 */
    @ApiModelProperty(value = "评价|回复", position = 3)
    private String content;

    /** 操作类型：回复 1 |关闭 2 |删除 3 */
    @ApiModelProperty(value = "操作类型：回复 1 |关闭 2 |删除 3", position = 2)
    private String actionType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attach;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;
}
