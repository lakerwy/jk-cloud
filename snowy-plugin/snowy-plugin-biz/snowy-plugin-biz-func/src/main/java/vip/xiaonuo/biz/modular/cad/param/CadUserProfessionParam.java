package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户专业设置参数实体
 */

@Getter
@Setter
public class CadUserProfessionParam {

    /** 用户集合【支持多个】 */
    @ApiModelProperty(value = "用户集合【支持多个】", position = 7)
    private List<String> userList;

    /** 所属专业集合【支持多个】 */
    @ApiModelProperty(value = "所属专业【支持多个】", position = 7)
    private List<String> professionList;

}
