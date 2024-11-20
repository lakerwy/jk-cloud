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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.Standard;
import vip.xiaonuo.sys.modular.hr.techAchive.mapper.StandardMapper;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.StandardService;

import java.util.List;

/**
 * 标准规范Service接口实现类
 *
 * @author pzh
 * @date  2024/10/17 17:05
 **/
@Service
public class StandardServiceImpl extends ServiceImpl<StandardMapper, Standard> implements StandardService {

    @Override
    public Page<Standard> page(StandardPageParam standardPageParam) {
        QueryWrapper<Standard> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(standardPageParam.getName())){
            queryWrapper.lambda().like(Standard::getName, standardPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(standardPageParam.getStandardType())){
            queryWrapper.lambda().eq(Standard::getStandardType, standardPageParam.getStandardType());
        }
        if(ObjectUtil.isNotEmpty(standardPageParam.getPersonnelId())){
            queryWrapper.lambda().like(Standard::getParticipantsId, standardPageParam.getPersonnelId());
        }
        if(ObjectUtil.isNotEmpty(standardPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(Standard::getName,standardPageParam.getSearchKey())
                    .or().like(Standard::getType,standardPageParam.getSearchKey())
                    .or().like(Standard::getStandardType,standardPageParam.getSearchKey())
                    .or().like(Standard::getParticipants,standardPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(standardPageParam.getSortField(), standardPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(standardPageParam.getSortOrder());
            queryWrapper.orderBy(true, standardPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(standardPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Standard::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(StandardAddParam standardAddParam) {
        Standard standard = BeanUtil.toBean(standardAddParam, Standard.class);
        this.save(standard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(StandardEditParam standardEditParam) {
        Standard standard = this.queryEntity(standardEditParam.getId());
        BeanUtil.copyProperties(standardEditParam, standard);
        this.updateById(standard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<StandardIdParam> standardIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(standardIdParamList, StandardIdParam::getId));
    }

    @Override
    public Standard detail(StandardIdParam standardIdParam) {
        return this.queryEntity(standardIdParam.getId());
    }

    @Override
    public Standard queryEntity(String id) {
        Standard standard = this.getById(id);
        if(ObjectUtil.isEmpty(standard)) {
            throw new CommonException("标准规范不存在，id值为：{}", id);
        }
        return standard;
    }
}
