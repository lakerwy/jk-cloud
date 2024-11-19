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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.SoftwareCopyright;
import vip.xiaonuo.sys.modular.hr.techAchive.mapper.SoftwareCopyrightMapper;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.SoftwareCopyrightService;

import java.util.List;

/**
 * 软著信息Service接口实现类
 *
 * @author pzh
 * @date  2024/10/17 10:53
 **/
@Service
public class SoftwareCopyrightServiceImpl extends ServiceImpl<SoftwareCopyrightMapper, SoftwareCopyright> implements SoftwareCopyrightService {

    @Override
    public Page<SoftwareCopyright> page(SoftwareCopyrightPageParam softwareCopyrightPageParam) {
        QueryWrapper<SoftwareCopyright> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(softwareCopyrightPageParam.getName())){
            queryWrapper.lambda().eq(SoftwareCopyright::getName, softwareCopyrightPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(softwareCopyrightPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SoftwareCopyright::getName,softwareCopyrightPageParam.getSearchKey())
                    .or().like(SoftwareCopyright::getOwner,softwareCopyrightPageParam.getSearchKey())
                    .or().like(SoftwareCopyright::getRegisterNo,softwareCopyrightPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(softwareCopyrightPageParam.getSortField(), softwareCopyrightPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(softwareCopyrightPageParam.getSortOrder());
            queryWrapper.orderBy(true, softwareCopyrightPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(softwareCopyrightPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SoftwareCopyright::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SoftwareCopyrightAddParam softwareCopyrightAddParam) {
        SoftwareCopyright softwareCopyright = BeanUtil.toBean(softwareCopyrightAddParam, SoftwareCopyright.class);
        this.save(softwareCopyright);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SoftwareCopyrightEditParam softwareCopyrightEditParam) {
        SoftwareCopyright softwareCopyright = this.queryEntity(softwareCopyrightEditParam.getId());
        BeanUtil.copyProperties(softwareCopyrightEditParam, softwareCopyright);
        this.updateById(softwareCopyright);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SoftwareCopyrightIdParam> softwareCopyrightIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(softwareCopyrightIdParamList, SoftwareCopyrightIdParam::getId));
    }

    @Override
    public SoftwareCopyright detail(SoftwareCopyrightIdParam softwareCopyrightIdParam) {
        return this.queryEntity(softwareCopyrightIdParam.getId());
    }

    @Override
    public SoftwareCopyright queryEntity(String id) {
        SoftwareCopyright softwareCopyright = this.getById(id);
        if(ObjectUtil.isEmpty(softwareCopyright)) {
            throw new CommonException("软著信息不存在，id值为：{}", id);
        }
        return softwareCopyright;
    }
}
