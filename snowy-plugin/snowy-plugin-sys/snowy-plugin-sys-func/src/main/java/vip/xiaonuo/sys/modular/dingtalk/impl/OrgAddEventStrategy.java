package vip.xiaonuo.sys.modular.dingtalk.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.dingtalk.EventHandlingStrategy;
import vip.xiaonuo.sys.modular.dingtalk.config.DingSyncProperties;
import vip.xiaonuo.sys.modular.dingtalk.utils.SyncUtils;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:32
 */
@Component
@Slf4j
public class OrgAddEventStrategy implements EventHandlingStrategy {
    @Resource
    DingTalkProperties dingTalkProperties;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    DingSyncProperties dingSyncProperties;
    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {
        if(!dingSyncProperties.getIsSync()){
            return;
        }
        log.info("【事件订阅-通讯录企业部门创建】 -- start 钉钉参数 --"+ JSONUtil.toJsonStr(event));
        JSONArray dingDeptIds = event.getData().getJSONArray("deptId");
        //钉钉token
        String access_Token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret());
        //系统组织架构信息【历史数据】
        List<SysOrg> sysOrgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().isNotNull(SysOrg::getDingDeptId));
        //循环添加部门
        List<SysOrg> addList = new ArrayList<>();
        for (int i = 0; i < dingDeptIds.size(); i++) {
            Long dingDeptId = dingDeptIds.getLong(i);
            OapiV2DepartmentGetResponse.DeptGetResponse dingDept = CommonDingDingUtil.getDeptById(access_Token, dingDeptId);
            //新增SysUser
            SysOrg tmpAddOrg = SyncUtils.addOrg(dingDept.getName(),dingDept.getDeptId(),dingDept.getParentId());
            sysOrgList.add(tmpAddOrg);  //资源，避免同时推送多个部门，相互有是上下级
            addList.add(tmpAddOrg);
        }
        //赋值parentId
        if(CollUtil.isNotEmpty(addList)){
            addList.forEach(item->{
                SysOrg tmpSysOrg = sysOrgList.stream().filter(x -> x.getDingDeptId().equals(item.getDingParentId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpSysOrg)) item.setParentId(tmpSysOrg.getId());
            });
            sysOrgService.saveBatch(addList);
        }
        log.info("【事件订阅-通讯录企业部门创建】 -- end  --"+ JSONUtil.toJsonStr(addList));
    }
}
