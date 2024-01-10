package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class DataBaseParam {
    @ApiModelProperty(value = "mysql用户名", required = true,position = 1)
    @NotNull(message = "mysql用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true,position = 2)
    @NotNull(message = "mysql密码不能为空")
    private String password;

    @ApiModelProperty(value = "数据库名称-自动生成", position = 3)
    private String dbName;

    @ApiModelProperty(value = "数据库ip", required = true,position = 4)
    @NotNull(message = "数据库ip不能为空")
    private String dbIp;

    @ApiModelProperty(value = "mysql数据库端口", required = true,position = 5)
    @NotNull(message = "mysql数据库端口不能为空")
    private String port;
}
