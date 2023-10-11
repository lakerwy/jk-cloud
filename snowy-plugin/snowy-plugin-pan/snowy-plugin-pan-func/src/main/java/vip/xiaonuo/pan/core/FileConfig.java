package vip.xiaonuo.pan.core;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/10/11 9:50
 */
@Component
@Getter
public class FileConfig {
    /**
     * 默认初始化空间大小 单位为g
     */
    @Value("${pan.file.storage.self.size:5}")
    private   Long totalStorageSize;

    /**
     * 默认初始化空间大小 单位为g
     */
    @Value("${pan.file.storage.group.size:50}")
    private   Long totalGroupStorageSize;
}
