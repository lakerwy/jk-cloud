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
package vip.xiaonuo.sys.modular.hr.userApplygoOut.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
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
import vip.xiaonuo.sys.modular.hr.userApplyHoliday.enums.HrUserApplyHolidayEnum;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.entity.HrUserApplyGoout;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.mapper.HrUserApplyGooutMapper;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutAddParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutEditParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutIdParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.param.HrUserApplyGooutPageParam;
import vip.xiaonuo.sys.modular.hr.userApplygoOut.service.HrUserApplyGooutService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户外出申请表Service接口实现类
 *
 * @author ljy
 * @date  2023/11/01 22:53
 **/
@Service
public class HrUserApplyGooutServiceImpl extends ServiceImpl<HrUserApplyGooutMapper, HrUserApplyGoout> implements HrUserApplyGooutService {

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Override
    public Page<HrUserApplyGoout> page(HrUserApplyGooutPageParam hrUserApplyGooutPageParam) {
        QueryWrapper<HrUserApplyGoout> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserApplyGooutPageParam.getApplyUserId())) {
            queryWrapper.lambda().eq(HrUserApplyGoout::getApplyUserId, hrUserApplyGooutPageParam.getApplyUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserApplyGooutPageParam.getProjectId())) {
            queryWrapper.lambda().eq(HrUserApplyGoout::getProjectId, hrUserApplyGooutPageParam.getProjectId());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserApplyGooutPageParam.getSortField(), hrUserApplyGooutPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserApplyGooutPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserApplyGooutPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserApplyGooutPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserApplyGoout::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserApplyGooutAddParam hrUserApplyGooutAddParam) {
        /** 申请流程 */
        HrUserApplyGoout hrUserApplyGoout = BeanUtil.toBean(hrUserApplyGooutAddParam, HrUserApplyGoout.class);
        String processInstanceId = hrUserApplyGooutAddParam.getProcessInstanceId();
        String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
        String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
        hrUserApplyGoout.setId(id);
        hrUserApplyGoout.setModelId(modelId);
        hrUserApplyGoout.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
        FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
        //将流程实例和业务表关联起来
        String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));/**  数据库名一定得是小写*/
        formRelationTableApi.save(modelId, BizCommonEnum.BIZ_AUDIT_LISTS.getBizTable(),id,processInstanceId,dbName);
        hrUserApplyGoout.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());//申请之后，状态修改为审批中

        this.save(hrUserApplyGoout);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserApplyGooutEditParam hrUserApplyGooutEditParam) {
        HrUserApplyGoout hrUserApplyGoout = this.queryEntity(hrUserApplyGooutEditParam.getId());
        BeanUtil.copyProperties(hrUserApplyGooutEditParam, hrUserApplyGoout);
        this.updateById(hrUserApplyGoout);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserApplyGooutIdParam> hrUserApplyGooutIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserApplyGooutIdParamList, HrUserApplyGooutIdParam::getId));
    }

    @Override
    public HrUserApplyGoout detail(HrUserApplyGooutIdParam hrUserApplyGooutIdParam) {
        return this.queryEntity(hrUserApplyGooutIdParam.getId());
    }

    @Override
    public HrUserApplyGoout queryEntity(String id) {
        HrUserApplyGoout hrUserApplyGoout = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserApplyGoout)) {
            throw new CommonException("用户外出申请表不存在，id值为：{}", id);
        }
        return hrUserApplyGoout;
    }

    @Override
    public  Double countTime(HrUserApplyGooutAddParam hrUserApplyGooutAddParam){

        //暂时默认先给个3
        return 3.0;
    }
}
