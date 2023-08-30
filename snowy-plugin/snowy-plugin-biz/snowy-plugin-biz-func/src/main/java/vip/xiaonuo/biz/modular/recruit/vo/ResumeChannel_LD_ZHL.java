package vip.xiaonuo.biz.modular.recruit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/8/29 16:13
 *
 * 漏斗 - 汇总
 */

@Getter
@Setter
public class ResumeChannel_LD_ZHL {
    /** 名称 */
    @ApiModelProperty(value = "名称", position = 1)
    private String name;

    /** 数值 */
    @ApiModelProperty(value = "数值", position = 2)
    private Double value;

    public ResumeChannel_LD_ZHL(String name, Double value){
        this.name = name;
        this.value = value;
    }

}
