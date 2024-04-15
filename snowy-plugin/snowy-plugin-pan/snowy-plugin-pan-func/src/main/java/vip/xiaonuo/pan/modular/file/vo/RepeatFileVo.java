package vip.xiaonuo.pan.modular.file.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

/**
 * 网盘判断文件夹|文件是否存在，若存在，返回存在的UserFile信息等
 */

@Getter
@Setter
public class RepeatFileVo{
    @ApiModelProperty(value = "新文件名")
    private String newFileName;

    @ApiModelProperty(value = "已存在的文件信息实体")
    private UserFile oldUserFile;

}
