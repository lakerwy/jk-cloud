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
package vip.xiaonuo.biz.modular.epc.safety.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.epc.safety.entity.EpcWorkerPool;
import vip.xiaonuo.biz.modular.epc.safety.mapper.EpcWorkerPoolMapper;
import vip.xiaonuo.biz.modular.epc.safety.param.*;
import vip.xiaonuo.biz.modular.epc.safety.service.EpcWorkerPoolService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * EPC-工人信息池Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/05 15:01
 **/
@Service
public class EpcWorkerPoolServiceImpl extends ServiceImpl<EpcWorkerPoolMapper, EpcWorkerPool> implements EpcWorkerPoolService {

    @Override
    public Page<EpcWorkerPool> page(EpcWorkerPoolPageParam epcWorkerPoolPageParam) {
        QueryWrapper<EpcWorkerPool> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(epcWorkerPoolPageParam.getTenantId())) {
            queryWrapper.lambda().eq(EpcWorkerPool::getTenantId, epcWorkerPoolPageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(epcWorkerPoolPageParam.getName())) {
            queryWrapper.lambda().like(EpcWorkerPool::getName, epcWorkerPoolPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(epcWorkerPoolPageParam.getIdCard())) {
            queryWrapper.lambda().like(EpcWorkerPool::getIdCard, epcWorkerPoolPageParam.getIdCard());
        }
        if(ObjectUtil.isNotEmpty(epcWorkerPoolPageParam.getSearchKey())){
            queryWrapper.and(q->q.lambda().like(EpcWorkerPool::getIdCard,epcWorkerPoolPageParam.getSearchKey())
                    .or().like(EpcWorkerPool::getName,epcWorkerPoolPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(epcWorkerPoolPageParam.getSortField(), epcWorkerPoolPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(epcWorkerPoolPageParam.getSortOrder());
            queryWrapper.orderBy(true, epcWorkerPoolPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(epcWorkerPoolPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(EpcWorkerPool::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EpcWorkerPoolAddParam epcWorkerPoolAddParam) {
        //判断当前身份证是否已存在
        EpcWorkerPool old = this.getOne(new QueryWrapper<EpcWorkerPool>().lambda().eq(EpcWorkerPool::getIdCard,epcWorkerPoolAddParam.getIdCard()));
        if(ObjectUtil.isNotEmpty(old)) {
            throw new CommonException("身份证：{}已存在", epcWorkerPoolAddParam.getIdCard());
        }

        EpcWorkerPool epcWorkerPool = BeanUtil.toBean(epcWorkerPoolAddParam, EpcWorkerPool.class);
        this.save(epcWorkerPool);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EpcWorkerPoolEditParam epcWorkerPoolEditParam) {
        //修改身份证号校验
        EpcWorkerPool old = this.getOne(new QueryWrapper<EpcWorkerPool>().lambda().eq(EpcWorkerPool::getIdCard,epcWorkerPoolEditParam.getIdCard())
                .ne(EpcWorkerPool::getId,epcWorkerPoolEditParam.getId()));
        if(ObjectUtil.isNotEmpty(old)) {
            throw new CommonException("身份证：{}已存在", epcWorkerPoolEditParam.getIdCard());
        }

        EpcWorkerPool epcWorkerPool = this.queryEntity(epcWorkerPoolEditParam.getId());
        BeanUtil.copyProperties(epcWorkerPoolEditParam, epcWorkerPool);
        this.updateById(epcWorkerPool);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EpcWorkerPoolIdParam> epcWorkerPoolIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(epcWorkerPoolIdParamList, EpcWorkerPoolIdParam::getId));
    }

    @Override
    public EpcWorkerPool detail(EpcWorkerPoolIdParam epcWorkerPoolIdParam) {
        return this.queryEntity(epcWorkerPoolIdParam.getId());
    }

    @Override
    public EpcWorkerPool queryEntity(String id) {
        EpcWorkerPool epcWorkerPool = this.getById(id);
        if(ObjectUtil.isEmpty(epcWorkerPool)) {
            throw new CommonException("EPC-工人信息池不存在，id值为：{}", id);
        }
        return epcWorkerPool;
    }
}
