package vip.xiaonuo.pan.modular.commonFile.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/11 15:12
 */

@Getter
@Setter
public class CommonFileParam {

    /** 用户文件id */
    @ApiModelProperty(value = "用户文件id", position = 1)
    private String userFileId;

    /** 共享用户id集合 */
    @ApiModelProperty(value = "共享用户id集合", position = 2)
    private List<String> commonUserList;

}
