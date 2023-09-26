package vip.xiaonuo.biz.modular.fgs.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.fgs.entity.Output;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/2 10:48
 */

@Getter
@Setter
public class BatchOutputParam {
    /** 产值数据集合 */
    @ApiModelProperty(value = "产值数据集合", position = 1)
    private List<Output> outputlist;
}
