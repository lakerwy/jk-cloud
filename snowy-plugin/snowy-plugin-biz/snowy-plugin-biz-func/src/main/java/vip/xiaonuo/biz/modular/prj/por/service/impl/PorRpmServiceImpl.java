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
import vip.xiaonuo.biz.modular.prj.por.vo.PorRpmVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorRpm;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorRpmMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorRpmPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorRpmService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 责任上缴费用管理Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:55
 **/
@Service
@DS("slave_1")
public class PorRpmServiceImpl extends ServiceImpl<PorRpmMapper, PorRpm> implements PorRpmService {

    @Resource
    private PorRpmMapper porRpmMapper;

    @Override
    public Page<PorRpmVo> page(PorRpmPageParam porRpmPageParam) {
        QueryWrapper<PorRpm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porRpmPageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", porRpmPageParam.getHetongId());
        }
        if(ObjectUtil.isAllNotEmpty(porRpmPageParam.getSortField(), porRpmPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porRpmPageParam.getSortOrder());
            queryWrapper.orderBy(true, porRpmPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porRpmPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porRpmMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorRpmAddParam porRpmAddParam) {
        PorRpm porRpm = BeanUtil.toBean(porRpmAddParam, PorRpm.class);
        this.save(porRpm);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorRpmEditParam porRpmEditParam) {
        PorRpm porRpm = this.queryEntity(porRpmEditParam.getId());
        BeanUtil.copyProperties(porRpmEditParam, porRpm);
        this.updateById(porRpm);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorRpmIdParam> porRpmIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porRpmIdParamList, PorRpmIdParam::getId));
    }

    @Override
    public PorRpmVo detail(PorRpmIdParam porRpmIdParam) {
        return porRpmMapper.getDetail(porRpmIdParam.getId());
    }

    @Override
    public PorRpm queryEntity(String id) {
        PorRpm porRpm = this.getById(id);
        if(ObjectUtil.isEmpty(porRpm)) {
            throw new CommonException("项目产值台账 - 项目管理 - 责任上缴费用管理不存在，id值为：{}", id);
        }
        return porRpm;
    }
}
