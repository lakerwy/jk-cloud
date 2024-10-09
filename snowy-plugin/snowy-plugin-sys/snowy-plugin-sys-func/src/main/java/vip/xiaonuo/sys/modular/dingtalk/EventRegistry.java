package vip.xiaonuo.sys.modular.dingtalk;

import cn.hutool.json.JSONUtil;
import com.dingtalk.open.app.api.GenericEventListener;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:35
 */
@Component
@Slf4j
public class EventRegistry {
    private Map<String, EventHandlingStrategy> strategyMap = new HashMap<>();

    @Resource
    DingTalkProperties dingTalkProperties;

    // 注册事件处理策略
    public void registerStrategy(String eventType, EventHandlingStrategy strategy) {
        strategyMap.put(eventType, strategy);
    }

    // 根据事件类型获取处理策略
    public EventHandlingStrategy getStrategy(String eventType) {
        return strategyMap.get(eventType);
    }

    // 处理未知事件类型
    public void handleUnknownEvent(GenericOpenDingTalkEvent event) {
        // 处理未知事件类型的业务逻辑
        log.info("【事件订阅-未知订阅】"+ JSONUtil.toJsonStr(event));
    }

    @PostConstruct
    public void init() throws Exception {
        handleData();
    }

    public void handleData() throws Exception {
        OpenDingTalkStreamClientBuilder
                .custom().credential(new AuthClientCredential(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret()))
                //注册事件监听
                .registerAllEventListener(new GenericEventListener() {
                    public EventAckStatus onEvent(GenericOpenDingTalkEvent event) {
                        try {
                           //事件类型
                            String eventType = event.getEventType();
                            EventHandlingStrategy strategy = getStrategy(eventType);
                            if(null != strategy){
                                //处理事件
                                getStrategy(eventType).handleEvent(event);
                            }else {
                                handleUnknownEvent(event);
                            }
                            //消费成功
                            return EventAckStatus.SUCCESS;
                        } catch (Exception e) {
                            //消费失败
                            return EventAckStatus.LATER;
                        }
                    }
                })
                .build().start();
    }
}
