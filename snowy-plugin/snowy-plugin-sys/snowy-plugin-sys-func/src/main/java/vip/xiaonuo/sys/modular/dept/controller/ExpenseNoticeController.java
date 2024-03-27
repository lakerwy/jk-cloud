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
package vip.xiaonuo.sys.modular.dept.controller;

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
import vip.xiaonuo.sys.modular.dept.entity.ExpenseNotice;
import vip.xiaonuo.sys.modular.dept.param.*;
import vip.xiaonuo.sys.modular.dept.service.ExpenseNoticeService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 部门报销公示控制器
 *
 * @author yong.ye
 * @date  2024/03/27 09:04
 */
@Api(tags = "部门报销公示控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ExpenseNoticeController {

    @Resource
    private ExpenseNoticeService expenseNoticeService;

    /**
     * 获取部门报销公示分页【权限控制】
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取部门报销公示分页【权限控制】")
    @SaCheckPermission("/sys/dept/expense/notice/page")
    @GetMapping("/sys/dept/expense/notice/page")
    public CommonResult<Page<ExpenseNotice>> page(ExpenseNoticePageParam expenseNoticePageParam) {
        return CommonResult.data(expenseNoticeService.page(expenseNoticePageParam));
    }

    /**
     * 添加部门报销公示
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加部门报销公示")
    @CommonLog("添加部门报销公示")
//    @SaCheckPermission("/sys/dept/expense/notice/add")
    @PostMapping("/sys/dept/expense/notice/add")
    public CommonResult<String> add(@RequestBody @Valid ExpenseNoticeAddParam expenseNoticeAddParam) {
        expenseNoticeService.add(expenseNoticeAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑部门报销公示
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑部门报销公示")
    @CommonLog("编辑部门报销公示")
//    @SaCheckPermission("/sys/dept/expense/notice/edit")
    @PostMapping("/sys/dept/expense/notice/edit")
    public CommonResult<String> edit(@RequestBody @Valid ExpenseNoticeEditParam expenseNoticeEditParam) {
        expenseNoticeService.edit(expenseNoticeEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除部门报销公示
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除部门报销公示")
    @CommonLog("删除部门报销公示")
//    @SaCheckPermission("/sys/dept/expense/notice/delete")
    @PostMapping("/sys/dept/expense/notice/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ExpenseNoticeIdParam> expenseNoticeIdParamList) {
        expenseNoticeService.delete(expenseNoticeIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取部门报销公示详情
     *
     * @author yong.ye
     * @date  2024/03/27 09:04
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取部门报销公示详情")
//    @SaCheckPermission("/sys/dept/expense/notice/detail")
    @GetMapping("/sys/dept/expense/notice/detail")
    public CommonResult<ExpenseNotice> detail(@Valid ExpenseNoticeIdParam expenseNoticeIdParam) {
        return CommonResult.data(expenseNoticeService.detail(expenseNoticeIdParam));
    }


    /**
     * 部门报销公示 - 导入 【直接导入模式，无校验】
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("部门报销公示 - 导入 【直接导入模式，无校验】")
    @CommonLog("部门报销公示 - 导入 【直接导入模式，无校验】")
    @PostMapping("/sys/dept/expense/notice/importNotice")
    public CommonResult<String> importNotice(@RequestBody @Valid ImportNotice input) {
        expenseNoticeService.importNotice(input);
        return CommonResult.ok();
    }


    /**
     * 部门报销公示 - 删除全部
     *
     * @author yong.ye
     * @date  2024/03/19 10:24
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("部门报销公示 - 删除全部")
    @CommonLog("部门报销公示 - 删除全部")
    @PostMapping("/sys/dept/expense/notice/delAll")
    public CommonResult<String> delAll() {
        expenseNoticeService.delAll();
        return CommonResult.ok();
    }

}
