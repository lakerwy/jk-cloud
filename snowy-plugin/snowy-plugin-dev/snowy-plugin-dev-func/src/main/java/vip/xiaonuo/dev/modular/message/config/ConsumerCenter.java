package vip.xiaonuo.dev.modular.message.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.pojo.RabbitMsg;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/7 10:58
 */
@Configuration
@Slf4j
public class ConsumerCenter {

    @Resource
    IDingDingService iDingDingService;

    @Bean
    public Consumer<RabbitMsg> sink(){
        return rabbitMsg -> {
            log.info("------接受到消息----- 开始发送-----------"+rabbitMsg.getTitle());
            iDingDingService.commonSend(rabbitMsg);
        };
    }

}
