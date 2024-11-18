package vip.xiaonuo.sys.modular.hr.honorAward.controller;

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
import vip.xiaonuo.sys.modular.hr.honorAward.entity.EnterpriseHonor;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorAddParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorEditParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorIdParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.EnterpriseHonorPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.service.EnterpriseHonorService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 企业荣誉控制器
 *
 * @author pzh
 * @date  2024/10/16 11:17
 */
@Api(tags = "企业荣誉控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EnterpriseHonorController {

    @Resource
    private EnterpriseHonorService enterpriseHonorService;

    /**
     * 获取企业荣誉分页
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取企业荣誉分页")
    // @SaCheckPermission("/ai/enterpriseHonor/page")
    @GetMapping("/sys/enterpriseHonor/page")
    public CommonResult<Page<EnterpriseHonor>> page(EnterpriseHonorPageParam enterpriseHonorPageParam) {
        return CommonResult.data(enterpriseHonorService.page(enterpriseHonorPageParam));
    }

    /**
     * 添加企业荣誉
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加企业荣誉")
    @CommonLog("添加企业荣誉")
    // @SaCheckPermission("/sys/enterpriseHonor/add")
    @PostMapping("/sys/enterpriseHonor/add")
    public CommonResult<String> add(@RequestBody @Valid EnterpriseHonorAddParam enterpriseHonorAddParam) {
        enterpriseHonorService.add(enterpriseHonorAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑企业荣誉
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑企业荣誉")
    @CommonLog("编辑企业荣誉")
    // @SaCheckPermission("/sys/enterpriseHonor/edit")
    @PostMapping("/sys/enterpriseHonor/edit")
    public CommonResult<String> edit(@RequestBody @Valid EnterpriseHonorEditParam enterpriseHonorEditParam) {
        enterpriseHonorService.edit(enterpriseHonorEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除企业荣誉
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除企业荣誉")
    @CommonLog("删除企业荣誉")
    // @SaCheckPermission("/sys/enterpriseHonor/delete")
    @PostMapping("/sys/enterpriseHonor/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<EnterpriseHonorIdParam> enterpriseHonorIdParamList) {
        enterpriseHonorService.delete(enterpriseHonorIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取企业荣誉详情
     *
     * @author pzh
     * @date  2024/10/16 11:17
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取企业荣誉详情")
    // @SaCheckPermission("/sys/enterpriseHonor/detail")
    @GetMapping("/sys/enterpriseHonor/detail")
    public CommonResult<EnterpriseHonor> detail(@Valid EnterpriseHonorIdParam enterpriseHonorIdParam) {
        return CommonResult.data(enterpriseHonorService.detail(enterpriseHonorIdParam));
    }
}
