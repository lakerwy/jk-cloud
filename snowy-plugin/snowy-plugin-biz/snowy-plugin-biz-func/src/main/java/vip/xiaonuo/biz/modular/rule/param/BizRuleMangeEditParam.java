package vip.xiaonuo.biz.modular.rule.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BizRuleMangeEditParam {
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;
    @ApiModelProperty(value = "标题", position = 2)
    private String title;
    @ApiModelProperty(value = "时间", position = 3)
    private String getDate;
    @ApiModelProperty(value = "备注", position = 4)
    private String memo;
    @ApiModelProperty(value = "附件", position = 5)
    private String attachment;
    @ApiModelProperty(value = "负责人", position = 6)
    private String manager;

}
