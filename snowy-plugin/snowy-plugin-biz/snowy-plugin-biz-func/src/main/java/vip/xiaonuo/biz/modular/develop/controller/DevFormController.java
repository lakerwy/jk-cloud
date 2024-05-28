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
package vip.xiaonuo.biz.modular.develop.controller;

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
import vip.xiaonuo.biz.modular.develop.vo.DevFormVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.develop.param.DevFormAddParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormEditParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormIdParam;
import vip.xiaonuo.biz.modular.develop.param.DevFormPageParam;
import vip.xiaonuo.biz.modular.develop.service.DevFormService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 需求单 - 发起流程控制器
 *
 * @author yong.ye
 * @date  2024/05/27 16:08
 */
@Api(tags = "需求单 - 发起流程控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class DevFormController {

    @Resource
    private DevFormService devFormService;

    /**
     * 获取需求单 - 发起流程分页【携带审核通过确认单信息】
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取需求单 - 发起流程分页 【携带审核通过确认单信息】")
//    @SaCheckPermission("/biz/develop/form/page")
    @GetMapping("/biz/develop/form/page")
    public CommonResult<Page<DevFormVo>> page(DevFormPageParam devFormPageParam) {
        return CommonResult.data(devFormService.page(devFormPageParam));
    }

    /**
     * 添加需求单 - 发起流程
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加需求单 - 发起流程")
    @CommonLog("添加需求单 - 发起流程")
//    @SaCheckPermission("/biz/develop/form/add")
    @PostMapping("/biz/develop/form/add")
    public CommonResult<String> add(@RequestBody @Valid DevFormAddParam devFormAddParam) {
        devFormService.add(devFormAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑需求单 - 发起流程
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑需求单 - 发起流程")
    @CommonLog("编辑需求单 - 发起流程")
//    @SaCheckPermission("/biz/develop/form/edit")
    @PostMapping("/biz/develop/form/edit")
    public CommonResult<String> edit(@RequestBody @Valid DevFormEditParam devFormEditParam) {
        devFormService.edit(devFormEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除需求单 - 发起流程
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除需求单 - 发起流程")
    @CommonLog("删除需求单 - 发起流程")
//    @SaCheckPermission("/biz/develop/form/delete")
    @PostMapping("/biz/develop/form/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<DevFormIdParam> devFormIdParamList) {
        devFormService.delete(devFormIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取需求单 - 发起流程详情
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取需求单 - 发起流程详情")
//    @SaCheckPermission("/biz/develop/form/detail")
    @GetMapping("/biz/develop/form/detail")
    public CommonResult<DevFormVo> detail(@Valid DevFormIdParam devFormIdParam) {
        return CommonResult.data(devFormService.detail(devFormIdParam));
    }


    /**
     * 获取需求单分页 - 【审核通过，且未发起过确认单流程】
     *
     * @author yong.ye
     * @date  2024/05/27 16:08
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取需求单分页 - 【审核通过，且未发起过确认单流程】")
    @GetMapping("/biz/develop/form/listPage")
    public CommonResult<Page<DevFormVo>> listPage(DevFormPageParam devFormPageParam) {
        return CommonResult.data(devFormService.listPage(devFormPageParam));
    }
}
