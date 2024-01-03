package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingleConfParam {
    @ApiModelProperty(value = "配置文件路径", required = true,position = 1)
    private String configPath;

    @ApiModelProperty(value = "源服务器用户名", required = true,position = 2)
    private String sourceUsername;

    @ApiModelProperty(value = "源服务器密码", required = true,position = 3)
    private String sourcePassword;

    @ApiModelProperty(value = "源服务器IP地址", required = true,position = 4)
    private String sourceHost;

    @ApiModelProperty(value = "目标服务器用户名", required = true,position = 5)
    private String targetUsername;

    @ApiModelProperty(value = "目标服务器密码", required = true,position = 6)
    private String targetPassword;

    @ApiModelProperty(value = "目标服务器IP地址", required = true,position = 7)
    private String targetHost;

    @ApiModelProperty(value = "系统服务ID",position = 8)
    private String systemId;

}
