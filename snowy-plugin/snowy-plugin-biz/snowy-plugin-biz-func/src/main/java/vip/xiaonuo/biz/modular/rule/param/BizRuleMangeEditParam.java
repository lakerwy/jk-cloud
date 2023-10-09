package vip.xiaonuo.biz.modular.rule.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class BizRuleMangeEditParam {
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;
    @ApiModelProperty(value = "标题", position = 2)
    private String title;
    @ApiModelProperty(value = "获取时间", position = 3)
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date getDate;
    @ApiModelProperty(value = "备注", position = 4)
    private String memo;
    @ApiModelProperty(value = "附件", position = 5)
    private String attachment;
    @ApiModelProperty(value = "负责人", position = 6)
    private String manager;
    @ApiModelProperty(value = "部门ID", position = 7)
    private String orgId;
    @ApiModelProperty(value = "部门名称", position = 8)
    private String orgName;

}
