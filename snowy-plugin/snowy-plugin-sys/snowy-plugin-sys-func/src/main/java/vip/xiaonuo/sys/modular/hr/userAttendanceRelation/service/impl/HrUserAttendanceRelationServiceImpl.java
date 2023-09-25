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
package vip.xiaonuo.sys.modular.hr.userAttendanceRelation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.entity.HrUserAttendanceRelation;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.enums.HrUserAttendanceRelationEnum;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.mapper.HrUserAttendanceRelationMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.param.HrUserAttendanceRelationAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.param.HrUserAttendanceRelationEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.param.HrUserAttendanceRelationIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.param.HrUserAttendanceRelationPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceRelation.service.HrUserAttendanceRelationService;

import java.util.List;

/**
 * 人员考勤班次关联表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/13 14:44
 **/
@Service
public class HrUserAttendanceRelationServiceImpl extends ServiceImpl<HrUserAttendanceRelationMapper, HrUserAttendanceRelation> implements HrUserAttendanceRelationService {

    @Override
    public Page<HrUserAttendanceRelation> page(HrUserAttendanceRelationPageParam hrUserAttendanceRelationPageParam) {
        QueryWrapper<HrUserAttendanceRelation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendanceRelationPageParam.getEmployId())) {
            queryWrapper.lambda().eq(HrUserAttendanceRelation::getEmployId, hrUserAttendanceRelationPageParam.getEmployId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceRelationPageParam.getEmployType())) {
            queryWrapper.lambda().eq(HrUserAttendanceRelation::getEmployType, hrUserAttendanceRelationPageParam.getEmployType());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceRelationPageParam.getAttendanceType())) {
            queryWrapper.lambda().eq(HrUserAttendanceRelation::getAttendanceType, hrUserAttendanceRelationPageParam.getAttendanceType());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendanceRelationPageParam.getSortField(), hrUserAttendanceRelationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendanceRelationPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendanceRelationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendanceRelationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendanceRelation::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String add(HrUserAttendanceRelationAddParam hrUserAttendanceRelationAddParam) {
        if(StringUtils.isEmpty(hrUserAttendanceRelationAddParam.getEmployId()) || StringUtils.isEmpty(hrUserAttendanceRelationAddParam.getAttendanceType())
            || ObjectUtil.isEmpty(hrUserAttendanceRelationAddParam.getBeginTime()) || ObjectUtil.isEmpty(hrUserAttendanceRelationAddParam.getEndTime())){
            return HrUserAttendanceRelationEnum.FAIL.getValue();
        }
        HrUserAttendanceRelation hrUserAttendanceRelation = BeanUtil.toBean(hrUserAttendanceRelationAddParam, HrUserAttendanceRelation.class);
        this.save(hrUserAttendanceRelation);
        return HrUserAttendanceRelationEnum.SUCESS.getValue();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceRelationEditParam hrUserAttendanceRelationEditParam) {
        HrUserAttendanceRelation hrUserAttendanceRelation = this.queryEntity(hrUserAttendanceRelationEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceRelationEditParam, hrUserAttendanceRelation);
        this.updateById(hrUserAttendanceRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceRelationIdParam> hrUserAttendanceRelationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceRelationIdParamList, HrUserAttendanceRelationIdParam::getId));
    }

    @Override
    public HrUserAttendanceRelation detail(HrUserAttendanceRelationIdParam hrUserAttendanceRelationIdParam) {
        return this.queryEntity(hrUserAttendanceRelationIdParam.getId());
    }

    @Override
    public HrUserAttendanceRelation queryEntity(String id) {
        HrUserAttendanceRelation hrUserAttendanceRelation = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendanceRelation)) {
            throw new CommonException("人员考勤班次关联表不存在，id值为：{}", id);
        }
        return hrUserAttendanceRelation;
    }
}
