package vip.xiaonuo.biz.modular.prj.expense.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpCost;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpItem;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpCostService;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpItemService;
import vip.xiaonuo.biz.modular.prj.expense.vo.BizExpApplyVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpApply;
import vip.xiaonuo.biz.modular.prj.expense.mapper.BizExpApplyMapper;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpApplyAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpApplyEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpApplyIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpApplyPageParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpApplyService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 **/

@DS("slave_1")
@Service
public class BizExpApplyServiceImpl extends ServiceImpl<BizExpApplyMapper, BizExpApply> implements BizExpApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private BizExpItemService bizExpItemService;
    @Resource
    private BizExpCostService bizExpCostService;
    @Override
    public Page<BizExpApplyVo> page(BizExpApplyPageParam bizExpApplyPageParam) {
        QueryWrapper<BizExpApply> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getExpType())) {
            queryWrapper.lambda().eq(BizExpApply::getExpType, bizExpApplyPageParam.getExpType());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getInitor())) {
            queryWrapper.lambda().like(BizExpApply::getInitor, bizExpApplyPageParam.getInitor());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getExptor())) {
            queryWrapper.lambda().like(BizExpApply::getExptor, bizExpApplyPageParam.getExptor());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getFinCode())) {
            queryWrapper.lambda().like(BizExpApply::getFinCode, bizExpApplyPageParam.getFinCode());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getUnit())) {
            queryWrapper.lambda().like(BizExpApply::getUnit, bizExpApplyPageParam.getUnit());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getDept())) {
            queryWrapper.lambda().like(BizExpApply::getDept, bizExpApplyPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getStartExpDate()) && ObjectUtil.isNotEmpty(bizExpApplyPageParam.getEndExpDate())) {
            queryWrapper.lambda().between(BizExpApply::getExpDate, bizExpApplyPageParam.getStartExpDate(), bizExpApplyPageParam.getEndExpDate());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getSn())) {
            queryWrapper.lambda().like(BizExpApply::getSn, bizExpApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizExpApply::getStatus, bizExpApplyPageParam.getStatus());
        }
//        // 校验数据范围
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.lambda().in(BizExpApply::getDataScopeId, loginUserDataScope);
//        } else {
//            queryWrapper.lambda().eq(BizExpApply::getCreateUser, StpUtil.getLoginIdAsString());
//        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizExpApply::getSn,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getInitor,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getFinCode,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getDept,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getUnit,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getExptor,bizExpApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizExpApplyPageParam.getSortField(), bizExpApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizExpApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizExpApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizExpApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizExpApply::getCreateTime);
        }
        Page<BizExpApply> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<BizExpApplyVo> recordNew = BeanUtil.copyToList(result.getRecords(),BizExpApplyVo.class);
        if(CollUtil.isNotEmpty(recordNew)){
            recordNew.forEach(item->{
                item.setItemList(bizExpItemService.list(new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,item.getId())));
                item.setCostList(bizExpCostService.list(new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,item.getId())));
            });
        }
        Page<BizExpApplyVo> resultVo = new Page<>();
        resultVo.setRecords(recordNew);
        resultVo.setPages(result.getPages());
        resultVo.setTotal(result.getTotal());
        resultVo.setSize(result.getSize());
        resultVo.setCurrent(result.getCurrent());
        return resultVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizExpApplyAddParam bizExpApplyAddParam) {
        BizExpApply bizExpApply = BeanUtil.toBean(bizExpApplyAddParam, BizExpApply.class);

        //走审批流程
        String processInstanceId = bizExpApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizExpApply.setId(id);
            bizExpApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizExpApply.setModelId(modelId);
            bizExpApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_EXP_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizExpApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizExpApply);
        //处理费用明细
        if(CollUtil.isNotEmpty(bizExpApplyAddParam.getItemList())){
            List<BizExpItem> bizExpItemList = bizExpApplyAddParam.getItemList();
            bizExpItemList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            bizExpItemService.saveBatch(bizExpItemList);
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizExpApplyAddParam.getCostList())){
            List<BizExpCost> bizExpCostList = bizExpApplyAddParam.getCostList();
            bizExpCostList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            bizExpCostService.saveBatch(bizExpCostList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizExpApplyEditParam bizExpApplyEditParam) {
        String id = bizExpApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizExpApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizExpApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizExpApplyEditParam.setId(id);
            }
        }
        BizExpApply bizExpApply = this.queryEntity(bizExpApplyEditParam.getId());
        BeanUtil.copyProperties(bizExpApplyEditParam, bizExpApply);
        this.updateById(bizExpApply);
        //处理费用明细
        if(CollUtil.isNotEmpty(bizExpApplyEditParam.getItemList())){
            //本次传入的数据
            List<BizExpItem> bizExpItemList = bizExpApplyEditParam.getItemList();
            bizExpItemList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            //获取历史数据
            List<BizExpItem> oldList = bizExpItemService.list(
                    new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,bizExpApply.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizExpItem> addList = bizExpItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizExpItem> updateList = bizExpItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizExpItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizExpItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizExpItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizExpItemService.removeByIds(removeList);
            }
        }else{
            bizExpItemService.remove(new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,bizExpApply.getId()));
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizExpApplyEditParam.getCostList())){
            //本次传入的数据
            List<BizExpCost> bizExpCostList = bizExpApplyEditParam.getCostList();
            bizExpCostList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            //获取历史数据
            List<BizExpCost> oldList = bizExpCostService.list(
                    new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,bizExpApply.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizExpCost> addList = bizExpCostList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizExpCost> updateList = bizExpCostList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizExpCost> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizExpCostService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizExpCostService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizExpCostService.removeByIds(removeList);
            }
        }else{
            bizExpCostService.remove(new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,bizExpApply.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizExpApplyIdParam> bizExpApplyIdParamList) {
        // 待删除的id
        List<String> delIds = CollStreamUtil.toList(bizExpApplyIdParamList, BizExpApplyIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            bizExpItemService.remove(new QueryWrapper<BizExpItem>().lambda().in(BizExpItem::getBizExpApplyId,delIds));
            bizExpCostService.remove(new QueryWrapper<BizExpCost>().lambda().in(BizExpCost::getBizExpApplyId,delIds));
        }
    }

    @Override
    public BizExpApplyVo detail(BizExpApplyIdParam bizExpApplyIdParam) {
        BizExpApply bizExpApply = this.queryEntity(bizExpApplyIdParam.getId());
        BizExpApplyVo bizExpApplyVo = BeanUtil.copyProperties(bizExpApply,BizExpApplyVo.class);
        bizExpApplyVo.setItemList(bizExpItemService.list(new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,bizExpApplyVo.getId())));
        bizExpApplyVo.setCostList(bizExpCostService.list(new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,bizExpApplyVo.getId())));
        return bizExpApplyVo;
    }

    @Override
    public BizExpApply queryEntity(String id) {
        BizExpApply bizExpApply = this.getById(id);
        if(ObjectUtil.isEmpty(bizExpApply)) {
            throw new CommonException("财务管理 - 费用报销|差旅费用报销 - 申请流程不存在，id值为：{}", id);
        }
        return bizExpApply;
    }
}
