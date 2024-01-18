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
import vip.xiaonuo.sys.modular.hr.sub.entity.HrSubTransfer;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.HrSubTransferPageParam;
import vip.xiaonuo.sys.modular.hr.sub.service.HrSubTransferService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * HR-分公司-工资社保转入|转出总部控制器
 *
 * @author yong.ye
 * @date  2024/01/18 09:41
 */
@Api(tags = "HR-分公司-工资社保转入|转出总部控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HrSubTransferController {

    @Resource
    private HrSubTransferService hrSubTransferService;

    /**
     * 获取HR-分公司-工资社保转入|转出总部分页【含权限控制】
     *
     * @author yong.ye
     * @date  2024/01/18 09:41
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取HR-分公司-工资社保转入|转出总部分页【含权限控制】")
    @SaCheckPermission("/sys/hr/sub/transfer/page")
    @GetMapping("/sys/hr/sub/transfer/page")
    public CommonResult<Page<HrSubTransfer>> page(HrSubTransferPageParam hrSubTransferPageParam) {
        return CommonResult.data(hrSubTransferService.page(hrSubTransferPageParam));
    }

    /**
     * 添加HR-分公司-工资社保转入|转出总部
     *
     * @author yong.ye
     * @date  2024/01/18 09:41
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加HR-分公司-工资社保转入|转出总部")
    @CommonLog("添加HR-分公司-工资社保转入|转出总部")
//    @SaCheckPermission("/sys/hr/sub/transfer/add")
    @PostMapping("/sys/hr/sub/transfer/add")
    public CommonResult<String> add(@RequestBody @Valid HrSubTransferAddParam hrSubTransferAddParam) {
        hrSubTransferService.add(hrSubTransferAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑HR-分公司-工资社保转入|转出总部
     *
     * @author yong.ye
     * @date  2024/01/18 09:41
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑HR-分公司-工资社保转入|转出总部")
    @CommonLog("编辑HR-分公司-工资社保转入|转出总部")
//    @SaCheckPermission("/sys/hr/sub/transfer/edit")
    @PostMapping("/sys/hr/sub/transfer/edit")
    public CommonResult<String> edit(@RequestBody @Valid HrSubTransferEditParam hrSubTransferEditParam) {
        hrSubTransferService.edit(hrSubTransferEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除HR-分公司-工资社保转入|转出总部
     *
     * @author yong.ye
     * @date  2024/01/18 09:41
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除HR-分公司-工资社保转入|转出总部")
    @CommonLog("删除HR-分公司-工资社保转入|转出总部")
//    @SaCheckPermission("/sys/hr/sub/transfer/delete")
    @PostMapping("/sys/hr/sub/transfer/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HrSubTransferIdParam> hrSubTransferIdParamList) {
        hrSubTransferService.delete(hrSubTransferIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取HR-分公司-工资社保转入|转出总部详情
     *
     * @author yong.ye
     * @date  2024/01/18 09:41
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取HR-分公司-工资社保转入|转出总部详情")
//    @SaCheckPermission("/sys/hr/sub/transfer/detail")
    @GetMapping("/sys/hr/sub/transfer/detail")
    public CommonResult<HrSubTransfer> detail(@Valid HrSubTransferIdParam hrSubTransferIdParam) {
        return CommonResult.data(hrSubTransferService.detail(hrSubTransferIdParam));
    }
}
