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
package vip.xiaonuo.oa.modular.attendance.workplace.controller;

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
import vip.xiaonuo.oa.modular.attendance.workplace.entity.WorkPlace;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlaceAddParam;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlaceEditParam;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlaceIdParam;
import vip.xiaonuo.oa.modular.attendance.workplace.param.WorkPlacePageParam;
import vip.xiaonuo.oa.modular.attendance.workplace.service.WorkPlaceService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤打卡位置表控制器
 * 我们是蓝牙打卡
 * 暂时不需要考虑
 *
 * @author mayike
 * @date  2024/06/11 11:58
 */
@Api(tags = "考勤打卡位置表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
@Deprecated
public class WorkPlaceController {

    @Resource
    private WorkPlaceService workPlaceService;

    /**
     * 获取考勤打卡位置表分页
     *
     * @author mayike
     * @date  2024/06/11 11:58
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤打卡位置表分页")
    @SaCheckPermission("/oa/workplace/page")
    @GetMapping("/oa/workplace/page")
    public CommonResult<Page<WorkPlace>> page(WorkPlacePageParam workPlacePageParam) {
        return CommonResult.data(workPlaceService.page(workPlacePageParam));
    }

    /**
     * 添加考勤打卡位置表
     *
     * @author mayike
     * @date  2024/06/11 11:58
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤打卡位置表")
    @CommonLog("添加考勤打卡位置表")
//    @SaCheckPermission("/oa/workplace/add")
    @PostMapping("/oa/workplace/add")
    public CommonResult<String> add(@RequestBody @Valid WorkPlaceAddParam workPlaceAddParam) {
        workPlaceService.add(workPlaceAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤打卡位置表
     *
     * @author mayike
     * @date  2024/06/11 11:58
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤打卡位置表")
    @CommonLog("编辑考勤打卡位置表")
//    @SaCheckPermission("/oa/workplace/edit")
    @PostMapping("/oa/workplace/edit")
    public CommonResult<String> edit(@RequestBody @Valid WorkPlaceEditParam workPlaceEditParam) {
        workPlaceService.edit(workPlaceEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤打卡位置表
     *
     * @author mayike
     * @date  2024/06/11 11:58
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤打卡位置表")
    @CommonLog("删除考勤打卡位置表")
//    @SaCheckPermission("/oa/workplace/delete")
    @PostMapping("/oa/workplace/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<WorkPlaceIdParam> workPlaceIdParamList) {
        workPlaceService.delete(workPlaceIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤打卡位置表详情
     *
     * @author mayike
     * @date  2024/06/11 11:58
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤打卡位置表详情")
//    @SaCheckPermission("/oa/workplace/detail")
    @GetMapping("/oa/workplace/detail")
    public CommonResult<WorkPlace> detail(@Valid WorkPlaceIdParam workPlaceIdParam) {
        return CommonResult.data(workPlaceService.detail(workPlaceIdParam));
    }
}
