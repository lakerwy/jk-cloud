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
package vip.xiaonuo.sys.modular.hr.sub.controller;

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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnel;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelService;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubPersonnelVo;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * HR-分公司-人事档案控制器
 *
 * @author yong.ye
 * @date  2023/11/21 11:14
 */
@Api(tags = "HR-分公司-人事档案控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class SubPersonnelController {

    @Resource
    private SubPersonnelService subPersonnelService;

    /**
     * 获取HR-分公司-人事档案分页
     *
     * @author yong.ye
     * @date  2023/11/21 11:14
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取HR-分公司-人事档案分页")
    @SaCheckPermission("/sys/hr/sub/personnel/page")
    @GetMapping("/sys/hr/sub/personnel/page")
    public CommonResult<Page<SubPersonnel>> page(SubPersonnelPageParam subPersonnelPageParam) {
        return CommonResult.data(subPersonnelService.page(subPersonnelPageParam));
    }

    /**
     * 添加HR-分公司-人事档案
     *
     * @author yong.ye
     * @date  2023/11/21 11:14
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加HR-分公司-人事档案")
    @CommonLog("添加HR-分公司-人事档案")
    @SaCheckPermission("/sys/hr/sub/personnel/add")
    @PostMapping("/sys/hr/sub/personnel/add")
    public CommonResult<String> add(@RequestBody @Valid SubPersonnelParam subPersonnelParam) {
        subPersonnelService.add(subPersonnelParam);
        return CommonResult.ok();
    }

    /**
     * 编辑HR-分公司-人事档案
     *
     * @author yong.ye
     * @date  2023/11/21 11:14
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑HR-分公司-人事档案")
    @CommonLog("编辑HR-分公司-人事档案")
    @SaCheckPermission("/sys/hr/sub/personnel/edit")
    @PostMapping("/sys/hr/sub/personnel/edit")
    public CommonResult<String> edit(@RequestBody @Valid SubPersonnelParam subPersonnelParam) {
        subPersonnelService.edit(subPersonnelParam);
        return CommonResult.ok();
    }

    /**
     * 删除HR-分公司-人事档案
     *
     * @author yong.ye
     * @date  2023/11/21 11:14
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除HR-分公司-人事档案")
    @CommonLog("删除HR-分公司-人事档案")
    @SaCheckPermission("/sys/hr/sub/personnel/delete")
    @PostMapping("/sys/hr/sub/personnel/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<SubPersonnelIdParam> subPersonnelIdParamList) {
        subPersonnelService.delete(subPersonnelIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取HR-分公司-人事档案详情
     *
     * @author yong.ye
     * @date  2023/11/21 11:14
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取HR-分公司-人事档案详情")
    @SaCheckPermission("/sys/hr/sub/personnel/detail")
    @GetMapping("/sys/hr/sub/personnel/detail")
    public CommonResult<SubPersonnelVo> detail(@Valid SubPersonnelIdParam subPersonnelIdParam) {
        return CommonResult.data(subPersonnelService.detail(subPersonnelIdParam));
    }
}
