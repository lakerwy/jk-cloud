package vip.xiaonuo.pan.modular.commonFile.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.pan.modular.fileRight.param.ObjectRight;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/11 15:12
 */

@Getter
@Setter
public class CommonFileParam {

    /** 待共享的文件id */
    @ApiModelProperty(value = "待共享的文件id", position = 1)
    private String userFileId;

    /** 共享用户 权限分配 集合 */
    @ApiModelProperty(value = "共享用户 权限分配 集合", position = 2)
    private List<ObjectRight> commonUserList;

}



