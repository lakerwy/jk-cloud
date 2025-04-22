package vip.xiaonuo.sys.modular.msg.service.impl;

import org.springframework.stereotype.Service;
import vip.xiaonuo.dev.api.DevMessageApi;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.dev.modular.message.enums.DevMessageCategoryEnum;
import vip.xiaonuo.sys.modular.company.param.SendMsgParam;
import vip.xiaonuo.sys.modular.msg.service.SendMsgService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Resource
    DevMessageApi devMessageApi;

    @Resource
    IDingDingService dingDingService;

    @Override
    public void sendMsg(SendMsgParam sendMsgParam) {
        List<String> userIdList = sendMsgParam.getUserIdList();
        String title = sendMsgParam.getTitle();
        String content = sendMsgParam.getContent();
        // 发送站内信
        devMessageApi.sendMessageWithContent(userIdList, DevMessageCategoryEnum.BIZ.getValue(), title, content, null);
        // 发送钉钉提醒
        dingDingService.batchSend(String.format("【%s】%s", title, content),userIdList);
    }
}
