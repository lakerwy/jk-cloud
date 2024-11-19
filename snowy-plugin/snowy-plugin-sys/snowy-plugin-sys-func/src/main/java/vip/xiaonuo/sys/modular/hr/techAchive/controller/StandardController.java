package vip.xiaonuo.sys.modular.hr.techAchive.controller;

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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.Standard;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.StandardService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 标准规范控制器
 *
 * @author pzh
 * @date  2024/10/17 17:05
 */
@Api(tags = "标准规范控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class StandardController {

    @Resource
    private StandardService standardService;

    /**
     * 获取标准规范分页
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取标准规范分页")
    // @SaCheckPermission("/sys/techAchive/standard/page")
    @GetMapping("/sys/techAchive/standard/page")
    public CommonResult<Page<Standard>> page(StandardPageParam standardPageParam) {
        return CommonResult.data(standardService.page(standardPageParam));
    }

    /**
     * 添加标准规范
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加标准规范")
    @CommonLog("添加标准规范")
    // @SaCheckPermission("/sys/techAchive/standard/add")
    @PostMapping("/sys/techAchive/standard/add")
    public CommonResult<String> add(@RequestBody @Valid StandardAddParam standardAddParam) {
        standardService.add(standardAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑标准规范
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑标准规范")
    @CommonLog("编辑标准规范")
    // @SaCheckPermission("/sys/techAchive/standard/edit")
    @PostMapping("/sys/techAchive/standard/edit")
    public CommonResult<String> edit(@RequestBody @Valid StandardEditParam standardEditParam) {
        standardService.edit(standardEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除标准规范
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除标准规范")
    @CommonLog("删除标准规范")
    // @SaCheckPermission("/sys/techAchive/standard/delete")
    @PostMapping("/sys/techAchive/standard/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<StandardIdParam> standardIdParamList) {
        standardService.delete(standardIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取标准规范详情
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取标准规范详情")
    // @SaCheckPermission("/sys/techAchive/standard/detail")
    @GetMapping("/sys/techAchive/standard/detail")
    public CommonResult<Standard> detail(@Valid StandardIdParam standardIdParam) {
        return CommonResult.data(standardService.detail(standardIdParam));
    }
}
