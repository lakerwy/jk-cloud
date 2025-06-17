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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorMember;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorMemberMapper;
import vip.xiaonuo.biz.modular.prj.por.service.PorMemberService;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目成员管理Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:40
 **/
@Service
@DS("slave_1")
public class PorMemberServiceImpl extends ServiceImpl<PorMemberMapper, PorMember> implements PorMemberService {

    @Override
    public Page<PorMember> page(PorMemberPageParam porMemberPageParam) {
        QueryWrapper<PorMember> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(porMemberPageParam.getPorProjectId())) {
            queryWrapper.lambda().eq(PorMember::getPorProjectId, porMemberPageParam.getPorProjectId());
        }
        if(ObjectUtil.isNotEmpty(porMemberPageParam.getUserName())) {
            queryWrapper.lambda().like(PorMember::getUserName, porMemberPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(porMemberPageParam.getUserId())) {
            queryWrapper.lambda().eq(PorMember::getUserId, porMemberPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(porMemberPageParam.getPhone())) {
            queryWrapper.lambda().like(PorMember::getPhone, porMemberPageParam.getPhone());
        }
        if(ObjectUtil.isNotEmpty(porMemberPageParam.getOrgName())) {
            queryWrapper.lambda().like(PorMember::getOrgName, porMemberPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(porMemberPageParam.getOrgId())) {
            queryWrapper.lambda().like(PorMember::getOrgId, porMemberPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(porMemberPageParam.getRoleName())) {
            queryWrapper.lambda().like(PorMember::getRoleName, porMemberPageParam.getRoleName());
        }
        if(ObjectUtil.isAllNotEmpty(porMemberPageParam.getSortField(), porMemberPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porMemberPageParam.getSortOrder());
            queryWrapper.orderBy(true, porMemberPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porMemberPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PorMember::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorMemberAddParam porMemberAddParam) {
        PorMember porMember = BeanUtil.toBean(porMemberAddParam, PorMember.class);
        this.save(porMember);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorMemberEditParam porMemberEditParam) {
        PorMember porMember = this.queryEntity(porMemberEditParam.getId());
        BeanUtil.copyProperties(porMemberEditParam, porMember);
        this.updateById(porMember);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorMemberIdParam> porMemberIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porMemberIdParamList, PorMemberIdParam::getId));
    }

    @Override
    public PorMember detail(PorMemberIdParam porMemberIdParam) {
        return this.queryEntity(porMemberIdParam.getId());
    }

    @Override
    public PorMember queryEntity(String id) {
        PorMember porMember = this.getById(id);
        if(ObjectUtil.isEmpty(porMember)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目成员管理不存在，id值为：{}", id);
        }
        return porMember;
    }
}
