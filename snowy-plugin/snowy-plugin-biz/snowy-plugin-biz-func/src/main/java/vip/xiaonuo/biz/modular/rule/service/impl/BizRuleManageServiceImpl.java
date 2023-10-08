package vip.xiaonuo.biz.modular.rule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.rule.entity.BizRuleManage;
import vip.xiaonuo.biz.modular.rule.mapper.BizRuleMapper;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeAddParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeEditParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangeIdParam;
import vip.xiaonuo.biz.modular.rule.param.BizRuleMangePageParam;
import vip.xiaonuo.biz.modular.rule.service.BizRuleManageService;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

@DS("slave_1")
@Service
public class BizRuleManageServiceImpl extends ServiceImpl<BizRuleMapper, BizRuleManage> implements BizRuleManageService {
    @Override
    public Page<BizRuleManage> page(BizRuleMangePageParam bizRuleMangePageParam) {
        QueryWrapper<BizRuleManage> qw = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(bizRuleMangePageParam.getTitle())) {
            qw.lambda().eq(BizRuleManage::getTitle, bizRuleMangePageParam.getTitle());
        }
        return this.page(CommonPageRequest.defaultPage(), qw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizRuleMangeAddParam bizRuleMangeAddParam) {
        BizRuleManage bizRuleManage = BeanUtil.toBean(bizRuleMangeAddParam, BizRuleManage.class);
        this.save(bizRuleManage);
    }

    @Override
    public BizRuleManage queryEntity(String id) {
        BizRuleManage bizRuleManage = this.getById(id);
        if (ObjectUtil.isEmpty(bizRuleManage)) {
            throw new CommonException("业务表-规章表不存在", id);
        }
        return bizRuleManage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizRuleMangeEditParam bizRuleMangeEditParam) {
        BizRuleManage bizRuleManage = this.queryEntity(bizRuleMangeEditParam.getId());
        BeanUtil.copyProperties(bizRuleMangeEditParam, bizRuleManage);
        this.updateById(bizRuleManage);
    }


    @Override
    public BizRuleManage detail(BizRuleMangeIdParam bizRuleMangeIdParam) {
        return this.queryEntity(bizRuleMangeIdParam.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizRuleMangeIdParam> bizRuleMangeIdParamList) {
        this.removeByIds(CollStreamUtil.toList(bizRuleMangeIdParamList, BizRuleMangeIdParam::getId));
    }
}
