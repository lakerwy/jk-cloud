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
import vip.xiaonuo.biz.modular.prj.por.vo.PorOemVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOem;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorOemMapper;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemAddParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemEditParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemIdParam;
import vip.xiaonuo.biz.modular.prj.por.param.PorOemPageParam;
import vip.xiaonuo.biz.modular.prj.por.service.PorOemService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 经营费管理Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:56
 **/
@Service
@DS("slave_1")
public class PorOemServiceImpl extends ServiceImpl<PorOemMapper, PorOem> implements PorOemService {

    @Resource
    private PorOemMapper porOemMapper;

    @Override
    public Page<PorOemVo> page(PorOemPageParam porOemPageParam) {
        QueryWrapper<PorOem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porOemPageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", porOemPageParam.getHetongId());
        }
        if(ObjectUtil.isAllNotEmpty(porOemPageParam.getSortField(), porOemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porOemPageParam.getSortOrder());
            queryWrapper.orderBy(true, porOemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porOemPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porOemMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorOemAddParam porOemAddParam) {
        PorOem porOem = BeanUtil.toBean(porOemAddParam, PorOem.class);
        this.save(porOem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorOemEditParam porOemEditParam) {
        PorOem porOem = this.queryEntity(porOemEditParam.getId());
        BeanUtil.copyProperties(porOemEditParam, porOem);
        this.updateById(porOem);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorOemIdParam> porOemIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porOemIdParamList, PorOemIdParam::getId));
    }

    @Override
    public PorOemVo detail(PorOemIdParam porOemIdParam) {
        return porOemMapper.getDetail(porOemIdParam.getId());
    }

    @Override
    public PorOem queryEntity(String id) {
        PorOem porOem = this.getById(id);
        if(ObjectUtil.isEmpty(porOem)) {
            throw new CommonException("项目产值台账 - 项目管理 - 经营费管理不存在，id值为：{}", id);
        }
        return porOem;
    }
}
