package vip.xiaonuo.biz.modular.prj.fin.controller;

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
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataCon;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataConService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务数据汇总台账 - 施工控制器
 *
 * @author yy
 * @date  2025/06/05 17:22
 */
@Api(tags = "财务数据汇总台账 - 施工控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class FinDataConController {

    @Resource
    private FinDataConService finDataConService;

    /**
     * 获取财务数据汇总台账 - 施工分页
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务数据汇总台账 - 施工分页")
//    @SaCheckPermission("/biz/prj/fin/con/page")
    @GetMapping("/biz/prj/fin/con/page")
    public CommonResult<Page<FinDataCon>> page(FinDataConPageParam finDataConPageParam) {
        return CommonResult.data(finDataConService.page(finDataConPageParam));
    }

    /**
     * 添加财务数据汇总台账 - 施工
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务数据汇总台账 - 施工")
    @CommonLog("添加财务数据汇总台账 - 施工")
//    @SaCheckPermission("/biz/prj/fin/con/add")
    @PostMapping("/biz/prj/fin/con/add")
    public CommonResult<String> add(@RequestBody @Valid FinDataConAddParam finDataConAddParam) {
        finDataConService.add(finDataConAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务数据汇总台账 - 施工
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务数据汇总台账 - 施工")
    @CommonLog("编辑财务数据汇总台账 - 施工")
//    @SaCheckPermission("/biz/prj/fin/con/edit")
    @PostMapping("/biz/prj/fin/con/edit")
    public CommonResult<String> edit(@RequestBody @Valid FinDataConEditParam finDataConEditParam) {
        finDataConService.edit(finDataConEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务数据汇总台账 - 施工
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务数据汇总台账 - 施工")
    @CommonLog("删除财务数据汇总台账 - 施工")
//    @SaCheckPermission("/biz/prj/fin/con/delete")
    @PostMapping("/biz/prj/fin/con/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<FinDataConIdParam> finDataConIdParamList) {
        finDataConService.delete(finDataConIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务数据汇总台账 - 施工详情
     *
     * @author yy
     * @date  2025/06/05 17:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务数据汇总台账 - 施工详情")
//    @SaCheckPermission("/biz/prj/fin/con/detail")
    @GetMapping("/biz/prj/fin/con/detail")
    public CommonResult<FinDataCon> detail(@Valid FinDataConIdParam finDataConIdParam) {
        return CommonResult.data(finDataConService.detail(finDataConIdParam));
    }


    /**
     * 导入 - 施工
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("导入 - 施工")
    @CommonLog("导入 - 施工")
    @PostMapping("/biz/prj/fin/con/doImport")
    public CommonResult<ImportResult> doImport(@RequestBody @Valid ImportParam<ImportFinDataCon> importParam) {
        return CommonResult.data(finDataConService.doImport(importParam));
    }
}
