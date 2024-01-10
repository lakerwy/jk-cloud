package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Setter
@Getter
public class TransferLocalFileParam {


    @ApiModelProperty(value = "目标服务器IP", required = true,position = 2)
    private String targetHost;

    @ApiModelProperty(value = "目标服务器账户", required = true,position = 4)
    private String targetUserName;

    @ApiModelProperty(value = "目标服务器密码", required = true,position = 5)
    private String targetPassword;

    @ApiModelProperty(value = "目标服务器路径", required = true,position = 6)
    private String targetRemoteFolderPath;
}
