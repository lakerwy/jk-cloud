package vip.xiaonuo.sys.modular.hr.sub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFiling;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFilingItem;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubFilingMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFilingItemService;
import vip.xiaonuo.sys.modular.hr.sub.service.SubFilingService;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubFilingVo;
import vip.xiaonuo.sys.modular.info.param.PartnerInfoPageParam;
import vip.xiaonuo.sys.modular.info.service.PartnerInfoService;
import vip.xiaonuo.sys.modular.info.vo.PartnerInfoVo;
import vip.xiaonuo.sys.modular.org.service.SysOrgService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 分包单位备案Service接口实现类
 *
 * @author pzh
 * @date  2025/03/11 14:32
 **/
@Service
public class SubFilingServiceImpl extends ServiceImpl<SubFilingMapper, SubFiling> implements SubFilingService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private SubFilingItemService subFilingItemService;

    @Resource
    private SysOrgService sysOrgService;

    @Resource
    private SubFilingMapper subFilingMapper;

    @Resource
    PartnerInfoService partnerInfoService;

    @Override
    public Page<SubFilingVo> page(SubFilingPageParam subFilingPageParam) {
        QueryWrapper<SubFiling> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        // 仅查询审批通过的单据
        queryWrapper.eq("a.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue());
        // 关键词搜索
        if(ObjectUtil.isNotEmpty(subFilingPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SEAL_DEPT",subFilingPageParam.getSearchKey())
                    .or().like("b.UNIT_NAME",subFilingPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(subFilingPageParam.getSealDept())){
            queryWrapper.like("a.SEAL_DEPT", subFilingPageParam.getSealDept());
        }
        if(ObjectUtil.isNotEmpty(subFilingPageParam.getSealDeptId())){
            queryWrapper.eq("a.SEAL_DEPT_ID", subFilingPageParam.getSealDeptId());
        }
        if(ObjectUtil.isAllNotEmpty(subFilingPageParam.getSortField(), subFilingPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subFilingPageParam.getSortOrder());
            queryWrapper.orderBy(true, subFilingPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subFilingPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.UPDATE_TIME").orderByDesc("a.SEAL_DEPT_ID");
        }
        // 校验数据范围
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.in("a.DATA_SCOPE_ID", loginUserDataScope);
//        } else {
//            queryWrapper.eq("a.CREATE_USER", StpUtil.getLoginIdAsString());
//        }
        return subFilingMapper.getSubFilingPage(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public List<SubFilingVo> getList(SubFilingListParam subFilingListParam) {
        QueryWrapper<SubFiling> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        // 仅查询审批通过的单据
        queryWrapper.eq("a.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue());
        // 关键词搜索
        if(ObjectUtil.isNotEmpty(subFilingListParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SEAL_DEPT",subFilingListParam.getSearchKey())
                    .or().like("b.UNIT_NAME",subFilingListParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(subFilingListParam.getSealDeptId())){
            queryWrapper.eq("a.SEAL_DEPT_ID", subFilingListParam.getSealDeptId());
        }
        if(ObjectUtil.isNotEmpty(subFilingListParam.getOrgId())){
            queryWrapper.eq("a.ORG_ID", subFilingListParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(subFilingListParam.getSealDept())){
            queryWrapper.like("a.SEAL_DEPT", subFilingListParam.getSealDept());
        }
        queryWrapper.orderByDesc("a.UPDATE_TIME").orderByDesc("a.SEAL_DEPT_ID");
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.in("a.DATA_SCOPE_ID", loginUserDataScope);
        } else {
            queryWrapper.eq("a.CREATE_USER", StpUtil.getLoginIdAsString());
        }
        return subFilingMapper.getSubFilingList(queryWrapper);
    }

    @Override
    public List<SubFilingVo> getListAll(SubFilingListParam subFilingListParam) {
        QueryWrapper<SubFiling> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        // 仅查询审批通过的单据
        queryWrapper.eq("a.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue());
        // 关键词搜索
        if(ObjectUtil.isNotEmpty(subFilingListParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SEAL_DEPT",subFilingListParam.getSearchKey())
                    .or().like("b.UNIT_NAME",subFilingListParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(subFilingListParam.getSealDeptId())){
            queryWrapper.eq("a.SEAL_DEPT_ID", subFilingListParam.getSealDeptId());
        }
        if(ObjectUtil.isNotEmpty(subFilingListParam.getOrgId())){
            queryWrapper.eq("a.ORG_ID", subFilingListParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(subFilingListParam.getSealDept())){
            queryWrapper.like("a.SEAL_DEPT", subFilingListParam.getSealDept());
        }
        queryWrapper.orderByDesc("a.UPDATE_TIME").orderByDesc("a.SEAL_DEPT_ID");
        return subFilingMapper.getSubFilingList(queryWrapper);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubFilingAddParam subFilingAddParam) {
        SubFiling subFiling = BeanUtil.toBean(subFilingAddParam, SubFiling.class);
        //走审批流程
        String processInstanceId = subFiling.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            subFiling.setId(id);
            subFiling.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            subFiling.setModelId(modelId);
            subFiling.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.SUB_FILING_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            subFiling.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        Date date = new Date();
        subFiling.setCreateTime(date);
        subFiling.setUpdateTime(date);
        if(ObjectUtil.isNotEmpty(subFilingAddParam.getOrgId())){
            subFiling.setDataScopeId(subFilingAddParam.getOrgId());
        }
        this.save(subFiling);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubFilingEditParam subFilingEditParam) {
        String id = subFilingEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(subFilingEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(subFilingEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                subFilingEditParam.setId(id);
            }
        }
        SubFiling subFiling = this.queryEntity(subFilingEditParam.getId());
        BeanUtil.copyProperties(subFilingEditParam, subFiling);
        this.updateById(subFiling);
//        updateSubFilingItem(subFiling.getId(), subFilingEditParam.getSubFilingItemList());
    }

    @Override
    public void updateSubFilingItem(String id, String sealDeptId, List<SubFilingItemEditParam> subFilingItemList) {
//        if(CollUtil.isEmpty(subFilingItemList)){
//            return;
//        }
        //本次传入的数据
        List<SubFilingItem> itemList = BeanUtil.copyToList(subFilingItemList,SubFilingItem.class);
        itemList.forEach(x->{
            x.setSubFilingId(id);
        });
        //获取历史数据
        List<SubFiling> subFilingList = this.list(new LambdaQueryWrapper<SubFiling>().eq(SubFiling::getSealDeptId, sealDeptId));
        List<String> oldSubFilingIdList = subFilingList.stream().map(SubFiling::getId).collect(Collectors.toList());
        List<SubFilingItem> oldSubFilingItemList = subFilingItemService.list(new QueryWrapper<SubFilingItem>().lambda().in(SubFilingItem::getSubFilingId, oldSubFilingIdList));
        List<String> oldIds = oldSubFilingItemList.stream().map(SubFilingItem::getId).collect(Collectors.toList());
        //待新增的
        List<SubFilingItem> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
        //待更新的
        List<SubFilingItem> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
        //待删除的
        List<String> updateIds = updateList.stream().map(SubFilingItem::getId).collect(Collectors.toList());
        List<SubFilingItem> removeList = oldSubFilingItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(addList)){
            subFilingItemService.saveBatch(addList);
        }
        if(CollUtil.isNotEmpty(updateList)){
            subFilingItemService.updateBatchById(updateList);
        }
        if(CollUtil.isNotEmpty(removeList)){
            subFilingItemService.removeByIds(removeList);
        }
        // 更新主表时间
        this.update(new LambdaUpdateWrapper<SubFiling>().eq(SubFiling::getId, id).set(SubFiling::getUpdateTime,new Date()));
    }

    @Override
    public void check(SubFilingCheckParam checkParam) {
        // 是否存在审批中的流程
        if(ObjectUtil.isNotEmpty(checkParam.getSealDeptId())){
            List<SubFiling> subFilingList = this.list(new LambdaQueryWrapper<SubFiling>()
                    .eq(SubFiling::getSealDeptId,checkParam.getSealDeptId()).in(SubFiling::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue()));
            if(CollUtil.isNotEmpty(subFilingList)){
                throw new CommonException("该分公司已有审核中的分包单位备案流程，不可发起新的分包单位备案流程");
            }
        }
        List<SubFilingItemEditParam> subFilingItemList = checkParam.getSubFilingItemList();
        if(CollUtil.isNotEmpty(subFilingItemList)){
            // 传参列表内部是否存在分包单位名称重复
            Set<String> tempSet = new HashSet<>();
            boolean hasDuplicate = subFilingItemList.stream().anyMatch(obj -> !tempSet.add(obj.getUnitName()));
            if(hasDuplicate){
                throw new CommonException("分包单位名称不可重复！");
            }
            // 与数据库比较是否存在分包单位名称重复
            List<String> unitNameList = subFilingItemList.stream().map(SubFilingItemEditParam::getUnitName).collect(Collectors.toList());
            List<SubFiling> subFilingList = this.list(new LambdaQueryWrapper<SubFiling>()
                    .select(SubFiling::getId)
                    .eq(SubFiling::getSealDeptId, checkParam.getSealDeptId())
                    .eq(SubFiling::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
            List<String> subFilingIdList = subFilingList.stream().map(SubFiling::getId).collect(Collectors.toList());
            LambdaQueryWrapper<SubFilingItem> itemQueryWrapper = new LambdaQueryWrapper<>();
            if(CollUtil.isNotEmpty(subFilingIdList)){
                itemQueryWrapper.notIn(SubFilingItem::getSubFilingId, subFilingIdList);
            }
            List<SubFilingItem> existSubFilingItemList = subFilingItemService.list(itemQueryWrapper);
            for(SubFilingItem subFilingItem : existSubFilingItemList){
                if(unitNameList.contains(subFilingItem.getUnitName())){
                    SubFiling subFiling = this.getById(subFilingItem.getSubFilingId());
                    if(ObjectUtil.isNotEmpty(subFiling)){
                        throw new CommonException("分包单位名称已与{}已被案分包单位名称重复！请确认后再发起",subFiling.getSealDept());
                    }
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubFilingIdParam> subFilingIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(subFilingIdParamList, SubFilingIdParam::getId);
        this.removeByIds(ids);
        // 同时删除明细
        if(CollUtil.isNotEmpty(ids)){
            subFilingItemService.remove(new LambdaQueryWrapper<SubFilingItem>().in(SubFilingItem::getSubFilingId,ids));
        }
    }

    @Override
    public SubFiling detail(SubFilingIdParam subFilingIdParam) {
        return this.queryEntity(subFilingIdParam.getId());
    }

    @Override
    public SubFiling queryEntity(String id) {
        SubFiling subFiling = this.getById(id);
        if(ObjectUtil.isEmpty(subFiling)) {
            throw new CommonException("分包单位备案不存在，id值为：{}", id);
        }
        return subFiling;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(ImportSubFiling input) {
        List<ImportSubFiling.SubFilingItemImportParam> importData = input.getImportData();
        if(CollUtil.isEmpty(importData)){
            throw new CommonException("无有效导入数据");
        }
        Map<String, List<ImportSubFiling.SubFilingItemImportParam>> groupedMap = importData.stream()
                .collect(Collectors.groupingBy(ImportSubFiling.SubFilingItemImportParam::getSealDept));
        PartnerInfoPageParam partnerInfoPageParam = new PartnerInfoPageParam();
        IPage<PartnerInfoVo> partnerInfoVoPage = partnerInfoService.pageList(partnerInfoPageParam);
        List<PartnerInfoVo> partnerList = partnerInfoVoPage.getRecords();
        if(CollUtil.isEmpty(partnerList)){
            return;
        }
        List<String> existList = this.list().stream().map(SubFiling::getSealDeptId).collect(Collectors.toList());
        Date now = new Date();
        List<SubFiling> subFilingList = new ArrayList<>();
        List<SubFilingItem> subFilingItemList = new ArrayList<>();
        for (String sealDept: groupedMap.keySet()){
            for(PartnerInfoVo partnerInfoVo : partnerList){
                if(sealDept.equals(partnerInfoVo.getName())){
                    if(!("分公司".equals(partnerInfoVo.getPartnerType()) || "分公司（注册）".equals(partnerInfoVo.getPartnerType()))){
                        continue;
                    }
                    if(existList.contains(partnerInfoVo.getId())){
                        continue;
                    }
                    SubFiling subFiling = new SubFiling();
                    String id = IdWorker.getIdStr();
                    subFiling.setId(id);
                    subFiling.setSealDept(partnerInfoVo.getName());
                    subFiling.setSealDeptId(partnerInfoVo.getId());
                    subFiling.setOrgId(partnerInfoVo.getOrgId());
                    if(ObjectUtil.isNotEmpty(partnerInfoVo.getOrgId())){
                        subFiling.setDataScopeId(partnerInfoVo.getOrgId());
                    }
                    subFiling.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
                    subFiling.setCreateTime(now);
                    subFiling.setUpdateTime(now);
                    subFilingList.add(subFiling);
                    List<ImportSubFiling.SubFilingItemImportParam> subFilingItemImportParams = groupedMap.get(sealDept);
                    List<SubFilingItem> itemList = BeanUtil.copyToList(subFilingItemImportParams, SubFilingItem.class);
                    itemList.forEach(item->{
                        item.setSubFilingId(id);
                    });
                    subFilingItemList.addAll(itemList);
                    break;
                }
            }
        }
        this.saveBatch(subFilingList);
        subFilingItemService.saveBatch(subFilingItemList);
    }
}
