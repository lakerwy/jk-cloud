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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.NoveltySearch;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.NoveltySearchService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 查新控制器
 *
 * @author pzh
 * @date  2024/10/18 08:44
 */
@Api(tags = "查新控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class NoveltySearchController {

    @Resource
    private NoveltySearchService noveltySearchService;

    /**
     * 获取查新分页
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取查新分页")
    // @SaCheckPermission("/sys/techAchive/noveltySearch/page")
    @GetMapping("/sys/techAchive/noveltySearch/page")
    public CommonResult<Page<NoveltySearch>> page(NoveltySearchPageParam noveltySearchPageParam) {
        return CommonResult.data(noveltySearchService.page(noveltySearchPageParam));
    }

    /**
     * 添加查新
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加查新")
    @CommonLog("添加查新")
    // @SaCheckPermission("/sys/techAchive/noveltySearch/add")
    @PostMapping("/sys/techAchive/noveltySearch/add")
    public CommonResult<String> add(@RequestBody @Valid NoveltySearchAddParam noveltySearchAddParam) {
        noveltySearchService.add(noveltySearchAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑查新
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑查新")
    @CommonLog("编辑查新")
    // @SaCheckPermission("/sys/techAchive/noveltySearch/edit")
    @PostMapping("/sys/techAchive/noveltySearch/edit")
    public CommonResult<String> edit(@RequestBody @Valid NoveltySearchEditParam noveltySearchEditParam) {
        noveltySearchService.edit(noveltySearchEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除查新
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除查新")
    @CommonLog("删除查新")
    // @SaCheckPermission("/sys/techAchive/noveltySearch/delete")
    @PostMapping("/sys/techAchive/noveltySearch/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<NoveltySearchIdParam> noveltySearchIdParamList) {
        noveltySearchService.delete(noveltySearchIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取查新详情
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取查新详情")
    // @SaCheckPermission("/sys/techAchive/noveltySearch/detail")
    @GetMapping("/sys/techAchive/noveltySearch/detail")
    public CommonResult<NoveltySearch> detail(@Valid NoveltySearchIdParam noveltySearchIdParam) {
        return CommonResult.data(noveltySearchService.detail(noveltySearchIdParam));
    }
}
