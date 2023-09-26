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
package vip.xiaonuo.biz.modular.epc.safety.controller;

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
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcPersonnelSpecial;
import vip.xiaonuo.biz.modular.epc.safety.param.EpcPersonnelSpecialAddParam;
import vip.xiaonuo.biz.modular.epc.safety.param.EpcPersonnelSpecialEditParam;
import vip.xiaonuo.biz.modular.epc.safety.param.EpcPersonnelSpecialIdParam;
import vip.xiaonuo.biz.modular.epc.safety.param.EpcPersonnelSpecialPageParam;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcPersonnelSpecialService;
import vip.xiaonuo.biz.modular.epc.safety.vo.EpcPersonnelSpecialVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * EPC-特种作业人员管理控制器
 *
 * @author yong.ye
 * @date  2023/09/05 14:54
 */
@Api(tags = "EPC-特种作业人员管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EpcPersonnelSpecialController {

    @Resource
    private EpcPersonnelSpecialService epcPersonnelSpecialService;

    /**
     * 获取EPC-特种作业人员管理分页
     *
     * @author yong.ye
     * @date  2023/09/05 14:54
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取EPC-特种作业人员管理分页")
//    @SaCheckPermission("/biz/epc/special/page")
    @GetMapping("/biz/epc/special/page")
    public CommonResult<IPage<EpcPersonnelSpecialVo>> page(EpcPersonnelSpecialPageParam epcPersonnelSpecialPageParam) {
        return CommonResult.data(epcPersonnelSpecialService.page(epcPersonnelSpecialPageParam));
    }

    /**
     * 添加EPC-特种作业人员管理
     *
     * @author yong.ye
     * @date  2023/09/05 14:54
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加EPC-特种作业人员管理")
    @CommonLog("添加EPC-特种作业人员管理")
//    @SaCheckPermission("/biz/epc/special/add")
    @PostMapping("/biz/epc/special/add")
    public CommonResult<String> add(@RequestBody @Valid EpcPersonnelSpecialAddParam epcPersonnelSpecialAddParam) {
        epcPersonnelSpecialService.add(epcPersonnelSpecialAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑EPC-特种作业人员管理
     *
     * @author yong.ye
     * @date  2023/09/05 14:54
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑EPC-特种作业人员管理")
    @CommonLog("编辑EPC-特种作业人员管理")
//    @SaCheckPermission("/biz/epc/special/edit")
    @PostMapping("/biz/epc/special/edit")
    public CommonResult<String> edit(@RequestBody @Valid EpcPersonnelSpecialEditParam epcPersonnelSpecialEditParam) {
        epcPersonnelSpecialService.edit(epcPersonnelSpecialEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除EPC-特种作业人员管理
     *
     * @author yong.ye
     * @date  2023/09/05 14:54
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除EPC-特种作业人员管理")
    @CommonLog("删除EPC-特种作业人员管理")
//    @SaCheckPermission("/biz/epc/special/delete")
    @PostMapping("/biz/epc/special/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EpcPersonnelSpecialIdParam> epcPersonnelSpecialIdParamList) {
        epcPersonnelSpecialService.delete(epcPersonnelSpecialIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取EPC-特种作业人员管理详情
     *
     * @author yong.ye
     * @date  2023/09/05 14:54
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取EPC-特种作业人员管理详情")
//    @SaCheckPermission("/biz/epc/special/detail")
    @GetMapping("/biz/epc/special/detail")
    public CommonResult<EpcPersonnelSpecial> detail(@Valid EpcPersonnelSpecialIdParam epcPersonnelSpecialIdParam) {
        return CommonResult.data(epcPersonnelSpecialService.detail(epcPersonnelSpecialIdParam));
    }
}
