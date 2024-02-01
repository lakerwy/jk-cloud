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
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.enums.SaClientTypeEnum;
import vip.xiaonuo.auth.core.pojo.SaBaseClientLoginUser;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.modular.login.enums.AuthDeviceTypeEnum;
import vip.xiaonuo.auth.modular.login.enums.AuthExceptionEnum;
import vip.xiaonuo.auth.modular.login.param.AuthAccountPasswordLoginParam;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.common.util.CommonCryptogramUtil;

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

//    @Resource
//    private CommonProperties commonProperties;
//
//    @Value("${server.servlet.context-path}")
//    private String preFix;

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
//            dataMap.put("checkTicket",commonProperties.getBackendUrl()+preFix+ssoTemplate.apiName.ssoCheckTicket);
            return CommonResult.data(dataMap);
        }
    }

    @Override
    public Object ssoDoLogin() {
        // 获取对象
        SaRequest req = SaHolder.getRequest();
        SaSsoConfig cfg = SaSsoManager.getConfig();
        ParamName paramName = ssoTemplate.paramName;

        // 处理
        return cfg.getDoLoginHandle().apply(req.getParam(paramName.name), req.getParam(paramName.pwd));
    }

//    public String doLogin(AuthAccountPasswordLoginParam authAccountPasswordLoginParam, String type) {
//        // 获取账号
//        String account = authAccountPasswordLoginParam.getAccount();
//        // 获取密码
//        String password = authAccountPasswordLoginParam.getPassword();
//        // 获取设备
//        String device = authAccountPasswordLoginParam.getDevice();
//        // 默认指定为PC，如在小程序跟移动端的情况下，自行指定即可
//        if(ObjectUtil.isEmpty(device)) {
//            device = AuthDeviceTypeEnum.PC.getValue();
//        } else {
//            AuthDeviceTypeEnum.validate(device);
//        }
//        // 校验验证码
//        String defaultCaptchaOpen = devConfigApi.getValueByKey(SNOWY_SYS_DEFAULT_CAPTCHA_OPEN_KEY);
//        if(ObjectUtil.isNotEmpty(defaultCaptchaOpen)) {
//            if(Convert.toBool(defaultCaptchaOpen)) {
//                // 获取验证码
//                String validCode = authAccountPasswordLoginParam.getValidCode();
//                // 获取验证码请求号
//                String validCodeReqNo = authAccountPasswordLoginParam.getValidCodeReqNo();
//                // 开启验证码则必须传入验证码
//                if(ObjectUtil.isEmpty(validCode)) {
//                    throw new CommonException(AuthExceptionEnum.VALID_CODE_EMPTY.getValue());
//                }
//                // 开启验证码则必须传入验证码请求号
//                if(ObjectUtil.isEmpty(validCodeReqNo)) {
//                    throw new CommonException(AuthExceptionEnum.VALID_CODE_REQ_NO_EMPTY.getValue());
//                }
//                // 执行校验验证码
//                validValidCode(null, validCode, validCodeReqNo);
//            }
//        }
//        // SM2解密并获得前端传来的密码哈希值
//        String passwordHash;
//        try {
//            // 解密，并做哈希值
//            passwordHash = CommonCryptogramUtil.doHashValue(CommonCryptogramUtil.doSm2Decrypt(password));
//        } catch (Exception e) {
//            throw new CommonException(AuthExceptionEnum.PWD_DECRYPT_ERROR.getValue());
//        }
//        // 根据账号获取用户信息，根据B端或C端判断
//        if(SaClientTypeEnum.B.getValue().equals(type)) {
//            SaBaseLoginUser saBaseLoginUser = loginUserApi.getUserByAccount(account);
//            if(ObjectUtil.isEmpty(saBaseLoginUser)) {
//                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
//            }
//            if (!saBaseLoginUser.getPassword().equals(passwordHash)) {
//                throw new CommonException(AuthExceptionEnum.PWD_ERROR.getValue());
//            }
//            // 执行B端登录
//            return execLoginB(saBaseLoginUser, device);
//        } else {
//            SaBaseClientLoginUser saBaseClientLoginUser = clientLoginUserApi.getClientUserByAccount(account);
//            if(ObjectUtil.isEmpty(saBaseClientLoginUser)) {
//                throw new CommonException(AuthExceptionEnum.ACCOUNT_ERROR.getValue());
//            }
//            if (!saBaseClientLoginUser.getPassword().equals(passwordHash)) {
//                throw new CommonException(AuthExceptionEnum.PWD_ERROR.getValue());
//            }
//            // 执行C端登录
//            return execLoginC(saBaseClientLoginUser, device);
//        }
//    }
}
