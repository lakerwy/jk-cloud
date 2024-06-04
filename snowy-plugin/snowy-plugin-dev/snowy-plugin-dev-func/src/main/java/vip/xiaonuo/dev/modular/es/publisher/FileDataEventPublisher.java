package vip.xiaonuo.dev.modular.es.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.es.entity.FileData;
import vip.xiaonuo.dev.modular.es.event.FileDataEvent;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/3 8:59
 * 事件发布
 */
@Component
public class FileDataEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public FileDataEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    public void publishEvent(FileData fileData) {
        FileDataEvent event = new FileDataEvent(this, fileData);
        applicationEventPublisher.publishEvent(event);
    }
}
