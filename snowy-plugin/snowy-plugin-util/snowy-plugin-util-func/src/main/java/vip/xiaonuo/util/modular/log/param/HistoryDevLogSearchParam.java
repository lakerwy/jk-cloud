package vip.xiaonuo.util.modular.log.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/9/3 10:01
 * 历史日志查询
 */
@Getter
@Setter
public class HistoryDevLogSearchParam {

    @ApiModelProperty(value = "历史表名称",required = true)
    @NotBlank(message = "表名不能为空")
    private String tableName;

    @ApiModelProperty(value = "关键字查询")
    private String searchKey;

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;
}
