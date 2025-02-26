package vip.xiaonuo.sys.modular.hr.icManage.controller;

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
import vip.xiaonuo.sys.modular.hr.icManage.entity.IcManage;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageAddParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageEditParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManageIdParam;
import vip.xiaonuo.sys.modular.hr.icManage.param.IcManagePageParam;
import vip.xiaonuo.sys.modular.hr.icManage.service.IcManageService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 工商管理控制器
 *
 * @author pzh
 * @date  2025/02/25 16:33
 */
@Api(tags = "工商管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class IcManageController {

    @Resource
    private IcManageService icManageService;

    /**
     * 获取工商管理分页
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工商管理分页")
    // @SaCheckPermission("/sys/icManage/page")
    @GetMapping("/sys/icManage/page")
    public CommonResult<Page<IcManage>> page(IcManagePageParam icManagePageParam) {
        return CommonResult.data(icManageService.page(icManagePageParam));
    }

    /**
     * 添加工商管理
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工商管理")
    @CommonLog("添加工商管理")
    // @SaCheckPermission("/sys/icManage/add")
    @PostMapping("/sys/icManage/add")
    public CommonResult<String> add(@RequestBody @Valid IcManageAddParam icManageAddParam) {
        icManageService.add(icManageAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工商管理
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工商管理")
    @CommonLog("编辑工商管理")
    // @SaCheckPermission("/sys/icManage/edit")
    @PostMapping("/sys/icManage/edit")
    public CommonResult<String> edit(@RequestBody @Valid IcManageEditParam icManageEditParam) {
        icManageService.edit(icManageEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工商管理
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工商管理")
    @CommonLog("删除工商管理")
    // @SaCheckPermission("/sys/icManage/delete")
    @PostMapping("/sys/icManage/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<IcManageIdParam> icManageIdParamList) {
        icManageService.delete(icManageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工商管理详情
     *
     * @author pzh
     * @date  2025/02/25 16:33
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工商管理详情")
    // @SaCheckPermission("/sys/icManage/detail")
    @GetMapping("/sys/icManage/detail")
    public CommonResult<IcManage> detail(@Valid IcManageIdParam icManageIdParam) {
        return CommonResult.data(icManageService.detail(icManageIdParam));
    }
}
