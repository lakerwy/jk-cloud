package vip.xiaonuo.biz.modular.prj.mou.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.prj.mou.param.*;
import vip.xiaonuo.biz.modular.prj.mou.vo.BizPrjMouVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.mou.entity.BizPrjMou;
import vip.xiaonuo.biz.modular.prj.mou.mapper.BizPrjMouMapper;
import vip.xiaonuo.biz.modular.prj.mou.service.BizPrjMouService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目承接备忘录Service接口实现类
 *
 * @author yy
 * @date  2025/05/27 10:06
 **/

@DS("slave_1")
@Service
public class BizPrjMouServiceImpl extends ServiceImpl<BizPrjMouMapper, BizPrjMou> implements BizPrjMouService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private HetongService hetongService;

    @Override
    public Page<BizPrjMou> page(BizPrjMouPageParam bizPrjMouPageParam) {
        QueryWrapper<BizPrjMou> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getPrjId())) {
            queryWrapper.lambda().eq(BizPrjMou::getPrjId, bizPrjMouPageParam.getPrjId());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getPrjName())) {
            queryWrapper.lambda().like(BizPrjMou::getPrjName, bizPrjMouPageParam.getPrjName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getPrjMemo())) {
            queryWrapper.lambda().like(BizPrjMou::getPrjMemo, bizPrjMouPageParam.getPrjMemo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getDevUnit())) {
            queryWrapper.lambda().like(BizPrjMou::getDevUnit, bizPrjMouPageParam.getDevUnit());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getPartnerType())) {
            queryWrapper.lambda().eq(BizPrjMou::getPartnerType, bizPrjMouPageParam.getPartnerType());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getSn())) {
            queryWrapper.lambda().like(BizPrjMou::getSn, bizPrjMouPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizPrjMou::getStatus, bizPrjMouPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizPrjMouPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizPrjMou::getSn,bizPrjMouPageParam.getSearchKey())
                    .or().like(BizPrjMou::getPrjName,bizPrjMouPageParam.getSearchKey())
                    .or().like(BizPrjMou::getDevUnit,bizPrjMouPageParam.getSearchKey())
                    .or().like(BizPrjMou::getPartnerUnit,bizPrjMouPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizPrjMouPageParam.getSortField(), bizPrjMouPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjMouPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjMouPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjMouPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizPrjMou::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizPrjMouAddParam bizPrjMouAddParam) {
        BizPrjMou bizPrjMou = BeanUtil.toBean(bizPrjMouAddParam, BizPrjMou.class);

        //走审批流程
        String processInstanceId = bizPrjMouAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizPrjMou.setId(id);
            bizPrjMou.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizPrjMou.setModelId(modelId);
            bizPrjMou.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_PRJ_MOU.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizPrjMou.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        bizPrjMou.setApplyDate(new Date());

        this.save(bizPrjMou);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizPrjMouEditParam bizPrjMouEditParam) {
        String id = bizPrjMouEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizPrjMouEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizPrjMouEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizPrjMouEditParam.setId(id);
            }
        }

        BizPrjMou bizPrjMou = this.queryEntity(bizPrjMouEditParam.getId());
        BeanUtil.copyProperties(bizPrjMouEditParam, bizPrjMou);
        this.updateById(bizPrjMou);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjMouIdParam> bizPrjMouIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizPrjMouIdParamList, BizPrjMouIdParam::getId));
    }

    @Override
    public BizPrjMou detail(BizPrjMouIdParam bizPrjMouIdParam) {
        return this.queryEntity(bizPrjMouIdParam.getId());
    }

    @Override
    public BizPrjMou queryEntity(String id) {
        BizPrjMou bizPrjMou = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjMou)) {
            throw new CommonException("项目承接备忘录不存在，id值为：{}", id);
        }
        return bizPrjMou;
    }

    @Override
    public List<BizPrjMouVo> getListById(MouParam param) {
        if(ObjectUtil.isAllEmpty(param.getBizProjectId(),param.getHetongId())){
            throw new CommonException("参数缺失");
        }
        String prjId = param.getBizProjectId();
        if(ObjectUtil.isEmpty(prjId)){
            Hetong hetong = hetongService.getById(param.getHetongId());
            if(ObjectUtil.isNotEmpty(hetong)) prjId = hetong.getBizProjectId();
        }

        List<BizPrjMouVo> result = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(prjId)){
            LambdaQueryWrapper<BizPrjMou> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(BizPrjMou::getPrjId,prjId);
            queryWrapper.in(BizPrjMou::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
            List<BizPrjMou> tmpList = this.list(queryWrapper);
            result = BeanUtil.copyToList(tmpList, BizPrjMouVo.class);
        }
        return result;
    }
}
