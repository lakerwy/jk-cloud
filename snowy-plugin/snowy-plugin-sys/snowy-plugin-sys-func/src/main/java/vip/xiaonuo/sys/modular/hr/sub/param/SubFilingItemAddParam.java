package vip.xiaonuo.sys.modular.hr.sub.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分包单位备案明细添加参数
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Getter
@Setter
public class SubFilingItemAddParam {

    /** 关联-分包单位备案ID */
    @ApiModelProperty(value = "关联-分包单位备案ID", position = 2)
    private String subFilingId;

    /** 分包单位名称 */
    @ApiModelProperty(value = "分包单位名称", position = 3)
    private String unitName;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attachments;

}
