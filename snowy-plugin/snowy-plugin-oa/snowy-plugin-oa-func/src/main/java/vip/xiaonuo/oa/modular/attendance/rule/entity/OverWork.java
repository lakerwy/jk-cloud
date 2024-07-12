package vip.xiaonuo.oa.modular.attendance.rule.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.handler.OverWorkRuleHandler;

import java.util.List;

/**
 * 考勤规则配置表实体
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Getter
@Setter
@TableName(value = "OA_ATTN_RULE",autoResultMap = true)
public class OverWork extends Rule<OverWorkRule>{

    /** 规则JSON */
    @ApiModelProperty(value = "规则JSON", position = 5)
    @TableField(value = "RULE_JSON", typeHandler = OverWorkRuleHandler.class)
    private List<OverWorkRule> ruleJson;

}
