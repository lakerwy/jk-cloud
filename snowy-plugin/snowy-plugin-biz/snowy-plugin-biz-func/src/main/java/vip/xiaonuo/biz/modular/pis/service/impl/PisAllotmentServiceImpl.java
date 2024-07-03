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
package vip.xiaonuo.biz.modular.pis.service.impl;

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
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.pis.entity.PisAllotment;
import vip.xiaonuo.biz.modular.pis.mapper.PisAllotmentMapper;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentAddParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentEditParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentIdParam;
import vip.xiaonuo.biz.modular.pis.param.PisAllotmentPageParam;
import vip.xiaonuo.biz.modular.pis.service.PisAllotmentService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 生产院及子公司项目拨付款 - 申请流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/03 15:29
 **/
@Service
@DS("slave_1")
public class PisAllotmentServiceImpl extends ServiceImpl<PisAllotmentMapper, PisAllotment> implements PisAllotmentService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<PisAllotment> page(PisAllotmentPageParam pisAllotmentPageParam) {
        QueryWrapper<PisAllotment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(PisAllotment::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue(),FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getDept())) {
            queryWrapper.lambda().like(PisAllotment::getDept, pisAllotmentPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getPrjOwner())) {
            queryWrapper.lambda().like(PisAllotment::getPrjOwner, pisAllotmentPageParam.getPrjOwner());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getPrjManager())) {
            queryWrapper.lambda().like(PisAllotment::getPrjManager, pisAllotmentPageParam.getPrjManager());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getPrjNature())) {
            queryWrapper.lambda().eq(PisAllotment::getPrjNature, pisAllotmentPageParam.getPrjNature());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getHeadUnit())) {
            queryWrapper.lambda().like(PisAllotment::getHeadUnit, pisAllotmentPageParam.getHeadUnit());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getHtProjectName())) {
            queryWrapper.lambda().like(PisAllotment::getHtProjectName, pisAllotmentPageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getHtContractNo())) {
            queryWrapper.lambda().like(PisAllotment::getHtContractNo, pisAllotmentPageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getSn())) {
            queryWrapper.lambda().like(PisAllotment::getSn, pisAllotmentPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getStatus())) {
            queryWrapper.lambda().eq(PisAllotment::getStatus, pisAllotmentPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(pisAllotmentPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(PisAllotment::getSn,pisAllotmentPageParam.getSearchKey())
                    .or().like(PisAllotment::getDept,pisAllotmentPageParam.getSearchKey())
                    .or().like(PisAllotment::getPrjOwner,pisAllotmentPageParam.getSearchKey())
                    .or().like(PisAllotment::getPrjManager,pisAllotmentPageParam.getSearchKey())
                    .or().like(PisAllotment::getHtContractNo,pisAllotmentPageParam.getSearchKey())
                    .or().like(PisAllotment::getHtProjectName,pisAllotmentPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(pisAllotmentPageParam.getSortField(), pisAllotmentPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(pisAllotmentPageParam.getSortOrder());
            queryWrapper.orderBy(true, pisAllotmentPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(pisAllotmentPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PisAllotment::getSn);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PisAllotmentAddParam pisAllotmentAddParam) {
        PisAllotment pisAllotment = BeanUtil.toBean(pisAllotmentAddParam, PisAllotment.class);
        //走审批流程
        String processInstanceId = pisAllotmentAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            pisAllotment.setId(id);
            pisAllotment.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            pisAllotment.setModelId(modelId);
            pisAllotment.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_PIS_ALLOTMENT.getBizTable(),id,processInstanceId,dbName);
        }else{
            pisAllotment.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(pisAllotment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PisAllotmentEditParam pisAllotmentEditParam) {
        String id = pisAllotmentEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(pisAllotmentEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(pisAllotmentEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                pisAllotmentEditParam.setId(id);
            }
        }
        PisAllotment pisAllotment = this.queryEntity(pisAllotmentEditParam.getId());
        BeanUtil.copyProperties(pisAllotmentEditParam, pisAllotment);
        this.updateById(pisAllotment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PisAllotmentIdParam> pisAllotmentIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(pisAllotmentIdParamList, PisAllotmentIdParam::getId));
    }

    @Override
    public PisAllotment detail(PisAllotmentIdParam pisAllotmentIdParam) {
        return this.queryEntity(pisAllotmentIdParam.getId());
    }

    @Override
    public PisAllotment queryEntity(String id) {
        PisAllotment pisAllotment = this.getById(id);
        if(ObjectUtil.isEmpty(pisAllotment)) {
            throw new CommonException("生产院及子公司项目拨付款 - 申请流程不存在，id值为：{}", id);
        }
        return pisAllotment;
    }

    @Override
    public PisAllotment getByHtId(String htId) {
        LambdaQueryWrapper<PisAllotment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PisAllotment::getHtId,htId)
                .eq(PisAllotment::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue())
                .orderByDesc(PisAllotment::getCreateTime).last(" limit 1 ");
        PisAllotment pisAllotment = this.getOne(lambdaQueryWrapper);
        return pisAllotment;
    }
}
