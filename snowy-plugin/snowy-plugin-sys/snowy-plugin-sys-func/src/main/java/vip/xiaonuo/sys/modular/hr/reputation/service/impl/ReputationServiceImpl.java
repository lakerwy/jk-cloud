package vip.xiaonuo.sys.modular.hr.reputation.service.impl;

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
import vip.xiaonuo.sys.modular.hr.reputation.entity.Reputation;
import vip.xiaonuo.sys.modular.hr.reputation.mapper.ReputationMapper;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationAddParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationEditParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationIdParam;
import vip.xiaonuo.sys.modular.hr.reputation.param.ReputationPageParam;
import vip.xiaonuo.sys.modular.hr.reputation.service.ReputationService;

import java.util.List;

/**
 * 信誉情况Service接口实现类
 *
 * @author pzh
 * @date  2024/10/18 16:52
 **/
@Service
public class ReputationServiceImpl extends ServiceImpl<ReputationMapper, Reputation> implements ReputationService {

    @Override
    public Page<Reputation> page(ReputationPageParam reputationPageParam) {
        QueryWrapper<Reputation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(reputationPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Reputation::getUnitName,reputationPageParam.getSearchKey())
                    .or().like(Reputation::getReason,reputationPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(reputationPageParam.getSortField(), reputationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(reputationPageParam.getSortOrder());
            queryWrapper.orderBy(true, reputationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(reputationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Reputation::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ReputationAddParam reputationAddParam) {
        Reputation reputation = BeanUtil.toBean(reputationAddParam, Reputation.class);
        this.save(reputation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ReputationEditParam reputationEditParam) {
        Reputation reputation = this.queryEntity(reputationEditParam.getId());
        BeanUtil.copyProperties(reputationEditParam, reputation);
        this.updateById(reputation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ReputationIdParam> reputationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(reputationIdParamList, ReputationIdParam::getId));
    }

    @Override
    public Reputation detail(ReputationIdParam reputationIdParam) {
        return this.queryEntity(reputationIdParam.getId());
    }

    @Override
    public Reputation queryEntity(String id) {
        Reputation reputation = this.getById(id);
        if(ObjectUtil.isEmpty(reputation)) {
            throw new CommonException("信誉情况不存在，id值为：{}", id);
        }
        return reputation;
    }
}
