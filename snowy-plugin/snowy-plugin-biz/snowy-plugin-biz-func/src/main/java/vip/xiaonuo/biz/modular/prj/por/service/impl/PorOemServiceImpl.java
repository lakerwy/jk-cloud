package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.por.enums.ExpenseNatureEnum;
import vip.xiaonuo.biz.modular.prj.por.param.*;
import vip.xiaonuo.biz.modular.prj.por.service.PorProjectService;
import vip.xiaonuo.biz.modular.prj.por.vo.PisData;
import vip.xiaonuo.biz.modular.prj.por.vo.PorOemVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOem;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorOemMapper;
import vip.xiaonuo.biz.modular.prj.por.service.PorOemService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private PorProjectService porProjectService;

    @Override
    public Page<PorOemVo> page(PorOemPageParam porOemPageParam) {
        QueryWrapper<PorOem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porOemPageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", porOemPageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(porOemPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.PROJECT_NAME",porOemPageParam.getSearchKey())
                    .or().like("b.CONTRACT_NO",porOemPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(porOemPageParam.getSortField(), porOemPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porOemPageParam.getSortOrder());
            queryWrapper.orderBy(true, porOemPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porOemPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        Page<PorOemVo> result = porOemMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        for (PorOemVo record : result.getRecords()) {
            record.setOemPayAmount(sumPayable(record));
        }
        return result;
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
        PorOemVo result = porOemMapper.getDetail(porOemIdParam.getId());
        result.setOemPayAmount(sumPayable(result));
        return result;
    }

    @Override
    public PorOem queryEntity(String id) {
        PorOem porOem = this.getById(id);
        if(ObjectUtil.isEmpty(porOem)) {
            throw new CommonException("项目产值台账 - 项目管理 - 经营费管理不存在，id值为：{}", id);
        }
        return porOem;
    }

    @Override
    public List<PisData> getPisListByHtId(ContractParam param) {
        List<String> htIds = CollectionUtil.toList(param.getHetongId());
        List<String> natureList = CollectionUtil.toList(ExpenseNatureEnum.JYF.getValue(),ExpenseNatureEnum.JJF.getValue());
        return porProjectService.getPisData(htIds,natureList,null);
    }

    /**
     * 计算 累计付款金额（审批通过）
     * @param param
     * @return
     */
    public BigDecimal sumPayable(PorOemVo param){
        List<String> htIds = CollectionUtil.toList(param.getHetongId());
        List<String> natureList = CollectionUtil.toList(ExpenseNatureEnum.JYF.getValue(),ExpenseNatureEnum.JJF.getValue());
        List<PisData> pisDataList = porProjectService.getPisData(htIds,natureList, FlwStatusEnum.COMPLETED_STATUS.getValue());
        return pisDataList.stream().map(x->x.getPayable()).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
