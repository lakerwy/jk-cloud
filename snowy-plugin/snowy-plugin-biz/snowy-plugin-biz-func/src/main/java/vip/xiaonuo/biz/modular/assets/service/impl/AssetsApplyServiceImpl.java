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
package vip.xiaonuo.biz.modular.assets.service.impl;

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
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.assets.entity.AssetsBill;
import vip.xiaonuo.biz.modular.assets.service.AssetsBillService;
import vip.xiaonuo.biz.modular.assets.vo.AssetsApplyVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.assets.entity.AssetsApply;
import vip.xiaonuo.biz.modular.assets.mapper.AssetsApplyMapper;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyAddParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyEditParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyIdParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsApplyPageParam;
import vip.xiaonuo.biz.modular.assets.service.AssetsApplyService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 固资申购 - 申请流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/04/08 16:17
 **/
@Service
@DS("slave_1")
public class AssetsApplyServiceImpl extends ServiceImpl<AssetsApplyMapper, AssetsApply> implements AssetsApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private AssetsBillService assetsBillService;

    @Override
    public Page<AssetsApplyVo> page(AssetsApplyPageParam assetsApplyPageParam) {
        QueryWrapper<AssetsApply> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().in(AssetsApply::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getOrgName())) {
            queryWrapper.lambda().like(AssetsApply::getOrgName, assetsApplyPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getOrgId())) {
            queryWrapper.lambda().eq(AssetsApply::getOrgId, assetsApplyPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getUserName())) {
            queryWrapper.lambda().like(AssetsApply::getUserName, assetsApplyPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getType())) {
            queryWrapper.lambda().eq(AssetsApply::getType, assetsApplyPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getItems())) {
            queryWrapper.lambda().like(AssetsApply::getItems, assetsApplyPageParam.getItems());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getHandle())) {
            queryWrapper.lambda().eq(AssetsApply::getHandle, assetsApplyPageParam.getHandle());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getSn())) {
            queryWrapper.lambda().like(AssetsApply::getSn, assetsApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getStatus())) {
            queryWrapper.lambda().eq(AssetsApply::getStatus, assetsApplyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getOptName())){
            queryWrapper.lambda().like(AssetsApply::getOptName,assetsApplyPageParam.getOptName());
        }
        if(ObjectUtil.isNotEmpty(assetsApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(AssetsApply::getOptName,assetsApplyPageParam.getSearchKey())
                    .or().like(AssetsApply::getSn,assetsApplyPageParam.getSearchKey())
                    .or().like(AssetsApply::getItems,assetsApplyPageParam.getSearchKey())
                    .or().like(AssetsApply::getOrgName,assetsApplyPageParam.getSearchKey())
                    .or().like(AssetsApply::getUserName,assetsApplyPageParam.getSearchKey())
                    .or().like(AssetsApply::getType,assetsApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(assetsApplyPageParam.getSortField(), assetsApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(assetsApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, assetsApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(assetsApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(AssetsApply::getCreateTime);
        }
        Page<AssetsApply> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<AssetsApplyVo> assetsApplyVos = BeanUtil.copyToList(result.getRecords(), AssetsApplyVo.class);
        assetsApplyVos.forEach(x->{
            List<AssetsBill> assetsBillList = assetsBillService.list(new QueryWrapper<AssetsBill>().lambda().eq(AssetsBill::getAssetsApplyId,x.getId()));
            x.setBillList(assetsBillList);
        });
        Page<AssetsApplyVo> newResult = new Page<>();
        newResult.setRecords(assetsApplyVos);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AssetsApplyAddParam assetsApplyAddParam) {
        AssetsApply assetsApply = BeanUtil.toBean(assetsApplyAddParam, AssetsApply.class);
        assetsApply.setOptName(StpLoginUserUtil.getLoginUser().getName());
        //走审批流程
        String processInstanceId = assetsApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            assetsApply.setId(id);
            assetsApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            assetsApply.setModelId(modelId);
            assetsApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_ASSETS_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            assetsApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(assetsApply);
        //固资申购-详情
        if (CollUtil.isNotEmpty(assetsApplyAddParam.getBillList())) {
            List<AssetsBill> tmpList = BeanUtil.copyToList(assetsApplyAddParam.getBillList(), AssetsBill.class);
            tmpList.forEach(v -> {
                v.setAssetsApplyId(assetsApply.getId());
            });
            assetsBillService.saveBatch(tmpList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AssetsApplyEditParam assetsApplyEditParam) {
        String id = assetsApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(assetsApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(assetsApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                assetsApplyEditParam.setId(id);
            }
        }
        AssetsApply assetsApply = this.queryEntity(assetsApplyEditParam.getId());
        BeanUtil.copyProperties(assetsApplyEditParam, assetsApply);
        this.updateById(assetsApply);

        //【固资申购清单】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(assetsApplyEditParam.getBillList())) {
            assetsApplyEditParam.getBillList().forEach(x->x.setAssetsApplyId(assetsApply.getId()));
            //新增|修改|删除
            List<AssetsBill> oldList = assetsBillService.list(new QueryWrapper<AssetsBill>().lambda().eq(AssetsBill::getAssetsApplyId, assetsApply.getId()));
            List<String> oldIds = oldList.stream().map(AssetsBill::getId).collect(Collectors.toList());
            //待更新的
            List<AssetsBill> updateList = assetsApplyEditParam.getBillList().stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<AssetsBill> addList = assetsApplyEditParam.getBillList().stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(AssetsBill::getId).collect(Collectors.toList());
            List<AssetsBill> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                assetsBillService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                assetsBillService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                assetsBillService.removeByIds(removeList);
            }
        }else{
            assetsBillService.remove(new QueryWrapper<AssetsBill>().lambda().eq(AssetsBill::getAssetsApplyId, assetsApply.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<AssetsApplyIdParam> assetsApplyIdParamList) {
        List<String> delIds = CollStreamUtil.toList(assetsApplyIdParamList, AssetsApplyIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            assetsBillService.remove(new QueryWrapper<AssetsBill>().lambda().in(AssetsBill::getAssetsApplyId,delIds));
        }
    }

    @Override
    public AssetsApplyVo detail(AssetsApplyIdParam assetsApplyIdParam) {
        AssetsApply assetsApply = this.queryEntity(assetsApplyIdParam.getId());
        AssetsApplyVo assetsApplyVo = BeanUtil.copyProperties(assetsApply,AssetsApplyVo.class);
        List<AssetsBill> assetsBillList = assetsBillService.list(new QueryWrapper<AssetsBill>().lambda().eq(AssetsBill::getAssetsApplyId,assetsApply.getId()));
        assetsApplyVo.setBillList(assetsBillList);
        return assetsApplyVo;
    }

    @Override
    public AssetsApply queryEntity(String id) {
        AssetsApply assetsApply = this.getById(id);
        if(ObjectUtil.isEmpty(assetsApply)) {
            throw new CommonException("固资申购 - 申请流程不存在，id值为：{}", id);
        }
        return assetsApply;
    }
}
