package vip.xiaonuo.sys.modular.hr.sub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFund;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFundItem;
import vip.xiaonuo.sys.modular.hr.sub.enums.SubFundItemTypeEnum;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubFundMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFundItemService;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFundService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分公司-五险一金（流程）Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/06 09:12
 **/
@Service
public class SubFundServiceImpl extends ServiceImpl<SubFundMapper, SubFund> implements SubFundService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private SubFundItemService subFundItemService;

    @Override
    public Page<SubFund> page(SubFundPageParam subFundPageParam) {
        QueryWrapper<SubFund> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SubFund::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(subFundPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SubFund::getOrgId, subFundPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(subFundPageParam.getOrgName())) {
            queryWrapper.lambda().like(SubFund::getOrgName, subFundPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(subFundPageParam.getYM())) {
            queryWrapper.lambda().eq(SubFund::getYM, subFundPageParam.getYM());
        }
        if(ObjectUtil.isNotEmpty(subFundPageParam.getRegion())) {
            queryWrapper.lambda().eq(SubFund::getRegion, subFundPageParam.getRegion());
        }
        if(ObjectUtil.isNotEmpty(subFundPageParam.getManagerName())) {
            queryWrapper.lambda().like(SubFund::getManagerName, subFundPageParam.getManagerName());
        }
        if(ObjectUtil.isNotEmpty(subFundPageParam.getSn())) {
            queryWrapper.lambda().like(SubFund::getSn, subFundPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(subFundPageParam.getStatus())) {
            queryWrapper.lambda().eq(SubFund::getStatus, subFundPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(subFundPageParam.getSortField(), subFundPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subFundPageParam.getSortOrder());
            queryWrapper.orderBy(true, subFundPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subFundPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubFund::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubFundParam subFundParam) {
        SubFund subFund = BeanUtil.toBean(subFundParam, SubFund.class);
        //走审批流程
        String processInstanceId = subFund.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            subFund.setId(id);
            subFund.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            subFund.setModelId(modelId);
            subFund.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.SUB_FUND.getBizTable(),id,processInstanceId,dbName);
        }else{
            subFund.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(subFund);
        //处理社保
        List<SubFundItem> subFundItemList = new ArrayList<>();
        if(CollUtil.isNotEmpty(subFundParam.getInsList())){
            List<SubFundItem> tmpItems = BeanUtil.copyToList(subFundParam.getInsList(), SubFundItem.class);
            tmpItems.forEach(x->{
                x.setSubFundId(subFund.getId());
                x.setType(SubFundItemTypeEnum.INS.getValue());
            });
            subFundItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subFundParam.getGjjList())){
            List<SubFundItem> tmpItems = BeanUtil.copyToList(subFundParam.getGjjList(), SubFundItem.class);
            tmpItems.forEach(x->{
                x.setSubFundId(subFund.getId());
                x.setType(SubFundItemTypeEnum.GJJ.getValue());
            });
            subFundItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subFundItemList)) {
            subFundItemService.saveBatch(subFundItemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubFundParam subFundParam) {
        String id = subFundParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(subFundParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(subFundParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                subFundParam.setId(id);
            }
        }
        SubFund subFund = this.queryEntity(subFundParam.getId());
        BeanUtil.copyProperties(subFundParam, subFund);
        this.updateById(subFund);
        //处理工资
        if(CollUtil.isNotEmpty(subFundParam.getInsList())){
            //本次传入的数据
            List<SubFundItem> insItemList = BeanUtil.copyToList(subFundParam.getInsList(),SubFundItem.class);
            insItemList.forEach(x->{
                x.setSubFundId(subFund.getId());
                x.setType(SubFundItemTypeEnum.INS.getValue());
            });
            //获取历史数据
            List<SubFundItem> oldFundItemList = subFundItemService.list(new QueryWrapper<SubFundItem>().lambda().eq(SubFundItem::getSubFundId,subFund.getId())
                    .eq(SubFundItem::getType,SubFundItemTypeEnum.INS.getValue()));
            List<String> oldIds = oldFundItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<SubFundItem> addList = insItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<SubFundItem> updateList = insItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<SubFundItem> removeList = oldFundItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                subFundItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                subFundItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                subFundItemService.removeByIds(removeList);
            }
        }
        //处理奖金
        if(CollUtil.isNotEmpty(subFundParam.getGjjList())){
            //本次传入的数据
            List<SubFundItem> gjjItemList = BeanUtil.copyToList(subFundParam.getGjjList(),SubFundItem.class);
            gjjItemList.forEach(x->{
                x.setSubFundId(subFund.getId());
                x.setType(SubFundItemTypeEnum.GJJ.getValue());
            });
            //获取历史数据
            List<SubFundItem> oldFundItemList = subFundItemService.list(new QueryWrapper<SubFundItem>().lambda().eq(SubFundItem::getSubFundId,subFund.getId())
                    .eq(SubFundItem::getType,SubFundItemTypeEnum.GJJ.getValue()));
            List<String> oldIds = oldFundItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<SubFundItem> addList = gjjItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<SubFundItem> updateList = gjjItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<SubFundItem> removeList = oldFundItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                subFundItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                subFundItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                subFundItemService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubFundIdParam> subFundIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(subFundIdParamList, SubFundIdParam::getId);
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            subFundItemService.remove(new LambdaQueryWrapper<SubFundItem>().in(SubFundItem::getSubFundId,ids));
        }
    }

    @Override
    public SubFund detail(SubFundIdParam subFundIdParam) {
        return this.queryEntity(subFundIdParam.getId());
    }

    @Override
    public SubFund queryEntity(String id) {
        SubFund subFund = this.getById(id);
        if(ObjectUtil.isEmpty(subFund)) {
            throw new CommonException("分公司-五险一金（流程）不存在，id值为：{}", id);
        }
        return subFund;
    }
}
