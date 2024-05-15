package vip.xiaonuo.sys.modular.dingtalk;

import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:28
 * 事件处理的策略接口
 */
public interface EventHandlingStrategy {
    void handleEvent(GenericOpenDingTalkEvent event);
}
