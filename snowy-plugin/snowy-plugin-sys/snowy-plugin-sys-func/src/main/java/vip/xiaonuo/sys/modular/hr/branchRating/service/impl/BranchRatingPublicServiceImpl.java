package vip.xiaonuo.sys.modular.hr.branchRating.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchRatingPublic;
import vip.xiaonuo.sys.modular.hr.branchRating.enums.BranchRatingStatusEnum;
import vip.xiaonuo.sys.modular.hr.branchRating.mapper.BranchRatingPublicMapper;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicAddParam;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicEditParam;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicIdParam;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicPageParam;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchRatingPublicService;

import java.util.List;

/**
 * 分公司评级-公示年份和季度Service接口实现类
 *
 * @author pzh
 * @date  2024/09/04 10:21
 **/
@Service
public class BranchRatingPublicServiceImpl extends ServiceImpl<BranchRatingPublicMapper, BranchRatingPublic> implements BranchRatingPublicService {

    @Override
    public Page<BranchRatingPublic> page(BranchRatingPublicPageParam branchRatingPublicPageParam) {
        QueryWrapper<BranchRatingPublic> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(branchRatingPublicPageParam.getSortField(), branchRatingPublicPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchRatingPublicPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchRatingPublicPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchRatingPublicPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BranchRatingPublic::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BranchRatingPublicAddParam branchRatingPublicAddParam) {
        BranchRatingPublic branchRatingPublic = BeanUtil.toBean(branchRatingPublicAddParam, BranchRatingPublic.class);
        this.save(branchRatingPublic);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BranchRatingPublicEditParam branchRatingPublicEditParam) {
        BranchRatingPublic branchRatingPublic = this.queryEntity(branchRatingPublicEditParam.getId());
        BeanUtil.copyProperties(branchRatingPublicEditParam, branchRatingPublic);
        this.updateById(branchRatingPublic);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BranchRatingPublicIdParam> branchRatingPublicIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(branchRatingPublicIdParamList, BranchRatingPublicIdParam::getId));
    }

    @Override
    public BranchRatingPublic detail(BranchRatingPublicIdParam branchRatingPublicIdParam) {
        return this.queryEntity(branchRatingPublicIdParam.getId());
    }

    @Override
    public BranchRatingPublic queryEntity(String id) {
        BranchRatingPublic branchRatingPublic = this.getById(id);
        if(ObjectUtil.isEmpty(branchRatingPublic)) {
            throw new CommonException("分公司评级-公示年份和季度不存在，id值为：{}", id);
        }
        return branchRatingPublic;
    }

    @Override
    public BranchRatingPublic getCurPublic() {
        return this.getOne(new LambdaUpdateWrapper<BranchRatingPublic>()
                .orderByDesc(BranchRatingPublic::getCreateTime).last(" limit 1 "));
    }

    @Override
    public String getPublic(String year, String quarter) {
        BranchRatingPublic branchRatingPublic = this.getCurPublic();
        if(ObjectUtil.isNotEmpty(branchRatingPublic)
                && branchRatingPublic.getYear().equals(year)
                && branchRatingPublic.getQuarter().equals(quarter)) {
            return BranchRatingStatusEnum.PUBLIC.getValue();
        }else {
            return BranchRatingStatusEnum.NOT_PUBLIC.getValue();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setPublic(String year, String quarter) {
        BranchRatingPublic branchRatingPublic = this.getOne(new LambdaQueryWrapper<BranchRatingPublic>()
                .orderByDesc(BranchRatingPublic::getCreateTime).last(" limit 1 "));
        BranchRatingPublic newBranchRatingPublic = new BranchRatingPublic();
        newBranchRatingPublic.setYear(year);
        newBranchRatingPublic.setQuarter(quarter);
        if(ObjectUtil.isNotEmpty(branchRatingPublic)){
            newBranchRatingPublic.setId(branchRatingPublic.getId());
        }
        this.saveOrUpdate(newBranchRatingPublic);
    }
}
