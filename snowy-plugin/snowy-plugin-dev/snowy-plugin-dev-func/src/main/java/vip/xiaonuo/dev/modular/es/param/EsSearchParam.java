package vip.xiaonuo.dev.modular.es.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/3 10:06
 */
@Setter
@Getter
public class EsSearchParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;
}
