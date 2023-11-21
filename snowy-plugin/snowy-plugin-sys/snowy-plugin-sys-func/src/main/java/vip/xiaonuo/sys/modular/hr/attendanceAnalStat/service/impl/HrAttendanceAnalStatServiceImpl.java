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
package vip.xiaonuo.sys.modular.hr.attendanceAnalStat.service.impl;

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
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.entity.HrAttendanceAnalStat;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.mapper.HrAttendanceAnalStatMapper;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatAddParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatEditParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatIdParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.param.HrAttendanceAnalStatPageParam;
import vip.xiaonuo.sys.modular.hr.attendanceAnalStat.service.HrAttendanceAnalStatService;

import java.util.List;

/**
 * 考勤-员工考勤统计分析数据汇总表Service接口实现类
 *
 * @author ljy
 * @date  2023/11/21 23:04
 **/
@Service
public class HrAttendanceAnalStatServiceImpl extends ServiceImpl<HrAttendanceAnalStatMapper, HrAttendanceAnalStat> implements HrAttendanceAnalStatService {

    @Override
    public Page<HrAttendanceAnalStat> page(HrAttendanceAnalStatPageParam hrAttendanceAnalStatPageParam) {
        QueryWrapper<HrAttendanceAnalStat> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrAttendanceAnalStatPageParam.getUserId())) {
            queryWrapper.lambda().eq(HrAttendanceAnalStat::getUserId, hrAttendanceAnalStatPageParam.getUserId());
        }
        if(ObjectUtil.isAllNotEmpty(hrAttendanceAnalStatPageParam.getSortField(), hrAttendanceAnalStatPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrAttendanceAnalStatPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrAttendanceAnalStatPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrAttendanceAnalStatPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrAttendanceAnalStat::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrAttendanceAnalStatAddParam hrAttendanceAnalStatAddParam) {
        HrAttendanceAnalStat hrAttendanceAnalStat = BeanUtil.toBean(hrAttendanceAnalStatAddParam, HrAttendanceAnalStat.class);
        this.save(hrAttendanceAnalStat);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrAttendanceAnalStatEditParam hrAttendanceAnalStatEditParam) {
        HrAttendanceAnalStat hrAttendanceAnalStat = this.queryEntity(hrAttendanceAnalStatEditParam.getId());
        BeanUtil.copyProperties(hrAttendanceAnalStatEditParam, hrAttendanceAnalStat);
        this.updateById(hrAttendanceAnalStat);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrAttendanceAnalStatIdParam> hrAttendanceAnalStatIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrAttendanceAnalStatIdParamList, HrAttendanceAnalStatIdParam::getId));
    }

    @Override
    public HrAttendanceAnalStat detail(HrAttendanceAnalStatIdParam hrAttendanceAnalStatIdParam) {
        return this.queryEntity(hrAttendanceAnalStatIdParam.getId());
    }

    @Override
    public HrAttendanceAnalStat queryEntity(String id) {
        HrAttendanceAnalStat hrAttendanceAnalStat = this.getById(id);
        if(ObjectUtil.isEmpty(hrAttendanceAnalStat)) {
            throw new CommonException("考勤-员工考勤统计分析数据汇总表不存在，id值为：{}", id);
        }
        return hrAttendanceAnalStat;
    }
}
