package vip.xiaonuo.sys.modular.dingtalk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:40
 */
@Configuration
public class EventConfig {
    @Autowired
    public void registerEventStrategies(EventRegistry eventRegistry,
                                        EventHandlingStrategy userAddEventStrategy,
                                        EventHandlingStrategy userUpdateEventStrategy,
                                        EventHandlingStrategy userLeaveEventStrategy,
                                        EventHandlingStrategy orgAddEventStrategy,
                                        EventHandlingStrategy orgUpdateEventStrategy,
                                        EventHandlingStrategy orgRemoveEventStrategy
                                        ) {
        eventRegistry.registerStrategy(DingTalk.EventType.USER_ADD, userAddEventStrategy);
        eventRegistry.registerStrategy(DingTalk.EventType.USER_UPDATE, userUpdateEventStrategy);
        eventRegistry.registerStrategy(DingTalk.EventType.USER_LEAVE,userLeaveEventStrategy);
        eventRegistry.registerStrategy(DingTalk.EventType.ORG_ADD,orgAddEventStrategy);
        eventRegistry.registerStrategy(DingTalk.EventType.ORG_UPDATE,orgUpdateEventStrategy);
        eventRegistry.registerStrategy(DingTalk.EventType.ORG_REMOVE,orgRemoveEventStrategy);
    }
}
