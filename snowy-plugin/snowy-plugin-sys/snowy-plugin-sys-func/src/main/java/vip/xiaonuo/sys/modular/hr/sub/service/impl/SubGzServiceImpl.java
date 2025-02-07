package vip.xiaonuo.sys.modular.hr.sub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubFund;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGz;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGzItem;
import vip.xiaonuo.sys.modular.hr.sub.enums.SubGzItemTypeEnum;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubFundMapper;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubGzMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzItemService;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzService;
import vip.xiaonuo.sys.modular.hr.sub.util.SubUtils;
import vip.xiaonuo.sys.modular.hr.sub.vo.MonthVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.SubGzItemVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearGzVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearNumVo;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分公司-工资发放（流程）Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/06 09:09
 **/
@Service
public class SubGzServiceImpl extends ServiceImpl<SubGzMapper, SubGz> implements SubGzService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private SubGzItemService subGzItemService;

    @Resource
    private SubGzMapper subGzMapper;

    @Resource
    private SubFundMapper subFundMapper;

    /**
     * SubGz - 工资奖金  - 人数
     */
    public static final String[] NUM_GZ_REPORT_TYPE_LIST = {"1","2"};

    /**
     * SubFund - 五险一金 - 人数
     */
    public static final String[] NUM_FUND_REPORT_TYPE_LIST = {"5"};

    /**
     * SubGz - 工资奖金  - 总额
     */
    public static final String[] GZ_REPORT_TYPE_LIST = {"3","4"};

    /**
     * SubFund - 五险一金 - 总额
     */
    public static final String[] FUND_REPORT_TYPE_LIST = {"6"};

    /**
     * 构造12月
     */

    public static final String[] MONTH_LIST = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    @Override
    public Page<SubGz> page(SubGzPageParam subGzPageParam) {
        QueryWrapper<SubGz> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(subGzPageParam.getOrgId())) {
            queryWrapper.lambda().eq(SubGz::getOrgId, subGzPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getOrgName())) {
            queryWrapper.lambda().like(SubGz::getOrgName, subGzPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getYM())) {
            queryWrapper.lambda().eq(SubGz::getYM, subGzPageParam.getYM());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getRegion())) {
            queryWrapper.lambda().eq(SubGz::getRegion, subGzPageParam.getRegion());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getManagerName())) {
            queryWrapper.lambda().like(SubGz::getManagerName, subGzPageParam.getManagerName());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getSn())) {
            queryWrapper.lambda().like(SubGz::getSn, subGzPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getStatus())) {
            queryWrapper.lambda().eq(SubGz::getStatus, subGzPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(subGzPageParam.getSortField(), subGzPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subGzPageParam.getSortOrder());
            queryWrapper.orderBy(true, subGzPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subGzPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(SubGz::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubGzParam subGzParam) {
        SubGz subGz = BeanUtil.toBean(subGzParam, SubGz.class);
        //走审批流程
        String processInstanceId = subGzParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            subGz.setId(id);
            subGz.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            subGz.setModelId(modelId);
            subGz.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.SUB_GZ.getBizTable(),id,processInstanceId,dbName);
        }else{
            subGz.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(subGz);
        //处理发票
        List<SubGzItem> subGzItemList = new ArrayList<>();
        if(CollUtil.isNotEmpty(subGzParam.getGzList())){
            List<SubGzItem> tmpItems = BeanUtil.copyToList(subGzParam.getGzList(), SubGzItem.class);
            tmpItems.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.GZ.getValue());
            });
            subGzItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subGzParam.getJjList())){
            List<SubGzItem> tmpItems = BeanUtil.copyToList(subGzParam.getJjList(), SubGzItem.class);
            tmpItems.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.JJ.getValue());
            });
            subGzItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subGzItemList)) {
            subGzItemService.saveBatch(subGzItemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubGzParam subGzParam) {
        String id = subGzParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(subGzParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(subGzParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                subGzParam.setId(id);
            }
        }
        SubGz subGz = this.queryEntity(subGzParam.getId());
        BeanUtil.copyProperties(subGzParam, subGz);
        this.updateById(subGz);
        //处理工资
        if(CollUtil.isNotEmpty(subGzParam.getGzList())){
            //本次传入的数据
            List<SubGzItem> gzItemList = BeanUtil.copyToList(subGzParam.getGzList(),SubGzItem.class);
            gzItemList.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.GZ.getValue());
            });
            //获取历史数据
            List<SubGzItem> oldGzItemList = subGzItemService.list(new QueryWrapper<SubGzItem>().lambda().eq(SubGzItem::getSubGzId,subGz.getId())
                            .eq(SubGzItem::getType,SubGzItemTypeEnum.GZ.getValue()));
            List<String> oldIds = oldGzItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<SubGzItem> addList = gzItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<SubGzItem> updateList = gzItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<SubGzItem> removeList = oldGzItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                subGzItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                subGzItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                subGzItemService.removeByIds(removeList);
            }
        }
        //处理奖金
        if(CollUtil.isNotEmpty(subGzParam.getJjList())){
            //本次传入的数据
            List<SubGzItem> gzItemList = BeanUtil.copyToList(subGzParam.getJjList(),SubGzItem.class);
            gzItemList.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.JJ.getValue());
            });
            //获取历史数据
            List<SubGzItem> oldGzItemList = subGzItemService.list(new QueryWrapper<SubGzItem>().lambda().eq(SubGzItem::getSubGzId,subGz.getId())
                    .eq(SubGzItem::getType,SubGzItemTypeEnum.JJ.getValue()));
            List<String> oldIds = oldGzItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<SubGzItem> addList = gzItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<SubGzItem> updateList = gzItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<SubGzItem> removeList = oldGzItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                subGzItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                subGzItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                subGzItemService.removeByIds(removeList);
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubGzIdParam> subGzIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(subGzIdParamList, SubGzIdParam::getId);
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            subGzItemService.remove(new LambdaQueryWrapper<SubGzItem>().in(SubGzItem::getSubGzId,ids));
        }
    }

    @Override
    public SubGz detail(SubGzIdParam subGzIdParam) {
        return this.queryEntity(subGzIdParam.getId());
    }

    @Override
    public SubGz queryEntity(String id) {
        SubGz subGz = this.getById(id);
        if(ObjectUtil.isEmpty(subGz)) {
            throw new CommonException("分公司-工资发放（流程）不存在，id值为：{}", id);
        }
        return subGz;
    }

    @Override
    public List<MonthVo> monthTotal(SearchTotal searchTotal) {
        /*3 构造查询数据 - 工资 + 奖金 */
        List<SubGzItemVo> subGzItemDataList = new ArrayList<>();
        List<MonthVo> gzResult = getSubGzMonthVoList(searchTotal,"month",subGzItemDataList);
        List<SubGzItemVo> gzItemList = subGzItemDataList.stream().filter(x->SubGzItemTypeEnum.GZ.getValue().equals(x.getType())).collect(Collectors.toList());
        //统计五险一金
        if(CollUtil.isNotEmpty(gzResult)){
            for (MonthVo item : gzResult){
                List<SubGzItemVo> tmpGzItemList = gzItemList.stream().filter(x -> item.getYM().equals(x.getFlwYM()) && item.getRegion().equals(x.getRegion()) && item.getOrgId().equals(x.getOrgId()) && item.getOrgName().equals(x.getOrgName())).collect(Collectors.toList());
                if(CollUtil.isNotEmpty(tmpGzItemList)){
                    item.setInsTotal(SubUtils.addTotal(tmpGzItemList.stream().map(x->x.getIns()).collect(Collectors.toList())));
                    item.setGjjTotal(SubUtils.addTotal(tmpGzItemList.stream().map(x->x.getGjj()).collect(Collectors.toList())));
                }
            }
        }

        /*4 构造查询数据 - 五险一金 */
        List<MonthVo> fundResult = getSubFundMonthVoList(searchTotal,"month");

        /*5 构造结果集 */
        List<MonthVo> result = new ArrayList<>();
        if(CollUtil.isNotEmpty(gzResult)){
            for (MonthVo item : gzResult) {
                MonthVo tmpFund = fundResult.stream().filter(x -> item.getYM().equals(x.getYM()) && item.getRegion().equals(x.getRegion()) && item.getOrgId().equals(x.getOrgId()) && item.getOrgName().equals(x.getOrgName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpFund)){
                    item.setYjWxNum(tmpFund.getYjWxNum());
                    item.setSjWxNum(tmpFund.getSjWxNum());
                    item.setYjWxTotal(tmpFund.getYjWxTotal());
                    item.setSjWxTotal(tmpFund.getSjWxTotal());
                    item.setYjGjjNum(tmpFund.getYjGjjNum());
                    item.setSjGjjNum(tmpFund.getSjGjjNum());
                    item.setYjGjjTotal(tmpFund.getYjGjjTotal());
                    item.setSjGjjTotal(tmpFund.getSjGjjTotal());
                }
            }
            result.addAll(gzResult);
        }
        if(CollUtil.isNotEmpty(fundResult)){
            for (MonthVo item : fundResult) {
                MonthVo tmpObj = gzResult.stream().filter(x -> item.getYM().equals(x.getYM()) && item.getRegion().equals(x.getRegion()) && item.getOrgId().equals(x.getOrgId()) && item.getOrgName().equals(x.getOrgName())).findFirst().orElse(null);
                if(ObjectUtil.isEmpty(tmpObj)){
                    result.add(item);
                }
            }
        }
        result.sort(Comparator.comparing(MonthVo::getYM).thenComparing(MonthVo::getRegion).thenComparing(MonthVo::getOrgName));
        return result;
    }

    @Override
    public List<YearNumVo> yearNumTotal(SearchTotal searchTotal) {
        if(ObjectUtil.isEmpty(searchTotal.getReportType())){
            throw new CommonException("报表类型必填");
        }

        //数据源
        List<MonthVo> dataList = new ArrayList<>();

        /* 不同的报表 - 数据源不一致 */
        if(CollectionUtil.newArrayList(NUM_GZ_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
            dataList = getSubGzMonthVoList(searchTotal,"year",null);
        } else if (CollectionUtil.newArrayList(NUM_FUND_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
            dataList = getSubFundMonthVoList(searchTotal,"year");
        }else {
            throw new CommonException("不支持的报表类型");
        }

        //构建结果集
        List<YearNumVo> result = new ArrayList<>();
        if(CollUtil.isNotEmpty(dataList)){
            dataList.forEach(data->{
                YearNumVo tmp = result.stream().filter(x -> data.getRegion().equals(x.getRegion()) && data.getOrgId().equals(x.getOrgId()) && data.getOrgName().equals(x.getOrgName())).findFirst().orElse(null);
                if(ObjectUtil.isEmpty(tmp)){
                    YearNumVo tmpYearNumVo = new YearNumVo();
                    tmpYearNumVo.setYM(searchTotal.getYM());
                    tmpYearNumVo.setRegion(data.getRegion());
                    tmpYearNumVo.setOrgId(data.getOrgId());
                    tmpYearNumVo.setOrgName(data.getOrgName());
                    result.add(tmpYearNumVo);
                }
            });
            /*构造12月数据*/
            List<String> monthList = CollectionUtil.newArrayList(MONTH_LIST);
            for (YearNumVo obj : result) {
                Integer sumNums = 0;
                for (String month : monthList) {
                    String tmpYM = obj.getYM() + "-" + month;
                    MonthVo tmpData = dataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName()) && tmpYM.equals(x.getYM())).findFirst().orElse(null);
                    Integer tmpNums = 0;
                    if(ObjectUtil.isNotEmpty(tmpData)){
                        if("1".equals(searchTotal.getReportType())){
                            tmpNums = tmpData.getGzYfNum();
                        } else if ("2".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getGzSfNum();
                        } else if("5".equals(searchTotal.getReportType())){
                            tmpNums = tmpData.getSjWxNum();
                        }
                    }
                    sumNums = sumNums + tmpNums;
                    switch (month){
                        case "01": obj.setNum1(tmpNums); break;
                        case "02": obj.setNum2(tmpNums); break;
                        case "03": obj.setNum3(tmpNums); break;
                        case "04": obj.setNum4(tmpNums); break;
                        case "05": obj.setNum5(tmpNums); break;
                        case "06": obj.setNum6(tmpNums); break;
                        case "07": obj.setNum7(tmpNums); break;
                        case "08": obj.setNum8(tmpNums); break;
                        case "09": obj.setNum9(tmpNums); break;
                        case "10": obj.setNum10(tmpNums); break;
                        case "11": obj.setNum11(tmpNums); break;
                        case "12": obj.setNum12(tmpNums); break;
                        default:break;
                    }
                }
                obj.setAvgNum(sumNums/12d);
            }
        }
        result.sort(Comparator.comparing(YearNumVo::getYM).thenComparing(YearNumVo::getRegion).thenComparing(YearNumVo::getOrgName));
        return result;
    }

    @Override
    public List<YearGzVo> yearGzTotal(SearchTotal searchTotal) {
        if(ObjectUtil.isEmpty(searchTotal.getReportType())){
            throw new CommonException("报表类型必填");
        }

        //数据源
        List<MonthVo> dataList = new ArrayList<>();

        /* 不同的报表 - 数据源不一致 */
        if(CollectionUtil.newArrayList(GZ_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
            dataList = getSubGzMonthVoList(searchTotal, "year",null);
        } else if (CollectionUtil.newArrayList(FUND_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
            dataList = getSubFundMonthVoList(searchTotal,"year");
        }else {
            throw new CommonException("不支持的报表类型");
        }

        //构建结果集
        List<YearGzVo> result = new ArrayList<>();
        if(CollUtil.isNotEmpty(dataList)){
            dataList.forEach(data->{
                YearGzVo tmp = result.stream().filter(x -> data.getRegion().equals(x.getRegion()) && data.getOrgId().equals(x.getOrgId()) && data.getOrgName().equals(x.getOrgName())).findFirst().orElse(null);
                if(ObjectUtil.isEmpty(tmp)){
                    YearGzVo tmpYearNumVo = new YearGzVo();
                    tmpYearNumVo.setYM(searchTotal.getYM());
                    tmpYearNumVo.setRegion(data.getRegion());
                    tmpYearNumVo.setOrgId(data.getOrgId());
                    tmpYearNumVo.setOrgName(data.getOrgName());
                    result.add(tmpYearNumVo);
                }
            });
            /*构造12月数据*/
            List<String> monthList = CollectionUtil.newArrayList(MONTH_LIST);
            for (YearGzVo obj : result) {
                BigDecimal sumNums = BigDecimal.ZERO;
                for (String month : monthList) {
                    String tmpYM = obj.getYM() + "-" + month;
                    MonthVo tmpData = dataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName()) && tmpYM.equals(x.getYM())).findFirst().orElse(null);
                    BigDecimal tmpNums = BigDecimal.ZERO;
                    if(ObjectUtil.isNotEmpty(tmpData)){
                        if("3".equals(searchTotal.getReportType())){
                            tmpNums = tmpData.getYfGzTotal();
                        } else if ("4".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getSfGzTotal();
                        } else if("6".equals(searchTotal.getReportType())){
                            tmpNums = tmpData.getSjWxTotal().add(tmpData.getSjGjjTotal());
                        }
                    }
                    sumNums = sumNums.add(tmpNums);
                    switch (month){
                        case "01": obj.setNum1(tmpNums); break;
                        case "02": obj.setNum2(tmpNums); break;
                        case "03": obj.setNum3(tmpNums); break;
                        case "04": obj.setNum4(tmpNums); break;
                        case "05": obj.setNum5(tmpNums); break;
                        case "06": obj.setNum6(tmpNums); break;
                        case "07": obj.setNum7(tmpNums); break;
                        case "08": obj.setNum8(tmpNums); break;
                        case "09": obj.setNum9(tmpNums); break;
                        case "10": obj.setNum10(tmpNums); break;
                        case "11": obj.setNum11(tmpNums); break;
                        case "12": obj.setNum12(tmpNums); break;
                        default:break;
                    }
                }
                obj.setTotal(sumNums);
            }
        }
        result.sort(Comparator.comparing(YearGzVo::getYM).thenComparing(YearGzVo::getRegion).thenComparing(YearGzVo::getOrgName));
        return result;
    }

    /**
     * 根据查询条件，获取月度|年度统计源数据 【五险一金】
     * @param searchTotal 查询条件
     * @param type 年度|月度
     * @return
     */
    public List<MonthVo> getSubFundMonthVoList(SearchTotal searchTotal, String type) {
        /* 2 构造查询条件 - 五险 */
        QueryWrapper<SubFund> fundQueryWrapper = new QueryWrapper<>();
        fundQueryWrapper.in("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(searchTotal.getRegion())){
            fundQueryWrapper.eq("a.REGION", searchTotal.getRegion());
        }
        if(ObjectUtil.isNotEmpty(searchTotal.getOrgId())){
            fundQueryWrapper.eq("a.ORG_ID", searchTotal.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(searchTotal.getOrgName())){
            fundQueryWrapper.like("a.ORG_NAME", searchTotal.getOrgName());
        }
        if("month".equals(type)){
            fundQueryWrapper.eq("a.Y_M", searchTotal.getYM());
        } else if ("year".equals(type)) {
            fundQueryWrapper.likeRight("a.Y_M", searchTotal.getYM());
        }
        if(ObjectUtil.isNotEmpty(searchTotal.getSearchKey())){
            fundQueryWrapper.and(q->q.like("a.REGION", searchTotal.getSearchKey())
                    .or().like("a.ORG_NAME", searchTotal.getSearchKey())
                    .or().like("a.SN", searchTotal.getSearchKey())
                    .or().like("a.MANAGER_NAME", searchTotal.getSearchKey()));
        }
        return subFundMapper.getMonthVoList(fundQueryWrapper);
    }

    /**
     * 根据查询条件，获取月度|年度统计源数据 【工资发放】
     * @param searchTotal 查询条件
     * @param type 年度|月度
     * @return
     */
    public List<MonthVo> getSubGzMonthVoList(SearchTotal searchTotal, String type,List<SubGzItemVo> gzItemList) {
        /* 1 构建查询条件 - 工资 */
        QueryWrapper<SubGz> gzQueryWrapper = new QueryWrapper<>();
        gzQueryWrapper.in("a.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue());
        if (ObjectUtil.isNotEmpty(searchTotal.getRegion())) {
            gzQueryWrapper.eq("a.REGION", searchTotal.getRegion());
        }
        if (ObjectUtil.isNotEmpty(searchTotal.getOrgId())) {
            gzQueryWrapper.eq("a.ORG_ID", searchTotal.getOrgId());
        }
        if (ObjectUtil.isNotEmpty(searchTotal.getOrgName())) {
            gzQueryWrapper.like("a.ORG_NAME", searchTotal.getOrgName());
        }
        if("month".equals(type)){
            gzQueryWrapper.eq("a.Y_M", searchTotal.getYM());
        } else if ("year".equals(type)) {
            gzQueryWrapper.likeRight("a.Y_M", searchTotal.getYM());
        }
        if (ObjectUtil.isNotEmpty(searchTotal.getSearchKey())) {
            gzQueryWrapper.and(q -> q.like("a.REGION", searchTotal.getSearchKey())
                    .or().like("a.ORG_NAME", searchTotal.getSearchKey())
                    .or().like("a.SN", searchTotal.getSearchKey())
                    .or().like("a.MANAGER_NAME", searchTotal.getSearchKey()));
        }
        //月报表 - 带出工资详情数据
        if("month".equals(type)) {
            gzItemList = subGzMapper.getSubGzItemList(gzQueryWrapper);
        }
        return subGzMapper.getMonthVoList(gzQueryWrapper);
    }

}
