package vip.xiaonuo.sys.modular.hr.honorAward.service.impl;

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
import vip.xiaonuo.sys.modular.hr.honorAward.entity.HonorAward;
import vip.xiaonuo.sys.modular.hr.honorAward.mapper.HonorAwardMapper;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardAddParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardEditParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardIdParam;
import vip.xiaonuo.sys.modular.hr.honorAward.param.HonorAwardPageParam;
import vip.xiaonuo.sys.modular.hr.honorAward.service.HonorAwardService;

import java.util.List;

/**
 * 项目成果Service接口实现类
 *
 * @author pzh
 * @date  2024/10/16 15:18
 **/
@Service
public class HonorAwardServiceImpl extends ServiceImpl<HonorAwardMapper, HonorAward> implements HonorAwardService {

    @Override
    public Page<HonorAward> page(HonorAwardPageParam honorAwardPageParam) {
        QueryWrapper<HonorAward> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(honorAwardPageParam.getPersonnelId())){
            queryWrapper.apply("FIND_IN_SET({0}, COMPLETED_PERSONNEL_ID) > 0", honorAwardPageParam.getPersonnelId());
        }
        if(ObjectUtil.isNotEmpty(honorAwardPageParam.getType())){
            queryWrapper.lambda().eq(HonorAward::getType, honorAwardPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(honorAwardPageParam.getPrjName())){
            queryWrapper.lambda().like(HonorAward::getPrjName, honorAwardPageParam.getPrjName());
        }
        if(ObjectUtil.isNotEmpty(honorAwardPageParam.getProfession())){
            queryWrapper.lambda().eq(HonorAward::getProfession, honorAwardPageParam.getProfession());
        }
        if(ObjectUtil.isNotEmpty(honorAwardPageParam.getLevel())){
            queryWrapper.lambda().eq(HonorAward::getLevel, honorAwardPageParam.getLevel());
        }
        if(ObjectUtil.isNotEmpty(honorAwardPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(HonorAward::getPrjName,honorAwardPageParam.getSearchKey())
                    .or().like(HonorAward::getActivityName,honorAwardPageParam.getSearchKey())
                    .or().like(HonorAward::getAwardingOrg,honorAwardPageParam.getSearchKey())
                    .or().like(HonorAward::getHoldingOrg,honorAwardPageParam.getSearchKey())
                    .or().like(HonorAward::getCompletedPersonnel,honorAwardPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(honorAwardPageParam.getSortField(), honorAwardPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(honorAwardPageParam.getSortOrder());
            queryWrapper.orderBy(true, honorAwardPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(honorAwardPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(HonorAward::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HonorAwardAddParam honorAwardAddParam) {
        HonorAward honorAward = BeanUtil.toBean(honorAwardAddParam, HonorAward.class);
        this.save(honorAward);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HonorAwardEditParam honorAwardEditParam) {
        HonorAward honorAward = this.queryEntity(honorAwardEditParam.getId());
        BeanUtil.copyProperties(honorAwardEditParam, honorAward);
        this.updateById(honorAward);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HonorAwardIdParam> honorAwardIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(honorAwardIdParamList, HonorAwardIdParam::getId));
    }

    @Override
    public HonorAward detail(HonorAwardIdParam honorAwardIdParam) {
        return this.queryEntity(honorAwardIdParam.getId());
    }

    @Override
    public HonorAward queryEntity(String id) {
        HonorAward honorAward = this.getById(id);
        if(ObjectUtil.isEmpty(honorAward)) {
            throw new CommonException("项目成果不存在，id值为：{}", id);
        }
        return honorAward;
    }
}
