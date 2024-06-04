package vip.xiaonuo.dev.modular.es.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vip.xiaonuo.dev.modular.es.entity.FileData;
import vip.xiaonuo.dev.modular.es.param.EsSearchParam;

import java.io.IOException;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/31 11:28
 */
public interface FileDataService {
    void save(FileData fileData) throws IOException;

    Page<FileData> searchFullKey(EsSearchParam esSearchParam);

    List<String> getAssociationalWordOther(EsSearchParam esSearchParam);
}
