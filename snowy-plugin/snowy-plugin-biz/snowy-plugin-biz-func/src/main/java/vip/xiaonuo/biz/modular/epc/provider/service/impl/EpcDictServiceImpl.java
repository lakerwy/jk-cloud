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
package vip.xiaonuo.biz.modular.epc.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.provider.entity.EpcDict;
import vip.xiaonuo.biz.modular.epc.provider.mapper.EpcDictMapper;
import vip.xiaonuo.biz.modular.epc.provider.param.*;
import vip.xiaonuo.biz.modular.epc.provider.service.EpcDictService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC -字典配置表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/25 16:27
 **/
@Service
public class EpcDictServiceImpl extends ServiceImpl<EpcDictMapper, EpcDict> implements EpcDictService {

    @Override
    public Page<EpcDict> page(EpcDictPageParam epcDictPageParam) {
        QueryWrapper<EpcDict> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcDictPageParam.getCode())) {
            queryWrapper.lambda().eq(EpcDict::getCode, epcDictPageParam.getCode());
        }
        if(ObjectUtil.isNotEmpty(epcDictPageParam.getValue())) {
            queryWrapper.lambda().like(EpcDict::getValue, epcDictPageParam.getValue());
        }
        if(ObjectUtil.isNotEmpty(epcDictPageParam.getType())) {
            queryWrapper.lambda().like(EpcDict::getType, epcDictPageParam.getType());
        }
        if(ObjectUtil.isAllNotEmpty(epcDictPageParam.getSortField(), epcDictPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcDictPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcDictPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcDictPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EpcDict::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcDictAddParam epcDictAddParam) {
        EpcDict epcDict = BeanUtil.toBean(epcDictAddParam, EpcDict.class);
        this.save(epcDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcDictEditParam epcDictEditParam) {
        EpcDict epcDict = this.queryEntity(epcDictEditParam.getId());
        BeanUtil.copyProperties(epcDictEditParam, epcDict);
        this.updateById(epcDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcDictIdParam> epcDictIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcDictIdParamList, EpcDictIdParam::getId));
    }

    @Override
    public EpcDict detail(EpcDictIdParam epcDictIdParam) {
        return this.queryEntity(epcDictIdParam.getId());
    }

    @Override
    public EpcDict queryEntity(String id) {
        EpcDict epcDict = this.getById(id);
        if(ObjectUtil.isEmpty(epcDict)) {
            throw new CommonException("EPC -字典配置表不存在，id值为：{}", id);
        }
        return epcDict;
    }
}
