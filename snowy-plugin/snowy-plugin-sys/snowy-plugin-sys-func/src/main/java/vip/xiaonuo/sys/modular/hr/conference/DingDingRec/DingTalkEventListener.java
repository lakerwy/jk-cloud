package vip.xiaonuo.sys.modular.hr.conference.DingDingRec;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.open.app.api.GenericEventListener;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.message.GenericOpenDingTalkEvent;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.dingtalk.open.app.stream.protocol.event.EventAckStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.dev.modular.config.service.DevConfigService;
import vip.xiaonuo.sys.modular.hr.conference.rez.service.ConferenceRezService;
import vip.xiaonuo.sys.modular.user.entity.SysUser;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DingTalkEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final String MEETING_BOOK = "meetingroom_book";
    private static final String EVENT_SUB_TYPE = "eventSubType";
    private static final String BOOK_SUCCESS = "bookSuccess";
//    private static final String SP_UNION_ID = "QLLSud8sSkVN0SXuiScfxuwiEiE"; //4SViPiiX4TPmlN0SXuiScfxuwiEiE


    @Value("${dingtalkstream.clientId}")
    private String clientId;

    @Value("${dingtalkstream.clientSecret}")
    private String clientSecret;

    @Value("${dingtalkstream.enable}")
    private Boolean enable;

    @Value("${dingtalkstream.spUnionId}")
    private String spUnionId;

    @Resource
    ConferenceRezService conferenceRezService;
    @Resource
    DingdingCalendarUtil dingdingCalendarUtil;
    @Resource
    IDingDingService iDingDingService;
    @Resource
    DevConfigService devConfigService;


    /**
     * 初始化钉钉事件监听客户端
     * */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(enable){
            try{
                OpenDingTalkStreamClientBuilder.custom()
                        .credential(new AuthClientCredential(clientId, clientSecret))
                        .registerAllEventListener(new GenericEventListener() {//注册事件监听
                            public EventAckStatus onEvent(GenericOpenDingTalkEvent event) {
                                    try {
                                        String eventType = event.getEventType();//事件类型
                                        log.info("ding-eventType:"+eventType);
                                        if(MEETING_BOOK.equals(eventType)){
                                            JSONObject bizData = event.getData();//获取事件体
                                            bizData = getRecPart(bizData);
                                            conferenceRezService.process(bizData, ObjectUtil.isNotEmpty(bizData.get("bookId")) ? bizData.get("bookId").toString() : "");//处理事件
                                        }
                                    } catch (Exception e) {
                                        log.error("获取钉钉预约会议数据失败！e:"+e.getMessage());
                                        return EventAckStatus.LATER;//消费失败
                                    }
                                    return EventAckStatus.SUCCESS;//消费成功
                            }
                        }).build().start();
            }catch (Exception e){
                log.error("获取OpenDingTalkStream失败！");
            }
        }
    }

    private JSONObject getRecPart(JSONObject bizData){
        String xAcsDingtalkAccessToken = iDingDingService.getDingToken(clientId,clientSecret);//获取token
        String recType = ObjectUtil.isNotEmpty(bizData.get(EVENT_SUB_TYPE)) ? bizData.get(EVENT_SUB_TYPE).toString() : "";
        if(!recType.equals(BOOK_SUCCESS)){return bizData;}
        try{
            cn.hutool.json.JSONObject objResult = JSONUtil.parseObj(ObjectUtil.isNotEmpty(bizData.get("extension")) ? bizData.get("extension").toString() :"");
            if(ObjectUtil.isEmpty(objResult.get("calendarEventId"))){return bizData;}

            String calendarEventIdJson =objResult.get("calendarEventId").toString();
            if(StringUtils.isEmpty(calendarEventIdJson)){return bizData;}

            String[] calendarIds = calendarEventIdJson.split(";");
            if(calendarIds.length <=1){return bizData;}
            List<SysUser> sysUsers = dingdingCalendarUtil.getCalendarPart(spUnionId,calendarIds[1],xAcsDingtalkAccessToken);
            String joinUsers = sysUsers.stream().map(SysUser::getId).collect(Collectors.joining(","));
            //StringBuilder joinUsers = new StringBuilder();
            //sysUsers.forEach(user -> joinUsers.append(user.getId()).append(","));
            bizData.put("joinUser",joinUsers);
            bizData.put("joinSize",sysUsers.size());
        }catch (Exception e){
            log.error("获取会议参与人失败！e:"+e.getMessage()+",bizData:"+bizData.toString());
        }
        return bizData;
    }
}