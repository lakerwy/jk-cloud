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
package vip.xiaonuo.biz.modular.visit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.visit.entity.BizVisitList;
import vip.xiaonuo.biz.modular.visit.param.*;
import vip.xiaonuo.biz.modular.visit.service.BizVisitListService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.visit.entity.BizVisit;
import vip.xiaonuo.biz.modular.visit.mapper.BizVisitMapper;
import vip.xiaonuo.biz.modular.visit.service.BizVisitService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 外访表Service接口实现类
 *
 * @author kaiminglu
 * @date 2023/10/12 10:35
 **/
@Service
public class BizVisitServiceImpl extends ServiceImpl<BizVisitMapper, BizVisit> implements BizVisitService {
    @Resource
    BizVisitListService bizVisitListService;

    @Override
    public Page<BizVisit> page(BizVisitPageParam bizVisitPageParam) {
        QueryWrapper<BizVisit> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isAllNotEmpty(bizVisitPageParam.getSortField(), bizVisitPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizVisitPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizVisitPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizVisitPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizVisit::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizVisitAddParam bizVisitAddParam) {
        BizVisit bizVisit = BeanUtil.toBean(bizVisitAddParam, BizVisit.class);
        this.save(bizVisit);
        if (CollUtil.isNotEmpty(bizVisitAddParam.getParamList())) {
            List<BizVisitList> bizVisitList = bizVisitAddParam.getParamList();
            bizVisitList.forEach(v -> {
                v.setKey(bizVisit.getId());
            });
            bizVisitListService.saveBatch(bizVisitList);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizVisitEditParam bizVisitEditParam) {
        BizVisit bizVisit = this.queryEntity(bizVisitEditParam.getId());
        BeanUtil.copyProperties(bizVisitEditParam, bizVisit);
        this.updateById(bizVisit);
        if (CollUtil.isNotEmpty(bizVisitEditParam.getParamList())) {
            //本次传入的数据
            List<BizVisitList> bizVisitList = bizVisitEditParam.getParamList();
            bizVisitList.forEach(v -> {
                v.setKey(bizVisit.getId());
            });
            List<BizVisitList> oldVizVisitList = bizVisitListService.list(
                    new QueryWrapper<BizVisitList>().lambda().eq(BizVisitList::getId, bizVisit.getId()));
            List<String> oldIds = oldVizVisitList.stream().map(v -> v.getId()).collect(Collectors.toList());
            //待新增
            List<BizVisitList> addList = bizVisitList.stream().filter(v -> !oldIds.contains(v.getId())).collect(Collectors.toList());
            //待更新
            List<BizVisitList> updateList = bizVisitList.stream().filter(v -> oldIds.contains(v.getId())).collect(Collectors.toList());
            //待删除的
            List<String> uodateIds = updateList.stream().map(v -> v.getId()).collect(Collectors.toList());
            List<BizVisitList> removeList = oldVizVisitList.stream().filter(v -> !uodateIds.contains(v.getId())).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(addList)) {
                bizVisitListService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                bizVisitListService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                bizVisitListService.removeByIds(removeList);
            }
        }
        ;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizVisitIdParam> bizVisitIdParamList) {
        List<String> ids = CollStreamUtil.toList(bizVisitIdParamList, BizVisitIdParam::getId);
        // 执行删除
        this.removeByIds(ids);
        bizVisitListService.remove((new QueryWrapper<BizVisitList>().lambda().in(BizVisitList::getKey, ids)));
    }

    @Override
    public BizVisit detail(BizVisitIdParam bizVisitIdParam) {
        return this.queryEntity(bizVisitIdParam.getId());
    }

    @Override
    public BizVisit queryEntity(String id) {
        BizVisit bizVisit = this.getById(id);
        if (ObjectUtil.isEmpty(bizVisit)) {
            throw new CommonException("外访表不存在，id值为：{}", id);
        }
        return bizVisit;
    }
}
