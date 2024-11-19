package vip.xiaonuo.sys.modular.hr.techAchive.controller;

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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.PatentInfo;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.PatentInfoService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 专利信息控制器
 *
 * @author pzh
 * @date  2024/10/17 10:22
 */
@Api(tags = "专利信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PatentInfoController {

    @Resource
    private PatentInfoService patentInfoService;

    /**
     * 获取专利信息分页
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取专利信息分页")
    // @SaCheckPermission("/sys/techAchive/parentInfo/page")
    @GetMapping("/sys/techAchive/parentInfo/page")
    public CommonResult<Page<PatentInfo>> page(PatentInfoPageParam patentInfoPageParam) {
        return CommonResult.data(patentInfoService.page(patentInfoPageParam));
    }

    /**
     * 添加专利信息
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加专利信息")
    @CommonLog("添加专利信息")
    // @SaCheckPermission("/sys/techAchive/parentInfo/add")
    @PostMapping("/sys/techAchive/parentInfo/add")
    public CommonResult<String> add(@RequestBody @Valid PatentInfoAddParam patentInfoAddParam) {
        patentInfoService.add(patentInfoAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑专利信息
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑专利信息")
    @CommonLog("编辑专利信息")
    // @SaCheckPermission("/sys/techAchive/parentInfo/edit")
    @PostMapping("/sys/techAchive/parentInfo/edit")
    public CommonResult<String> edit(@RequestBody @Valid PatentInfoEditParam patentInfoEditParam) {
        patentInfoService.edit(patentInfoEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除专利信息
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除专利信息")
    @CommonLog("删除专利信息")
    // @SaCheckPermission("/sys/techAchive/parentInfo/delete")
    @PostMapping("/sys/techAchive/parentInfo/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<PatentInfoIdParam> patentInfoIdParamList) {
        patentInfoService.delete(patentInfoIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取专利信息详情
     *
     * @author pzh
     * @date  2024/10/17 10:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取专利信息详情")
    // @SaCheckPermission("/sys/techAchive/parentInfo/detail")
    @GetMapping("/sys/techAchive/parentInfo/detail")
    public CommonResult<PatentInfo> detail(@Valid PatentInfoIdParam patentInfoIdParam) {
        return CommonResult.data(patentInfoService.detail(patentInfoIdParam));
    }
}
