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
package vip.xiaonuo.oa.modular.attendance.record.controller;

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
import vip.xiaonuo.oa.modular.attendance.record.entity.Record;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordAddParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordEditParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordIdParam;
import vip.xiaonuo.oa.modular.attendance.record.param.RecordPageParam;
import vip.xiaonuo.oa.modular.attendance.record.service.RecordService;
import vip.xiaonuo.oa.modular.attendance.record.vo.RecordPreVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 考勤记录表控制器
 *
 * @author mayike
 * @date  2024/06/12 11:03
 */
@Api(tags = "考勤记录表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class RecordController {

    @Resource
    private RecordService recordService;

    /**
     * 获取考勤记录表分页
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤记录表分页")
//    @SaCheckPermission("/oa/record/page")
    @GetMapping("/oa/record/page")
    public CommonResult<Page<Record>> page(RecordPageParam recordPageParam) {
        return CommonResult.data(recordService.page(recordPageParam));
    }

    /**
     * 添加考勤记录表
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤记录表")
    @CommonLog("添加考勤记录表")
//    @SaCheckPermission("/oa/record/add")
    @PostMapping("/oa/record/add")
    public CommonResult<String> add(@RequestBody @Valid RecordAddParam recordAddParam) {
        recordService.add(recordAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤记录表
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤记录表")
    @CommonLog("编辑考勤记录表")
//    @SaCheckPermission("/oa/record/edit")
    @PostMapping("/oa/record/edit")
    public CommonResult<String> edit(@RequestBody @Valid RecordEditParam recordEditParam) {
        recordService.edit(recordEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤记录表
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤记录表")
    @CommonLog("删除考勤记录表")
//    @SaCheckPermission("/oa/record/delete")
    @PostMapping("/oa/record/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<RecordIdParam> recordIdParamList) {
        recordService.delete(recordIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤记录表详情
     *
     * @author mayike
     * @date  2024/06/12 11:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤记录表详情")
//    @SaCheckPermission("/oa/record/detail")
    @GetMapping("/oa/record/detail")
    public CommonResult<Record> detail(@Valid RecordIdParam recordIdParam) {
        return CommonResult.data(recordService.detail(recordIdParam));
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation("考勤打卡预处理")
    @CommonLog("考勤打卡预处理")
//    @SaCheckPermission("/oa/record/add")
    @GetMapping("/oa/record/preCheck")
    public CommonResult<RecordPreVo> preCheck() {
        return CommonResult.data(recordService.preCheck());
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation("考勤打卡")
    @CommonLog("考勤打卡")
//    @SaCheckPermission("/oa/record/add")
    @GetMapping("/oa/record/checkIn")
    public CommonResult<String> checkIn(@RequestBody @Valid RecordAddParam recordAddParam) {
        recordService.checkIn(recordAddParam);
        return CommonResult.ok();
    }
}
