/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.web.core.provider.dev.dev;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.dev.feign.DevFeign;
import vip.xiaonuo.dev.modular.dev.DevApiProvider;
import vip.xiaonuo.dev.modular.dingDingSchedule.param.DingDingScheduleParam;
import vip.xiaonuo.dev.modular.dingDingSchedule.util.StartDingDingScheduleCore;
import vip.xiaonuo.dev.modular.dingDingSchedule.vo.DingDingScheduleVo;
import vip.xiaonuo.dev.modular.job.task.CommonHandleUtil;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 开发工具模块综合Feign提供者
 *
 * @author dongxiayu
 * @date 2022/11/22 22:50
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Configuration
public class DevFeignProvider implements DevFeign {

    @Value("${dingtalkstream.clientId}")
    private String appKey;
    @Value("${dingtalkstream.clientSecret}")
    private String appSecret;

    private final DevApiProvider devApiProvider;
    @Resource
    StartDingDingScheduleCore startDingDingScheduleCore;
    @Resource
    SysUserService sysUserService;

    /**
     * 初始化ID类型的租户开发工具模块数据
     *
     * @param tenId
     * @param tenName
     * @author dongxiayu
     * @date 2022/9/26 14:25
     */
    @Override
    @RequestMapping("/feign/dev/initTenDataForCategoryId")
    public void initTenDataForCategoryId(@RequestParam("tenId") String tenId, @RequestParam("tenName") String tenName) {
        devApiProvider.initTenDataForCategoryId(tenId,tenName);
    }

    /**
     * 删除ID类型的租户开发工具模块数据
     *
     * @param tenId
     * @author dongxiayu
     * @date 2022/9/26 14:25
     */
    @Override
    @RequestMapping("/feign/dev/removeTenDataForCategoryId")
    public void removeTenDataForCategoryId(@RequestParam("tenId") String tenId) {
        devApiProvider.removeTenDataForCategoryId(tenId);
    }

    @Override
    @RequestMapping("/feign/dev/addScheduleForWork")
    public String addScheduleForWork(@RequestParam String id){
        String scheduleId = "";
        if(StringUtils.isEmpty(id)){return scheduleId;}
        try{
            String sql = "SELECT ID,TASK_NAME,TASK_REMARK,TASK_START_TIME,TASK_END_TIME,BELONGER,CREATE_USER,SN FROM BIZ_TASK_INFO " +
                    " WHERE ID = '"+id+"'";
            Map<String,Object> map= CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
            if(ObjectUtil.isEmpty(map)){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("BELONGER"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("TASK_START_TIME"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("TASK_END_TIME"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("TASK_NAME"))){return scheduleId;}
            if(ObjectUtil.isEmpty(map.get("CREATE_USER"))){return scheduleId;}

            List<DingDingScheduleParam> dingDingScheduleParamList = new ArrayList<>();
            SysUser sysUser = sysUserService.getById(map.get("BELONGER").toString());
            DingDingScheduleParam dingDingScheduleParam = new DingDingScheduleParam();
            dingDingScheduleParam.setUserId(sysUser.getId());
            dingDingScheduleParam.setUserName(sysUser.getName());
            dingDingScheduleParam.setPhone(sysUser.getAccount());
            dingDingScheduleParamList.add(dingDingScheduleParam);

            DingDingScheduleVo dingDingScheduleVo = new DingDingScheduleVo();
            dingDingScheduleVo.setDingDingScheduleParamList(dingDingScheduleParamList);
            dingDingScheduleVo.setStartTime(map.get("TASK_START_TIME").toString());
            dingDingScheduleVo.setEndTime(map.get("TASK_END_TIME").toString());
            dingDingScheduleVo.setSummary(map.get("TASK_NAME").toString());
            dingDingScheduleVo.setDescription(ObjectUtil.isNotEmpty(map.get("TASK_REMARK")) ? map.get("TASK_REMARK").toString() : "");
            dingDingScheduleVo.setId(map.get("ID").toString());
            dingDingScheduleVo.setCreateUser(StpUtil.getLoginIdAsString());
            scheduleId =  startDingDingScheduleCore.addDingDingSchedule(dingDingScheduleVo, appKey, appSecret);
        }catch (Exception e){
            log.error("任务创建日程失败！e:"+e.getMessage());
        }
        return scheduleId;
    }

    @Override
    @RequestMapping("/feign/dev/deleteScheduleForWork")
    public void deleteScheduleForWork(@RequestParam("scheduleId") String scheduleId,@RequestParam String dutyUserId){
        try{
            startDingDingScheduleCore.deleteScheduleForUserId(appKey,appSecret,scheduleId,dutyUserId);
        }catch (Exception e){
            log.error("删除日程失败！scheduleId："+scheduleId+",dutyUserId:"+dutyUserId);
        }
    }
}
