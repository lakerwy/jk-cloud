package vip.xiaonuo.util.modular.log.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 操作日志编辑参数
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
@Getter
@Setter
public class OperationLogEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 日志分类 */
    @ApiModelProperty(value = "日志分类", position = 2)
    private String category;

    /** 功能名称 */
    @ApiModelProperty(value = "功能名称", position = 3)
    private String name;

    /** 业务主键ID */
    @ApiModelProperty(value = "业务主键ID", position = 4)
    private String businessId;

    /** 请求参数 */
    @ApiModelProperty(value = "请求参数", position = 7)
    private String paramJson;

    /** 数据变化记录 */
    @ApiModelProperty(value = "数据变化记录", position = 10)
    private String updateJson;

    /** 操作时间 */
    @ApiModelProperty(value = "操作时间", position = 11)
    private Date opTime;

    /** 操作人姓名 */
    @ApiModelProperty(value = "操作人姓名", position = 12)
    private String opUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 17)
    private String memo;

}
