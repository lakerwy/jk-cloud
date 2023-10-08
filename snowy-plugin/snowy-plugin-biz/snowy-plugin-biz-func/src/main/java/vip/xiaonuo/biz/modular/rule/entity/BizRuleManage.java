package vip.xiaonuo.biz.modular.rule.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("BIZ_RULE_MANAGE")
public class BizRuleManage {
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    @ApiModelProperty(value = "标题", position = 2)
    private String title;

    @ApiModelProperty(value = "获取时间", position = 3)
    private String getDate;

    @ApiModelProperty(value = "备注", position = 4)
    private String memo;

    @ApiModelProperty(value = "附件", position = 5)
    private String attachment;

    @ApiModelProperty(value = "负责人", position = 6)
    private String manager;
}
