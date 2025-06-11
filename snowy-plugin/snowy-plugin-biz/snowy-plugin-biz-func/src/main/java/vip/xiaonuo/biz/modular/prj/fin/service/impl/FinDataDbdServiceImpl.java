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
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataDbd;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataDbdMapper;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataDbdService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务数据汇总台账 - 拨付明细Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:23
 **/
@Service
@DS("slave_1")
public class FinDataDbdServiceImpl extends ServiceImpl<FinDataDbdMapper, FinDataDbd> implements FinDataDbdService {

    @Resource
    private HetongService hetongService;

    @Override
    public Page<FinDataDbd> page(FinDataDbdPageParam finDataDbdPageParam) {
        QueryWrapper<FinDataDbd> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataDbdPageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataDbd::getContractId,finDataDbdPageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataDbdPageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataDbd::getContractNo, finDataDbdPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataDbdPageParam.getStartBfDate()) && ObjectUtil.isNotEmpty(finDataDbdPageParam.getEndBfDate())) {
            queryWrapper.lambda().between(FinDataDbd::getBfDate, finDataDbdPageParam.getStartBfDate(), finDataDbdPageParam.getEndBfDate());
        }
        if(ObjectUtil.isNotEmpty(finDataDbdPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataDbd::getContractNo,finDataDbdPageParam.getSearchKey())
                    .or().like(FinDataDbd::getKkOtherContent,finDataDbdPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataDbdPageParam.getSortField(), finDataDbdPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataDbdPageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataDbdPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataDbdPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataDbd::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataDbdAddParam finDataDbdAddParam) {
        FinDataDbd finDataDbd = BeanUtil.toBean(finDataDbdAddParam, FinDataDbd.class);
        finDataDbd.setExtJson("页面-新增");
        this.save(finDataDbd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataDbdEditParam finDataDbdEditParam) {
        FinDataDbd finDataDbd = this.queryEntity(finDataDbdEditParam.getId());
        BeanUtil.copyProperties(finDataDbdEditParam, finDataDbd);
        this.updateById(finDataDbd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataDbdIdParam> finDataDbdIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataDbdIdParamList, FinDataDbdIdParam::getId));
    }

    @Override
    public FinDataDbd detail(FinDataDbdIdParam finDataDbdIdParam) {
        return this.queryEntity(finDataDbdIdParam.getId());
    }

    @Override
    public FinDataDbd queryEntity(String id) {
        FinDataDbd finDataDbd = this.getById(id);
        if(ObjectUtil.isEmpty(finDataDbd)) {
            throw new CommonException("财务数据汇总台账 - 拨付明细不存在，id值为：{}", id);
        }
        return finDataDbd;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataDbd> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataDbd> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataDbd tmpFinData = BeanUtil.copyProperties(item,FinDataDbd.class);
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
}
