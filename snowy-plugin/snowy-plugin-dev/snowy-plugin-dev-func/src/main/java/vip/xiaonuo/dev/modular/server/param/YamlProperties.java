package vip.xiaonuo.dev.modular.server.param;

import cn.hutool.core.util.RandomUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/29 14:38
 */
@Setter
@Getter
public class YamlProperties {

    @ApiModelProperty(value = "rabbit交换机名称", required = false, position = 1)
    String rabbitTopicExchange = "exchange-epc-"+ RandomUtil.randomString(6);

}
