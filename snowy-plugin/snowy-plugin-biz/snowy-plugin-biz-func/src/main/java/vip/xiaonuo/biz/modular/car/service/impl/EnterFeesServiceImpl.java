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
import vip.xiaonuo.biz.modular.car.entity.EnterFees;
import vip.xiaonuo.biz.modular.car.mapper.EnterFeesMapper;
import vip.xiaonuo.biz.modular.car.param.EnterFeesAddParam;
import vip.xiaonuo.biz.modular.car.param.EnterFeesEditParam;
import vip.xiaonuo.biz.modular.car.param.EnterFeesIdParam;
import vip.xiaonuo.biz.modular.car.param.EnterFeesPageParam;
import vip.xiaonuo.biz.modular.car.service.EnterFeesService;

import java.util.List;

/**
 * CAR-费用补登Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 15:59
 **/

@DS("slave_1")
@Service
public class EnterFeesServiceImpl extends ServiceImpl<EnterFeesMapper, EnterFees> implements EnterFeesService {

    @Override
    public Page<EnterFees> page(EnterFeesPageParam enterFeesPageParam) {
        QueryWrapper<EnterFees> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(enterFeesPageParam.getVehicleId())) {
            queryWrapper.lambda().eq(EnterFees::getVehicleId, enterFeesPageParam.getVehicleId());
        }
        if(ObjectUtil.isNotEmpty(enterFeesPageParam.getLicenseNo())) {
            queryWrapper.lambda().like(EnterFees::getLicenseNo, enterFeesPageParam.getLicenseNo());
        }
        if(ObjectUtil.isNotEmpty(enterFeesPageParam.getVehicleBrand())) {
            queryWrapper.lambda().like(EnterFees::getVehicleBrand, enterFeesPageParam.getVehicleBrand());
        }
        if(ObjectUtil.isNotEmpty(enterFeesPageParam.getFeeType())) {
            queryWrapper.lambda().eq(EnterFees::getFeeType, enterFeesPageParam.getFeeType());
        }
        if(ObjectUtil.isAllNotEmpty(enterFeesPageParam.getSortField(), enterFeesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(enterFeesPageParam.getSortOrder());
            queryWrapper.orderBy(true, enterFeesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(enterFeesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EnterFees::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EnterFeesAddParam enterFeesAddParam) {
        EnterFees enterFees = BeanUtil.toBean(enterFeesAddParam, EnterFees.class);
        this.save(enterFees);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EnterFeesEditParam enterFeesEditParam) {
        EnterFees enterFees = this.queryEntity(enterFeesEditParam.getId());
        BeanUtil.copyProperties(enterFeesEditParam, enterFees);
        this.updateById(enterFees);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EnterFeesIdParam> enterFeesIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(enterFeesIdParamList, EnterFeesIdParam::getId));
    }

    @Override
    public EnterFees detail(EnterFeesIdParam enterFeesIdParam) {
        return this.queryEntity(enterFeesIdParam.getId());
    }

    @Override
    public EnterFees queryEntity(String id) {
        EnterFees enterFees = this.getById(id);
        if(ObjectUtil.isEmpty(enterFees)) {
            throw new CommonException("CAR-费用补登不存在，id值为：{}", id);
        }
        return enterFees;
    }
}
