package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataBaseParam {
    /*@ApiModelProperty(value = "服务器连接地址，例如：jdbc:mysql://10.0.33.81:3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true", required = true,position = 1)
    private String url;*/

    @ApiModelProperty(value = "mysql用户名", required = true,position = 1)
    private String username;

    @ApiModelProperty(value = "密码", required = true,position = 2)
    private String password;

    @ApiModelProperty(value = "数据库名称", required = true,position = 3)
    private String dbName;


    @ApiModelProperty(value = "数据库ip", required = true,position = 4)
    private String dbIp;

    @ApiModelProperty(value = "mysql数据库端口", required = true,position = 5)
    private String port;

    @ApiModelProperty(value = "系统服务ID",position = 6)
    private String systemId;
}
