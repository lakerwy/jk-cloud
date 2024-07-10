package vip.xiaonuo.util.modular.initData.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.util.modular.initData.entity.InitData;
import vip.xiaonuo.util.modular.initData.mapper.InitDataMapper;
import vip.xiaonuo.util.modular.initData.service.InitDataService;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/10 9:50
 */
@Service
public class InitDataServiceImpl extends ServiceImpl<InitDataMapper, InitData> implements InitDataService {

    @Override
    public boolean getInitStatusByType(String type) {
        InitData one = this.getOne(new LambdaQueryWrapper<InitData>().eq(InitData::getType, type));
        if(ObjectUtil.isNotEmpty(one)){
            return  one.getInitStatus();
        }
        return false;
    }
}
