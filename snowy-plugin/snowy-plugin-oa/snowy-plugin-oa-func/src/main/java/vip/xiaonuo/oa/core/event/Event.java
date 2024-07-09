package vip.xiaonuo.oa.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author mayike
 * @version 1.0  通用事件
 * @date 2024/7/5 16:25
 */
public class Event<T extends EventPayload> extends ApplicationEvent {

    private final T payload;

    public Event(Object source, T payload) {
        super(source);
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
