package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.controller;

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
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.entity.EnterpriseMaterial;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialAddParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialEditParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialIdParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialPageParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.service.EnterpriseMaterialService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 企业宣传资料控制器
 *
 * @author pzh
 * @date  2024/09/18 14:57
 */
@Api(tags = "企业宣传资料控制器1")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EnterpriseMaterialController {

    @Resource
    private EnterpriseMaterialService enterpriseMaterialService;

    /**
     * 获取企业宣传资料分页
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取企业宣传资料分页")
//    @SaCheckPermission("/sys/enterpriseMaterial/page")
    @GetMapping("/sys/enterpriseMaterial/page")
    public CommonResult<Page<EnterpriseMaterial>> page(EnterpriseMaterialPageParam enterpriseMaterialPageParam) {
        return CommonResult.data(enterpriseMaterialService.page(enterpriseMaterialPageParam));
    }

    /**
     * 添加企业宣传资料
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加企业宣传资料")
    @CommonLog("添加企业宣传资料")
//    @SaCheckPermission("/sys/enterpriseMaterial/add")
    @PostMapping("/sys/enterpriseMaterial/add")
    public CommonResult<String> add(@RequestBody @Valid EnterpriseMaterialAddParam enterpriseMaterialAddParam) {
        enterpriseMaterialService.add(enterpriseMaterialAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑企业宣传资料
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑企业宣传资料")
    @CommonLog("编辑企业宣传资料")
//    @SaCheckPermission("/sys/enterpriseMaterial/edit")
    @PostMapping("/sys/enterpriseMaterial/edit")
    public CommonResult<String> edit(@RequestBody @Valid EnterpriseMaterialEditParam enterpriseMaterialEditParam) {
        enterpriseMaterialService.edit(enterpriseMaterialEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除企业宣传资料
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除企业宣传资料")
    @CommonLog("删除企业宣传资料")
//    @SaCheckPermission("/sys/enterpriseMaterial/delete")
    @PostMapping("/sys/enterpriseMaterial/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EnterpriseMaterialIdParam> enterpriseMaterialIdParamList) {
        enterpriseMaterialService.delete(enterpriseMaterialIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取企业宣传资料详情
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取企业宣传资料详情")
//    @SaCheckPermission("/sys/enterpriseMaterial/detail")
    @GetMapping("/sys/enterpriseMaterial/detail")
    public CommonResult<EnterpriseMaterial> detail(@Valid EnterpriseMaterialIdParam enterpriseMaterialIdParam) {
        return CommonResult.data(enterpriseMaterialService.detail(enterpriseMaterialIdParam));
    }

    /**
     * 获取最新排序序号
     *
     * @author pzh
     * @date  2024/09/26 08:45
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取最新排序序号")
//    @SaCheckPermission("/sys/enterpriseMaterial/getIndexSort")
    @GetMapping("/sys/enterpriseMaterial/getIndexSort")
    public CommonResult<Integer> getIndexSort() {
        return CommonResult.data(enterpriseMaterialService.getIndexSort());
    }
}
