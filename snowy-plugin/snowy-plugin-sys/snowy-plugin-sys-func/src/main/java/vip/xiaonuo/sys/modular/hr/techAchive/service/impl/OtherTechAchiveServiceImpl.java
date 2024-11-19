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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.OtherTechAchive;
import vip.xiaonuo.sys.modular.hr.techAchive.mapper.OtherTechAchiveMapper;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchiveIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.OtherTechAchivePageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.OtherTechAchiveService;

import java.util.List;

/**
 * 其他（科技成果）Service接口实现类
 *
 * @author pzh
 * @date  2024/10/18 09:10
 **/
@Service
public class OtherTechAchiveServiceImpl extends ServiceImpl<OtherTechAchiveMapper, OtherTechAchive> implements OtherTechAchiveService {

    @Override
    public Page<OtherTechAchive> page(OtherTechAchivePageParam otherTechAchivePageParam) {
        QueryWrapper<OtherTechAchive> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(otherTechAchivePageParam.getPersonnelId())){
            queryWrapper.lambda().like(OtherTechAchive::getCompletedPersonnelId, otherTechAchivePageParam.getPersonnelId());
        }
        if(ObjectUtil.isNotEmpty(otherTechAchivePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(OtherTechAchive::getName,otherTechAchivePageParam.getSearchKey())
                    .or().like(OtherTechAchive::getType,otherTechAchivePageParam.getSearchKey())
                    .or().like(OtherTechAchive::getCompletedPersonnel,otherTechAchivePageParam.getSearchKey())
                    .or().like(OtherTechAchive::getIssueOrg,otherTechAchivePageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(otherTechAchivePageParam.getSortField(), otherTechAchivePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(otherTechAchivePageParam.getSortOrder());
            queryWrapper.orderBy(true, otherTechAchivePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(otherTechAchivePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(OtherTechAchive::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(OtherTechAchiveAddParam otherTechAchiveAddParam) {
        OtherTechAchive otherTechAchive = BeanUtil.toBean(otherTechAchiveAddParam, OtherTechAchive.class);
        this.save(otherTechAchive);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(OtherTechAchiveEditParam otherTechAchiveEditParam) {
        OtherTechAchive otherTechAchive = this.queryEntity(otherTechAchiveEditParam.getId());
        BeanUtil.copyProperties(otherTechAchiveEditParam, otherTechAchive);
        this.updateById(otherTechAchive);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<OtherTechAchiveIdParam> otherTechAchiveIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(otherTechAchiveIdParamList, OtherTechAchiveIdParam::getId));
    }

    @Override
    public OtherTechAchive detail(OtherTechAchiveIdParam otherTechAchiveIdParam) {
        return this.queryEntity(otherTechAchiveIdParam.getId());
    }

    @Override
    public OtherTechAchive queryEntity(String id) {
        OtherTechAchive otherTechAchive = this.getById(id);
        if(ObjectUtil.isEmpty(otherTechAchive)) {
            throw new CommonException("其他（科技成果）不存在，id值为：{}", id);
        }
        return otherTechAchive;
    }
}
