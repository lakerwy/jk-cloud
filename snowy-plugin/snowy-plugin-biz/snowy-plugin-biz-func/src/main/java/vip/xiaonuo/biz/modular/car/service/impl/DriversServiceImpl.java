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
package vip.xiaonuo.biz.modular.car.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.Drivers;
import vip.xiaonuo.biz.modular.car.mapper.DriversMapper;
import vip.xiaonuo.biz.modular.car.param.DriversAddParam;
import vip.xiaonuo.biz.modular.car.param.DriversEditParam;
import vip.xiaonuo.biz.modular.car.param.DriversIdParam;
import vip.xiaonuo.biz.modular.car.param.DriversPageParam;
import vip.xiaonuo.biz.modular.car.service.DriversService;

import java.util.List;

/**
 * CAR-驾驶员Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 15:57
 **/

@DS("slave_1")
@Service
public class DriversServiceImpl extends ServiceImpl<DriversMapper, Drivers> implements DriversService {

    @Override
    public Page<Drivers> page(DriversPageParam driversPageParam) {
        QueryWrapper<Drivers> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(driversPageParam.getSn())) {
            queryWrapper.lambda().like(Drivers::getSn, driversPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(driversPageParam.getName())) {
            queryWrapper.lambda().like(Drivers::getName, driversPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(driversPageParam.getLicenseType())) {
            queryWrapper.lambda().eq(Drivers::getLicenseType, driversPageParam.getLicenseType());
        }
        if(ObjectUtil.isNotEmpty(driversPageParam.getStatus())) {
            queryWrapper.lambda().eq(Drivers::getStatus, driversPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(driversPageParam.getSortField(), driversPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(driversPageParam.getSortOrder());
            queryWrapper.orderBy(true, driversPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(driversPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Drivers::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DriversAddParam driversAddParam) {
        Drivers drivers = BeanUtil.toBean(driversAddParam, Drivers.class);
        this.save(drivers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DriversEditParam driversEditParam) {
        Drivers drivers = this.queryEntity(driversEditParam.getId());
        BeanUtil.copyProperties(driversEditParam, drivers);
        this.updateById(drivers);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DriversIdParam> driversIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(driversIdParamList, DriversIdParam::getId));
    }

    @Override
    public Drivers detail(DriversIdParam driversIdParam) {
        return this.queryEntity(driversIdParam.getId());
    }

    @Override
    public Drivers queryEntity(String id) {
        Drivers drivers = this.getById(id);
        if(ObjectUtil.isEmpty(drivers)) {
            throw new CommonException("CAR-驾驶员不存在，id值为：{}", id);
        }
        return drivers;
    }
}
