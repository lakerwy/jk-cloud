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
package vip.xiaonuo.sys.modular.hr.jkAttendance.service.impl;

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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrAttendanceMachine;
import vip.xiaonuo.sys.modular.hr.jkAttendance.mapper.HrAttendanceMachineMapper;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachineIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrAttendanceMachinePageParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.service.HrAttendanceMachineService;

import java.util.List;

/**
 * 打卡机信息表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/08 14:39
 **/
@Service
public class HrAttendanceMachineServiceImpl extends ServiceImpl<HrAttendanceMachineMapper, HrAttendanceMachine> implements HrAttendanceMachineService {

    @Override
    public Page<HrAttendanceMachine> page(HrAttendanceMachinePageParam hrAttendanceMachinePageParam) {
        QueryWrapper<HrAttendanceMachine> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrAttendanceMachinePageParam.getCode())) {
            queryWrapper.lambda().eq(HrAttendanceMachine::getCode, hrAttendanceMachinePageParam.getCode());
        }
        if(ObjectUtil.isNotEmpty(hrAttendanceMachinePageParam.getTenantId())) {
            queryWrapper.lambda().eq(HrAttendanceMachine::getTenantId, hrAttendanceMachinePageParam.getTenantId());
        }
        if(ObjectUtil.isAllNotEmpty(hrAttendanceMachinePageParam.getSortField(), hrAttendanceMachinePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrAttendanceMachinePageParam.getSortOrder());
            queryWrapper.orderBy(true, hrAttendanceMachinePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrAttendanceMachinePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrAttendanceMachine::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrAttendanceMachineAddParam hrAttendanceMachineAddParam) {
        HrAttendanceMachine hrAttendanceMachine = BeanUtil.toBean(hrAttendanceMachineAddParam, HrAttendanceMachine.class);
        this.save(hrAttendanceMachine);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrAttendanceMachineEditParam hrAttendanceMachineEditParam) {
        HrAttendanceMachine hrAttendanceMachine = this.queryEntity(hrAttendanceMachineEditParam.getId());
        BeanUtil.copyProperties(hrAttendanceMachineEditParam, hrAttendanceMachine);
        this.updateById(hrAttendanceMachine);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrAttendanceMachineIdParam> hrAttendanceMachineIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrAttendanceMachineIdParamList, HrAttendanceMachineIdParam::getId));
    }

    @Override
    public HrAttendanceMachine detail(HrAttendanceMachineIdParam hrAttendanceMachineIdParam) {
        return this.queryEntity(hrAttendanceMachineIdParam.getId());
    }

    @Override
    public HrAttendanceMachine queryEntity(String id) {
        HrAttendanceMachine hrAttendanceMachine = this.getById(id);
        if(ObjectUtil.isEmpty(hrAttendanceMachine)) {
            throw new CommonException("打卡机信息表不存在，id值为：{}", id);
        }
        return hrAttendanceMachine;
    }
}
