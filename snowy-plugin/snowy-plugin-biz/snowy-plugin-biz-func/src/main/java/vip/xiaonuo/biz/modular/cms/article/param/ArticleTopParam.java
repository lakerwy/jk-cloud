package vip.xiaonuo.biz.modular.cms.article.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/17 9:17
 */

@Getter
@Setter
public class ArticleTopParam {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 是否置顶，0: 取消置顶 1: 置顶 */
    @ApiModelProperty(value = "是否置顶，0: 未置顶 1: 置顶", required = true)
    private Integer isTop;

}
