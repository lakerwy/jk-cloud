package vip.xiaonuo.sys.modular.hr.h.controller;

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
import vip.xiaonuo.sys.modular.hr.h.entity.HReg;
import vip.xiaonuo.sys.modular.hr.h.param.HRegAddParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegEditParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegIdParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegPageParam;
import vip.xiaonuo.sys.modular.hr.h.service.HRegService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 招待事项登记控制器
 *
 * @author yy
 * @date  2025/05/06 16:11
 */
@Api(tags = "招待事项登记控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HRegController {

    @Resource
    private HRegService hRegService;

    /**
     * 获取招待事项登记分页
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取招待事项登记分页")
    @SaCheckPermission("/sys/hr/hReg/page")
    @GetMapping("/sys/hr/hReg/page")
    public CommonResult<Page<HReg>> page(HRegPageParam hRegPageParam) {
        return CommonResult.data(hRegService.page(hRegPageParam));
    }

    /**
     * 添加招待事项登记
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加招待事项登记")
    @CommonLog("添加招待事项登记")
//    @SaCheckPermission("/sys/hr/hReg/add")
    @PostMapping("/sys/hr/hReg/add")
    public CommonResult<String> add(@RequestBody @Valid HRegAddParam hRegAddParam) {
        hRegService.add(hRegAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑招待事项登记
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑招待事项登记")
    @CommonLog("编辑招待事项登记")
//    @SaCheckPermission("/sys/hr/hReg/edit")
    @PostMapping("/sys/hr/hReg/edit")
    public CommonResult<String> edit(@RequestBody @Valid HRegEditParam hRegEditParam) {
        hRegService.edit(hRegEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除招待事项登记
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除招待事项登记")
    @CommonLog("删除招待事项登记")
//    @SaCheckPermission("/sys/hr/hReg/delete")
    @PostMapping("/sys/hr/hReg/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HRegIdParam> hRegIdParamList) {
        hRegService.delete(hRegIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取招待事项登记详情
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取招待事项登记详情")
//    @SaCheckPermission("/sys/hr/hReg/detail")
    @GetMapping("/sys/hr/hReg/detail")
    public CommonResult<HReg> detail(@Valid HRegIdParam hRegIdParam) {
        return CommonResult.data(hRegService.detail(hRegIdParam));
    }




    /**
     * 获取招待事项登记分页（无权限控制）
     *
     * @author yy
     * @date  2025/05/06 16:11
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取招待事项登记分页（无权限控制）")
    @GetMapping("/sys/hr/hReg/pageList")
    public CommonResult<Page<HReg>> pageList(HRegPageParam hRegPageParam) {
        return CommonResult.data(hRegService.pageList(hRegPageParam));
    }
}
