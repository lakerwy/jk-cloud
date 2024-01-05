package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ImportUserParam {
    @ApiModelProperty(value = "服务器连接地址，IP： 10.0.33.81，例如：jdbc:mysql://10.0.33.81:3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true", position = 1)
    private String ip;

    @ApiModelProperty(value = "服务器连接地址，端口Port：3306，例如：jdbc:mysql://10.0.33.81:3306?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&nullCatalogMeansCurrent=true&useInformationSchema=true", position = 2)
    private String port;

    @ApiModelProperty(value = "mysql用户名",position = 3)
    private String username;

    @ApiModelProperty(value = "密码",position = 4)
    private String password;

    @ApiModelProperty(value = "数据库名称", position = 5)
    private String dbName;

    @ApiModelProperty(value = "导入的文件ID", required = true,position = 6)
    @NotNull(message = "导入的文件ID不能为空")
    private String fileId;

    @ApiModelProperty(value = "系统服务ID", required = true,position = 7)
    @NotNull(message = "系统服务ID不能为空")
    String systemId;
}
