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
package vip.xiaonuo.sys.modular.hr.social.service.impl;

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
import vip.xiaonuo.sys.modular.hr.social.entity.SocialInterview;
import vip.xiaonuo.sys.modular.hr.social.mapper.SocialInterviewMapper;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewAddParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewEditParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewIdParam;
import vip.xiaonuo.sys.modular.hr.social.param.SocialInterviewPageParam;
import vip.xiaonuo.sys.modular.hr.social.service.SocialInterviewService;
import vip.xiaonuo.sys.modular.hr.social.vo.SocialInterviewVo;

import javax.annotation.Resource;
import java.util.List;

/**
 * 社招 - （应聘者）- 面试入职流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/01 09:36
 **/
@Service
public class SocialInterviewServiceImpl extends ServiceImpl<SocialInterviewMapper, SocialInterview> implements SocialInterviewService {

    @Resource
    private SocialInterviewMapper socialInterviewMapper;
    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<SocialInterviewVo> page(SocialInterviewPageParam socialInterviewPageParam) {
        QueryWrapper<SocialInterview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("a.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getDeptId())) {
            queryWrapper.eq("a.DEPT_ID", socialInterviewPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getDept())) {
            queryWrapper.like("a.DEPT", socialInterviewPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getPosition())) {
            queryWrapper.like("a.POSITION", socialInterviewPageParam.getPosition());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getReasult())) {
            queryWrapper.eq("a.REASULT", socialInterviewPageParam.getReasult());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getSn())) {
            queryWrapper.like("a.SN", socialInterviewPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getStatus())) {
            queryWrapper.eq("a.STATUS", socialInterviewPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getName())){
            queryWrapper.like("b.NAME",socialInterviewPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getPhone())){
            queryWrapper.like("b.PHONE",socialInterviewPageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(socialInterviewPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.SN",socialInterviewPageParam.getSearchKey())
                    .or().like("a.DEPT",socialInterviewPageParam.getSearchKey())
                    .or().like("a.POSITION",socialInterviewPageParam.getSearchKey())
                    .or().like("b.NAME",socialInterviewPageParam.getSearchKey())
                    .or().like("a.PHONE",socialInterviewPageParam.getPhone()));
        }
        if(ObjectUtil.isAllNotEmpty(socialInterviewPageParam.getSortField(), socialInterviewPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(socialInterviewPageParam.getSortOrder());
            queryWrapper.orderBy(true, socialInterviewPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(socialInterviewPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.SN");
        }
        return socialInterviewMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SocialInterviewAddParam socialInterviewAddParam) {
        SocialInterview socialInterview = BeanUtil.toBean(socialInterviewAddParam, SocialInterview.class);
        //走审批流程
        String processInstanceId = socialInterviewAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            socialInterview.setId(id);
            socialInterview.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            socialInterview.setModelId(modelId);
            socialInterview.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_SOCIAL_INTERVIEW.getBizTable(),id,processInstanceId,dbName);
        }else{
            socialInterview.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(socialInterview);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SocialInterviewEditParam socialInterviewEditParam) {
        String id = socialInterviewEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(socialInterviewEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(socialInterviewEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                socialInterviewEditParam.setId(id);
            }
        }
        SocialInterview socialInterview = this.queryEntity(socialInterviewEditParam.getId());
        BeanUtil.copyProperties(socialInterviewEditParam, socialInterview);
        this.updateById(socialInterview);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SocialInterviewIdParam> socialInterviewIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(socialInterviewIdParamList, SocialInterviewIdParam::getId));
    }

    @Override
    public SocialInterviewVo detail(SocialInterviewIdParam socialInterviewIdParam) {
        return socialInterviewMapper.getDetail(socialInterviewIdParam.getId());
    }

    @Override
    public SocialInterview queryEntity(String id) {
        SocialInterview socialInterview = this.getById(id);
        if(ObjectUtil.isEmpty(socialInterview)) {
            throw new CommonException("社招 - （应聘者）- 面试入职流程不存在，id值为：{}", id);
        }
        return socialInterview;
    }
}
