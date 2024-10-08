package vip.xiaonuo.sys.modular.dingtalk.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.modular.dingtalk.EventHandlingStrategy;
import vip.xiaonuo.sys.modular.dingtalk.config.DingSyncProperties;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:33
 */
@Component
@Slf4j
public class OrgRemoveEventStrategy implements EventHandlingStrategy {
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    DingSyncProperties dingSyncProperties;
    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {
        if(!dingSyncProperties.getIsSync()){
            return;
        }
        log.info("【事件订阅-通讯录企业部门删除】 -- start 钉钉参数 --"+ JSONUtil.toJsonStr(event));
        JSONArray dingDeptIds = event.getData().getJSONArray("deptId");
        List<SysOrg> removeList = new ArrayList<>();
        if(dingDeptIds.size()>0) {
            removeList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().in(SysOrg::getDingDeptId,dingDeptIds));
        }
        if(CollUtil.isNotEmpty(removeList)){
            removeList.forEach(item-> {
                item.setDeleteFlag("DELETED");
                item.setDingSyncMemo(DateUtil.now()+" 操作：【事件订阅-通讯录企业部门删除】");
                item.setUpdateTime(new Date());
            });
            sysOrgService.updateBatchById(removeList);
        }
        log.info("【事件订阅-通讯录企业部门删除】 -- end --"+ JSONUtil.toJsonStr(removeList));
    }
}
