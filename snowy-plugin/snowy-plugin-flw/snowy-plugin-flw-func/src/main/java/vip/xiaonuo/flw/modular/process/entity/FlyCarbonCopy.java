package vip.xiaonuo.flw.modular.process.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName FlyCarbonCopy
 * @Description TODO
 * @Author hxw
 * @Date 2024/4/24 17:30
 */
@Getter
@Setter
public class FlyCarbonCopy {
    /**
     *流程id
     */
    @ApiModelProperty(value = "流程id", position = 1)
    private String ProcessInstanceId;
    /**
     * 人员id
     */
    @ApiModelProperty(value = "人员id", position = 2)
    private List<String> userList;
}
