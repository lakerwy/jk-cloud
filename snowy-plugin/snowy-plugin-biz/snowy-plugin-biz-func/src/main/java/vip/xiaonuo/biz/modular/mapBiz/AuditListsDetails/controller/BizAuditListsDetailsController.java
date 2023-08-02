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
package vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.entity.BizAuditLists;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.service.BizAuditListsService;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.service.impl.BizAuditListsServiceImpl;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsIdParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsPageParam;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.entity.BizAuditListsDetails;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsAddParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.param.BizAuditListsDetailsEditParam;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.service.BizAuditListsDetailsService;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 业务表-设计校审记录表控制器
 *
 * @author mayike
 * @date  2023/07/03 11:33
 */
@Api(tags = "业务表-设计校审记录表控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizAuditListsDetailsController {

    @Resource
    private BizAuditListsDetailsService bizAuditListsDetailsService;

    @Autowired
    IDingDingService iDingDingService;

    @Resource
    BizAuditListsService bizAuditListsService;

    /**
     * 获取业务表-设计校审记录表分页
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取业务表-设计校审记录表分页")
//    @SaCheckPermission("/biz/AuditListsDetails/page")
    @GetMapping("/biz/AuditListsDetails/page")
    public CommonResult<Page<BizAuditListsDetails>> page(BizAuditListsDetailsPageParam bizAuditListsDetailsPageParam) {
        return CommonResult.data(bizAuditListsDetailsService.page(bizAuditListsDetailsPageParam));
    }

    /**
     * 添加业务表-设计校审记录表
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加业务表-设计校审记录表")
    @CommonLog("添加业务表-设计校审记录表")
//    @SaCheckPermission("/biz/AuditListsDetails/add")
    @PostMapping("/biz/AuditListsDetails/add")
    public CommonResult<String> add(@RequestBody @Valid BizAuditListsDetailsAddParam bizAuditListsDetailsAddParam) {
        bizAuditListsDetailsService.add(bizAuditListsDetailsAddParam);
        try {
            BizAuditLists auditLists = bizAuditListsService.getById(bizAuditListsDetailsAddParam.getAuditId());
            JSONObject entries = JSONUtil.parseObj(auditLists);
            List<String> ids = new ArrayList<>();
            ids.add(auditLists.getCreateUser());
            iDingDingService.batchSend(entries,ids);
        }catch (Exception e){
        }
        return CommonResult.ok();
    }

    /**
     * 编辑业务表-设计校审记录表
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑业务表-设计校审记录表")
    @CommonLog("编辑业务表-设计校审记录表")
//    @SaCheckPermission("/biz/AuditListsDetails/edit")
    @PostMapping("/biz/AuditListsDetails/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizAuditListsDetailsEditParam bizAuditListsDetailsEditParam) {
        bizAuditListsDetailsService.edit(bizAuditListsDetailsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除业务表-设计校审记录表
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除业务表-设计校审记录表")
    @CommonLog("删除业务表-设计校审记录表")
//    @SaCheckPermission("/biz/AuditListsDetails/delete")
    @PostMapping("/biz/AuditListsDetails/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizAuditListsDetailsIdParam> bizAuditListsDetailsIdParamList) {
        bizAuditListsDetailsService.delete(bizAuditListsDetailsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取业务表-设计校审记录表详情
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取业务表-设计校审记录表详情")
//    @SaCheckPermission("/biz/AuditListsDetails/detail")
    @GetMapping("/biz/AuditListsDetails/detail")
    public CommonResult<BizAuditListsDetails> detail(@Valid BizAuditListsDetailsIdParam bizAuditListsDetailsIdParam) {
        return CommonResult.data(bizAuditListsDetailsService.detail(bizAuditListsDetailsIdParam));
    }


    /**
     * 编辑业务表-设计校审记录表
     *
     * @author mayike
     * @date  2023/07/03 11:33
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("编辑业务表-设计校审记录表无token")
    @CommonLog("编辑业务表-设计校审记录表-无token")
//    @SaCheckPermission("/biz/AuditListsDetails/edit")
    @PostMapping("/biz/AuditListsDetails/editNoToken")
    public CommonResult<String> editNoToken(@RequestBody @Valid BizAuditListsDetailsEditParam bizAuditListsDetailsEditParam) {
        bizAuditListsDetailsService.edit(bizAuditListsDetailsEditParam);
        try {
            BizAuditLists auditLists = bizAuditListsService.getById(bizAuditListsDetailsEditParam.getAuditId());
            JSONObject entries = JSONUtil.parseObj(auditLists);
            List<String> ids = new ArrayList<>();
            ids.add(auditLists.getCreateUser());
            //ids.add(auditLists.getReader());
            if(ObjectUtil.isNotEmpty(bizAuditListsDetailsEditParam.getCondition())) {
                iDingDingService.batchSend(entries, ids);
            }
        }catch (Exception e){
            //消息发送失败
        }
        return CommonResult.ok();
    }
}
