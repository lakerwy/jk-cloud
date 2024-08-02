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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.api.IDingDingService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 借支单 - 预计还款日 - 未归还 - 逾期通知
 *
 * 预计归还时间：在借支单列表中记录还款状态，如果在预计归还时间前10天未，该借支单的状态为”待归还“，则分别给发起人与人事发钉钉提醒，如果到归还日期的次日还是”待归还“，则再给人事发钉钉提醒，提醒内容带入员工姓名与借支单流水号：
 *
 * 例如，张三预期在2024-08-01归还，2024-07-24 08:30:00 前借支单的状态为”待归还“，则在该时间点发送钉钉提醒消息至 发起人 与 人事谢春华、邱红玲，给发起人的消息内容为“建科云| 您还有一笔借支单JZ20240712001待处理，若归还，请忽略”，给人事的消息内容为“建科云| 员工张三还有一笔借支单JZ20240712001待处理，若归还，请忽略”
 * 如果发起人在2024-08-02 08:30:00前借支单的状态依旧为”待归还“，则在该时间点只给人事谢春华、邱红玲发送钉钉消息，消息内容为“建科云| 员工张三有一笔借支单JZ20240712001已逾期，若归还，请忽略”。
 *
 * debit.sendMsgPhone web + flw
 **/
@Slf4j
@Component
public class DebitOverDueMsgTaskRunner implements CommonTimerTaskRunner {

    @Value("${debit.hrPhone}")
    private String hrPhone;

    @Resource
    IDingDingService dingDingService;
    @Override
    public void action() {
        try {
            /* 借支单 - 预计还款日（次日检查） - 未归还 - 逾期通知 */
            String sql = "SELECT ID,FIN_CODE,INITOR_ID,INITOR,RETURN_DATE FROM BIZ_DEBIT_APPLY WHERE DELETE_FLAG='NOT_DELETE' AND `STATUS`='1' AND DEBIT_STATUS = '0' AND RETURN_DATE IS NOT NULL AND RETURN_DATE = DATE_SUB(DATE(NOW()), INTERVAL 1 DAY)";
            log.info("借支单 - 预计还款日 - 未归还 - 逾期通知 - 数据查询----"+sql);
            List<Map<String, Object>> mapList = CommonHandleUtil.handleSqlQuery2(sql, Boolean.FALSE);
            // 人事手机号配置
            String[] hrPhones = hrPhone.split(",");
            if(CollUtil.isNotEmpty(mapList) && hrPhones.length > 0){
                //发给人事
                mapList.forEach(item->{
                    String initor = item.get("INITOR").toString();
                    String finCode = item.get("FIN_CODE").toString();
                    String msg = "建科云|员工" + initor + "有一笔借支单" + finCode + "已逾期，若归还，请忽略。";
                    for (String phone : hrPhones) {
                        dingDingService.asyncsendV2(msg,phone);
                        log.info("【给人事:" + phone + "】借支单 - 预计还款日 - 未归还 - 逾期通知......"+ msg);
                    }
                });
            }
        }catch (Exception e){
            log.error("借支单 - 预计还款日 - 未归还 - 逾期通知......"+e.getMessage());
        }
    }
}
