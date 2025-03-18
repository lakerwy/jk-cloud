package vip.xiaonuo.sys.modular.hr.branchRating.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchFill;
import vip.xiaonuo.sys.modular.hr.branchRating.mapper.BranchFillMapper;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchAreaService;
import vip.xiaonuo.sys.modular.hr.branchRating.service.BranchFillService;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchFillVo;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;
import vip.xiaonuo.sys.modular.user.entity.SysUser;
import vip.xiaonuo.sys.modular.user.service.SysUserService;

import javax.annotation.Resource;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分公司评级（二期）- 分公司评级填报Service接口实现类
 *
 * @author yong.ye
 * @date  2025/03/18 08:48
 **/
@Service
public class BranchFillServiceImpl extends ServiceImpl<BranchFillMapper, BranchFill> implements BranchFillService {

    @Resource
    private BranchAreaService branchAreaService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private SysUserService sysUserService;

    @Override
    public Page<BranchFill> page(BranchFillPageParam branchFillPageParam) {
        QueryWrapper<BranchFill> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getBranchId())) {
            queryWrapper.lambda().eq(BranchFill::getBranchId, branchFillPageParam.getBranchId());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getBranchName())) {
            queryWrapper.lambda().like(BranchFill::getBranchName, branchFillPageParam.getBranchName());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getBranchScope())) {
            queryWrapper.lambda().like(BranchFill::getBranchScope, branchFillPageParam.getBranchScope());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getArea())) {
            queryWrapper.lambda().eq(BranchFill::getArea, branchFillPageParam.getArea());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getAreaManager())) {
            queryWrapper.lambda().like(BranchFill::getAreaManager, branchFillPageParam.getAreaManager());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getLevel())) {
            queryWrapper.lambda().eq(BranchFill::getLevel, branchFillPageParam.getLevel());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getIsShow())) {
            queryWrapper.lambda().eq(BranchFill::getIsShow, branchFillPageParam.getIsShow());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getSn())) {
            queryWrapper.lambda().like(BranchFill::getSn, branchFillPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(branchFillPageParam.getStatus())) {
            queryWrapper.lambda().eq(BranchFill::getStatus, branchFillPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(branchFillPageParam.getSortField(), branchFillPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(branchFillPageParam.getSortOrder());
            queryWrapper.orderBy(true, branchFillPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(branchFillPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BranchFill::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BranchFillAddParam branchFillAddParam) {
        BranchFill branchFill = BeanUtil.toBean(branchFillAddParam, BranchFill.class);
        //走审批流程
        String processInstanceId = branchFillAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            branchFill.setId(id);
            branchFill.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            branchFill.setModelId(modelId);
            branchFill.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.BRANCH_FILL.getBizTable(),id,processInstanceId,dbName);
        }else{
            branchFill.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(branchFill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BranchFillEditParam branchFillEditParam) {
        String id = branchFillEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(branchFillEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(branchFillEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                branchFillEditParam.setId(id);
            }
        }
        BranchFill branchFill = this.queryEntity(branchFillEditParam.getId());
        BeanUtil.copyProperties(branchFillEditParam, branchFill);
        this.updateById(branchFill);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BranchFillIdParam> branchFillIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(branchFillIdParamList, BranchFillIdParam::getId));
    }

    @Override
    public BranchFill detail(BranchFillIdParam branchFillIdParam) {
        return this.queryEntity(branchFillIdParam.getId());
    }

    @Override
    public BranchFill queryEntity(String id) {
        BranchFill branchFill = this.getById(id);
        if(ObjectUtil.isEmpty(branchFill)) {
            throw new CommonException("分公司评级（二期）- 分公司评级填报不存在，id值为：{}", id);
        }
        return branchFill;
    }

    @Override
    public List<String> getQuarterList() {
        int currentYear = Year.now().getValue();
        // 获取去年
        int lastYear = currentYear - 1;
        // 获取前年
        int yearBeforeLast = currentYear - 2;
        List<String> result = new ArrayList<>();
        result.add(StrUtil.toString(currentYear) + "年上半年");
        result.add(StrUtil.toString(currentYear) + "年下半年");
        result.add(StrUtil.toString(lastYear) + "年上半年");
        result.add(StrUtil.toString(lastYear) + "年下半年");
        result.add(StrUtil.toString(yearBeforeLast) + "年上半年");
        result.add(StrUtil.toString(yearBeforeLast) + "年下半年");
        return result;
    }

    @Override
    public List<PartnerInfo> getBranchList(String fillSeason) {
        if(StrUtil.isEmpty(fillSeason)){
            throw new CommonException("填报季度不能为空！");
        }
        //已填报
        List<BranchFill> branchFillList = this.list(new LambdaQueryWrapper<BranchFill>()
                .eq(BranchFill::getFillSeason,fillSeason)
                .in(BranchFill::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue()));
        List<String> branchIds = branchFillList.stream().map(x->x.getBranchId()).collect(Collectors.toList());
        //分公司
        List<PartnerInfo> result = branchAreaService.getPartnerList();
        return result.stream().filter(x -> !branchIds.contains(x.getId())).collect(Collectors.toList());
    }

    @Override
    public void show(BranchFillShowParam showParam) {
        //全部公示|取消全部公示
        if(1 == showParam.getIsAll()){
            if(ObjectUtil.isEmpty(showParam.getFillSeason())){
                throw new CommonException("操作全部公示必须传入填报季度");
            }
            List<BranchFill> tmpList = this.list(new LambdaQueryWrapper<BranchFill>()
                    .eq(BranchFill::getFillSeason,showParam.getFillSeason())
                    .in(BranchFill::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue()));
            if(CollUtil.isNotEmpty(tmpList)){
                tmpList.forEach(item->item.setIsShow(showParam.getIsShow()));
                this.updateBatchById(tmpList);
            }
        }else{
            //单一操作
            if(ObjectUtil.isEmpty(showParam.getId())){
                throw new CommonException("操作单一公示必须传入填报记录id");
            }
            BranchFill tmpObj = this.queryEntity(showParam.getId());
            tmpObj.setIsShow(showParam.getIsShow());
            this.updateById(tmpObj);
        }
    }

    @Override
    public List<BranchFillVo> getBranchFillList(BranchFillParam fillParam) {
        List<BranchFillVo> result = new ArrayList<>();
        QueryWrapper<BranchFill> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BranchFill::getFillSeason,fillParam.getFillSeason());
        queryWrapper.lambda().in(BranchFill::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        List<BranchFill> tmpFillList = this.list(queryWrapper);
        //全部分公司
        List<PartnerInfo> partnerList = branchAreaService.getPartnerList();
        //用户
        List<SysUser> sysUserList = sysUserService.list();
        for (PartnerInfo item : partnerList) {
            BranchFillVo tmpObj = new BranchFillVo();
            tmpObj.setFillSeason(fillParam.getFillSeason());
            tmpObj.setBranchId(item.getId());
            tmpObj.setBranchName(item.getName());
            tmpObj.setArea(item.getRegion());
            tmpObj.setLevel("-");
            tmpObj.setIsShow(0);
            tmpObj.setAreaManagerId(item.getRegionalManager());
            SysUser tmpSysUser = sysUserList.stream().filter(x -> x.getId().equals(tmpObj.getAreaManagerId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpSysUser)){
                tmpObj.setAreaManager(tmpSysUser.getName());
            }
            BranchFill tmpFilter = tmpFillList.stream().filter(x -> item.getId().equals(x.getBranchId()) && item.getName().equals(x.getBranchName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpFilter)){
                tmpObj.setArea(tmpFilter.getArea());
                tmpObj.setAreaManagerId(tmpFilter.getAreaManagerId());
                tmpObj.setAreaManager(tmpFilter.getAreaManager());
                tmpObj.setTotalScore(tmpFilter.getTotalScore());
                tmpObj.setLevel(tmpFilter.getLevel());
                tmpObj.setBranchScope(tmpFilter.getBranchScope());
                tmpObj.setSn(tmpFilter.getSn());
                tmpObj.setId(tmpFilter.getId());
                tmpObj.setIsShow(tmpFilter.getIsShow());
                tmpObj.setModelId(tmpFilter.getModelId());
                tmpObj.setProcessInstanceId(tmpFilter.getProcessInstanceId());
                tmpObj.setStatus(tmpFilter.getStatus());
            }
            String fillStatus = "";
            if(ObjectUtil.isEmpty(tmpObj.getId())){
                fillStatus = "未填报";
            } else{
                if(FlwStatusEnum.ACTIVE_STATUS.getValue().equals(tmpObj.getStatus())){
                    fillStatus = "审核中";
                }else{
                    fillStatus = "已填报";
                }
            }
            tmpObj.setFillStatus(fillStatus);
            result.add(tmpObj);
        }
        //特殊处理
        for (BranchFillVo item : result) {
            if("江门分公司".equals(item.getBranchName())){
                BranchFillVo tmpHN = result.stream().filter(x -> "海南分公司".equals(x.getBranchName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpHN)){
                    item.setFillStatus(tmpHN.getFillStatus());
                    item.setIsShow(tmpHN.getIsShow());
                }
            }
        }
        if(ObjectUtil.isNotEmpty(fillParam.getIsShow())){
            result = result.stream().filter(x->fillParam.getIsShow().equals(x.getIsShow())).collect(Collectors.toList());
        }
        if(ObjectUtil.isNotEmpty(fillParam.getArea())){
            result = result.stream().filter(x->fillParam.getArea().equals(x.getArea())).collect(Collectors.toList());
        }
        if(ObjectUtil.isNotEmpty(fillParam.getLevel())){
            result = result.stream().filter(x->fillParam.getLevel().equals(x.getLevel())).collect(Collectors.toList());
        }
        if(ObjectUtil.isNotEmpty(fillParam.getFillStatus())){
            result = result.stream().filter(x->fillParam.getFillStatus().equals(x.getFillStatus())).collect(Collectors.toList());
        }
        if(ObjectUtil.isNotEmpty(fillParam.getSearchKey())){
            result = result.stream().filter(x->x.getBranchName().contains(fillParam.getSearchKey())).collect(Collectors.toList());
        }
        result.sort(Comparator.comparing(BranchFillVo::getArea,Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(BranchFillVo::getBranchName,Comparator.nullsLast(Comparator.naturalOrder())));
        return result;
    }
}
