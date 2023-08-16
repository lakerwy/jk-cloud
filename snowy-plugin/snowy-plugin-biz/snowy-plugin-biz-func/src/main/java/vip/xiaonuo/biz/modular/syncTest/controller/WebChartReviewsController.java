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
package vip.xiaonuo.biz.modular.syncTest.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.entity.BizAuditLists;
import vip.xiaonuo.biz.modular.mapBiz.AuditLists.service.BizAuditListsService;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.entity.BizAuditListsDetails;
import vip.xiaonuo.biz.modular.mapBiz.AuditListsDetails.service.BizAuditListsDetailsService;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity.BizChartReviewDetails;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity.BizChartReviews;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.service.BizChartReviewDetailsService;
import vip.xiaonuo.biz.modular.mapBiz.Chartreviews.service.BizChartReviewsService;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.entity.BizMapRegister;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.service.BizMapRegisterService;
import vip.xiaonuo.biz.modular.syncTest.entity.*;
import vip.xiaonuo.biz.modular.syncTest.service.*;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-图审受理单控制器
 *
 * @author mayike
 * @date  2023/08/16 09:38
 */
@Api(tags = "业务表-图审受理单控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
@Slf4j
public class WebChartReviewsController {
    @Resource
    WebChartReviewsService webChartReviewsService;

    @Resource
    WebAuditListsService webAuditListsService;

    @Resource
    WebChartReviewDetailsService webChartReviewDetailsService;

    @Resource
    WebMapRegisterService webMapRegisterService;

    @Resource
    WebAuditListsDetailsService webAuditListsDetailsService;


    @Resource
    BizChartReviewsService bizChartReviewsService;

    @Resource
    BizChartReviewDetailsService bizChartReviewDetailsService;

    @Resource
    BizAuditListsService bizAuditListsService;

    @Resource
    BizMapRegisterService bizMapRegisterService;


    @Resource
    BizAuditListsDetailsService bizAuditListsDetailsService;



    /**
     * 获取业务表-图审受理单详情
     *
     * @author mayike
     * @date  2023/08/16 09:38
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("图审相关信息同步")
    @GetMapping("/biz/syncTest/syncChartView")
    public CommonResult<String> syncChartView() {
        //先执行 UPDATE BIZ_AUDIT_LISTS_DETAILS  a  JOIN `jk-cloud-test`.WEB_AUDIT_LISTS_DETAILS b  ON a.TASK_ID = b.TASK_ID AND a.ORDER_INDEX = b.ORDER_INDEX SET a.ID = b.ID ;
        List<WebChartReviews> chartReviewsList = webChartReviewsService.list(new LambdaQueryWrapper<WebChartReviews>().eq(WebChartReviews::getDeleteFlag,"NOT_DELETE"))
                .stream().filter(webChartReviews -> webChartReviews.getDeleteFlag().equals("NOT_DELETE")).collect(Collectors.toList());
        log.info("chartReviewsList更新条数-----"+chartReviewsList.size());
        List<BizChartReviews> bizChartReviews = BeanUtil.copyToList(chartReviewsList, BizChartReviews.class);
        //bizChartReviewsService.delete();
        bizChartReviewsService.saveOrUpdateBatch(bizChartReviews);

        List<WebChartReviewDetails> chartReviewDetails = webChartReviewDetailsService.list(new LambdaQueryWrapper<WebChartReviewDetails>().eq(WebChartReviewDetails::getDeleteFlag,"NOT_DELETE"))
                .stream().filter(webChartReviewDetails -> webChartReviewDetails.getDeleteFlag().equals("NOT_DELETE")).collect(Collectors.toList());
        log.info("chartReviewDetails更新条数-----"+chartReviewDetails.size());
        List<BizChartReviewDetails> bizChartReviewDetails = BeanUtil.copyToList(chartReviewDetails, BizChartReviewDetails.class);
        bizChartReviewDetailsService.saveOrUpdateBatch(bizChartReviewDetails);

        List<WebAuditLists> auditLists = webAuditListsService.list().stream().filter(bean -> bean.getDeleteFlag().equals("NOT_DELETE")).collect(Collectors.toList())
                .stream().filter(bean -> bean.getDeleteFlag().equals("NOT_DELETE")).collect(Collectors.toList());
        log.info("auditLists更新条数-----"+auditLists.size());
        List<BizAuditLists> bizAuditLists = BeanUtil.copyToList(auditLists, BizAuditLists.class);
        bizAuditListsService.saveOrUpdateBatch(bizAuditLists);


        List<WebAuditListsDetails> auditListsDetails = webAuditListsDetailsService.list(new LambdaQueryWrapper<WebAuditListsDetails>().eq(WebAuditListsDetails::getDeleteFlag,"NOT_DELETE"))
                .stream().filter(bean -> bean.getDeleteFlag().equals("NOT_DELETE")).collect(Collectors.toList());
        log.info("auditListsDetails更新条数----"+auditListsDetails.size());
        List<BizAuditListsDetails> bizAuditListsDetails = BeanUtil.copyToList(auditListsDetails, BizAuditListsDetails.class);
        bizAuditListsDetailsService.saveOrUpdateBatch(bizAuditListsDetails);

        List<WebMapRegister> mapRegisters = webMapRegisterService.list(new LambdaQueryWrapper<WebMapRegister>().eq(WebMapRegister::getDeleteFlag,"NOT_DELETE"))
                        .stream().filter(bean -> bean.getDeleteFlag().equals("NOT_DELETE")).collect(Collectors.toList());
        log.info("mapRegisters更新条数----"+mapRegisters.size());
        List<BizMapRegister> bizMapRegisters = BeanUtil.copyToList(mapRegisters, BizMapRegister.class);
        bizMapRegisterService.saveOrUpdateBatch(bizMapRegisters);
        return CommonResult.ok();
    }
}
