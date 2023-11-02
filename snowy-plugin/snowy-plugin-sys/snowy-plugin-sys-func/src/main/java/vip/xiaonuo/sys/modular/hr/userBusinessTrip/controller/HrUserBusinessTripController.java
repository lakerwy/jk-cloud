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
package vip.xiaonuo.sys.modular.hr.userBusinessTrip.controller;

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
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.entity.HrUserBusinessTrip;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.param.*;
import vip.xiaonuo.sys.modular.hr.userBusinessTrip.service.HrUserBusinessTripService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-考勤-用户出差申请实体数据表控制器
 *
 * @author ljy
 * @date  2023/11/02 09:03
 */
@Api(tags = "业务表-考勤-用户出差申请实体")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserBusinessTripController {

    @Resource
    private HrUserBusinessTripService hrUserBusinessTripService;

    /**
     * 获取业务表-考勤-用户出差申请实体数据表分页
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-考勤-用户出差申请实体数据表分页")
    //@SaCheckPermission("/sys/userBusinessTrip/page")
    @GetMapping("/sys/userBusinessTrip/page")
    public CommonResult<Page<HrUserBusinessTrip>> page(HrUserBusinessTripPageParam hrUserBusinessTripPageParam) {
        return CommonResult.data(hrUserBusinessTripService.page(hrUserBusinessTripPageParam));
    }

    /**
     * 添加业务表-考勤-用户出差申请实体数据表
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-考勤-用户出差申请实体数据表")
    @CommonLog("添加业务表-考勤-用户出差申请实体数据表")
    //@SaCheckPermission("/sys/userBusinessTrip/add")
    @PostMapping("/sys/userBusinessTrip/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserBusinessTripAddParam hrUserBusinessTripAddParam) {
        hrUserBusinessTripService.add(hrUserBusinessTripAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-考勤-用户出差申请实体数据表
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-考勤-用户出差申请实体数据表")
    @CommonLog("编辑业务表-考勤-用户出差申请实体数据表")
    //@SaCheckPermission("/sys/userBusinessTrip/edit")
    @PostMapping("/sys/userBusinessTrip/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserBusinessTripEditParam hrUserBusinessTripEditParam) {
        hrUserBusinessTripService.edit(hrUserBusinessTripEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-考勤-用户出差申请实体数据表
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-考勤-用户出差申请实体数据表")
    @CommonLog("删除业务表-考勤-用户出差申请实体数据表")
    //@SaCheckPermission("/sys/userBusinessTrip/delete")
    @PostMapping("/sys/userBusinessTrip/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserBusinessTripIdParam> hrUserBusinessTripIdParamList) {
        hrUserBusinessTripService.delete(hrUserBusinessTripIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-考勤-用户出差申请实体数据表详情
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-考勤-用户出差申请实体数据表详情")
    //@SaCheckPermission("/sys/userBusinessTrip/detail")
    @GetMapping("/sys/userBusinessTrip/detail")
    public CommonResult<HrUserBusinessTrip> detail(@Valid HrUserBusinessTripIdParam hrUserBusinessTripIdParam) {
        return CommonResult.data(hrUserBusinessTripService.detail(hrUserBusinessTripIdParam));
    }


    /**
     * 添加业务表-考勤-用户出差申请
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("添加业务表-考勤-用户出差申请")
    @CommonLog("添加业务表-考勤-用户出差申请")
    //@SaCheckPermission("/sys/userBusinessTrip/addFlw")
    @PostMapping("/sys/userBusinessTrip/addFlw")
    public CommonResult<String> addFlw(@RequestBody @Valid BusinessTripFlwAddParam businessTripFlwAddParam) {
        hrUserBusinessTripService.addFlw(businessTripFlwAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-考勤-用户出差申请实体数据表
     *
     * @author ljy
     * @date  2023/11/02 09:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-考勤-用户出差申请数据修改")
    @CommonLog("编辑业务表-考勤-用户出差申请数据修改")
    //@SaCheckPermission("/sys/userBusinessTrip/edit")
    @PostMapping("/sys/userBusinessTrip/editFlw")
    public CommonResult<String> edit(@RequestBody @Valid BusinessTripFlwAddParam businessTripFlwAddParam) {
        hrUserBusinessTripService.editFlw(businessTripFlwAddParam);
        return CommonResult.ok();
    }
}
