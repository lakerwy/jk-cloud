package vip.xiaonuo.flw.core.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/15 10:30
 */
@Setter
@Getter
public class GenericEntity {

    @ApiModelProperty(value = "库名",required = true)
    private String dbName;

    /**表名**/
    @ApiModelProperty(value = "表名",required = true)
    private String tableName;

    /**字段集合**/
    @ApiModelProperty(value = "字段集合",required = true)
    private Map<String,Object> fieldMap;

    private Boolean result;
}
