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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;
import vip.xiaonuo.biz.modular.car.vo.ServiceRatingsViewModel;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.ServiceRatings;
import vip.xiaonuo.biz.modular.car.mapper.ServiceRatingsMapper;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsAddParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsEditParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsIdParam;
import vip.xiaonuo.biz.modular.car.param.ServiceRatingsPageParam;
import vip.xiaonuo.biz.modular.car.service.ServiceRatingsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * CAR-用车服务评价Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:03
 **/

@DS("slave_1")
@Service
public class ServiceRatingsServiceImpl extends ServiceImpl<ServiceRatingsMapper, ServiceRatings> implements ServiceRatingsService {

    @Resource
    private ServiceRatingsMapper serviceRatingsMapper;

    @Override
    public Page<ServiceRatings> page(ServiceRatingsPageParam serviceRatingsPageParam) {
        QueryWrapper<ServiceRatings> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(serviceRatingsPageParam.getVehicleApplyId())) {
            queryWrapper.lambda().eq(ServiceRatings::getVehicleApplyId, serviceRatingsPageParam.getVehicleApplyId());
        }
        if(ObjectUtil.isAllNotEmpty(serviceRatingsPageParam.getSortField(), serviceRatingsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(serviceRatingsPageParam.getSortOrder());
            queryWrapper.orderBy(true, serviceRatingsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(serviceRatingsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ServiceRatings::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ServiceRatingsAddParam serviceRatingsAddParam) {
        ServiceRatings serviceRatings = BeanUtil.toBean(serviceRatingsAddParam, ServiceRatings.class);
        this.save(serviceRatings);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ServiceRatingsEditParam serviceRatingsEditParam) {
        ServiceRatings serviceRatings = this.queryEntity(serviceRatingsEditParam.getId());
        BeanUtil.copyProperties(serviceRatingsEditParam, serviceRatings);
        this.updateById(serviceRatings);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ServiceRatingsIdParam> serviceRatingsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(serviceRatingsIdParamList, ServiceRatingsIdParam::getId));
    }

    @Override
    public ServiceRatingsViewModel detail(ServiceRatingsIdParam serviceRatingsIdParam) {
        return serviceRatingsMapper.getById(serviceRatingsIdParam.getId());
    }

    @Override
    public ServiceRatings queryEntity(String id) {
        ServiceRatings serviceRatings = this.getById(id);
        if(ObjectUtil.isEmpty(serviceRatings)) {
            throw new CommonException("CAR-用车服务评价不存在，id值为：{}", id);
        }
        return serviceRatings;
    }

    @Override
    public ServiceRatingsViewModel detailByApplyId(String applyId) {
        List<ServiceRatingsViewModel> list = serviceRatingsMapper.getByApplyId(applyId);
        return CollUtil.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    public List<ServiceRatingsViewModel> detailByDriverId(String driverId) {
        return serviceRatingsMapper.getByDriverId(driverId);
    }

    @Override
    public Page<ServiceRatingsViewModel> pageList(ServiceRatingsPageParam serviceRatingsPageParam) {
        QueryWrapper<ServiceRatings> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(serviceRatingsPageParam.getVehicleApplySN())) {
            queryWrapper.eq("b.SN", serviceRatingsPageParam.getVehicleApplySN());
        }
        if(ObjectUtil.isNotEmpty(serviceRatingsPageParam.getVehicleApplyUsers())) {
            queryWrapper.eq("b.USERS", serviceRatingsPageParam.getVehicleApplyUsers());
        }
        if(ObjectUtil.isNotEmpty(serviceRatingsPageParam.getVehicleApplyVehicle())) {
            queryWrapper.eq("b.VEHICLE", serviceRatingsPageParam.getVehicleApplyVehicle());
        }
        if(ObjectUtil.isNotEmpty(serviceRatingsPageParam.getVehicleApplyDriver())) {
            queryWrapper.eq("b.DRIVER", serviceRatingsPageParam.getVehicleApplyDriver());
        }
        if(ObjectUtil.isAllNotEmpty(serviceRatingsPageParam.getSortField(), serviceRatingsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(serviceRatingsPageParam.getSortOrder());
            queryWrapper.orderBy(true, serviceRatingsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(serviceRatingsPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.UPDATE_TIME");
        }
        return serviceRatingsMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);

    }
}
