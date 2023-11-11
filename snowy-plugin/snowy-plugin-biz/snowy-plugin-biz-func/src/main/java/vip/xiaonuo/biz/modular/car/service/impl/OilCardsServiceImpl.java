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
import vip.xiaonuo.biz.modular.car.entity.OilCards;
import vip.xiaonuo.biz.modular.car.mapper.OilCardsMapper;
import vip.xiaonuo.biz.modular.car.param.OilCardsAddParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsEditParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsIdParam;
import vip.xiaonuo.biz.modular.car.param.OilCardsPageParam;
import vip.xiaonuo.biz.modular.car.service.OilCardsService;

import java.math.BigDecimal;
import java.util.List;

/**
 * CAR-油卡信息Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 **/

@DS("slave_1")
@Service
public class OilCardsServiceImpl extends ServiceImpl<OilCardsMapper, OilCards> implements OilCardsService {

    @Override
    public Page<OilCards> page(OilCardsPageParam oilCardsPageParam) {
        QueryWrapper<OilCards> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(oilCardsPageParam.getCardNumber())) {
            queryWrapper.lambda().like(OilCards::getCardNumber, oilCardsPageParam.getCardNumber());
        }
        if(ObjectUtil.isNotEmpty(oilCardsPageParam.getChannel())) {
            queryWrapper.lambda().eq(OilCards::getChannel, oilCardsPageParam.getChannel());
        }
        if(ObjectUtil.isAllNotEmpty(oilCardsPageParam.getSortField(), oilCardsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(oilCardsPageParam.getSortOrder());
            queryWrapper.orderBy(true, oilCardsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(oilCardsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(OilCards::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OilCardsAddParam oilCardsAddParam) {
        OilCards oilCards = BeanUtil.toBean(oilCardsAddParam, OilCards.class);
        this.save(oilCards);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OilCardsEditParam oilCardsEditParam) {
        OilCards oilCards = this.queryEntity(oilCardsEditParam.getId());
        BeanUtil.copyProperties(oilCardsEditParam, oilCards);
        this.updateById(oilCards);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<OilCardsIdParam> oilCardsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(oilCardsIdParamList, OilCardsIdParam::getId));
    }

    @Override
    public OilCards detail(OilCardsIdParam oilCardsIdParam) {
        return this.queryEntity(oilCardsIdParam.getId());
    }

    @Override
    public OilCards queryEntity(String id) {
        OilCards oilCards = this.getById(id);
        if(ObjectUtil.isEmpty(oilCards)) {
            throw new CommonException("CAR-油卡信息不存在，id值为：{}", id);
        }
        return oilCards;
    }

    @Override
    public void updateOilCardBlance(String cardNumber, int type, Boolean isEdit, BigDecimal amt, BigDecimal oldAmt) {
        QueryWrapper<OilCards> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OilCards::getCardNumber,cardNumber).last("limit 1");
        OilCards oilCards = this.getOne(queryWrapper);
        if(ObjectUtil.isEmpty(oilCards)) return;
        //油卡充值
        if(type == 1){
            if(isEdit){
                oilCards.setBalance(toValue(oilCards.getBalance()).add(toValue(amt)).subtract(toValue(oldAmt)));
            }else {
                oilCards.setBalance(toValue(oilCards.getBalance()).add(toValue(amt)));
            }
        }//车辆加油
        else if(type == 2){
            if(isEdit){
                oilCards.setBalance(toValue(oilCards.getBalance()).add(toValue(oldAmt)).subtract(toValue(amt)));
            }else {
                oilCards.setBalance(toValue(oilCards.getBalance()).subtract(toValue(amt)));
            }
        }
        this.updateById(oilCards);
    }

    /**
     * 数值处理
     * @param value 入参值
     * @return
     */
    public BigDecimal toValue(BigDecimal value){
        return value == null ? BigDecimal.ZERO : value;
    }
}
