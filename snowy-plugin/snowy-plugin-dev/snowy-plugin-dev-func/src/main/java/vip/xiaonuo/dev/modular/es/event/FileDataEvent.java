package vip.xiaonuo.dev.modular.es.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import vip.xiaonuo.dev.modular.es.entity.FileData;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/3 8:58
 * 文件事件
 */
@Getter
public class FileDataEvent extends ApplicationEvent {
    private final FileData fileData;

    public FileDataEvent(Object source, FileData fileData) {
        super(source);
        this.fileData = fileData;
    }

}
