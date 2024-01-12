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
package vip.xiaonuo.dev.modular.supervisionPlatform.service.impl;

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
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBProjectSysRelation;
import vip.xiaonuo.dev.modular.supervisionPlatform.mapper.TBProjectSysRelationMapper;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBProjectSysRelationAddParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBProjectSysRelationEditParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBProjectSysRelationIdParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBProjectSysRelationPageParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBProjectSysRelationService;

import java.util.List;

/**
 * 市场监管公共服务平台-与其他系统项目关联信息表Service接口实现类
 *
 * @author ljy
 * @date  2024/01/11 10:59
 **/
@Service
public class TBProjectSysRelationServiceImpl extends ServiceImpl<TBProjectSysRelationMapper, TBProjectSysRelation> implements TBProjectSysRelationService {

    @Override
    public Page<TBProjectSysRelation> page(TBProjectSysRelationPageParam tbprojectsysrelationPageParam) {
        QueryWrapper<TBProjectSysRelation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tbprojectsysrelationPageParam.getPrinum())) {
            queryWrapper.lambda().eq(TBProjectSysRelation::getPrinum, tbprojectsysrelationPageParam.getPrinum());
        }
        if(ObjectUtil.isAllNotEmpty(tbprojectsysrelationPageParam.getSortField(), tbprojectsysrelationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tbprojectsysrelationPageParam.getSortOrder());
            queryWrapper.orderBy(true, tbprojectsysrelationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tbprojectsysrelationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TBProjectSysRelation::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TBProjectSysRelationAddParam tbprojectsysrelationAddParam) {
        TBProjectSysRelation tbprojectsysrelation = BeanUtil.toBean(tbprojectsysrelationAddParam, TBProjectSysRelation.class);
        this.save(tbprojectsysrelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TBProjectSysRelationEditParam tbprojectsysrelationEditParam) {
        TBProjectSysRelation tbprojectsysrelation = this.queryEntity(tbprojectsysrelationEditParam.getId());
        BeanUtil.copyProperties(tbprojectsysrelationEditParam, tbprojectsysrelation);
        this.updateById(tbprojectsysrelation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TBProjectSysRelationIdParam> TBProjectSysRelationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(TBProjectSysRelationIdParamList, TBProjectSysRelationIdParam::getId));
    }

    @Override
    public TBProjectSysRelation detail(TBProjectSysRelationIdParam tbprojectsysrelationIdParam) {
        return this.queryEntity(tbprojectsysrelationIdParam.getId());
    }

    @Override
    public TBProjectSysRelation queryEntity(String id) {
        TBProjectSysRelation tbprojectsysrelation = this.getById(id);
        if(ObjectUtil.isEmpty(tbprojectsysrelation)) {
            throw new CommonException("市场监管公共服务平台-与其他系统项目关联信息表不存在，id值为：{}", id);
        }
        return tbprojectsysrelation;
    }
}
