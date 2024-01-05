package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class SingleConfParam {
    @ApiModelProperty(value = "配置文件路径",position = 1)
    private String configPath;

    @ApiModelProperty(value = "源服务器用户名", position = 2)
    private String sourceUsername;

    @ApiModelProperty(value = "源服务器密码", position = 3)
    private String sourcePassword;

    @ApiModelProperty(value = "源服务器IP地址", position = 4)
    private String sourceHost;

    @ApiModelProperty(value = "目标服务器用户名", position = 5)
    private String targetUsername;

    @ApiModelProperty(value = "目标服务器密码",position = 6)
    private String targetPassword;

    @ApiModelProperty(value = "目标服务器IP地址",position = 7)
    private String targetHost;

    @ApiModelProperty(value = "系统服务ID",required = true,position = 8)
    @NotNull(message = "系统服务ID不能为空")
    private String systemId;

}
