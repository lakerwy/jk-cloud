package vip.xiaonuo.biz.modular.rule.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizRuleMangeAddParam {
    @ApiModelProperty(value = "标题", position = 1)
    private String title;
    @ApiModelProperty(value = "时间", position = 2)
    private String getDate;
    @ApiModelProperty(value = "备注", position = 3)
    private String memo;
    @ApiModelProperty(value = "附件", position = 4)
    private String attachment;
    @ApiModelProperty(value = "负责人", position = 5)
    private String manager;
}
