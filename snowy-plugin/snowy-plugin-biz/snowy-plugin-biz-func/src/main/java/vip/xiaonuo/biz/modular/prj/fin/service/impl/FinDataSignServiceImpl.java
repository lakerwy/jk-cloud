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
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataSign;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataSignMapper;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataSignService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务数据汇总台账 - 转入签字费Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:27
 **/
@Service
@DS("slave_1")
public class FinDataSignServiceImpl extends ServiceImpl<FinDataSignMapper, FinDataSign> implements FinDataSignService {

    @Resource
    private HetongService hetongService;

    @Resource
    private BizOrgService bizOrgService;

    @Override
    public Page<FinDataSign> page(FinDataSignPageParam finDataSignPageParam) {
        QueryWrapper<FinDataSign> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataSignPageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataSign::getContractId,finDataSignPageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataSignPageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataSign::getContractNo, finDataSignPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataSignPageParam.getUnitId())){
            queryWrapper.lambda().eq(FinDataSign::getUnitId,finDataSignPageParam.getUnitId());
        }
        if(ObjectUtil.isNotEmpty(finDataSignPageParam.getUnitName())) {
            queryWrapper.lambda().like(FinDataSign::getUnitName, finDataSignPageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(finDataSignPageParam.getStartZkDate()) && ObjectUtil.isNotEmpty(finDataSignPageParam.getEndZkDate())) {
            queryWrapper.lambda().between(FinDataSign::getZkDate, finDataSignPageParam.getStartZkDate(), finDataSignPageParam.getEndZkDate());
        }
        if(ObjectUtil.isNotEmpty(finDataSignPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataSign::getContractNo,finDataSignPageParam.getSearchKey())
                    .or().like(FinDataSign::getUnitName,finDataSignPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataSignPageParam.getSortField(), finDataSignPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataSignPageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataSignPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataSignPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataSign::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataSignAddParam finDataSignAddParam) {
        FinDataSign finDataSign = BeanUtil.toBean(finDataSignAddParam, FinDataSign.class);
        finDataSign.setExtJson("页面-新增");
        this.save(finDataSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataSignEditParam finDataSignEditParam) {
        FinDataSign finDataSign = this.queryEntity(finDataSignEditParam.getId());
        BeanUtil.copyProperties(finDataSignEditParam, finDataSign);
        this.updateById(finDataSign);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataSignIdParam> finDataSignIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataSignIdParamList, FinDataSignIdParam::getId));
    }

    @Override
    public FinDataSign detail(FinDataSignIdParam finDataSignIdParam) {
        return this.queryEntity(finDataSignIdParam.getId());
    }

    @Override
    public FinDataSign queryEntity(String id) {
        FinDataSign finDataSign = this.getById(id);
        if(ObjectUtil.isEmpty(finDataSign)) {
            throw new CommonException("财务数据汇总台账 - 转入签字费不存在，id值为：{}", id);
        }
        return finDataSign;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataSign> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataSign> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        List<BizOrg> bizOrgList = bizOrgService.list();
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataSign tmpFinData = BeanUtil.copyProperties(item,FinDataSign.class);
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
}
