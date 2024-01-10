/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.dev.modular.server.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统服务操作记录表添加参数
 *
 * @author ljy
 * @date  2024/01/03 16:07
 **/
@Getter
@Setter
public class SystemConfExecAddParam {

    /** 系统服务主键 */
    @ApiModelProperty(value = "系统服务主键", position = 2)
    private String systemId;

    /** 数据库信息 */
    @ApiModelProperty(value = "数据库配置信息(数据库ip、端口、用户、密码)", position = 3)
    private String dataBaseJson;

    @ApiModelProperty(value = "系统部署服务器信息(部署的服务器IP,用户,密码,配置文件存放地址《默认/epc/server/》,容器外放端口,域名)", position = 4)
    private String systemServerJson;

}
