package vip.xiaonuo.biz.modular.prj.expense.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.expense.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizFinCode;
import vip.xiaonuo.biz.modular.prj.expense.mapper.BizFinCodeMapper;
import vip.xiaonuo.biz.modular.prj.expense.service.BizFinCodeService;

import java.util.List;

/**
 * 财务管理 - 费用报销 - 财务收单编码库Service接口实现类
 *
 * @author yong.ye
 * @date  2024/08/01 11:21
 **/
@Service
public class BizFinCodeServiceImpl extends ServiceImpl<BizFinCodeMapper, BizFinCode> implements BizFinCodeService {

    @Override
    public Page<BizFinCode> page(BizFinCodePageParam bizFinCodePageParam) {
        QueryWrapper<BizFinCode> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizFinCodePageParam.getCompany())) {
            queryWrapper.lambda().like(BizFinCode::getCompany, bizFinCodePageParam.getCompany());
        }
        if(ObjectUtil.isNotEmpty(bizFinCodePageParam.getCodePre())) {
            queryWrapper.lambda().eq(BizFinCode::getCodePre, bizFinCodePageParam.getCodePre());
        }
        if(ObjectUtil.isNotEmpty(bizFinCodePageParam.getCodeYear())) {
            queryWrapper.lambda().eq(BizFinCode::getCodeYear, bizFinCodePageParam.getCodeYear());
        }
        if(ObjectUtil.isNotEmpty(bizFinCodePageParam.getCodeMonth())) {
            queryWrapper.lambda().eq(BizFinCode::getCodeMonth, bizFinCodePageParam.getCodeMonth());
        }
        if(ObjectUtil.isNotEmpty(bizFinCodePageParam.getCode())) {
            queryWrapper.lambda().like(BizFinCode::getCode, bizFinCodePageParam.getCode());
        }
        if(ObjectUtil.isAllNotEmpty(bizFinCodePageParam.getSortField(), bizFinCodePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFinCodePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFinCodePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFinCodePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFinCode::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFinCodeAddParam bizFinCodeAddParam) {
        BizFinCode bizFinCode = BeanUtil.toBean(bizFinCodeAddParam, BizFinCode.class);
        this.save(bizFinCode);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFinCodeEditParam bizFinCodeEditParam) {
        BizFinCode bizFinCode = this.queryEntity(bizFinCodeEditParam.getId());
        BeanUtil.copyProperties(bizFinCodeEditParam, bizFinCode);
        this.updateById(bizFinCode);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFinCodeIdParam> bizFinCodeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFinCodeIdParamList, BizFinCodeIdParam::getId));
    }

    @Override
    public BizFinCode detail(BizFinCodeIdParam bizFinCodeIdParam) {
        return this.queryEntity(bizFinCodeIdParam.getId());
    }

    @Override
    public BizFinCode queryEntity(String id) {
        BizFinCode bizFinCode = this.getById(id);
        if(ObjectUtil.isEmpty(bizFinCode)) {
            throw new CommonException("财务管理 - 费用报销 - 财务收单编码库不存在，id值为：{}", id);
        }
        return bizFinCode;
    }

    @Override
    public String genFinCode(FinCodeParam finCodeParam) {
         BizFinCode preFinCode = this.getOne(new QueryWrapper<BizFinCode>().lambda()
                 .eq(BizFinCode::getCodePre,finCodeParam.getCodePre())
                 .eq(BizFinCode::getCodeYear,finCodeParam.getCodeYear())
                 .eq(BizFinCode::getCodeMonth,finCodeParam.getCodeMonth())
                 .orderByDesc(BizFinCode::getCode).last(" limit 1 "));
         BizFinCode nextFinCode = new BizFinCode();
         nextFinCode.setCompany(finCodeParam.getCompany());
         nextFinCode.setCodePre(finCodeParam.getCodePre());
         nextFinCode.setCodeYear(finCodeParam.getCodeYear());
         nextFinCode.setCodeMonth(finCodeParam.getCodeMonth());
         if(ObjectUtil.isNotEmpty(preFinCode)){
             // 将字符串转换为整数
             int number = Integer.parseInt(preFinCode.getCodeNo());
             // 将整数格式化为三位数的字符串
             String newCodeNo = String.format("%03d", number+1);
             nextFinCode.setCodeNo(newCodeNo);
             // 拼接最终编码
             String newCode = nextFinCode.getCodePre()+nextFinCode.getCodeYear()+nextFinCode.getCodeMonth()+nextFinCode.getCodeNo();
             nextFinCode.setCode(newCode);
         }else{
             nextFinCode.setCodeNo("001");
             // 拼接最终编码
             String newCode = nextFinCode.getCodePre()+nextFinCode.getCodeYear()+nextFinCode.getCodeMonth()+nextFinCode.getCodeNo();
             nextFinCode.setCode(newCode);
         }
         this.save(nextFinCode);
         return nextFinCode.getCode();
    }
}
