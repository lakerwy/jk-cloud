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
package vip.xiaonuo.sys.modular.hr.userHolidayDataRec.service.impl;

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
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.entity.HrUserHolidayDataRec;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.mapper.HrUserHolidayDataRecMapper;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecAddParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecEditParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecIdParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.param.HrUserHolidayDataRecPageParam;
import vip.xiaonuo.sys.modular.hr.userHolidayDataRec.service.HrUserHolidayDataRecService;

import java.util.List;

/**
 * 公司员工假期增减详情数据Service接口实现类
 *
 * @author ljy
 * @date  2023/10/26 09:21
 **/
@Service
public class HrUserHolidayDataRecServiceImpl extends ServiceImpl<HrUserHolidayDataRecMapper, HrUserHolidayDataRec> implements HrUserHolidayDataRecService {

    @Override
    public Page<HrUserHolidayDataRec> page(HrUserHolidayDataRecPageParam hrUserHolidayDataRecPageParam) {
        QueryWrapper<HrUserHolidayDataRec> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataRecPageParam.getUserId())) {
            queryWrapper.lambda().eq(HrUserHolidayDataRec::getUserId, hrUserHolidayDataRecPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataRecPageParam.getUserName())) {
            queryWrapper.lambda().eq(HrUserHolidayDataRec::getUserName, hrUserHolidayDataRecPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataRecPageParam.getApplyType())) {
            queryWrapper.lambda().eq(HrUserHolidayDataRec::getApplyType, hrUserHolidayDataRecPageParam.getApplyType());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserHolidayDataRecPageParam.getSortField(), hrUserHolidayDataRecPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserHolidayDataRecPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserHolidayDataRecPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserHolidayDataRecPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserHolidayDataRec::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserHolidayDataRecAddParam hrUserHolidayDataRecAddParam) {
        HrUserHolidayDataRec hrUserHolidayDataRec = BeanUtil.toBean(hrUserHolidayDataRecAddParam, HrUserHolidayDataRec.class);
        this.save(hrUserHolidayDataRec);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserHolidayDataRecEditParam hrUserHolidayDataRecEditParam) {
        HrUserHolidayDataRec hrUserHolidayDataRec = this.queryEntity(hrUserHolidayDataRecEditParam.getId());
        BeanUtil.copyProperties(hrUserHolidayDataRecEditParam, hrUserHolidayDataRec);
        this.updateById(hrUserHolidayDataRec);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserHolidayDataRecIdParam> hrUserHolidayDataRecIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserHolidayDataRecIdParamList, HrUserHolidayDataRecIdParam::getId));
    }

    @Override
    public HrUserHolidayDataRec detail(HrUserHolidayDataRecIdParam hrUserHolidayDataRecIdParam) {
        return this.queryEntity(hrUserHolidayDataRecIdParam.getId());
    }

    @Override
    public HrUserHolidayDataRec queryEntity(String id) {
        HrUserHolidayDataRec hrUserHolidayDataRec = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserHolidayDataRec)) {
            throw new CommonException("公司员工假期增减详情数据不存在，id值为：{}", id);
        }
        return hrUserHolidayDataRec;
    }
}
