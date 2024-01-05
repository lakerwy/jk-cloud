package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnixCommandParam {
    @ApiModelProperty(value = "目标服务器ip", position = 1)
    private String sshHost;

    @ApiModelProperty(value = "端口号", position = 2)
    private Integer sshPort;

    @ApiModelProperty(value = "目标服务器用户名", position = 3)
    private String sshUsername;

    @ApiModelProperty(value = "目标服务器密码", position = 4)
    private String sshPassword;

    @ApiModelProperty(value = "执行命令",  required = true,position = 5)
    private String command;

    @ApiModelProperty(value = "系统服务ID",position = 6)
    private String systemId;
}
