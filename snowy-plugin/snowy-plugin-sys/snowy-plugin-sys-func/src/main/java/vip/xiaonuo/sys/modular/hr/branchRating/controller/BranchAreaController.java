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
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchArea;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchAreaService;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchAreaVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.ImportResultT;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分公司评级（二期）- 区域经济修正系数 控制器
 *
 * @author yong.ye
 * @date  2025/03/18 08:47
 */
@Api(tags = "分公司评级（二期）- 区域经济修正系数 控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BranchAreaController {

    @Resource
    private BranchAreaService branchAreaService;

    /**
     * 获取分公司评级（二期）- 区域经济修正系数 分页
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司评级（二期）- 区域经济修正系数 分页")
//    @SaCheckPermission("/sys/hr/branchRating/area/page")
    @GetMapping("/sys/hr/branchRating/area/page")
    public CommonResult<Page<BranchArea>> page(BranchAreaPageParam branchAreaPageParam) {
        return CommonResult.data(branchAreaService.page(branchAreaPageParam));
    }

    /**
     * 添加分公司评级（二期）- 区域经济修正系数 
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司评级（二期）- 区域经济修正系数 ")
    @CommonLog("添加分公司评级（二期）- 区域经济修正系数 ")
//    @SaCheckPermission("/sys/hr/branchRating/area/add")
    @PostMapping("/sys/hr/branchRating/area/add")
    public CommonResult<String> add(@RequestBody @Valid BranchAreaAddParam branchAreaAddParam) {
        branchAreaService.add(branchAreaAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司评级（二期）- 区域经济修正系数 
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司评级（二期）- 区域经济修正系数 ")
    @CommonLog("编辑分公司评级（二期）- 区域经济修正系数 ")
//    @SaCheckPermission("/sys/hr/branchRating/area/edit")
    @PostMapping("/sys/hr/branchRating/area/edit")
    public CommonResult<String> edit(@RequestBody @Valid BranchAreaEditParam branchAreaEditParam) {
        branchAreaService.edit(branchAreaEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司评级（二期）- 区域经济修正系数 
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司评级（二期）- 区域经济修正系数 ")
    @CommonLog("删除分公司评级（二期）- 区域经济修正系数 ")
//    @SaCheckPermission("/sys/hr/branchRating/area/delete")
    @PostMapping("/sys/hr/branchRating/area/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BranchAreaIdParam> branchAreaIdParamList) {
        branchAreaService.delete(branchAreaIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司评级（二期）- 区域经济修正系数 详情
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司评级（二期）- 区域经济修正系数 详情")
//    @SaCheckPermission("/sys/hr/branchRating/area/detail")
    @GetMapping("/sys/hr/branchRating/area/detail")
    public CommonResult<BranchArea> detail(@Valid BranchAreaIdParam branchAreaIdParam) {
        return CommonResult.data(branchAreaService.detail(branchAreaIdParam));
    }


    /**
     * 获取分公司评级（二期）- 区域经济修正系数 - 分公司下拉框（排除已填报区域经济调整系数）
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取分公司评级（二期）- 区域经济修正系数 - 分公司下拉框（排除已填报区域经济调整系数）")
    @GetMapping("/sys/hr/branchRating/area/getBranchList")
    public CommonResult<List<PartnerInfo>> getBranchList() {
        return CommonResult.data(branchAreaService.getBranchList());
    }


    /**
     * 获取分公司评级（二期）- 区域经济修正系数 - 列表+图
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取分公司评级（二期）- 区域经济修正系数 - 列表+图")
    @GetMapping("/sys/hr/branchRating/area/getList")
    public CommonResult<BranchAreaVo> getList(BranchAreaPageParam branchAreaPageParam) {
        return CommonResult.data(branchAreaService.getList(branchAreaPageParam));
    }


    /**
     * 分公司评级（二期）- 区域经济修正系数 - 导入
     */
    @ApiOperationSupport(order = 8)
    @ApiOperation("分公司评级（二期）- 区域经济修正系数 - 导入")
    @CommonLog("分公司评级（二期）- 区域经济修正系数 - 导入")
    @PostMapping("/sys/hr/branchRating/area/doImport")
    public CommonResult<ImportResultT> doImport(@RequestBody @Valid ImportParam importParam) {
        return CommonResult.data(branchAreaService.doImport(importParam));
    }


    /**
     * 获取分公司评级（二期）- 区域经济修正系数 (根据分公司，获取设置的经济系数)
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    @ApiOperationSupport(order = 9)
    @ApiOperation("获取分公司评级（二期）- 区域经济修正系数 (根据分公司，获取设置的经济系数)")
    @GetMapping("/sys/hr/branchRating/area/getByBranch")
    public CommonResult<BranchArea> getByBranch(@Valid BranchAreaParam branchAreaParam) {
        return CommonResult.data(branchAreaService.getByBranch(branchAreaParam));
    }

}
