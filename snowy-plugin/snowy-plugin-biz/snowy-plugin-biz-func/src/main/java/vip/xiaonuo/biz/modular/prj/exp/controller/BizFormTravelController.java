package vip.xiaonuo.biz.modular.prj.exp.controller;

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
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormTravelTotal;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravel;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 费用报销（二期）- 差旅费用报销单控制器
 *
 * @author yong.ye
 * @date  2025/02/24 15:17
 */
@Api(tags = "费用报销（二期）- 差旅费用报销单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFormTravelController {

    @Resource
    private BizFormTravelService bizFormTravelService;

    /**
     * 获取费用报销（二期）- 差旅费用报销单分页
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取费用报销（二期）- 差旅费用报销单分页")
    @SaCheckPermission("/biz/prj/exp/travel/page")
    @GetMapping("/biz/prj/exp/travel/page")
    public CommonResult<Page<BizFormTravel>> page(BizFormTravelPageParam bizFormTravelPageParam) {
        return CommonResult.data(bizFormTravelService.page(bizFormTravelPageParam));
    }

    /**
     * 添加费用报销（二期）- 差旅费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加费用报销（二期）- 差旅费用报销单")
    @CommonLog("添加费用报销（二期）- 差旅费用报销单")
//    @SaCheckPermission("/biz/prj/exp/travel/add")
    @PostMapping("/biz/prj/exp/travel/add")
    public CommonResult<String> add(@RequestBody @Valid BizFormTravelAddParam bizFormTravelAddParam) {
        bizFormTravelService.add(bizFormTravelAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑费用报销（二期）- 差旅费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑费用报销（二期）- 差旅费用报销单")
    @CommonLog("编辑费用报销（二期）- 差旅费用报销单")
//    @SaCheckPermission("/biz/prj/exp/travel/edit")
    @PostMapping("/biz/prj/exp/travel/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFormTravelEditParam bizFormTravelEditParam) {
        bizFormTravelService.edit(bizFormTravelEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除费用报销（二期）- 差旅费用报销单
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除费用报销（二期）- 差旅费用报销单")
    @CommonLog("删除费用报销（二期）- 差旅费用报销单")
//    @SaCheckPermission("/biz/prj/exp/travel/delete")
    @PostMapping("/biz/prj/exp/travel/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFormTravelIdParam> bizFormTravelIdParamList) {
        bizFormTravelService.delete(bizFormTravelIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取费用报销（二期）- 差旅费用报销单详情
     *
     * @author yong.ye
     * @date  2025/02/24 15:17
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取费用报销（二期）- 差旅费用报销单详情")
//    @SaCheckPermission("/biz/prj/exp/travel/detail")
    @GetMapping("/biz/prj/exp/travel/detail")
    public CommonResult<BizFormTravel> detail(@Valid BizFormTravelIdParam bizFormTravelIdParam) {
        return CommonResult.data(bizFormTravelService.detail(bizFormTravelIdParam));
    }





    /**
     * 获取费用报销（二期）- 差旅费用报销单 - 列表统计（所有满足条件的数据统计）
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 66)
    @ApiOperation("获取费用报销（二期）- 差旅费用报销单 - 列表统计（所有满足条件的数据统计）")
    @SaCheckPermission("/biz/prj/exp/travel/pageTotal")
    @GetMapping("/biz/prj/exp/travel/pageTotal")
    public CommonResult<BizFormTravelTotal> pageTotal(BizFormTravelPageParam bizFormTravelPageParam) {
        return CommonResult.data(bizFormTravelService.pageTotal(bizFormTravelPageParam));
    }
}
