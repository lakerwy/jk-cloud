package vip.xiaonuo.oa.modular.attendance.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.handler.OverWorkEnumHandler;
import vip.xiaonuo.oa.core.handler.OverWorkRuleHandler;
import vip.xiaonuo.oa.modular.attendance.rule.enums.OverWorkEnum;

import java.util.List;

/**
 * @author mayike
 * @version 1.0  加班规则
 * @date 2024/7/3 10:21
 */
public class OverWorkRule extends BaseRule{

    @ApiModelProperty(value = "日期类型(1 工作日 2 休息日 3 节假日)")
    private int dayType = 1;

    @ApiModelProperty(value = "加班规则类型")
    private String overWorkType;

    /**
     * @JsonIgnore: 标记这些字段在 JSON 序列化时应被忽略，以避免存储在数据库中或暴露内部字段
     */
    @JsonIgnore
    private String overWorkValue;

    public int getDayType() {
        return dayType;
    }

    public void setDayType(int dayType) {
        this.dayType = dayType;
    }

    public String getOverWorkType() {
        return overWorkType;
    }

    public void setOverWorkType(String overWorkType) {
        this.overWorkType = overWorkType;
    }

    /**
     *
     * @return   @JsonProperty  确保自定义 getter 方法生成的值在 JSON 序列化时包含在返回的 JSON 中
     */
    @JsonProperty("overWorkValue")
    public String getOverWorkValue() {
        this.overWorkValue = (this.getDayType() == 1?"工作日":(this.getDayType()==2?"休息日":"节假日")) +":"+OverWorkEnum.fromType(this.getOverWorkType());
        return overWorkValue;
    }
}
