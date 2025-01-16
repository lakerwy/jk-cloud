package vip.xiaonuo.sys.modular.hr.reg.controller;

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
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;
import vip.xiaonuo.sys.modular.hr.reg.param.*;
import vip.xiaonuo.sys.modular.hr.reg.service.RegSubsidyResultService;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegResultVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 注册补贴（新版）- 结果反馈控制器
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 */
@Api(tags = "注册补贴（新版）- 结果反馈控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class RegSubsidyResultController {

    @Resource
    private RegSubsidyResultService regSubsidyResultService;

    /**
     * 获取注册补贴（新版）- 结果反馈分页
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取注册补贴（新版）- 结果反馈分页")
//    @SaCheckPermission("/sys/hr/reg/result/page")
    @GetMapping("/sys/hr/reg/result/page")
    public CommonResult<Page<RegResultVo>> page(RegSubsidyResultPageParam regSubsidyResultPageParam) {
        return CommonResult.data(regSubsidyResultService.page(regSubsidyResultPageParam));
    }

    /**
     * 添加注册补贴（新版）- 结果反馈
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加注册补贴（新版）- 结果反馈")
    @CommonLog("添加注册补贴（新版）- 结果反馈")
//    @SaCheckPermission("/sys/hr/reg/result/add")
    @PostMapping("/sys/hr/reg/result/add")
    public CommonResult<String> add(@RequestBody @Valid RegSubsidyResultAddParam regSubsidyResultAddParam) {
        regSubsidyResultService.add(regSubsidyResultAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑注册补贴（新版）- 结果反馈
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑注册补贴（新版）- 结果反馈")
    @CommonLog("编辑注册补贴（新版）- 结果反馈")
//    @SaCheckPermission("/sys/hr/reg/result/edit")
    @PostMapping("/sys/hr/reg/result/edit")
    public CommonResult<String> edit(@RequestBody @Valid RegSubsidyResultEditParam regSubsidyResultEditParam) {
        regSubsidyResultService.edit(regSubsidyResultEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除注册补贴（新版）- 结果反馈
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除注册补贴（新版）- 结果反馈")
    @CommonLog("删除注册补贴（新版）- 结果反馈")
//    @SaCheckPermission("/sys/hr/reg/result/delete")
    @PostMapping("/sys/hr/reg/result/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<RegSubsidyResultIdParam> regSubsidyResultIdParamList) {
        regSubsidyResultService.delete(regSubsidyResultIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取注册补贴（新版）- 结果反馈详情
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取注册补贴（新版）- 结果反馈详情")
//    @SaCheckPermission("/sys/hr/reg/result/detail")
    @GetMapping("/sys/hr/reg/result/detail")
    public CommonResult<RegSubsidyResult> detail(@Valid RegSubsidyResultIdParam regSubsidyResultIdParam) {
        return CommonResult.data(regSubsidyResultService.detail(regSubsidyResultIdParam));
    }


    /**
     * 获取注册补贴（新版）- 已有证书列表（审核通过）
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取注册补贴（新版）- 已有证书列表（只获取审核通过）")
    @GetMapping("/sys/hr/reg/result/listById")
    public CommonResult<List<RegSubsidyResult>> listById(@Valid RegParam regParam) {
        return CommonResult.data(regSubsidyResultService.listById(regParam));
    }

}
