package vip.xiaonuo.biz.modular.teambition.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
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

@Getter
@Setter
public class TaskInfoMsg {

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

    public TaskInfoMsg() {
    }

    public TaskInfoMsg(TaskInfoMsg.Builder builder) {
        this.forms = builder.forms;
        this.userSets = builder.userSets;
        this.title = builder.title;
        this.uuid = UUID.randomUUID(Boolean.FALSE).toString();
    }

    public static  class Builder{
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms;
        Set<String> userSets;
        String title;

        public TaskInfoMsg.Builder forms(List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms) {
            this.forms = forms;
            return this;
        }

        public TaskInfoMsg.Builder userSets(Set<String> userSets) {
            this.userSets = userSets;
            return this;
        }

        public TaskInfoMsg.Builder title(String title){
            this.title = title;
            return this;
        };

        public TaskInfoMsg build(){
            return new TaskInfoMsg(this);
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
    public static  TaskInfoMsg buildRabbitMsg(JSONObject jsonObject) {
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();

        String taskName = jsonObject.getStr("taskName");
        if(StrUtil.isNotEmpty(taskName)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务名称：");
            form.setValue(taskName);
            forms.add(form);
        }
        String createUser = jsonObject.getStr("createUser");
        if(StrUtil.isNotEmpty(createUser)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务创建人：");
            form.setValue(createUser);
            forms.add(form);
        }
        String belongerUser = jsonObject.getStr("belongerUser");
        if(StrUtil.isNotEmpty(belongerUser)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务执行人：");
            form.setValue(belongerUser);
            forms.add(form);
        }
        String leadUser = jsonObject.getStr("leadUser");
        if(StrUtil.isNotEmpty(belongerUser)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务负责人：");
            form.setValue(leadUser);
            forms.add(form);
        }
        String taskTime = jsonObject.getStr("taskTime");
        if(StrUtil.isNotEmpty(taskTime)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务执行时间：");
            form.setValue(taskTime);
            forms.add(form);
        }
        String taskParticipants = jsonObject.getStr("taskParticipants");
        if(StrUtil.isNotEmpty(taskParticipants)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务参与人：");
            form.setValue(taskParticipants);
            forms.add(form);
        }
        String taskChange = jsonObject.getStr("taskChange");
        if(StrUtil.isNotEmpty(taskChange)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务变更：");
            form.setValue(taskChange);
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

    /**
     * @param jsonObject
     * 任务名称：taskName
     * 任务创建人：createUser
     * 任务执行人：belongerUser
     * 任务执行时间：taskTime
     * 任务参与人：taskParticipants
     * 任务变更：taskChange
     */
    public static  TaskInfoMsg changeRabbitMsg(JSONObject jsonObject) {
        List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
        OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();

        String taskName = jsonObject.getStr("taskName");
        if(StrUtil.isNotEmpty(taskName)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务名称：");
            form.setValue(taskName);
            forms.add(form);
        }
        String createUser = jsonObject.getStr("createUser");
        if(StrUtil.isNotEmpty(createUser)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务创建人：");
            form.setValue(createUser);
            forms.add(form);
        }
        String belongerUser = jsonObject.getStr("belongerUser");
        if(StrUtil.isNotEmpty(belongerUser)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务执行人：");
            form.setValue(belongerUser);
            forms.add(form);
        }
        String taskTime = jsonObject.getStr("taskTime");
        if(StrUtil.isNotEmpty(taskTime)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务执行时间：");
            form.setValue(taskTime);
            forms.add(form);
        }
        String taskParticipants = jsonObject.getStr("taskParticipants");
        if(StrUtil.isNotEmpty(taskParticipants)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务参与人：");
            form.setValue(taskParticipants);
            forms.add(form);
        }
        String taskChange = jsonObject.getStr("taskChange");
        if(StrUtil.isNotEmpty(taskChange)){
            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("任务变更：");
            form.setValue(taskChange);
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
