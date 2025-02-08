package vip.xiaonuo.biz.modular.cmb.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.cmb.entity.CmbCallback;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackAddParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackEditParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackIdParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackPageParam;
import vip.xiaonuo.biz.modular.cmb.service.CmbCallbackService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 招商银行 - 回调记录控制器
 *
 * @author pzh
 * @date  2025/02/08 10:09
 */
@Api(tags = "招商银行 - 回调记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CmbCallbackController {

    @Resource
    private CmbCallbackService cmbCallbackService;

    /**
     * 获取招商银行 - 回调记录分页
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取招商银行 - 回调记录分页")
    @GetMapping("/biz/cmb/callback/page")
    public CommonResult<Page<CmbCallback>> page(CmbCallbackPageParam cmbCallbackPageParam) {
        return CommonResult.data(cmbCallbackService.page(cmbCallbackPageParam));
    }

    /**
     * 添加招商银行 - 回调记录
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加招商银行 - 回调记录")
    @CommonLog("添加招商银行 - 回调记录")
    @PostMapping("/biz/cmb/callback/add")
    public CommonResult<String> add(@RequestBody @Valid CmbCallbackAddParam cmbCallbackAddParam) {
        cmbCallbackService.add(cmbCallbackAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑招商银行 - 回调记录
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑招商银行 - 回调记录")
    @CommonLog("编辑招商银行 - 回调记录")
    @PostMapping("/biz/cmb/callback/edit")
    public CommonResult<String> edit(@RequestBody @Valid CmbCallbackEditParam cmbCallbackEditParam) {
        cmbCallbackService.edit(cmbCallbackEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除招商银行 - 回调记录
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除招商银行 - 回调记录")
    @CommonLog("删除招商银行 - 回调记录")
    @PostMapping("/biz/cmb/callback/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<CmbCallbackIdParam> cmbCallbackIdParamList) {
        cmbCallbackService.delete(cmbCallbackIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取招商银行 - 回调记录详情
     *
     * @author pzh
     * @date  2025/02/08 10:09
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取招商银行 - 回调记录详情")
    @GetMapping("/biz/cmb/callback/detail")
    public CommonResult<CmbCallback> detail(@Valid CmbCallbackIdParam cmbCallbackIdParam) {
        return CommonResult.data(cmbCallbackService.detail(cmbCallbackIdParam));
    }
}
