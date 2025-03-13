package vip.xiaonuo.dev.modular.job.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.sys.api.SysOrgApi;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
public class IcManageTaskRunner implements CommonTimerTaskRunner {

    @Value("${icManage.annualInformDeptIds}")
    private String annualInformDeptIds;

    @Resource
    IDingDingService dingDingService;

    @Resource
    SysOrgApi sysOrgApi;

    /**
     * 每年4月1日的08:30、5月10日的08:30发送提醒
     * 由于cron表达式的限制,0 30 8 1,10 4,5 ?,该表达式会触发 4月1日、4月10日、5月1日、5月10日 四个时间点，而非仅4月1日和5月10日
     * 同时考虑手动触发的情况,加个判断,本job触发时,判断当前时间,如果是4月10日、5月1日,则不发送通知
     */
    @Override
    public void action() {
        try{
            log.info("工商信息年检钉钉提醒定时任务触发--Time:"+ DateUtil.now());
            if(checkDate()){
                log.info("工商信息年检钉钉提醒定时任务--当前时间不符,不发送提醒");
                return;
            }
            List<String> informUserIdList = getInformUserId();
//            List<String> informUserIdList = new ArrayList<>();
//            informUserIdList.add("1815553163921506305");
            if(CollUtil.isEmpty(informUserIdList)){
                log.info("工商信息年检钉钉提醒定时任务--需要发送提醒的用户列表为空");
                return;
            }
            dingDingService.batchSend("请您在今年6月30日前完成对集团各公司的工商信息年检任务！如已完成请忽略。",informUserIdList);
        }catch (Exception e){
            log.error("工商信息年检钉钉提醒定时任务错误：{}", e.getMessage());
        }
    }

    // 获取工商信息年检,需要通知的人员Id
    private List<String> getInformUserId(){
        List<String> userIdList = new ArrayList<>();
        if(ObjectUtil.isEmpty(annualInformDeptIds)){
            log.error("工商信息年检发送提醒,未读取到部门Id配置!");
        }
        String[] deptIdList = annualInformDeptIds.split(",");
        for(String orgId :deptIdList){
            String supervisorIdByOrgId = sysOrgApi.getSupervisorIdByOrgId(orgId);
            if(ObjectUtil.isNotEmpty(supervisorIdByOrgId)){
                userIdList.add(supervisorIdByOrgId);
            }
        }
        return userIdList.stream().distinct().collect(Collectors.toList());
    }

    // 判断当前时间是否在 4月10日或5月1日的8:00-9:00之间
    public boolean checkDate() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();   // 月份（1-12）
        int day = now.getDayOfMonth();     // 日期（1-31）
        int hour = now.getHour();          // 小时（0-23）
        return (month == 4 && day == 10 && hour == 8) ||
                (month == 5 && day == 1 && hour == 8);
    }

}
