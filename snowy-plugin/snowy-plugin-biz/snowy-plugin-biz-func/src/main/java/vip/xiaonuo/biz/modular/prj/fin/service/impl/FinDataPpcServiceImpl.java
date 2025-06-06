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
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPpc;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataPpcMapper;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataPpcService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务数据汇总台账 - 项目回款Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
@Service
@DS("slave_1")
public class FinDataPpcServiceImpl extends ServiceImpl<FinDataPpcMapper, FinDataPpc> implements FinDataPpcService {

    @Resource
    private HetongService hetongService;

    @Resource
    private BizOrgService bizOrgService;

    @Override
    public Page<FinDataPpc> page(FinDataPpcPageParam finDataPpcPageParam) {
        QueryWrapper<FinDataPpc> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataPpcPageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataPpc::getContractId,finDataPpcPageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataPpcPageParam.getUnitId())){
            queryWrapper.lambda().eq(FinDataPpc::getUnitId,finDataPpcPageParam.getUnitId());
        }
        if(ObjectUtil.isNotEmpty(finDataPpcPageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataPpc::getContractNo, finDataPpcPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataPpcPageParam.getUnitName())) {
            queryWrapper.lambda().like(FinDataPpc::getUnitName, finDataPpcPageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(finDataPpcPageParam.getStartHkDate()) && ObjectUtil.isNotEmpty(finDataPpcPageParam.getEndHkDate())) {
            queryWrapper.lambda().between(FinDataPpc::getHkDate, finDataPpcPageParam.getStartHkDate(), finDataPpcPageParam.getEndHkDate());
        }
        if(ObjectUtil.isNotEmpty(finDataPpcPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataPpc::getContractNo,finDataPpcPageParam.getSearchKey())
                    .or().like(FinDataPpc::getUnitName,finDataPpcPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataPpcPageParam.getSortField(), finDataPpcPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataPpcPageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataPpcPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataPpcPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataPpc::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataPpcAddParam finDataPpcAddParam) {
        FinDataPpc finDataPpc = BeanUtil.toBean(finDataPpcAddParam, FinDataPpc.class);
        finDataPpc.setExtJson("页面-新增");
        this.save(finDataPpc);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataPpcEditParam finDataPpcEditParam) {
        FinDataPpc finDataPpc = this.queryEntity(finDataPpcEditParam.getId());
        BeanUtil.copyProperties(finDataPpcEditParam, finDataPpc);
        this.updateById(finDataPpc);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataPpcIdParam> finDataPpcIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataPpcIdParamList, FinDataPpcIdParam::getId));
    }

    @Override
    public FinDataPpc detail(FinDataPpcIdParam finDataPpcIdParam) {
        return this.queryEntity(finDataPpcIdParam.getId());
    }

    @Override
    public FinDataPpc queryEntity(String id) {
        FinDataPpc finDataPpc = this.getById(id);
        if(ObjectUtil.isEmpty(finDataPpc)) {
            throw new CommonException("财务数据汇总台账 - 项目回款不存在，id值为：{}", id);
        }
        return finDataPpc;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataPpc> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataPpc> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        List<BizOrg> bizOrgList = bizOrgService.list();
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataPpc tmpFinData = BeanUtil.copyProperties(item,FinDataPpc.class);
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
                    errorMsg += "回款单位不匹配；";
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
