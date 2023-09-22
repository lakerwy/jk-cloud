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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfig.controller;

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
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.param.HrUserAttendanceConfigPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.service.HrUserAttendanceConfigService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 打卡班次配置表控制器
 *
 * @author ljy
 * @date  2023/09/07 13:07
 */
@Api(tags = "打卡班次配置表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrUserAttendanceConfigController {

    @Resource
    private HrUserAttendanceConfigService hrUserAttendanceConfigService;

    /**
     * 获取打卡班次配置表分页
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取打卡班次配置表分页")
    //@SaCheckPermission("/sys/userAttendanceConfig/page")
    @GetMapping("/sys/userAttendanceConfig/page")
    public CommonResult<Page<HrUserAttendanceConfig>> page(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam) {
        return CommonResult.data(hrUserAttendanceConfigService.page(hrUserAttendanceConfigPageParam));
    }

    /**
     * 添加打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加单个打卡班次配置表")
    @CommonLog("添加单个打卡班次配置表")
    //@SaCheckPermission("/sys/userAttendanceConfig/add")
    @PostMapping("/sys/userAttendanceConfig/add")
    public CommonResult<String> add(@RequestBody @Valid HrUserAttendanceConfigAddParam hrUserAttendanceConfigAddParam) {
        hrUserAttendanceConfigService.add(hrUserAttendanceConfigAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑打卡班次配置表--未启用
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑打卡班次配置表--未启用")
    @CommonLog("编辑打卡班次配置表--未启用")
    //@SaCheckPermission("/sys/userAttendanceConfig/edit")
    @PostMapping("/sys/userAttendanceConfig/edit")
    public CommonResult<String> editNo(@RequestBody @Valid HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam) {
        hrUserAttendanceConfigService.edit(hrUserAttendanceConfigEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除打卡班次配置表")
    @CommonLog("删除打卡班次配置表")
    //@SaCheckPermission("/sys/userAttendanceConfig/delete")
    @PostMapping("/sys/userAttendanceConfig/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrUserAttendanceConfigIdParam> hrUserAttendanceConfigIdParamList) {
        hrUserAttendanceConfigService.delete(hrUserAttendanceConfigIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取打卡班次配置表详情
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取打卡班次配置表详情")
    //@SaCheckPermission("/sys/userAttendanceConfig/detail")
    @GetMapping("/sys/userAttendanceConfig/detail")
    public CommonResult<HrUserAttendanceConfig> detail(@Valid HrUserAttendanceConfigIdParam hrUserAttendanceConfigIdParam) {
        return CommonResult.data(hrUserAttendanceConfigService.detail(hrUserAttendanceConfigIdParam));
    }

    /**
     * 获取打卡班次列表
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取打卡班次列表")
    //@SaCheckPermission("/sys/userAttendanceConfig/attendanceconfiglist")
    @GetMapping("/sys/userAttendanceConfig/attendanceconfiglist")
    public CommonResult<Map<String,Object>> attendanceConfigList(HrUserAttendanceConfigPageParam hrUserAttendanceConfigPageParam) {
        return CommonResult.data(hrUserAttendanceConfigService.attendanceConfigList(hrUserAttendanceConfigPageParam));
    }

    /**
     * 批量添加打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/21 11:07
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("批量添加打卡班次配置表")
    @CommonLog("批量添加打卡班次配置表")
    //@SaCheckPermission("/sys/userAttendanceConfig/addlist")
    @PostMapping("/sys/userAttendanceConfig/addlist")
    public CommonResult<String> addList(@RequestBody @Valid HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam) {
        return CommonResult.data(hrUserAttendanceConfigService.addList(hrUserAttendanceConfigEditParam));
    }

    /**
     * 编辑打卡班次配置表
     *
     * @author ljy
     * @date  2023/09/07 13:07
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑打卡班次配置表")
    @CommonLog("编辑打卡班次配置表")
    //@SaCheckPermission("/sys/userAttendanceConfig/edit")
    @PostMapping("/sys/userAttendanceConfig/editConfig")
    public CommonResult<String> editConfig(@RequestBody @Valid HrUserAttendanceConfigEditParam hrUserAttendanceConfigEditParam) {
        return CommonResult.data(hrUserAttendanceConfigService.editConfig(hrUserAttendanceConfigEditParam));
    }
}
