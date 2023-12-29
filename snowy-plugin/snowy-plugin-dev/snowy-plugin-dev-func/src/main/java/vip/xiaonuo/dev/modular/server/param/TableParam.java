package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TableParam {
    @ApiModelProperty(value = "服务器ip", required = true,position = 1)
    private String sshHost;

    @ApiModelProperty(value = "端口号", required = true,position = 2)
    private Integer sshPort;

    @ApiModelProperty(value = "服务器用户名", required = true,position = 3)
    private String sshUsername;

    @ApiModelProperty(value = "服务器密码", required = true,position = 4)
    private String sshPassword;

    @ApiModelProperty(value = "数据库用户名", required = true,position = 5)
    private String databaseUsername;

    @ApiModelProperty(value = "数据库连接密码", required = true,position = 6)
    private String databasePassword;

    @ApiModelProperty(value = "数据库名", required = true,position = 7)
    private String databaseName;

    @ApiModelProperty(value = "db数据表存放路径", required = true,position = 8)
    private String dumpFilePath;

    @ApiModelProperty(value = "docker容器mysql名称", required = true,position = 9)
    private String dockerMyName;

    @ApiModelProperty(value = "执行命令", position = 10)
    private String command;
}
