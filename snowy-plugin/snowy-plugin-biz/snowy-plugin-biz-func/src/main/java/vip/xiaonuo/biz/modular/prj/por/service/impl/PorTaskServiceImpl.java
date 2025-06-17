package vip.xiaonuo.biz.modular.prj.por.service.impl;

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
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.vo.PorTaskVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorTask;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorTaskMapper;
import vip.xiaonuo.biz.modular.prj.por.service.PorTaskService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目计划管理Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:42
 **/
@Service
@DS("slave_1")
public class PorTaskServiceImpl extends ServiceImpl<PorTaskMapper, PorTask> implements PorTaskService {

    @Resource
    private PorTaskMapper porTaskMapper;

    @Override
    public Page<PorTaskVo> page(PorTaskPageParam porTaskPageParam) {
        QueryWrapper<PorTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porTaskPageParam.getPorProjectId())) {
            queryWrapper.eq("a.POR_PROJECT_ID", porTaskPageParam.getPorProjectId());
        }
        if(ObjectUtil.isNotEmpty(porTaskPageParam.getTaskName())) {
            queryWrapper.like("a.TASK_NAME", porTaskPageParam.getTaskName());
        }
        if(ObjectUtil.isNotEmpty(porTaskPageParam.getTaskLeaderId())) {
            queryWrapper.eq("a.TASK_LEADER_ID", porTaskPageParam.getTaskLeaderId());
        }
        if(ObjectUtil.isNotEmpty(porTaskPageParam.getTaskLeaderName())) {
            queryWrapper.like("a.TASK_LEADER_NAME", porTaskPageParam.getTaskLeaderName());
        }
        if(ObjectUtil.isNotEmpty(porTaskPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.TASK_NAME",porTaskPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(porTaskPageParam.getSortField(), porTaskPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porTaskPageParam.getSortOrder());
            queryWrapper.orderBy(true, porTaskPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porTaskPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porTaskMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorTaskAddParam porTaskAddParam) {
        PorTask porTask = BeanUtil.toBean(porTaskAddParam, PorTask.class);
        this.save(porTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorTaskEditParam porTaskEditParam) {
        PorTask porTask = this.queryEntity(porTaskEditParam.getId());
        BeanUtil.copyProperties(porTaskEditParam, porTask);
        this.updateById(porTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorTaskIdParam> porTaskIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porTaskIdParamList, PorTaskIdParam::getId));
    }

    @Override
    public PorTaskVo detail(PorTaskIdParam porTaskIdParam) {
        return porTaskMapper.getDetail(porTaskIdParam.getId());
    }

    @Override
    public PorTask queryEntity(String id) {
        PorTask porTask = this.getById(id);
        if(ObjectUtil.isEmpty(porTask)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目计划管理不存在，id值为：{}", id);
        }
        return porTask;
    }
}
