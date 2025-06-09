package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.dev33.satoken.stp.StpUtil;
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
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
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
import java.util.stream.Collectors;

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

    @Resource
    private BizOrgService bizOrgService;

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

    @Override
    public Page<PorProjectVo> pageList(PorProjectPageParam porProjectPageParam) {
        QueryWrapper<PorProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectId())) {
            queryWrapper.eq("a.PROJECT_ID", porProjectPageParam.getProjectId());
        }
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getProjectStatus())) {
            queryWrapper.eq("a.PROJECT_STATUS", porProjectPageParam.getProjectStatus());
        }
        if(ObjectUtil.isNotEmpty(porProjectPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.NAME",porProjectPageParam.getSearchKey()));
        }

        // 校验数据范围
        // 责任人
        String userId = StpUtil.getLoginIdAsString();
        // 所属部门
        String dutyDeptId = StpLoginUserUtil.getLoginUser().getOrgId();
        // 所属部门级以下
        List<String> dutyDeptList = bizOrgService.getChildListById(bizOrgService.getAllOrgList(), dutyDeptId, true).stream().map(x->x.getId()).collect(Collectors.toList());
        // 接口数据权限设置
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();

        /**
         * 公司领导层-查看所有项目数据
         * 项目责任人-查看、编辑自己责任项目
         * 责任部门负责人-查看归属部门的项目数据
         * 项目主要成员-查看参与项目数据
         */
        queryWrapper.and(q->{
            q.eq("a.DUTY_ID",userId);   //责任人
            q.or().apply("a.ID in (select member.POR_PROJECT_ID from BIZ_POR_MEMBER member where member.DELETE_FLAG='NOT_DELETE' and member.USER_ID = {0}) ",userId); //项目成员
            if(ObjectUtil.isNotEmpty(loginUserDataScope)){
                q.or().in("a.DUTY_DEPT_ID",loginUserDataScope); // 权限设置
            }
            if(CollUtil.isNotEmpty(dutyDeptList)){
                q.or().in("a.DUTY_DEPT_ID",dutyDeptList); //责任部门及以下
            }
        });

        if(ObjectUtil.isAllNotEmpty(porProjectPageParam.getSortField(), porProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, porProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porProjectPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
