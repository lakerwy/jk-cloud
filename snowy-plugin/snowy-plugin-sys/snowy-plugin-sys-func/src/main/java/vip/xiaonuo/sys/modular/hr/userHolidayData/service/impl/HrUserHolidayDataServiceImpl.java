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
package vip.xiaonuo.sys.modular.hr.userHolidayData.service.impl;

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
import vip.xiaonuo.sys.modular.hr.userHolidayData.entity.HrUserHolidayData;
import vip.xiaonuo.sys.modular.hr.userHolidayData.mapper.HrUserHolidayDataMapper;
import vip.xiaonuo.sys.modular.hr.userHolidayData.param.HrUserHolidayDataAddParam;
import vip.xiaonuo.sys.modular.hr.userHolidayData.param.HrUserHolidayDataEditParam;
import vip.xiaonuo.sys.modular.hr.userHolidayData.param.HrUserHolidayDataIdParam;
import vip.xiaonuo.sys.modular.hr.userHolidayData.param.HrUserHolidayDataPageParam;
import vip.xiaonuo.sys.modular.hr.userHolidayData.service.HrUserHolidayDataService;

import java.util.List;

/**
 * 公司员工假期数据表Service接口实现类
 *
 * @author ljy
 * @date  2023/09/22 16:48
 **/
@Service
public class HrUserHolidayDataServiceImpl extends ServiceImpl<HrUserHolidayDataMapper, HrUserHolidayData> implements HrUserHolidayDataService {

    @Override
    public Page<HrUserHolidayData> page(HrUserHolidayDataPageParam hrUserHolidayDataPageParam) {
        QueryWrapper<HrUserHolidayData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataPageParam.getUserName())) {
            queryWrapper.lambda().eq(HrUserHolidayData::getUserName, hrUserHolidayDataPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataPageParam.getDeparId())) {
            queryWrapper.lambda().eq(HrUserHolidayData::getDeparId, hrUserHolidayDataPageParam.getDeparId());
        }
        if(ObjectUtil.isNotEmpty(hrUserHolidayDataPageParam.getDeparName())) {
            queryWrapper.lambda().eq(HrUserHolidayData::getDeparName, hrUserHolidayDataPageParam.getDeparName());
        }
        if(ObjectUtil.isAllNotEmpty(hrUserHolidayDataPageParam.getSortField(), hrUserHolidayDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrUserHolidayDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrUserHolidayDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrUserHolidayDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrUserHolidayData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrUserHolidayDataAddParam hrUserHolidayDataAddParam) {
        HrUserHolidayData hrUserHolidayData = BeanUtil.toBean(hrUserHolidayDataAddParam, HrUserHolidayData.class);
        this.save(hrUserHolidayData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrUserHolidayDataEditParam hrUserHolidayDataEditParam) {
        HrUserHolidayData hrUserHolidayData = this.queryEntity(hrUserHolidayDataEditParam.getId());
        BeanUtil.copyProperties(hrUserHolidayDataEditParam, hrUserHolidayData);
        this.updateById(hrUserHolidayData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrUserHolidayDataIdParam> hrUserHolidayDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrUserHolidayDataIdParamList, HrUserHolidayDataIdParam::getId));
    }

    @Override
    public HrUserHolidayData detail(HrUserHolidayDataIdParam hrUserHolidayDataIdParam) {
        return this.queryEntity(hrUserHolidayDataIdParam.getId());
    }

    @Override
    public HrUserHolidayData queryEntity(String id) {
        HrUserHolidayData hrUserHolidayData = this.getById(id);
        if(ObjectUtil.isEmpty(hrUserHolidayData)) {
            throw new CommonException("公司员工假期数据表不存在，id值为：{}", id);
        }
        return hrUserHolidayData;
    }
}
