package vip.xiaonuo.biz.modular.orgPerf.teamAss.service.impl;

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
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAss;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.mapper.BizTeamAssMapper;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssPageParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.service.BizTeamAssService;

import java.util.List;

/**
 * 班子考核Service接口实现类
 *
 * @author pzh
 * @date  2025/06/17 16:23
 **/
@Service
@DS("slave_1")
public class BizTeamAssServiceImpl extends ServiceImpl<BizTeamAssMapper, BizTeamAss> implements BizTeamAssService {

    @Override
    public Page<BizTeamAss> page(BizTeamAssPageParam bizTeamAssPageParam) {
        QueryWrapper<BizTeamAss> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTeamAssPageParam.getAssessorId())){
            queryWrapper.lambda().eq(BizTeamAss::getAssessorId, bizTeamAssPageParam.getAssessorId());
        }
        if(ObjectUtil.isNotEmpty(bizTeamAssPageParam.getAssessorName())){
            queryWrapper.lambda().like(BizTeamAss::getAssessorName, bizTeamAssPageParam.getAssessorName());
        }
        if(ObjectUtil.isNotEmpty(bizTeamAssPageParam.getAssYear())) {
            queryWrapper.lambda().eq(BizTeamAss::getAssYear, bizTeamAssPageParam.getAssYear());
        }
        if(ObjectUtil.isAllNotEmpty(bizTeamAssPageParam.getSortField(), bizTeamAssPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTeamAssPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTeamAssPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTeamAssPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTeamAss::getId);
        }
        Page<BizTeamAss> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        for (BizTeamAss item : result.getRecords()) {
            item.setTotalScore(item.getAddScore().subtract(item.getMinusScore()));
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTeamAssAddParam bizTeamAssAddParam) {
        long count = this.count(new QueryWrapper<BizTeamAss>().lambda()
                .eq(BizTeamAss::getAssYear,bizTeamAssAddParam.getAssYear())
                .eq(BizTeamAss::getAssessorId,bizTeamAssAddParam.getAssessorId()));
        if(count > 0){
            throw new CommonException("当前考核人已存在{}年度数据",bizTeamAssAddParam.getAssYear());
        }
        BizTeamAss bizTeamAss = BeanUtil.toBean(bizTeamAssAddParam, BizTeamAss.class);
        this.save(bizTeamAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTeamAssEditParam bizTeamAssEditParam) {
        long count = this.count(new QueryWrapper<BizTeamAss>().lambda()
                .eq(BizTeamAss::getAssYear,bizTeamAssEditParam.getAssYear())
                .eq(BizTeamAss::getAssessorId,bizTeamAssEditParam.getAssessorId())
                .ne(BizTeamAss::getId, bizTeamAssEditParam.getId()));
        if(count > 0){
            throw new CommonException("当前考核人已存在{}年度数据", bizTeamAssEditParam.getAssYear());
        }
        BizTeamAss bizTeamAss = this.queryEntity(bizTeamAssEditParam.getId());
        BeanUtil.copyProperties(bizTeamAssEditParam, bizTeamAss);
        this.updateById(bizTeamAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTeamAssIdParam> bizTeamAssIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTeamAssIdParamList, BizTeamAssIdParam::getId));
    }

    @Override
    public BizTeamAss detail(BizTeamAssIdParam bizTeamAssIdParam) {
        BizTeamAss bizTeamAss = this.queryEntity(bizTeamAssIdParam.getId());
        bizTeamAss.setTotalScore(bizTeamAss.getAddScore().subtract(bizTeamAss.getMinusScore()));
        return bizTeamAss;
    }

    @Override
    public BizTeamAss queryEntity(String id) {
        BizTeamAss bizTeamAss = this.getById(id);
        if(ObjectUtil.isEmpty(bizTeamAss)) {
            throw new CommonException("班子考核不存在，id值为：{}", id);
        }
        return bizTeamAss;
    }
}
