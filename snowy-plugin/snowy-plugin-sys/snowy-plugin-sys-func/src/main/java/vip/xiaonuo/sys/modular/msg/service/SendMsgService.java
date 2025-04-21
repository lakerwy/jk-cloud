package vip.xiaonuo.sys.modular.msg.service;

import vip.xiaonuo.sys.modular.company.param.SendMsgParam;

public interface SendMsgService {

    /**
     * 发送站内信和钉钉提醒
     *
     * @author pzh
     * @date  2025/04/21 15:04
     */
    void sendMsg(SendMsgParam sendMsgParam);
}
