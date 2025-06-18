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
import vip.xiaonuo.biz.modular.orgPerf.teamAss.service.BizTeamAssRecordService;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.vo.TeamAssRecordVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAssRecord;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordAddParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordEditParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordIdParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordPageParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 班子考核记录控制器
 *
 * @author pzh
 * @date  2025/06/17 17:08
 */
@Api(tags = "班子考核记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizTeamAssRecordController {

    @Resource
    private BizTeamAssRecordService bizTeamAssRecordService;

    /**
     * 获取班子考核记录分页
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取班子考核记录分页")
    // @SaCheckPermission("/biz/orgPerf/teamAssRecord/page")
    @GetMapping("/biz/orgPerf/teamAssRecord/page")
    public CommonResult<Page<TeamAssRecordVo>> page(BizTeamAssRecordPageParam bizTeamAssRecordPageParam) {
        return CommonResult.data(bizTeamAssRecordService.page(bizTeamAssRecordPageParam));
    }

    /**
     * 添加班子考核记录
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加班子考核记录")
    @CommonLog("添加班子考核记录")
    // @SaCheckPermission("/biz/orgPerf/teamAssRecord/add")
    @PostMapping("/biz/orgPerf/teamAssRecord/add")
    public CommonResult<String> add(@RequestBody @Valid BizTeamAssRecordAddParam bizTeamAssRecordAddParam) {
        bizTeamAssRecordService.add(bizTeamAssRecordAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑班子考核记录
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑班子考核记录")
    @CommonLog("编辑班子考核记录")
    // @SaCheckPermission("/biz/orgPerf/teamAssRecord/edit")
    @PostMapping("/biz/orgPerf/teamAssRecord/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizTeamAssRecordEditParam bizTeamAssRecordEditParam) {
        bizTeamAssRecordService.edit(bizTeamAssRecordEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除班子考核记录
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除班子考核记录")
    @CommonLog("删除班子考核记录")
    // @SaCheckPermission("/biz/orgPerf/teamAssRecord/delete")
    @PostMapping("/biz/orgPerf/teamAssRecord/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizTeamAssRecordIdParam> bizTeamAssRecordIdParamList) {
        bizTeamAssRecordService.delete(bizTeamAssRecordIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取班子考核记录详情
     *
     * @author pzh
     * @date  2025/06/17 17:08
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取班子考核记录详情")
    // @SaCheckPermission("/biz/orgPerf/teamAssRecord/detail")
    @GetMapping("/biz/orgPerf/teamAssRecord/detail")
    public CommonResult<BizTeamAssRecord> detail(@Valid BizTeamAssRecordIdParam bizTeamAssRecordIdParam) {
        return CommonResult.data(bizTeamAssRecordService.detail(bizTeamAssRecordIdParam));
    }
}
