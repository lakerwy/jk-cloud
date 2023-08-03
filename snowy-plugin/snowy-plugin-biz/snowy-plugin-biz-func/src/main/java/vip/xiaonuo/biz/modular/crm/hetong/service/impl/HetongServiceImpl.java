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
package vip.xiaonuo.biz.modular.crm.hetong.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.crm.hetong.mapper.HetongMapper;
import vip.xiaonuo.biz.modular.crm.hetong.param.HetongIdParam;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongInfoVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.param.HetongAddParam;
import vip.xiaonuo.biz.modular.crm.hetong.param.HetongEditParam;
import vip.xiaonuo.biz.modular.crm.hetong.param.HetongPageParam;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.common.util.CommonSnUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 业务表-CRM-合同Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 14:49
 **/
@DS("slave_1")
@Service
public class HetongServiceImpl extends ServiceImpl<HetongMapper, Hetong> implements HetongService {

    @Resource
    private HetongMapper hetongMapper;
    @Resource
    CommonCacheOperator commonCacheOperator;

    @Override
    public IPage<HetongDetailVo> page(HetongPageParam hetongPageParam) {
        QueryWrapper<Hetong> queryWrapper = new QueryWrapper<>();
        //审核通过的合同
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.STATUS","1");
        if (ObjectUtil.isNotEmpty(hetongPageParam.getSearchKey())) {
            List<String> keys = StrUtil.splitTrim(hetongPageParam.getSearchKey().replace(" ",",").replace("，",","),",");
            if(keys.size() > 1){
                for (String key:keys) {
                    queryWrapper.and(q->q.like("a.PROJECT_NAME",key)
                            .or().like("a.CONTRACT_NO",key));
                }
            }else {
                queryWrapper.like("a.SECOND_LEVEL", hetongPageParam.getSearchKey()).or()
                        .like("a.CONTRACT_NO", hetongPageParam.getSearchKey()).or()
                        .like("a.PROJECT_NAME", hetongPageParam.getSearchKey()).or()
                        .like("a.COUNTERPARTY", hetongPageParam.getSearchKey()).or()
                        .like("a.SECONDPARTY", hetongPageParam.getSearchKey()).or()
                        .like("a.COMPANY_OR_DEPARTMENT", hetongPageParam.getSearchKey()).or()
                        .like("a.PARTYC", hetongPageParam.getSearchKey());
            }
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getStartRegisterDate()) && ObjectUtil.isNotEmpty(hetongPageParam.getEndRegisterDate())) {
            queryWrapper.between("a.REGISTER_DATE", hetongPageParam.getStartRegisterDate(), hetongPageParam.getEndRegisterDate());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getSecondLevel())) {
            queryWrapper.eq("a.SECOND_LEVEL", hetongPageParam.getSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getContractNo())) {
            queryWrapper.like("a.CONTRACT_NO", hetongPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getProjectName())) {
            queryWrapper.like("a.PROJECT_NAME", hetongPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getCounterparty())) {
            queryWrapper.like("a.COUNTERPARTY", hetongPageParam.getCounterparty());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getSecondparty())) {
            queryWrapper.like("a.SECONDPARTY", hetongPageParam.getSecondparty());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getPartnerType())) {
            queryWrapper.eq("a.PARTNER_TYPE", hetongPageParam.getPartnerType());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getCompanyOrDepartment())) {
            queryWrapper.like("a.COMPANY_OR_DEPARTMENT", hetongPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(hetongPageParam.getEndSealDate())) {
            queryWrapper.between("a.SEAL_DATE", hetongPageParam.getStartSealDate(), hetongPageParam.getEndSealDate());
        }
        if(ObjectUtil.isNotEmpty(hetongPageParam.getPartyc())) {
            queryWrapper.like("a.PARTYC", hetongPageParam.getPartyc());
        }
        if(ObjectUtil.isAllNotEmpty(hetongPageParam.getSortField(), hetongPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hetongPageParam.getSortOrder());
            queryWrapper.orderBy(true, hetongPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hetongPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Hetong::getContractNo);
        }
        return hetongMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HetongAddParam hetongAddParam) {
        Hetong hetong = BeanUtil.toBean(hetongAddParam, Hetong.class);
        hetong.setOpt(StpLoginUserUtil.getLoginUser().getName());
        hetong.setRegisterDate(new Date());

        //走合同审批流程
        String processInstanceId = hetongAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            hetong.setId(id);
            hetong.setContractNo(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            hetong.setModelId(modelId);
            hetong.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            formRelationTableApi.save(modelId, BizCommonEnum.PERSONNEL_FILE_MOVES.getBizTable(),id,processInstanceId,dbName);
        }
        else{//不走审批流程
            Hetong tmpht = this.getOne(new QueryWrapper<Hetong>().lambda().orderByDesc(Hetong::getCreateTime).last("limit 1"));
            String newSn = CommonSnUtil.GenerateSn(hetongAddParam.getContractNo(),tmpht==null?"":tmpht.getContractNo(), CommonSnFormatEnum.SNYYYYMM0001.getValue());
            hetong.setContractNo(newSn);
        }
        this.save(hetong);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HetongEditParam hetongEditParam) {
        String id = hetongEditParam.getId();
        //判断是否走合同审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(hetongEditParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(hetongEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                hetongEditParam.setId(id);
            }
        }
        Hetong hetong = this.queryEntity(hetongEditParam.getId());
        BeanUtil.copyProperties(hetongEditParam, hetong);
        this.updateById(hetong);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HetongIdParam> hetongIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hetongIdParamList, HetongIdParam::getId));
    }

    @Override
    public HetongDetailVo detail(HetongIdParam hetongIdParam) {
        return hetongMapper.getDetailById(hetongIdParam.getId());
    }

    @Override
    public Hetong queryEntity(String id) {
        Hetong hetong = this.getById(id);
        if(ObjectUtil.isEmpty(hetong)) {
            throw new CommonException("业务表-CRM-合同不存在，id值为：{}", id);
        }
        return hetong;
    }

    @Override
    public List<HetongInfoVo> getListAll() {
        return hetongMapper.getListAll();
    }
}
