package vip.xiaonuo.sys.modular.dingtalk.service.impl;

import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.dingtalk.service.DingDingSyncService;

import javax.annotation.Resource;

/**
 * 钉钉 - 数据 - 同步  服务实现类
 */

@Service
public class DingDingSyncServiceImpl implements DingDingSyncService {

    @Resource
    private DingTalkProperties dingTalkProperties;


    /**
     * 同步逻辑说明 【暂停】：
     *        1. 获取钉钉所有部门列表
     *        2. 获取钉钉所有用户列表
     *        3. 同步建科云组织架构 - 维护建科云与钉钉关系
     *        4. 同步建科云用户信息 - 维护建科云与钉钉关系
     */
    @Override
    public void sync() {
        //1. 获取钉钉access_token
       String access_token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(),dingTalkProperties.getAppSecret());

        //2. 获取钉钉部门
        OapiV2DepartmentListsubResponse listSub = CommonDingDingUtil.getListSub(access_token, 1L);


    }



}
