package vip.xiaonuo.sys.modular.hr.certificationSystem.controller;

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
import vip.xiaonuo.sys.modular.hr.certificationSystem.entity.CertificationSystem;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemAddParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemEditParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemIdParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemPageParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.service.CertificationSystemService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 认证体系控制器
 *
 * @author pzh
 * @date  2024/10/18 10:01
 */
@Api(tags = "认证体系控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class CertificationSystemController {

    @Resource
    private CertificationSystemService certificationSystemService;

    /**
     * 获取认证体系分页
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取认证体系分页")
    // @SaCheckPermission("/sys/certificationSystem/page")
    @GetMapping("/sys/certificationSystem/page")
    public CommonResult<Page<CertificationSystem>> page(CertificationSystemPageParam certificationSystemPageParam) {
        return CommonResult.data(certificationSystemService.page(certificationSystemPageParam));
    }

    /**
     * 添加认证体系
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加认证体系")
    @CommonLog("添加认证体系")
    // @SaCheckPermission("/sys/certificationSystem/add")
    @PostMapping("/sys/certificationSystem/add")
    public CommonResult<String> add(@RequestBody @Valid CertificationSystemAddParam certificationSystemAddParam) {
        certificationSystemService.add(certificationSystemAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑认证体系
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑认证体系")
    @CommonLog("编辑认证体系")
    // @SaCheckPermission("/sys/certificationSystem/edit")
    @PostMapping("/sys/certificationSystem/edit")
    public CommonResult<String> edit(@RequestBody @Valid CertificationSystemEditParam certificationSystemEditParam) {
        certificationSystemService.edit(certificationSystemEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除认证体系
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除认证体系")
    @CommonLog("删除认证体系")
    // @SaCheckPermission("/sys/certificationSystem/delete")
    @PostMapping("/sys/certificationSystem/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<CertificationSystemIdParam> certificationSystemIdParamList) {
        certificationSystemService.delete(certificationSystemIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取认证体系详情
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取认证体系详情")
    // @SaCheckPermission("/sys/certificationSystem/detail")
    @GetMapping("/sys/certificationSystem/detail")
    public CommonResult<CertificationSystem> detail(@Valid CertificationSystemIdParam certificationSystemIdParam) {
        return CommonResult.data(certificationSystemService.detail(certificationSystemIdParam));
    }
}
