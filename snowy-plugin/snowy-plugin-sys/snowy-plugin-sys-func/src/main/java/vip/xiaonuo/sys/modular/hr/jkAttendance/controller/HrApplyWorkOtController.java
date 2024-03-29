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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrApplyWorkOt;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrApplyWorkOtPageParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.service.HrApplyWorkOtService;
import vip.xiaonuo.sys.modular.hr.jkAttendance.vo.UserWorkOtVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤-申请加班数据表控制器
 *
 * @author ljy
 * @date  2023/10/29 19:57
 */
@Api(tags = "考勤-申请加班数据表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrApplyWorkOtController {

    @Resource
    private HrApplyWorkOtService hrApplyWorkOtService;

    /**
     * 获取考勤-申请加班数据表分页
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤-申请加班数据表分页")
    //@SaCheckPermission("/sys/applyWorkOt/page")
    @GetMapping("/sys/applyWorkOt/page")
    public CommonResult<Page<HrApplyWorkOt>> page(HrApplyWorkOtPageParam hrApplyWorkOtPageParam) {
        return CommonResult.data(hrApplyWorkOtService.page(hrApplyWorkOtPageParam));
    }

    /**
     * 添加考勤-申请加班数据表
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤-申请加班数据表")
    @CommonLog("添加考勤-申请加班数据表")
    //@SaCheckPermission("/sys/applyWorkOt/add")
    @PostMapping("/sys/applyWorkOt/add")
    public CommonResult<String> add(@RequestBody @Valid HrApplyWorkOtAddParam hrApplyWorkOtAddParam) {
        hrApplyWorkOtService.add(hrApplyWorkOtAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤-申请加班数据表
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤-申请加班数据表")
    @CommonLog("编辑考勤-申请加班数据表")
    //@SaCheckPermission("/sys/applyWorkOt/edit")
    @PostMapping("/sys/applyWorkOt/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrApplyWorkOtEditParam hrApplyWorkOtEditParam) {
        hrApplyWorkOtService.edit(hrApplyWorkOtEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤-申请加班数据表
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤-申请加班数据表")
    @CommonLog("删除考勤-申请加班数据表")
    //@SaCheckPermission("/sys/applyWorkOt/delete")
    @PostMapping("/sys/applyWorkOt/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrApplyWorkOtIdParam> hrApplyWorkOtIdParamList) {
        hrApplyWorkOtService.delete(hrApplyWorkOtIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤-申请加班数据表详情
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤-申请加班数据表详情")
    //@SaCheckPermission("/sys/applyWorkOt/detail")
    @GetMapping("/sys/applyWorkOt/detail")
    public CommonResult<HrApplyWorkOt> detail(@Valid HrApplyWorkOtIdParam hrApplyWorkOtIdParam) {
        return CommonResult.data(hrApplyWorkOtService.detail(hrApplyWorkOtIdParam));
    }

    /**
     * 获取考勤-申请加班数据表分页
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取考勤-申请加班数据表分页数据")
    //@SaCheckPermission("/sys/applyWorkOt/userWorkOtPage")
    @GetMapping("/sys/applyWorkOt/userWorkOtPage")
    public CommonResult<Page<UserWorkOtVo>> userWorkOtVoPage(UserWorkOtVo userWorkOtVo) {
        return CommonResult.data(hrApplyWorkOtService.userWorkOtVoPage(userWorkOtVo));
    }

    /**
     * 获取考勤-申请加班数据表分页
     *
     * @author ljy
     * @date  2023/10/29 19:57
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("申请加班的时间计算")
    //@SaCheckPermission("/sys/applyWorkOt/userWorkOtPage")
    @GetMapping("/sys/applyWorkOt/countWorkHours")
    public CommonResult<Double> countWorkHours(String beginTime,String endTime) {
        return CommonResult.data(hrApplyWorkOtService.countWorkHours(beginTime,endTime));
    }
}
