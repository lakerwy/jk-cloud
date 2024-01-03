package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferFileParam {
    @ApiModelProperty(value = "源文件路径", required = true,position = 1)
    private String sourceFilePath ;

    @ApiModelProperty(value = "目标文件路径", required = true,position = 2)
    private String targetFilePath;

    @ApiModelProperty(value = "源服务器用户名", required = true,position = 3)
    private String sourceUsername;

    @ApiModelProperty(value = "源服务器密码", required = true,position = 4)
    private String sourcePassword;

    @ApiModelProperty(value = "源服务器IP地址", required = true,position = 5)
    private String sourceHost;

    @ApiModelProperty(value = "目标服务器用户名", required = true,position = 6)
    private String targetUsername;

    @ApiModelProperty(value = "目标服务器密码", required = true,position = 7)
    private String targetPassword;

    @ApiModelProperty(value = "目标服务器IP地址", required = true,position = 8)
    private String targetHost;

    @ApiModelProperty(value = "文件名称，需要带后缀名", required = true,position = 9)
    private String fileName;

    @ApiModelProperty(value = "系统服务ID",position = 10)
    private String systemId;
}
