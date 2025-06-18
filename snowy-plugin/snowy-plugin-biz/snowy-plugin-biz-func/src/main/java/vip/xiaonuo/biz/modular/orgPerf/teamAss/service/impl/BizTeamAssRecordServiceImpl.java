package vip.xiaonuo.biz.modular.orgPerf.teamAss.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAss;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.mapper.BizTeamAssRecordMapper;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.service.BizTeamAssRecordService;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.service.BizTeamAssService;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.vo.TeamAssRecordVo;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.vo.TeamAssScoreVo;
import vip.xiaonuo.biz.modular.task.entity.BizDeptAss;
import vip.xiaonuo.biz.modular.task.param.BizDeptAssRecordsIdParam;
import vip.xiaonuo.biz.modular.task.vo.DeptAssScore;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.entity.BizTeamAssRecord;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordAddParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordEditParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordIdParam;
import vip.xiaonuo.biz.modular.orgPerf.teamAss.param.BizTeamAssRecordPageParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班子考核记录Service接口实现类
 *
 * @author pzh
 * @date  2025/06/17 17:08
 **/
@Service
@DS("slave_1")
public class BizTeamAssRecordServiceImpl extends ServiceImpl<BizTeamAssRecordMapper, BizTeamAssRecord> implements BizTeamAssRecordService {

    @Resource
    private BizTeamAssService bizTeamAssService;

    @Resource
    private BizTeamAssRecordMapper bizTeamAssRecordMapper;

    @Override
    public Page<TeamAssRecordVo> page(BizTeamAssRecordPageParam bizTeamAssRecordPageParam) {
        QueryWrapper<BizTeamAssRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(bizTeamAssRecordPageParam.getAssYear())) {
            queryWrapper.eq("a.ASS_YEAR", bizTeamAssRecordPageParam.getAssYear());
        }
        if(ObjectUtil.isNotEmpty(bizTeamAssRecordPageParam.getAssessorId())) {
            queryWrapper.eq("a.ASSESSOR_ID", bizTeamAssRecordPageParam.getAssessorId());
        }
        if(ObjectUtil.isNotEmpty(bizTeamAssRecordPageParam.getAssessorName())) {
            queryWrapper.like("a.ASSESSOR_NAME", bizTeamAssRecordPageParam.getAssessorName());
        }
        if(ObjectUtil.isAllNotEmpty(bizTeamAssRecordPageParam.getSortField(), bizTeamAssRecordPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTeamAssRecordPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTeamAssRecordPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTeamAssRecordPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.ASS_YEAR").orderByAsc("a.ASSESSOR_ID").orderByDesc("a.CREATE_TIME");
        }
        return bizTeamAssRecordMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTeamAssRecordAddParam bizTeamAssRecordAddParam) {
        BizTeamAssRecord bizTeamAssRecord = BeanUtil.toBean(bizTeamAssRecordAddParam, BizTeamAssRecord.class);
        this.save(bizTeamAssRecord);
        // 反更新班子考核分
        if(StrUtil.isNotEmpty(bizTeamAssRecordAddParam.getAssId())) {
            TeamAssScoreVo teamAssScoreVo = bizTeamAssRecordMapper.getTeamAssScore(bizTeamAssRecord.getAssId());
            BizTeamAss bizTeamAss = bizTeamAssService.queryEntity(bizTeamAssRecordAddParam.getAssId());
            bizTeamAss.setAddScore(teamAssScoreVo.getAddScore());
            bizTeamAss.setMinusScore(teamAssScoreVo.getMinusScore());
            bizTeamAssService.updateById(bizTeamAss);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTeamAssRecordEditParam bizTeamAssRecordEditParam) {
        BizTeamAssRecord bizTeamAssRecord = this.queryEntity(bizTeamAssRecordEditParam.getId());
        BeanUtil.copyProperties(bizTeamAssRecordEditParam, bizTeamAssRecord);
        this.updateById(bizTeamAssRecord);
        // 反更新班子考核分
        if(StrUtil.isNotEmpty(bizTeamAssRecordEditParam.getAssId())) {
            TeamAssScoreVo teamAssScoreVo = bizTeamAssRecordMapper.getTeamAssScore(bizTeamAssRecord.getAssId());
            BizTeamAss bizTeamAss = bizTeamAssService.queryEntity(bizTeamAssRecordEditParam.getAssId());
            bizTeamAss.setAddScore(teamAssScoreVo.getAddScore());
            bizTeamAss.setMinusScore(teamAssScoreVo.getMinusScore());
            bizTeamAssService.updateById(bizTeamAss);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTeamAssRecordIdParam> bizTeamAssRecordIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(bizTeamAssRecordIdParamList, BizTeamAssRecordIdParam::getId);
        //删除集合
        List<String> assIds = this.listByIds(ids).stream().map(BizTeamAssRecord::getAssId).collect(Collectors.toList());
        // 执行删除
        this.removeByIds(ids);
        // 反更新班子考核分
        if(CollUtil.isNotEmpty(assIds)) {
            List<BizTeamAss> assList = new ArrayList<>();
            for (String assId:assIds) {
                TeamAssScoreVo teamAssScoreVo = bizTeamAssRecordMapper.getTeamAssScore(assId);
                BizTeamAss bizTeamAss = bizTeamAssService.queryEntity(assId);
                bizTeamAss.setAddScore(teamAssScoreVo.getAddScore());
                bizTeamAss.setMinusScore(teamAssScoreVo.getMinusScore());
                assList.add(bizTeamAss);
            }
            bizTeamAssService.updateBatchById(assList);
        }
    }

    @Override
    public BizTeamAssRecord detail(BizTeamAssRecordIdParam bizTeamAssRecordIdParam) {
        return this.queryEntity(bizTeamAssRecordIdParam.getId());
    }

    @Override
    public BizTeamAssRecord queryEntity(String id) {
        BizTeamAssRecord bizTeamAssRecord = this.getById(id);
        if(ObjectUtil.isEmpty(bizTeamAssRecord)) {
            throw new CommonException("班子考核记录不存在，id值为：{}", id);
        }
        return bizTeamAssRecord;
    }
}
