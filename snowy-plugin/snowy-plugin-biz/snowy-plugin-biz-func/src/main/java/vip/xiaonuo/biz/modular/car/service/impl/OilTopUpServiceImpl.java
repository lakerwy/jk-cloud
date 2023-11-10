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
import vip.xiaonuo.biz.modular.car.entity.OilTopUp;
import vip.xiaonuo.biz.modular.car.mapper.OilTopUpMapper;
import vip.xiaonuo.biz.modular.car.param.OilTopUpAddParam;
import vip.xiaonuo.biz.modular.car.param.OilTopUpEditParam;
import vip.xiaonuo.biz.modular.car.param.OilTopUpIdParam;
import vip.xiaonuo.biz.modular.car.param.OilTopUpPageParam;
import vip.xiaonuo.biz.modular.car.service.OilTopUpService;

import java.util.List;

/**
 * CAR-油卡充值Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:02
 **/

@DS("slave_1")
@Service
public class OilTopUpServiceImpl extends ServiceImpl<OilTopUpMapper, OilTopUp> implements OilTopUpService {

    @Override
    public Page<OilTopUp> page(OilTopUpPageParam oilTopUpPageParam) {
        QueryWrapper<OilTopUp> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(oilTopUpPageParam.getCardNumber())) {
            queryWrapper.lambda().like(OilTopUp::getCardNumber, oilTopUpPageParam.getCardNumber());
        }
        if(ObjectUtil.isNotEmpty(oilTopUpPageParam.getStartTopUpDate()) && ObjectUtil.isNotEmpty(oilTopUpPageParam.getEndTopUpDate())) {
            queryWrapper.lambda().between(OilTopUp::getTopUpDate, oilTopUpPageParam.getStartTopUpDate(), oilTopUpPageParam.getEndTopUpDate());
        }
        if(ObjectUtil.isAllNotEmpty(oilTopUpPageParam.getSortField(), oilTopUpPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(oilTopUpPageParam.getSortOrder());
            queryWrapper.orderBy(true, oilTopUpPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(oilTopUpPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(OilTopUp::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OilTopUpAddParam oilTopUpAddParam) {
        OilTopUp oilTopUp = BeanUtil.toBean(oilTopUpAddParam, OilTopUp.class);
        this.save(oilTopUp);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OilTopUpEditParam oilTopUpEditParam) {
        OilTopUp oilTopUp = this.queryEntity(oilTopUpEditParam.getId());
        BeanUtil.copyProperties(oilTopUpEditParam, oilTopUp);
        this.updateById(oilTopUp);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<OilTopUpIdParam> oilTopUpIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(oilTopUpIdParamList, OilTopUpIdParam::getId));
    }

    @Override
    public OilTopUp detail(OilTopUpIdParam oilTopUpIdParam) {
        return this.queryEntity(oilTopUpIdParam.getId());
    }

    @Override
    public OilTopUp queryEntity(String id) {
        OilTopUp oilTopUp = this.getById(id);
        if(ObjectUtil.isEmpty(oilTopUp)) {
            throw new CommonException("CAR-油卡充值不存在，id值为：{}", id);
        }
        return oilTopUp;
    }
}
