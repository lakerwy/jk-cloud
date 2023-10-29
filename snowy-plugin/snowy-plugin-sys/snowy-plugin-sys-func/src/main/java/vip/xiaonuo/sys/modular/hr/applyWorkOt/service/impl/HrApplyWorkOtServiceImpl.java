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
package vip.xiaonuo.sys.modular.hr.applyWorkOt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
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
import vip.xiaonuo.flw.api.FormRelationTableApi;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.entity.HrApplyWorkOt;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.mapper.HrApplyWorkOtMapper;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtAddParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtEditParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtIdParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.param.HrApplyWorkOtPageParam;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.service.HrApplyWorkOtService;
import vip.xiaonuo.sys.modular.hr.applyWorkOt.vo.UserWorkOtVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 考勤-申请加班数据表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/29 19:57
 **/
@Service
public class HrApplyWorkOtServiceImpl extends ServiceImpl<HrApplyWorkOtMapper, HrApplyWorkOt> implements HrApplyWorkOtService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<HrApplyWorkOt> page(HrApplyWorkOtPageParam hrApplyWorkOtPageParam) {
        QueryWrapper<HrApplyWorkOt> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrApplyWorkOtPageParam.getApplyUserId())) {
            queryWrapper.lambda().eq(HrApplyWorkOt::getApplyUserId, hrApplyWorkOtPageParam.getApplyUserId());
        }
        if(ObjectUtil.isNotEmpty(hrApplyWorkOtPageParam.getProjectId())) {
            queryWrapper.lambda().eq(HrApplyWorkOt::getProjectId, hrApplyWorkOtPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(hrApplyWorkOtPageParam.getProjectName())) {
            queryWrapper.lambda().like(HrApplyWorkOt::getProjectName, hrApplyWorkOtPageParam.getProjectName());
        }
        if(ObjectUtil.isAllNotEmpty(hrApplyWorkOtPageParam.getSortField(), hrApplyWorkOtPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrApplyWorkOtPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrApplyWorkOtPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrApplyWorkOtPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrApplyWorkOt::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrApplyWorkOtAddParam hrApplyWorkOtAddParam) {
        /*HrApplyWorkOt hrApplyWorkOt = BeanUtil.toBean(hrApplyWorkOtAddParam, HrApplyWorkOt.class);
        this.save(hrApplyWorkOt);*/
        saveProcessInstance(hrApplyWorkOtAddParam);
    }

    private void saveProcessInstance(HrApplyWorkOtAddParam hrApplyWorkOtAddParam){
        /** 申请流程 */
        HrApplyWorkOt hrApplyWorkOt = BeanUtil.toBean(hrApplyWorkOtAddParam, HrApplyWorkOt.class);
        String processInstanceId = hrApplyWorkOtAddParam.getProcessInstanceId();
        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
        hrApplyWorkOt.setId(id);
        hrApplyWorkOt.setModelId(modelId);
        hrApplyWorkOt.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
        FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
        //将流程实例和业务表关联起来
        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/**  数据库名一定得是小写*/
        formRelationTableApi.save(modelId, BizCommonEnum.BIZ_AUDIT_LISTS.getBizTable(),id,processInstanceId,dbName);
        hrApplyWorkOt.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中

        this.save(hrApplyWorkOt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrApplyWorkOtEditParam hrApplyWorkOtEditParam) {
        HrApplyWorkOt hrApplyWorkOt = this.queryEntity(hrApplyWorkOtEditParam.getId());
        BeanUtil.copyProperties(hrApplyWorkOtEditParam, hrApplyWorkOt);
        this.updateById(hrApplyWorkOt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrApplyWorkOtIdParam> hrApplyWorkOtIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrApplyWorkOtIdParamList, HrApplyWorkOtIdParam::getId));
    }

    @Override
    public HrApplyWorkOt detail(HrApplyWorkOtIdParam hrApplyWorkOtIdParam) {
        return this.queryEntity(hrApplyWorkOtIdParam.getId());
    }

    @Override
    public HrApplyWorkOt queryEntity(String id) {
        HrApplyWorkOt hrApplyWorkOt = this.getById(id);
        if(ObjectUtil.isEmpty(hrApplyWorkOt)) {
            throw new CommonException("考勤-申请加班数据表不存在，id值为：{}", id);
        }
        return hrApplyWorkOt;
    }

    @Override
    public  Page<UserWorkOtVo> userWorkOtVoPage(UserWorkOtVo UserWorkOtVo){
        Page<UserWorkOtVo> page = new Page<>();
        List<HrApplyWorkOt> hrApplyWorkOtList = this.list();
        Long total = this.count(new LambdaQueryWrapper<HrApplyWorkOt>().isNotNull(HrApplyWorkOt::getApplyUserId).groupBy(HrApplyWorkOt::getApplyUserId));
        Map<String,UserWorkOtVo> map = new HashMap<>();
        hrApplyWorkOtList.forEach(workOt -> {
            UserWorkOtVo userWorkOtVo = ObjectUtil.isNotEmpty(map.get(workOt.getApplyUserId())) ? map.get(workOt.getApplyUserId()) : new UserWorkOtVo();
            Double workOtHours = userWorkOtVo.getWorkHours()!= null ?  userWorkOtVo.getWorkHours() : 0.0;
            userWorkOtVo.setApplyUserId(workOt.getApplyUserId());
            userWorkOtVo.setApplyUserName(workOt.getApplyUserName());
            userWorkOtVo.setWorkHours( workOtHours+workOt.getWorkHours());
            map.put(workOt.getApplyUserId(),userWorkOtVo);
        });
        List<UserWorkOtVo> userWorkOtVoList = map.entrySet().stream().map(entry  -> {
            UserWorkOtVo vo = entry.getValue();
            vo.setWorkDays(vo.getWorkHours()/8);
            return vo;
        }).collect(Collectors.toList());
        page.setRecords(userWorkOtVoList);
        page.setTotal(total);
        return page;
    }
}
