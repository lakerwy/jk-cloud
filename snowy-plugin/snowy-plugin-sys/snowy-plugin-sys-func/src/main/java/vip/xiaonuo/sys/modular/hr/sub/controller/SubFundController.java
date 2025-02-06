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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFund;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFundService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 分公司-五险一金（流程）控制器
 *
 * @author yong.ye
 * @date  2025/02/06 09:12
 */
@Api(tags = "分公司-五险一金（流程）控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubFundController {

    @Resource
    private SubFundService subFundService;

    /**
     * 获取分公司-五险一金（流程）分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司-五险一金（流程）分页")
//    @SaCheckPermission("/sys/hr/sub/fund/page")
    @GetMapping("/sys/hr/sub/fund/page")
    public CommonResult<Page<SubFund>> page(SubFundPageParam subFundPageParam) {
        return CommonResult.data(subFundService.page(subFundPageParam));
    }

    /**
     * 添加分公司-五险一金（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司-五险一金（流程）")
    @CommonLog("添加分公司-五险一金（流程）")
//    @SaCheckPermission("/sys/hr/sub/fund/add")
    @PostMapping("/sys/hr/sub/fund/add")
    public CommonResult<String> add(@RequestBody @Valid SubFundParam subFundParam) {
        subFundService.add(subFundParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司-五险一金（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司-五险一金（流程）")
    @CommonLog("编辑分公司-五险一金（流程）")
//    @SaCheckPermission("/sys/hr/sub/fund/edit")
    @PostMapping("/sys/hr/sub/fund/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubFundParam subFundParam) {
        subFundService.edit(subFundParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司-五险一金（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司-五险一金（流程）")
    @CommonLog("删除分公司-五险一金（流程）")
//    @SaCheckPermission("/sys/hr/sub/fund/delete")
    @PostMapping("/sys/hr/sub/fund/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubFundIdParam> subFundIdParamList) {
        subFundService.delete(subFundIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司-五险一金（流程）详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:12
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司-五险一金（流程）详情")
//    @SaCheckPermission("/sys/hr/sub/fund/detail")
    @GetMapping("/sys/hr/sub/fund/detail")
    public CommonResult<SubFund> detail(@Valid SubFundIdParam subFundIdParam) {
        return CommonResult.data(subFundService.detail(subFundIdParam));
    }
}
