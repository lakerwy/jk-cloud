package vip.xiaonuo.biz.modular.crm.prjValue.service.impl;

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
import vip.xiaonuo.biz.modular.crm.prjValue.entity.PrjValue;
import vip.xiaonuo.biz.modular.crm.prjValue.mapper.PrjValueMapper;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueAddParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueEditParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValueIdParam;
import vip.xiaonuo.biz.modular.crm.prjValue.param.PrjValuePageParam;
import vip.xiaonuo.biz.modular.crm.prjValue.service.PrjValueService;

import java.util.List;

/**
 * 生产院及子公司产值Service接口实现类
 *
 * @author pzh
 * @date  2025/06/05 10:04
 **/
@Service
public class PrjValueServiceImpl extends ServiceImpl<PrjValueMapper, PrjValue> implements PrjValueService {

    @Override
    public Page<PrjValue> page(PrjValuePageParam prjValuePageParam) {
        QueryWrapper<PrjValue> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(prjValuePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(PrjValue::getDeptName, prjValuePageParam.getSearchKey()));
        }
        if(ObjectUtil.isNotEmpty(prjValuePageParam.getYear())){
            queryWrapper.lambda().eq(PrjValue::getYear, prjValuePageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(prjValuePageParam.getSeason())){
            queryWrapper.lambda().eq(PrjValue::getSeason, prjValuePageParam.getSeason());
        }
        if(ObjectUtil.isAllNotEmpty(prjValuePageParam.getSortField(), prjValuePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(prjValuePageParam.getSortOrder());
            queryWrapper.orderBy(true, prjValuePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(prjValuePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PrjValue::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjValueAddParam prjValueAddParam) {
        PrjValue prjValue = BeanUtil.toBean(prjValueAddParam, PrjValue.class);
        this.save(prjValue);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjValueEditParam prjValueEditParam) {
        PrjValue prjValue = this.queryEntity(prjValueEditParam.getId());
        BeanUtil.copyProperties(prjValueEditParam, prjValue);
        this.updateById(prjValue);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PrjValueIdParam> prjValueIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(prjValueIdParamList, PrjValueIdParam::getId));
    }

    @Override
    public PrjValue detail(PrjValueIdParam prjValueIdParam) {
        return this.queryEntity(prjValueIdParam.getId());
    }

    @Override
    public PrjValue queryEntity(String id) {
        PrjValue prjValue = this.getById(id);
        if(ObjectUtil.isEmpty(prjValue)) {
            throw new CommonException("生产院及子公司产值不存在，id值为：{}", id);
        }
        return prjValue;
    }
}
