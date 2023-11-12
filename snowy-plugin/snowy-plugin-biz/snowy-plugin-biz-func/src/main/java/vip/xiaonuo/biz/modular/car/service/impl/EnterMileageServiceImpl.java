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
import vip.xiaonuo.biz.modular.car.service.VehicleInformationService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.car.entity.EnterMileage;
import vip.xiaonuo.biz.modular.car.mapper.EnterMileageMapper;
import vip.xiaonuo.biz.modular.car.param.EnterMileageAddParam;
import vip.xiaonuo.biz.modular.car.param.EnterMileageEditParam;
import vip.xiaonuo.biz.modular.car.param.EnterMileageIdParam;
import vip.xiaonuo.biz.modular.car.param.EnterMileagePageParam;
import vip.xiaonuo.biz.modular.car.service.EnterMileageService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * CAR-里程补登Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:00
 **/

@DS("slave_1")
@Service
public class EnterMileageServiceImpl extends ServiceImpl<EnterMileageMapper, EnterMileage> implements EnterMileageService {

    @Resource
    private VehicleInformationService vehicleInformationService;

    @Override
    public Page<EnterMileage> page(EnterMileagePageParam enterMileagePageParam) {
        QueryWrapper<EnterMileage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(enterMileagePageParam.getVehicleId())) {
            queryWrapper.lambda().eq(EnterMileage::getVehicleId, enterMileagePageParam.getVehicleId());
        }
        if(ObjectUtil.isNotEmpty(enterMileagePageParam.getLicenseNo())) {
            queryWrapper.lambda().like(EnterMileage::getLicenseNo, enterMileagePageParam.getLicenseNo());
        }
        if(ObjectUtil.isNotEmpty(enterMileagePageParam.getVehicleBrand())) {
            queryWrapper.lambda().like(EnterMileage::getVehicleBrand, enterMileagePageParam.getVehicleBrand());
        }
        if(ObjectUtil.isNotEmpty(enterMileagePageParam.getOpt())) {
            queryWrapper.lambda().like(EnterMileage::getOpt, enterMileagePageParam.getOpt());
        }
        if(ObjectUtil.isNotEmpty(enterMileagePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(EnterMileage::getLicenseNo,enterMileagePageParam.getSearchKey())
                    .or().like(EnterMileage::getVehicleBrand,enterMileagePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(enterMileagePageParam.getSortField(), enterMileagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(enterMileagePageParam.getSortOrder());
            queryWrapper.orderBy(true, enterMileagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(enterMileagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EnterMileage::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EnterMileageAddParam enterMileageAddParam) {
        EnterMileage enterMileage = BeanUtil.toBean(enterMileageAddParam, EnterMileage.class);
        this.save(enterMileage);
        //更新车辆总行驶里程
        vehicleInformationService.updateVehicleTotalMileage(enterMileage.getVehicleId(),false,enterMileage.getMileages(), BigDecimal.ZERO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EnterMileageEditParam enterMileageEditParam) {
        EnterMileage enterMileage = this.queryEntity(enterMileageEditParam.getId());
        //旧的补登里程
        BigDecimal oldMileages = enterMileage.getMileages();
        BeanUtil.copyProperties(enterMileageEditParam, enterMileage);
        this.updateById(enterMileage);
        //更新车辆总行驶里程
        vehicleInformationService.updateVehicleTotalMileage(enterMileage.getVehicleId(),true,enterMileage.getMileages(), oldMileages);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EnterMileageIdParam> enterMileageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(enterMileageIdParamList, EnterMileageIdParam::getId));
    }

    @Override
    public EnterMileage detail(EnterMileageIdParam enterMileageIdParam) {
        return this.queryEntity(enterMileageIdParam.getId());
    }

    @Override
    public EnterMileage queryEntity(String id) {
        EnterMileage enterMileage = this.getById(id);
        if(ObjectUtil.isEmpty(enterMileage)) {
            throw new CommonException("CAR-里程补登不存在，id值为：{}", id);
        }
        return enterMileage;
    }
}
