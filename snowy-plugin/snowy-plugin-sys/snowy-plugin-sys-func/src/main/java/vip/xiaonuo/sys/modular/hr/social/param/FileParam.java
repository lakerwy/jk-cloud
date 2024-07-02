package vip.xiaonuo.sys.modular.hr.social.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileParam {

    /** 简历附件-Id */
    @ApiModelProperty(value = "简历附件-Id", position = 1)
    private String fileId;

    /** 简历附件-文件名称 */
    @ApiModelProperty(value = "简历附件-文件名称", position = 2)
    private String name;

}
