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
import vip.xiaonuo.biz.modular.prj.por.vo.PorProjectVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorProjectMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorProjectPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorProjectService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 项目管理Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
@Service
@DS("slave_1")
public class PorProjectServiceImpl extends ServiceImpl<PorProjectMapper, PorProject> implements PorProjectService {

    @Resource
    private PorProjectMapper porProjectMapper;

    @Override
    public Page<PorProjectVo> page(PorProjectPageParam porProjectPageParam) {
        QueryWrapper<PorProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectId())) {
            queryWrapper.eq("a.PROJECT_ID", porProjectPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectStatus())) {
            queryWrapper.eq("a.PROJECT_STATUS", porProjectPageParam.getProjectStatus());
        }
        if(ObjectUtil.isAllNotEmpty(porProjectPageParam.getSortField(), porProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, porProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porProjectPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorProjectAddParam porProjectAddParam) {
        PorProject porProject = BeanUtil.toBean(porProjectAddParam, PorProject.class);
        this.save(porProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorProjectEditParam porProjectEditParam) {
        PorProject porProject = this.queryEntity(porProjectEditParam.getId());
        BeanUtil.copyProperties(porProjectEditParam, porProject);
        this.updateById(porProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorProjectIdParam> porProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porProjectIdParamList, PorProjectIdParam::getId));
    }

    @Override
    public PorProjectVo detail(PorProjectIdParam porProjectIdParam) {
        return porProjectMapper.getDetail(porProjectIdParam.getId());
    }

    @Override
    public PorProject queryEntity(String id) {
        PorProject porProject = this.getById(id);
        if(ObjectUtil.isEmpty(porProject)) {
            throw new CommonException("项目产值台账 - 项目管理不存在，id值为：{}", id);
        }
        return porProject;
    }
}
