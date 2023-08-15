package vip.xiaonuo.dev.modular.sms.service;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import vip.xiaonuo.dev.modular.message.entity.DevMessage;

import java.util.List;

/**
 * @author mayike
 * @create 2023/08/10 16:17
 */
public interface UniPushService {

    IPushResult singlePush(String phoneNumber, DevMessage devMessage, Integer isAlias);//单个用户推送信息
    IAliasResult bindAlias(String alias, String clientId);//别名绑定
    IAliasResult queryClientId(String appId,String alias);//根据别名查询用户cid
    IQueryResult getClientIdStatus(String appId, String clientId);//通过cid查询用户cid状态
    String CodeStatus(String alias,Integer isAlias);//包装的一个判断用户cid状态离线还是在线的方法，并代替了之前用type决定推送模板的方式
    void batchSendPush(List<String> cids, DevMessage devMessage, Integer isAlias);//单个用户推送信息
}

