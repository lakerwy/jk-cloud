package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/10/13 9:17
 */
@Getter
@Setter
public class DownloadParam {
    private String fileIds;

    private String tenantId;

    @ApiModelProperty(value = "权限校验类型：GROUP 群组  COMMON 共享  注：（不传或不在指定范围） 不做权限校验")
    private String rightType;
}
