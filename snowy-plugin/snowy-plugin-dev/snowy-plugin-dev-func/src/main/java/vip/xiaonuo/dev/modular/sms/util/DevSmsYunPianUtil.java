package vip.xiaonuo.dev.modular.sms.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.fhs.common.utils.JsonUtils;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.api.DevConfigApi;

import java.util.Map;

/**
 *  * 云片短信工具类
 *  * 参考文档：https://www.yunpian.com/official/document/sms/zh_CN/domestic_tpl_single_send
 *
 * @author yong.ye
 * @date 2023/6/27 14:00
 */
@Slf4j
public class DevSmsYunPianUtil {

    /* 初始化云片客户端 */
    private static YunpianClient client;
    /* 云片短信：APIKey */
    private static final String SNOWY_SMS_YUNPIAN_API_KEY = "SNOWY_SMS_YUNPIAN_API_KEY";

    /**
     * 根据模板发送短信（单一发送）
     *
     * @author yong.ye
     * @date 2023/6/27 14:00
     */
    public static String sendSms(String apiKey, String mobile, String tpl_id, String tpl_value){
        try{
            if(ObjectUtil.isEmpty(apiKey)) {
                DevConfigApi devConfigApi = SpringUtil.getBean(DevConfigApi.class);
                /* APIKey */
                apiKey = devConfigApi.getValueByKey(SNOWY_SMS_YUNPIAN_API_KEY);
            }
            if(ObjectUtil.isEmpty(apiKey)) {
                throw new CommonException("云片短信操作客户端未正确配置：apiKey为空");
            }
            log.info("apiKey= " + apiKey + "  tpl_id= " + tpl_id + "  phone= " + mobile);
            //初始化clnt,使用单例方式
            client = new YunpianClient(apiKey).init();
            //发送短信API
            Map<String, String> param = client.newParam(4);
            param.put(YunpianClient.APIKEY, apiKey);
            param.put(YunpianClient.MOBILE, mobile);
            param.put(YunpianClient.TPL_ID, tpl_id);
            param.put(YunpianClient.TPL_VALUE, tpl_value);
            Result<SmsSingleSend> r = client.sms().tpl_single_send(param);
            //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
            //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*
            //释放:clnt.close() 释放后clnt将无法再使用
            return JsonUtils.object2json(r);
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }finally {
            //释放:clnt.close() 释放后clnt将无法再使用
            client.close();
        }
    }

}
