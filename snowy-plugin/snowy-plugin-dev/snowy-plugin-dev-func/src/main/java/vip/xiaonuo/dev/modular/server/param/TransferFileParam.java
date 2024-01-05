package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class TransferFileParam {
    @ApiModelProperty(value = "源文件路径", position = 1)
    @NotNull(message = "非系统服务，路径必传")
    private String sourceFilePath ;

    @ApiModelProperty(value = "目标文件路径",position = 2)
    @NotNull(message = "非系统服务，路径必传")
    private String targetFilePath;

    @ApiModelProperty(value = "源服务器用户名", position = 3)
    @NotNull(message = "非系统服务，路径必传")
    private String sourceUsername;

    @ApiModelProperty(value = "源服务器密码", required = true,position = 4)
    private String sourcePassword;

    @ApiModelProperty(value = "源服务器IP地址",position = 5)
    @NotNull(message = "非系统服务调用，源服务器IP地址必传")
    private String sourceHost;

    @ApiModelProperty(value = "目标服务器用户名", required = true,position = 6)
    private String targetUsername;

    @ApiModelProperty(value = "目标服务器密码", required = true,position = 7)
    private String targetPassword;

    @ApiModelProperty(value = "目标服务器IP地址", required = true,position = 8)
    private String targetHost;

    @ApiModelProperty(value = "文件名称，需要带后缀名",position = 9)
    @NotNull(message = "非系统服务，文件名称必传")
    private String fileName;

    @ApiModelProperty(value = "系统服务ID",position = 10)
    private String systemId;
}
