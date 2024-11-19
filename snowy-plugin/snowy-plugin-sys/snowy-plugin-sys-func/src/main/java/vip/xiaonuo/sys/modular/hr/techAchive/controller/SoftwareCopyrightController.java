package vip.xiaonuo.sys.modular.hr.techAchive.controller;

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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.SoftwareCopyright;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.SoftwareCopyrightService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 软著信息控制器
 *
 * @author pzh
 * @date  2024/10/17 10:53
 */
@Api(tags = "软著信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SoftwareCopyrightController {

    @Resource
    private SoftwareCopyrightService softwareCopyrightService;

    /**
     * 获取软著信息分页
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取软著信息分页")
    // @SaCheckPermission("/sys/techAchive/softwareCopyright/page")
    @GetMapping("/sys/techAchive/softwareCopyright/page")
    public CommonResult<Page<SoftwareCopyright>> page(SoftwareCopyrightPageParam softwareCopyrightPageParam) {
        return CommonResult.data(softwareCopyrightService.page(softwareCopyrightPageParam));
    }

    /**
     * 添加软著信息
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加软著信息")
    @CommonLog("添加软著信息")
    // @SaCheckPermission("/sys/techAchive/softwareCopyright/add")
    @PostMapping("/sys/techAchive/softwareCopyright/add")
    public CommonResult<String> add(@RequestBody @Valid SoftwareCopyrightAddParam softwareCopyrightAddParam) {
        softwareCopyrightService.add(softwareCopyrightAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑软著信息
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑软著信息")
    @CommonLog("编辑软著信息")
    // @SaCheckPermission("/sys/techAchive/softwareCopyright/edit")
    @PostMapping("/sys/techAchive/softwareCopyright/edit")
    public CommonResult<String> edit(@RequestBody @Valid SoftwareCopyrightEditParam softwareCopyrightEditParam) {
        softwareCopyrightService.edit(softwareCopyrightEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除软著信息
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除软著信息")
    @CommonLog("删除软著信息")
    // @SaCheckPermission("/sys/techAchive/softwareCopyright/delete")
    @PostMapping("/sys/techAchive/softwareCopyright/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<SoftwareCopyrightIdParam> softwareCopyrightIdParamList) {
        softwareCopyrightService.delete(softwareCopyrightIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取软著信息详情
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取软著信息详情")
    // @SaCheckPermission("/sys/techAchive/softwareCopyright/detail")
    @GetMapping("/sys/techAchive/softwareCopyright/detail")
    public CommonResult<SoftwareCopyright> detail(@Valid SoftwareCopyrightIdParam softwareCopyrightIdParam) {
        return CommonResult.data(softwareCopyrightService.detail(softwareCopyrightIdParam));
    }
}
