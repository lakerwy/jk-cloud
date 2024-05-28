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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.api.DevSealApi;
import vip.xiaonuo.flw.core.seal.SealParam;
import vip.xiaonuo.flw.core.seal.SealVo;
import vip.xiaonuo.flw.core.seal.UsedSealParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 群杰印章审核完成执行监听器
 *
 *@Author hxw
 *@Date 2024/5/22 17:02
 */
@Slf4j
@Component
public class FlwNewSealMessageExecutionListener implements ExecutionListener {

    @Resource
    private DevSealApi devSealApi;

    @Override
    public void notify(DelegateExecution execution) {
        try {
            log.info("shen>>> 执行监听器：{}事件触发，流程id为：{}，当前活动节点为：{}",
                    execution.getEventName(), execution.getProcessInstanceId(), execution.getCurrentActivityName());
            //获取流程表单数据
            Object dataJson = execution.getVariable("initiatorDataJson");
            //获取程数据--印章名称
            String sealName = String.valueOf(JSONUtil.parseObj(dataJson).get("sealName"));
            if(ObjectUtil.isNotEmpty(sealName)) {
                //组装数据 群杰需要的数据格式
                SealParam sealParam = new SealParam();
                sealParam.setTitle("admin用户印章申请");
                UsedSealParam usedSealParam = new UsedSealParam();
                usedSealParam.setSealerUserName("admin");//目前没有同步群杰组织架构,默认admin账号
                usedSealParam.setSealId(String.valueOf(JSONUtil.parseObj(dataJson).get("sealId")));
                //获取流程数据--每份用印次数  页面用印次数字段不一样 兼容两个值  如果都没有拿到值，则默认5份
                Object contractCopiesCount = ObjectUtil.isEmpty(JSONUtil.parseObj(dataJson).get("applyCount")) ? JSONUtil.parseObj(dataJson).get("printNum") : JSONUtil.parseObj(dataJson).get("applyCount");
                if (ObjectUtil.isEmpty(contractCopiesCount)){
                    contractCopiesCount = 5;
                }
                usedSealParam.setApplyCount(String.valueOf(contractCopiesCount));
                sealParam.setApplyerUserName("admin");//目前没有同步群杰组织架构,默认admin账号
                //sealParam.setCallBackUrl("www.baidu.com");
                List list = new ArrayList<>();
                list.add(usedSealParam);
                sealParam.setUsedSealParamList(list);

                //获取授权信息
                Object obj = JSONObject.parseObject(devSealApi.getSealFieldList(JSONObject.toJSONString(sealParam))).get("data");
                //得到授权码
                SealVo sealVo = BeanUtil.toBean(obj, SealVo.class);
                //将授权码赋值到流程initiatorDataJson中
                execution.setVariable("initiatorDataJson", JSONUtil.parseObj(execution.getVariable("initiatorDataJson")).set("safeCode", sealVo.getSafeCode()));
            }
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", FlwNewSealMessageExecutionListener.class.getName(), e.getMessage());
        }
    }
}
