package vip.xiaonuo.sys.modular.hr.branchRating.controller;

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
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchFill;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchFillService;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchFillVo;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分公司评级（二期）- 分公司评级填报控制器
 *
 * @author yong.ye
 * @date  2025/03/18 08:48
 */
@Api(tags = "分公司评级（二期）- 分公司评级填报控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BranchFillController {

    @Resource
    private BranchFillService branchFillService;

    /**
     * 获取分公司评级（二期）- 分公司评级填报分页
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司评级（二期）- 分公司评级填报分页")
//    @SaCheckPermission("/sys/hr/branchRating/fill/page")
    @GetMapping("/sys/hr/branchRating/fill/page")
    public CommonResult<Page<BranchFill>> page(BranchFillPageParam branchFillPageParam) {
        return CommonResult.data(branchFillService.page(branchFillPageParam));
    }

    /**
     * 添加分公司评级（二期）- 分公司评级填报
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司评级（二期）- 分公司评级填报")
    @CommonLog("添加分公司评级（二期）- 分公司评级填报")
//    @SaCheckPermission("/sys/hr/branchRating/fill/add")
    @PostMapping("/sys/hr/branchRating/fill/add")
    public CommonResult<String> add(@RequestBody @Valid BranchFillAddParam branchFillAddParam) {
        branchFillService.add(branchFillAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司评级（二期）- 分公司评级填报
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司评级（二期）- 分公司评级填报")
    @CommonLog("编辑分公司评级（二期）- 分公司评级填报")
//    @SaCheckPermission("/sys/hr/branchRating/fill/edit")
    @PostMapping("/sys/hr/branchRating/fill/edit")
    public CommonResult<String> edit(@RequestBody @Valid BranchFillEditParam branchFillEditParam) {
        branchFillService.edit(branchFillEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司评级（二期）- 分公司评级填报
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司评级（二期）- 分公司评级填报")
    @CommonLog("删除分公司评级（二期）- 分公司评级填报")
//    @SaCheckPermission("/sys/hr/branchRating/fill/delete")
    @PostMapping("/sys/hr/branchRating/fill/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BranchFillIdParam> branchFillIdParamList) {
        branchFillService.delete(branchFillIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司评级（二期）- 分公司评级填报详情
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司评级（二期）- 分公司评级填报详情")
//    @SaCheckPermission("/sys/hr/branchRating/fill/detail")
    @GetMapping("/sys/hr/branchRating/fill/detail")
    public CommonResult<BranchFill> detail(@Valid BranchFillIdParam branchFillIdParam) {
        return CommonResult.data(branchFillService.detail(branchFillIdParam));
    }


    /**
     * 获取分公司评级（二期）- 分公司评级填报 - 填报季度（近三年）
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取分公司评级（二期）- 分公司评级填报 - 填报季度（近三年）")
    @GetMapping("/sys/hr/branchRating/fill/getQuarterList")
    public CommonResult<List<String>> getQuarterList() {
        return CommonResult.data(branchFillService.getQuarterList());
    }


    /**
     * 获取分公司评级（二期）- 分公司评级填报 - 填报季度 下 未填报的分公司
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取分公司评级（二期）- 分公司评级填报 - 填报季度 下 未填报的分公司")
    @GetMapping("/sys/hr/branchRating/fill/getBranchList")
    public CommonResult<List<PartnerInfo>> getBranchList(String fillSeason) {
        return CommonResult.data(branchFillService.getBranchList(fillSeason));
    }


    /**
     * 编辑分公司评级（二期）- 公示（全部|某一个）
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("编辑分公司评级（二期）- 公示（全部|某一个）")
    @CommonLog("编辑分公司评级（二期）- 公示（全部|某一个）")
    @PostMapping("/sys/hr/branchRating/fill/show")
    public CommonResult<String> show(@RequestBody @Valid BranchFillShowParam showParam) {
        branchFillService.show(showParam);
        return CommonResult.ok();
    }



    /**
     * 获取分公司评级（二期）- 分公司评级填报 - 列表
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取分公司评级（二期）- 分公司评级填报 - 列表")
    @GetMapping("/sys/hr/branchRating/fill/getBranchFillList")
    public CommonResult<List<BranchFillVo>> getBranchFillList(@Valid BranchFillParam fillParam) {
        return CommonResult.data(branchFillService.getBranchFillList(fillParam));
    }

}
