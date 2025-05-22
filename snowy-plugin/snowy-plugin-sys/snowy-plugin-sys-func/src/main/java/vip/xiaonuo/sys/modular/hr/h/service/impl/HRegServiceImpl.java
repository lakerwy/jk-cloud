package vip.xiaonuo.sys.modular.hr.h.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import vip.xiaonuo.sys.modular.hr.h.entity.HReg;
import vip.xiaonuo.sys.modular.hr.h.mapper.HRegMapper;
import vip.xiaonuo.sys.modular.hr.h.param.HRegAddParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegEditParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegIdParam;
import vip.xiaonuo.sys.modular.hr.h.param.HRegPageParam;
import vip.xiaonuo.sys.modular.hr.h.service.HRegService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 招待事项登记Service接口实现类
 *
 * @author yy
 * @date  2025/05/06 16:11
 **/
@Service
public class HRegServiceImpl extends ServiceImpl<HRegMapper, HReg> implements HRegService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<HReg> page(HRegPageParam hRegPageParam) {
        QueryWrapper<HReg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(HReg::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(hRegPageParam.getDeptId())) {
            queryWrapper.lambda().eq(HReg::getDeptId, hRegPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getDeptName())) {
            queryWrapper.lambda().like(HReg::getDeptName, hRegPageParam.getDeptName());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getOptId())) {
            queryWrapper.lambda().eq(HReg::getOptId, hRegPageParam.getOptId());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getOptName())) {
            queryWrapper.lambda().like(HReg::getOptName, hRegPageParam.getOptName());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getStartOptTime()) && ObjectUtil.isNotEmpty(hRegPageParam.getEndOptTime())) {
            queryWrapper.lambda().between(HReg::getOptTime, hRegPageParam.getStartOptTime(), hRegPageParam.getEndOptTime());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getStartHDate()) && ObjectUtil.isNotEmpty(hRegPageParam.getEndHDate())) {
            queryWrapper.lambda().between(HReg::getHDate, hRegPageParam.getStartHDate(), hRegPageParam.getEndHDate());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getDescMemo())) {
            queryWrapper.lambda().like(HReg::getDescMemo, hRegPageParam.getDescMemo());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getJoinJosn())) {
            queryWrapper.lambda().like(HReg::getJoinJosn, hRegPageParam.getJoinJosn());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getSn())) {
            queryWrapper.lambda().like(HReg::getSn, hRegPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getStatus())) {
            queryWrapper.lambda().eq(HReg::getStatus, hRegPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getType())){
            queryWrapper.lambda().eq(HReg::getType,hRegPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(HReg::getDescMemo,hRegPageParam.getSearchKey())
                    .or().like(HReg::getJoinJosn,hRegPageParam.getSearchKey()));
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(HReg::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(HReg::getCreateUser, StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(hRegPageParam.getSortField(), hRegPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hRegPageParam.getSortOrder());
            queryWrapper.orderBy(true, hRegPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hRegPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(HReg::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HRegAddParam hRegAddParam) {
        HReg hReg = BeanUtil.toBean(hRegAddParam, HReg.class);

        //走审批流程
        String processInstanceId = hRegAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            hReg.setId(id);
            hReg.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            hReg.setModelId(modelId);
            hReg.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_H_REG.getBizTable(),id,processInstanceId,dbName);
        }else{
            hReg.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        hReg.setOptId(StpLoginUserUtil.getLoginUser().getId());
        hReg.setOptName(StpLoginUserUtil.getLoginUser().getName());
        hReg.setOptTime(new Date());
        this.save(hReg);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HRegEditParam hRegEditParam) {
        String id = hRegEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(hRegEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(hRegEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                hRegEditParam.setId(id);
            }
        }

        HReg hReg = this.queryEntity(hRegEditParam.getId());
        BeanUtil.copyProperties(hRegEditParam, hReg);
        this.updateById(hReg);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HRegIdParam> hRegIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hRegIdParamList, HRegIdParam::getId));
    }

    @Override
    public HReg detail(HRegIdParam hRegIdParam) {
        return this.queryEntity(hRegIdParam.getId());
    }

    @Override
    public HReg queryEntity(String id) {
        HReg hReg = this.getById(id);
        if(ObjectUtil.isEmpty(hReg)) {
            throw new CommonException("招待事项登记不存在，id值为：{}", id);
        }
        return hReg;
    }

    @Override
    public Page<HReg> pageList(HRegPageParam hRegPageParam) {
        QueryWrapper<HReg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(HReg::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(hRegPageParam.getDeptId())) {
            queryWrapper.lambda().eq(HReg::getDeptId, hRegPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getDeptName())) {
            queryWrapper.lambda().like(HReg::getDeptName, hRegPageParam.getDeptName());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getOptId())) {
            queryWrapper.lambda().eq(HReg::getOptId, hRegPageParam.getOptId());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getOptName())) {
            queryWrapper.lambda().like(HReg::getOptName, hRegPageParam.getOptName());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getStartOptTime()) && ObjectUtil.isNotEmpty(hRegPageParam.getEndOptTime())) {
            queryWrapper.lambda().between(HReg::getOptTime, hRegPageParam.getStartOptTime(), hRegPageParam.getEndOptTime());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getStartHDate()) && ObjectUtil.isNotEmpty(hRegPageParam.getEndHDate())) {
            queryWrapper.lambda().between(HReg::getHDate, hRegPageParam.getStartHDate(), hRegPageParam.getEndHDate());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getDescMemo())) {
            queryWrapper.lambda().like(HReg::getDescMemo, hRegPageParam.getDescMemo());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getJoinJosn())) {
            queryWrapper.lambda().like(HReg::getJoinJosn, hRegPageParam.getJoinJosn());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getSn())) {
            queryWrapper.lambda().like(HReg::getSn, hRegPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getStatus())) {
            queryWrapper.lambda().eq(HReg::getStatus, hRegPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getType())){
            queryWrapper.lambda().eq(HReg::getType,hRegPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(hRegPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(HReg::getSn,hRegPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(hRegPageParam.getSortField(), hRegPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hRegPageParam.getSortOrder());
            queryWrapper.orderBy(true, hRegPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hRegPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(HReg::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
