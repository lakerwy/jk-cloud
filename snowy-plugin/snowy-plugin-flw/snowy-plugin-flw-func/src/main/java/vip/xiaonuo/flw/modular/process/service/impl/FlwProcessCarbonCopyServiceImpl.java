package vip.xiaonuo.flw.modular.process.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.dev.api.DevMessageApi;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.process.entity.FlyCarbonCopy;
import vip.xiaonuo.flw.modular.process.service.FlwProcessCarbonCopyService;
import vip.xiaonuo.flw.modular.process.service.FlwProcessService;
import vip.xiaonuo.flw.modular.util.HandleCarbonUtil;

import java.util.Map;

/**
 *  抄送流程Service接口实现类
 *
 * @Author hxw
 * @Date 2024/4/24 17:32
 */

@Slf4j
@Service
public class FlwProcessCarbonCopyServiceImpl implements FlwProcessCarbonCopyService {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void getCarbonCopy(FlyCarbonCopy bizCarbonCopy) {
        //新增待阅流程数据
        SpringUtil.getBean(FlwProcessService.class).doCopy(bizCarbonCopy.getProcessInstanceId(), bizCarbonCopy.getUserList());
        //通过流程id查询流程实例信息
        Map<String, Object> map = NodeRuntimeUtil.getMap(bizCarbonCopy.getProcessInstanceId());
        //map数据转换
        Map<String, String> taskMap = HandleCarbonUtil.convertMap(map);
        taskMap.put("noticeType","CARBON");
        DevMessageApi devMessageApi = SpringUtil.getBean(DevMessageApi.class);
        try{
            // 发送抄送通知
            devMessageApi.sendMessage(bizCarbonCopy.getUserList(), "流程抄送分类",bizCarbonCopy.getProcessInstanceId(),taskMap);
            log.info("给{}发送内容为：流程抄送分类的MSG", bizCarbonCopy.getUserList());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
