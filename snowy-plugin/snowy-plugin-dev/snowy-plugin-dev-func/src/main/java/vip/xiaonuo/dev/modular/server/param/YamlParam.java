package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Setter
@Getter
public class YamlParam {

    @ApiModelProperty(value = "系统服务ID",position = 1,required = true)
    private String systemId;

    @ApiModelProperty(value = "系统服务ip地址",position = 2)
    private String systemIp;

    @ApiModelProperty(value = "系统服务端口",position = 3)
    private String systemPort;

    @ApiModelProperty(value = "系统服务docker 外放端口范围（9800-9900）",position = 4,required = true)
    private String systemDockerPort;

    @ApiModelProperty(value = "系统服务域名地址",position = 5)
    private String systemDomain;
}
