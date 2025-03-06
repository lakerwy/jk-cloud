package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钉钉考勤-员工白名单编辑参数
 *
 * @author pzh
 * @date  2025/03/06 11:34
 **/
@Getter
@Setter
public class AttendWhiteListEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 类型（高管|返聘） */
    @ApiModelProperty(value = "类型（高管|返聘）", position = 2)
    private String type;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 4)
    private String userId;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

}
