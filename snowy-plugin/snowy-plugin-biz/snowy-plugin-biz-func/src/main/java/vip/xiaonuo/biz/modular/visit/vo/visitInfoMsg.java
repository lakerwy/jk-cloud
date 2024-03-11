package vip.xiaonuo.biz.modular.visit.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.common.consts.FlwCacheConstant;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class visitInfoMsg {

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

    @ApiModelProperty(value = "消息的唯一表示",position = 6)
    String uuid;

    public visitInfoMsg() {
    }

    public visitInfoMsg(visitInfoMsg.Builder builder) {
        this.forms = builder.forms;
        this.userSets = builder.userSets;
        this.title = builder.title;
        this.uuid = UUID.randomUUID(Boolean.FALSE).toString();
    }

    public static  class Builder{
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms;
        Set<String> userSets;
        String title;

        public visitInfoMsg.Builder forms(List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms) {
            this.forms = forms;
            return this;
        }

        public visitInfoMsg.Builder userSets(Set<String> userSets) {
            this.userSets = userSets;
            return this;
        }

        public visitInfoMsg.Builder title(String title){
            this.title = title;
            return this;
        };

        public visitInfoMsg build(){
            return new visitInfoMsg(this);
        }
    }

    /**
     * @param jsonObject
     * 任务名称：taskName
     * 任务创建人：createUser
     * 任务执行人：belongerUser
     * 任务执行时间：taskTime
     * 任务参与人：taskParticipants
     */
    public static visitInfoMsg buildRabbitMsg(JSONObject jsonObject) {
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();

        String taskName = jsonObject.getStr("taskName");
        if(StrUtil.isNotEmpty(taskName)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("外访目的：");
            form.setValue(taskName);
            forms.add(form);
        }
        String taskTime = jsonObject.getStr("taskTime");
        if(StrUtil.isNotEmpty(taskTime)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("外访结束时间：");
            form.setValue(taskTime);
            forms.add(form);
        }
        String endFlag = jsonObject.getStr("endFlag");
        if(StrUtil.isNotEmpty(endFlag)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("外访总结完成情况：");
            form.setValue(endFlag);
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
        }
        if(CollUtil.isNotEmpty(userSets)){
            builder.userSets(userSets);
        }
        return builder.build();
    }
}
