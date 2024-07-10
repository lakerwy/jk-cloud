package vip.xiaonuo.im.modular.tencent.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.im.modular.tencent.param.AccountParam;
import vip.xiaonuo.im.modular.tencent.service.TencentImService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/14 10:57
 * 腾讯im后台管理控制器
 */
@Api(tags = "腾讯im控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 0)
@RestController
@Validated
public class TencentImController {
    @Resource
    TencentImService tencentImService;

    /**
     * 导入单个用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("导入单个用户")
    @CommonLog("导入单个用户")
    @PostMapping("/sys/im/account/import")
    public CommonResult<String> accountImport(@RequestBody @Valid AccountParam accountParam) {
        return tencentImService.accountImport(accountParam);
    }

    /**
     * 导入多个用户
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("导入多个用户")
    @CommonLog("导入多个用户")
    @PostMapping("/sys/im/account/multiImport")
    public CommonResult<Object> multiAccountImport(@RequestBody @Valid List<AccountParam> accountParam) {
        return tencentImService.multiAccountImport(accountParam);
    }

    /**
     * 删除账号
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("删除账号")
    @CommonLog("删除账号")
    @PostMapping("/sys/im/account/delete")
    public CommonResult<Object> delete(@RequestBody @Valid List<AccountParam> accountParam) {
        return tencentImService.accountDelete(accountParam);
    }

    /**
     * 查询账户（支持批量）
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("查询账号")
    @CommonLog("查询账号")
    @PostMapping("/sys/im/account/check")
    public CommonResult<Object> check(@RequestBody @Valid List<AccountParam> accountParam) {
        return tencentImService.accountCheck(accountParam);
    }

    @ApiOperationSupport(order = 4)
    @ApiOperation("创建账号userSig")
    @CommonLog("创建账号userSig")
    @PostMapping("/sys/im/createUserSign")
    public CommonResult<Object> createUserSign(@RequestBody @Valid AccountParam accountParam) {
        String userSign = tencentImService.createUserSignByUserId(accountParam.getUserID());
        return CommonResult.data(userSign);
    }
}
