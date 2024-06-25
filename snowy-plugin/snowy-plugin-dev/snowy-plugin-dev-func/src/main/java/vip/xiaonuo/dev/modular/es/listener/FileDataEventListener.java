package vip.xiaonuo.dev.modular.es.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.es.entity.FileData;
import vip.xiaonuo.dev.modular.es.event.FileDataEvent;
import vip.xiaonuo.dev.modular.es.service.FileDataService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/3 9:00
 * 事件监听
 */
@Component
@Slf4j
public class FileDataEventListener {

    @Resource
    FileDataService fileDataService;

    @Async
    @EventListener
    public void handleFileDataEvent(FileDataEvent event) {
        FileData fileData = event.getFileData();

        try {
            fileDataService.save(fileData);
            // 处理 fileData ...
        }catch (Exception e){
            log.error("New file data received fail: " + fileData.getName());
        }

    }
}
