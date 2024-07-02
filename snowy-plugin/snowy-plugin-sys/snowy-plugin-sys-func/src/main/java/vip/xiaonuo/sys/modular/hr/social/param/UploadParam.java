package vip.xiaonuo.sys.modular.hr.social.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 批量上传简历 实体
 */

@Getter
@Setter
public class UploadParam {

    /** 简历附件-集合 */
    @ApiModelProperty(value = "简历附件-集合", position = 1)
    private List<FileParam> fileList;

}

