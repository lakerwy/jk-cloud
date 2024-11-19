package vip.xiaonuo.sys.modular.hr.techAchive.service.impl;

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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.NoveltySearch;
import vip.xiaonuo.sys.modular.hr.techAchive.mapper.NoveltySearchMapper;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.NoveltySearchService;

import java.util.List;

/**
 * 查新Service接口实现类
 *
 * @author pzh
 * @date  2024/10/18 08:44
 **/
@Service
public class NoveltySearchServiceImpl extends ServiceImpl<NoveltySearchMapper, NoveltySearch> implements NoveltySearchService {

    @Override
    public Page<NoveltySearch> page(NoveltySearchPageParam noveltySearchPageParam) {
        QueryWrapper<NoveltySearch> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(noveltySearchPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(NoveltySearch::getPrjName,noveltySearchPageParam.getSearchKey())
                    .or().like(NoveltySearch::getReportNo,noveltySearchPageParam.getSearchKey())
                    .or().like(NoveltySearch::getPurpose,noveltySearchPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(noveltySearchPageParam.getSortField(), noveltySearchPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(noveltySearchPageParam.getSortOrder());
            queryWrapper.orderBy(true, noveltySearchPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(noveltySearchPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(NoveltySearch::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(NoveltySearchAddParam noveltySearchAddParam) {
        NoveltySearch noveltySearch = BeanUtil.toBean(noveltySearchAddParam, NoveltySearch.class);
        this.save(noveltySearch);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(NoveltySearchEditParam noveltySearchEditParam) {
        NoveltySearch noveltySearch = this.queryEntity(noveltySearchEditParam.getId());
        BeanUtil.copyProperties(noveltySearchEditParam, noveltySearch);
        this.updateById(noveltySearch);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<NoveltySearchIdParam> noveltySearchIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(noveltySearchIdParamList, NoveltySearchIdParam::getId));
    }

    @Override
    public NoveltySearch detail(NoveltySearchIdParam noveltySearchIdParam) {
        return this.queryEntity(noveltySearchIdParam.getId());
    }

    @Override
    public NoveltySearch queryEntity(String id) {
        NoveltySearch noveltySearch = this.getById(id);
        if(ObjectUtil.isEmpty(noveltySearch)) {
            throw new CommonException("查新不存在，id值为：{}", id);
        }
        return noveltySearch;
    }
}
