package vip.xiaonuo.sys.modular.dingtalk.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 同步钉钉用户信息 - 指定用户
 */


@Getter
@Setter
public class SyncUserParam {

    /** 待同步-员工集合 */
    @ApiModelProperty(value = "待同步-员工集合", position = 1)
    private List<@Valid UserParam> userList;


    /**
     * 待同步员工信息
     */
    @Getter
    @Setter
    public static class UserParam{
        /** 员工手机号 */
        @ApiModelProperty(value = "员工手机号", position = 1,required = true)
        @NotBlank(message = "员工手机号不能为空")
        private String mobile;

        /** 员工姓名 */
        @ApiModelProperty(value = "员工姓名", position = 2,required = true)
        @NotBlank(message = "员工姓名不能为空")
        private String name;
    }
}

