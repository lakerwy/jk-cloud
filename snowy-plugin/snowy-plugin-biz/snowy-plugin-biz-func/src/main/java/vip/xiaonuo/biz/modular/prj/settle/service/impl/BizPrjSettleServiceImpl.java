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
package vip.xiaonuo.biz.modular.prj.settle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettleInvoice;
import vip.xiaonuo.biz.modular.prj.settle.param.*;
import vip.xiaonuo.biz.modular.prj.settle.service.BizPrjSettleInvoiceService;
import vip.xiaonuo.biz.modular.prj.settle.vo.BizPrjSettleVo;
import vip.xiaonuo.biz.modular.prj.settle.vo.SettleInvoiceVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettle;
import vip.xiaonuo.biz.modular.prj.settle.mapper.BizPrjSettleMapper;
import vip.xiaonuo.biz.modular.prj.settle.service.BizPrjSettleService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-项目款结算单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/28 11:36
 **/

@DS("slave_1")
@Service
public class BizPrjSettleServiceImpl extends ServiceImpl<BizPrjSettleMapper, BizPrjSettle> implements BizPrjSettleService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private BizPrjSettleInvoiceService bizPrjSettleInvoiceService;
    @Resource
    private BizPrjSettleMapper bizPrjSettleMapper;


    @Override
    public Page<BizPrjSettleVo> page(BizPrjSettlePageParam bizPrjSettlePageParam) {
        QueryWrapper<BizPrjSettle> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getHzfCode())) {
            queryWrapper.lambda().like(BizPrjSettle::getHzfCode, bizPrjSettlePageParam.getHzfCode());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getHzfName())) {
            queryWrapper.lambda().like(BizPrjSettle::getHzfName, bizPrjSettlePageParam.getHzfName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getStartApplyDate()) && ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getEndApplyDate())) {
            queryWrapper.lambda().between(BizPrjSettle::getApplyDate, bizPrjSettlePageParam.getStartApplyDate(), bizPrjSettlePageParam.getEndApplyDate());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getSettleNo())) {
            queryWrapper.lambda().like(BizPrjSettle::getSettleNo, bizPrjSettlePageParam.getSettleNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getXyContractNo())) {
            queryWrapper.lambda().eq(BizPrjSettle::getXyContractNo, bizPrjSettlePageParam.getXyContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getXyContractName())) {
            queryWrapper.lambda().like(BizPrjSettle::getXyContractName, bizPrjSettlePageParam.getXyContractName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getZbContractNo())) {
            queryWrapper.lambda().eq(BizPrjSettle::getZbContractNo, bizPrjSettlePageParam.getZbContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getZbContractName())) {
            queryWrapper.lambda().like(BizPrjSettle::getZbContractName, bizPrjSettlePageParam.getZbContractName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getSn())) {
            queryWrapper.lambda().eq(BizPrjSettle::getSn, bizPrjSettlePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getStatus())) {
            queryWrapper.lambda().eq(BizPrjSettle::getStatus, bizPrjSettlePageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizPrjSettlePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizPrjSettle::getSettleNo,bizPrjSettlePageParam.getSearchKey())
                    .or().like(BizPrjSettle::getSettleNo,bizPrjSettlePageParam.getSearchKey())
                    .or().like(BizPrjSettle::getHzfCode,bizPrjSettlePageParam.getSearchKey())
                    .or().like(BizPrjSettle::getHzfName,bizPrjSettlePageParam.getSearchKey())
                    .or().like(BizPrjSettle::getXyContractNo,bizPrjSettlePageParam.getSearchKey())
                    .or().like(BizPrjSettle::getXyContractName,bizPrjSettlePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizPrjSettlePageParam.getSortField(), bizPrjSettlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjSettlePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjSettlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjSettlePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPrjSettle::getId);
        }
        Page<BizPrjSettleVo> result = bizPrjSettleMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        for (BizPrjSettleVo vo:result.getRecords()) {
            vo.setSettleInvoiceList(bizPrjSettleInvoiceService.list(new QueryWrapper<BizPrjSettleInvoice>().lambda().eq(BizPrjSettleInvoice::getSettleId,vo.getId())));
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjSettleParam prjSettleParam) {
        BizPrjSettle bizPrjSettle = BeanUtil.toBean(prjSettleParam, BizPrjSettle.class);
        //走审批流程
        String processInstanceId = prjSettleParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizPrjSettle.setId(id);
            bizPrjSettle.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizPrjSettle.setModelId(modelId);
            bizPrjSettle.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            bizPrjSettle.setSettleNo(bizPrjSettle.getSn());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save(modelId, BizCommonEnum.BIZ_PRJ_SETTLE.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizPrjSettle.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        bizPrjSettle.setApplyDate(DateTime.now());
        bizPrjSettle.setExtJson(JsonUtils.object2json(prjSettleParam));
        this.save(bizPrjSettle);
        //处理发票
        if(CollUtil.isNotEmpty(prjSettleParam.getSettleInvoiceList())){
            List<BizPrjSettleInvoice> prjSettleInvoiceList = prjSettleParam.getSettleInvoiceList();
            prjSettleInvoiceList.forEach(x->{
                x.setSettleId(bizPrjSettle.getId());
            });
            bizPrjSettleInvoiceService.saveBatch(prjSettleInvoiceList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjSettleParam prjSettleParam) {
        String id = prjSettleParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(prjSettleParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(prjSettleParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                prjSettleParam.setId(id);
            }
        }
        BizPrjSettle bizPrjSettle = this.queryEntity(prjSettleParam.getId());
        BeanUtil.copyProperties(prjSettleParam, bizPrjSettle);
        bizPrjSettle.setExtJson(JsonUtils.object2json(prjSettleParam));
        this.updateById(bizPrjSettle);
        //处理发票
        if(CollUtil.isNotEmpty(prjSettleParam.getSettleInvoiceList())){
            //本次传入的数据
            List<BizPrjSettleInvoice> prjSettleInvoiceList = prjSettleParam.getSettleInvoiceList();
            prjSettleInvoiceList.forEach(x->{
                x.setSettleId(bizPrjSettle.getId());
            });
            //获取历史数据
            List<BizPrjSettleInvoice> oldSettleInvoiceList = bizPrjSettleInvoiceService.list(
                    new QueryWrapper<BizPrjSettleInvoice>().lambda().eq(BizPrjSettleInvoice::getSettleId,bizPrjSettle.getId()));
            List<String> oldIds = oldSettleInvoiceList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizPrjSettleInvoice> addList = prjSettleInvoiceList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizPrjSettleInvoice> updateList = prjSettleInvoiceList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizPrjSettleInvoice> removeList = oldSettleInvoiceList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizPrjSettleInvoiceService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizPrjSettleInvoiceService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizPrjSettleInvoiceService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjSettleIdParam> bizPrjSettleIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(bizPrjSettleIdParamList, BizPrjSettleIdParam::getId);
        this.removeByIds(ids);
        //删除发票
        bizPrjSettleInvoiceService.remove(new QueryWrapper<BizPrjSettleInvoice>().lambda().in(BizPrjSettleInvoice::getSettleId,ids));
    }

    @Override
    public BizPrjSettle detail(BizPrjSettleIdParam bizPrjSettleIdParam) {
        return this.queryEntity(bizPrjSettleIdParam.getId());
    }

    @Override
    public BizPrjSettle queryEntity(String id) {
        BizPrjSettle bizPrjSettle = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjSettle)) {
            throw new CommonException("业务表-项目计划付款单不存在，id值为：{}", id);
        }
        return bizPrjSettle;
    }

    @Override
    public List<SettleInvoiceVo> invoiceList(InvoiceParam invoiceParam) {
        QueryWrapper<BizPrjSettle> queryWrapper = new QueryWrapper<>();
        //审核通过
        queryWrapper.eq("a.STATUS","1");
        //有效数据
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        //合同编号
        if(ObjectUtil.isNotEmpty(invoiceParam.getContractNo())){
            queryWrapper.eq("a.ZB_CONTRACT_NO",invoiceParam.getContractNo());
        }
        queryWrapper.orderByAsc("a.SETTLE_NO").orderByAsc("b.CREATE_TIME");
        return bizPrjSettleMapper.getInvoiceList(queryWrapper);
    }

    @Override
    public List<BizPrjSettle> settleList(InvoiceParam invoiceParam) {
        QueryWrapper<BizPrjSettle> queryWrapper = new QueryWrapper<>();
        //审核通过
        queryWrapper.lambda().eq(BizPrjSettle::getStatus,"1");
        //有效数据
        queryWrapper.lambda().eq(BizPrjSettle::getDeleteFlag,"NOT_DELETE");
        //合同编号
        if(ObjectUtil.isNotEmpty(invoiceParam.getContractNo())){
            queryWrapper.lambda().eq(BizPrjSettle::getZbContractNo,invoiceParam.getContractNo());
        }
        queryWrapper.lambda().orderByAsc(BizPrjSettle::getSettleNo);
        return this.list(queryWrapper);
    }
}
