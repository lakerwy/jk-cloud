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
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ErrorResult;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataInvoice;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataInvoiceMapper;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataInvoiceService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务数据汇总台账 - 销售发票Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:24
 **/
@Service
@DS("slave_1")
public class FinDataInvoiceServiceImpl extends ServiceImpl<FinDataInvoiceMapper, FinDataInvoice> implements FinDataInvoiceService {

    @Resource
    private HetongService hetongService;

    @Resource
    private BizOrgService bizOrgService;

    @Override
    public Page<FinDataInvoice> page(FinDataInvoicePageParam finDataInvoicePageParam) {
        QueryWrapper<FinDataInvoice> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataInvoicePageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataInvoice::getContractId,finDataInvoicePageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataInvoicePageParam.getUnitId())){
            queryWrapper.lambda().eq(FinDataInvoice::getUnitId,finDataInvoicePageParam.getUnitId());
        }
        if(ObjectUtil.isNotEmpty(finDataInvoicePageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataInvoice::getContractNo, finDataInvoicePageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataInvoicePageParam.getUnitName())) {
            queryWrapper.lambda().like(FinDataInvoice::getUnitName, finDataInvoicePageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(finDataInvoicePageParam.getStartKpDate()) && ObjectUtil.isNotEmpty(finDataInvoicePageParam.getEndKpDate())) {
            queryWrapper.lambda().between(FinDataInvoice::getKpDate, finDataInvoicePageParam.getStartKpDate(), finDataInvoicePageParam.getEndKpDate());
        }
        if(ObjectUtil.isNotEmpty(finDataInvoicePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataInvoice::getContractNo,finDataInvoicePageParam.getSearchKey())
                    .or().like(FinDataInvoice::getUnitName,finDataInvoicePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataInvoicePageParam.getSortField(), finDataInvoicePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataInvoicePageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataInvoicePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataInvoicePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataInvoice::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataInvoiceAddParam finDataInvoiceAddParam) {
        FinDataInvoice finDataInvoice = BeanUtil.toBean(finDataInvoiceAddParam, FinDataInvoice.class);
        finDataInvoice.setExtJson("页面-新增");
        this.save(finDataInvoice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataInvoiceEditParam finDataInvoiceEditParam) {
        FinDataInvoice finDataInvoice = this.queryEntity(finDataInvoiceEditParam.getId());
        BeanUtil.copyProperties(finDataInvoiceEditParam, finDataInvoice);
        this.updateById(finDataInvoice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataInvoiceIdParam> finDataInvoiceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataInvoiceIdParamList, FinDataInvoiceIdParam::getId));
    }

    @Override
    public FinDataInvoice detail(FinDataInvoiceIdParam finDataInvoiceIdParam) {
        return this.queryEntity(finDataInvoiceIdParam.getId());
    }

    @Override
    public FinDataInvoice queryEntity(String id) {
        FinDataInvoice finDataInvoice = this.getById(id);
        if(ObjectUtil.isEmpty(finDataInvoice)) {
            throw new CommonException("财务数据汇总台账 - 销售发票不存在，id值为：{}", id);
        }
        return finDataInvoice;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataInvoice> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataInvoice> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        List<BizOrg> bizOrgList = bizOrgService.list();
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataInvoice tmpFinData = BeanUtil.copyProperties(item,FinDataInvoice.class);
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
            //校验单位
            if(ObjectUtil.isNotEmpty(item.getUnitName())) {
                BizOrg tmpBizOrg = bizOrgList.stream().filter(x ->item.getUnitName().equals(x.getName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpBizOrg)){
                    tmpFinData.setUnitId(tmpBizOrg.getId());
                }else{
                    tmpFinData.setUnitName(null);
                    errorMsg += "开票单位不匹配；";
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
}
