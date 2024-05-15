package vip.xiaonuo.sys.modular.user.service.impl;

import org.springframework.stereotype.Service;
import vip.xiaonuo.sys.modular.user.service.DingDingSyncService;

/**
 * 钉钉 - 数据 - 同步  服务实现类
 */

@Service
public class DingDingSyncServiceImpl implements DingDingSyncService {



    /**
     * 同步逻辑说明：
     *        1. 获取钉钉所有部门列表
     *        2. 获取钉钉所有用户列表
     *        3. 同步建科云组织架构 - 维护建科云与钉钉关系
     *        4. 同步建科云用户信息 - 维护建科云与钉钉关系
     */
    @Override
    public void sync() {


    }
}
