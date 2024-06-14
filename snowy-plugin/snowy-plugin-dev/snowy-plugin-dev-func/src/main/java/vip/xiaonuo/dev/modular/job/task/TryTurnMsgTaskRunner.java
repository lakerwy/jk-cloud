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
package vip.xiaonuo.dev.modular.job.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 试用期人员转正-人资通知
 *
 * 规则：需要在转正时间前15天 钉钉发送试用期转正考核提醒至人资 提醒人资填写发起流程
 * tryTurn.sendMsgPhone web + flw
 **/
@Slf4j
@Component
public class TryTurnMsgTaskRunner implements CommonTimerTaskRunner {

    @Value("${tryTurn.sendMsgPhone}")
    private String sendMsgPhone;

    @Resource
    IDingDingService dingDingService;
    @Override
    public void action() {
        try {
            /* 查询15天后转正的人员 */
            String sql = "SELECT b.`NAME`,a.SN FROM HR_TRY_DUTY a JOIN PERSONNEL b ON a.PERSONNEL_ID=b.ID WHERE a.DELETE_FLAG='NOT_DELETE' AND a.`STATUS`='1' AND b.POSITIVE_TIME IS NOT NULL AND DATEDIFF(b.POSITIVE_TIME,NOW()) = 15";
            log.info("15天后转正人员查询----"+sql);
            List<Map<String, Object>> mapList = CommonHandleUtil.handleSqlQuery2(sql, Boolean.TRUE);
            if(CollUtil.isNotEmpty(mapList)){
                List<String> nameList = mapList.stream().map(result -> result.get("NAME").toString()).collect(Collectors.toList());
                //拼接消息体
                String msg = "【转正提醒】"+StrUtil.join("，",nameList);
                dingDingService.asyncsendV2(msg,sendMsgPhone);
                log.info("试用期人员转正前通知提醒......"+msg);
            }
        }catch (Exception e){
            log.error("试用期人员转正前通知提醒......"+e.getMessage());
        }
    }
}
