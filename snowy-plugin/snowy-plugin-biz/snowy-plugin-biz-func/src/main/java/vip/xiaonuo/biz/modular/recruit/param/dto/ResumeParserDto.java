package vip.xiaonuo.biz.modular.recruit.param.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/24 8:45
 */

@Getter
@Setter
public class ResumeParserDto {
    /** 待解析的简历 主键ID */
    @ApiModelProperty(value = "待解析的简历 主键ID", position = 1)
    private String id;
}
