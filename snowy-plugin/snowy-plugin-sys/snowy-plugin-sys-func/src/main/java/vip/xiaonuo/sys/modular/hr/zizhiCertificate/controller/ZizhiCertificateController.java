package vip.xiaonuo.sys.modular.hr.zizhiCertificate.controller;

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
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.entity.ZizhiCertificate;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateAddParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateEditParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateIdParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificatePageParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.service.ZizhiCertificateService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 资质证照控制器
 *
 * @author pzh
 * @date  2024/10/16 16:39
 */
@Api(tags = "资质证照控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZizhiCertificateController {

    @Resource
    private ZizhiCertificateService zizhiCertificateService;

    /**
     * 获取资质证照分页
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取资质证照分页")
    // @SaCheckPermission("/sys/zizhiCertificate/page")
    @GetMapping("/sys/zizhiCertificate/page")
    public CommonResult<Page<ZizhiCertificate>> page(ZizhiCertificatePageParam zizhiCertificatePageParam) {
        return CommonResult.data(zizhiCertificateService.page(zizhiCertificatePageParam));
    }

    /**
     * 添加资质证照
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加资质证照")
    @CommonLog("添加资质证照")
    // @SaCheckPermission("/sys/zizhiCertificate/add")
    @PostMapping("/sys/zizhiCertificate/add")
    public CommonResult<String> add(@RequestBody @Valid ZizhiCertificateAddParam zizhiCertificateAddParam) {
        zizhiCertificateService.add(zizhiCertificateAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑资质证照
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑资质证照")
    @CommonLog("编辑资质证照")
    // @SaCheckPermission("/sys/zizhiCertificate/edit")
    @PostMapping("/sys/zizhiCertificate/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZizhiCertificateEditParam zizhiCertificateEditParam) {
        zizhiCertificateService.edit(zizhiCertificateEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除资质证照
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除资质证照")
    @CommonLog("删除资质证照")
    // @SaCheckPermission("/sys/zizhiCertificate/delete")
    @PostMapping("/sys/zizhiCertificate/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<ZizhiCertificateIdParam> zizhiCertificateIdParamList) {
        zizhiCertificateService.delete(zizhiCertificateIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取资质证照详情
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取资质证照详情")
    // @SaCheckPermission("/sys/zizhiCertificate/detail")
    @GetMapping("/sys/zizhiCertificate/detail")
    public CommonResult<ZizhiCertificate> detail(@Valid ZizhiCertificateIdParam zizhiCertificateIdParam) {
        return CommonResult.data(zizhiCertificateService.detail(zizhiCertificateIdParam));
    }

}
