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
import vip.xiaonuo.biz.modular.prj.por.vo.PorSubSettleVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSubSettle;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorSubSettleMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettleAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettleEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettleIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorSubSettlePageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorSubSettleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目分包结算记录Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:53
 **/
@Service
@DS("slave_1")
public class PorSubSettleServiceImpl extends ServiceImpl<PorSubSettleMapper, PorSubSettle> implements PorSubSettleService {

    @Resource
    private PorSubSettleMapper porSubSettleMapper;

    @Override
    public Page<PorSubSettleVo> page(PorSubSettlePageParam porSubSettlePageParam) {
        QueryWrapper<PorSubSettle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porSubSettlePageParam.getSubHetongId())) {
            queryWrapper.eq("a.SUB_HETONG_ID", porSubSettlePageParam.getSubHetongId());
        }
        if(ObjectUtil.isNotEmpty(porSubSettlePageParam.getSn())) {
            queryWrapper.like("a.SN", porSubSettlePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(porSubSettlePageParam.getSettleType())) {
            queryWrapper.eq("a.SETTLE_TYPE", porSubSettlePageParam.getSettleType());
        }
        if(ObjectUtil.isNotEmpty(porSubSettlePageParam.getStartSettleDate()) && ObjectUtil.isNotEmpty(porSubSettlePageParam.getEndSettleDate())) {
            queryWrapper.between("a.SETTLE_DATE", porSubSettlePageParam.getStartSettleDate(), porSubSettlePageParam.getEndSettleDate());
        }
        if(ObjectUtil.isNotEmpty(porSubSettlePageParam.getSettleStatus())) {
            queryWrapper.eq("a.SETTLE_STATUS", porSubSettlePageParam.getSettleStatus());
        }
        if(ObjectUtil.isAllNotEmpty(porSubSettlePageParam.getSortField(), porSubSettlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porSubSettlePageParam.getSortOrder());
            queryWrapper.orderBy(true, porSubSettlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porSubSettlePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porSubSettleMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorSubSettleAddParam porSubSettleAddParam) {
        PorSubSettle porSubSettle = BeanUtil.toBean(porSubSettleAddParam, PorSubSettle.class);
        this.save(porSubSettle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorSubSettleEditParam porSubSettleEditParam) {
        PorSubSettle porSubSettle = this.queryEntity(porSubSettleEditParam.getId());
        BeanUtil.copyProperties(porSubSettleEditParam, porSubSettle);
        this.updateById(porSubSettle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorSubSettleIdParam> porSubSettleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porSubSettleIdParamList, PorSubSettleIdParam::getId));
    }

    @Override
    public PorSubSettleVo detail(PorSubSettleIdParam porSubSettleIdParam) {
        return porSubSettleMapper.getDetail(porSubSettleIdParam.getId());
    }

    @Override
    public PorSubSettle queryEntity(String id) {
        PorSubSettle porSubSettle = this.getById(id);
        if(ObjectUtil.isEmpty(porSubSettle)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目分包结算记录不存在，id值为：{}", id);
        }
        return porSubSettle;
    }
}
