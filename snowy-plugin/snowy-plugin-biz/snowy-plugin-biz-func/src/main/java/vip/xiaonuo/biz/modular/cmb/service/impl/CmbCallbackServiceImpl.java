package vip.xiaonuo.biz.modular.cmb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cmb.entity.CmbCallback;
import vip.xiaonuo.biz.modular.cmb.mapper.CmbCallbackMapper;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackAddParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackEditParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackIdParam;
import vip.xiaonuo.biz.modular.cmb.param.CmbCallbackPageParam;
import vip.xiaonuo.biz.modular.cmb.service.CmbCallbackService;

import java.util.List;

/**
 * 招商银行 - 回调记录Service接口实现类
 *
 * @author pzh
 * @date  2025/02/08 10:09
 **/
@Service
public class CmbCallbackServiceImpl extends ServiceImpl<CmbCallbackMapper, CmbCallback> implements CmbCallbackService {

    @Override
    public Page<CmbCallback> page(CmbCallbackPageParam cmbCallbackPageParam) {
        QueryWrapper<CmbCallback> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(cmbCallbackPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(CmbCallback::getType,cmbCallbackPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(cmbCallbackPageParam.getSortField(), cmbCallbackPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cmbCallbackPageParam.getSortOrder());
            queryWrapper.orderBy(true, cmbCallbackPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cmbCallbackPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(CmbCallback::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CmbCallbackAddParam cmbCallbackAddParam) {
        CmbCallback cmbCallback = BeanUtil.toBean(cmbCallbackAddParam, CmbCallback.class);
        this.save(cmbCallback);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CmbCallbackEditParam cmbCallbackEditParam) {
        CmbCallback cmbCallback = this.queryEntity(cmbCallbackEditParam.getId());
        BeanUtil.copyProperties(cmbCallbackEditParam, cmbCallback);
        this.updateById(cmbCallback);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CmbCallbackIdParam> cmbCallbackIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cmbCallbackIdParamList, CmbCallbackIdParam::getId));
    }

    @Override
    public CmbCallback detail(CmbCallbackIdParam cmbCallbackIdParam) {
        return this.queryEntity(cmbCallbackIdParam.getId());
    }

    @Override
    public CmbCallback queryEntity(String id) {
        CmbCallback cmbCallback = this.getById(id);
        if(ObjectUtil.isEmpty(cmbCallback)) {
            throw new CommonException("招商银行 - 回调记录不存在，id值为：{}", id);
        }
        return cmbCallback;
    }
}
