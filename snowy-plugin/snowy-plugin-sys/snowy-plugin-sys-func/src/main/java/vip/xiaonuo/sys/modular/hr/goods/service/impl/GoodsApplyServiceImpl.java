package vip.xiaonuo.sys.modular.hr.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsApply;
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsItem;
import vip.xiaonuo.sys.modular.hr.goods.enums.GoodsTypeEnum;
import vip.xiaonuo.sys.modular.hr.goods.mapper.GoodsApplyMapper;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyAddParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyEditParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyIdParam;
import vip.xiaonuo.sys.modular.hr.goods.param.GoodsApplyPageParam;
import vip.xiaonuo.sys.modular.hr.goods.service.GoodsApplyService;
import vip.xiaonuo.sys.modular.hr.goods.service.GoodsItemService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物资领用申请Service接口实现类
 *
 * @author yy
 * @date  2025/05/23 14:34
 **/
@Service
public class GoodsApplyServiceImpl extends ServiceImpl<GoodsApplyMapper, GoodsApply> implements GoodsApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private GoodsItemService goodsItemService;

    @Override
    public Page<GoodsApply> page(GoodsApplyPageParam goodsApplyPageParam) {
        QueryWrapper<GoodsApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(GoodsApply::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getDeptId())) {
            queryWrapper.lambda().eq(GoodsApply::getDeptId, goodsApplyPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getDeptName())) {
            queryWrapper.lambda().like(GoodsApply::getDeptName, goodsApplyPageParam.getDeptName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getOptId())) {
            queryWrapper.lambda().eq(GoodsApply::getOptId, goodsApplyPageParam.getOptId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getOptName())) {
            queryWrapper.lambda().like(GoodsApply::getOptName, goodsApplyPageParam.getOptName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getStartOptTime()) && ObjectUtil.isNotEmpty(goodsApplyPageParam.getEndOptTime())) {
            queryWrapper.lambda().between(GoodsApply::getOptTime, goodsApplyPageParam.getStartOptTime(), goodsApplyPageParam.getEndOptTime());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getDescMemo())) {
            queryWrapper.lambda().like(GoodsApply::getDescMemo, goodsApplyPageParam.getDescMemo());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getConfirmType())) {
            queryWrapper.lambda().eq(GoodsApply::getConfirmType, goodsApplyPageParam.getConfirmType());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getStartConfirmTime()) && ObjectUtil.isNotEmpty(goodsApplyPageParam.getEndConfirmTime())) {
            queryWrapper.lambda().between(GoodsApply::getConfirmTime, goodsApplyPageParam.getStartConfirmTime(), goodsApplyPageParam.getEndConfirmTime());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getBackId())) {
            queryWrapper.lambda().eq(GoodsApply::getBackId, goodsApplyPageParam.getBackId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getBackName())) {
            queryWrapper.lambda().like(GoodsApply::getBackName, goodsApplyPageParam.getBackName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getVerifyId())) {
            queryWrapper.lambda().eq(GoodsApply::getVerifyId, goodsApplyPageParam.getVerifyId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getVerifyName())) {
            queryWrapper.lambda().like(GoodsApply::getVerifyName, goodsApplyPageParam.getVerifyName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getSn())) {
            queryWrapper.lambda().like(GoodsApply::getSn, goodsApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getStatus())) {
            queryWrapper.lambda().eq(GoodsApply::getStatus, goodsApplyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(GoodsApply::getDeptName,goodsApplyPageParam.getSearchKey())
                    .or().like(GoodsApply::getSn,goodsApplyPageParam.getSearchKey())
                    .or().like(GoodsApply::getGoodsApplyJson,goodsApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(goodsApplyPageParam.getSortField(), goodsApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(goodsApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, goodsApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(goodsApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(GoodsApply::getSn);
        }
        Page<GoodsApply> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        if(CollUtil.isNotEmpty(result.getRecords())){
            List<String> ids = result.getRecords().stream().map(GoodsApply::getId).collect(Collectors.toList());
            List<GoodsItem> itemList = goodsItemService.list(new LambdaQueryWrapper<GoodsItem>().in(GoodsItem::getGoodsApplyId,ids));
            result.getRecords().forEach(item->{
                List<String> applyList = itemList.stream().filter(x->item.getId().equals(x.getGoodsApplyId()) && GoodsTypeEnum.GOODS_APPLY.getValue().equals(x.getType())).map(x->x.getGoodName()).collect(Collectors.toList());
                List<String> backList = itemList.stream().filter(x->item.getId().equals(x.getGoodsApplyId()) && GoodsTypeEnum.GOODS_BACK.getValue().equals(x.getType())).map(x->x.getGoodName()).collect(Collectors.toList());
                item.setGoodsApplyStr(StrUtil.join("，",applyList));
                item.setGoodsBackStr(StrUtil.join("，",backList));
                item.setGoodsApplyJson(null);
                item.setGoodsBackJson(null);
            });
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(GoodsApplyAddParam goodsApplyAddParam) {
        GoodsApply goodsApply = BeanUtil.toBean(goodsApplyAddParam, GoodsApply.class);

        //走审批流程
        String processInstanceId = goodsApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            goodsApply.setId(id);
            goodsApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            goodsApply.setModelId(modelId);
            goodsApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            goodsApply.setOptTime(new Date());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_GOODS_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            goodsApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        goodsApply.setGoodsApplyJson(JSONUtil.toJsonStr(goodsApplyAddParam.getGoodsApplyList()));
        goodsApply.setGoodsBackJson(JSONUtil.toJsonStr(goodsApplyAddParam.getGoodsBackList()));
        this.save(goodsApply);

        //处理物资清单
        List<GoodsItem> goodsItemList = new ArrayList<>();
        if(CollUtil.isNotEmpty(goodsApplyAddParam.getGoodsApplyList())){
            List<GoodsItem> goodsApplyList = BeanUtil.copyToList(goodsApplyAddParam.getGoodsApplyList(), GoodsItem.class);
            goodsApplyList.forEach(x->{
                x.setType(GoodsTypeEnum.GOODS_APPLY.getValue());
                x.setGoodsApplyId(goodsApply.getId());
            });
            goodsItemList.addAll(goodsApplyList);
        }
        if(CollUtil.isNotEmpty(goodsApplyAddParam.getGoodsBackList())){
            List<GoodsItem> goodsBackList = BeanUtil.copyToList(goodsApplyAddParam.getGoodsBackList(), GoodsItem.class);
            goodsBackList.forEach(x->{
                x.setType(GoodsTypeEnum.GOODS_BACK.getValue());
                x.setGoodsApplyId(goodsApply.getId());
            });
            goodsItemList.addAll(goodsBackList);
        }
        if(CollUtil.isNotEmpty(goodsItemList)) {
            goodsItemService.saveBatch(goodsItemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(GoodsApplyEditParam goodsApplyEditParam) {
        String id = goodsApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(goodsApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(goodsApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                goodsApplyEditParam.setId(id);
            }
        }
        GoodsApply goodsApply = this.queryEntity(goodsApplyEditParam.getId());
        BeanUtil.copyProperties(goodsApplyEditParam, goodsApply);
        goodsApply.setGoodsApplyJson(JSONUtil.toJsonStr(goodsApplyEditParam.getGoodsApplyList()));
        goodsApply.setGoodsBackJson(JSONUtil.toJsonStr(goodsApplyEditParam.getGoodsBackList()));
        this.updateById(goodsApply);

        //处理物资清单
        if(CollUtil.isNotEmpty(goodsApplyEditParam.getGoodsApplyList())){
            //本次传入的数据
            List<GoodsItem> goodsItemList = BeanUtil.copyToList(goodsApplyEditParam.getGoodsApplyList(),GoodsItem.class);
            goodsItemList.forEach(x->{
                x.setGoodsApplyId(goodsApply.getId());
                x.setType(GoodsTypeEnum.GOODS_APPLY.getValue());
            });
            //获取历史数据
            List<GoodsItem> oldItemList = goodsItemService.list(new QueryWrapper<GoodsItem>().lambda().eq(GoodsItem::getGoodsApplyId,goodsApply.getId())
                    .eq(GoodsItem::getType,GoodsTypeEnum.GOODS_APPLY.getValue()));
            List<String> oldIds = oldItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<GoodsItem> addList = goodsItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<GoodsItem> updateList = goodsItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<GoodsItem> removeList = oldItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                goodsItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                goodsItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                goodsItemService.removeByIds(removeList);
            }
        }
        //处理退回清单
        if(CollUtil.isNotEmpty(goodsApplyEditParam.getGoodsBackList())){
            //本次传入的数据
            List<GoodsItem> goodsItemList = BeanUtil.copyToList(goodsApplyEditParam.getGoodsBackList(),GoodsItem.class);
            goodsItemList.forEach(x->{
                x.setGoodsApplyId(goodsApply.getId());
                x.setType(GoodsTypeEnum.GOODS_BACK.getValue());
            });
            //获取历史数据
            List<GoodsItem> oldItemList = goodsItemService.list(new QueryWrapper<GoodsItem>().lambda().eq(GoodsItem::getGoodsApplyId,goodsApply.getId())
                    .eq(GoodsItem::getType,GoodsTypeEnum.GOODS_BACK.getValue()));
            List<String> oldIds = oldItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<GoodsItem> addList = goodsItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<GoodsItem> updateList = goodsItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<GoodsItem> removeList = oldItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                goodsItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                goodsItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                goodsItemService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<GoodsApplyIdParam> goodsApplyIdParamList) {
        List<String> delIds = CollStreamUtil.toList(goodsApplyIdParamList, GoodsApplyIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            goodsItemService.remove(new LambdaQueryWrapper<GoodsItem>().in(GoodsItem::getGoodsApplyId,delIds));
        }
    }

    @Override
    public GoodsApply detail(GoodsApplyIdParam goodsApplyIdParam) {
        return this.queryEntity(goodsApplyIdParam.getId());
    }

    @Override
    public GoodsApply queryEntity(String id) {
        GoodsApply goodsApply = this.getById(id);
        if(ObjectUtil.isEmpty(goodsApply)) {
            throw new CommonException("物资领用申请不存在，id值为：{}", id);
        }
        return goodsApply;
    }
}
