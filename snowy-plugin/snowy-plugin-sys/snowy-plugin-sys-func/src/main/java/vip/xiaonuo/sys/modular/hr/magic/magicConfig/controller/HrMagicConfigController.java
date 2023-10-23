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
package vip.xiaonuo.sys.modular.hr.magic.magicConfig.controller;

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
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.entity.HrMagicConfig;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigAddParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigEditParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigIdParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.param.HrMagicConfigPageParam;
import vip.xiaonuo.sys.modular.hr.magic.magicConfig.service.HrMagicConfigService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 魔点请求参数配置表控制器
 *
 * @author ljy
 * @date  2023/10/18 16:44
 */
@Api(tags = "魔点请求参数配置表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrMagicConfigController {

    @Resource
    private HrMagicConfigService hrMagicConfigService;

    /**
     * 获取魔点请求参数配置表分页
     *
     * @author ljy
     * @date  2023/10/18 16:44
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取魔点请求参数配置表分页")
    @SaCheckPermission("/sys/magicConfig/page")
    @GetMapping("/sys/magicConfig/page")
    public CommonResult<Page<HrMagicConfig>> page(HrMagicConfigPageParam hrMagicConfigPageParam) {
        return CommonResult.data(hrMagicConfigService.page(hrMagicConfigPageParam));
    }

    /**
     * 添加魔点请求参数配置表
     *
     * @author ljy
     * @date  2023/10/18 16:44
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加魔点请求参数配置表")
    @CommonLog("添加魔点请求参数配置表")
    @SaCheckPermission("/sys/magicConfig/add")
    @PostMapping("/sys/magicConfig/add")
    public CommonResult<String> add(@RequestBody @Valid HrMagicConfigAddParam hrMagicConfigAddParam) {
        hrMagicConfigService.add(hrMagicConfigAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑魔点请求参数配置表
     *
     * @author ljy
     * @date  2023/10/18 16:44
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑魔点请求参数配置表")
    @CommonLog("编辑魔点请求参数配置表")
    @SaCheckPermission("/sys/magicConfig/edit")
    @PostMapping("/sys/magicConfig/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrMagicConfigEditParam hrMagicConfigEditParam) {
        hrMagicConfigService.edit(hrMagicConfigEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除魔点请求参数配置表
     *
     * @author ljy
     * @date  2023/10/18 16:44
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除魔点请求参数配置表")
    @CommonLog("删除魔点请求参数配置表")
    @SaCheckPermission("/sys/magicConfig/delete")
    @PostMapping("/sys/magicConfig/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrMagicConfigIdParam> hrMagicConfigIdParamList) {
        hrMagicConfigService.delete(hrMagicConfigIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取魔点请求参数配置表详情
     *
     * @author ljy
     * @date  2023/10/18 16:44
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取魔点请求参数配置表详情")
    @SaCheckPermission("/sys/magicConfig/detail")
    @GetMapping("/sys/magicConfig/detail")
    public CommonResult<HrMagicConfig> detail(@Valid HrMagicConfigIdParam hrMagicConfigIdParam) {
        return CommonResult.data(hrMagicConfigService.detail(hrMagicConfigIdParam));
    }
}
