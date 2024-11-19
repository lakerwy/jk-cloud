package vip.xiaonuo.sys.modular.hr.techAchive.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 查新Id参数
 *
 * @author pzh
 * @date  2024/10/18 08:44
 **/
@Getter
@Setter
public class NoveltySearchIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
