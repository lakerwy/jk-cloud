package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.por.vo.PorOwnerSettleVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOwnerSettle;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorOwnerSettleMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettleAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettleEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettleIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOwnerSettlePageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorOwnerSettleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目业主结算记录Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:52
 **/
@Service
@DS("slave_1")
public class PorOwnerSettleServiceImpl extends ServiceImpl<PorOwnerSettleMapper, PorOwnerSettle> implements PorOwnerSettleService {

    @Resource
    private PorOwnerSettleMapper porOwnerSettleMapper;

    @Override
    public Page<PorOwnerSettleVo> page(PorOwnerSettlePageParam porOwnerSettlePageParam) {
        QueryWrapper<PorOwnerSettle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porOwnerSettlePageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", porOwnerSettlePageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(porOwnerSettlePageParam.getSn())) {
            queryWrapper.like("a.SN", porOwnerSettlePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(porOwnerSettlePageParam.getSettleType())) {
            queryWrapper.eq("a.SETTLE_TYPE", porOwnerSettlePageParam.getSettleType());
        }
        if(ObjectUtil.isNotEmpty(porOwnerSettlePageParam.getStartSettleDate()) && ObjectUtil.isNotEmpty(porOwnerSettlePageParam.getEndSettleDate())) {
            queryWrapper.between("a.SETTLE_DATE", porOwnerSettlePageParam.getStartSettleDate(), porOwnerSettlePageParam.getEndSettleDate());
        }
        if(ObjectUtil.isNotEmpty(porOwnerSettlePageParam.getSettleStatus())) {
            queryWrapper.eq("a.SETTLE_STATUS", porOwnerSettlePageParam.getSettleStatus());
        }
        if(ObjectUtil.isAllNotEmpty(porOwnerSettlePageParam.getSortField(), porOwnerSettlePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porOwnerSettlePageParam.getSortOrder());
            queryWrapper.orderBy(true, porOwnerSettlePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porOwnerSettlePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porOwnerSettleMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorOwnerSettleAddParam porOwnerSettleAddParam) {
        PorOwnerSettle porOwnerSettle = BeanUtil.toBean(porOwnerSettleAddParam, PorOwnerSettle.class);
        this.save(porOwnerSettle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorOwnerSettleEditParam porOwnerSettleEditParam) {
        PorOwnerSettle porOwnerSettle = this.queryEntity(porOwnerSettleEditParam.getId());
        BeanUtil.copyProperties(porOwnerSettleEditParam, porOwnerSettle);
        this.updateById(porOwnerSettle);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorOwnerSettleIdParam> porOwnerSettleIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porOwnerSettleIdParamList, PorOwnerSettleIdParam::getId));
    }

    @Override
    public PorOwnerSettleVo detail(PorOwnerSettleIdParam porOwnerSettleIdParam) {
        return porOwnerSettleMapper.getDetail(porOwnerSettleIdParam.getId());
    }

    @Override
    public PorOwnerSettle queryEntity(String id) {
        PorOwnerSettle porOwnerSettle = this.getById(id);
        if(ObjectUtil.isEmpty(porOwnerSettle)) {
            throw new CommonException("项目产值台账 - 项目管理 - 项目业主结算记录不存在，id值为：{}", id);
        }
        return porOwnerSettle;
    }
}
