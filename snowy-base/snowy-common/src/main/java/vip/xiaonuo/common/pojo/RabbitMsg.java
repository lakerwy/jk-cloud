package vip.xiaonuo.common.pojo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.common.consts.FlwCacheConstant;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/7 13:59
 */
@Setter
@Getter
public class RabbitMsg {
    /**
     * oa 消息体forms
     */
    @ApiModelProperty(value = "消息体forms", position = 1)
    List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms;
    /**
     * 发送人集合
     */
    @ApiModelProperty(value = "发送人集合", position = 2)
    Set<String> userSets;

    /**
     * 消息的title
     */
    @ApiModelProperty(value = "消息的title", position = 3)
    String title;

    /***
     * 移动端(或小程序) url
     */
    @ApiModelProperty(value = "移动端(或小程序) url", position = 4)
    String url;

    /**
     * pc url
     */

    @ApiModelProperty(value = "pc url", position = 5)
    String pcUrl;


    String userId;

    @ApiModelProperty(value = "消息的唯一表示",position = 6)
    String uuid;

    @Override
    public String toString() {
        return "RabbitMsg{" +
                "forms=" + forms +
                ", userSets=" + userSets +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", pcUrl='" + pcUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }

    public RabbitMsg() {
    }

    public RabbitMsg(Builder builder) {
        this.forms = builder.forms;
        this.userSets = builder.userSets;
        this.title = builder.title;
        this.url = builder.url;
        this.pcUrl = builder.pcUrl;
        this.uuid = UUID.randomUUID(Boolean.FALSE).toString();
    }

    public static  class Builder{
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms;
        Set<String> userSets;
        String title;
        String url;
        String pcUrl;

        public Builder forms(List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms) {
            this.forms = forms;
            return this;
        }

        public Builder userSets(Set<String> userSets) {
            this.userSets = userSets;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        };

        public Builder url(String url){
            this.url = url;
            return this;
        };

        public Builder pcUrl(String pcUrl){
            this.pcUrl = pcUrl;
            return this;
        };

        public RabbitMsg build(){
            return new RabbitMsg(this);
        }
    }

    /**
     *  json 中共存 str
     * @param jsonObject
     */
    public static  RabbitMsg buildRabbitMsg(JSONObject jsonObject) {
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
        String flwName = jsonObject.getStr(FlwCacheConstant.FLW_NAME);
        if(StrUtil.isNotEmpty(flwName)){
            form.setKey("流程名称： ");
            form.setValue(flwName);
            forms.add(form);
        }

        String flwMsgType = jsonObject.getStr(FlwCacheConstant.FLW_MSG_TYPE);
        if(StrUtil.isNotEmpty(flwMsgType)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("通知类型： ");
            form.setValue(flwMsgType);
            forms.add(form);
        }

        String flwBaseInfo = jsonObject.getStr(FlwCacheConstant.FLW_BASE_INFO);
        if(StrUtil.isNotEmpty(flwBaseInfo)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("基本信息： ");
            form.setValue(flwBaseInfo);
            forms.add(form);
        }

        String flwSn = jsonObject.getStr(FlwCacheConstant.FLW_SN);
        if(StrUtil.isNotEmpty(flwSn)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("流水号：");
            form.setValue(flwSn);
            forms.add(form);
        }

        String flwStarter = jsonObject.getStr(FlwCacheConstant.FLW_STARTER);
        Date flwStartTime = jsonObject.getDate(FlwCacheConstant.FLW_START_TIME);
        if(StrUtil.isNotEmpty(flwStarter)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("发起于：");
            form.setValue(flwStarter+" | "+ (ObjectUtil.isNotNull(flwStartTime)?DateUtil.format(flwStartTime, DatePattern.NORM_DATETIME_PATTERN):DateUtil.now()));
            forms.add(form);
        }

        String flwStatus = jsonObject.getStr(FlwCacheConstant.FLW_STATUS);
        if(StrUtil.isNotEmpty(flwStatus)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("流程完成情况：");
            form.setValue(flwStatus);
            forms.add(form);
        }

        String flwSenders = jsonObject.getStr(FlwCacheConstant.SENDERS);
        Set<String> userSets = new HashSet<>();
        if(StrUtil.isNotEmpty(flwSenders)){
            //发送人
            String str = jsonObject.getStr(FlwCacheConstant.SENDERS);
            String[] split = str.split(StrUtil.COMMA);
            userSets = Arrays.stream(split).collect(Collectors.toSet());
        }

        Builder builder = new Builder().forms(forms);
        //流程标题
        String title= jsonObject.getStr(FlwCacheConstant.TITLE);
        if(StrUtil.isNotEmpty(title)){
            builder.title(title);
            if(title.contains("流程分享通知单")){
                form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
                form.setKey("分享人：");
                form.setValue(jsonObject.getStr(FlwCacheConstant.CARBON_NOTIFICATION_NAME));
                builder.forms.add(form);
                OapiMessageCorpconversationAsyncsendV2Request.Form request = new OapiMessageCorpconversationAsyncsendV2Request.Form();
                request.setKey("提示：");
                request.setValue("请前往审批中心-抄送事宜(我收到的)查看");
                builder.forms.add(request);
            }
        }
        if(CollUtil.isNotEmpty(userSets)){
            builder.userSets(userSets);
        }
        String processInstanceId = jsonObject.getStr(FlwCacheConstant.FLE_PROCESS_INSTANCE_ID);
        if(StrUtil.isNotEmpty(processInstanceId)){
            builder.url(processInstanceId);
        }
        return builder.build();
    }

}
