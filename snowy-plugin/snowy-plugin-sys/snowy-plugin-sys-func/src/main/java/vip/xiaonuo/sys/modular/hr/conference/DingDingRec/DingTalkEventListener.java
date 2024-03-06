package vip.xiaonuo.sys.modular.hr.conference.DingDingRec;

import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.open.app.api.GenericEventListener;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;

import javax.annotation.Resource;

@Component
@Slf4j
public class DingTalkEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Value("${dingtalkstream.clientId}")
    private String clientId;

    @Value("${dingtalkstream.clientSecret}")
    private String clientSecret;

    @Value("${dingtalkstream.enabled}")
    private Boolean enable;

    @Resource
    ConferenceRezService conferenceRezService;

    // 初始化钉钉事件监听客户端
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        /*if(enable){
            try{
                OpenDingTalkStreamClientBuilder.custom()
                        //.credential(new AuthClientCredential("dingdpzuakabx5swx1xz", "wLyIifhR9Xi8OY9phul0OpS32A2Mksj4p5nbgDirva0ZnRBn7ecJWM7SVnOTLjYN"))
                        .credential(new AuthClientCredential(clientId, clientSecret))
                        .registerAllEventListener(new GenericEventListener() {//注册事件监听
                            public EventAckStatus onEvent(GenericOpenDingTalkEvent event) {
                                try {
                                    String eventId = event.getEventId();//事件唯一Id
                                    String eventType = event.getEventType();//事件类型
                                    //Long bornTime = event.getEventBornTime();//事件产生时间
                                    JSONObject bizData = event.getData();//获取事件体
                                    conferenceRezService.process(bizData,eventType,eventId);//处理事件
                                    return EventAckStatus.SUCCESS;//消费成功
                                } catch (Exception e) {
                                    log.error("获取钉钉预约会议数据失败！e:"+e.getMessage());
                                    return EventAckStatus.LATER;//消费失败
                                }
                            }
                        }).build().start();
            }catch (Exception e){
                log.error("获取OpenDingTalkStream失败！");
            }
        }*/
    }
}
