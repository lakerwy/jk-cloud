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
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataManagePageTotal;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataManage;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataManageMapper;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataManageService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 财务数据汇总台账 - 转入管理费Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:25
 **/
@Service
@DS("slave_1")
public class FinDataManageServiceImpl extends ServiceImpl<FinDataManageMapper, FinDataManage> implements FinDataManageService {

    @Resource
    private HetongService hetongService;

    @Resource
    private BizOrgService bizOrgService;

    @Override
    public Page<FinDataManage> page(FinDataManagePageParam finDataManagePageParam) {
        QueryWrapper<FinDataManage> queryWrapper = getFinDataManageQueryWrapper(finDataManagePageParam);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    private static QueryWrapper<FinDataManage> getFinDataManageQueryWrapper(FinDataManagePageParam finDataManagePageParam) {
        QueryWrapper<FinDataManage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataManagePageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataManage::getContractId, finDataManagePageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataManagePageParam.getUnitId())){
            queryWrapper.lambda().eq(FinDataManage::getUnitId, finDataManagePageParam.getUnitId());
        }
        if(ObjectUtil.isNotEmpty(finDataManagePageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataManage::getContractNo, finDataManagePageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataManagePageParam.getUnitName())) {
            queryWrapper.lambda().like(FinDataManage::getUnitName, finDataManagePageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(finDataManagePageParam.getStartZkDate()) && ObjectUtil.isNotEmpty(finDataManagePageParam.getEndZkDate())) {
            queryWrapper.lambda().between(FinDataManage::getZkDate, finDataManagePageParam.getStartZkDate(), finDataManagePageParam.getEndZkDate());
        }
        if(ObjectUtil.isNotEmpty(finDataManagePageParam.getFeeType())) {
            queryWrapper.lambda().like(FinDataManage::getFeeType, finDataManagePageParam.getFeeType());
        }
        if(ObjectUtil.isNotEmpty(finDataManagePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataManage::getContractNo, finDataManagePageParam.getSearchKey())
                    .or().like(FinDataManage::getUnitName, finDataManagePageParam.getSearchKey())
                    .or().like(FinDataManage::getFeeType, finDataManagePageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataManagePageParam.getSortField(), finDataManagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataManagePageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataManagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataManagePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataManage::getCreateTime);
        }
        return queryWrapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataManageAddParam finDataManageAddParam) {
        FinDataManage finDataManage = BeanUtil.toBean(finDataManageAddParam, FinDataManage.class);
        finDataManage.setExtJson("页面-新增");
        this.save(finDataManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataManageEditParam finDataManageEditParam) {
        FinDataManage finDataManage = this.queryEntity(finDataManageEditParam.getId());
        BeanUtil.copyProperties(finDataManageEditParam, finDataManage);
        this.updateById(finDataManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataManageIdParam> finDataManageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataManageIdParamList, FinDataManageIdParam::getId));
    }

    @Override
    public FinDataManage detail(FinDataManageIdParam finDataManageIdParam) {
        return this.queryEntity(finDataManageIdParam.getId());
    }

    @Override
    public FinDataManage queryEntity(String id) {
        FinDataManage finDataManage = this.getById(id);
        if(ObjectUtil.isEmpty(finDataManage)) {
            throw new CommonException("财务数据汇总台账 - 转入管理费不存在，id值为：{}", id);
        }
        return finDataManage;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataManage> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataManage> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        List<BizOrg> bizOrgList = bizOrgService.list();
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataManage tmpFinData = BeanUtil.copyProperties(item,FinDataManage.class);
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
                    tmpFinData.setMemo(tmpBizOrg.getCategory());
                }else{
                    tmpFinData.setUnitName(null);
                    errorMsg += "转款单位不匹配；";
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
    public FinDataManagePageTotal pageTotal(FinDataManagePageParam finDataManagePageParam) {
        QueryWrapper<FinDataManage> queryWrapper = getFinDataManageQueryWrapper(finDataManagePageParam);
        List<FinDataManage> dataList = this.list(queryWrapper);
        FinDataManagePageTotal result = new FinDataManagePageTotal();
        result.setZkAmount(dataList.stream().map(x->x.getZkAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        return result;
    }
}
