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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpDev;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevPageParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpDevService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 财务管理 - 费用报销 - 研发项目信息表控制器
 *
 * @author yong.ye
 * @date  2024/08/01 11:22
 */
@Api(tags = "财务管理 - 费用报销 - 研发项目信息表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizExpDevController {

    @Resource
    private BizExpDevService bizExpDevService;

    /**
     * 获取财务管理 - 费用报销 - 研发项目信息表分页
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务管理 - 费用报销 - 研发项目信息表分页")
//    @SaCheckPermission("/biz/prj/exp/dev/page")
    @GetMapping("/biz/prj/exp/dev/page")
    public CommonResult<Page<BizExpDev>> page(BizExpDevPageParam bizExpDevPageParam) {
        return CommonResult.data(bizExpDevService.page(bizExpDevPageParam));
    }

    /**
     * 添加财务管理 - 费用报销 - 研发项目信息表
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务管理 - 费用报销 - 研发项目信息表")
    @CommonLog("添加财务管理 - 费用报销 - 研发项目信息表")
//    @SaCheckPermission("/biz/prj/exp/dev/add")
    @PostMapping("/biz/prj/exp/dev/add")
    public CommonResult<String> add(@RequestBody @Valid BizExpDevAddParam bizExpDevAddParam) {
        bizExpDevService.add(bizExpDevAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务管理 - 费用报销 - 研发项目信息表
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务管理 - 费用报销 - 研发项目信息表")
    @CommonLog("编辑财务管理 - 费用报销 - 研发项目信息表")
//    @SaCheckPermission("/biz/prj/exp/dev/edit")
    @PostMapping("/biz/prj/exp/dev/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizExpDevEditParam bizExpDevEditParam) {
        bizExpDevService.edit(bizExpDevEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务管理 - 费用报销 - 研发项目信息表
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务管理 - 费用报销 - 研发项目信息表")
    @CommonLog("删除财务管理 - 费用报销 - 研发项目信息表")
//    @SaCheckPermission("/biz/prj/exp/dev/delete")
    @PostMapping("/biz/prj/exp/dev/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizExpDevIdParam> bizExpDevIdParamList) {
        bizExpDevService.delete(bizExpDevIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务管理 - 费用报销 - 研发项目信息表详情
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务管理 - 费用报销 - 研发项目信息表详情")
//    @SaCheckPermission("/biz/prj/exp/dev/detail")
    @GetMapping("/biz/prj/exp/dev/detail")
    public CommonResult<BizExpDev> detail(@Valid BizExpDevIdParam bizExpDevIdParam) {
        return CommonResult.data(bizExpDevService.detail(bizExpDevIdParam));
    }
}
