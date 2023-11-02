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
package vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.controller;

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
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.entity.HrUserBusinessTripFlw;
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.param.HrUserBusinessTripFlwAddParam;
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.param.HrUserBusinessTripFlwEditParam;
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.param.HrUserBusinessTripFlwIdParam;
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.param.HrUserBusinessTripFlwPageParam;
import vip.xiaonuo.sys.modular.hr.userBusinessTripFlw.service.HrUserBusinessTripFlwService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-考勤-用户出差申请流程表控制器
 *
 * @author ljy
 * @date  2023/11/02 08:58
 */
@Api(tags = "业务表-考勤-用户出差申请流程表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserBusinessTripFlwController {

    @Resource
    private HrUserBusinessTripFlwService hrUserBusinessTripFlwService;

    /**
     * 获取业务表-考勤-用户出差申请流程表分页
     *
     * @author ljy
     * @date  2023/11/02 08:58
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-考勤-用户出差申请流程表分页")
    //@SaCheckPermission("/sys/userBusinessTripFlw/page")
    @GetMapping("/sys/userBusinessTripFlw/page")
    public CommonResult<Page<HrUserBusinessTripFlw>> page(HrUserBusinessTripFlwPageParam hrUserBusinessTripFlwPageParam) {
        return CommonResult.data(hrUserBusinessTripFlwService.page(hrUserBusinessTripFlwPageParam));
    }

    /**
     * 添加业务表-考勤-用户出差申请流程表
     *
     * @author ljy
     * @date  2023/11/02 08:58
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-考勤-用户出差申请流程表")
    @CommonLog("添加业务表-考勤-用户出差申请流程表")
    //@SaCheckPermission("/sys/userBusinessTripFlw/add")
    @PostMapping("/sys/userBusinessTripFlw/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserBusinessTripFlwAddParam hrUserBusinessTripFlwAddParam) {
        hrUserBusinessTripFlwService.add(hrUserBusinessTripFlwAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-考勤-用户出差申请流程表
     *
     * @author ljy
     * @date  2023/11/02 08:58
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-考勤-用户出差申请流程表")
    @CommonLog("编辑业务表-考勤-用户出差申请流程表")
    //@SaCheckPermission("/sys/userBusinessTripFlw/edit")
    @PostMapping("/sys/userBusinessTripFlw/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserBusinessTripFlwEditParam hrUserBusinessTripFlwEditParam) {
        hrUserBusinessTripFlwService.edit(hrUserBusinessTripFlwEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-考勤-用户出差申请流程表
     *
     * @author ljy
     * @date  2023/11/02 08:58
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-考勤-用户出差申请流程表")
    @CommonLog("删除业务表-考勤-用户出差申请流程表")
    //@SaCheckPermission("/sys/userBusinessTripFlw/delete")
    @PostMapping("/sys/userBusinessTripFlw/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserBusinessTripFlwIdParam> hrUserBusinessTripFlwIdParamList) {
        hrUserBusinessTripFlwService.delete(hrUserBusinessTripFlwIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-考勤-用户出差申请流程表详情
     *
     * @author ljy
     * @date  2023/11/02 08:58
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-考勤-用户出差申请流程表详情")
    //@SaCheckPermission("/sys/userBusinessTripFlw/detail")
    @GetMapping("/sys/userBusinessTripFlw/detail")
    public CommonResult<HrUserBusinessTripFlw> detail(@Valid HrUserBusinessTripFlwIdParam hrUserBusinessTripFlwIdParam) {
        return CommonResult.data(hrUserBusinessTripFlwService.detail(hrUserBusinessTripFlwIdParam));
    }
}
