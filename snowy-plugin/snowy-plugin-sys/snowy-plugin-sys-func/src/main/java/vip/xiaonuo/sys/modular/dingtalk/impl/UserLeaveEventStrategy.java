package vip.xiaonuo.sys.modular.dingtalk.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.enums.SysUserStatusEnum;
import vip.xiaonuo.sys.modular.dingtalk.EventHandlingStrategy;
import vip.xiaonuo.sys.modular.dingtalk.config.DingSyncProperties;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            updateList = sysUserService.getDingSysUserByIds(dingUserIds.toList(String.class));
            if(CollUtil.isEmpty(updateList)) return;
            updateList.forEach(user->{
                user.setUserStatus(SysUserStatusEnum.DISABLED.getValue());
                user.setDingSyncMemo(DateUtil.now()+" 操作：【事件订阅-通讯录用户离职】");
                user.setUpdateTime(new Date());
            });
        }
        //更新用户
        if(CollUtil.isNotEmpty(updateList)){
            sysUserService.updateBatchById(updateList);
        }
        log.info("【事件订阅-通讯录用户离职】 -- end --"+ JSONUtil.toJsonStr(updateList));
    }
}
