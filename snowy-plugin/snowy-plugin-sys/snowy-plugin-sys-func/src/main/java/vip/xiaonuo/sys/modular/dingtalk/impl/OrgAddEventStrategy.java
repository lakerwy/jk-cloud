package vip.xiaonuo.sys.modular.dingtalk.impl;

import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.modular.dingtalk.EventHandlingStrategy;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:32
 */
@Component
@Slf4j
public class OrgAddEventStrategy implements EventHandlingStrategy {
    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {
        log.info("org add----");
    }
}
