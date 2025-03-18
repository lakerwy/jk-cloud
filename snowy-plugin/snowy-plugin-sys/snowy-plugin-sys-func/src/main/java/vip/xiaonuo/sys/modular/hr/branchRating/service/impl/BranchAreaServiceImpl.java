package vip.xiaonuo.sys.modular.hr.branchRating.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchArea;
import vip.xiaonuo.sys.modular.hr.branchRating.mapper.BranchAreaMapper;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchAreaService;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchAreaVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.ImportResultT;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;
import vip.xiaonuo.sys.modular.info.service.PartnerInfoService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分公司评级（二期）- 区域经济修正系数 Service接口实现类
 *
 * @author yong.ye
 * @date  2025/03/18 08:47
 **/
@Service
public class BranchAreaServiceImpl extends ServiceImpl<BranchAreaMapper, BranchArea> implements BranchAreaService {

    @Resource
    private PartnerInfoService partnerInfoService;

    @Override
    public Page<BranchArea> page(BranchAreaPageParam branchAreaPageParam) {
        QueryWrapper<BranchArea> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getBranchId())) {
            queryWrapper.lambda().eq(BranchArea::getBranchId, branchAreaPageParam.getBranchId());
        }
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getBranchName())) {
            queryWrapper.lambda().like(BranchArea::getBranchName, branchAreaPageParam.getBranchName());
        }
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getBranchCity())) {
            queryWrapper.lambda().like(BranchArea::getBranchCity, branchAreaPageParam.getBranchCity());
        }
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BranchArea::getBranchName,branchAreaPageParam.getSearchKey())
                    .or().like(BranchArea::getBranchCity,branchAreaPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(branchAreaPageParam.getSortField(), branchAreaPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchAreaPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchAreaPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchAreaPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BranchArea::getAreaRate);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BranchAreaAddParam branchAreaAddParam) {
        List<BranchArea> tmpList = this.list(new QueryWrapper<BranchArea>().lambda()
                .eq(BranchArea::getBranchName,branchAreaAddParam.getBranchName())
                .eq(BranchArea::getBranchId,branchAreaAddParam.getBranchId()));
        if(CollUtil.isNotEmpty(tmpList)){
            throw new CommonException("{}已存在区域经济修正系数数据！",branchAreaAddParam.getBranchName());
        }
        BranchArea branchArea = BeanUtil.toBean(branchAreaAddParam, BranchArea.class);
        this.save(branchArea);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BranchAreaEditParam branchAreaEditParam) {
        List<BranchArea> tmpList = this.list(new QueryWrapper<BranchArea>().lambda()
                .eq(BranchArea::getBranchName,branchAreaEditParam.getBranchName())
                .eq(BranchArea::getBranchId,branchAreaEditParam.getBranchId())
                .ne(BranchArea::getId,branchAreaEditParam.getId()));
        if(CollUtil.isNotEmpty(tmpList)){
            throw new CommonException("{}已存在区域经济修正系数数据！",branchAreaEditParam.getBranchName());
        }
        BranchArea branchArea = this.queryEntity(branchAreaEditParam.getId());
        BeanUtil.copyProperties(branchAreaEditParam, branchArea);
        this.updateById(branchArea);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BranchAreaIdParam> branchAreaIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(branchAreaIdParamList, BranchAreaIdParam::getId));
    }

    @Override
    public BranchArea detail(BranchAreaIdParam branchAreaIdParam) {
        return this.queryEntity(branchAreaIdParam.getId());
    }

    @Override
    public BranchArea queryEntity(String id) {
        BranchArea branchArea = this.getById(id);
        if(ObjectUtil.isEmpty(branchArea)) {
            throw new CommonException("分公司评级（二期）- 区域经济修正系数 不存在，id值为：{}", id);
        }
        return branchArea;
    }

    @Override
    public List<PartnerInfo> getBranchList() {
        //已填报
        List<BranchArea> branchAreaList = this.list();
        List<String> branchIds = branchAreaList.stream().map(x->x.getBranchId()).distinct().collect(Collectors.toList());
        //分公司
        List<PartnerInfo> result = getPartnerList();
        return result.stream().filter(x -> !branchIds.contains(x.getId())).collect(Collectors.toList());
    }

    @Override
    public BranchAreaVo getList(BranchAreaPageParam branchAreaPageParam) {
        QueryWrapper<BranchArea> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getBranchId())) {
            queryWrapper.lambda().eq(BranchArea::getBranchId, branchAreaPageParam.getBranchId());
        }
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getBranchName())) {
            queryWrapper.lambda().like(BranchArea::getBranchName, branchAreaPageParam.getBranchName());
        }
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getBranchCity())) {
            queryWrapper.lambda().like(BranchArea::getBranchCity, branchAreaPageParam.getBranchCity());
        }
        if(ObjectUtil.isNotEmpty(branchAreaPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BranchArea::getBranchName,branchAreaPageParam.getSearchKey())
                    .or().like(BranchArea::getBranchCity,branchAreaPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(branchAreaPageParam.getSortField(), branchAreaPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchAreaPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchAreaPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchAreaPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BranchArea::getAreaRate);
        }
        List<BranchArea> branchAreaList = this.list(queryWrapper);
        BranchAreaVo result = new BranchAreaVo();
        List<String> xAxis = new ArrayList<>();
        List<BigDecimal> branchCityGdp = new ArrayList<>();
        List<BigDecimal> areaRate = new ArrayList<>();
        for (BranchArea item : branchAreaList) {
            xAxis.add(item.getBranchName());
            branchCityGdp.add(item.getBranchCityGdp());
            areaRate.add(item.getAreaRate());
        }
        result.setXAxis(xAxis);
        result.setBranchCityGdp(branchCityGdp);
        result.setAreaRate(areaRate);
        result.setBranchAreaList(branchAreaList);
        return result;
    }

    @Override
    public ImportResultT doImport(ImportParam importParam) {
        if(CollUtil.isEmpty(importParam.getBranchAreaList())){
            throw new CommonException("无有效导入数据！");
        }
        List<BranchAreaAddParam> dataList = importParam.getBranchAreaList();
        List<PartnerInfo> partnerList = getPartnerList();
        List<BranchArea> branchAreaList = this.list();
        //校验
        for (BranchAreaAddParam item : importParam.getBranchAreaList()) {
            String errorMsg = "";
            if(ObjectUtil.isEmpty(item.getBranchName())){
                errorMsg = "分公司名称必填！";
            }
            if(ObjectUtil.isEmpty(errorMsg)){
                List<BranchAreaAddParam> tmpList = dataList.stream().filter(x->item.getBranchName().equals(x.getBranchName())).collect(Collectors.toList());
                if(CollUtil.isNotEmpty(tmpList) && tmpList.size() > 1){
                    errorMsg = "导入数据中存在分公司名称重复记录！";
                }
            }
            if(ObjectUtil.isEmpty(errorMsg)){
                PartnerInfo tmpPartner = partnerList.stream().filter(x->item.getBranchName().equals(x.getName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpPartner)){
                    item.setBranchId(tmpPartner.getId());
                }else{
                    errorMsg = "系统中未匹配到该分公司！";
                }
            }
            if(ObjectUtil.isEmpty(errorMsg)){
                BranchArea tmpBranchArea = branchAreaList.stream().filter(x->item.getBranchName().equals(x.getBranchName()) && item.getBranchId().equals(x.getBranchId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpBranchArea)){
                    errorMsg = "系统中已存在该分公司区域经济修正系数数据！";
                }
            }
            item.setErrorMsg(errorMsg);
        }
        //导入
        ImportResultT result = new ImportResultT();
        result.setTotal(importParam.getBranchAreaList().size());
        result.setErrCount(importParam.getBranchAreaList().stream().filter(x->ObjectUtil.isNotEmpty(x.getErrorMsg())).collect(Collectors.toList()).size());
        result.setSucCount(result.getTotal() - result.getErrCount());
        result.setResultList(importParam.getBranchAreaList());
        List<BranchArea> addList = BeanUtil.copyToList(importParam.getBranchAreaList(),BranchArea.class);
        this.saveBatch(addList);
        return result;
    }


    /**
     * 从合作伙伴，获取分公司数据
     * @return
     */
    @Override
    public List<PartnerInfo> getPartnerList(){
        QueryWrapper<PartnerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerInfo::getDeleteFlag,"NOT_DELETE");
        queryWrapper.lambda().in(PartnerInfo::getPartnerType,"分公司", "分公司（注册）");
        return partnerInfoService.list(queryWrapper);
    }

    @Override
    public BranchArea getByBranch(BranchAreaParam branchAreaParam) {
        return this.getOne(new LambdaQueryWrapper<BranchArea>()
                .eq(BranchArea::getBranchId,branchAreaParam.getBranchId())
                .eq(BranchArea::getBranchName,branchAreaParam.getBranchName())
                .last(" limit 1 "));
    }
}
