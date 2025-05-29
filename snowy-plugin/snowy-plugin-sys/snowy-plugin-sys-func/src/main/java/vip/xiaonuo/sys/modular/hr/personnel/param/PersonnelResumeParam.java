package vip.xiaonuo.sys.modular.hr.personnel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 业务表-人事档案添加参数
 *
 * @author pzh
 * @date  2025/05/29 10:08
 **/
@Getter
@Setter
public class PersonnelResumeParam {

    @ApiModelProperty(value = "姓名", required = true)
    @NotNull(message = "姓名不能为空")
    private String name;

}
