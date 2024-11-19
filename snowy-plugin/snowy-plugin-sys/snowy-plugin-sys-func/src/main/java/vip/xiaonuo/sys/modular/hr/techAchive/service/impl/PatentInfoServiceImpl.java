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
import vip.xiaonuo.sys.modular.hr.techAchive.entity.PatentInfo;
import vip.xiaonuo.sys.modular.hr.techAchive.mapper.PatentInfoMapper;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.PatentInfoPageParam;
import vip.xiaonuo.sys.modular.hr.techAchive.service.PatentInfoService;

import java.util.List;

/**
 * 专利信息Service接口实现类
 *
 * @author pzh
 * @date  2024/10/17 10:22
 **/
@Service
public class PatentInfoServiceImpl extends ServiceImpl<PatentInfoMapper, PatentInfo> implements PatentInfoService {

    @Override
    public Page<PatentInfo> page(PatentInfoPageParam patentInfoPageParam) {
        QueryWrapper<PatentInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(patentInfoPageParam.getPersonnelId())){
            queryWrapper.lambda().like(PatentInfo::getInventorId, patentInfoPageParam.getPersonnelId());
        }
        if(ObjectUtil.isNotEmpty(patentInfoPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(PatentInfo::getName,patentInfoPageParam.getSearchKey())
                    .or().like(PatentInfo::getApplyType,patentInfoPageParam.getSearchKey())
                    .or().like(PatentInfo::getApplyNo,patentInfoPageParam.getSearchKey())
                    .or().like(PatentInfo::getPatentNo,patentInfoPageParam.getSearchKey())
                    .or().like(PatentInfo::getInventor,patentInfoPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isAllNotEmpty(patentInfoPageParam.getSortField(), patentInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(patentInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, patentInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(patentInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PatentInfo::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PatentInfoAddParam patentInfoAddParam) {
        PatentInfo patentInfo = BeanUtil.toBean(patentInfoAddParam, PatentInfo.class);
        this.save(patentInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PatentInfoEditParam patentInfoEditParam) {
        PatentInfo patentInfo = this.queryEntity(patentInfoEditParam.getId());
        BeanUtil.copyProperties(patentInfoEditParam, patentInfo);
        this.updateById(patentInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PatentInfoIdParam> patentInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(patentInfoIdParamList, PatentInfoIdParam::getId));
    }

    @Override
    public PatentInfo detail(PatentInfoIdParam patentInfoIdParam) {
        return this.queryEntity(patentInfoIdParam.getId());
    }

    @Override
    public PatentInfo queryEntity(String id) {
        PatentInfo patentInfo = this.getById(id);
        if(ObjectUtil.isEmpty(patentInfo)) {
            throw new CommonException("专利信息不存在，id值为：{}", id);
        }
        return patentInfo;
    }
}
