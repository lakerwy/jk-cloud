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
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataCon;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataConMapper;
import vip.xiaonuo.biz.modular.prj.fin.service.FinDataConService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务数据汇总台账 - 施工Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:22
 **/
@Service
@DS("slave_1")
public class FinDataConServiceImpl extends ServiceImpl<FinDataConMapper, FinDataCon> implements FinDataConService {

    @Resource
    private HetongService hetongService;

    @Override
    public Page<FinDataCon> page(FinDataConPageParam finDataConPageParam) {
        QueryWrapper<FinDataCon> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataConPageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataCon::getContractNo, finDataConPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataConPageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataCon::getContractId,finDataConPageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataConPageParam.getStartBfDate()) && ObjectUtil.isNotEmpty(finDataConPageParam.getEndBfDate())) {
            queryWrapper.lambda().between(FinDataCon::getBfDate, finDataConPageParam.getStartBfDate(), finDataConPageParam.getEndBfDate());
        }
        if(ObjectUtil.isNotEmpty(finDataConPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataCon::getContractNo,finDataConPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataConPageParam.getSortField(), finDataConPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataConPageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataConPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataConPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataCon::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataConAddParam finDataConAddParam) {
        FinDataCon finDataCon = BeanUtil.toBean(finDataConAddParam, FinDataCon.class);
        finDataCon.setExtJson("页面-新增");
        this.save(finDataCon);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataConEditParam finDataConEditParam) {
        FinDataCon finDataCon = this.queryEntity(finDataConEditParam.getId());
        BeanUtil.copyProperties(finDataConEditParam, finDataCon);
        this.updateById(finDataCon);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataConIdParam> finDataConIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataConIdParamList, FinDataConIdParam::getId));
    }

    @Override
    public FinDataCon detail(FinDataConIdParam finDataConIdParam) {
        return this.queryEntity(finDataConIdParam.getId());
    }

    @Override
    public FinDataCon queryEntity(String id) {
        FinDataCon finDataCon = this.getById(id);
        if(ObjectUtil.isEmpty(finDataCon)) {
            throw new CommonException("财务数据汇总台账 - 施工不存在，id值为：{}", id);
        }
        return finDataCon;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataCon> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataCon> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataCon tmpFinData = BeanUtil.copyProperties(item,FinDataCon.class);
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
