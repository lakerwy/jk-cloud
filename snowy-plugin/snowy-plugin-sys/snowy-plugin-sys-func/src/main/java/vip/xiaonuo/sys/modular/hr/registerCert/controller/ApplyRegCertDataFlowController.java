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
package vip.xiaonuo.sys.modular.hr.registerCert.controller;

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
import vip.xiaonuo.sys.modular.hr.registerCert.entity.ApplyRegCertDataFlow;
import vip.xiaonuo.sys.modular.hr.registerCert.param.*;
import vip.xiaonuo.sys.modular.hr.registerCert.service.ApplyRegCertDataFlowService;
import vip.xiaonuo.sys.modular.hr.registerCert.service.ApplyRegCertDataService;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelRegInfoVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 注册补贴-申请FLW流程表信息控制器
 *
 * @author ljy
 * @date  2024/03/28 15:48
 */
@Api(tags = "注册补贴-申请FLW流程表信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ApplyRegCertDataFlowController {

    @Resource
    private ApplyRegCertDataFlowService applyRegCertDataFlowService;
    @Resource
    ApplyRegCertDataService applyRegCertDataService;

    /**
     * 获取注册补贴-申请FLW流程表信息分页
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取注册补贴-申请FLW流程表信息分页")
    @GetMapping("/sys/regDertDataFlow/page")
    public CommonResult<Page<ApplyRegCertDataFlow>> page(ApplyRegCertDataFlowPageParam applyRegCertDataFlowPageParam) {
        return CommonResult.data(applyRegCertDataFlowService.page(applyRegCertDataFlowPageParam));
    }

    /**
     * 添加注册补贴-申请FLW流程表信息
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加注册补贴-申请FLW流程表信息")
    @CommonLog("添加注册补贴-申请FLW流程表信息")
    @PostMapping("/sys/regDertDataFlow/start")
    public CommonResult<String> add(@RequestBody @Valid ApplyRegCertDataFlowAddParam applyRegCertDataFlowAddParam) {
        applyRegCertDataFlowService.add(applyRegCertDataFlowAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑注册补贴-申请FLW流程表信息
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑注册补贴-申请FLW流程表信息")
    @CommonLog("编辑注册补贴-申请FLW流程表信息")
    @PostMapping("/sys/regDertDataFlow/edit")
    public CommonResult<String> edit(@RequestBody @Valid ApplyRegCertDataFlowEditParam applyRegCertDataFlowEditParam) {
        applyRegCertDataFlowService.edit(applyRegCertDataFlowEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除注册补贴-申请FLW流程表信息
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除注册补贴-申请FLW流程表信息")
    @CommonLog("删除注册补贴-申请FLW流程表信息")
    @PostMapping("/sys/regDertDataFlow/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ApplyRegCertDataFlowIdParam> applyRegCertDataFlowIdParamList) {
        applyRegCertDataFlowService.delete(applyRegCertDataFlowIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取注册补贴-申请FLW流程表信息详情
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取注册补贴-申请FLW流程表信息详情")
    @GetMapping("/sys/regDertDataFlow/detail")
    public CommonResult<ApplyRegCertDataFlow> detail(@Valid ApplyRegCertDataFlowIdParam applyRegCertDataFlowIdParam) {
        return CommonResult.data(applyRegCertDataFlowService.detail(applyRegCertDataFlowIdParam));
    }

    /**
     * 注册补贴-获取基本信息和已有证书数据
     *
     * @author ljy
     * @date  2024/03/27 15:28
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取注册补贴-前置接口-获取基本信息和已有证书数据")
    @GetMapping("/sys/regCertData/alwAndCertData")
    public CommonResult<List<PersonnelRegInfoVo>> alwAndCertData(@Valid AlwAndCertParam alwAndCertParam) {
        return CommonResult.data(applyRegCertDataService.alwAndCertData(alwAndCertParam));
    }
}
