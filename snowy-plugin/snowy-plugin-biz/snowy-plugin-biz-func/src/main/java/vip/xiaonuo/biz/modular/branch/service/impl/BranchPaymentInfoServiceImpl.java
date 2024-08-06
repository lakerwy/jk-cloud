package vip.xiaonuo.biz.modular.branch.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.branch.entity.BranchPaymentInfo;
import vip.xiaonuo.biz.modular.branch.mapper.BranchPaymentInfoMapper;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoAddParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoEditParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoIdParam;
import vip.xiaonuo.biz.modular.branch.param.BranchPaymentInfoPageParam;
import vip.xiaonuo.biz.modular.branch.service.BranchPaymentInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * 分公司日常费用拨付 - 付款信息Service接口实现类
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Service
public class BranchPaymentInfoServiceImpl extends ServiceImpl<BranchPaymentInfoMapper, BranchPaymentInfo> implements BranchPaymentInfoService {

    @Override
    public Page<BranchPaymentInfo> page(BranchPaymentInfoPageParam branchPaymentInfoPageParam) {
        QueryWrapper<BranchPaymentInfo> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(branchPaymentInfoPageParam.getSortField(), branchPaymentInfoPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchPaymentInfoPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchPaymentInfoPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchPaymentInfoPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BranchPaymentInfo::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BranchPaymentInfoAddParam branchPaymentInfoAddParam) {
        BranchPaymentInfo branchPaymentInfo = BeanUtil.toBean(branchPaymentInfoAddParam, BranchPaymentInfo.class);
        this.save(branchPaymentInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BranchPaymentInfoEditParam branchPaymentInfoEditParam) {
        BranchPaymentInfo branchPaymentInfo = this.queryEntity(branchPaymentInfoEditParam.getId());
        BeanUtil.copyProperties(branchPaymentInfoEditParam, branchPaymentInfo);
        this.updateById(branchPaymentInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BranchPaymentInfoIdParam> branchPaymentInfoIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(branchPaymentInfoIdParamList, BranchPaymentInfoIdParam::getId));
    }

    @Override
    public BranchPaymentInfo detail(BranchPaymentInfoIdParam branchPaymentInfoIdParam) {
        return this.queryEntity(branchPaymentInfoIdParam.getId());
    }

    @Override
    public BranchPaymentInfo queryEntity(String id) {
        BranchPaymentInfo branchPaymentInfo = this.getById(id);
        if(ObjectUtil.isEmpty(branchPaymentInfo)) {
            throw new CommonException("分公司日常费用拨付 - 付款信息不存在，id值为：{}", id);
        }
        return branchPaymentInfo;
    }

    @Override
    public List<BranchPaymentInfo> getListByAllotmentId(BranchPaymentInfoIdParam branchPaymentInfoIdParam) {
        QueryWrapper<BranchPaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ALLOTMENT_APPLY_ID",branchPaymentInfoIdParam.getId());
        return this.list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdate(List<BranchPaymentInfoEditParam> branchPaymentInfoEditParamList) {
        List<BranchPaymentInfo> branchPaymentInfoList = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(branchPaymentInfoEditParamList)) {
            for(BranchPaymentInfoEditParam editParam : branchPaymentInfoEditParamList){
                BranchPaymentInfo branchPaymentInfo = this.queryEntity(editParam.getId());
                BeanUtil.copyProperties(editParam, branchPaymentInfo);
                branchPaymentInfoList.add(branchPaymentInfo);
            }
        }
        this.updateBatchById(branchPaymentInfoList);
    }

}
