package vip.xiaonuo.sys.modular.dingtalk.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import com.fhs.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.util.CommonDingDingUtil;
import vip.xiaonuo.dev.modular.message.config.DingTalkProperties;
import vip.xiaonuo.sys.modular.dingtalk.EventHandlingStrategy;
import vip.xiaonuo.sys.modular.dingtalk.config.DingSyncProperties;
import vip.xiaonuo.sys.modular.dingtalk.utils.SyncUtils;
import vip.xiaonuo.sys.modular.org.entity.SysOrg;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:33
 */
@Component
@Slf4j
public class OrgUpdateEventStrategy implements EventHandlingStrategy {
    @Resource
    DingTalkProperties dingTalkProperties;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    DingSyncProperties dingSyncProperties;
    @Resource
    private SysUserService sysUserService;
    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {
        if(!dingSyncProperties.getIsSync()){
            return;
        }
        log.info("【事件订阅-通讯录企业部门修改】 -- start 钉钉参数 --"+ JSONUtil.toJsonStr(event));
        JSONArray dingDeptIds = event.getData().getJSONArray("deptId");
        //钉钉token
        String access_Token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret());
        //系统组织架构信息【历史数据】
        List<SysOrg> sysOrgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().isNotNull(SysOrg::getDingDeptId));
        //系统用户信息（携带钉钉id）
        List<SysUser> sysUserList = sysUserService.getDingSysUser();
        //循环修改部门
        List<SysOrg> updateList = new ArrayList<>();
        if(dingDeptIds.size()>0){
            updateList = sysOrgList.stream().filter(x->dingDeptIds.contains(x.getDingDeptId())).collect(Collectors.toList());
            updateList.forEach(item->{
                OapiV2DepartmentGetResponse.DeptGetResponse dingDept = CommonDingDingUtil.getDeptById(access_Token, item.getDingDeptId());
                item.setName(dingDept.getName());
                SysOrg tmpSysOrg = sysOrgList.stream().filter(x -> x.getDingDeptId().equals(dingDept.getParentId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpSysOrg)) item.setParentId(tmpSysOrg.getId());
                item.setDingDirectorId(JsonUtils.object2json(dingDept.getDeptManagerUseridList()));
                item.setDirectorId(SyncUtils.getManageId(sysUserList,dingDept.getDeptManagerUseridList()));
                item.setDingSyncMemo(DateUtil.now()+" 操作：【事件订阅-通讯录企业部门修改】");
                item.setUpdateTime(new Date());
            });
        }
        if(CollUtil.isNotEmpty(updateList)){
            sysOrgService.updateBatchById(updateList);
        }
        log.info("【事件订阅-通讯录企业部门修改】 -- end  --"+ JSONUtil.toJsonStr(updateList));
    }
}
