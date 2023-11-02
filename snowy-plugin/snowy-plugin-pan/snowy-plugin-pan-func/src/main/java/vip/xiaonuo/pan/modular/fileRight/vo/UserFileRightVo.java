package vip.xiaonuo.pan.modular.fileRight.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

/**
 * @author yong.ye
 * @date 2023/10/26 11:11
 */

@Getter
@Setter
public class UserFileRightVo extends UserFile {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 5)
    private String rightId;

    /** 权限值 */
    @ApiModelProperty(value = "权限值", position = 5)
    private Long rightValue;

}
