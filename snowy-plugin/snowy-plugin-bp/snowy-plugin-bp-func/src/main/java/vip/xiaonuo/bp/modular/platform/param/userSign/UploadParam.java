package vip.xiaonuo.bp.modular.platform.param.userSign;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 招采平台 - 报名 - 上传报名文件
 */

@Getter
@Setter
public class UploadParam {

    /** 报名记录 主键 Id (BP_USER_SIGN.ID) */
    @ApiModelProperty(value = "报名记录 主键 Id (BP_USER_SIGN.ID) ", position = 2)
    private String id;

    /** 报名记录 报名文件（BP_USER_SIGN.ATTACH） */
    @ApiModelProperty(value = "报名记录 报名文件（BP_USER_SIGN.ATTACH）",required = true, position = 3)
    private String attach;
}
