package vip.xiaonuo.sys.modular.hr.branchRating.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
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
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchRating;
import vip.xiaonuo.sys.modular.hr.branchRating.enums.BranchRatingStatusEnum;
import vip.xiaonuo.sys.modular.hr.branchRating.mapper.BranchRatingMapper;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchRatingPublicService;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchRatingService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分公司评级Service接口实现类
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Service
public class BranchRatingServiceImpl extends ServiceImpl<BranchRatingMapper, BranchRating> implements BranchRatingService {

    @Resource
    private BranchRatingPublicService branchRatingPublicService;

    @Override
    public Page<BranchRating> page(BranchRatingPageParam branchRatingPageParam) {
        QueryWrapper<BranchRating> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(branchRatingPageParam.getSortField(), branchRatingPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchRatingPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchRatingPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchRatingPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BranchRating::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BranchRatingAddParam branchRatingAddParam) {
        BranchRating branchRating = BeanUtil.toBean(branchRatingAddParam, BranchRating.class);
        branchRating.setStatus(branchRatingPublicService.getPublic(branchRatingAddParam.getYear(),branchRatingAddParam.getQuarter()));
        this.save(branchRating);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BranchRatingEditParam branchRatingEditParam) {
        BranchRating branchRating = this.queryEntity(branchRatingEditParam.getId());
        BeanUtil.copyProperties(branchRatingEditParam, branchRating);
        branchRating.setStatus(branchRatingPublicService.getPublic(branchRatingEditParam.getYear(),branchRatingEditParam.getQuarter()));
        this.updateById(branchRating);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BranchRatingIdParam> branchRatingIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(branchRatingIdParamList, BranchRatingIdParam::getId));
    }

    @Override
    public BranchRating detail(BranchRatingIdParam branchRatingIdParam) {
        return this.queryEntity(branchRatingIdParam.getId());
    }

    @Override
    public BranchRating queryEntity(String id) {
        BranchRating branchRating = this.getById(id);
        if(ObjectUtil.isEmpty(branchRating)) {
            throw new CommonException("分公司评级不存在，id值为：{}", id);
        }
        return branchRating;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(ImportBranchRating input) {
        if(CollUtil.isEmpty(input.getImportData())){
            throw new CommonException("无有效导入数据");
        }
        List<BranchRating> addList = BeanUtil.copyToList(input.getImportData(), BranchRating.class);
        addList.forEach(x -> {
            x.setYear(input.getYear());
            x.setQuarter(input.getQuarter());
        });
        this.saveBatch(addList);
        // 设置公示状态
        BranchRatingStatusEditParam branchRatingStatusEditParam = new BranchRatingStatusEditParam();
        branchRatingStatusEditParam.setYear(input.getYear());
        branchRatingStatusEditParam.setQuarter(input.getQuarter());
        this.setStatus(branchRatingStatusEditParam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setStatus(BranchRatingStatusEditParam branchRatingStatusEditParam) {
        branchRatingPublicService.setPublic(branchRatingStatusEditParam.getYear(),branchRatingStatusEditParam.getQuarter());
        this.update(new LambdaUpdateWrapper<BranchRating>().set(BranchRating::getStatus, BranchRatingStatusEnum.NOT_PUBLIC.getValue()));
        this.update(new LambdaUpdateWrapper<BranchRating>().eq(BranchRating::getYear, branchRatingStatusEditParam.getYear()).eq(BranchRating::getQuarter, branchRatingStatusEditParam.getQuarter())
                .set(BranchRating::getStatus, BranchRatingStatusEnum.PUBLIC.getValue()));
    }

    @Override
    public List<BranchRating> getList(BranchRatingListParam branchRatingListParam) {
        LambdaQueryWrapper<BranchRating> queryWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(branchRatingListParam.getYear())){
            queryWrapper.eq(BranchRating::getYear, branchRatingListParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(branchRatingListParam.getQuarter())){
            queryWrapper.eq(BranchRating::getQuarter, branchRatingListParam.getQuarter());
        }
        if(ObjectUtil.isNotEmpty(branchRatingListParam.getArea())){
            queryWrapper.eq(BranchRating::getArea, branchRatingListParam.getArea());
        }
        if(ObjectUtil.isNotEmpty(branchRatingListParam.getLevel())){
            queryWrapper.eq(BranchRating::getLevel, branchRatingListParam.getLevel());
        }
        if(ObjectUtil.isNotEmpty(branchRatingListParam.getStatus())){
            queryWrapper.eq(BranchRating::getStatus, branchRatingListParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(branchRatingListParam.getSearchKey())){
            queryWrapper.and(q->q.like(BranchRating::getName, branchRatingListParam.getSearchKey())
                    .or().like(BranchRating::getAreaManager, branchRatingListParam.getSearchKey())
                    .or().like(BranchRating::getLevel, branchRatingListParam.getSearchKey()));
        }
        queryWrapper.orderByAsc(BranchRating::getArea).orderByDesc(BranchRating::getCreateTime);
        return this.list(queryWrapper);
    }
}
