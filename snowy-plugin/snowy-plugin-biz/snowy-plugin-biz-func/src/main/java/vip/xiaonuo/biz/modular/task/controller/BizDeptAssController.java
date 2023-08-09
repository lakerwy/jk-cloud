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
package vip.xiaonuo.biz.modular.task.controller;

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
import vip.xiaonuo.biz.modular.task.entity.BizDeptAss;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.biz.modular.task.service.BizDeptAssService;
import vip.xiaonuo.biz.modular.task.vo.DeptAssReportVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 业务表-部门绩效考核-考核表控制器
 *
 * @author yong.ye
 * @date  2023/08/09 08:38
 */
@Api(tags = "业务表-部门绩效考核-考核表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizDeptAssController {

    @Resource
    private BizDeptAssService bizDeptAssService;

    /**
     * 获取业务表-部门绩效考核-考核表分页
     *
     * @author yong.ye
     * @date  2023/08/09 08:38
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-部门绩效考核-考核表分页")
    @GetMapping("/biz/task/dept/ass/page")
    public CommonResult<Page<BizDeptAss>> page(BizDeptAssPageParam bizDeptAssPageParam) {
        return CommonResult.data(bizDeptAssService.page(bizDeptAssPageParam));
    }

    /**
     * 添加业务表-部门绩效考核-考核表
     *
     * @author yong.ye
     * @date  2023/08/09 08:38
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-部门绩效考核-考核表")
    @CommonLog("添加业务表-部门绩效考核-考核表")
    @PostMapping("/biz/task/dept/ass/add")
    public CommonResult<String> add(@RequestBody @Valid BizDeptAssAddParam bizDeptAssAddParam) {
        bizDeptAssService.add(bizDeptAssAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-部门绩效考核-考核表
     *
     * @author yong.ye
     * @date  2023/08/09 08:38
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-部门绩效考核-考核表")
    @CommonLog("编辑业务表-部门绩效考核-考核表")
    @PostMapping("/biz/task/dept/ass/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizDeptAssEditParam bizDeptAssEditParam) {
        bizDeptAssService.edit(bizDeptAssEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-部门绩效考核-考核表
     *
     * @author yong.ye
     * @date  2023/08/09 08:38
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-部门绩效考核-考核表")
    @CommonLog("删除业务表-部门绩效考核-考核表")
    @PostMapping("/biz/task/dept/ass/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizDeptAssIdParam> bizDeptAssIdParamList) {
        bizDeptAssService.delete(bizDeptAssIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-部门绩效考核-考核表详情
     *
     * @author yong.ye
     * @date  2023/08/09 08:38
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-部门绩效考核-考核表详情")
    @GetMapping("/biz/task/dept/ass/detail")
    public CommonResult<BizDeptAss> detail(@Valid BizDeptAssIdParam bizDeptAssIdParam) {
        return CommonResult.data(bizDeptAssService.detail(bizDeptAssIdParam));
    }


    /**
     * 获取业务表-部门绩效考核-年度考核报表
     *
     * @author yong.ye
     * @date  2023/08/09 08:38
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取业务表-部门绩效考核-年度考核报表")
    @GetMapping("/biz/task/dept/ass/yearReport")
    public CommonResult<List<DeptAssReportVo>> yearReport(DeptAssReportParam deptAssReportParam) {
        return CommonResult.data(bizDeptAssService.yearReport(deptAssReportParam));
    }
}
