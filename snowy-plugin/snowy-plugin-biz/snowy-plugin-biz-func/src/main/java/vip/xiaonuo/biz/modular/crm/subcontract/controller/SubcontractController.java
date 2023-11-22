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
package vip.xiaonuo.biz.modular.crm.subcontract.controller;

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
import vip.xiaonuo.biz.modular.crm.hetong.param.CheckNameParam;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SubContractInfoVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractAddParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractEditParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractIdParam;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SubcontractPageParam;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-CRM-分包合同控制器
 *
 * @author yong.ye
 * @date  2023/06/30 16:22
 */
@Api(tags = "业务表-CRM-分包合同控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubcontractController {

    @Resource
    private SubcontractService subcontractService;

    /**
     * 获取业务表-CRM-分包合同分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-分包合同分页  【api/crm/contract/GetSubContractPage】")
    @GetMapping("/biz/crm/subcontract/page")
    public CommonResult<Page<SubContractInfoVo>> page(SubcontractPageParam subcontractPageParam) {
        return CommonResult.data(subcontractService.page(subcontractPageParam));
    }

    /**
     * 添加业务表-CRM-分包合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-CRM-分包合同  【api/crm/contract/AddSubContract】")
    @CommonLog("添加业务表-CRM-分包合同")
    @PostMapping("/biz/crm/subcontract/add")
    public CommonResult<String> add(@RequestBody @Valid SubcontractAddParam subcontractAddParam) {
        subcontractService.add(subcontractAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-CRM-分包合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-CRM-分包合同  【api/crm/contract/UpdateSubContract】")
    @CommonLog("编辑业务表-CRM-分包合同")
    @PostMapping("/biz/crm/subcontract/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubcontractEditParam subcontractEditParam) {
        subcontractService.edit(subcontractEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-CRM-分包合同
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-CRM-分包合同  【api/crm/contract/DeleteSubContract】")
    @CommonLog("删除业务表-CRM-分包合同")
    @PostMapping("/biz/crm/subcontract/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubcontractIdParam> subcontractIdParamList) {
        subcontractService.delete(subcontractIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-CRM-分包合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-分包合同详情")
    @GetMapping("/biz/crm/subcontract/detail")
    public CommonResult<Subcontract> detail(@Valid SubcontractIdParam subcontractIdParam) {
        return CommonResult.data(subcontractService.detail(subcontractIdParam));
    }

    /**
     * 获取业务表-CRM-分包合同详情(携带源合同信息)
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取业务表-CRM-分包合同详情(携带源合同信息)  【api/crm/contract/GetSubContractById】")
    @GetMapping("/biz/crm/subcontract/detailById")
    public CommonResult<SubContractInfoVo> detailById(@Valid SubcontractIdParam subcontractIdParam) {
        return CommonResult.data(subcontractService.detailById(subcontractIdParam));
    }

    /**
     * 判断合同名称是否重名 （审批中+审核通过状态下）
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("判断合同名称是否重名 （审批中+审核通过状态下）")
    @GetMapping("/biz/crm/subcontract/checkName")
    public CommonResult<Boolean> checkName(@Valid CheckNameParam checkNameParam) {
        return CommonResult.data(subcontractService.checkName(checkNameParam));
    }

    /**
     * 获取业务表-CRM-分包合同分页【含权限设置-由于page接口有许多调用，故但起一个权限接口】
     *
     * @author yong.ye
     * @date  2023/06/30 16:22
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取业务表-CRM-分包合同分页 【含权限设置-由于page接口有许多调用，故但起一个权限接口】")
    @SaCheckPermission("/biz/crm/subcontract/limitPage")
    @GetMapping("/biz/crm/subcontract/limitPage")
    public CommonResult<Page<SubContractInfoVo>> limitPage(SubcontractPageParam subcontractPageParam) {
        return CommonResult.data(subcontractService.limitPage(subcontractPageParam));
    }
}
