package vip.xiaonuo.sso.modular.center.core;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.sso.SaSsoConsts;
import cn.dev33.satoken.sso.SaSsoManager;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.sso.name.ParamName;
import cn.dev33.satoken.stp.StpLogic;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.api.SaBaseLoginUserApi;
import vip.xiaonuo.auth.core.enums.SaClientTypeEnum;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.modular.login.enums.AuthExceptionEnum;
import vip.xiaonuo.auth.modular.login.param.AuthAccountPasswordLoginParam;
import vip.xiaonuo.auth.modular.login.service.AuthService;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sso.modular.userextend.entity.SysUserExtend;
import vip.xiaonuo.sso.modular.userextend.service.SysUserExtendService;

import javax.annotation.Resource;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/29 11:50
 */
@Component
public class CustomSaSsoProcessor extends SaSsoProcessor {

    @Resource
    CustomSaSsoTemplate customSaSsoTemplate;

//    @Resource
//    private CommonProperties commonProperties;
//
//    @Value("${server.servlet.context-path}")
//    private String preFix;

    @Resource
    AuthService authService;

    @Resource(name = "loginUserApi")
    private SaBaseLoginUserApi loginUserApi;

    @Resource
    SysUserExtendService sysUserExtendService;

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
//            HashMap<String, Object> dataMap = new HashMap<>();
//            dataMap.put("ticket",ticket);
//            dataMap.put("checkTicket",commonProperties.getBackendUrl()+preFix+ssoTemplate.apiName.ssoCheckTicket);
            return CommonResult.data(ticket);
        }
    }

    @Override
    public Object ssoDoLogin() {
        // 获取对象
        SaRequest req = SaHolder.getRequest();
        SaSsoConfig cfg = SaSsoManager.getConfig();
        Map<String, String> paramMap = req.getParamMap();
        AuthAccountPasswordLoginParam authAccountPasswordLoginParam = new AuthAccountPasswordLoginParam();
        BeanUtil.fillBeanWithMap(paramMap, authAccountPasswordLoginParam, true);
        // ParamName paramName = ssoTemplate.paramName;
        AbstractMap.SimpleEntry<String, String> result = authService.checkUser(authAccountPasswordLoginParam);
        String passwordHash = result.getKey();
        Boolean flag = Boolean.TRUE;
        SaBaseLoginUser saBaseLoginUser = loginUserApi.getUserByAccount(authAccountPasswordLoginParam.getAccount());
        if(ObjectUtil.isEmpty(saBaseLoginUser)) {
            SysUserExtend sysUserExtend = sysUserExtendService.getOne(new LambdaQueryWrapper<SysUserExtend>().eq(SysUserExtend::getAccount, authAccountPasswordLoginParam.getAccount()));
            if(ObjectUtil.isEmpty(sysUserExtend)){
                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
            }
            flag = Boolean.FALSE;
            saBaseLoginUser = BeanUtil.toBean(sysUserExtend,SaBaseLoginUser.class);
        }
        if (!saBaseLoginUser.getPassword().equals(passwordHash)) {
            throw new CommonException(AuthExceptionEnum.PWD_ERROR.getValue());
        }
        String token = authService.ssoExecLoginB(saBaseLoginUser, SaClientTypeEnum.B.getValue(), flag);

        // 处理
        return cfg.getDoLoginHandle().apply(token,null);
    }
}
