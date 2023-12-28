package vip.xiaonuo.sys.modular.epcMod.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库查询结果对象
 */

@Getter
@Setter
public class ObjInfo {

    /** id */
    @ApiModelProperty(value = "id", position = 1)
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;
}
