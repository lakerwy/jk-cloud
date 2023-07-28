package vip.xiaonuo.biz.modular.report.service.impl;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.report.mapper.PartnerInfoMapper;
import vip.xiaonuo.biz.modular.report.result.PartnerVo;
import vip.xiaonuo.biz.modular.report.service.PartnerInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/7/28 9:01
 */

@Master
@Service
public class PartnerInfoServiceImpl implements Mapper<PartnerInfoMapper>, PartnerInfoService {

    @Resource
    private PartnerInfoMapper partnerInfoMapper;

    @Override
    public List<PartnerVo> getAll() {
        return partnerInfoMapper.getAll();
    }
}
