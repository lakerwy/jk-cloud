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
import vip.xiaonuo.biz.modular.car.entity.DriverBreaks;
import vip.xiaonuo.biz.modular.car.mapper.DriverBreaksMapper;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksAddParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksEditParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksIdParam;
import vip.xiaonuo.biz.modular.car.param.DriverBreaksPageParam;
import vip.xiaonuo.biz.modular.car.service.DriverBreaksService;

import java.util.List;

/**
 * CAR-违章记录Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 15:58
 **/
@DS("slave_1")
@Service
public class DriverBreaksServiceImpl extends ServiceImpl<DriverBreaksMapper, DriverBreaks> implements DriverBreaksService {

    @Override
    public Page<DriverBreaks> page(DriverBreaksPageParam driverBreaksPageParam) {
        QueryWrapper<DriverBreaks> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(driverBreaksPageParam.getSn())) {
            queryWrapper.lambda().like(DriverBreaks::getSn, driverBreaksPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(driverBreaksPageParam.getName())) {
            queryWrapper.lambda().like(DriverBreaks::getName, driverBreaksPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(driverBreaksPageParam.getVehicle())) {
            queryWrapper.lambda().like(DriverBreaks::getVehicle, driverBreaksPageParam.getVehicle());
        }
        if(ObjectUtil.isNotEmpty(driverBreaksPageParam.getDecisionNo())) {
            queryWrapper.lambda().like(DriverBreaks::getDecisionNo, driverBreaksPageParam.getDecisionNo());
        }
        if(ObjectUtil.isNotEmpty(driverBreaksPageParam.getStatus())) {
            queryWrapper.lambda().eq(DriverBreaks::getStatus, driverBreaksPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(driverBreaksPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(DriverBreaks::getName,driverBreaksPageParam.getSearchKey())
                    .or().like(DriverBreaks::getSn,driverBreaksPageParam.getSearchKey())
                    .or().like(DriverBreaks::getVehicle,driverBreaksPageParam.getSearchKey())
                    .or().like(DriverBreaks::getDecisionNo,driverBreaksPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(driverBreaksPageParam.getSortField(), driverBreaksPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(driverBreaksPageParam.getSortOrder());
            queryWrapper.orderBy(true, driverBreaksPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(driverBreaksPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(DriverBreaks::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(DriverBreaksAddParam driverBreaksAddParam) {
        DriverBreaks driverBreaks = BeanUtil.toBean(driverBreaksAddParam, DriverBreaks.class);
        this.save(driverBreaks);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(DriverBreaksEditParam driverBreaksEditParam) {
        DriverBreaks driverBreaks = this.queryEntity(driverBreaksEditParam.getId());
        BeanUtil.copyProperties(driverBreaksEditParam, driverBreaks);
        this.updateById(driverBreaks);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<DriverBreaksIdParam> driverBreaksIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(driverBreaksIdParamList, DriverBreaksIdParam::getId));
    }

    @Override
    public DriverBreaks detail(DriverBreaksIdParam driverBreaksIdParam) {
        return this.queryEntity(driverBreaksIdParam.getId());
    }

    @Override
    public DriverBreaks queryEntity(String id) {
        DriverBreaks driverBreaks = this.getById(id);
        if(ObjectUtil.isEmpty(driverBreaks)) {
            throw new CommonException("CAR-违章记录不存在，id值为：{}", id);
        }
        return driverBreaks;
    }
}
