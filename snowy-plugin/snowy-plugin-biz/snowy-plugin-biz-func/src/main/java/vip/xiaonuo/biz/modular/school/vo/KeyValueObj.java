package vip.xiaonuo.biz.modular.school.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyValueObj {

    /** Key */
    @ApiModelProperty(value = "Key")
    private String key ;

    /** value */
    @ApiModelProperty(value = "value")
    private Object value ;
}
