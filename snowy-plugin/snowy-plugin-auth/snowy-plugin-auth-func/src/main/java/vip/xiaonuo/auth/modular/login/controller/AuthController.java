/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.auth.modular.login.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.auth.core.enums.SaClientTypeEnum;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.modular.login.param.AuthAccountPasswordLoginParam;
import vip.xiaonuo.auth.modular.login.param.AuthGetPhoneValidCodeParam;
import vip.xiaonuo.auth.modular.login.param.AuthPhoneValidCodeLoginParam;
import vip.xiaonuo.auth.modular.login.result.AuthPicValidCodeResult;
import vip.xiaonuo.auth.modular.login.service.AuthService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.prop.CommonProperties;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * B端登录控制器
 *
 * @author xuyuxiang
 * @date 2021/12/23 21:50
 */
@Api(tags = "B端登录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 2)
@RestController
@Validated
public class AuthController {

    @Resource
    private AuthService authService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private CommonProperties commonProperties;


    private static  final  String QR_REQUEST = "/api/webapp/auth/b/autoLogin?uuid=";

    private static  Map<String,String> USER_MAP = Collections.synchronizedMap(new HashMap<>());

    private static final ConcurrentHashMap<String, CountDownLatch> USER_LATCH_MAP = new ConcurrentHashMap<>();

    private static final long TIMEOUT = 8; // 超时时间设置为 10 秒

    private static  final String QR_UUID = "qr_uuid:";

    private static  final String QR_UUID_USERID = "qr_uuid_USERID:";

    /**
     * B端获取图片验证码
     *
     * @author xuyuxiang
     * @date 2022/7/8 9:26
     **/
    @ApiOperationSupport(order = 1)
    @ApiOperation("B端获取图片验证码")
    @GetMapping("/auth/b/getPicCaptcha")
    public CommonResult<AuthPicValidCodeResult> getPicCaptcha() {
        return CommonResult.data(authService.getPicCaptcha(SaClientTypeEnum.B.getValue()));
    }

    /**
     * B端获取手机验证码
     *
     * @author xuyuxiang
     * @date 2022/7/8 9:26
     **/
    @ApiOperationSupport(order = 2)
    @ApiOperation("B端获取手机验证码")
    @GetMapping("/auth/b/getPhoneValidCode")
    public CommonResult<String> getPhoneValidCode(@Valid AuthGetPhoneValidCodeParam authGetPhoneValidCodeParam) {
        return CommonResult.data(authService.getPhoneValidCode(authGetPhoneValidCodeParam, SaClientTypeEnum.B.getValue()));
    }

    /**
     * B端账号密码登录
     *
     * @author xuyuxiang
     * @date 2021/10/15 13:12
     **/
    @ApiOperationSupport(order = 3)
    @ApiOperation("B端账号密码登录")
    @PostMapping("/auth/b/doLogin")
    public CommonResult<Object> doLogin(@RequestBody @Valid AuthAccountPasswordLoginParam authAccountPasswordLoginParam) {
        return CommonResult.data(authService.doLogin(authAccountPasswordLoginParam, SaClientTypeEnum.B.getValue()));
    }

    /**
     * B端手机验证码登录
     *
     * @author xuyuxiang
     * @date 2021/10/15 13:12
     **/
    @ApiOperationSupport(order = 4)
    @ApiOperation("B端手机验证码登录")
    @PostMapping("/auth/b/doLoginByPhone")
    public CommonResult<Object> doLoginByPhone(@RequestBody @Valid AuthPhoneValidCodeLoginParam authPhoneValidCodeLoginParam) {
        return CommonResult.data(authService.doLoginByPhone(authPhoneValidCodeLoginParam, SaClientTypeEnum.B.getValue()));
    }

    /**
     * B端退出
     *
     * @author xuyuxiang
     * @date 2021/10/15 13:12
     **/
    @ApiOperationSupport(order = 5)
    @ApiOperation("B端退出")
    @SaCheckLogin
    @GetMapping("/auth/b/doLogout")
    public CommonResult<String> doLogout() {
        StpUtil.logout();
        return CommonResult.ok();
    }

    /**
     * B端获取用户信息
     *
     * @author xuyuxiang
     * @date 2021/10/15 13:12
     **/
    @ApiOperationSupport(order = 6)
    @ApiOperation("B端获取用户信息")
    @SaCheckLogin
    @GetMapping("/auth/b/getLoginUser")
    public CommonResult<SaBaseLoginUser> getLoginUser() {
        return CommonResult.data(authService.getLoginUser());
    }


    /**
     * B端账号密码登录
     *
     * @author xuyuxiang
     * @date 2021/10/15 13:12
     **/
    @ApiOperationSupport(order = 7)
    @ApiOperation("CAD端账号密码登录")
    @PostMapping("/auth/b/cadDoLogin")
    public CommonResult<Map<String,String>> cadDoLogin(@RequestBody @Valid AuthAccountPasswordLoginParam authAccountPasswordLoginParam) {
        return CommonResult.data(authService.cadDoLogin(authAccountPasswordLoginParam));
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("获取二维码")
    @GetMapping("/auth/b/generateQRCode")
    public CommonResult<Object> generateQRCode(){
        String uuid = UUID.randomUUID().toString();
        commonCacheOperator.put(QR_UUID+uuid,uuid,180);
        //String qrCodeText = commonProperties.getBackendUrl()+QR_REQUEST + uuid;
        JSONObject dateText = new JSONObject();
        dateText.set("url",commonProperties.getBackendUrl()+QR_REQUEST);
        dateText.set("uuid",uuid);
        dateText.set("type","login");
        byte[] bytes = QrCodeUtil.generatePng(dateText.toString(), 300, 300);
        // 转换为 Base64 编码
        String base64Image = java.util.Base64.getEncoder().encodeToString(bytes);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("codeImage",base64Image);
        dataMap.put("uuid",uuid);
        return CommonResult.data(dataMap);
    }

    @ApiOperationSupport(order = 7)
    @ApiOperation("扫码后设置token")
    @PostMapping("/auth/b/autoLogin")
    public CommonResult<String> autoLogin(@RequestParam(value = "userId") String userId,
                                          @RequestParam(value = "uuid") String uuid) {
        Object o = commonCacheOperator.get(QR_UUID + uuid);
        if (ObjectUtil.isEmpty(o)) {
            throw new CommonException("二维码已过期.");
        }
        commonCacheOperator.put(QR_UUID_USERID+uuid,userId);

        boolean login = StpUtil.isLogin(userId);
        if (login) {
            USER_MAP.put(userId, StpUtil.getTokenValue());

            // 唤醒等待的线程
            CountDownLatch latch = USER_LATCH_MAP.get(userId);
            if (latch != null) {
                latch.countDown();
            }

            // 等待10秒获取 token
            latch = USER_LATCH_MAP.computeIfAbsent(userId, k -> new CountDownLatch(1));
            try {
                // 如果等待成功，返回结果；如果超时，则表示失败
                if (!latch.await(TIMEOUT, TimeUnit.SECONDS)) {
                    throw new CommonException("扫码登录超时，请稍后重试。");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CommonException("扫码登录被中断，请稍后重试。");
            } finally {
                USER_LATCH_MAP.remove(userId); // 确保 latch 被清除
                commonCacheOperator.remove(QR_UUID + uuid);
                commonCacheOperator.remove(QR_UUID_USERID+uuid);
            }

            return CommonResult.ok();
        } else {
            throw new CommonException("用户未登录.");
        }
    }

    @ApiOperationSupport(order = 8)
    @ApiOperation("扫码后获取token")
    @GetMapping("/auth/b/getToken")
    public CommonResult<String> getToken(@RequestParam(value = "uuid") String uuid) {
        Object o = commonCacheOperator.get(QR_UUID_USERID + uuid);
        if(ObjectUtil.isNotEmpty(o)){
            String s = USER_MAP.get(String.valueOf(o));
            if (StrUtil.isNotEmpty(s)) {
                // 如果 token 存在，返回 token 并唤醒 autoLogin
                CountDownLatch latch = USER_LATCH_MAP.get(String.valueOf(o));
                if (latch != null) {
                    latch.countDown();
                }
                USER_MAP.remove(s);
                return CommonResult.data(s);
            }
        }else {
            return  CommonResult.error("扫码登录失败，请刷新后重试。");
        }
        return CommonResult.error("扫码登录失败，请刷新后重试。");
    }
}
