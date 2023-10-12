package vip.xiaonuo.biz.modular.visit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.visit.entity.BizVisitList;
import vip.xiaonuo.biz.modular.visit.mapper.BizVisitListMapper;
import vip.xiaonuo.biz.modular.visit.param.BizVisitListAddParam;
import vip.xiaonuo.biz.modular.visit.param.BizVisitListEditParam;
import vip.xiaonuo.biz.modular.visit.param.BizVisitListIdParam;
import vip.xiaonuo.biz.modular.visit.param.BizVisitListPageParam;
import vip.xiaonuo.biz.modular.visit.service.BizVisitListService;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

public class BizVisitListServiceImpl extends ServiceImpl<BizVisitListMapper, BizVisitList> implements BizVisitListService {
    @Override
    public Page<BizVisitList> page(BizVisitListPageParam bizVisitListPageParam) {
        QueryWrapper<BizVisitList> queryWrapper = new QueryWrapper<>();
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public void add(BizVisitListAddParam bizVisitListAddParam) {
    BizVisitList bizVisitList = BeanUtil.toBean(bizVisitListAddParam,BizVisitList.class);
    this.save(bizVisitList);
    }

    @Override
    public void edit(BizVisitListEditParam bizVisitListEditParam) {
        BizVisitList bizVisitList = this.queryEntity(bizVisitListEditParam.getId());
        this.updateById(bizVisitList);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizVisitListIdParam> bizVisitListIdParam) {
    this.removeByIds(CollStreamUtil.toList(bizVisitListIdParam,BizVisitListIdParam::getId));
    }

    @Override
    public BizVisitList detail(BizVisitListIdParam bizVisitListIdParam) {
        return this.queryEntity(bizVisitListIdParam.getId());
    }

    @Override
    public BizVisitList queryEntity(String id) {
        BizVisitList bizVisitList = this.getById(id);
        if (ObjectUtil.isEmpty(bizVisitList)){
            throw new CommonException("业务表-外访计划表，id值为：{}", id);
        }
        return bizVisitList;
    }
}
