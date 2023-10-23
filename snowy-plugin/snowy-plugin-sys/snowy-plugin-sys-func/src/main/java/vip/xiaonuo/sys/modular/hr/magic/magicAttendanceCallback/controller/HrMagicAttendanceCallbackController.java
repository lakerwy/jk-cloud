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
package vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.controller;

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
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.entity.HrMagicAttendanceCallback;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackAddParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackEditParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackIdParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.param.HrMagicAttendanceCallbackPageParam;
import vip.xiaonuo.sys.modular.hr.magic.magicAttendanceCallback.service.HrMagicAttendanceCallbackService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 魔点打卡回调数据表控制器
 *
 * @author ljy
 * @date  2023/10/20 10:40
 */
@Api(tags = "魔点打卡回调数据表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrMagicAttendanceCallbackController {

    @Resource
    private HrMagicAttendanceCallbackService hrMagicAttendanceCallbackService;

    /**
     * 获取魔点打卡回调数据表分页
     *
     * @author ljy
     * @date  2023/10/20 10:40
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取魔点打卡回调数据表分页")
    @SaCheckPermission("/sys/magicAttendanceCallback/page")
    @GetMapping("/sys/magicAttendanceCallback/page")
    public CommonResult<Page<HrMagicAttendanceCallback>> page(HrMagicAttendanceCallbackPageParam hrMagicAttendanceCallbackPageParam) {
        return CommonResult.data(hrMagicAttendanceCallbackService.page(hrMagicAttendanceCallbackPageParam));
    }

    /**
     * 添加魔点打卡回调数据表
     *
     * @author ljy
     * @date  2023/10/20 10:40
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加魔点打卡回调数据表")
    @CommonLog("添加魔点打卡回调数据表")
    @SaCheckPermission("/sys/magicAttendanceCallback/add")
    @PostMapping("/sys/magicAttendanceCallback/add")
    public CommonResult<String> add(@RequestBody @Valid HrMagicAttendanceCallbackAddParam hrMagicAttendanceCallbackAddParam) {
        hrMagicAttendanceCallbackService.add(hrMagicAttendanceCallbackAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑魔点打卡回调数据表
     *
     * @author ljy
     * @date  2023/10/20 10:40
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑魔点打卡回调数据表")
    @CommonLog("编辑魔点打卡回调数据表")
    @SaCheckPermission("/sys/magicAttendanceCallback/edit")
    @PostMapping("/sys/magicAttendanceCallback/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrMagicAttendanceCallbackEditParam hrMagicAttendanceCallbackEditParam) {
        hrMagicAttendanceCallbackService.edit(hrMagicAttendanceCallbackEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除魔点打卡回调数据表
     *
     * @author ljy
     * @date  2023/10/20 10:40
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除魔点打卡回调数据表")
    @CommonLog("删除魔点打卡回调数据表")
    @SaCheckPermission("/sys/magicAttendanceCallback/delete")
    @PostMapping("/sys/magicAttendanceCallback/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrMagicAttendanceCallbackIdParam> hrMagicAttendanceCallbackIdParamList) {
        hrMagicAttendanceCallbackService.delete(hrMagicAttendanceCallbackIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取魔点打卡回调数据表详情
     *
     * @author ljy
     * @date  2023/10/20 10:40
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取魔点打卡回调数据表详情")
    @SaCheckPermission("/sys/magicAttendanceCallback/detail")
    @GetMapping("/sys/magicAttendanceCallback/detail")
    public CommonResult<HrMagicAttendanceCallback> detail(@Valid HrMagicAttendanceCallbackIdParam hrMagicAttendanceCallbackIdParam) {
        return CommonResult.data(hrMagicAttendanceCallbackService.detail(hrMagicAttendanceCallbackIdParam));
    }
}
