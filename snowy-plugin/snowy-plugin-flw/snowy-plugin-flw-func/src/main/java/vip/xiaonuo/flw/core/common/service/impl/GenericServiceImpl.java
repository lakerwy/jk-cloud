package vip.xiaonuo.flw.core.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.flw.core.common.entity.GenericEntity;
import vip.xiaonuo.flw.core.common.mapper.GenericMapper;
import vip.xiaonuo.flw.core.common.service.GenericService;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/15 10:42
 */
@Service
public class GenericServiceImpl extends ServiceImpl<GenericMapper, GenericEntity> implements GenericService {
    @Override
    public Boolean insertGenericEntity(GenericEntity genericEntity) {
        return baseMapper.insertGenericEntity(genericEntity);
    }
}
