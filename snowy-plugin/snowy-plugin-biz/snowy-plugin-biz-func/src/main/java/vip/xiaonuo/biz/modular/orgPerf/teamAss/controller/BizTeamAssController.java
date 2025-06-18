package vip.xiaonuo.biz.modular.orgPerf.teamAss.controller;

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
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAss;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssPageParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.service.BizTeamAssService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 班子考核控制器
 *
 * @author pzh
 * @date  2025/06/17 16:23
 */
@Api(tags = "班子考核控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizTeamAssController {

    @Resource
    private BizTeamAssService bizTeamAssService;

    /**
     * 获取班子考核分页
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取班子考核分页")
    // @SaCheckPermission("/biz/orgPerf/teamAss/page")
    @GetMapping("/biz/orgPerf/teamAss/page")
    public CommonResult<Page<BizTeamAss>> page(BizTeamAssPageParam bizTeamAssPageParam) {
        return CommonResult.data(bizTeamAssService.page(bizTeamAssPageParam));
    }

    /**
     * 添加班子考核
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加班子考核")
    @CommonLog("添加班子考核")
    // @SaCheckPermission("/biz/orgPerf/teamAss/add")
    @PostMapping("/biz/orgPerf/teamAss/add")
    public CommonResult<String> add(@RequestBody @Valid BizTeamAssAddParam bizTeamAssAddParam) {
        bizTeamAssService.add(bizTeamAssAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑班子考核
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑班子考核")
    @CommonLog("编辑班子考核")
    // @SaCheckPermission("/biz/orgPerf/teamAss/edit")
    @PostMapping("/biz/orgPerf/teamAss/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizTeamAssEditParam bizTeamAssEditParam) {
        bizTeamAssService.edit(bizTeamAssEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除班子考核
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除班子考核")
    @CommonLog("删除班子考核")
    // @SaCheckPermission("/biz/orgPerf/teamAss/delete")
    @PostMapping("/biz/orgPerf/teamAss/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizTeamAssIdParam> bizTeamAssIdParamList) {
        bizTeamAssService.delete(bizTeamAssIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取班子考核详情
     *
     * @author pzh
     * @date  2025/06/17 16:23
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取班子考核详情")
    // @SaCheckPermission("/biz/orgPerf/teamAss/detail")
    @GetMapping("/biz/orgPerf/teamAss/detail")
    public CommonResult<BizTeamAss> detail(@Valid BizTeamAssIdParam bizTeamAssIdParam) {
        return CommonResult.data(bizTeamAssService.detail(bizTeamAssIdParam));
    }
}
