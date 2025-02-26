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
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormBackVo;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormCashTotal;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormBack;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormBackService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 费用报销（二期）- 归还登记控制器
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 */
@Api(tags = "费用报销（二期）- 归还登记控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFormBackController {

    @Resource
    private BizFormBackService bizFormBackService;

    /**
     * 获取费用报销（二期）- 归还登记分页
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取费用报销（二期）- 归还登记分页")
    @SaCheckPermission("/biz/prj/exp/back/page")
    @GetMapping("/biz/prj/exp/back/page")
    public CommonResult<Page<BizFormBackVo>> page(BizFormBackPageParam bizFormBackPageParam) {
        return CommonResult.data(bizFormBackService.page(bizFormBackPageParam));
    }

    /**
     * 添加费用报销（二期）- 归还登记
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加费用报销（二期）- 归还登记")
    @CommonLog("添加费用报销（二期）- 归还登记")
//    @SaCheckPermission("/biz/prj/exp/back/add")
    @PostMapping("/biz/prj/exp/back/add")
    public CommonResult<String> add(@RequestBody @Valid BizFormBackAddParam bizFormBackAddParam) {
        bizFormBackService.add(bizFormBackAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑费用报销（二期）- 归还登记
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑费用报销（二期）- 归还登记")
    @CommonLog("编辑费用报销（二期）- 归还登记")
//    @SaCheckPermission("/biz/prj/exp/back/edit")
    @PostMapping("/biz/prj/exp/back/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFormBackEditParam bizFormBackEditParam) {
        bizFormBackService.edit(bizFormBackEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除费用报销（二期）- 归还登记
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除费用报销（二期）- 归还登记")
    @CommonLog("删除费用报销（二期）- 归还登记")
//    @SaCheckPermission("/biz/prj/exp/back/delete")
    @PostMapping("/biz/prj/exp/back/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFormBackIdParam> bizFormBackIdParamList) {
        bizFormBackService.delete(bizFormBackIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取费用报销（二期）- 归还登记详情
     *
     * @author yong.ye
     * @date  2025/02/25 09:15
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取费用报销（二期）- 归还登记详情")
//    @SaCheckPermission("/biz/prj/exp/back/detail")
    @GetMapping("/biz/prj/exp/back/detail")
    public CommonResult<BizFormBack> detail(@Valid BizFormBackIdParam bizFormBackIdParam) {
        return CommonResult.data(bizFormBackService.detail(bizFormBackIdParam));
    }



    /**
     * 获取费用报销（二期）- 归还登记 - 列表统计（所有满足条件的数据统计）
     *
     * @author yong.ye
     * @date  2025/02/24 15:12
     */
    @ApiOperationSupport(order = 66)
    @ApiOperation("获取费用报销（二期）- 归还登记 - 列表统计（所有满足条件的数据统计）")
    @SaCheckPermission("/biz/prj/exp/back/pageTotal")
    @GetMapping("/biz/prj/exp/back/pageTotal")
    public CommonResult<BizFormCashTotal> pageTotal(BizFormBackPageParam bizFormBackPageParam) {
        return CommonResult.data(bizFormBackService.pageTotal(bizFormBackPageParam));
    }

}
