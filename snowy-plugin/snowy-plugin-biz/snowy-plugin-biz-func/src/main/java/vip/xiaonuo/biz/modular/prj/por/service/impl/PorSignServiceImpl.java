package vip.xiaonuo.biz.modular.prj.por.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import vip.xiaonuo.biz.modular.prj.por.vo.PorSignVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSign;
import vip.xiaonuo.biz.modular.prj.por.mapper.PorSignMapper;
import vip.xiaonuo.biz.modular.prj.por.service.PorSignService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目产值台账 - 项目管理 - 签字审图费管理Service接口实现类
 *
 * @author yy
 * @date  2025/06/03 09:54
 **/
@Service
@DS("slave_1")
public class PorSignServiceImpl extends ServiceImpl<PorSignMapper, PorSign> implements PorSignService {

    @Resource
    private PorSignMapper porSignMapper;

    @Resource
    private SubcontractService subcontractService;

    @Resource
    private PisAllotmentService pisAllotmentService;

    @Resource
    private PorProjectService porProjectService;

    @Override
    public Page<PorSignVo> page(PorSignPageParam porSignPageParam) {
        QueryWrapper<PorSign> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(porSignPageParam.getHetongId())) {
            queryWrapper.eq("a.HETONG_ID", porSignPageParam.getHetongId());
        }
        if(ObjectUtil.isNotEmpty(porSignPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("b.PROJECT_NAME",porSignPageParam.getSearchKey())
                    .or().like("b.CONTRACT_NO",porSignPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(porSignPageParam.getSortField(), porSignPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(porSignPageParam.getSortOrder());
            queryWrapper.orderBy(true, porSignPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(porSignPageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("a.CREATE_TIME");
        }
        return porSignMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PorSignAddParam porSignAddParam) {
        PorSign porSign = BeanUtil.toBean(porSignAddParam, PorSign.class);
        this.save(porSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PorSignEditParam porSignEditParam) {
        PorSign porSign = this.queryEntity(porSignEditParam.getId());
        BeanUtil.copyProperties(porSignEditParam, porSign);
        this.updateById(porSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PorSignIdParam> porSignIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(porSignIdParamList, PorSignIdParam::getId));
    }

    @Override
    public PorSignVo detail(PorSignIdParam porSignIdParam) {
        return porSignMapper.getDetail(porSignIdParam.getId());
    }

    @Override
    public PorSign queryEntity(String id) {
        PorSign porSign = this.getById(id);
        if(ObjectUtil.isEmpty(porSign)) {
            throw new CommonException("项目产值台账 - 项目管理 - 签字审图费管理不存在，id值为：{}", id);
        }
        return porSign;
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
        List<Subcontract> subcontractList = subcontractService.list(subcontractLambdaQueryWrapper);

        List<PisData> pisData = porProjectService.getPisData(result, pisAllotmentList, subcontractList);

        return pisData.stream().filter(x->ObjectUtil.isNotEmpty(x.getDeductSignature()) && BigDecimal.ZERO.compareTo(x.getDeductSignature())!=0).collect(Collectors.toList());
    }
}
