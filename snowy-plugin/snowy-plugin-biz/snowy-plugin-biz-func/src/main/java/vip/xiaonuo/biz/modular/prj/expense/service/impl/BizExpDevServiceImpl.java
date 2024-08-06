package vip.xiaonuo.biz.modular.prj.expense.service.impl;

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
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpDev;
import vip.xiaonuo.biz.modular.prj.expense.mapper.BizExpDevMapper;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevPageParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpDevService;

import java.util.List;

/**
 * 财务管理 - 费用报销 - 研发项目信息表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/08/01 11:22
 **/

@DS("slave_1")
@Service
public class BizExpDevServiceImpl extends ServiceImpl<BizExpDevMapper, BizExpDev> implements BizExpDevService {

    @Override
    public Page<BizExpDev> page(BizExpDevPageParam bizExpDevPageParam) {
        QueryWrapper<BizExpDev> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizExpDevPageParam.getDept())) {
            queryWrapper.lambda().like(BizExpDev::getDept, bizExpDevPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(bizExpDevPageParam.getCode())) {
            queryWrapper.lambda().like(BizExpDev::getCode, bizExpDevPageParam.getCode());
        }
        if(ObjectUtil.isNotEmpty(bizExpDevPageParam.getSubject())) {
            queryWrapper.lambda().like(BizExpDev::getSubject, bizExpDevPageParam.getSubject());
        }
        if(ObjectUtil.isNotEmpty(bizExpDevPageParam.getSearchKey())) {
            queryWrapper.lambda().and(q->q.like(BizExpDev::getCode,bizExpDevPageParam.getSearchKey())
                    .or().like(BizExpDev::getDept,bizExpDevPageParam.getSearchKey())
                    .or().like(BizExpDev::getSubject,bizExpDevPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizExpDevPageParam.getSortField(), bizExpDevPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizExpDevPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizExpDevPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizExpDevPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizExpDev::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizExpDevAddParam bizExpDevAddParam) {
        BizExpDev bizExpDev = BeanUtil.toBean(bizExpDevAddParam, BizExpDev.class);
        this.save(bizExpDev);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizExpDevEditParam bizExpDevEditParam) {
        BizExpDev bizExpDev = this.queryEntity(bizExpDevEditParam.getId());
        BeanUtil.copyProperties(bizExpDevEditParam, bizExpDev);
        this.updateById(bizExpDev);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizExpDevIdParam> bizExpDevIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizExpDevIdParamList, BizExpDevIdParam::getId));
    }

    @Override
    public BizExpDev detail(BizExpDevIdParam bizExpDevIdParam) {
        return this.queryEntity(bizExpDevIdParam.getId());
    }

    @Override
    public BizExpDev queryEntity(String id) {
        BizExpDev bizExpDev = this.getById(id);
        if(ObjectUtil.isEmpty(bizExpDev)) {
            throw new CommonException("财务管理 - 费用报销 - 研发项目信息表不存在，id值为：{}", id);
        }
        return bizExpDev;
    }
}
