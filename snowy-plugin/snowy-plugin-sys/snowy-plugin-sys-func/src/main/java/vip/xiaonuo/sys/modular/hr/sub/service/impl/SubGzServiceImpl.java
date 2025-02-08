package vip.xiaonuo.sys.modular.hr.sub.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
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
import vip.xiaonuo.common.util.CommonDownloadUtil;
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
import vip.xiaonuo.sys.modular.hr.sub.vo.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
        queryWrapper.lambda().in(SubGz::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
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
        if(CollUtil.isNotEmpty(result)) {
            // 赋予序号 1,2,3...
            AtomicInteger counter = new AtomicInteger(1);
            result.forEach(p -> p.setNo(counter.getAndIncrement()));
        }
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
                obj.setAvgNum(SubUtils.getDoubleVal(sumNums/12.0,2));
            }
        }
        result.sort(Comparator.comparing(YearNumVo::getYM).thenComparing(YearNumVo::getRegion).thenComparing(YearNumVo::getOrgName));
        if(CollUtil.isNotEmpty(result)) {
            // 赋予序号 1,2,3...
            AtomicInteger counter = new AtomicInteger(1);
            result.forEach(p -> p.setNo(counter.getAndIncrement()));
        }
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
        if(CollUtil.isNotEmpty(result)) {
            // 赋予序号 1,2,3...
            AtomicInteger counter = new AtomicInteger(1);
            result.forEach(p -> p.setNo(counter.getAndIncrement()));
        }
        return result;
    }

    @Override
    public void genExcel(SearchTotal searchTotal, HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        InputStream inputStream = null;
        try {
            // 定义没有分隔符的日期时间格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            // 将当前日期时间格式化为字符串
            String dateStr = LocalDateTime.now().format(formatter);
            // 定义导出excel文件路径
            String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + searchTotal.getExcelType() + "_" + dateStr + ".xlsx";
            // 分类处理
            if ("month".equals(searchTotal.getExcelType())) {
                ClassPathResource classPathResource = new ClassPathResource("SubMonthTemplate.xlsx");
                inputStream = classPathResource.getStream();
                List<MonthVo> excelData = monthTotal(searchTotal);
                List<MonthVo> excelTotalData = new ArrayList<>();
                excelTotalData.add(sumMonthTotal(excelData));

                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                WriteSheet sheet1 = EasyExcel.writerSheet(0).build();
                Map<String, Object> map = new HashMap<String, Object>();
                String[] split = searchTotal.getYM().split("-");
                map.put("year", split[0]);
                map.put("month",split[1]);
                excelWriter.fill(map,sheet1);
                excelWriter.fill(new FillWrapper("data1", excelData),fillConfig, sheet1);  //数据
                excelWriter.fill(new FillWrapper("total", excelTotalData),fillConfig, sheet1);  //数据
                excelWriter.finish();

            } else if ("year".equals(searchTotal.getExcelType())) {
                ClassPathResource classPathResource = new ClassPathResource("SubYearTemplate.xlsx");
                inputStream = classPathResource.getStream();
                List<YearVo> excelData = yearTotal(searchTotal);
                List<YearVo> excelTotalData = new ArrayList<>();
                excelTotalData.add(sumYearTotal(excelData));

                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                WriteSheet sheet1 = EasyExcel.writerSheet(0).build();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("year", searchTotal.getYM());
                excelWriter.fill(map, sheet1);
                excelWriter.fill(new FillWrapper("data1", excelData),fillConfig, sheet1);
                excelWriter.fill(new FillWrapper("total", excelTotalData),fillConfig, sheet1);
                excelWriter.finish();

            }else {
                throw new CommonException("不支持的Excel报表");
            }
            File tempFile = FileUtil.file(outputFileName);
            CommonDownloadUtil.download(tempFile, response);
        }catch (Exception e){
            throw new CommonException(e.getMessage());
        }finally {
            // 关闭流
            if(excelWriter != null){
                excelWriter.close();
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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
        fundQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
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
        gzQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
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


    /**
     * 年度汇总
     * @param searchTotal
     * @return
     */
    @Override
    public List<YearVo> yearTotal(SearchTotal searchTotal){
        //数据源
        List<MonthVo> gzDataList = getSubGzMonthVoList(searchTotal, "year",null);
        List<MonthVo> fundDataList = getSubFundMonthVoList(searchTotal,"year");

        //构建结果集
        List<YearVo> result = new ArrayList<>();
        if(CollUtil.isNotEmpty(gzDataList)) {
            gzDataList.forEach(data -> {
                YearVo tmp = result.stream().filter(x -> data.getRegion().equals(x.getRegion()) && data.getOrgId().equals(x.getOrgId()) && data.getOrgName().equals(x.getOrgName())).findFirst().orElse(null);
                if (ObjectUtil.isEmpty(tmp)) {
                    YearVo tmpYearNumVo = new YearVo();
                    tmpYearNumVo.setYM(searchTotal.getYM());
                    tmpYearNumVo.setRegion(data.getRegion());
                    tmpYearNumVo.setOrgId(data.getOrgId());
                    tmpYearNumVo.setOrgName(data.getOrgName());
                    result.add(tmpYearNumVo);
                }
            });
        }
        if(CollUtil.isNotEmpty(fundDataList)) {
            fundDataList.forEach(data -> {
                YearVo tmp = result.stream().filter(x -> data.getRegion().equals(x.getRegion()) && data.getOrgId().equals(x.getOrgId()) && data.getOrgName().equals(x.getOrgName())).findFirst().orElse(null);
                if (ObjectUtil.isEmpty(tmp)) {
                    YearVo tmpYearNumVo = new YearVo();
                    tmpYearNumVo.setYM(searchTotal.getYM());
                    tmpYearNumVo.setRegion(data.getRegion());
                    tmpYearNumVo.setOrgId(data.getOrgId());
                    tmpYearNumVo.setOrgName(data.getOrgName());
                    result.add(tmpYearNumVo);
                }
            });
        }
        //构造12月数据
        if(CollUtil.isNotEmpty(result)){
            List<String> monthList = CollectionUtil.newArrayList(MONTH_LIST);
            for (YearVo obj : result) {
                Integer yfGzNumSum = 0;
                Integer sfGzNumSum = 0;
                Integer sjWxNumSum = 0;
                BigDecimal yfGzAmountSum = BigDecimal.ZERO;
                BigDecimal sfGzAmountSum = BigDecimal.ZERO;
                BigDecimal sjWxYjAmountSum = BigDecimal.ZERO;
                for (String month : monthList) {
                    String tmpYM = obj.getYM() + "-" + month;
                    MonthVo tmpGzData = gzDataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName()) && tmpYM.equals(x.getYM())).findFirst().orElse(null);
                    MonthVo tmpFundData = fundDataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName()) && tmpYM.equals(x.getYM())).findFirst().orElse(null);
                    Integer tmpYfGzNum = 0;
                    Integer tmpSfGzNum = 0;
                    Integer tmpSjWxNum = 0;
                    BigDecimal tmpYfGzAmount = BigDecimal.ZERO;
                    BigDecimal tmpSfGzAmount = BigDecimal.ZERO;
                    BigDecimal tmpSjWxYjAmount = BigDecimal.ZERO;
                    if(ObjectUtil.isNotEmpty(tmpGzData)){
                        tmpYfGzNum = tmpGzData.getGzYfNum();
                        tmpSfGzNum = tmpGzData.getGzSfNum();
                        tmpYfGzAmount = tmpGzData.getYfGzTotal();
                        tmpSfGzAmount = tmpGzData.getSfGzTotal();
                    }
                    if(ObjectUtil.isNotEmpty(tmpFundData)){
                        tmpSjWxNum = tmpFundData.getSjWxNum();
                        tmpSjWxYjAmount = tmpFundData.getSjWxTotal().add(tmpFundData.getSjGjjTotal());
                    }
                    yfGzNumSum = yfGzNumSum + tmpYfGzNum;
                    sfGzNumSum = sfGzNumSum + tmpSfGzNum;
                    yfGzAmountSum = yfGzAmountSum.add(tmpYfGzAmount);
                    sfGzAmountSum = sfGzAmountSum.add(tmpSfGzAmount);
                    sjWxNumSum = sjWxNumSum + tmpSjWxNum;
                    sjWxYjAmountSum = sjWxYjAmountSum.add(tmpSjWxYjAmount);

                    switch (month){
                        case "01":
                            obj.setYfGzNum1(tmpYfGzNum);
                            obj.setSfGzNum1(tmpSfGzNum);
                            obj.setYfGzAmount1(tmpYfGzAmount);
                            obj.setSfGzAmount1(tmpSfGzAmount);
                            obj.setSjWxNum1(tmpSjWxNum);
                            obj.setSjWxYjAmount1(tmpSjWxYjAmount);
                            break;
                        case "02":
                            obj.setYfGzNum2(tmpYfGzNum);
                            obj.setSfGzNum2(tmpSfGzNum);
                            obj.setYfGzAmount2(tmpYfGzAmount);
                            obj.setSfGzAmount2(tmpSfGzAmount);
                            obj.setSjWxNum2(tmpSjWxNum);
                            obj.setSjWxYjAmount2(tmpSjWxYjAmount);
                            break;
                        case "03":
                            obj.setYfGzNum3(tmpYfGzNum);
                            obj.setSfGzNum3(tmpSfGzNum);
                            obj.setYfGzAmount3(tmpYfGzAmount);
                            obj.setSfGzAmount3(tmpSfGzAmount);
                            obj.setSjWxNum3(tmpSjWxNum);
                            obj.setSjWxYjAmount3(tmpSjWxYjAmount);
                            break;
                        case "04":
                            obj.setYfGzNum4(tmpYfGzNum);
                            obj.setSfGzNum4(tmpSfGzNum);
                            obj.setYfGzAmount4(tmpYfGzAmount);
                            obj.setSfGzAmount4(tmpSfGzAmount);
                            obj.setSjWxNum4(tmpSjWxNum);
                            obj.setSjWxYjAmount4(tmpSjWxYjAmount);
                            break;
                        case "05":
                            obj.setYfGzNum5(tmpYfGzNum);
                            obj.setSfGzNum5(tmpSfGzNum);
                            obj.setYfGzAmount5(tmpYfGzAmount);
                            obj.setSfGzAmount5(tmpSfGzAmount);
                            obj.setSjWxNum5(tmpSjWxNum);
                            obj.setSjWxYjAmount5(tmpSjWxYjAmount);
                            break;
                        case "06":
                            obj.setYfGzNum6(tmpYfGzNum);
                            obj.setSfGzNum6(tmpSfGzNum);
                            obj.setYfGzAmount6(tmpYfGzAmount);
                            obj.setSfGzAmount6(tmpSfGzAmount);
                            obj.setSjWxNum6(tmpSjWxNum);
                            obj.setSjWxYjAmount6(tmpSjWxYjAmount);
                            break;
                        case "07":
                            obj.setYfGzNum7(tmpYfGzNum);
                            obj.setSfGzNum7(tmpSfGzNum);
                            obj.setYfGzAmount7(tmpYfGzAmount);
                            obj.setSfGzAmount7(tmpSfGzAmount);
                            obj.setSjWxNum7(tmpSjWxNum);
                            obj.setSjWxYjAmount7(tmpSjWxYjAmount);
                            break;
                        case "08":
                            obj.setYfGzNum8(tmpYfGzNum);
                            obj.setSfGzNum8(tmpSfGzNum);
                            obj.setYfGzAmount8(tmpYfGzAmount);
                            obj.setSfGzAmount8(tmpSfGzAmount);
                            obj.setSjWxNum8(tmpSjWxNum);
                            obj.setSjWxYjAmount8(tmpSjWxYjAmount);
                            break;
                        case "09":
                            obj.setYfGzNum9(tmpYfGzNum);
                            obj.setSfGzNum9(tmpSfGzNum);
                            obj.setYfGzAmount9(tmpYfGzAmount);
                            obj.setSfGzAmount9(tmpSfGzAmount);
                            obj.setSjWxNum9(tmpSjWxNum);
                            obj.setSjWxYjAmount9(tmpSjWxYjAmount);
                            break;
                        case "10":
                            obj.setYfGzNum10(tmpYfGzNum);
                            obj.setSfGzNum10(tmpSfGzNum);
                            obj.setYfGzAmount10(tmpYfGzAmount);
                            obj.setSfGzAmount10(tmpSfGzAmount);
                            obj.setSjWxNum10(tmpSjWxNum);
                            obj.setSjWxYjAmount10(tmpSjWxYjAmount);
                            break;
                        case "11":
                            obj.setYfGzNum11(tmpYfGzNum);
                            obj.setSfGzNum11(tmpSfGzNum);
                            obj.setYfGzAmount11(tmpYfGzAmount);
                            obj.setSfGzAmount11(tmpSfGzAmount);
                            obj.setSjWxNum11(tmpSjWxNum);
                            obj.setSjWxYjAmount11(tmpSjWxYjAmount);
                            break;
                        case "12":
                            obj.setYfGzNum12(tmpYfGzNum);
                            obj.setSfGzNum12(tmpSfGzNum);
                            obj.setYfGzAmount12(tmpYfGzAmount);
                            obj.setSfGzAmount12(tmpSfGzAmount);
                            obj.setSjWxNum12(tmpSjWxNum);
                            obj.setSjWxYjAmount12(tmpSjWxYjAmount);
                            break;
                        default:break;
                    }
                }
                obj.setYfGzAvgNum(SubUtils.getDoubleVal(yfGzNumSum/12.0,2));
                obj.setSfGzAvgNum(SubUtils.getDoubleVal(sfGzNumSum/12.0,2));
                obj.setYfGzTotal(yfGzAmountSum);
                obj.setSfGzTotal(sfGzAmountSum);
                obj.setSjWxAvgNum(SubUtils.getDoubleVal(sjWxNumSum/12.0,2));
                obj.setSjWxYjTotal(sjWxYjAmountSum);
            }
        }

        result.sort(Comparator.comparing(YearVo::getYM).thenComparing(YearVo::getRegion).thenComparing(YearVo::getOrgName));
        if(CollUtil.isNotEmpty(result)) {
            // 赋予序号 1,2,3...
            AtomicInteger counter = new AtomicInteger(1);
            result.forEach(p -> p.setNo(counter.getAndIncrement()));
        }
        return result;
    }


    /**
     * 月度统计表 - 合计行
     * @param excelData
     * @return
     */
    public MonthVo sumMonthTotal(List<MonthVo> excelData){
        MonthVo result = new MonthVo();
        if(CollUtil.isNotEmpty(excelData)){
            excelData.forEach(item->{
                result.setGzYfNum(result.getGzYfNum()+item.getGzYfNum());
                result.setGzSfNum(result.getGzSfNum()+item.getGzSfNum());
                result.setYfGzTotal(result.getYfGzTotal().add(item.getYfGzTotal()));
                result.setInsTotal(result.getInsTotal().add(item.getInsTotal()));
                result.setGjjTotal(result.getGjjTotal().add(item.getGjjTotal()));
                result.setGzTaxTotal(result.getGzTaxTotal().add(item.getGzTaxTotal()));
                result.setSfGzTotal(result.getSfGzTotal().add(item.getSfGzTotal()));
                result.setJjYfNum(result.getJjYfNum()+item.getJjYfNum());
                result.setJjSfNum(result.getJjSfNum()+item.getJjSfNum());
                result.setYfJjTotal(result.getYfJjTotal().add(item.getYfJjTotal()));
                result.setJjTaxTotal(result.getJjTaxTotal().add(item.getJjTaxTotal()));
                result.setSfJjTotal(result.getSfJjTotal().add(item.getSfJjTotal()));
                result.setYjWxNum(result.getYjWxNum()+item.getYjWxNum());
                result.setSjWxNum(result.getSjWxNum()+item.getSjWxNum());
                result.setYjWxTotal(result.getYjWxTotal().add(item.getYjWxTotal()));
                result.setSjWxTotal(result.getSjWxTotal().add(item.getSjWxTotal()));
                result.setYjGjjNum(result.getYjGjjNum()+item.getYjGjjNum());
                result.setSjGjjNum(result.getSjGjjNum()+item.getSjGjjNum());
                result.setYjGjjTotal(result.getYjGjjTotal().add(item.getYjGjjTotal()));
                result.setSjGjjTotal(result.getSjGjjTotal().add(item.getSjGjjTotal()));
            });
        }
        return result;
    }


    /**
     * 月度统计表 - 合计行
     * @param excelData
     * @return
     */
    public YearVo sumYearTotal(List<YearVo> excelData){
        YearVo result = new YearVo();
        if(CollUtil.isNotEmpty(excelData)){
            excelData.forEach(item->{
                result.setYfGzNum1(result.getYfGzNum1()+item.getYfGzNum1());
                result.setYfGzNum2(result.getYfGzNum2()+item.getYfGzNum2());
                result.setYfGzNum3(result.getYfGzNum3()+item.getYfGzNum3());
                result.setYfGzNum4(result.getYfGzNum4()+item.getYfGzNum4());
                result.setYfGzNum5(result.getYfGzNum5()+item.getYfGzNum5());
                result.setYfGzNum6(result.getYfGzNum6()+item.getYfGzNum6());
                result.setYfGzNum7(result.getYfGzNum7()+item.getYfGzNum7());
                result.setYfGzNum8(result.getYfGzNum8()+item.getYfGzNum8());
                result.setYfGzNum9(result.getYfGzNum9()+item.getYfGzNum9());
                result.setYfGzNum10(result.getYfGzNum10()+item.getYfGzNum10());
                result.setYfGzNum11(result.getYfGzNum11()+item.getYfGzNum11());
                result.setYfGzNum12(result.getYfGzNum12()+item.getYfGzNum12());
                result.setYfGzAvgNum(result.getYfGzAvgNum()+item.getYfGzAvgNum());

                result.setSfGzNum1(result.getSfGzNum1()+item.getSfGzNum1());
                result.setSfGzNum2(result.getSfGzNum2()+item.getSfGzNum2());
                result.setSfGzNum3(result.getSfGzNum3()+item.getSfGzNum3());
                result.setSfGzNum4(result.getSfGzNum4()+item.getSfGzNum4());
                result.setSfGzNum5(result.getSfGzNum5()+item.getSfGzNum5());
                result.setSfGzNum6(result.getSfGzNum6()+item.getSfGzNum6());
                result.setSfGzNum7(result.getSfGzNum7()+item.getSfGzNum7());
                result.setSfGzNum8(result.getSfGzNum8()+item.getSfGzNum8());
                result.setSfGzNum9(result.getSfGzNum9()+item.getSfGzNum9());
                result.setSfGzNum10(result.getSfGzNum10()+item.getSfGzNum10());
                result.setSfGzNum11(result.getSfGzNum11()+item.getSfGzNum11());
                result.setSfGzNum12(result.getSfGzNum12()+item.getSfGzNum12());
                result.setSfGzAvgNum(result.getSfGzAvgNum()+item.getSfGzAvgNum());

                result.setYfGzAmount1(result.getYfGzAmount1().add(item.getYfGzAmount1()));
                result.setYfGzAmount2(result.getYfGzAmount2().add(item.getYfGzAmount2()));
                result.setYfGzAmount3(result.getYfGzAmount3().add(item.getYfGzAmount3()));
                result.setYfGzAmount4(result.getYfGzAmount4().add(item.getYfGzAmount4()));
                result.setYfGzAmount5(result.getYfGzAmount5().add(item.getYfGzAmount5()));
                result.setYfGzAmount6(result.getYfGzAmount6().add(item.getYfGzAmount6()));
                result.setYfGzAmount7(result.getYfGzAmount7().add(item.getYfGzAmount7()));
                result.setYfGzAmount8(result.getYfGzAmount8().add(item.getYfGzAmount8()));
                result.setYfGzAmount9(result.getYfGzAmount9().add(item.getYfGzAmount9()));
                result.setYfGzAmount10(result.getYfGzAmount10().add(item.getYfGzAmount10()));
                result.setYfGzAmount11(result.getYfGzAmount11().add(item.getYfGzAmount11()));
                result.setYfGzAmount12(result.getYfGzAmount12().add(item.getYfGzAmount12()));
                result.setYfGzTotal(result.getYfGzTotal().add(item.getYfGzTotal()));

                result.setSfGzAmount1(result.getSfGzAmount1().add(item.getSfGzAmount1()));
                result.setSfGzAmount2(result.getSfGzAmount2().add(item.getSfGzAmount2()));
                result.setSfGzAmount3(result.getSfGzAmount3().add(item.getSfGzAmount3()));
                result.setSfGzAmount4(result.getSfGzAmount4().add(item.getSfGzAmount4()));
                result.setSfGzAmount5(result.getSfGzAmount5().add(item.getSfGzAmount5()));
                result.setSfGzAmount6(result.getSfGzAmount6().add(item.getSfGzAmount6()));
                result.setSfGzAmount7(result.getSfGzAmount7().add(item.getSfGzAmount7()));
                result.setSfGzAmount8(result.getSfGzAmount8().add(item.getSfGzAmount8()));
                result.setSfGzAmount9(result.getSfGzAmount9().add(item.getSfGzAmount9()));
                result.setSfGzAmount10(result.getSfGzAmount10().add(item.getSfGzAmount10()));
                result.setSfGzAmount11(result.getSfGzAmount11().add(item.getSfGzAmount11()));
                result.setSfGzAmount12(result.getSfGzAmount12().add(item.getSfGzAmount12()));
                result.setSfGzTotal(result.getSfGzTotal().add(item.getSfGzTotal()));

                result.setSjWxNum1(result.getSjWxNum1()+item.getSjWxNum1());
                result.setSjWxNum2(result.getSjWxNum2()+item.getSjWxNum2());
                result.setSjWxNum3(result.getSjWxNum3()+item.getSjWxNum3());
                result.setSjWxNum4(result.getSjWxNum4()+item.getSjWxNum4());
                result.setSjWxNum5(result.getSjWxNum5()+item.getSjWxNum5());
                result.setSjWxNum6(result.getSjWxNum6()+item.getSjWxNum6());
                result.setSjWxNum7(result.getSjWxNum7()+item.getSjWxNum7());
                result.setSjWxNum8(result.getSjWxNum8()+item.getSjWxNum8());
                result.setSjWxNum9(result.getSjWxNum9()+item.getSjWxNum9());
                result.setSjWxNum10(result.getSjWxNum10()+item.getSjWxNum10());
                result.setSjWxNum11(result.getSjWxNum11()+item.getSjWxNum11());
                result.setSjWxNum12(result.getSjWxNum12()+item.getSjWxNum12());
                result.setSjWxAvgNum(result.getSjWxAvgNum()+item.getSjWxAvgNum());

                result.setSjWxYjAmount1(result.getSjWxYjAmount1().add(item.getSjWxYjAmount1()));
                result.setSjWxYjAmount2(result.getSjWxYjAmount2().add(item.getSjWxYjAmount2()));
                result.setSjWxYjAmount3(result.getSjWxYjAmount3().add(item.getSjWxYjAmount3()));
                result.setSjWxYjAmount4(result.getSjWxYjAmount4().add(item.getSjWxYjAmount4()));
                result.setSjWxYjAmount5(result.getSjWxYjAmount5().add(item.getSjWxYjAmount5()));
                result.setSjWxYjAmount6(result.getSjWxYjAmount6().add(item.getSjWxYjAmount6()));
                result.setSjWxYjAmount7(result.getSjWxYjAmount7().add(item.getSjWxYjAmount7()));
                result.setSjWxYjAmount8(result.getSjWxYjAmount8().add(item.getSjWxYjAmount8()));
                result.setSjWxYjAmount9(result.getSjWxYjAmount9().add(item.getSjWxYjAmount9()));
                result.setSjWxYjAmount10(result.getSjWxYjAmount10().add(item.getSjWxYjAmount10()));
                result.setSjWxYjAmount11(result.getSjWxYjAmount11().add(item.getSjWxYjAmount11()));
                result.setSjWxYjAmount12(result.getSjWxYjAmount12().add(item.getSjWxYjAmount12()));
                result.setSjWxYjTotal(result.getSjWxYjTotal().add(item.getSjWxYjTotal()));
            });
        }
        return result;
    }

}
