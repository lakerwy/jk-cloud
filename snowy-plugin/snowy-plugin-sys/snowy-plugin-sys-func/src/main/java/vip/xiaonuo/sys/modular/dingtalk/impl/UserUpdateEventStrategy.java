package vip.xiaonuo.sys.modular.dingtalk.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.response.OapiV2UserGetResponse;
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
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:30
 */
@Component
@Slf4j
public class UserUpdateEventStrategy implements EventHandlingStrategy {

    @Resource
    DingTalkProperties dingTalkProperties;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    DingSyncProperties dingSyncProperties;

    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {
        log.info("【事件订阅-通讯录用户更改】 -- start 钉钉参数 --"+ JSONUtil.toJsonStr(event));
        if(!dingSyncProperties.getIsSync()){
            return;
        }
        JSONArray dingUserIds = event.getData().getJSONArray("userId");
        //钉钉token
        String access_Token = CommonDingDingUtil.getAccessToken(dingTalkProperties.getAppKey(), dingTalkProperties.getAppSecret());
        //系统组织架构信息
        List<SysOrg> sysOrgList = sysOrgService.list(new LambdaQueryWrapper<SysOrg>().isNotNull(SysOrg::getDingDeptId));
        //系统用户信息（携带钉钉id）
        List<SysUser> sysUserList = sysUserService.getDingSysUser();
        //待更新用户列表
        List<SysUser> updateList = new ArrayList<>();
        //循环修改
        if(dingUserIds.size()>0) {
            //找对应关系
            updateList = sysUserService.getDingSysUserByIds(dingUserIds.toList(String.class));
            if(CollUtil.isEmpty(updateList)) return;
            updateList.forEach(user->{
                //更新信息
                OapiV2UserGetResponse.UserGetResponse dingUser = CommonDingDingUtil.getUserById(access_Token, user.getDingUserId());
                user.setOrgId(SyncUtils.getOrgId(sysOrgList,dingUser.getDeptIdList()));
                user.setName(dingUser.getName());
                user.setEmail(dingUser.getEmail());
                user.setDingDirectorId(dingUser.getManagerUserid());
                user.setPhone(dingUser.getMobile());
                //找到直属主管 - 对应建科云用户id
                if(ObjectUtil.isNotEmpty(dingUser.getManagerUserid())) {
                    SysUser tmpSysUser = sysUserList.stream().filter(x -> dingUser.getManagerUserid().equals(x.getDingUserId())).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(tmpSysUser)) user.setDirectorId(tmpSysUser.getId());
                }
                user.setDingSyncMemo(DateUtil.now()+" 操作：【事件订阅-通讯录用户更改】");
                user.setUpdateTime(new Date());
            });
        }
        //更新用户
        if(CollUtil.isNotEmpty(updateList)){
            sysUserService.updateBatchById(updateList);
        }
        log.info("【事件订阅-通讯录用户更改】 -- end --");
    }
}
