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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGz;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGzItem;
import vip.xiaonuo.sys.modular.hr.sub.enums.SubGzItemTypeEnum;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubGzMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzItemService;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzService;
import vip.xiaonuo.sys.modular.hr.sub.vo.MonthVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearGzVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearNumVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分公司-工资发放（流程）Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/06 09:09
 **/
@Service
public class SubGzServiceImpl extends ServiceImpl<SubGzMapper, SubGz> implements SubGzService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private SubGzItemService subGzItemService;

    @Override
    public Page<SubGz> page(SubGzPageParam subGzPageParam) {
        QueryWrapper<SubGz> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subGzPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SubGz::getOrgId, subGzPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getOrgName())) {
            queryWrapper.lambda().like(SubGz::getOrgName, subGzPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getYM())) {
            queryWrapper.lambda().eq(SubGz::getYM, subGzPageParam.getYM());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getRegion())) {
            queryWrapper.lambda().eq(SubGz::getRegion, subGzPageParam.getRegion());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getManagerName())) {
            queryWrapper.lambda().like(SubGz::getManagerName, subGzPageParam.getManagerName());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getSn())) {
            queryWrapper.lambda().like(SubGz::getSn, subGzPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getStatus())) {
            queryWrapper.lambda().eq(SubGz::getStatus, subGzPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(subGzPageParam.getSortField(), subGzPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subGzPageParam.getSortOrder());
            queryWrapper.orderBy(true, subGzPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subGzPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubGz::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubGzParam subGzParam) {
        SubGz subGz = BeanUtil.toBean(subGzParam, SubGz.class);
        //走审批流程
        String processInstanceId = subGzParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            subGz.setId(id);
            subGz.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            subGz.setModelId(modelId);
            subGz.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.SUB_GZ.getBizTable(),id,processInstanceId,dbName);
        }else{
            subGz.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(subGz);
        //处理发票
        List<SubGzItem> subGzItemList = new ArrayList<>();
        if(CollUtil.isNotEmpty(subGzParam.getGzList())){
            List<SubGzItem> tmpItems = BeanUtil.copyToList(subGzParam.getGzList(), SubGzItem.class);
            tmpItems.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.GZ.getValue());
            });
            subGzItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subGzParam.getJjList())){
            List<SubGzItem> tmpItems = BeanUtil.copyToList(subGzParam.getJjList(), SubGzItem.class);
            tmpItems.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.JJ.getValue());
            });
            subGzItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subGzItemList)) {
            subGzItemService.saveBatch(subGzItemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubGzParam subGzParam) {
        String id = subGzParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(subGzParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(subGzParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                subGzParam.setId(id);
            }
        }
        SubGz subGz = this.queryEntity(subGzParam.getId());
        BeanUtil.copyProperties(subGzParam, subGz);
        this.updateById(subGz);
        //处理工资
        if(CollUtil.isNotEmpty(subGzParam.getGzList())){
            //本次传入的数据
            List<SubGzItem> gzItemList = BeanUtil.copyToList(subGzParam.getGzList(),SubGzItem.class);
            gzItemList.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.GZ.getValue());
            });
            //获取历史数据
            List<SubGzItem> oldGzItemList = subGzItemService.list(new QueryWrapper<SubGzItem>().lambda().eq(SubGzItem::getSubGzId,subGz.getId())
                            .eq(SubGzItem::getType,SubGzItemTypeEnum.GZ.getValue()));
            List<String> oldIds = oldGzItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<SubGzItem> addList = gzItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<SubGzItem> updateList = gzItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<SubGzItem> removeList = oldGzItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                subGzItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                subGzItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                subGzItemService.removeByIds(removeList);
            }
        }
        //处理奖金
        if(CollUtil.isNotEmpty(subGzParam.getJjList())){
            //本次传入的数据
            List<SubGzItem> gzItemList = BeanUtil.copyToList(subGzParam.getJjList(),SubGzItem.class);
            gzItemList.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.JJ.getValue());
            });
            //获取历史数据
            List<SubGzItem> oldGzItemList = subGzItemService.list(new QueryWrapper<SubGzItem>().lambda().eq(SubGzItem::getSubGzId,subGz.getId())
                    .eq(SubGzItem::getType,SubGzItemTypeEnum.JJ.getValue()));
            List<String> oldIds = oldGzItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<SubGzItem> addList = gzItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<SubGzItem> updateList = gzItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<SubGzItem> removeList = oldGzItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                subGzItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                subGzItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                subGzItemService.removeByIds(removeList);
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubGzIdParam> subGzIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(subGzIdParamList, SubGzIdParam::getId);
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            subGzItemService.remove(new LambdaQueryWrapper<SubGzItem>().in(SubGzItem::getSubGzId,ids));
        }
    }

    @Override
    public SubGz detail(SubGzIdParam subGzIdParam) {
        return this.queryEntity(subGzIdParam.getId());
    }

    @Override
    public SubGz queryEntity(String id) {
        SubGz subGz = this.getById(id);
        if(ObjectUtil.isEmpty(subGz)) {
            throw new CommonException("分公司-工资发放（流程）不存在，id值为：{}", id);
        }
        return subGz;
    }

    @Override
    public List<MonthVo> monthTotal(SearchTotal searchTotal) {
        return null;
    }

    @Override
    public List<YearNumVo> yearNumTotal(SearchTotal searchTotal) {
        return null;
    }

    @Override
    public List<YearGzVo> yearGzTotal(SearchTotal searchTotal) {
        return null;
    }
}
