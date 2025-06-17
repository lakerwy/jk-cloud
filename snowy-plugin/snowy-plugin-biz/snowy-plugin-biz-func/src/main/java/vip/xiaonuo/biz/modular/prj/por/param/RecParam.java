package vip.xiaonuo.biz.modular.prj.por.param;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class RecParam {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID",required = true, position = 1)
    @NotBlank(message = "主键id不能为空")
    private String id;

    /** 回款情况 */
    @ApiModelProperty(value = "回款情况", position = 2)
    private String recStatus;
}
