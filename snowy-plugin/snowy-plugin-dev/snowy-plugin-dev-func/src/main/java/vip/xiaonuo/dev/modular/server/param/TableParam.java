package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class TableParam {
    @ApiModelProperty(value = "服务器ip", required = true,position = 1)
    @NotNull(message = "服务器ip不能为空")
    private String sshHost;

    @ApiModelProperty(value = "端口号", required = true,position = 2)
    @NotNull(message = "端口号不能为空")
    private Integer sshPort;

    @ApiModelProperty(value = "服务器用户名", required = true,position = 3)
    @NotNull(message = "服务器用户名不能为空")
    private String sshUsername;

    @ApiModelProperty(value = "服务器密码", required = true,position = 4)
    @NotNull(message = "服务器密码不能为空")
    private String sshPassword;

    @ApiModelProperty(value = "数据库用户名",position = 5)
    private String databaseUsername;

    @ApiModelProperty(value = "数据库连接密码",position = 6)
    private String databasePassword;

    @ApiModelProperty(value = "数据库名", position = 7)
    private String databaseName;

    @ApiModelProperty(value = "db数据表存放路径", position = 8)
    private String dumpFilePath;

    @ApiModelProperty(value = "docker容器mysql名称",position = 9)
    private String dockerMyName;

    @ApiModelProperty(value = "执行命令-无需传参", position = 10)
    private String command;

    @ApiModelProperty(value = "系统服务ID", required = true,position = 10)
    @NotNull(message = "系统服务ID不能为空")
    private String systemId;
}
