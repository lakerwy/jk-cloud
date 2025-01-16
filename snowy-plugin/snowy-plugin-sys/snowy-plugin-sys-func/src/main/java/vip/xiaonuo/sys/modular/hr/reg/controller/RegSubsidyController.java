package vip.xiaonuo.sys.modular.hr.reg.controller;

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
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidy;
import vip.xiaonuo.sys.modular.hr.reg.param.*;
import vip.xiaonuo.sys.modular.hr.reg.service.RegSubsidyService;
import vip.xiaonuo.sys.modular.hr.reg.vo.PersonnelInfoVo;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegCertVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 注册补贴（新版）-审批流控制器
 *
 * @author yong.ye
 * @date  2025/01/15 09:07
 */
@Api(tags = "注册补贴（新版）-审批流控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class RegSubsidyController {

    @Resource
    private RegSubsidyService regSubsidyService;

    /**
     * 获取注册补贴（新版）-审批流分页
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取注册补贴（新版）-审批流分页")
//    @SaCheckPermission("/sys/hr/reg/page")
    @GetMapping("/sys/hr/reg/page")
    public CommonResult<Page<RegSubsidy>> page(RegSubsidyPageParam regSubsidyPageParam) {
        return CommonResult.data(regSubsidyService.page(regSubsidyPageParam));
    }

    /**
     * 添加注册补贴（新版）-审批流
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加注册补贴（新版）-审批流")
    @CommonLog("添加注册补贴（新版）-审批流")
//    @SaCheckPermission("/sys/hr/reg/add")
    @PostMapping("/sys/hr/reg/add")
    public CommonResult<String> add(@RequestBody @Valid RegSubsidyAddParam regSubsidyAddParam) {
        regSubsidyService.add(regSubsidyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑注册补贴（新版）-审批流
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑注册补贴（新版）-审批流")
    @CommonLog("编辑注册补贴（新版）-审批流")
//    @SaCheckPermission("/sys/hr/reg/edit")
    @PostMapping("/sys/hr/reg/edit")
    public CommonResult<String> edit(@RequestBody @Valid RegSubsidyEditParam regSubsidyEditParam) {
        regSubsidyService.edit(regSubsidyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除注册补贴（新版）-审批流
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除注册补贴（新版）-审批流")
    @CommonLog("删除注册补贴（新版）-审批流")
//    @SaCheckPermission("/sys/hr/reg/delete")
    @PostMapping("/sys/hr/reg/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<RegSubsidyIdParam> regSubsidyIdParamList) {
        regSubsidyService.delete(regSubsidyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取注册补贴（新版）-审批流详情
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取注册补贴（新版）-审批流详情")
//    @SaCheckPermission("/sys/hr/reg/detail")
    @GetMapping("/sys/hr/reg/detail")
    public CommonResult<RegSubsidy> detail(@Valid RegSubsidyIdParam regSubsidyIdParam) {
        return CommonResult.data(regSubsidyService.detail(regSubsidyIdParam));
    }



    /**
     * 注册补贴（新版）-审批流 - 校验是否已存在未完成的审批
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("注册补贴（新版）-审批流 - 校验是否已存在未完成的审批")
    @GetMapping("/sys/hr/reg/check")
    public CommonResult<Boolean> check(@Valid RegParam regParam) {
        return CommonResult.data(regSubsidyService.check(regParam));
    }


    /**
     * 注册补贴（新版）-审批流 - 若为初次申请注册审批，本次申请，自动携带出资质管理中的注册证书，若非首次申请，不携带信息
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("注册补贴（新版）-审批流 - 【本次申请】 若为初次申请，自动携带出资质管理中的注册证书，若非首次申请，不携带信息")
    @GetMapping("/sys/hr/reg/certList")
    public CommonResult<List<RegCertVo>> certList(@Valid RegParam regParam) {
        return CommonResult.data(regSubsidyService.certList(regParam));
    }


    /**
     * 获取【总部|分公司】-花名册信息（支持按照 总部、分公司、在职、姓名筛选）
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("获取【总部|分公司】-花名册信息（支持按照 总部、分公司、在职、姓名筛选）")
    @GetMapping("/sys/hr/reg/personnelList")
    public CommonResult<List<PersonnelInfoVo>> personnelList(@Valid PersonnelParam personnelParam) {
        return CommonResult.data(regSubsidyService.personnelList(personnelParam));
    }

}
