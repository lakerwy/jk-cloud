package vip.xiaonuo.sys.modular.dingtalk.impl;

import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.modular.dingtalk.EventHandlingStrategy;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:30
 */
@Component
public class UserUpdateEventStrategy implements EventHandlingStrategy {
    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {

    }
}
