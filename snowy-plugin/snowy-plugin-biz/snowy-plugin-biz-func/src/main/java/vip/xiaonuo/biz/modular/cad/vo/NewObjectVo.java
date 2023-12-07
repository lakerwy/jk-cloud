package vip.xiaonuo.biz.modular.cad.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 新的对象实体
 */

@Getter
@Setter
public class NewObjectVo {

    /** 键值 - key */
    @ApiModelProperty(value = "键值 - key", position = 30)
    private String key;

    /** 键值 - value */
    @ApiModelProperty(value = "键值 - value", position = 30)
    private String value;
}
