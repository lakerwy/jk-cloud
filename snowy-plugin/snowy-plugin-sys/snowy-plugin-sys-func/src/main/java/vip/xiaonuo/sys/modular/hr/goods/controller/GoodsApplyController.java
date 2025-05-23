package vip.xiaonuo.sys.modular.hr.goods.controller;

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
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsApply;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyAddParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyEditParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyIdParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyPageParam;
import vip.xiaonuo.sys.modular.hr.goods.service.GoodsApplyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 物资领用申请控制器
 *
 * @author yy
 * @date  2025/05/23 14:34
 */
@Api(tags = "物资领用申请控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class GoodsApplyController {

    @Resource
    private GoodsApplyService goodsApplyService;

    /**
     * 获取物资领用申请分页
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取物资领用申请分页")
//    @SaCheckPermission("/sys/hr/goods/page")
    @GetMapping("/sys/hr/goods/page")
    public CommonResult<Page<GoodsApply>> page(GoodsApplyPageParam goodsApplyPageParam) {
        return CommonResult.data(goodsApplyService.page(goodsApplyPageParam));
    }

    /**
     * 添加物资领用申请
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加物资领用申请")
    @CommonLog("添加物资领用申请")
//    @SaCheckPermission("/sys/hr/goods/add")
    @PostMapping("/sys/hr/goods/add")
    public CommonResult<String> add(@RequestBody @Valid GoodsApplyAddParam goodsApplyAddParam) {
        goodsApplyService.add(goodsApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑物资领用申请
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑物资领用申请")
    @CommonLog("编辑物资领用申请")
//    @SaCheckPermission("/sys/hr/goods/edit")
    @PostMapping("/sys/hr/goods/edit")
    public CommonResult<String> edit(@RequestBody @Valid GoodsApplyEditParam goodsApplyEditParam) {
        goodsApplyService.edit(goodsApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除物资领用申请
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除物资领用申请")
    @CommonLog("删除物资领用申请")
//    @SaCheckPermission("/sys/hr/goods/delete")
    @PostMapping("/sys/hr/goods/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<GoodsApplyIdParam> goodsApplyIdParamList) {
        goodsApplyService.delete(goodsApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取物资领用申请详情
     *
     * @author yy
     * @date  2025/05/23 14:34
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取物资领用申请详情")
//    @SaCheckPermission("/sys/hr/goods/detail")
    @GetMapping("/sys/hr/goods/detail")
    public CommonResult<GoodsApply> detail(@Valid GoodsApplyIdParam goodsApplyIdParam) {
        return CommonResult.data(goodsApplyService.detail(goodsApplyIdParam));
    }
}
