package vip.xiaonuo.sys.modular.dingtalk.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.enums.SysUserStatusEnum;
import vip.xiaonuo.dev.modular.dingThirdData.entity.AuthThirdUser;
import vip.xiaonuo.dev.modular.dingThirdData.service.AuthThirdUserService;
import vip.xiaonuo.sys.modular.dingtalk.EventHandlingStrategy;
import vip.xiaonuo.sys.modular.dingtalk.config.DingSyncProperties;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.enums.AuthThirdCategoryEnum;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/14 17:32
 */
@Component
@Slf4j
public class UserLeaveEventStrategy implements EventHandlingStrategy {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private AuthThirdUserService authThirdUserService;
    @Resource
    DingSyncProperties dingSyncProperties;
    @Override
    public void handleEvent(GenericOpenDingTalkEvent event) {
        if(!dingSyncProperties.getIsSync()){
            return;
        }
        log.info("【事件订阅-通讯录用户离职】 -- start 钉钉参数 --"+ JSONUtil.toJsonStr(event));
        JSONArray dingUserIds = event.getData().getJSONArray("userId");
        //更新用户
        List<SysUser> updateList = new ArrayList<>();
        //循环修改
        if(dingUserIds.size()>0) {
            //找对应关系
            List<AuthThirdUser> thirdUserList = authThirdUserService.list(new LambdaQueryWrapper<AuthThirdUser>().eq(AuthThirdUser::getCategory, AuthThirdCategoryEnum.DingDing.getValue()).in(AuthThirdUser::getThirdId,dingUserIds));
            List<String> userIds = thirdUserList.stream().map(x->x.getUserId()).collect(Collectors.toList());
            log.info("【事件订阅-通讯录用户离职】 -- 涉及 系统用户Id --"+ JSONUtil.toJsonStr(userIds));
            if(CollUtil.isEmpty(userIds)) return;
            updateList = sysUserService.list(new LambdaQueryWrapper<SysUser>().in(SysUser::getId,userIds));
            updateList.forEach(user->{
                user.setUserStatus(SysUserStatusEnum.DISABLED.getValue());
            });
        }
        //更新用户
        if(CollUtil.isNotEmpty(updateList)){
            sysUserService.updateBatchById(updateList);
        }
        log.info("【事件订阅-通讯录用户离职】 -- end --"+ JSONUtil.toJsonStr(updateList));
    }
}
