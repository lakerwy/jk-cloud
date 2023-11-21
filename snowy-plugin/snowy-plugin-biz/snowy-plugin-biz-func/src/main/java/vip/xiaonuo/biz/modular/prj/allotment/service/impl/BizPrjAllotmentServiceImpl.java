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
package vip.xiaonuo.biz.modular.prj.allotment.service.impl;

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
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.allotment.entity.BizPrjAllotmentCn;
import vip.xiaonuo.biz.modular.prj.allotment.service.BizPrjAllotmentCnService;
import vip.xiaonuo.biz.modular.prj.allotment.vo.BizPrjAllotmentVo;
import vip.xiaonuo.biz.modular.prj.plan.param.InvoiceParam;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.allotment.entity.BizPrjAllotment;
import vip.xiaonuo.biz.modular.prj.allotment.mapper.BizPrjAllotmentMapper;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentAddParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentEditParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentIdParam;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentPageParam;
import vip.xiaonuo.biz.modular.prj.allotment.service.BizPrjAllotmentService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-项目拨付款申请单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/28 11:30
 **/

@DS("slave_1")
@Service
public class BizPrjAllotmentServiceImpl extends ServiceImpl<BizPrjAllotmentMapper, BizPrjAllotment> implements BizPrjAllotmentService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizPrjAllotmentCnService bizPrjAllotmentCnService;

    @Override
    public Page<BizPrjAllotmentVo> page(BizPrjAllotmentPageParam bizPrjAllotmentPageParam) {
        QueryWrapper<BizPrjAllotment> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getHzfCode())) {
            queryWrapper.lambda().like(BizPrjAllotment::getHzfCode, bizPrjAllotmentPageParam.getHzfCode());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getHzfName())) {
            queryWrapper.lambda().like(BizPrjAllotment::getHzfName, bizPrjAllotmentPageParam.getHzfName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getStartApplyDate()) && ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getEndApplyDate())) {
            queryWrapper.lambda().between(BizPrjAllotment::getApplyDate, bizPrjAllotmentPageParam.getStartApplyDate(), bizPrjAllotmentPageParam.getEndApplyDate());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getAllotmentNo())) {
            queryWrapper.lambda().eq(BizPrjAllotment::getAllotmentNo, bizPrjAllotmentPageParam.getAllotmentNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getXmContractNo())) {
            queryWrapper.lambda().eq(BizPrjAllotment::getXmContractNo, bizPrjAllotmentPageParam.getXmContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getXmContractName())) {
            queryWrapper.lambda().like(BizPrjAllotment::getXmContractName, bizPrjAllotmentPageParam.getXmContractName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getSn())) {
            queryWrapper.lambda().eq(BizPrjAllotment::getSn, bizPrjAllotmentPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizPrjAllotment::getStatus, bizPrjAllotmentPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizPrjAllotmentPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizPrjAllotment::getHzfCode,bizPrjAllotmentPageParam.getSearchKey())
                    .or().like(BizPrjAllotment::getHzfName,bizPrjAllotmentPageParam.getSearchKey())
                    .or().like(BizPrjAllotment::getAllotmentNo,bizPrjAllotmentPageParam.getSearchKey())
                    .or().like(BizPrjAllotment::getXmContractNo,bizPrjAllotmentPageParam.getSearchKey())
                    .or().like(BizPrjAllotment::getXmContractName,bizPrjAllotmentPageParam.getSearchKey())
                    .or().like(BizPrjAllotment::getSettleNo,bizPrjAllotmentPageParam.getSearchKey())
                    .or().like(BizPrjAllotment::getPlanNo,bizPrjAllotmentPageParam.getSearchKey())
                    .or().like(BizPrjAllotment::getSn,bizPrjAllotmentPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizPrjAllotmentPageParam.getSortField(), bizPrjAllotmentPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjAllotmentPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjAllotmentPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjAllotmentPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPrjAllotment::getId);
        }
        Page<BizPrjAllotment> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        //处理出纳
        List<BizPrjAllotmentVo> voList = new ArrayList<>();
        for (BizPrjAllotment item:result.getRecords()) {
            BizPrjAllotmentVo vo = BeanUtil.copyProperties(item,BizPrjAllotmentVo.class);
            vo.setAllotmentCnList(bizPrjAllotmentCnService.list(new QueryWrapper<BizPrjAllotmentCn>().lambda().eq(BizPrjAllotmentCn::getAllotmentId,item.getId())));
        }
        Page<BizPrjAllotmentVo> resultVo = new Page<>();
        resultVo.setRecords(voList);
        resultVo.setPages(result.getPages());
        resultVo.setTotal(result.getTotal());
        resultVo.setSize(result.getSize());
        resultVo.setCurrent(result.getCurrent());
        return resultVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizPrjAllotmentAddParam bizPrjAllotmentAddParam) {
        BizPrjAllotment bizPrjAllotment = BeanUtil.toBean(bizPrjAllotmentAddParam, BizPrjAllotment.class);
        //走审批流程
        String processInstanceId = bizPrjAllotmentAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizPrjAllotment.setId(id);
            bizPrjAllotment.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizPrjAllotment.setModelId(modelId);
            bizPrjAllotment.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            bizPrjAllotment.setAllotmentNo(bizPrjAllotment.getSn());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save(modelId, BizCommonEnum.BIZ_PRJ_ALLOTMENT.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizPrjAllotment.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        bizPrjAllotment.setApplyDate(DateTime.now());
        bizPrjAllotment.setExtJson(JsonUtils.object2json(bizPrjAllotmentAddParam));
        this.save(bizPrjAllotment);
        //处理出纳
        if(CollUtil.isNotEmpty(bizPrjAllotmentAddParam.getAllotmentCnList())){
            List<BizPrjAllotmentCn> prjAllotmentCnList = bizPrjAllotmentAddParam.getAllotmentCnList();
            prjAllotmentCnList.forEach(x->{
                x.setAllotmentId(bizPrjAllotment.getId());
            });
            bizPrjAllotmentCnService.saveBatch(prjAllotmentCnList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizPrjAllotmentEditParam bizPrjAllotmentEditParam) {
        String id = bizPrjAllotmentEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizPrjAllotmentEditParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(bizPrjAllotmentEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizPrjAllotmentEditParam.setId(id);
            }
        }
        BizPrjAllotment bizPrjAllotment = this.queryEntity(bizPrjAllotmentEditParam.getId());
        BeanUtil.copyProperties(bizPrjAllotmentEditParam, bizPrjAllotment);
        bizPrjAllotment.setExtJson(JsonUtils.object2json(bizPrjAllotmentEditParam));
        this.updateById(bizPrjAllotment);
        //处理出纳
        if(CollUtil.isNotEmpty(bizPrjAllotmentEditParam.getAllotmentCnList())){
            //本次传入的数据
            List<BizPrjAllotmentCn> prjAllotmentCnList = bizPrjAllotmentEditParam.getAllotmentCnList();
            prjAllotmentCnList.forEach(x->{
                x.setAllotmentId(bizPrjAllotment.getId());
            });
            //获取历史数据
            List<BizPrjAllotmentCn> oldList = bizPrjAllotmentCnService.list(
                    new QueryWrapper<BizPrjAllotmentCn>().lambda().eq(BizPrjAllotmentCn::getAllotmentId,bizPrjAllotment.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizPrjAllotmentCn> addList = prjAllotmentCnList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizPrjAllotmentCn> updateList = prjAllotmentCnList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizPrjAllotmentCn> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizPrjAllotmentCnService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizPrjAllotmentCnService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizPrjAllotmentCnService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjAllotmentIdParam> bizPrjAllotmentIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(bizPrjAllotmentIdParamList, BizPrjAllotmentIdParam::getId);
        this.removeByIds(ids);
        //删除出纳
        bizPrjAllotmentCnService.remove(new QueryWrapper<BizPrjAllotmentCn>().lambda().in(BizPrjAllotmentCn::getAllotmentId,ids));
    }

    @Override
    public BizPrjAllotmentVo detail(BizPrjAllotmentIdParam bizPrjAllotmentIdParam) {
        BizPrjAllotment bizPrjAllotment = this.queryEntity(bizPrjAllotmentIdParam.getId());
        BizPrjAllotmentVo bizPrjAllotmentVo = BeanUtil.copyProperties(bizPrjAllotment,BizPrjAllotmentVo.class);
        bizPrjAllotmentVo.setAllotmentCnList(bizPrjAllotmentCnService.list(new QueryWrapper<BizPrjAllotmentCn>().lambda().eq(BizPrjAllotmentCn::getAllotmentId,bizPrjAllotmentVo.getId())));
        return bizPrjAllotmentVo;
    }

    @Override
    public BizPrjAllotment queryEntity(String id) {
        BizPrjAllotment bizPrjAllotment = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjAllotment)) {
            throw new CommonException("业务表-项目计划付款单不存在，id值为：{}", id);
        }
        return bizPrjAllotment;
    }

    @Override
    public List<BizPrjAllotmentVo> allotmentList(InvoiceParam invoiceParam) {
        QueryWrapper<BizPrjAllotment> queryWrapper = new QueryWrapper<>();
        //审核通过
        queryWrapper.lambda().eq(BizPrjAllotment::getStatus,"1");
        //有效数据
        queryWrapper.lambda().eq(BizPrjAllotment::getDeleteFlag,"NOT_DELETE");
        //合同编号
        if(ObjectUtil.isNotEmpty(invoiceParam.getContractNo())){
            queryWrapper.lambda().eq(BizPrjAllotment::getXmContractNo,invoiceParam.getContractNo());
        }
        queryWrapper.lambda().orderByAsc(BizPrjAllotment::getAllotmentNo);
        List<BizPrjAllotment> result = this.list(queryWrapper);
        List<BizPrjAllotmentVo> resultVo = BeanUtil.copyToList(result,BizPrjAllotmentVo.class);
        resultVo.forEach(x->x.setAllotmentCnList(bizPrjAllotmentCnService.list(new QueryWrapper<BizPrjAllotmentCn>().lambda().eq(BizPrjAllotmentCn::getAllotmentId,x.getId()))));
        return resultVo;
    }
}
