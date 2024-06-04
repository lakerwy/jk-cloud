package vip.xiaonuo.dev.modular.es.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import vip.xiaonuo.dev.modular.es.entity.FileData;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/31 11:27
 */
@Repository
public interface FileDataRepository extends ElasticsearchRepository<FileData,String> {
}
