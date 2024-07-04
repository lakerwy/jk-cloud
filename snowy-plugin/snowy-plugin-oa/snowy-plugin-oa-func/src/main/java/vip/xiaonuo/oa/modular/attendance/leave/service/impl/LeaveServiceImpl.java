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
package vip.xiaonuo.oa.modular.attendance.leave.service.impl;

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
import vip.xiaonuo.oa.modular.attendance.leave.mapper.LeaveMapper;
import vip.xiaonuo.oa.modular.attendance.leave.service.LeaveService;
import vip.xiaonuo.oa.modular.attendance.leave.entity.Leave;
import vip.xiaonuo.oa.modular.attendance.leave.mapper.LeaveMapper;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeaveAddParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeaveEditParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeaveIdParam;
import vip.xiaonuo.oa.modular.attendance.leave.param.LeavePageParam;
import vip.xiaonuo.oa.modular.attendance.leave.service.LeaveService;

import java.util.List;

/**
 * 请假记录表Service接口实现类
 *
 * @author mayike
 * @date  2024/06/24 16:55
 **/
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements LeaveService {

    @Override
    public Page<Leave> page(LeavePageParam leavePageParam) {
        QueryWrapper<Leave> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(leavePageParam.getLeaveType())) {
            queryWrapper.lambda().like(Leave::getLeaveType, leavePageParam.getLeaveType());
        }
        if(ObjectUtil.isAllNotEmpty(leavePageParam.getSortField(), leavePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(leavePageParam.getSortOrder());
            queryWrapper.orderBy(true, leavePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(leavePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Leave::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(LeaveAddParam leaveAddParam) {
        Leave leave = BeanUtil.toBean(leaveAddParam, Leave.class);
        this.save(leave);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(LeaveEditParam leaveEditParam) {
        Leave leave = this.queryEntity(leaveEditParam.getId());
        BeanUtil.copyProperties(leaveEditParam, leave);
        this.updateById(leave);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<LeaveIdParam> leaveIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(leaveIdParamList, LeaveIdParam::getId));
    }

    @Override
    public Leave detail(LeaveIdParam leaveIdParam) {
        return this.queryEntity(leaveIdParam.getId());
    }

    @Override
    public Leave queryEntity(String id) {
        Leave leave = this.getById(id);
        if(ObjectUtil.isEmpty(leave)) {
            throw new CommonException("请假记录表不存在，id值为：{}", id);
        }
        return leave;
    }
}
