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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.dev.feign.DevFeign;
import vip.xiaonuo.dev.modular.dev.DevApiProvider;
import vip.xiaonuo.dev.modular.dingDingSchedule.util.StartDingDingScheduleCore;

import javax.annotation.Resource;

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

    private final DevApiProvider devApiProvider;
    @Resource
    StartDingDingScheduleCore startDingDingScheduleCore;

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
    public String addScheduleForWork(@RequestParam String appKey,@RequestParam String appSecret,@RequestParam String id){
        return startDingDingScheduleCore.taskAddSchedule(id,appKey,appSecret);
    }

    @Override
    @RequestMapping("/feign/dev/deleteScheduleForWork")
    public void deleteScheduleForWork(@RequestParam("appKey") String appKey,@RequestParam("appSecret") String appSecret,@RequestParam("scheduleId") String scheduleId,@RequestParam("dutyUserId") String dutyUserId){
        try{
            startDingDingScheduleCore.deleteScheduleForUserId(appKey,appSecret,scheduleId,dutyUserId);
        }catch (Exception e){
            log.error("删除日程失败！scheduleId："+scheduleId+",dutyUserId:"+dutyUserId);
        }
    }
}
