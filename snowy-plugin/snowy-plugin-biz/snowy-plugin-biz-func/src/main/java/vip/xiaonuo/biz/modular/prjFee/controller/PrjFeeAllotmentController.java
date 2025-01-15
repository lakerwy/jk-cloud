package vip.xiaonuo.biz.modular.prjFee.controller;

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
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeAllotmentItemVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotment;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentAddParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentEditParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentIdParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentPageParam;
import vip.xiaonuo.biz.modular.prjFee.service.PrjFeeAllotmentService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 工程款拨付审批控制器
 *
 * @author pzh
 * @date  2025/01/15 09:22
 */
@Api(tags = "工程款拨付审批控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PrjFeeAllotmentController {

    @Resource
    private PrjFeeAllotmentService prjFeeAllotmentService;

    /**
     * 获取工程款拨付审批分页
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取工程款拨付审批分页")
    // @SaCheckPermission("/biz/prjFee/page")
    @GetMapping("/biz/prjFee/page")
    public CommonResult<Page<PrjFeeAllotment>> page(PrjFeeAllotmentPageParam prjFeeAllotmentPageParam) {
        return CommonResult.data(prjFeeAllotmentService.page(prjFeeAllotmentPageParam));
    }

    /**
     * 添加工程款拨付审批
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加工程款拨付审批")
    @CommonLog("添加工程款拨付审批")
    // @SaCheckPermission("/biz/prjFee/add")
    @PostMapping("/biz/prjFee/add")
    public CommonResult<String> add(@RequestBody @Valid PrjFeeAllotmentAddParam prjFeeAllotmentAddParam) {
        prjFeeAllotmentService.add(prjFeeAllotmentAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑工程款拨付审批
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑工程款拨付审批")
    @CommonLog("编辑工程款拨付审批")
    // @SaCheckPermission("/biz/prjFee/edit")
    @PostMapping("/biz/prjFee/edit")
    public CommonResult<String> edit(@RequestBody @Valid PrjFeeAllotmentEditParam prjFeeAllotmentEditParam) {
        prjFeeAllotmentService.edit(prjFeeAllotmentEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除工程款拨付审批
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除工程款拨付审批")
    @CommonLog("删除工程款拨付审批")
    // @SaCheckPermission("/biz/prjFee/delete")
    @PostMapping("/biz/prjFee/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<PrjFeeAllotmentIdParam> prjFeeAllotmentIdParamList) {
        prjFeeAllotmentService.delete(prjFeeAllotmentIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取工程款拨付审批详情
     *
     * @author pzh
     * @date  2025/01/15 09:22
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取工程款拨付审批详情")
    // @SaCheckPermission("/biz/prjFee/detail")
    @GetMapping("/biz/prjFee/detail")
    public CommonResult<PrjFeeAllotmentItemVo> detail(@Valid PrjFeeAllotmentIdParam prjFeeAllotmentIdParam) {
        return CommonResult.data(prjFeeAllotmentService.detail(prjFeeAllotmentIdParam));
    }
}
