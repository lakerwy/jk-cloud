package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class YamlParam {
    @ApiModelProperty(value = "模板配置文件路径", required = true,position = 1)
    private String configPath;

    @ApiModelProperty(value = "配置文件输出路径", required = true,position = 2)
    private String outYamlPath;

    @ApiModelProperty(value = "需要修改的参数，例：key-param， value 82", required = true,position = 3)
    private Map<String,Object> paramMap;
}
