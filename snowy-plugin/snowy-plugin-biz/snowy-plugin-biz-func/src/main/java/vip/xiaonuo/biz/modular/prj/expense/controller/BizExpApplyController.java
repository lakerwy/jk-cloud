package vip.xiaonuo.biz.modular.prj.expense.controller;

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
import vip.xiaonuo.biz.modular.prj.expense.param.*;
import vip.xiaonuo.biz.modular.prj.expense.vo.BizExpApplyVo;
import vip.xiaonuo.biz.modular.prj.expense.vo.BizExpData;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpApplyService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程控制器
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 */
@Api(tags = "财务管理 - 费用报销|差旅费用报销 - 申请流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizExpApplyController {

    @Resource
    private BizExpApplyService bizExpApplyService;

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 申请流程分页
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取财务管理 - 费用报销|差旅费用报销 - 申请流程分页")
    @SaCheckPermission("/biz/prj/exp/page")
    @GetMapping("/biz/prj/exp/page")
    public CommonResult<Page<BizExpApplyVo>> page(BizExpApplyPageParam bizExpApplyPageParam) {
        return CommonResult.data(bizExpApplyService.page(bizExpApplyPageParam));
    }

    /**
     * 添加财务管理 - 费用报销|差旅费用报销 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加财务管理 - 费用报销|差旅费用报销 - 申请流程")
    @CommonLog("添加财务管理 - 费用报销|差旅费用报销 - 申请流程")
//    @SaCheckPermission("/biz/prj/exp/add")
    @PostMapping("/biz/prj/exp/add")
    public CommonResult<String> add(@RequestBody @Valid BizExpApplyAddParam bizExpApplyAddParam) {
        bizExpApplyService.add(bizExpApplyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑财务管理 - 费用报销|差旅费用报销 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑财务管理 - 费用报销|差旅费用报销 - 申请流程")
    @CommonLog("编辑财务管理 - 费用报销|差旅费用报销 - 申请流程")
//    @SaCheckPermission("/biz/prj/exp/edit")
    @PostMapping("/biz/prj/exp/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizExpApplyEditParam bizExpApplyEditParam) {
        bizExpApplyService.edit(bizExpApplyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除财务管理 - 费用报销|差旅费用报销 - 申请流程
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除财务管理 - 费用报销|差旅费用报销 - 申请流程")
    @CommonLog("删除财务管理 - 费用报销|差旅费用报销 - 申请流程")
//    @SaCheckPermission("/biz/prj/exp/delete")
    @PostMapping("/biz/prj/exp/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizExpApplyIdParam> bizExpApplyIdParamList) {
        bizExpApplyService.delete(bizExpApplyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取财务管理 - 费用报销|差旅费用报销 - 申请流程详情
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取财务管理 - 费用报销|差旅费用报销 - 申请流程详情")
//    @SaCheckPermission("/biz/prj/exp/detail")
    @GetMapping("/biz/prj/exp/detail")
    public CommonResult<BizExpApplyVo> detail(@Valid BizExpApplyIdParam bizExpApplyIdParam) {
        return CommonResult.data(bizExpApplyService.detail(bizExpApplyIdParam));
    }


    /**
     * 业务表-费用报销 - 生成打印Excel
     *
     * @author yong.ye
     * @date  2024/09/28 11:30
     */
    @ApiOperationSupport(order = 99)
    @ApiOperation("业务表-费用报销 - 生成打印Excel")
    @CommonLog("业务表-费用报销 - 生成打印Excel")
    @GetMapping("/biz/prj/exp/genExcel")
    public void genExcel(@Valid ExcelParam excelParam, HttpServletResponse response) {
        bizExpApplyService.genExcel(excelParam,response);
    }


    /**
     * 获取财务管理 - 报销统计
     *
     * @author yong.ye
     * @date  2024/07/30 15:26
     */
    @ApiOperationSupport(order = 100)
    @ApiOperation("获取财务管理 - 报销统计")
//    @SaCheckPermission("/biz/prj/exp/totalPage")
    @GetMapping("/biz/prj/exp/totalPage")
    public CommonResult<List<BizExpData>> totalPage(BizExpTotalSearch search) {
        return CommonResult.data(bizExpApplyService.totalPage(search));
    }
}
