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
package vip.xiaonuo.flw.core.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.api.DevSealApplyApi;
import vip.xiaonuo.flw.core.sealApply.SealApplyVo;

import javax.annotation.Resource;

/**
 * 云玺印章审核完成执行监听器
 *
 *@Author hxw
 *@Date 2024/5/24 11:00
 */
@Slf4j
@Component
public class FlwYxSealMessageExecutionListener implements ExecutionListener {

    @Resource
    private DevSealApplyApi devSealApplyApi;

    @Override
    public void notify(DelegateExecution execution) {
        try {
            log.info("shen>>> 执行监听器：{}事件触发，流程id为：{}，当前活动节点为：{}",
                    execution.getEventName(), execution.getProcessInstanceId(), execution.getCurrentActivityName());
            //获取流程表单数据
            Object dataJson = execution.getVariable("initiatorDataJson");
            //获取程数据--流程id
            String id = String.valueOf(JSONUtil.parseObj(dataJson).get("id"));
            //获取程数据--设备标识
            String uuid = String.valueOf(JSONUtil.parseObj(dataJson).get("sealId"));
            //获取流程数据--每份用印次数
            String contractCopiesCount = String.valueOf(JSONUtil.parseObj(dataJson).get("applyCount"));
            JSONObject jsonObject = devSealApplyApi.getSealApplyFieldList(id, uuid, contractCopiesCount);
            Object obj = jsonObject.get("data");
            //得到授权码
            SealApplyVo sealApplyVo = BeanUtil.toBean(obj, SealApplyVo.class);
            //将授权码赋值到流程initiatorDataJson中
            execution.setVariable("initiatorDataJson",JSONUtil.parseObj(execution.getVariable("initiatorDataJson")).set("safeCode", sealApplyVo.getAuthorizationCode()));
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", FlwNewSealMessageExecutionListener.class.getName(), e.getMessage());
        }
    }
}
