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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrCheckAttenanceRule;
import vip.xiaonuo.sys.modular.hr.jkAttendance.mapper.HrCheckAttenanceRuleMapper;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRuleAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRuleEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRuleIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrCheckAttenanceRulePageParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.service.HrCheckAttenanceRuleService;

import java.util.List;

/**
 * 考勤-考勤以及申请类的规则表数据Service接口实现类
 *
 * @author ljy
 * @date  2023/10/29 20:00
 **/
@Service
public class HrCheckAttenanceRuleServiceImpl extends ServiceImpl<HrCheckAttenanceRuleMapper, HrCheckAttenanceRule> implements HrCheckAttenanceRuleService {

    @Override
    public Page<HrCheckAttenanceRule> page(HrCheckAttenanceRulePageParam hrCheckAttenanceRulePageParam) {
        QueryWrapper<HrCheckAttenanceRule> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(hrCheckAttenanceRulePageParam.getSortField(), hrCheckAttenanceRulePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrCheckAttenanceRulePageParam.getSortOrder());
            queryWrapper.orderBy(true, hrCheckAttenanceRulePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrCheckAttenanceRulePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrCheckAttenanceRule::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrCheckAttenanceRuleAddParam hrCheckAttenanceRuleAddParam) {
        HrCheckAttenanceRule hrCheckAttenanceRule = BeanUtil.toBean(hrCheckAttenanceRuleAddParam, HrCheckAttenanceRule.class);
        this.save(hrCheckAttenanceRule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrCheckAttenanceRuleEditParam hrCheckAttenanceRuleEditParam) {
        HrCheckAttenanceRule hrCheckAttenanceRule = this.queryEntity(hrCheckAttenanceRuleEditParam.getId());
        BeanUtil.copyProperties(hrCheckAttenanceRuleEditParam, hrCheckAttenanceRule);
        this.updateById(hrCheckAttenanceRule);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrCheckAttenanceRuleIdParam> hrCheckAttenanceRuleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrCheckAttenanceRuleIdParamList, HrCheckAttenanceRuleIdParam::getId));
    }

    @Override
    public HrCheckAttenanceRule detail(HrCheckAttenanceRuleIdParam hrCheckAttenanceRuleIdParam) {
        return this.queryEntity(hrCheckAttenanceRuleIdParam.getId());
    }

    @Override
    public HrCheckAttenanceRule queryEntity(String id) {
        HrCheckAttenanceRule hrCheckAttenanceRule = this.getById(id);
        if(ObjectUtil.isEmpty(hrCheckAttenanceRule)) {
            throw new CommonException("考勤-考勤以及申请类的规则表数据不存在，id值为：{}", id);
        }
        return hrCheckAttenanceRule;
    }
}
