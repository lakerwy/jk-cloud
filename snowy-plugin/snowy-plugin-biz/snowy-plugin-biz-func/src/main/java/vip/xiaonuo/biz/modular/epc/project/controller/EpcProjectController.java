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
package vip.xiaonuo.biz.modular.epc.project.controller;

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
import vip.xiaonuo.biz.modular.epc.project.entity.EpcProject;
import vip.xiaonuo.biz.modular.epc.project.param.*;
import vip.xiaonuo.biz.modular.epc.project.service.EpcProjectService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * EPC-项目管理表控制器
 *
 * @author yong.ye
 * @date  2023/09/05 14:55
 */
@Api(tags = "EPC-项目管理表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EpcProjectController {

    @Resource
    private EpcProjectService epcProjectService;

    /**
     * 获取EPC-项目管理表分页
     *
     * @author yong.ye
     * @date  2023/09/05 14:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取EPC-项目管理表分页")
//    @SaCheckPermission("/biz/epc/project/page")
    @GetMapping("/biz/epc/project/page")
    public CommonResult<Page<EpcProject>> page(EpcProjectPageParam epcProjectPageParam) {
        return CommonResult.data(epcProjectService.page(epcProjectPageParam));
    }

    /**
     * 添加EPC-项目管理表
     *
     * @author yong.ye
     * @date  2023/09/05 14:55
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加EPC-项目管理表")
    @CommonLog("添加EPC-项目管理表")
//    @SaCheckPermission("/biz/epc/project/add")
    @PostMapping("/biz/epc/project/add")
    public CommonResult<String> add(@RequestBody @Valid EpcProjectAddParam epcProjectAddParam) {
        epcProjectService.add(epcProjectAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑EPC-项目管理表
     *
     * @author yong.ye
     * @date  2023/09/05 14:55
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑EPC-项目管理表")
    @CommonLog("编辑EPC-项目管理表")
//    @SaCheckPermission("/biz/epc/project/edit")
    @PostMapping("/biz/epc/project/edit")
    public CommonResult<String> edit(@RequestBody @Valid EpcProjectEditParam epcProjectEditParam) {
        epcProjectService.edit(epcProjectEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除EPC-项目管理表
     *
     * @author yong.ye
     * @date  2023/09/05 14:55
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除EPC-项目管理表")
    @CommonLog("删除EPC-项目管理表")
//    @SaCheckPermission("/biz/epc/project/delete")
    @PostMapping("/biz/epc/project/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EpcProjectIdParam> epcProjectIdParamList) {
        epcProjectService.delete(epcProjectIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取EPC-项目管理表详情
     *
     * @author yong.ye
     * @date  2023/09/05 14:55
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取EPC-项目管理表详情")
//    @SaCheckPermission("/biz/epc/project/detail")
    @GetMapping("/biz/epc/project/detail")
    public CommonResult<EpcProject> detail(@Valid EpcProjectIdParam epcProjectIdParam) {
        return CommonResult.data(epcProjectService.detail(epcProjectIdParam));
    }
}
