package vip.xiaonuo.sys.modular.hr.sub.controller;

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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFiling;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFilingService;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubFilingVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分包单位备案控制器
 *
 * @author pzh
 * @date  2025/03/11 14:32
 */
@Api(tags = "分包单位备案控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubFilingController {

    @Resource
    private SubFilingService subFilingService;

    /**
     * 获取分包单位备案分页
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取分包单位备案分页")
//    // @SaCheckPermission("/sys/hr/sub/filing/page")
//    @GetMapping("/sys/hr/sub/filing/page")
//    public CommonResult<Page<SubFilingVo>> page(SubFilingPageParam subFilingPageParam) {
//        return CommonResult.data(subFilingService.page(subFilingPageParam));
//    }

    /**
     * 获取分包单位备案列表
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分包单位备案列表")
    @SaCheckPermission("/sys/hr/sub/filing/list")
    @GetMapping("/sys/hr/sub/filing/list")
    public CommonResult<List<SubFilingVo>> list(SubFilingListParam subFilingListParam) {
        return CommonResult.data(subFilingService.getList(subFilingListParam));
    }

    /**
     * 添加分包单位备案
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分包单位备案")
    @CommonLog("添加分包单位备案")
    // @SaCheckPermission("/sys/hr/sub/filing/add")
    @PostMapping("/sys/hr/sub/filing/add")
    public CommonResult<String> add(@RequestBody @Valid SubFilingAddParam subFilingAddParam) {
        subFilingService.add(subFilingAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分包单位备案
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分包单位备案")
    @CommonLog("编辑分包单位备案")
    // @SaCheckPermission("/sys/hr/sub/filing/edit")
    @PostMapping("/sys/hr/sub/filing/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubFilingEditParam subFilingEditParam) {
        subFilingService.edit(subFilingEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分包单位备案
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分包单位备案")
    @CommonLog("删除分包单位备案")
    // @SaCheckPermission("/sys/hr/sub/filing/delete")
    @PostMapping("/sys/hr/sub/filing/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubFilingIdParam> subFilingIdParamList) {
        subFilingService.delete(subFilingIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分包单位备案详情
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分包单位备案详情")
    // @SaCheckPermission("/sys/hr/sub/filing/detail")
    @GetMapping("/sys/hr/sub/filing/detail")
    public CommonResult<SubFiling> detail(@Valid SubFilingIdParam subFilingIdParam) {
        return CommonResult.data(subFilingService.detail(subFilingIdParam));
    }

    /**
     * 分包单位备案 - 校验
     *
     * @author pzh
     * @date  2025/03/11 14:32
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("分包单位备案 - 校验")
    @CommonLog("分包单位备案 - 校验")
    // @SaCheckPermission("/sys/hr/sub/filing/check")
    @PostMapping("/sys/hr/sub/filing/check")
    public CommonResult<String> check(@RequestBody @Valid SubFilingCheckParam subFilingCheckParam) {
        subFilingService.check(subFilingCheckParam);
        return CommonResult.ok();
    }

    /**
     * 分包单位备案 - 导入
     *
     * @author pzh
     * @date  2025/03/14 10:30
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("分包单位备案 - 导入")
    @CommonLog("分包单位备案 - 导入")
    // @SaCheckPermission("/sys/hr/sub/filing/importData")
    @PostMapping("/sys/hr/sub/filing/importData")
    public CommonResult<String> importData(@RequestBody @Valid ImportSubFiling input) {
        subFilingService.importData(input);
        return CommonResult.ok();
    }
}
