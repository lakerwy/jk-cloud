/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.mapBiz.Chartreviews.controller;

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
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity.BizChartReviews;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.param.*;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.vo.BizChartReviewsDetailsVo;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.vo.BizChartReviewsVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.service.BizChartReviewsService;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-图审受理单
控制器
 *
 * @author mayike
 * @date  2023/06/30 16:07
 */
@Api(tags = "业务表-图审受理单 控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizChartReviewsController {

    @Resource
    private BizChartReviewsService bizChartReviewsService;

    /**
     * 获取业务表-图审受理单
分页
     *
     * @author mayike
     * @date  2023/06/30 16:07
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("获取业务表-图审受理单 分页")
//    @SaCheckPermission("/biz/Chartreviews/page")
    @GetMapping("/biz/ChartReviews/page")
    public CommonResult<Page<BizChartReviews>> page(BizChartReviewsPageParam bizChartReviewsPageParam) {
        return CommonResult.data(bizChartReviewsService.page(bizChartReviewsPageParam));
    }

    /**
     * 添加业务表-图审受理单

     *
     * @author mayike
     * @date  2023/06/30 16:07
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-图审受理单 ")
    @CommonLog("添加业务表-图审受理单 ")
//    @SaCheckPermission("/biz/Chartreviews/add")
    @PostMapping("/biz/ChartReviews/add")
    public CommonResult<String> add(@RequestBody @Valid BizChartReviewsAddParam bizChartReviewsAddParam) {
        bizChartReviewsService.add(bizChartReviewsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-图审受理单

     *
     * @author mayike
     * @date  2023/06/30 16:07
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-图审受理单 ")
    @CommonLog("编辑业务表-图审受理单 ")
//    @SaCheckPermission("/biz/Chartreviews/edit")
    @PostMapping("/biz/ChartReviews/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizChartReviewsEditParam bizChartReviewsEditParam) {
        bizChartReviewsService.edit(bizChartReviewsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-图审受理单

     *
     * @author mayike
     * @date  2023/06/30 16:07
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-图审受理单 ")
    @CommonLog("删除业务表-图审受理单 ")
//    @SaCheckPermission("/biz/Chartreviews/delete")
    @PostMapping("/biz/ChartReviews/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                       CommonValidList<BizChartReviewsIdParam> bizChartReviewsIdParamList) {
        bizChartReviewsService.delete(bizChartReviewsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-图审受理单详情
     *
     * @author mayike
     * @date  2023/06/30 16:07
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-图审受理单 详情")
//    @SaCheckPermission("/biz/Chartreviews/detail")
    @GetMapping("/biz/ChartReviews/detail")
    public CommonResult<BizChartReviewsDetailsVo> detail(@Valid BizChartReviewsIdParam bizChartReviewsIdParam) {
        //通过主表id 获取BIZ_CHART_REVIEW_DETAILS 表的列表信息 获取合同的基本信息
        return CommonResult.data(bizChartReviewsService.detail(bizChartReviewsIdParam));
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-图审受理单新分页")
    @SaCheckPermission("/biz/ChartReviews/pageList")
    @GetMapping("/biz/ChartReviews/pageList")
    public CommonResult<Page<BizChartReviewsVo>> pageList(BizChartReviewsPageParam bizChartReviewsPageParam) {
        return CommonResult.data(bizChartReviewsService.pageList(bizChartReviewsPageParam));
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation("图审受理单状态更新")
//    @SaCheckPermission("/biz/Chartreviews/page")
    @GetMapping("/biz/ChartReviews/updateStatus")
    public CommonResult<String> updateStatus(BizChartReviewsStatusParam bizChartReviewsStatusParam){
        bizChartReviewsService.updateStatus(bizChartReviewsStatusParam);
        return CommonResult.ok();
    }

}
