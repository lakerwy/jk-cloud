package vip.xiaonuo.biz.modular.prj.expense.controller;

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
import vip.xiaonuo.biz.modular.prj.expense.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizFinCode;
import vip.xiaonuo.biz.modular.prj.expense.service.BizFinCodeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务管理 - 费用报销 - 财务收单编码库控制器
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 */
@Api(tags = "财务管理 - 费用报销 - 财务收单编码库控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFinCodeController {

    @Resource
    private BizFinCodeService bizFinCodeService;

    /**
     * 获取财务管理 - 费用报销 - 财务收单编码库分页
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务管理 - 费用报销 - 财务收单编码库分页")
//    @SaCheckPermission("/biz/prj/finCode/page")
    @GetMapping("/biz/prj/finCode/page")
    public CommonResult<Page<BizFinCode>> page(BizFinCodePageParam bizFinCodePageParam) {
        return CommonResult.data(bizFinCodeService.page(bizFinCodePageParam));
    }

    /**
     * 添加财务管理 - 费用报销 - 财务收单编码库
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务管理 - 费用报销 - 财务收单编码库")
    @CommonLog("添加财务管理 - 费用报销 - 财务收单编码库")
//    @SaCheckPermission("/biz/prj/finCode/add")
    @PostMapping("/biz/prj/finCode/add")
    public CommonResult<String> add(@RequestBody @Valid BizFinCodeAddParam bizFinCodeAddParam) {
        bizFinCodeService.add(bizFinCodeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务管理 - 费用报销 - 财务收单编码库
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务管理 - 费用报销 - 财务收单编码库")
    @CommonLog("编辑财务管理 - 费用报销 - 财务收单编码库")
//    @SaCheckPermission("/biz/prj/finCode/edit")
    @PostMapping("/biz/prj/finCode/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFinCodeEditParam bizFinCodeEditParam) {
        bizFinCodeService.edit(bizFinCodeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务管理 - 费用报销 - 财务收单编码库
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务管理 - 费用报销 - 财务收单编码库")
    @CommonLog("删除财务管理 - 费用报销 - 财务收单编码库")
//    @SaCheckPermission("/biz/prj/finCode/delete")
    @PostMapping("/biz/prj/finCode/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFinCodeIdParam> bizFinCodeIdParamList) {
        bizFinCodeService.delete(bizFinCodeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务管理 - 费用报销 - 财务收单编码库详情
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务管理 - 费用报销 - 财务收单编码库详情")
//    @SaCheckPermission("/biz/prj/finCode/detail")
    @GetMapping("/biz/prj/finCode/detail")
    public CommonResult<BizFinCode> detail(@Valid BizFinCodeIdParam bizFinCodeIdParam) {
        return CommonResult.data(bizFinCodeService.detail(bizFinCodeIdParam));
    }


    /**
     * 删除财务管理 - 费用报销 - 财务收单编码库 - 生成编码
     *
     * @author yong.ye
     * @date  2024/08/01 11:21
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("删除财务管理 - 费用报销 - 财务收单编码库 - 生成编码")
    @CommonLog("删除财务管理 - 费用报销 - 财务收单编码库 - 生成编码")
//    @SaCheckPermission("/biz/prj/finCode/genFinCode")
    @PostMapping("/biz/prj/finCode/genFinCode")
    public CommonResult<String> genFinCode(@RequestBody @Valid FinCodeParam finCodeParam) {
        return CommonResult.data(bizFinCodeService.genFinCode(finCodeParam));
    }
}
