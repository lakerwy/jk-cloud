package vip.xiaonuo.dev.modular.job.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.modular.job.mapper.BizTaskJobMapper;
import vip.xiaonuo.dev.modular.job.service.BizTaskJobService;

import javax.annotation.Resource;

/**
 * @author yong.ye
 * @date 2023/8/16 15:25
 */
@DS("slave_1")
@Service
public class BizTaskJobServiceImpl implements Mapper<BizTaskJobMapper>, BizTaskJobService {
    @Resource
    private BizTaskJobMapper bizTaskJobMapper;
    @Override
    public void autoUpdateProgressRate() {
        bizTaskJobMapper.firstAuto();
        bizTaskJobMapper.secondAuto();
        bizTaskJobMapper.thirdAuto();
    }
}
