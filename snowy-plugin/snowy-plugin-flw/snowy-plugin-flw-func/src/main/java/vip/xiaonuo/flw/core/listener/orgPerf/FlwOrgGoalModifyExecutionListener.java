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
package vip.xiaonuo.flw.core.listener.orgPerf;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.api.BizExtApi;
import vip.xiaonuo.common.exception.CommonException;

import javax.annotation.Resource;

/**
 * 组织绩效考核-目标调整-流程完成执行监听器
 *
 *@Author pzh
 *@Date 2025/06/25 11:25
 */
@Slf4j
@Component
public class FlwOrgGoalModifyExecutionListener implements ExecutionListener {

    @Resource
    private BizExtApi bizExtApi;

    @Override
    public void notify(DelegateExecution execution) {
        try {
            log.info(">>> 执行监听器：{}事件触发，流程id为：{}，当前活动节点为：{}",
                    execution.getEventName(), execution.getProcessInstanceId(), execution.getCurrentActivityName());
            Object dataJson = execution.getVariable("initiatorDataJson");
            JSONObject jsonObject = JSONUtil.parseObj(dataJson);
            // 更新-组织绩效考核-目标登记
            bizExtApi.updateOrgGoal(jsonObject);
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", FlwOrgGoalModifyExecutionListener.class.getName(), e.getMessage());
        }
    }
}
