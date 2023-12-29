package vip.xiaonuo.dev.modular.epcMod.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnixCommandParam {
    @ApiModelProperty(value = "服务器ip", required = true,position = 1)
    private String sshHost;

    @ApiModelProperty(value = "端口号", required = true,position = 2)
    private Integer sshPort;

    @ApiModelProperty(value = "服务器用户名", required = true,position = 3)
    private String sshUsername;

    @ApiModelProperty(value = "服务器密码", required = true,position = 4)
    private String sshPassword;

    @ApiModelProperty(value = "执行命令",  required = true,position = 5)
    private String command;
}
