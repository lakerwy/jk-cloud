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
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;
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
public class UserAddEventStrategy implements EventHandlingStrategy {

    @Resource
    DingTalkProperties dingTalkProperties;
    @Resource
    private SysOrgService sysOrgService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private AuthThirdUserService authThirdUserService;
    @Resource
    DingSyncProperties dingSyncProperties;

    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {
        log.info("【事件订阅-企业新增员工】 -- start 钉钉参数 --"+ JSONUtil.toJsonStr(event));
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

        //循环增员
        List<SysUser> addUserList = new ArrayList<>();
        List<AuthThirdUser> addThirdUserList = new ArrayList<>();
        for (int i = 0; i < dingUserIds.size(); i++) {
            String dingUserId = dingUserIds.getString(i);
            OapiV2UserGetResponse.UserGetResponse dingUser = CommonDingDingUtil.getUserById(access_Token, dingUserId);
            //新增SysUser
            SysUser tmpAddUser = SyncUtils.addUser(dingUserId,dingUser.getManagerUserid(),dingUser.getMobile(),dingUser.getName(),dingUser.getMobile(),sysOrgList,dingUser.getDeptIdList());
            addUserList.add(tmpAddUser);
            //添加第三方库
            AuthThirdUser tmpAddThirdUser = SyncUtils.addThirdUser(dingUser.getName(),tmpAddUser.getId(),dingUser.getUserid(),dingUser.getUnionid());
            addThirdUserList.add(tmpAddThirdUser);
        }
        sysUserList.addAll(addUserList);  //避免新增的员工中有主管

        //处理数据库
        if(CollUtil.isNotEmpty(addUserList)){
            addUserList.forEach(user->{
                user.setDingSyncMemo(DateUtil.now()+" 操作：【事件订阅-企业新增员工】");
                user.setUpdateTime(new Date());
                try {
                    if (ObjectUtil.isNotEmpty(user.getDingDirectorId())) {
                        SysUser tmpSysUser = sysUserList.stream().filter(x -> user.getDingDirectorId().equals(x.getDingUserId())).findFirst().orElse(null);
                        if(ObjectUtil.isNotEmpty(tmpSysUser)) user.setDirectorId(tmpSysUser.getId());
                    }
                    sysUserService.addAndGrantRole2(user);
                }catch (Exception e){
                    log.error("【事件订阅-企业新增员工】 -- 操作数据库 异常 --"+e.getMessage());
                }
            });
        }
        if(CollUtil.isNotEmpty(addThirdUserList)){
            authThirdUserService.saveBatch(addThirdUserList);
        }
        log.info("【事件订阅-企业新增员工】 -- end ");
    }
}
