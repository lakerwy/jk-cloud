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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity.HrUserAttendanceConfigGroup;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.mapper.HrUserAttendanceConfigGroupMapper;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param.HrUserAttendanceConfigGroupAddParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param.HrUserAttendanceConfigGroupEditParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param.HrUserAttendanceConfigGroupIdParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param.HrUserAttendanceConfigGroupPageParam;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.service.HrUserAttendanceConfigGroupService;

import java.util.List;

/**
 * 考勤班次组配置表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/21 15:55
 **/
@Service
public class HrUserAttendanceConfigGroupServiceImpl extends ServiceImpl<HrUserAttendanceConfigGroupMapper, HrUserAttendanceConfigGroup> implements HrUserAttendanceConfigGroupService {

    @Override
    public Page<HrUserAttendanceConfigGroup> page(HrUserAttendanceConfigGroupPageParam hrUserAttendanceConfigGroupPageParam) {
        QueryWrapper<HrUserAttendanceConfigGroup> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigGroupPageParam.getAttendanceTypeName())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfigGroup::getAttendanceTypeName, hrUserAttendanceConfigGroupPageParam.getAttendanceTypeName());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigGroupPageParam.getLeadId())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfigGroup::getLeadId, hrUserAttendanceConfigGroupPageParam.getLeadId());
        }
        if(ObjectUtil.isNotEmpty(hrUserAttendanceConfigGroupPageParam.getLeadName())) {
            queryWrapper.lambda().eq(HrUserAttendanceConfigGroup::getLeadName, hrUserAttendanceConfigGroupPageParam.getLeadName());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserAttendanceConfigGroupPageParam.getSortField(), hrUserAttendanceConfigGroupPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserAttendanceConfigGroupPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserAttendanceConfigGroupPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserAttendanceConfigGroupPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserAttendanceConfigGroup::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserAttendanceConfigGroupAddParam hrUserAttendanceConfigGroupAddParam) {
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = BeanUtil.toBean(hrUserAttendanceConfigGroupAddParam, HrUserAttendanceConfigGroup.class);
        this.save(hrUserAttendanceConfigGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserAttendanceConfigGroupEditParam hrUserAttendanceConfigGroupEditParam) {
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = this.queryEntity(hrUserAttendanceConfigGroupEditParam.getId());
        BeanUtil.copyProperties(hrUserAttendanceConfigGroupEditParam, hrUserAttendanceConfigGroup);
        this.updateById(hrUserAttendanceConfigGroup);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserAttendanceConfigGroupIdParam> hrUserAttendanceConfigGroupIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserAttendanceConfigGroupIdParamList, HrUserAttendanceConfigGroupIdParam::getId));
    }

    @Override
    public HrUserAttendanceConfigGroup detail(HrUserAttendanceConfigGroupIdParam hrUserAttendanceConfigGroupIdParam) {
        return this.queryEntity(hrUserAttendanceConfigGroupIdParam.getId());
    }

    @Override
    public HrUserAttendanceConfigGroup queryEntity(String id) {
        HrUserAttendanceConfigGroup hrUserAttendanceConfigGroup = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserAttendanceConfigGroup)) {
            throw new CommonException("考勤班次组配置表不存在，id值为：{}", id);
        }
        return hrUserAttendanceConfigGroup;
    }
}
