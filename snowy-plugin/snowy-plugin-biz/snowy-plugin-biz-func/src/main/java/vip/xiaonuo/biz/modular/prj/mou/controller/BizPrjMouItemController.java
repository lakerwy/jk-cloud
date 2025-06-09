package vip.xiaonuo.biz.modular.prj.mou.controller;

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
import vip.xiaonuo.biz.modular.prj.mou.entity.BizPrjMouItem;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemAddParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemEditParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemIdParam;
import vip.xiaonuo.biz.modular.prj.mou.param.BizPrjMouItemPageParam;
import vip.xiaonuo.biz.modular.prj.mou.service.BizPrjMouItemService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 项目承接备忘录 - 项目生产内部分工控制器
 *
 * @author yy
 * @date  2025/06/06 11:43
 */
@Api(tags = "项目承接备忘录 - 项目生产内部分工控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizPrjMouItemController {

    @Resource
    private BizPrjMouItemService bizPrjMouItemService;

    /**
     * 获取项目承接备忘录 - 项目生产内部分工分页
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取项目承接备忘录 - 项目生产内部分工分页")
//    @SaCheckPermission("/biz/prj/mou/item/page")
    @GetMapping("/biz/prj/mou/item/page")
    public CommonResult<Page<BizPrjMouItem>> page(BizPrjMouItemPageParam bizPrjMouItemPageParam) {
        return CommonResult.data(bizPrjMouItemService.page(bizPrjMouItemPageParam));
    }

    /**
     * 添加项目承接备忘录 - 项目生产内部分工
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加项目承接备忘录 - 项目生产内部分工")
    @CommonLog("添加项目承接备忘录 - 项目生产内部分工")
//    @SaCheckPermission("/biz/prj/mou/item/add")
    @PostMapping("/biz/prj/mou/item/add")
    public CommonResult<String> add(@RequestBody @Valid BizPrjMouItemAddParam bizPrjMouItemAddParam) {
        bizPrjMouItemService.add(bizPrjMouItemAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑项目承接备忘录 - 项目生产内部分工
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑项目承接备忘录 - 项目生产内部分工")
    @CommonLog("编辑项目承接备忘录 - 项目生产内部分工")
//    @SaCheckPermission("/biz/prj/mou/item/edit")
    @PostMapping("/biz/prj/mou/item/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizPrjMouItemEditParam bizPrjMouItemEditParam) {
        bizPrjMouItemService.edit(bizPrjMouItemEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除项目承接备忘录 - 项目生产内部分工
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除项目承接备忘录 - 项目生产内部分工")
    @CommonLog("删除项目承接备忘录 - 项目生产内部分工")
//    @SaCheckPermission("/biz/prj/mou/item/delete")
    @PostMapping("/biz/prj/mou/item/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizPrjMouItemIdParam> bizPrjMouItemIdParamList) {
        bizPrjMouItemService.delete(bizPrjMouItemIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取项目承接备忘录 - 项目生产内部分工详情
     *
     * @author yy
     * @date  2025/06/06 11:43
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取项目承接备忘录 - 项目生产内部分工详情")
//    @SaCheckPermission("/biz/prj/mou/item/detail")
    @GetMapping("/biz/prj/mou/item/detail")
    public CommonResult<BizPrjMouItem> detail(@Valid BizPrjMouItemIdParam bizPrjMouItemIdParam) {
        return CommonResult.data(bizPrjMouItemService.detail(bizPrjMouItemIdParam));
    }
}
