package vip.xiaonuo.sys.modular.hr.reputation.controller;

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
import vip.xiaonuo.sys.modular.hr.reputation.entity.Reputation;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationAddParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationEditParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationIdParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationPageParam;
import vip.xiaonuo.sys.modular.hr.reputation.service.ReputationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 信誉情况控制器
 *
 * @author pzh
 * @date  2024/10/18 16:52
 */
@Api(tags = "信誉情况控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ReputationController {

    @Resource
    private ReputationService reputationService;

    /**
     * 获取信誉情况分页
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取信誉情况分页")
    // @SaCheckPermission("/sys/reputation/page")
    @GetMapping("/sys/reputation/page")
    public CommonResult<Page<Reputation>> page(ReputationPageParam reputationPageParam) {
        return CommonResult.data(reputationService.page(reputationPageParam));
    }

    /**
     * 添加信誉情况
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加信誉情况")
    @CommonLog("添加信誉情况")
    // @SaCheckPermission("/sys/reputation/add")
    @PostMapping("/sys/reputation/add")
    public CommonResult<String> add(@RequestBody @Valid ReputationAddParam reputationAddParam) {
        reputationService.add(reputationAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑信誉情况
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑信誉情况")
    @CommonLog("编辑信誉情况")
    // @SaCheckPermission("/sys/reputation/edit")
    @PostMapping("/sys/reputation/edit")
    public CommonResult<String> edit(@RequestBody @Valid ReputationEditParam reputationEditParam) {
        reputationService.edit(reputationEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除信誉情况
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除信誉情况")
    @CommonLog("删除信誉情况")
    // @SaCheckPermission("/sys/reputation/delete")
    @PostMapping("/sys/reputation/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<ReputationIdParam> reputationIdParamList) {
        reputationService.delete(reputationIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取信誉情况详情
     *
     * @author pzh
     * @date  2024/10/18 16:52
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取信誉情况详情")
    // @SaCheckPermission("/sys/reputation/detail")
    @GetMapping("/sys/reputation/detail")
    public CommonResult<Reputation> detail(@Valid ReputationIdParam reputationIdParam) {
        return CommonResult.data(reputationService.detail(reputationIdParam));
    }
}
