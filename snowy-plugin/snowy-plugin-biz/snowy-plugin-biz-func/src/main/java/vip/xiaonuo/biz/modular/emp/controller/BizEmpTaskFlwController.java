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
package vip.xiaonuo.biz.modular.emp.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import vip.xiaonuo.biz.modular.emp.vo.BizEmpTaskFlwVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwAddParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwEditParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwIdParam;
import vip.xiaonuo.biz.modular.emp.param.BizEmpTaskFlwPageParam;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskFlwService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 业务表-员工绩效考核-季度任务流程审批单控制器
 *
 * @author yong.ye
 * @date  2023/09/19 08:48
 */
@Api(tags = "业务表-员工绩效考核-季度任务流程审批单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizEmpTaskFlwController {

    @Resource
    private BizEmpTaskFlwService bizEmpTaskFlwService;

    /**
     * 获取业务表-员工绩效考核-季度任务流程审批单分页
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-员工绩效考核-季度任务流程审批单分页")
//    @SaCheckPermission("/biz/emp/taskFlw/page")
    @GetMapping("/biz/emp/taskFlw/page")
    public CommonResult<IPage<BizEmpTaskFlwVo>> page(BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam) {
        return CommonResult.data(bizEmpTaskFlwService.pageList(bizEmpTaskFlwPageParam));
    }

//    /**
//     * 添加业务表-员工绩效考核-季度任务流程审批单
//     *
//     * @author yong.ye
//     * @date  2023/09/19 08:48
//     */
//    @ApiOperationSupport(order = 2)
//    @ApiOperation("添加业务表-员工绩效考核-季度任务流程审批单")
//    @CommonLog("添加业务表-员工绩效考核-季度任务流程审批单")
////    @SaCheckPermission("/biz/emp/taskFlw/add")
//    @PostMapping("/biz/emp/taskFlw/add")
//    public CommonResult<String> add(@RequestBody @Valid BizEmpTaskFlwAddParam bizEmpTaskFlwAddParam) {
//        bizEmpTaskFlwService.add(bizEmpTaskFlwAddParam);
//        return CommonResult.ok();
//    }

//    /**
//     * 编辑业务表-员工绩效考核-季度任务流程审批单
//     *
//     * @author yong.ye
//     * @date  2023/09/19 08:48
//     */
//    @ApiOperationSupport(order = 3)
//    @ApiOperation("编辑业务表-员工绩效考核-季度任务流程审批单")
//    @CommonLog("编辑业务表-员工绩效考核-季度任务流程审批单")
////    @SaCheckPermission("/biz/emp/taskFlw/edit")
//    @PostMapping("/biz/emp/taskFlw/edit")
//    public CommonResult<String> edit(@RequestBody @Valid BizEmpTaskFlwEditParam bizEmpTaskFlwEditParam) {
//        bizEmpTaskFlwService.edit(bizEmpTaskFlwEditParam);
//        return CommonResult.ok();
//    }

    /**
     * 删除业务表-员工绩效考核-季度任务流程审批单
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-员工绩效考核-季度任务流程审批单")
    @CommonLog("删除业务表-员工绩效考核-季度任务流程审批单")
//    @SaCheckPermission("/biz/emp/taskFlw/delete")
    @PostMapping("/biz/emp/taskFlw/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizEmpTaskFlwIdParam> bizEmpTaskFlwIdParamList) {
        bizEmpTaskFlwService.delete(bizEmpTaskFlwIdParamList);
        return CommonResult.ok();
    }

//    /**
//     * 获取业务表-员工绩效考核-季度任务流程审批单详情
//     *
//     * @author yong.ye
//     * @date  2023/09/19 08:48
//     */
//    @ApiOperationSupport(order = 5)
//    @ApiOperation("获取业务表-员工绩效考核-季度任务流程审批单详情")
//    //@SaCheckPermission("/biz/emp/taskFlw/detail")
//    @GetMapping("/biz/emp/taskFlw/detail")
//    public CommonResult<BizEmpTaskFlw> detail(@Valid BizEmpTaskFlwIdParam bizEmpTaskFlwIdParam) {
//        return CommonResult.data(bizEmpTaskFlwService.detail(bizEmpTaskFlwIdParam));
//    }
}
