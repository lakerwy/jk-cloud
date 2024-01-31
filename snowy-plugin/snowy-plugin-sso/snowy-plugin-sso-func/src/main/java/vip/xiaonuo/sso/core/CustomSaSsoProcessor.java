package vip.xiaonuo.sso.core;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.sso.SaSsoConsts;
import cn.dev33.satoken.sso.SaSsoManager;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.sso.name.ParamName;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.prop.CommonProperties;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/29 11:50
 */
@Component
public class CustomSaSsoProcessor extends SaSsoProcessor {

    @Resource
    CustomSaSsoTemplate customSaSsoTemplate;

    @Resource
    private CommonProperties commonProperties;

    @Value("${server.servlet.context-path}")
    private String preFix;

    @Override
    public Object ssoAuth() {
        // 获取对象
        SaRequest req = SaHolder.getRequest();
        SaResponse res = SaHolder.getResponse();
        SaSsoConfig cfg = SaSsoManager.getConfig();
        StpLogic stpLogic = ssoTemplate.getStpLogic();
        ParamName paramName = ssoTemplate.paramName;

        // ---------- 此处有两种情况分开处理：
        // ---- 情况1：在SSO认证中心尚未登录，需要先去登录
        if( ! stpLogic.isLogin()) {
            return cfg.getNotLoginView().get();
        }
        // ---- 情况2：在SSO认证中心已经登录，需要重定向回 Client 端，而这又分为两种方式：
        String mode = req.getParam(paramName.mode, "");

        // 方式1：直接重定向回Client端 (mode=simple)
        if(mode.equals(SaSsoConsts.MODE_SIMPLE)) {
            String redirect = req.getParam(paramName.redirect);
            ssoTemplate.checkRedirectUrl(redirect);
            return res.redirect(redirect);
        } else {
            String ticket = customSaSsoTemplate.createTicket(stpLogic.getLoginId(), req.getParam(paramName.client));
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("ticket",ticket);
            dataMap.put("checkTicket",commonProperties.getBackendUrl()+preFix+ssoTemplate.apiName.ssoCheckTicket);
            return CommonResult.data(dataMap);
        }
    }
}
