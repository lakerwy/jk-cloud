package vip.xiaonuo.biz.modular.ledger.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.hetong.param.HetongPageParam;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongVo;
import vip.xiaonuo.biz.modular.ledger.param.*;
import vip.xiaonuo.biz.modular.ledger.vo.BizLedgerAllocationCountAmountVo;
import vip.xiaonuo.biz.modular.ledger.vo.BizLedgerAllocationRecentVo;
import vip.xiaonuo.biz.modular.report.result.PartnerVo;
import vip.xiaonuo.biz.modular.report.service.PartnerInfoService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.ledger.entity.BizLedgerAllocation;
import vip.xiaonuo.biz.modular.ledger.mapper.BizLedgerAllocationMapper;
import vip.xiaonuo.biz.modular.ledger.service.BizLedgerAllocationService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 拨付台账Service接口实现类
 *
 * @author pzh
 * @date  2025/03/18 10:43
 **/
@DS("slave_1")
@Service
public class BizLedgerAllocationServiceImpl extends ServiceImpl<BizLedgerAllocationMapper, BizLedgerAllocation> implements BizLedgerAllocationService {

    @Resource
    private HetongService hetongService;

    @Resource
    PartnerInfoService partnerInfoService;

    @Override
    public Page<BizLedgerAllocation> page(BizLedgerAllocationPageParam pageParam) {
        QueryWrapper<BizLedgerAllocation> queryWrapper = getBizLedgerAllocationQueryWrapper(pageParam);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    private static QueryWrapper<BizLedgerAllocation> getBizLedgerAllocationQueryWrapper(BizLedgerAllocationPageParam pageParam) {
        QueryWrapper<BizLedgerAllocation> queryWrapper = new QueryWrapper<>();
        // 关键词
        if (ObjectUtil.isNotEmpty(pageParam.getSearchKey())) {
            List<String> keys = StrUtil.splitTrim(pageParam.getSearchKey().replace(" ",",").replace("，",","),",");
//            if(keys.size() > 1){
//                for (String key:keys) {
//                    queryWrapper.lambda().and(q->q.like(BizLedgerAllocation::getProjectName,key)
//                            .or().like(BizLedgerAllocation::getContractNo,key));
//                }
                queryWrapper.lambda().and(q -> keys.forEach(key -> q.or().like(BizLedgerAllocation::getProjectName, key)
                        .or().like(BizLedgerAllocation::getContractNo, key)
                        .or().like(BizLedgerAllocation::getUnitName, key)
                        .or().like(BizLedgerAllocation::getRegion, key)
                ));
//            }else {
//                queryWrapper.lambda().and(q -> q.like(BizLedgerAllocation::getProjectName, pageParam.getSearchKey())
//                        .or().like(BizLedgerAllocation::getContractNo, pageParam.getSearchKey()));
//            }
        }
        // 合作类型
        if(ObjectUtil.isNotEmpty(pageParam.getContractType())){
            queryWrapper.lambda().eq(BizLedgerAllocation::getContractType, pageParam.getContractType());
        }
        // 合作单位
        if(ObjectUtil.isNotEmpty(pageParam.getUnitId())){
            queryWrapper.lambda().eq(BizLedgerAllocation::getUnitId, pageParam.getUnitId());
        }
        if(ObjectUtil.isNotEmpty(pageParam.getUnitName())){
            queryWrapper.lambda().like(BizLedgerAllocation::getUnitName, pageParam.getUnitName());
        }
        // 所属区域
        if(ObjectUtil.isNotEmpty(pageParam.getRegion())){
            queryWrapper.lambda().eq(BizLedgerAllocation::getRegion, pageParam.getRegion());
        }
        // 登记时间范围查询
        if(ObjectUtil.isNotEmpty(pageParam.getStartCreateTime()) && ObjectUtil.isNotEmpty(pageParam.getEndCreateTime())) {
            queryWrapper.lambda().between(BizLedgerAllocation::getCreateTime, pageParam.getStartCreateTime(), pageParam.getEndCreateTime());
        }
        // 回款时间范围查询
        if(ObjectUtil.isNotEmpty(pageParam.getStartReceiveAmountTime()) && ObjectUtil.isNotEmpty(pageParam.getEndReceiveAmountTime())) {
            queryWrapper.lambda().between(BizLedgerAllocation::getReceiveAmountTime, pageParam.getStartReceiveAmountTime(), pageParam.getEndReceiveAmountTime());
        }
        if(ObjectUtil.isAllNotEmpty(pageParam.getSortField(), pageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(pageParam.getSortOrder());
            queryWrapper.orderBy(true, pageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(pageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizLedgerAllocation::getCreateTime);
        }
        return queryWrapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizLedgerAllocationAddParam bizLedgerAllocationAddParam) {
        checkParam(bizLedgerAllocationAddParam);
        BizLedgerAllocation bizLedgerAllocation = BeanUtil.toBean(bizLedgerAllocationAddParam, BizLedgerAllocation.class);
        this.save(bizLedgerAllocation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizLedgerAllocationEditParam bizLedgerAllocationEditParam) {
        checkParam(bizLedgerAllocationEditParam);
        BizLedgerAllocation bizLedgerAllocation = this.queryEntity(bizLedgerAllocationEditParam.getId());
        BeanUtil.copyProperties(bizLedgerAllocationEditParam, bizLedgerAllocation);
        this.updateById(bizLedgerAllocation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizLedgerAllocationIdParam> bizLedgerAllocationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizLedgerAllocationIdParamList, BizLedgerAllocationIdParam::getId));
    }

    @Override
    public BizLedgerAllocation detail(BizLedgerAllocationIdParam bizLedgerAllocationIdParam) {
        return this.queryEntity(bizLedgerAllocationIdParam.getId());
    }

    @Override
    public BizLedgerAllocation queryEntity(String id) {
        BizLedgerAllocation bizLedgerAllocation = this.getById(id);
        if(ObjectUtil.isEmpty(bizLedgerAllocation)) {
            throw new CommonException("拨付台账不存在，id值为：{}", id);
        }
        return bizLedgerAllocation;
    }

    private void checkParam(BizLedgerAllocationAddParam addParam) {
        long count = this.count(new LambdaQueryWrapper<BizLedgerAllocation>().eq(BizLedgerAllocation::getHetongId,addParam.getHetongId())
                .eq(BizLedgerAllocation::getReceiveAmountAcc,addParam.getReceiveAmountAcc()));
        if(count > 0) {
            throw new CommonException("同一项目累计回款金额不可重复！");
        }
    }

    private void checkParam(BizLedgerAllocationEditParam editParam) {
        long count = this.count(new LambdaQueryWrapper<BizLedgerAllocation>().eq(BizLedgerAllocation::getHetongId,editParam.getHetongId())
                .eq(BizLedgerAllocation::getReceiveAmountAcc,editParam.getReceiveAmountAcc())
                .ne(BizLedgerAllocation::getId, editParam.getId()));
        if(count > 0) {
            throw new CommonException("同一项目累计回款金额不可重复！");
        }
    }


    @Override
    public BizLedgerAllocationRecentVo recent(BizLedgerAllocationRecentParam param) {
        BizLedgerAllocationRecentVo vo = new BizLedgerAllocationRecentVo();
        if(ObjectUtil.isEmpty(param.getHetongId())){
            return vo;
        }
        BizLedgerAllocation bizLedgerAllocation = this.getOne(new LambdaQueryWrapper<BizLedgerAllocation>()
                .select(BizLedgerAllocation::getReceiveAmountAcc)
                .eq(BizLedgerAllocation::getHetongId, param.getHetongId())
                .orderByDesc(BizLedgerAllocation::getCreateTime).last("limit 1"));
        if(ObjectUtil.isNotEmpty(bizLedgerAllocation)){
            vo.setReceiveAmountAcc(bizLedgerAllocation.getReceiveAmountAcc());
        }
        return vo;
    }

    @Override
    public BizLedgerAllocationCountAmountVo countAmount(BizLedgerAllocationPageParam countAmountParam) {
        BizLedgerAllocationCountAmountVo vo = new BizLedgerAllocationCountAmountVo();
        QueryWrapper<BizLedgerAllocation> queryWrapper = getBizLedgerAllocationQueryWrapper(countAmountParam);
        List<BizLedgerAllocation> list = this.list(queryWrapper);
        BigDecimal receiveAmountTotal = BigDecimal.ZERO;
        BigDecimal manageAmountTotal = BigDecimal.ZERO;
        BigDecimal receiveAndManageAmountTotal = BigDecimal.ZERO;
        if(CollUtil.isNotEmpty(list)){
            receiveAmountTotal = list.stream().map(BizLedgerAllocation::getReceiveAmount)
                    .filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
            manageAmountTotal = list.stream().map(BizLedgerAllocation::getManageAmount)
                    .filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
            receiveAndManageAmountTotal = receiveAmountTotal.add(manageAmountTotal);
        }
        vo.setReceiveAmountTotal(receiveAmountTotal);
        vo.setManageAmountTotal(manageAmountTotal);
        vo.setReceiveAndManageAmountTotal(receiveAndManageAmountTotal);
        return vo;
    }

    @Override
    public void importData(ImportLedgerAllocation input) {
        List<BizLedgerAllocationAddParam> importData = input.getImportData();
        if(CollUtil.isEmpty(importData)){
            throw new CommonException("无有效导入数据");
        }
        List<BizLedgerAllocation> bizLedgerAllocationList = BeanUtil.copyToList(importData, BizLedgerAllocation.class);
        bizLedgerAllocationList.forEach(x -> {
            HetongPageParam hetongPageParam = new HetongPageParam();
            hetongPageParam.setStatus("1");
            hetongPageParam.setProjectName(x.getProjectName());
            IPage<HetongDetailVo> page = hetongService.page(hetongPageParam);
            if(CollUtil.isNotEmpty(page.getRecords())){
                List<HetongDetailVo> heTongList = page.getRecords();
                HetongDetailVo hetongDetailVo = heTongList.stream().filter(item -> item.getProjectName().equals(x.getProjectName())).findFirst().orElse(null);
                // 合同ID，合同编号
                if(ObjectUtil.isNotEmpty(hetongDetailVo)){
                    x.setHetongId(hetongDetailVo.getId());
                    x.setContractNo(hetongDetailVo.getContractNo());
                }
            }
            List<PartnerVo> partnerVoList = partnerInfoService.getAll();
            if(CollUtil.isNotEmpty(partnerVoList)){
                PartnerVo partnerVo = partnerVoList.stream().filter(item -> ("分公司".equals(item.getPartnerType()) || "分公司（注册）".equals(item.getPartnerType()))
                        && item.getName().equals(x.getUnitName())).findFirst().orElse(null);
                // 合作类型（分公司、零星），合作单位ID
                if(ObjectUtil.isNotEmpty(partnerVo)){
                    x.setUnitId(partnerVo.getId());
                    x.setOrgId(partnerVo.getOrgId());
                    x.setContractType("分公司");
                }else {
                    x.setContractType("零星");
                }
            }
            x.setNote(DateUtil.now()+"：页面导入；");
        });
        this.saveBatch(bizLedgerAllocationList);
    }
}
