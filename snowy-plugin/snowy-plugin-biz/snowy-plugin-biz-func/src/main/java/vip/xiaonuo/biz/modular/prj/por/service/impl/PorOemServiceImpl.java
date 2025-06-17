package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractService;
import vip.xiaonuo.biz.modular.pis.entity.PisAllotment;
import vip.xiaonuo.biz.modular.pis.service.PisAllotmentService;
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
import java.util.ArrayList;
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

    @Resource
    private SubcontractService subcontractService;

    @Resource
    private PisAllotmentService pisAllotmentService;

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

    @Override
    public List<PisData> getListByHtId(ContractParam param) {
        List<PisData> result = new ArrayList<>();

        LambdaQueryWrapper<PisAllotment> pisAllotmentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pisAllotmentLambdaQueryWrapper.in(PisAllotment::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        pisAllotmentLambdaQueryWrapper.eq(PisAllotment::getHtId,param.getHetongId());
        List<PisAllotment> pisAllotmentList = pisAllotmentService.list(pisAllotmentLambdaQueryWrapper);
        if(CollUtil.isEmpty(pisAllotmentList)) return result;

        LambdaQueryWrapper<Subcontract> subcontractLambdaQueryWrapper = new LambdaQueryWrapper<>();
        subcontractLambdaQueryWrapper.in(Subcontract::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        subcontractLambdaQueryWrapper.eq(Subcontract::getHetongId,param.getHetongId());
        subcontractLambdaQueryWrapper.eq(Subcontract::getExpenseNature,"经营费");
        List<Subcontract> subcontractList = subcontractService.list(subcontractLambdaQueryWrapper);
        if(CollUtil.isEmpty(subcontractList)) return result;

        return porProjectService.getPisData(result,pisAllotmentList,subcontractList);
    }
}
