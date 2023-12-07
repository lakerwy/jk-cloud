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
package vip.xiaonuo.biz.modular.cad.service.impl;

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
import vip.xiaonuo.biz.modular.cad.entity.CadStage;
import vip.xiaonuo.biz.modular.cad.mapper.CadStageMapper;
import vip.xiaonuo.biz.modular.cad.param.CadStageAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadStageEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadStageIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadStagePageParam;
import vip.xiaonuo.biz.modular.cad.service.CadStageService;

import java.util.List;

/**
 * CAD项目阶段Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:38
 **/
@DS("slave_1")
@Service
public class CadStageServiceImpl extends ServiceImpl<CadStageMapper, CadStage> implements CadStageService {

    @Override
    public Page<CadStage> page(CadStagePageParam cadStagePageParam) {
        QueryWrapper<CadStage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cadStagePageParam.getName())) {
            queryWrapper.lambda().like(CadStage::getName, cadStagePageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(cadStagePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CadStage::getName,cadStagePageParam.getSearchKey())
                    .or().like(CadStage::getMemo,cadStagePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(cadStagePageParam.getSortField(), cadStagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadStagePageParam.getSortOrder());
            queryWrapper.orderBy(true, cadStagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadStagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadStage::getSortIndex).orderByAsc(CadStage::getName);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadStageAddParam cadStageAddParam) {
        CadStage cadStage = BeanUtil.toBean(cadStageAddParam, CadStage.class);
        this.save(cadStage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadStageEditParam cadStageEditParam) {
        CadStage cadStage = this.queryEntity(cadStageEditParam.getId());
        BeanUtil.copyProperties(cadStageEditParam, cadStage);
        this.updateById(cadStage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadStageIdParam> cadStageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadStageIdParamList, CadStageIdParam::getId));
    }

    @Override
    public CadStage detail(CadStageIdParam cadStageIdParam) {
        return this.queryEntity(cadStageIdParam.getId());
    }

    @Override
    public CadStage queryEntity(String id) {
        CadStage cadStage = this.getById(id);
        if(ObjectUtil.isEmpty(cadStage)) {
            throw new CommonException("CAD项目阶段不存在，id值为：{}", id);
        }
        return cadStage;
    }
}
