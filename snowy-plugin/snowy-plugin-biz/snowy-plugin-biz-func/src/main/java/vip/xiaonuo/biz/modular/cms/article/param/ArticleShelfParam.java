package vip.xiaonuo.biz.modular.cms.article.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yong.ye
 * @date 2023/7/13 10:15
 */

@Getter
@Setter
public class ArticleShelfParam {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 上架：1  下架：0 */
    @ApiModelProperty(value = "上架：1  下架：0", required = true)
    @NotNull
    private Integer isShelf;
}
