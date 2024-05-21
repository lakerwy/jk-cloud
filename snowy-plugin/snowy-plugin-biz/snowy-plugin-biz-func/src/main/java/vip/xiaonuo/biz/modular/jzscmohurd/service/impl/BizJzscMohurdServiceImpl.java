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
package vip.xiaonuo.biz.modular.jzscmohurd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.jzscmohurd.entity.BizJzscMohurd;
import vip.xiaonuo.biz.modular.jzscmohurd.mapper.BizJzscMohurdMapper;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdAddParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdEditParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdIdParam;
import vip.xiaonuo.biz.modular.jzscmohurd.param.BizJzscMohurdPageParam;
import vip.xiaonuo.biz.modular.jzscmohurd.service.BizJzscMohurdService;

import java.util.List;

/**
 * BIZ_JZSC_MOHURDService接口实现类
 *
 * @author mayike
 * @date  2024/05/20 17:18
 **/
@Service
public class BizJzscMohurdServiceImpl extends ServiceImpl<BizJzscMohurdMapper, BizJzscMohurd> implements BizJzscMohurdService {

    @Override
    public Page<BizJzscMohurd> page(BizJzscMohurdPageParam bizJzscMohurdPageParam) {
        QueryWrapper<BizJzscMohurd> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRyName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRyName, bizJzscMohurdPageParam.getRyName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRyInQymc())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRyInQymc, bizJzscMohurdPageParam.getRyInQymc());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRegProfName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRegProfName, bizJzscMohurdPageParam.getRegProfName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getQyName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getQyName, bizJzscMohurdPageParam.getQyName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRegTypeName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRegTypeName, bizJzscMohurdPageParam.getRegTypeName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getCertRegNo())) {
            queryWrapper.lambda().like(BizJzscMohurd::getCertRegNo, bizJzscMohurdPageParam.getCertRegNo());
        }
        if(ObjectUtil.isAllNotEmpty(bizJzscMohurdPageParam.getSortField(), bizJzscMohurdPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizJzscMohurdPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizJzscMohurdPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizJzscMohurdPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizJzscMohurd::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizJzscMohurdAddParam bizJzscMohurdAddParam) {
        BizJzscMohurd bizJzscMohurd = BeanUtil.toBean(bizJzscMohurdAddParam, BizJzscMohurd.class);
        this.save(bizJzscMohurd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizJzscMohurdEditParam bizJzscMohurdEditParam) {
        BizJzscMohurd bizJzscMohurd = this.queryEntity(bizJzscMohurdEditParam.getId());
        BeanUtil.copyProperties(bizJzscMohurdEditParam, bizJzscMohurd);
        this.updateById(bizJzscMohurd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizJzscMohurdIdParam> bizJzscMohurdIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizJzscMohurdIdParamList, BizJzscMohurdIdParam::getId));
    }

    @Override
    public BizJzscMohurd detail(BizJzscMohurdIdParam bizJzscMohurdIdParam) {
        return this.queryEntity(bizJzscMohurdIdParam.getId());
    }

    @Override
    public BizJzscMohurd queryEntity(String id) {
        BizJzscMohurd bizJzscMohurd = this.getById(id);
        if(ObjectUtil.isEmpty(bizJzscMohurd)) {
            throw new CommonException("BIZ_JZSC_MOHURD不存在，id值为：{}", id);
        }
        return bizJzscMohurd;
    }
}
