package vip.xiaonuo.flw.core.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.flw.core.common.entity.GenericEntity;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/15 10:41
 */
public interface GenericService extends IService<GenericEntity> {
    Boolean insertGenericEntity(GenericEntity genericEntity);
}
