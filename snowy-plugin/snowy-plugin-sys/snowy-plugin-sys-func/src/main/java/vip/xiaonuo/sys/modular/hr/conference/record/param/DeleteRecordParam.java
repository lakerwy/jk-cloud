package vip.xiaonuo.sys.modular.hr.conference.record.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class DeleteRecordParam {
    /** ID */
    @ApiModelProperty(value = "ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
