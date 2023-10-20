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
package vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.service.impl;

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
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.entity.HrMagicUserRelation;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.mapper.HrMagicUserRelationMapper;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationAddParam;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationEditParam;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationIdParam;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.param.HrMagicUserRelationPageParam;
import vip.xiaonuo.sys.modular.hr.magic.magicUserRelation.service.HrMagicUserRelationService;

import java.util.List;

/**
 * 魔点-建科人员关联表Service接口实现类
 *
 * @author ljy
 * @date  2023/10/20 10:39
 **/
@Service
public class HrMagicUserRelationServiceImpl extends ServiceImpl<HrMagicUserRelationMapper, HrMagicUserRelation> implements HrMagicUserRelationService {

    @Override
    public Page<HrMagicUserRelation> page(HrMagicUserRelationPageParam hrMagicUserRelationPageParam) {
        QueryWrapper<HrMagicUserRelation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(hrMagicUserRelationPageParam.getSortField(), hrMagicUserRelationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrMagicUserRelationPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrMagicUserRelationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrMagicUserRelationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HrMagicUserRelation::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrMagicUserRelationAddParam hrMagicUserRelationAddParam) {
        HrMagicUserRelation hrMagicUserRelation = BeanUtil.toBean(hrMagicUserRelationAddParam, HrMagicUserRelation.class);
        this.save(hrMagicUserRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrMagicUserRelationEditParam hrMagicUserRelationEditParam) {
        HrMagicUserRelation hrMagicUserRelation = this.queryEntity(hrMagicUserRelationEditParam.getId());
        BeanUtil.copyProperties(hrMagicUserRelationEditParam, hrMagicUserRelation);
        this.updateById(hrMagicUserRelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrMagicUserRelationIdParam> hrMagicUserRelationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrMagicUserRelationIdParamList, HrMagicUserRelationIdParam::getId));
    }

    @Override
    public HrMagicUserRelation detail(HrMagicUserRelationIdParam hrMagicUserRelationIdParam) {
        return this.queryEntity(hrMagicUserRelationIdParam.getId());
    }

    @Override
    public HrMagicUserRelation queryEntity(String id) {
        HrMagicUserRelation hrMagicUserRelation = this.getById(id);
        if(ObjectUtil.isEmpty(hrMagicUserRelation)) {
            throw new CommonException("魔点-建科人员关联表不存在，id值为：{}", id);
        }
        return hrMagicUserRelation;
    }
}
