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
import vip.xiaonuo.biz.modular.card.entity.CardQrCode;
import vip.xiaonuo.biz.modular.card.mapper.CardQrCodeMapper;
import vip.xiaonuo.biz.modular.card.param.CardQrCodeAddParam;
import vip.xiaonuo.biz.modular.card.param.CardQrCodeEditParam;
import vip.xiaonuo.biz.modular.card.param.CardQrCodeIdParam;
import vip.xiaonuo.biz.modular.card.param.CardQrCodePageParam;
import vip.xiaonuo.biz.modular.card.service.CardQrCodeService;

import java.util.List;

/**
 * Ecard-电子名片-二维码Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/21 15:52
 **/

@DS("slave_1")
@Service
public class CardQrCodeServiceImpl extends ServiceImpl<CardQrCodeMapper, CardQrCode> implements CardQrCodeService {

    @Override
    public Page<CardQrCode> page(CardQrCodePageParam cardQrCodePageParam) {
        QueryWrapper<CardQrCode> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cardQrCodePageParam.getUserName())) {
            queryWrapper.lambda().like(CardQrCode::getUserName, cardQrCodePageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(cardQrCodePageParam.getDeptName())) {
            queryWrapper.lambda().like(CardQrCode::getDeptName, cardQrCodePageParam.getDeptName());
        }
        if(ObjectUtil.isNotEmpty(cardQrCodePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CardQrCode::getUserName,cardQrCodePageParam.getSearchKey())
                    .or().like(CardQrCode::getDeptName,cardQrCodePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(cardQrCodePageParam.getSortField(), cardQrCodePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cardQrCodePageParam.getSortOrder());
            queryWrapper.orderBy(true, cardQrCodePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cardQrCodePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(CardQrCode::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CardQrCodeAddParam cardQrCodeAddParam) {
        CardQrCode cardQrCode = BeanUtil.toBean(cardQrCodeAddParam, CardQrCode.class);
        this.save(cardQrCode);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CardQrCodeEditParam cardQrCodeEditParam) {
        CardQrCode cardQrCode = this.queryEntity(cardQrCodeEditParam.getId());
        BeanUtil.copyProperties(cardQrCodeEditParam, cardQrCode);
        this.updateById(cardQrCode);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CardQrCodeIdParam> cardQrCodeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cardQrCodeIdParamList, CardQrCodeIdParam::getId));
    }

    @Override
    public CardQrCode detail(CardQrCodeIdParam cardQrCodeIdParam) {
        return this.queryEntity(cardQrCodeIdParam.getId());
    }

    @Override
    public CardQrCode queryEntity(String id) {
        CardQrCode cardQrCode = this.getById(id);
        if(ObjectUtil.isEmpty(cardQrCode)) {
            throw new CommonException("Ecard-电子名片-二维码不存在，id值为：{}", id);
        }
        return cardQrCode;
    }
}
