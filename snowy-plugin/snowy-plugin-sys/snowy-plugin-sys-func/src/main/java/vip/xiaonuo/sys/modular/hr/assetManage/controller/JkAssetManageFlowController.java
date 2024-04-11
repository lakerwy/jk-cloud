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
package vip.xiaonuo.sys.modular.hr.assetManage.controller;

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
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManage;
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManageFlow;
import vip.xiaonuo.sys.modular.hr.assetManage.param.*;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageFlowService;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 建科资产管理权限申请表控制器
 *
 * @author ljy
 * @date  2024/04/10 08:56
 */
@Api(tags = "建科资产管理权限申请表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class JkAssetManageFlowController {

    @Resource
    private JkAssetManageFlowService jkAssetManageFlowService;

    @Resource
    private JkAssetManageService jkAssetManageService;


    /**
     * 获取建科资产管理权限申请表分页
     *
     * @author ljy
     * @date  2024/04/10 08:56
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取建科资产管理权限申请表分页")
    @GetMapping("/sys/assetManageFlow/page")
    public CommonResult<Page<JkAssetManageFlow>> page(JkAssetManageFlowPageParam jkAssetManageFlowPageParam) {
        return CommonResult.data(jkAssetManageFlowService.page(jkAssetManageFlowPageParam));
    }

    /**
     * 添加建科资产管理权限申请表
     *
     * @author ljy
     * @date  2024/04/10 08:56
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加建科资产管理权限申请表")
    @CommonLog("添加建科资产管理权限申请表")
    @PostMapping("/sys/assetManageFlow/add")
    public CommonResult<String> add(@RequestBody @Valid JkAssetManageFlowAddParam jkAssetManageFlowAddParam) {
        jkAssetManageFlowService.add(jkAssetManageFlowAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑建科资产管理权限申请表
     *
     * @author ljy
     * @date  2024/04/10 08:56
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑建科资产管理权限申请表")
    @CommonLog("编辑建科资产管理权限申请表")
    @PostMapping("/sys/assetManageFlow/edit")
    public CommonResult<String> edit(@RequestBody @Valid JkAssetManageFlowEditParam jkAssetManageFlowEditParam) {
        jkAssetManageFlowService.edit(jkAssetManageFlowEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除建科资产管理权限申请表
     *
     * @author ljy
     * @date  2024/04/10 08:56
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除建科资产管理权限申请表")
    @CommonLog("删除建科资产管理权限申请表")
    @PostMapping("/sys/assetManageFlow/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<JkAssetManageFlowIdParam> jkAssetManageFlowIdParamList) {
        jkAssetManageFlowService.delete(jkAssetManageFlowIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取建科资产管理权限申请表详情
     *
     * @author ljy
     * @date  2024/04/10 08:56
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取建科资产管理权限申请表详情")
    @GetMapping("/sys/assetManageFlow/detail")
    public CommonResult<JkAssetManageFlow> detail(@Valid JkAssetManageFlowIdParam jkAssetManageFlowIdParam) {
        return CommonResult.data(jkAssetManageFlowService.detail(jkAssetManageFlowIdParam));
    }


    /**   建科资产管理接口   */

    /**
     * 获取建科资产管理表分页
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取建科资产管理表分页")
    @GetMapping("/sys/assetManage/pageAssetManage")
    public CommonResult<Page<JkAssetManage>> pageAssetManage(JkAssetManagePageParam jkAssetManagePageParam) {
        return CommonResult.data(jkAssetManageService.page(jkAssetManagePageParam));
    }

    /**
     * 添加建科资产管理表
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("添加建科资产管理表")
    @CommonLog("添加建科资产管理表")
    @PostMapping("/sys/assetManage/addAssetManage")
    public CommonResult<String> addAssetManage(@RequestBody @Valid JkAssetManageAddParam jkAssetManageAddParam) {
        jkAssetManageService.add(jkAssetManageAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑建科资产管理表
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑建科资产管理表")
    @CommonLog("编辑建科资产管理表")
    @PostMapping("/sys/assetManage/editAssetManage")
    public CommonResult<String> editAssetManage(@RequestBody @Valid JkAssetManageEditParam jkAssetManageEditParam) {
        jkAssetManageService.edit(jkAssetManageEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除建科资产管理表
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("删除建科资产管理表")
    @CommonLog("删除建科资产管理表")
    @PostMapping("/sys/assetManage/deleteAssetManage")
    public CommonResult<String> deleteAssetManage(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<JkAssetManageIdParam> jkAssetManageIdParamList) {
        jkAssetManageService.delete(jkAssetManageIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取建科资产管理表详情
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取建科资产管理表详情")
    @GetMapping("/sys/assetManage/detailAssetManage")
    public CommonResult<JkAssetManage> detailAssetManage(@Valid JkAssetManageIdParam jkAssetManageIdParam) {
        return CommonResult.data(jkAssetManageService.detail(jkAssetManageIdParam));
    }

    /**
     * 申请资产查看流程，获取建科资产管理表详情
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("申请资产查看流程:获取建科资产管理表详情")
    @GetMapping("/sys/assetManage/detailManageFlow")
    public CommonResult<List<JkAssetManage>> detailManageFlow(@Valid JkAssetFlowManageIdParam jkAssetFlowManageIdParam) {
        return CommonResult.data(jkAssetManageService.detailManageFlow(jkAssetFlowManageIdParam));
    }
}
