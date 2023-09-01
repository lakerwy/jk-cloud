package vip.xiaonuo.biz.modular.task.service.impl;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.task.mapper.SysOrgMapper;
import vip.xiaonuo.biz.modular.task.service.SysOrgService;
import vip.xiaonuo.biz.modular.task.vo.BizSysOrgVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/8/7 16:42
 */

@Master
@Service
public class SysOrgServiceImpl implements Mapper<SysOrgMapper>, SysOrgService {

    @Resource
    private SysOrgMapper sysOrgMapper;

    @Override
    public List<BizSysOrgVo> getOrgList() {
        return sysOrgMapper.getOrgList();
    }

    @Override
    public BizSysOrgVo getOrgById(String orgId) {
        return sysOrgMapper.getOrgById(orgId);
    }

    @Override
    public BizSysOrgVo getOrgByName(String orgName) {
        return sysOrgMapper.getOrgByOrgName(orgName);
    }
}
