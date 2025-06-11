package vip.xiaonuo.biz.modular.prj.fin.service.impl;

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
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ErrorResult;
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataPdtPageTotal;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPdt;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataPdtMapper;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataPdtService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 财务数据汇总台账 - 生产院Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
@Service
@DS("slave_1")
public class FinDataPdtServiceImpl extends ServiceImpl<FinDataPdtMapper, FinDataPdt> implements FinDataPdtService {

    @Resource
    private HetongService hetongService;

    @Override
    public Page<FinDataPdt> page(FinDataPdtPageParam finDataPdtPageParam) {
        QueryWrapper<FinDataPdt> queryWrapper = getFinDataPdtQueryWrapper(finDataPdtPageParam);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    private static QueryWrapper<FinDataPdt> getFinDataPdtQueryWrapper(FinDataPdtPageParam finDataPdtPageParam) {
        QueryWrapper<FinDataPdt> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataPdt::getContractId, finDataPdtPageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataPdt::getContractNo, finDataPdtPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getStartBfDate()) && ObjectUtil.isNotEmpty(finDataPdtPageParam.getEndBfDate())) {
            queryWrapper.lambda().between(FinDataPdt::getBfDate, finDataPdtPageParam.getStartBfDate(), finDataPdtPageParam.getEndBfDate());
        }
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataPdt::getContractNo, finDataPdtPageParam.getSearchKey())
                    .or().like(FinDataPdt::getKkOtherContent, finDataPdtPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataPdtPageParam.getSortField(), finDataPdtPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataPdtPageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataPdtPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataPdtPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataPdt::getCreateTime);
        }
        return queryWrapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataPdtAddParam finDataPdtAddParam) {
        FinDataPdt finDataPdt = BeanUtil.toBean(finDataPdtAddParam, FinDataPdt.class);
        finDataPdt.setExtJson("页面-新增");
        this.save(finDataPdt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataPdtEditParam finDataPdtEditParam) {
        FinDataPdt finDataPdt = this.queryEntity(finDataPdtEditParam.getId());
        BeanUtil.copyProperties(finDataPdtEditParam, finDataPdt);
        this.updateById(finDataPdt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataPdtIdParam> finDataPdtIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataPdtIdParamList, FinDataPdtIdParam::getId));
    }

    @Override
    public FinDataPdt detail(FinDataPdtIdParam finDataPdtIdParam) {
        return this.queryEntity(finDataPdtIdParam.getId());
    }

    @Override
    public FinDataPdt queryEntity(String id) {
        FinDataPdt finDataPdt = this.getById(id);
        if(ObjectUtil.isEmpty(finDataPdt)) {
            throw new CommonException("财务数据汇总台账 - 生产院不存在，id值为：{}", id);
        }
        return finDataPdt;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataPdt> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataPdt> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataPdt tmpFinData = BeanUtil.copyProperties(item,FinDataPdt.class);
            tmpFinData.setExtJson(JSONUtil.toJsonStr(item));

            //校验合同号
            if(ObjectUtil.isNotEmpty(item.getContractNo())) {
                Hetong tmpHetong = hetongList.stream().filter(x ->item.getContractNo().equals(x.getContractNo())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpHetong)){
                    tmpFinData.setContractId(tmpHetong.getId());
                }else{
                    tmpFinData.setContractNo(null);
                    errorMsg += "合同编码不匹配；";
                }
            }

            if(ObjectUtil.isNotEmpty(errorMsg)){
                tmpErrorResult.setErrorMsg(errorMsg);
                errList.add(tmpErrorResult);
            }
            addList.add(tmpFinData);
        });
        this.saveBatch(addList);

        ImportResult result = new ImportResult();
        result.setTotal((int) importParam.getImportList().stream().count());
        result.setErrCount((int) errList.stream().count());
        result.setSucCount((int) addList.stream().count());
        result.setResultList(errList);
        return result;
    }

    @Override
    public FinDataPdtPageTotal pageTotal(FinDataPdtPageParam finDataPdtPageParam) {
        QueryWrapper<FinDataPdt> queryWrapper = getFinDataPdtQueryWrapper(finDataPdtPageParam);
        List<FinDataPdt> dataList = this.list(queryWrapper);
        FinDataPdtPageTotal result = new FinDataPdtPageTotal();
        result.setBfOutput(dataList.stream().map(x->x.getBfOutput()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfExpense(dataList.stream().map(x->x.getBfExpense()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfSub(dataList.stream().map(x->x.getBfSub()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfLabor(dataList.stream().map(x->x.getBfLabor()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfOther(dataList.stream().map(x->x.getBfOther()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setKkTax(dataList.stream().map(x->x.getKkTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setKkOther(dataList.stream().map(x->x.getKkOther()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        return result;
    }
}
