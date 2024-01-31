package vip.xiaonuo.sso.controller;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.hutool.http.HttpUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.auth.core.enums.SaClientTypeEnum;
import vip.xiaonuo.auth.modular.login.param.AuthAccountPasswordLoginParam;
import vip.xiaonuo.auth.modular.login.service.AuthService;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.sso.core.CustomSaSsoProcessor;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/1/25 15:59
 */
@Api(tags = "共享文件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SsoServerController {


    @Resource
    private CommonProperties commonProperties;

    @Resource
    private CustomSaSsoProcessor saSsoProcessor;

    @Resource
    AuthService authService;

    @Resource
    /*
     * SSO-Server端：处理所有SSO相关请求
     * 		http://{host}:{port}/sso/auth			-- 单点登录授权地址，接受参数：redirect=授权重定向地址
     * 		http://{host}:{port}/sso/doLogin		-- 账号密码登录接口，接受参数：name、pwd
     * 		http://{host}:{port}/sso/checkTicket	-- Ticket校验接口（isHttp=true时打开），接受参数：ticket=ticket码、ssoLogoutCall=单点注销回调地址 [可选]
     * 		http://{host}:{port}/sso/signout		-- 单点注销地址（isSlo=true时打开），接受参数：loginId=账号id、secretkey=接口调用秘钥
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("单点登录通用接口")
    @RequestMapping("/sso/*")
    @CrossOrigin
    public Object ssoRequest() {
        return saSsoProcessor.serverDister();
    }

    // 配置SSO相关参数
    @Autowired
    private void configSso(SaSsoConfig sso) {
        //如果未登录
        // 配置：未登录时返回的View
        sso.setNotLoginView(() -> {
            return new CommonResult<>(1000,"系统未登录",commonProperties.getFrontUrl());
        });

        // 配置：登录处理函数
        sso.setDoLoginHandle((name, pwd) -> {
            AuthAccountPasswordLoginParam authAccountPasswordLoginParam = new AuthAccountPasswordLoginParam();
            authAccountPasswordLoginParam.setAccount(name);
            authAccountPasswordLoginParam.setPassword(pwd);
            String token = authService.doLogin(authAccountPasswordLoginParam, SaClientTypeEnum.B.getValue());
            return CommonResult.data(token);
        });

        // 配置 Http 请求处理器 （在模式三的单点注销功能下用到，如不需要可以注释掉）
        sso.setSendHttp(url -> {
            try {
                // 发起 http 请求
                System.out.println("------ 发起请求：" + url);
                return HttpUtil.get(url);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
