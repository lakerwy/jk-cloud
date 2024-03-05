package vip.xiaonuo.sys.modular.hr.conference.DingDingRec;

import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.open.app.api.GenericEventListener;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DingTalkEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 初始化钉钉事件监听客户端
        try{
            OpenDingTalkStreamClientBuilder.custom()
                    .credential(new AuthClientCredential("dingdpzuakabx5swx1xz", "wLyIifhR9Xi8OY9phul0OpS32A2Mksj4p5nbgDirva0ZnRBn7ecJWM7SVnOTLjYN"))
                    //.credential(new AuthClientCredential("${clientId}", "${clientSecret}"))
                    //注册事件监听
                    .registerAllEventListener(new GenericEventListener() {
                        public EventAckStatus onEvent(GenericOpenDingTalkEvent event) {
                            try {
                                //事件唯一Id
                                String eventId = event.getEventId();
                                log.error("bizDataStream+eventId:"+eventId);
                                //事件类型
                                String eventType = event.getEventType();
                                //事件产生时间
                                Long bornTime = event.getEventBornTime();
                                //获取事件体
                                JSONObject bizData = event.getData();
                                //处理事件
                                process(bizData);
                                //消费成功
                                return EventAckStatus.SUCCESS;
                            } catch (Exception e) {
                                //消费失败
                                return EventAckStatus.LATER;
                            }
                        }
                    }).build().start();
        }catch (Exception e){
            log.error("获取OpenDingTalkStream失败！");
        }
    }

    private void process(JSONObject bizData) {
        // 在这里处理具体的事件逻辑
        log.error("bizDataStream数据:"+bizData.toString());
    }
}
