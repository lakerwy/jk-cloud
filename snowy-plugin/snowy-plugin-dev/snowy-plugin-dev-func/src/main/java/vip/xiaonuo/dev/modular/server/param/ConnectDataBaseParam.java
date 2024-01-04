package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConnectDataBaseParam {
    @ApiModelProperty(value = "mysql用户名",required = true,position = 1)
    private String username;

    @ApiModelProperty(value = "密码",required = true,position = 2)
    private String password;

    @ApiModelProperty(value = "数据库名称",required = true,position = 3)
    private String dbName;

    @ApiModelProperty(value = "数据库ip",required = true,position = 4)
    private String dbIp;

    @ApiModelProperty(value = "mysql数据库端口",required = true,position = 5)
    private String port;

    /*@ApiModelProperty(value = "系统服务ID",required = true,position = 6)
    private String systemId;*/
}
