package vip.xiaonuo.sys.modular.user.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.auth.core.pojo.SysLoginUser;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.sys.modular.user.param.*;
import vip.xiaonuo.sys.modular.user.service.ThirdUserService;
import vip.xiaonuo.sys.modular.user.vo.EpcRoleVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 第三方用户和系统用户相关操作控制器
 *
 * @author yong.ye
 * @date 2023/6/15 16:13
 */

@Api(tags = "第三方用户控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 9)
@RestController
@Validated
public class ThirdUserController {
    @Resource
    private ThirdUserService thirdUserService;

    /**
     * 钉钉应用跳转（免登）
     *
     * @author yong.ye
     * @date 2023/6/15 09:38
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("钉钉应用跳转（免登）")
    @CommonLog("钉钉应用跳转（免登）")
    @PostMapping("/sys/third/toDoDingDingApplication")
    public CommonResult<String> toDoDingDingApplication(@RequestBody @Valid DingDingLoginParam dingDingLoginParam) {
        return CommonResult.data(thirdUserService.toDoDingDingApplication(dingDingLoginParam));
    }

    /**
     * 钉钉应用,通过系统用户Id，获取钉钉Id
     *
     * @author yong.ye
     * @date 2023/6/15 10:38
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("钉钉应用,获取所有钉钉Id")
    @GetMapping("/sys/third/getAllDingDingIds")
    public CommonResult<List<Map<String,String>>> getAllDingDingIds() {
        return CommonResult.data(thirdUserService.getAllDingDingIds());
    }

    /**
     * 电子名片（绑定）
     *
     * @author yong.ye
     * @date 2023/6/15 10:38
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("电子名片（绑定）")
    @CommonLog("电子名片（绑定）")
    @PostMapping("/sys/third/bindECard")
    public CommonResult<String> bindECard(@RequestBody @Valid WeChatBindEcardParam weChatBindEcardParam) {
        return CommonResult.data(thirdUserService.bindECard(weChatBindEcardParam));
    }

    /**
     * 电子名片，通过微信小程序openid，获取用户信息
     *
     * @author yong.ye
     * @date 2023/6/15 10:38
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("电子名片，通过微信小程序openid，获取用户信息")
    @GetMapping("/sys/third/getUserByOpenId")
    public CommonResult<SysLoginUser> getUserByOpenId(@Valid WeChatUserParam weChatUserParam) {
        return CommonResult.data(thirdUserService.getUserByOpenId(weChatUserParam));
    }

    /**
     * 电子名片，根据系统用户Id，解绑电子名片微信小程序
     *
     * @author yong.ye
     * @date 2023/6/15 10:38
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("电子名片（解绑）")
    @CommonLog("电子名片（解绑）")
    @PostMapping("/sys/third/unBindECard")
    public CommonResult<Boolean> unBindECard(@RequestBody @Valid WeChatUnBindParam weChatUnBindParam) {
        return CommonResult.data(thirdUserService.unBindECard(weChatUnBindParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("同步建科人员到EPC")
    @CommonLog("同步建科人员到EPC")
    @PostMapping("/sys/third/putUserToEpc")
    public CommonResult<String> putUserToEpc(@RequestBody @Valid SystemEpcParam systemEpcParam){
        thirdUserService.putUserToEpc(systemEpcParam);
        return CommonResult.ok();
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("EPC角色数据")
    @CommonLog("EPC角色数据")
    @GetMapping("/sys/third/putUserToEpc")
    public CommonResult<List<EpcRoleVo>> getEpcRole(){
        return CommonResult.data(thirdUserService.getEpcRole());
    }
}
