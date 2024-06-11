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

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.flw.core.deploy.DeployProperties;
import vip.xiaonuo.flw.modular.system.entity.ExtSystem;
import vip.xiaonuo.flw.modular.system.service.ExtSystemService;

import javax.annotation.Resource;

/**
 * 采购计划申请审核完成执行监听器
 *
 *@Author hxw
 *@Date 2024/5/28 11:50
 */
@Slf4j
@Component
public class FlwPurchasePlanExecutionListener implements ExecutionListener {

    @Resource
    private DeployProperties deployProperties;

    @Resource
    private ExtSystemService extSystemService;

    /** 流程审核通过标志 */
    private static final String STATUS_FLAG = "1";
    private static final String URL_FLAG = "epc/purchasePlan/edit";

    @Override
    public void notify(DelegateExecution execution) {
        try {
            log.info("shen>>> 执行监听器：{}事件触发，流程id为：{}，当前活动节点为：{}",
                    execution.getEventName(), execution.getProcessInstanceId(), execution.getCurrentActivityName());
            //获取流程表单数据
            Object dataJson = execution.getVariable("initiatorDataJson");

            //请求路径
            String url = deployProperties.getPrefixUrl() + URL_FLAG;
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("id", String.valueOf(JSONUtil.parseObj(dataJson).get("planId"))); //流程数据--表单id
            jsonObject.set("sn", String.valueOf(JSONUtil.parseObj(dataJson).get("sn"))); //流程数据--流水号
            jsonObject.set("status", STATUS_FLAG); //已通过
            jsonObject.set("modelId", String.valueOf(JSONUtil.parseObj(dataJson).get("modelId"))); //流程数据--模型Id
            jsonObject.set("processInstanceId", execution.getProcessInstanceId()); //流程数据--流程实例id

            //调用epc系统 回写当前数据状态值
            String post = HttpUtil.post(url, jsonObject.toString());

            //返回成功则去记录系统表
            if("200".equals(String.valueOf(JSONUtil.parseObj(post).get("code")))){
                ///修改系统表--审批状态改成已通过
                extSystemService.update(new LambdaUpdateWrapper<ExtSystem>().eq(ExtSystem::getFromId, String.valueOf(JSONUtil.parseObj(dataJson).get("planId")))
                        .set(ExtSystem::getFromExtJson, JSONUtil.toJsonStr(JSONUtil.parseObj(dataJson).set("status", STATUS_FLAG).set("processInstanceId", execution.getProcessInstanceId())))
                        .set(ExtSystem::getSystemName,"EPC系统招采计划审批通过")
                        .set(ExtSystem::getUpdateTime, DateUtil.date())
                        .set(ExtSystem::getUpdateUser, StpLoginUserUtil.getLoginUser().getId()));
            }
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", FlwNewSealMessageExecutionListener.class.getName(), e.getMessage());
        }
    }
}
