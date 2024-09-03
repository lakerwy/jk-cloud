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
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchRating;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchRatingService;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchRatingVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分公司评级控制器
 *
 * @author pzh
 * @date  2024/09/03 10:03
 */
@Api(tags = "分公司评级控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BranchRatingController {

    @Resource
    private BranchRatingService branchRatingService;

    /**
     * 获取分公司评级分页
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
//    @ApiOperationSupport(order = 1)
//    @ApiOperation("获取分公司评级分页")
//    // @SaCheckPermission("/sys/branchRating/page")
//    @GetMapping("/sys/branchRating/page")
//    public CommonResult<Page<BranchRating>> page(BranchRatingPageParam branchRatingPageParam) {
//        return CommonResult.data(branchRatingService.page(branchRatingPageParam));
//    }


    /**
     * 获取分公司评级列表
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取分公司评级列表")
    // @SaCheckPermission("/sys/branchRating/list")
    @GetMapping("/sys/branchRating/list")
    public CommonResult<List<BranchRatingVo>> list(BranchRatingListParam branchRatingListParam) {
        return CommonResult.data(branchRatingService.getList(branchRatingListParam));
    }

    /**
     * 添加分公司评级
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加分公司评级")
    @CommonLog("添加分公司评级")
    // @SaCheckPermission("/sys/branchRating/add")
    @PostMapping("/sys/branchRating/add")
    public CommonResult<String> add(@RequestBody @Valid BranchRatingAddParam branchRatingAddParam) {
        branchRatingService.add(branchRatingAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑分公司评级
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑分公司评级")
    @CommonLog("编辑分公司评级")
    // @SaCheckPermission("/sys/branchRating/edit")
    @PostMapping("/sys/branchRating/edit")
    public CommonResult<String> edit(@RequestBody @Valid BranchRatingEditParam branchRatingEditParam) {
        branchRatingService.edit(branchRatingEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除分公司评级
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除分公司评级")
    @CommonLog("删除分公司评级")
    // @SaCheckPermission("/sys/branchRating/delete")
    @PostMapping("/sys/branchRating/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BranchRatingIdParam> branchRatingIdParamList) {
        branchRatingService.delete(branchRatingIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取分公司评级详情
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取分公司评级详情")
    // @SaCheckPermission("/sys/branchRating/detail")
    @GetMapping("/sys/branchRating/detail")
    public CommonResult<BranchRating> detail(@Valid BranchRatingIdParam branchRatingIdParam) {
        return CommonResult.data(branchRatingService.detail(branchRatingIdParam));
    }

    /**
     * 分公司评级 - 导入
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("分公司评级 - 导入")
    @CommonLog("分公司评级 - 导入")
    @PostMapping("/biz/branchRating/importData")
    public CommonResult<String> importData(@RequestBody @Valid ImportBranchRating input) {
        branchRatingService.importData(input);
        return CommonResult.ok();
    }

    /**
     * 分公司评级 - 设置公示状态
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("分公司评级 - 设置公示状态")
    @CommonLog("分公司评级 - 设置公示状态")
    @PostMapping("/biz/branchRating/setStatus")
    public CommonResult<String> setStatus(@RequestBody BranchRatingStatusEditParam branchRatingStatusEditParam) {
        branchRatingService.setStatus(branchRatingStatusEditParam);
        return CommonResult.ok();
    }
}
