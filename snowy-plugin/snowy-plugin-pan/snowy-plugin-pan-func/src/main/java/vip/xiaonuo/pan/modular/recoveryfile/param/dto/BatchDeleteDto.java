package vip.xiaonuo.pan.modular.recoveryfile.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/11 9:36
 */

@Getter
@Setter
public class BatchDeleteDto {

    /** 用户文件集Id */
    @ApiModelProperty(value = "用户文件集Id", position = 1)
    private List<String> userFileIds;
}
