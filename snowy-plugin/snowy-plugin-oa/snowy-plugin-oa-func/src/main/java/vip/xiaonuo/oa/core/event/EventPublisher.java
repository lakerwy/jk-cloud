package vip.xiaonuo.oa.core.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0 事件发布器
 * @date 2024/7/5 16:27
 */
@Component
public class EventPublisher {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(Event event){
        applicationEventPublisher.publishEvent(event);
    }

}
