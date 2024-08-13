package vip.xiaonuo.biz.modular.branch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.branch.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.branch.entity.BranchBankInfo;
import vip.xiaonuo.biz.modular.branch.mapper.BranchBankInfoMapper;
import vip.xiaonuo.biz.modular.branch.service.BranchBankInfoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 分公司银行账户信息Service接口实现类
 *
 * @author pzh
 * @date  2024/08/05 11:46
 **/
@DS("slave_1")
@Service
public class BranchBankInfoServiceImpl extends ServiceImpl<BranchBankInfoMapper, BranchBankInfo> implements BranchBankInfoService {

    @Override
    public Page<BranchBankInfo> page(BranchBankInfoPageParam branchBankInfoPageParam) {
        QueryWrapper<BranchBankInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(branchBankInfoPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BranchBankInfo::getBranchName,branchBankInfoPageParam.getSearchKey())
                    .or().like(BranchBankInfo::getBankName,branchBankInfoPageParam.getSearchKey())
                    .or().like(BranchBankInfo::getBankAccount,branchBankInfoPageParam.getSearchKey()));
        }
        if(ObjectUtil.isNotEmpty(branchBankInfoPageParam.getBalanceUpdateTime())){
            LocalDate date = LocalDate.parse(branchBankInfoPageParam.getBalanceUpdateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 将 LocalDate 转换为 LocalDateTime，并设置时间为当天的开始时间（00:00:00）
            LocalDateTime startOfDay = date.atStartOfDay();

            // 将 LocalDate 转换为 LocalDateTime，并设置时间为当天的结束时间（23:59:59）
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            // 构造查询条件，查询创建时间介于当天的开始时间和结束时间之间的记录
            queryWrapper.lambda().between(BranchBankInfo::getBalanceUpdateTime, startOfDay, endOfDay);
        }
        if(ObjectUtil.isAllNotEmpty(branchBankInfoPageParam.getSortField(), branchBankInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchBankInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchBankInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchBankInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BranchBankInfo::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BranchBankInfoAddParam branchBankInfoAddParam) {
        BranchBankInfo branchBankInfo = BeanUtil.toBean(branchBankInfoAddParam, BranchBankInfo.class);
        Date now = new Date();
        branchBankInfo.setCreateTime(now);
        branchBankInfo.setBalanceUpdateTime(now);
        this.save(branchBankInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BranchBankInfoEditParam branchBankInfoEditParam) {
        BranchBankInfo branchBankInfo = this.queryEntity(branchBankInfoEditParam.getId());
        BeanUtil.copyProperties(branchBankInfoEditParam, branchBankInfo);
        Date now = new Date();
        branchBankInfo.setUpdateTime(now);
        branchBankInfo.setBalanceUpdateTime(now);
        this.updateById(branchBankInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BranchBankInfoIdParam> branchBankInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(branchBankInfoIdParamList, BranchBankInfoIdParam::getId));
    }

    @Override
    public BranchBankInfo detail(BranchBankInfoIdParam branchBankInfoIdParam) {
        return this.queryEntity(branchBankInfoIdParam.getId());
    }

    @Override
    public BranchBankInfo queryEntity(String id) {
        BranchBankInfo branchBankInfo = this.getById(id);
        if(ObjectUtil.isEmpty(branchBankInfo)) {
            throw new CommonException("分公司银行账户信息不存在，id值为：{}", id);
        }
        return branchBankInfo;
    }

    @Override
    public void importData(ImportBranchBankInfo input) {
        if(CollUtil.isEmpty(input.getImportData())){
            throw new CommonException("无有效导入数据");
        }
        List<BranchBankInfo> addList = BeanUtil.copyToList(input.getImportData(), BranchBankInfo.class);
        Date now = new Date();
        for(BranchBankInfo branchBankInfo : addList){
            branchBankInfo.setCreateTime(now);
            branchBankInfo.setBalanceUpdateTime(now);
        }
        this.saveBatch(addList);
    }
}
