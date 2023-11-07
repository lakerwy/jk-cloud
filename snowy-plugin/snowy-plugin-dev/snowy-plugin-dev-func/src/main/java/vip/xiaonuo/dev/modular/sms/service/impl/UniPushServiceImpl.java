package vip.xiaonuo.dev.modular.sms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.Message;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.dto.GtReq;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.modular.message.entity.DevMessage;
import vip.xiaonuo.dev.modular.sms.service.UniPushService;

import java.util.List;

/**
 * @author wzh
 * @create 2020/11/9 16:17
 */
@Service
@Slf4j
public class UniPushServiceImpl implements UniPushService {

    //@Value("${gexin.host}")
    private String host = "http://api.getui.com/apiex.htm";
    @Value("${uniPush.appId}")
    private  String appId;
    @Value("${uniPush.appKey}")
    private  String appKey;
//    @Value("${uniPush.appSecret}")
//    private  String appSecret;
    @Value("${uniPush.masterSecret}")
    private  String masterSecret;


    @Override
    public IPushResult singlePush(String phoneNumberOrCid, DevMessage devMessage, Integer isAlias) throws NullPointerException  { //isAlias，1是根据cid推送，2是别名推送，  type的值：1为离线模板透传，2为通知

        IGtPush push = new IGtPush(host, appKey, masterSecret);
        SingleMessage message = new SingleMessage();
//        message.setStrategyJson("{\"default\":4,\"ios\":4,\"st\":4}");//1:个推通道优先，在线经个推通道下发，离线经厂商下发(默认);但是uniapp，没法这样离线推送
//        离线有效时间，单位为毫秒，可选，但是有时候离线会失效？
        message.setOffline(true);
        message.setOfflineExpireTime(72 * 3600 * 1000);
        boolean result = setMessageData(message,devMessage , phoneNumberOrCid,isAlias);
        if(!result){
            return null;
        }
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        if(isAlias==1){
            target.setClientId(phoneNumberOrCid);}
        else if(isAlias==2){
            target.setAlias(phoneNumberOrCid);
        }
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();//推送失败时，重推。
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("个推服务器响应异常");
        }
        return ret;
    }

    //别名绑定
    @Override
    public IAliasResult bindAlias(String alias, String clientId) {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        //        System.out.println("绑定结果：" + iAliasResult.getResponse());
        return push.bindAlias(appId, alias, clientId);
    }

    //根据别名查询cid
    @Override
    public IAliasResult queryClientId(String appId, String alias) {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        return push.queryClientId(appId, alias);
    }

    //根据cid查询用户状态
    @Override
    public IQueryResult getClientIdStatus(String appId, String cid) {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        return  push.getClientIdStatus(appId, cid);
    }


    //包装的一个判断用户cid状态离线还是在线的方法，代替了之前用type决定推送模板的方式
    @Override
    public String CodeStatus(String alias,Integer isAlias){
        if(isAlias==1){
            IQueryResult clientIdStatus = getClientIdStatus(appId, alias);
            return clientIdStatus.getResponse().get("result").toString();
        }else if(isAlias==2) {
            IAliasResult iAliasResult = queryClientId(appId, alias);
//        System.out.println(iAliasResult.getResponse());
            if(!iAliasResult.getResponse().get("result").equals("ok")){
                return "null";
            }
            List<String> clientIdList = iAliasResult.getClientIdList();
            if (clientIdList.size()==0){
                return "null";
            }
            String cid = clientIdList.get(0);
            IQueryResult clientIdStatus = getClientIdStatus(appId, cid);
            return clientIdStatus.getResponse().get("result").toString();
        }
        return null;
    }

    @Async
    @Override
    public void batchSendPush(List<String> cids, DevMessage devMessage, Integer isAlias) {
        if(CollUtil.isNotEmpty(cids)){
            cids.forEach(cid->singlePush(cid,devMessage,isAlias));
        }
    }

    //设置消息数据
    private boolean setMessageData(Message message, DevMessage msg, String phoneNumberOrCid,Integer isAlias) {
        String result = CodeStatus(phoneNumberOrCid,isAlias);
        if(result.equals("Offline")){
            TransmissionTemplate template = getTransmissionTemplate(msg);//离线模板1
            message.setData(template);
            return true;
//            NotificationTemplate template =  notificationTemplateDemo(msg);   //通知模板 ，再多来两个模板我就要整switch了
//            message.setData(template);
//            return true;
        }
        if(result.equals("Online")){
            NotificationTemplate template =  notificationTemplateDemo(msg);   //通知模板 ，再多来两个模板我就要整switch了
            message.setData(template);
            return true;
        }
        else return false;
    }


    //通知模板设置
    private NotificationTemplate notificationTemplateDemo(DevMessage msg/*, String phoneNumOrCid,String orderNum*/) {

        NotificationTemplate template = new NotificationTemplate();
        template.setNotifyid(RandomUtil.randomInt(6));
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        //透传内容，不在通知栏中展示，自定义内容，开发者自行处理，不支持转义字符
        template.setTransmissionContent(JSONUtil.toJsonStr(msg));
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(msg.getSubject());
        style.setText(msg.toString());
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
//        style.setLogoUrl(pushMessage.getIconUrl());
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        style.setChannel("默认channel");
        style.setChannelName("默认channel名称");
        style.setChannelLevel(3);//3：有声音，有震动，锁屏和通知栏中都显示，通知唤醒屏幕。（推荐）
        template.setStyle(style);
        // 设置定时展示时间，安卓机型可用
        // template.setDuration("2019-08-16 11:40:00", "2019-08-16 12:24:00");
        // 消息覆盖
        //template.setNotifyid(pushMessage.getMsgId()); // 在消息推送的时候设置自定义的notifyid。如果需要覆盖此条消息，则下次使用相同的notifyid发一条新的消息。客户端sdk会根据notifyid进行覆盖。
        return template;
    }

    //离线通知模板1 -- ios和安卓一起推送，自动识别手机厂商，如果不是苹果会忽视APN。
    private TransmissionTemplate getTransmissionTemplate(DevMessage msg) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionType(1);//搭配transmissionContent使用，可选值为1、2；1：立即启动APP（不推荐使用，影响客户体验）2：客户端收到消息后需要自行处理
        template.setTransmissionContent(JSONUtil.toJsonStr(msg)); //透传内容
        template.setAPNInfo(getAPNPayload(msg.getContent())); //ios消息推送，用于设置标题、内容、语音、多媒体、VoIP（基于IP的语音传输）等。离线走APNs时起效果
        Notify notify = new Notify();
        notify.setTitle(msg.getSubject());
        notify.setContent(msg.getContent());
        notify.setIntent("intent:#Intent;action=android.intent.action.oppopush;launchFlags=0x14000000;component=xxxxxx/io.dcloud.PandoraEntry;S.UP-OL-SU=true;S.title=xxxx;S.content=xxxxx;S.payload="+JSONUtil.toJsonStr(msg)+";end");
        //上面的参数可以去看官方的API文档，里面有写离线厂商推送配置的具体。
        notify.setType(GtReq.NotifyInfo.Type._intent);
        template.set3rdNotifyInfo(notify);//设置第三方通知
        return template;
    }

    //ios离线推送APN设置
    private APNPayload getAPNPayload(String msg) {
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(0);//0是有通知栏消息，1是无通知栏消息
        //ios 12.0 以上可以使用 Dictionary 类型的 sound
        payload.setSound("default");
        payload.setCategory("$由客户端定义");//在客户端通知栏触发特定的action和button显示
        payload.addCustomMsg("msg",msg);//增加自定义的数据,Key-Value形式
//        是否用字典模式?
        payload.setAlertMsg(getDictionaryAlertMsg(msg));
        return payload;
    }


    //获取字典模式对象。
    private APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String msg) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(msg);
        alertMsg.setTitle("订单到期提醒");
        return alertMsg;
    }


}

