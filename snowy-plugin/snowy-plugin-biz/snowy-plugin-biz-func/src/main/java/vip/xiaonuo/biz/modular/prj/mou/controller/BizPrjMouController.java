package vip.xiaonuo.biz.modular.prj.mou.controller;

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
import vip.xiaonuo.biz.modular.prj.mou.entity.BizPrjMou;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouAddParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouEditParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouIdParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouPageParam;
import vip.xiaonuo.biz.modular.prj.mou.service.BizPrjMouService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目承接备忘录控制器
 *
 * @author yy
 * @date  2025/05/13 09:14
 */
@Api(tags = "项目承接备忘录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizPrjMouController {

    @Resource
    private BizPrjMouService bizPrjMouService;

    /**
     * 获取项目承接备忘录分页
     *
     * @author yy
     * @date  2025/05/13 09:14
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目承接备忘录分页")
//    @SaCheckPermission("/biz/prj/mou/page")
    @GetMapping("/biz/prj/mou/page")
    public CommonResult<Page<BizPrjMou>> page(BizPrjMouPageParam bizPrjMouPageParam) {
        return CommonResult.data(bizPrjMouService.page(bizPrjMouPageParam));
    }

    /**
     * 添加项目承接备忘录
     *
     * @author yy
     * @date  2025/05/13 09:14
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目承接备忘录")
    @CommonLog("添加项目承接备忘录")
//    @SaCheckPermission("/biz/prj/mou/add")
    @PostMapping("/biz/prj/mou/add")
    public CommonResult<String> add(@RequestBody @Valid BizPrjMouAddParam bizPrjMouAddParam) {
        bizPrjMouService.add(bizPrjMouAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目承接备忘录
     *
     * @author yy
     * @date  2025/05/13 09:14
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目承接备忘录")
    @CommonLog("编辑项目承接备忘录")
//    @SaCheckPermission("/biz/prj/mou/edit")
    @PostMapping("/biz/prj/mou/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizPrjMouEditParam bizPrjMouEditParam) {
        bizPrjMouService.edit(bizPrjMouEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目承接备忘录
     *
     * @author yy
     * @date  2025/05/13 09:14
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目承接备忘录")
    @CommonLog("删除项目承接备忘录")
//    @SaCheckPermission("/biz/prj/mou/delete")
    @PostMapping("/biz/prj/mou/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizPrjMouIdParam> bizPrjMouIdParamList) {
        bizPrjMouService.delete(bizPrjMouIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目承接备忘录详情
     *
     * @author yy
     * @date  2025/05/13 09:14
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目承接备忘录详情")
//    @SaCheckPermission("/biz/prj/mou/detail")
    @GetMapping("/biz/prj/mou/detail")
    public CommonResult<BizPrjMou> detail(@Valid BizPrjMouIdParam bizPrjMouIdParam) {
        return CommonResult.data(bizPrjMouService.detail(bizPrjMouIdParam));
    }
}
