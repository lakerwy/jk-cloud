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
package vip.xiaonuo.sys.modular.hr.jkAttendance.controller;

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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.OriginalAtdData;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.OriginalAtdDataAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.OriginalAtdDataEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.OriginalAtdDataIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.OriginalAtdDataPageParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.service.OriginalAtdDataService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 统计-员工考勤原始记录统计表控制器
 *
 * @author ljy
 * @date  2023/11/17 11:36
 */
@Api(tags = "统计-员工考勤原始记录统计表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class OriginalAtdDataController {

    @Resource
    private OriginalAtdDataService originalAtdDataService;

    /**
     * 获取统计-员工考勤原始记录统计表分页
     *
     * @author ljy
     * @date  2023/11/17 11:36
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取统计-员工考勤原始记录统计表分页")
    @SaCheckPermission("/sys/atdData/page")
    @GetMapping("/sys/atdData/page")
    public CommonResult<Page<OriginalAtdData>> page(OriginalAtdDataPageParam originalAtdDataPageParam) {
        return CommonResult.data(originalAtdDataService.page(originalAtdDataPageParam));
    }

    /**
     * 添加统计-员工考勤原始记录统计表
     *
     * @author ljy
     * @date  2023/11/17 11:36
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加统计-员工考勤原始记录统计表")
    @CommonLog("添加统计-员工考勤原始记录统计表")
    @SaCheckPermission("/sys/atdData/add")
    @PostMapping("/sys/atdData/add")
    public CommonResult<String> add(@RequestBody @Valid OriginalAtdDataAddParam originalAtdDataAddParam) {
        originalAtdDataService.add(originalAtdDataAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑统计-员工考勤原始记录统计表
     *
     * @author ljy
     * @date  2023/11/17 11:36
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑统计-员工考勤原始记录统计表")
    @CommonLog("编辑统计-员工考勤原始记录统计表")
    @SaCheckPermission("/sys/atdData/edit")
    @PostMapping("/sys/atdData/edit")
    public CommonResult<String> edit(@RequestBody @Valid OriginalAtdDataEditParam originalAtdDataEditParam) {
        originalAtdDataService.edit(originalAtdDataEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除统计-员工考勤原始记录统计表
     *
     * @author ljy
     * @date  2023/11/17 11:36
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除统计-员工考勤原始记录统计表")
    @CommonLog("删除统计-员工考勤原始记录统计表")
    @SaCheckPermission("/sys/atdData/delete")
    @PostMapping("/sys/atdData/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<OriginalAtdDataIdParam> originalAtdDataIdParamList) {
        originalAtdDataService.delete(originalAtdDataIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取统计-员工考勤原始记录统计表详情
     *
     * @author ljy
     * @date  2023/11/17 11:36
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取统计-员工考勤原始记录统计表详情")
    @SaCheckPermission("/sys/atdData/detail")
    @GetMapping("/sys/atdData/detail")
    public CommonResult<OriginalAtdData> detail(@Valid OriginalAtdDataIdParam originalAtdDataIdParam) {
        return CommonResult.data(originalAtdDataService.detail(originalAtdDataIdParam));
    }
}
