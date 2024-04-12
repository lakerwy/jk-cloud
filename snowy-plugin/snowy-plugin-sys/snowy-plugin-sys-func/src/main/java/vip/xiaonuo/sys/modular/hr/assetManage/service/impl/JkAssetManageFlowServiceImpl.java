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
package vip.xiaonuo.sys.modular.hr.assetManage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManage;
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManageFlow;
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManageRelation;
import vip.xiaonuo.sys.modular.hr.assetManage.mapper.JkAssetManageFlowMapper;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageFlowAddParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageFlowEditParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageFlowIdParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageFlowPageParam;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageFlowService;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageRelationService;
import vip.xiaonuo.sys.modular.hr.assetManage.service.JkAssetManageService;
import vip.xiaonuo.sys.modular.hr.assetManage.vo.JkAssetManageRelationVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 建科资产管理权限申请表Service接口实现类
 *
 * @author ljy
 * @date  2024/04/10 08:56
 **/
@Service
public class JkAssetManageFlowServiceImpl extends ServiceImpl<JkAssetManageFlowMapper, JkAssetManageFlow> implements JkAssetManageFlowService {

    @Resource
    CommonCacheOperator commonCacheOperator;
    @Resource
    JkAssetManageService jkAssetManageService;
    @Resource
    JkAssetManageRelationService jkAssetManageRelationService;

    @Override
    public Page<JkAssetManageFlow> page(JkAssetManageFlowPageParam jkAssetManageFlowPageParam) {
        QueryWrapper<JkAssetManageFlow> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(jkAssetManageFlowPageParam.getUserId())) {
            queryWrapper.lambda().eq(JkAssetManageFlow::getUserId, jkAssetManageFlowPageParam.getUserId());
        }
        if(ObjectUtil.isAllNotEmpty(jkAssetManageFlowPageParam.getSortField(), jkAssetManageFlowPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(jkAssetManageFlowPageParam.getSortOrder());
            queryWrapper.orderBy(true, jkAssetManageFlowPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(jkAssetManageFlowPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(JkAssetManageFlow::getId);
        }
        //Page<JkAssetManageFlow> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        //getJkManageData(page);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    /*private void getJkManageData(Page<JkAssetManageFlow> page){
        if(CollUtil.isNotEmpty(page.getRecords())){
            List<String> assetIds = page.getRecords().stream().map(data -> data.getAssetId()).collect(Collectors.toList());
            List<JkAssetManage> jkAssetManageList = jkAssetManageService.list(new LambdaQueryWrapper<JkAssetManage>().in(JkAssetManage::getId,assetIds));
            Map<String,JkAssetManage> map = new HashMap<>();
            jkAssetManageList.forEach(asset -> map.put(asset.getId(),asset));
            page.getRecords().forEach(data -> {
                if(ObjectUtil.isNotEmpty(map.get(data.getAssetId()))){data.setJkAssetManage(map.get(data.getAssetId()));}
            });
        }
    }*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(JkAssetManageFlowAddParam jkAssetManageFlowAddParam) {
        JkAssetManageFlow jkAssetManageFlow = BeanUtil.toBean(jkAssetManageFlowAddParam, JkAssetManageFlow.class);
        String processInstanceId = jkAssetManageFlowAddParam.getProcessInstanceId();
        String userId = StpLoginUserUtil.getLoginUser().getId();
        /** processInstanceId区分是否为申请，还是手动修改 */
        if(StringUtils.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            jkAssetManageFlow.setId(id);
            jkAssetManageFlow.setModelId(modelId);
            jkAssetManageFlow.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/** 数据库名一定得是小写*/
            flwCommonApi.save(modelId, BizCommonEnum.JK_ASSET_MANAGE_FLOW.getBizTable(),id,processInstanceId,dbName);
            jkAssetManageFlow.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中
            this.save(jkAssetManageFlow);

            if(CollUtil.isNotEmpty(jkAssetManageFlowAddParam.getJkAssetManageRelationList())){
                List<JkAssetManageRelation> jkAssetManageRelationList = new ArrayList<>();
                jkAssetManageFlowAddParam.getJkAssetManageRelationList().forEach(relation -> {
                    JkAssetManageRelation jkAssetManageRelation = new JkAssetManageRelation();
                    jkAssetManageRelation.setAssetId(relation.getId());//资产管理ID
                    jkAssetManageRelation.setFlowId(processInstanceId);
                    jkAssetManageRelation.setUserId(userId);
                    if(StringUtils.isNotEmpty(relation.getIsPass())){jkAssetManageRelation.setIsPass(relation.getIsPass());}
                    jkAssetManageRelationList.add(jkAssetManageRelation);
                });
                jkAssetManageRelationService.saveBatch(jkAssetManageRelationList);
            }
            return;
        }
        /**  预留手动修改 */
        if(StringUtils.isEmpty(processInstanceId)){
            if(CollUtil.isNotEmpty(jkAssetManageFlowAddParam.getJkAssetManageRelationList())){
                List<JkAssetManageRelation> jkAssetManageRelationList = new ArrayList<>();
                jkAssetManageFlowAddParam.getJkAssetManageRelationList().forEach(relation -> {
                    JkAssetManageRelation jkAssetManageRelation = BeanUtil.toBean(relation,JkAssetManageRelation.class);
                    jkAssetManageRelationList.add(jkAssetManageRelation);
                });
                jkAssetManageRelationService.saveBatch(jkAssetManageRelationList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(JkAssetManageFlowEditParam jkAssetManageFlowEditParam) {
        JkAssetManageFlow jkAssetManageFlow = this.queryEntity(jkAssetManageFlowEditParam.getId());
        BeanUtil.copyProperties(jkAssetManageFlowEditParam, jkAssetManageFlow);
        List<JkAssetManageRelation> jkAssetManageRelationList = jkAssetManageFlowEditParam.getJkAssetManageRelationList();
        List<JkAssetManageRelation> saves = new ArrayList<>();
        jkAssetManageRelationList.forEach(relation -> {
            /*relation.setId(null);
            relation.setFlowId(jkAssetManageFlow.getProcessInstanceId());*/
            JkAssetManageRelation jkAssetManageRelation = new JkAssetManageRelation();
            jkAssetManageRelation.setAssetId(relation.getId());//资产管理ID
            jkAssetManageRelation.setFlowId(relation.getFlowId());
            jkAssetManageRelation.setUserId(relation.getUserId());
            if(StringUtils.isNotEmpty(relation.getIsPass())){jkAssetManageRelation.setIsPass(relation.getIsPass());}
            saves.add(jkAssetManageRelation);
        });
        deleteHis(jkAssetManageFlowEditParam);
        jkAssetManageRelationService.saveBatch(saves);
        this.updateById(jkAssetManageFlow);
    }

    /**  暂定查询历史数据，并一个个对比，逻辑繁琐复杂，直接把历史数据删掉，然后保存最新的 */
    private void deleteHis(JkAssetManageFlowEditParam jkAssetManageFlowEditParam){
        List<JkAssetManageRelation> jkAssetManageRelationList = jkAssetManageRelationService.list(new LambdaQueryWrapper<JkAssetManageRelation>()
                .in(JkAssetManageRelation::getFlowId,jkAssetManageFlowEditParam.getProcessInstanceId()));
        if(CollUtil.isNotEmpty(jkAssetManageRelationList)){
            List<String> relationIds = new ArrayList<>();
            jkAssetManageRelationList.forEach(relation ->relationIds.add(relation.getId()));
            jkAssetManageRelationService.removeByIds(relationIds);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<JkAssetManageFlowIdParam> jkAssetManageFlowIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(jkAssetManageFlowIdParamList, JkAssetManageFlowIdParam::getId));
    }

    @Override
    public JkAssetManageFlow detail(JkAssetManageFlowIdParam jkAssetManageFlowIdParam) {
        JkAssetManageFlow jkAssetManageFlow = this.queryEntity(jkAssetManageFlowIdParam.getId());
        getJkAssetManage(jkAssetManageFlow);
        return jkAssetManageFlow;
    }
    private void getJkAssetManage(JkAssetManageFlow jkAssetManageFlow){
        List<JkAssetManageRelation> jkAssetManageRelationList = jkAssetManageRelationService.list(new LambdaQueryWrapper<JkAssetManageRelation>()
                .eq(JkAssetManageRelation::getFlowId,jkAssetManageFlow.getProcessInstanceId()));
        if(CollUtil.isNotEmpty(jkAssetManageRelationList)){
            List<String> assetIds = jkAssetManageRelationList.stream().map(rela -> rela.getAssetId()).collect(Collectors.toList());
            jkAssetManageFlow.setJkAssetManageList(jkAssetManageService.list(new LambdaQueryWrapper<JkAssetManage>().in(JkAssetManage::getId,assetIds)));
        }
    }

    @Override
    public JkAssetManageFlow queryEntity(String id) {
        JkAssetManageFlow jkAssetManageFlow = this.getById(id);
        if(ObjectUtil.isEmpty(jkAssetManageFlow)) {
            throw new CommonException("建科资产管理权限申请表不存在，id值为：{}", id);
        }
        return jkAssetManageFlow;
    }
}
