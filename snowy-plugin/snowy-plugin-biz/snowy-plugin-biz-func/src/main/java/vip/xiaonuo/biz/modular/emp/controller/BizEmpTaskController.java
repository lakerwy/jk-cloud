/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.emp.controller;

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
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTask;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 业务表-员工绩效考核-季度任务表控制器
 *
 * @author yong.ye
 * @date  2023/09/19 08:47
 */
@Api(tags = "业务表-员工绩效考核-季度任务表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizEmpTaskController {

    @Resource
    private BizEmpTaskService bizEmpTaskService;

    /**
     * 获取业务表-员工绩效考核-季度任务表分页
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-员工绩效考核-季度任务表分页")
//    @SaCheckPermission("/biz/emp/task/page")
    @GetMapping("/biz/emp/task/page")
    public CommonResult<Page<BizEmpTask>> page(BizEmpTaskPageParam bizEmpTaskPageParam) {
        return CommonResult.data(bizEmpTaskService.page(bizEmpTaskPageParam));
    }

    /**
     * 添加业务表-员工绩效考核-季度任务表
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-员工绩效考核-季度任务表")
    @CommonLog("添加业务表-员工绩效考核-季度任务表")
//    @SaCheckPermission("/biz/emp/task/add")
    @PostMapping("/biz/emp/task/add")
    public CommonResult<String> add(@RequestBody @Valid EmpTaskFlwParam empTaskFlwParam) {
        bizEmpTaskService.add(empTaskFlwParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-员工绩效考核-季度任务表
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-员工绩效考核-季度任务表")
    @CommonLog("编辑业务表-员工绩效考核-季度任务表")
//    @SaCheckPermission("/biz/emp/task/edit")
    @PostMapping("/biz/emp/task/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizEmpTaskEditParam bizEmpTaskEditParam) {
        bizEmpTaskService.edit(bizEmpTaskEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-员工绩效考核-季度任务表
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-员工绩效考核-季度任务表")
    @CommonLog("删除业务表-员工绩效考核-季度任务表")
//    @SaCheckPermission("/biz/emp/task/delete")
    @PostMapping("/biz/emp/task/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizEmpTaskIdParam> bizEmpTaskIdParamList) {
        bizEmpTaskService.delete(bizEmpTaskIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-员工绩效考核-季度任务表详情
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-员工绩效考核-季度任务表详情")
//    @SaCheckPermission("/biz/emp/task/detail")
    @GetMapping("/biz/emp/task/detail")
    public CommonResult<BizEmpTask> detail(@Valid BizEmpTaskIdParam bizEmpTaskIdParam) {
        return CommonResult.data(bizEmpTaskService.detail(bizEmpTaskIdParam));
    }

    /**
     * 添加业务表-员工绩效考核-季度任务表(流程中添加 - 批量)
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("添加业务表-员工绩效考核-季度任务表(流程中添加 - 批量)")
    @CommonLog("添加业务表-员工绩效考核-季度任务表(流程中添加 - 批量)")
//    @SaCheckPermission("/biz/emp/task/add")
    @PostMapping("/biz/emp/task/flwAdd")
    public CommonResult<String> flwAdd(@RequestBody @Valid EmpTaskFlwParam empTaskFlwParam) {
        bizEmpTaskService.flwAdd(empTaskFlwParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-员工绩效考核-季度任务表(流程中编辑 - 批量)
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("编辑业务表-员工绩效考核-季度任务表(流程中编辑 - 批量)")
    @CommonLog("编辑业务表-员工绩效考核-季度任务表(流程中编辑 - 批量)")
//    @SaCheckPermission("/biz/emp/task/add")
    @PostMapping("/biz/emp/task/flwEdit")
    public CommonResult<String> flwEdit(@RequestBody @Valid EmpTaskFlwParam empTaskFlwParam) {
        bizEmpTaskService.flwEdit(empTaskFlwParam);
        return CommonResult.ok();
    }


    /**
     * 根据 用户Id+考核年度+考核季度 获取员工当前季度下的任务集合
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("根据 用户Id+考核年度+考核季度 获取员工当前季度下的任务集合")
//    @SaCheckPermission("/biz/emp/task/detail")
    @GetMapping("/biz/emp/task/listByUserId")
    public CommonResult<List<BizEmpTask>> listByUserId(@Valid EmpTaskUserIdParam empTaskUserIdParam) {
        return CommonResult.data(bizEmpTaskService.listByUserId(empTaskUserIdParam));
    }
}
