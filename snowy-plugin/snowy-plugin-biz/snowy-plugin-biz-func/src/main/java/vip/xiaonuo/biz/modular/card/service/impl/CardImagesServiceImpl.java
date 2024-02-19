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
package vip.xiaonuo.biz.modular.card.service.impl;

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
import vip.xiaonuo.biz.modular.card.entity.CardImages;
import vip.xiaonuo.biz.modular.card.mapper.CardImagesMapper;
import vip.xiaonuo.biz.modular.card.param.CardImagesAddParam;
import vip.xiaonuo.biz.modular.card.param.CardImagesEditParam;
import vip.xiaonuo.biz.modular.card.param.CardImagesIdParam;
import vip.xiaonuo.biz.modular.card.param.CardImagesPageParam;
import vip.xiaonuo.biz.modular.card.service.CardImagesService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Ecard-电子名片-图片Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/18 15:40
 **/

@DS("slave_1")
@Service
public class CardImagesServiceImpl extends ServiceImpl<CardImagesMapper, CardImages> implements CardImagesService {

    @Resource
    private CardImagesMapper cardImagesMapper;

    @Override
    public Page<CardImages> page(CardImagesPageParam cardImagesPageParam) {
        QueryWrapper<CardImages> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cardImagesPageParam.getType())) {
            queryWrapper.lambda().eq(CardImages::getType, cardImagesPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(cardImagesPageParam.getSortField(), cardImagesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cardImagesPageParam.getSortOrder());
            queryWrapper.orderBy(true, cardImagesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cardImagesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CardImages::getType).orderByAsc(CardImages::getSortIndex);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CardImagesAddParam cardImagesAddParam) {
        CardImages cardImages = BeanUtil.toBean(cardImagesAddParam, CardImages.class);
        this.save(cardImages);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CardImagesEditParam cardImagesEditParam) {
        CardImages cardImages = this.queryEntity(cardImagesEditParam.getId());
        BeanUtil.copyProperties(cardImagesEditParam, cardImages);
        this.updateById(cardImages);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CardImagesIdParam> cardImagesIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cardImagesIdParamList, CardImagesIdParam::getId));
    }

    @Override
    public CardImages detail(CardImagesIdParam cardImagesIdParam) {
        return this.queryEntity(cardImagesIdParam.getId());
    }

    @Override
    public CardImages queryEntity(String id) {
        CardImages cardImages = this.getById(id);
        if(ObjectUtil.isEmpty(cardImages)) {
            throw new CommonException("Ecard-电子名片-图片不存在，id值为：{}", id);
        }
        return cardImages;
    }

    @Override
    public List<CardImages> getImagesById(String cardId) {
        return cardImagesMapper.getImagesById(cardId);
    }
}
