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
package vip.xiaonuo.biz.modular.crm.hetong.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.crm.hetong.param.*;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongInfoVo;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

/**
 * 业务表-CRM-合同控制器
 *
 * @author yong.ye
 * @date  2023/06/30 14:49
 */
@Api(tags = "业务表-CRM-合同控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class HetongController {

    @Resource
    private HetongService hetongService;

    /**
     * 获取业务表-CRM-合同分页
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-CRM-合同分页  【api/crm/contract/GetHetongPage】")
//    @SaCheckPermission("/biz/hetong/page")
    @GetMapping("/biz/crm/hetong/page")
    public CommonResult<IPage<HetongDetailVo>> page(HetongPageParam hetongPageParam) {
        return CommonResult.data(hetongService.page(hetongPageParam));
    }

    /**
     * 添加业务表-CRM-合同
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-CRM-合同  【api/crm/contract/AddHetong】")
    @CommonLog("添加业务表-CRM-合同")
//    @SaCheckPermission("/biz/hetong/add")
    @PostMapping("/biz/crm/hetong/add")
    public CommonResult<String> add(@RequestBody @Valid HetongAddParam hetongAddParam) {
        hetongService.add(hetongAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-CRM-合同
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-CRM-合同  【api/crm/contract/UpdateHetong】")
    @CommonLog("编辑业务表-CRM-合同")
//    @SaCheckPermission("/biz/hetong/edit")
    @PostMapping("/biz/crm/hetong/edit")
    public CommonResult<String> edit(@RequestBody @Valid HetongEditParam hetongEditParam) {
        hetongService.edit(hetongEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-CRM-合同
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-CRM-合同  【api/crm/contract/DeleteHetong】")
    @CommonLog("删除业务表-CRM-合同")
//    @SaCheckPermission("/biz/hetong/delete")
    @PostMapping("/biz/crm/hetong/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<HetongIdParam> hetongIdParamList) {
        hetongService.delete(hetongIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-CRM-合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-CRM-合同详情  【api/crm/contract/GetHetong】")
//    @SaCheckPermission("/biz/hetong/detail")
    @GetMapping("/biz/crm/hetong/detail")
    public CommonResult<HetongDetailVo> detail(@Valid HetongIdParam hetongIdParam) {
        return CommonResult.data(hetongService.detail(hetongIdParam));
    }

    /**
     * 获取业务表-CRM-合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取业务表-CRM-合同列表  【注意：不建议使用，数据量过大】")
//    @SaCheckPermission("/biz/hetong/detail")
    @GetMapping("/biz/crm/hetong/listAll")
    public CommonResult<List<HetongInfoVo>> listAll() {
        return CommonResult.data(hetongService.getListAll());
    }


    /**
     * 判断合同名称是否重名 （审批中+审核通过状态下）
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("判断合同名称是否重名 （审批中+审核通过状态下）")
    @GetMapping("/biz/crm/hetong/checkName")
    public CommonResult<Boolean> checkName(@Valid CheckNameParam checkNameParam) {
        return CommonResult.data(hetongService.checkName(checkNameParam));
    }



    /**
     * 获取业务表-CRM-合同分页【含权限设置 -- 由于之前列表接口有许多应用，比如：立项、产值填报等】
     *
     * @author yong.ye
     * @date  2023/11/08 14:49
     */
    @ApiOperationSupport(order = 10)
    @ApiOperation("获取业务表-CRM-合同分页【含权限设置 -- 由于之前列表接口有许多应用，比如：立项、产值填报等】")
    @SaCheckPermission("/biz/crm/hetong/limitPage")
    @GetMapping("/biz/crm/hetong/limitPage")
    public CommonResult<IPage<HetongVo>> limitPage(HetongPageParam hetongPageParam) {
        return CommonResult.data(hetongService.limitPage(hetongPageParam));
    }




    /**
     * 根据考核日期范围，统计某分公司已签合同总额
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    @ApiOperationSupport(order = 11)
    @ApiOperation("根据考核日期范围，统计某分公司已签合同总额")
    @GetMapping("/biz/crm/hetong/getHtAmount")
    public CommonResult<BigDecimal> getHtAmount(@Valid HtParam htParam) {
        return CommonResult.data(hetongService.getHtAmount(htParam));
    }

}
