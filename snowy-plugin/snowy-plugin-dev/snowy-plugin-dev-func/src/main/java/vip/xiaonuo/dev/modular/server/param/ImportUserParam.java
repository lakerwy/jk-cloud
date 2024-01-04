package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImportUserParam {
    @ApiModelProperty(value = "服务器连接地址，IP： 10.0.33.81，例如：jdbc:mysql://10.0.33.81:3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true", required = true,position = 1)
    private String ip;

    @ApiModelProperty(value = "服务器连接地址，端口Port：3306，例如：jdbc:mysql://10.0.33.81:3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true", required = true,position = 2)
    private String port;

    @ApiModelProperty(value = "mysql用户名", required = true,position = 3)
    private String username;

    @ApiModelProperty(value = "密码", required = true,position = 4)
    private String password;

    @ApiModelProperty(value = "数据库名称", required = true,position = 5)
    private String dbName;

    @ApiModelProperty(value = "导入的文件名称", required = true,position = 6)
    private String fileId;

    @ApiModelProperty(value = "系统服务ID", required = true,position = 7)
    String systemId;
}
