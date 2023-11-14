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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.controller;

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
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param.*;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * 考勤班次组配置表控制器
 *
 * @author ljy
 * @date  2023/09/21 15:55
 */
@Api(tags = "考勤班次组配置表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserAttendanceConfigGroupController {

    @Resource
    private HrUserAttendanceConfigGroupService hrUserAttendanceConfigGroupService;

    /**
     * 获取考勤班次组配置表分页
     *
     * @author ljy
     * @date  2023/09/21 15:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取考勤班次组配置表分页")
    //@SaCheckPermission("/sys/userAttendanceConfigGroup/page")
    @GetMapping("/sys/userAttendanceConfigGroup/page")
    public CommonResult<Page<HrUserAttendanceConfigGroup>> page(HrUserAttendanceConfigGroupPageParam hrUserAttendanceConfigGroupPageParam) {
        return CommonResult.data(hrUserAttendanceConfigGroupService.page(hrUserAttendanceConfigGroupPageParam));
    }

    /**
     * 添加考勤班次组配置表
     *
     * @author ljy
     * @date  2023/09/21 15:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加考勤班次组配置表")
    @CommonLog("添加考勤班次组配置表")
    //@SaCheckPermission("/sys/userAttendanceConfigGroup/add")
    @PostMapping("/sys/userAttendanceConfigGroup/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserAttendanceConfigGroupAddParam hrUserAttendanceConfigGroupAddParam) {
        hrUserAttendanceConfigGroupService.add(hrUserAttendanceConfigGroupAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑考勤班次组配置表
     *
     * @author ljy
     * @date  2023/09/21 15:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑考勤班次组配置表")
    @CommonLog("编辑考勤班次组配置表")
    //@SaCheckPermission("/sys/userAttendanceConfigGroup/edit")
    @PostMapping("/sys/userAttendanceConfigGroup/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrUserAttendanceConfigGroupEditParam hrUserAttendanceConfigGroupEditParam) {
        hrUserAttendanceConfigGroupService.edit(hrUserAttendanceConfigGroupEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除考勤班次组配置表
     *
     * @author ljy
     * @date  2023/09/21 15:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除考勤班次组配置表")
    @CommonLog("删除考勤班次组配置表")
    //@SaCheckPermission("/sys/userAttendanceConfigGroup/delete")
    @PostMapping("/sys/userAttendanceConfigGroup/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserAttendanceConfigGroupIdParam> hrUserAttendanceConfigGroupIdParamList) {
        hrUserAttendanceConfigGroupService.delete(hrUserAttendanceConfigGroupIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取考勤班次组配置表详情
     *
     * @author ljy
     * @date  2023/09/21 15:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取考勤班次组配置表详情")
    //@SaCheckPermission("/sys/userAttendanceConfigGroup/detail")
    @GetMapping("/sys/userAttendanceConfigGroup/detail")
    public CommonResult<HrUserAttendanceConfigGroup> detail(@Valid HrUserAttendanceConfigGroupIdParam hrUserAttendanceConfigGroupIdParam) {
        return CommonResult.data(hrUserAttendanceConfigGroupService.detail(hrUserAttendanceConfigGroupIdParam));
    }

    /**
     * 获取考勤班次组配置表详情
     *
     * @author ljy
     * @date  2023/09/21 15:55
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取考勤班次组配置表详情")
    //@SaCheckPermission("/sys/userAttendanceConfigGroup/configAndUserData")
    @GetMapping("/sys/userAttendanceConfigGroup/configAndUserData")
    public CommonResult<Map<String,Object>> attendanceConfigGroupMap(@Valid HrAttendanceConfigAndUserParam hrAttendanceConfigAndUserParam) {
        return CommonResult.data(hrUserAttendanceConfigGroupService.attendanceConfigGroupMap(hrAttendanceConfigAndUserParam));
    }

    /**
     * 考勤组负责人及考勤人员信息设置
     *
     * @author ljy
     * @date  2023/09/26 15:55
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("考勤组负责人及考勤人员信息设置")
    @CommonLog("考勤组负责人及考勤人员信息设置")
    //@SaCheckPermission("/sys/userAttendanceConfigGroup/configAndUserDataEdit")
    @PostMapping("/sys/userAttendanceConfigGroup/configAndUserDataEdit")
    public CommonResult<String> configAndUserDataEdit(@RequestBody @Valid HrAttendanceConfigAndUserDataParam hrAttendanceConfigAndUserDataParam) {
        return CommonResult.data(hrUserAttendanceConfigGroupService.configAndUserDataEdit(hrAttendanceConfigAndUserDataParam));
    }

}
