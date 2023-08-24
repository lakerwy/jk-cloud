package vip.xiaonuo.biz.modular.recruit.param.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/24 9:03
 */

@Getter
@Setter
public class ResumeRegistSaveDto {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 应聘登记JSON */
    @ApiModelProperty(value = "应聘登记JSON", position = 2)
    private String registJson;

}
