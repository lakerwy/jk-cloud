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
package vip.xiaonuo.sys.modular.hr.tryTurn.controller;

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
import vip.xiaonuo.sys.modular.hr.tryTurn.entity.TryDuty;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyAddParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyEditParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyIdParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.param.TryDutyPageParam;
import vip.xiaonuo.sys.modular.hr.tryTurn.service.TryDutyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * HR - 试用期考核责任控制器
 *
 * @author yong.ye
 * @date  2024/06/12 10:13
 */
@Api(tags = "HR - 试用期考核责任控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class TryDutyController {

    @Resource
    private TryDutyService tryDutyService;

    /**
     * 获取HR - 试用期考核责任分页
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取HR - 试用期考核责任分页")
//    @SaCheckPermission("/sys/tryDuty/page")
    @GetMapping("/sys/tryDuty/page")
    public CommonResult<Page<TryDuty>> page(TryDutyPageParam tryDutyPageParam) {
        return CommonResult.data(tryDutyService.page(tryDutyPageParam));
    }

    /**
     * 添加HR - 试用期考核责任
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加HR - 试用期考核责任")
    @CommonLog("添加HR - 试用期考核责任")
//    @SaCheckPermission("/sys/tryDuty/add")
    @PostMapping("/sys/tryDuty/add")
    public CommonResult<String> add(@RequestBody @Valid TryDutyAddParam tryDutyAddParam) {
        tryDutyService.add(tryDutyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑HR - 试用期考核责任
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑HR - 试用期考核责任")
    @CommonLog("编辑HR - 试用期考核责任")
//    @SaCheckPermission("/sys/tryDuty/edit")
    @PostMapping("/sys/tryDuty/edit")
    public CommonResult<String> edit(@RequestBody @Valid TryDutyEditParam tryDutyEditParam) {
        tryDutyService.edit(tryDutyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除HR - 试用期考核责任
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除HR - 试用期考核责任")
    @CommonLog("删除HR - 试用期考核责任")
//    @SaCheckPermission("/sys/tryDuty/delete")
    @PostMapping("/sys/tryDuty/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<TryDutyIdParam> tryDutyIdParamList) {
        tryDutyService.delete(tryDutyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取HR - 试用期考核责任详情
     *
     * @author yong.ye
     * @date  2024/06/12 10:13
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取HR - 试用期考核责任详情")
//    @SaCheckPermission("/sys/tryDuty/detail")
    @GetMapping("/sys/tryDuty/detail")
    public CommonResult<TryDuty> detail(@Valid TryDutyIdParam tryDutyIdParam) {
        return CommonResult.data(tryDutyService.detail(tryDutyIdParam));
    }
}
