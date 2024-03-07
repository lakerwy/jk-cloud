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
package vip.xiaonuo.sys.modular.company.controller;

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
import vip.xiaonuo.sys.modular.company.entity.CompanyList;
import vip.xiaonuo.sys.modular.company.param.CompanyListAddParam;
import vip.xiaonuo.sys.modular.company.param.CompanyListEditParam;
import vip.xiaonuo.sys.modular.company.param.CompanyListIdParam;
import vip.xiaonuo.sys.modular.company.param.CompanyListPageParam;
import vip.xiaonuo.sys.modular.company.service.CompanyListService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 建科公司列表(子公司、分公司)控制器
 *
 * @author yong.ye
 * @date  2024/03/07 15:04
 */
@Api(tags = "建科公司列表(子公司、分公司)控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CompanyListController {

    @Resource
    private CompanyListService companyListService;

    /**
     * 获取建科公司列表(子公司、分公司)分页
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取建科公司列表(子公司、分公司)分页")
//    @SaCheckPermission("/sys/company/page")
    @GetMapping("/sys/company/page")
    public CommonResult<Page<CompanyList>> page(CompanyListPageParam companyListPageParam) {
        return CommonResult.data(companyListService.page(companyListPageParam));
    }

    /**
     * 添加建科公司列表(子公司、分公司)
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加建科公司列表(子公司、分公司)")
    @CommonLog("添加建科公司列表(子公司、分公司)")
//    @SaCheckPermission("/sys/company/add")
    @PostMapping("/sys/company/add")
    public CommonResult<String> add(@RequestBody @Valid CompanyListAddParam companyListAddParam) {
        companyListService.add(companyListAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑建科公司列表(子公司、分公司)
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑建科公司列表(子公司、分公司)")
    @CommonLog("编辑建科公司列表(子公司、分公司)")
//    @SaCheckPermission("/sys/company/edit")
    @PostMapping("/sys/company/edit")
    public CommonResult<String> edit(@RequestBody @Valid CompanyListEditParam companyListEditParam) {
        companyListService.edit(companyListEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除建科公司列表(子公司、分公司)
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除建科公司列表(子公司、分公司)")
    @CommonLog("删除建科公司列表(子公司、分公司)")
//    @SaCheckPermission("/sys/company/delete")
    @PostMapping("/sys/company/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<CompanyListIdParam> companyListIdParamList) {
        companyListService.delete(companyListIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取建科公司列表(子公司、分公司)详情
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取建科公司列表(子公司、分公司)详情")
//    @SaCheckPermission("/sys/company/detail")
    @GetMapping("/sys/company/detail")
    public CommonResult<CompanyList> detail(@Valid CompanyListIdParam companyListIdParam) {
        return CommonResult.data(companyListService.detail(companyListIdParam));
    }
}
