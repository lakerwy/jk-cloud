package vip.xiaonuo.biz.modular.prjFee.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotmentItem;
import vip.xiaonuo.biz.modular.prjFee.service.PrjFeeAllotmentItemService;
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeAllotmentItemVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotment;
import vip.xiaonuo.biz.modular.prjFee.mapper.PrjFeeAllotmentMapper;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentAddParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentEditParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentIdParam;
import vip.xiaonuo.biz.modular.prjFee.param.PrjFeeAllotmentPageParam;
import vip.xiaonuo.biz.modular.prjFee.service.PrjFeeAllotmentService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工程款拨付审批Service接口实现类
 *
 * @author pzh
 * @date  2025/01/15 09:22
 **/
@Service
public class PrjFeeAllotmentServiceImpl extends ServiceImpl<PrjFeeAllotmentMapper, PrjFeeAllotment> implements PrjFeeAllotmentService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    FlwCommonApi flwCommonApi;

    @Resource
    PrjFeeAllotmentItemService prjFeeAllotmentItemService;

    @Override
    public Page<PrjFeeAllotment> page(PrjFeeAllotmentPageParam prjFeeAllotmentPageParam) {
        QueryWrapper<PrjFeeAllotment> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(PrjFeeAllotment::getSn,prjFeeAllotmentPageParam.getSearchKey())
                    .or().like(PrjFeeAllotment::getProjectName,prjFeeAllotmentPageParam.getSearchKey())
                    .or().like(PrjFeeAllotment::getCompanyOrDepartment,prjFeeAllotmentPageParam.getSearchKey())
                    .or().like(PrjFeeAllotment::getCounterparty,prjFeeAllotmentPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getFeeType())){
            queryWrapper.lambda().like(PrjFeeAllotment::getFeeType,prjFeeAllotmentPageParam.getFeeType());
        }
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getIsMap())){
            queryWrapper.lambda().like(PrjFeeAllotment::getIsMap,prjFeeAllotmentPageParam.getIsMap());
        }
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getFeeDate())){
            LocalDate date = LocalDate.parse(prjFeeAllotmentPageParam.getFeeDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            queryWrapper.lambda().between(PrjFeeAllotment::getFeeDate, startOfDay, endOfDay);
        }
        if(ObjectUtil.isAllNotEmpty(prjFeeAllotmentPageParam.getSortField(), prjFeeAllotmentPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(prjFeeAllotmentPageParam.getSortOrder());
            queryWrapper.orderBy(true, prjFeeAllotmentPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(prjFeeAllotmentPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PrjFeeAllotment::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjFeeAllotmentAddParam prjFeeAllotmentAddParam) {
        PrjFeeAllotment prjFeeAllotment = BeanUtil.toBean(prjFeeAllotmentAddParam, PrjFeeAllotment.class);
        // 走审批流程
        String processInstanceId = prjFeeAllotmentAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            prjFeeAllotment.setId(id);
            prjFeeAllotment.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            prjFeeAllotment.setModelId(modelId);
            prjFeeAllotment.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_PRJ_FEE_ALLOTMENT.getBizTable(),id,processInstanceId,dbName);
        }else{
            prjFeeAllotment.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(prjFeeAllotment);
        // 处理款项拨付
        if(CollUtil.isNotEmpty(prjFeeAllotmentAddParam.getFeeItemList())){
            List<PrjFeeAllotmentItem> feeItemList = prjFeeAllotmentAddParam.getFeeItemList();
            feeItemList.forEach(x->{
                x.setPrjFeeAllotmentId(prjFeeAllotment.getId());
            });
            prjFeeAllotmentItemService.saveBatch(feeItemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjFeeAllotmentEditParam prjFeeAllotmentEditParam) {
        PrjFeeAllotment prjFeeAllotment = this.queryEntity(prjFeeAllotmentEditParam.getId());
        String id = prjFeeAllotmentEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(prjFeeAllotmentEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(prjFeeAllotmentEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                prjFeeAllotmentEditParam.setId(id);
            }
        }
        BeanUtil.copyProperties(prjFeeAllotmentEditParam, prjFeeAllotment);
        this.updateById(prjFeeAllotment);
        // 处理款项拨付
        if(CollUtil.isNotEmpty(prjFeeAllotmentEditParam.getFeeItemList())){
            //本次传入的数据
            List<PrjFeeAllotmentItem> feeItemList = prjFeeAllotmentEditParam.getFeeItemList();
            feeItemList.forEach(x->{
                x.setPrjFeeAllotmentId(prjFeeAllotment.getId());
            });
            //获取历史数据
            List<PrjFeeAllotmentItem> oldList = prjFeeAllotmentItemService.list(
                    new QueryWrapper<PrjFeeAllotmentItem>().lambda().eq(PrjFeeAllotmentItem::getPrjFeeAllotmentId,prjFeeAllotment.getId()));
            List<String> oldIds = oldList.stream().map(PrjFeeAllotmentItem::getId).collect(Collectors.toList());
            //待新增的
            List<PrjFeeAllotmentItem> addList = feeItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<PrjFeeAllotmentItem> updateList = feeItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(PrjFeeAllotmentItem::getId).collect(Collectors.toList());
            List<PrjFeeAllotmentItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                prjFeeAllotmentItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                prjFeeAllotmentItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                prjFeeAllotmentItemService.removeByIds(removeList);
            }
        }else{
            prjFeeAllotmentItemService.remove(new QueryWrapper<PrjFeeAllotmentItem>().lambda().eq(PrjFeeAllotmentItem::getPrjFeeAllotmentId,prjFeeAllotment.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PrjFeeAllotmentIdParam> prjFeeAllotmentIdParamList) {
        // 待删除的id
        List<String> delIds = CollStreamUtil.toList(prjFeeAllotmentIdParamList, PrjFeeAllotmentIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        // 删除款项拨付
        if(CollUtil.isNotEmpty(delIds)){
            prjFeeAllotmentItemService.remove(new QueryWrapper<PrjFeeAllotmentItem>().lambda().in(PrjFeeAllotmentItem::getPrjFeeAllotmentId,delIds));
        }
    }

    @Override
    public PrjFeeAllotmentItemVo detail(PrjFeeAllotmentIdParam prjFeeAllotmentIdParam) {
        PrjFeeAllotment prjFeeAllotment = this.queryEntity(prjFeeAllotmentIdParam.getId());
        PrjFeeAllotmentItemVo prjFeeAllotmentItemVo = BeanUtil.copyProperties(prjFeeAllotment,PrjFeeAllotmentItemVo.class);
        prjFeeAllotmentItemVo.setFeeItemList(prjFeeAllotmentItemService.list(new QueryWrapper<PrjFeeAllotmentItem>().lambda().eq(PrjFeeAllotmentItem::getPrjFeeAllotmentId,prjFeeAllotmentItemVo.getId())));
        return prjFeeAllotmentItemVo;
    }

    @Override
    public PrjFeeAllotment queryEntity(String id) {
        PrjFeeAllotment prjFeeAllotment = this.getById(id);
        if(ObjectUtil.isEmpty(prjFeeAllotment)) {
            throw new CommonException("工程款拨付审批不存在，id值为：{}", id);
        }
        return prjFeeAllotment;
    }
}
