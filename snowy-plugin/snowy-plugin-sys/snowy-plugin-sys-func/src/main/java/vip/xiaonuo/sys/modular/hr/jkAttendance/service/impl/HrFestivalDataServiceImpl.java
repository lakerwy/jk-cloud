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
import vip.xiaonuo.sys.modular.hr.jkAttendance.entity.HrFestivalData;
import vip.xiaonuo.sys.modular.hr.jkAttendance.mapper.HrFestivalDataMapper;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrFestivalDataAddParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrFestivalDataEditParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrFestivalDataIdParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.param.HrFestivalDataPageParam;
import vip.xiaonuo.sys.modular.hr.jkAttendance.service.HrFestivalDataService;

import java.util.List;

/**
 * 法定节假日数据Service接口实现类
 *
 * @author ljy
 * @date  2023/10/23 17:55
 **/
@Service
public class HrFestivalDataServiceImpl extends ServiceImpl<HrFestivalDataMapper, HrFestivalData> implements HrFestivalDataService {

    @Override
    public Page<HrFestivalData> page(HrFestivalDataPageParam hrFestivalDataPageParam) {
        QueryWrapper<HrFestivalData> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(hrFestivalDataPageParam.getSortField(), hrFestivalDataPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrFestivalDataPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrFestivalDataPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrFestivalDataPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrFestivalData::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrFestivalDataAddParam hrFestivalDataAddParam) {
        HrFestivalData hrFestivalData = BeanUtil.toBean(hrFestivalDataAddParam, HrFestivalData.class);
        this.save(hrFestivalData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrFestivalDataEditParam hrFestivalDataEditParam) {
        HrFestivalData hrFestivalData = this.queryEntity(hrFestivalDataEditParam.getId());
        BeanUtil.copyProperties(hrFestivalDataEditParam, hrFestivalData);
        this.updateById(hrFestivalData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrFestivalDataIdParam> hrFestivalDataIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrFestivalDataIdParamList, HrFestivalDataIdParam::getId));
    }

    @Override
    public HrFestivalData detail(HrFestivalDataIdParam hrFestivalDataIdParam) {
        return this.queryEntity(hrFestivalDataIdParam.getId());
    }

    @Override
    public HrFestivalData queryEntity(String id) {
        HrFestivalData hrFestivalData = this.getById(id);
        if(ObjectUtil.isEmpty(hrFestivalData)) {
            throw new CommonException("法定节假日数据不存在，id值为：{}", id);
        }
        return hrFestivalData;
    }
}
