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
import vip.xiaonuo.sys.modular.hr.sub.entity.SubPersonnel;
import vip.xiaonuo.sys.modular.hr.sub.enums.SubFundItemTypeEnum;
import vip.xiaonuo.sys.modular.hr.sub.enums.SubGzItemTypeEnum;
import vip.xiaonuo.sys.modular.hr.sub.enums.SubGzTypeEnum;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubFundMapper;
import vip.xiaonuo.sys.modular.hr.sub.mapper.SubGzMapper;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzItemService;
import vip.xiaonuo.sys.modular.hr.sub.service.SubGzService;
import vip.xiaonuo.sys.modular.hr.sub.service.SubPersonnelService;
import vip.xiaonuo.sys.modular.hr.sub.util.SubUtils;
import vip.xiaonuo.sys.modular.hr.sub.vo.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Resource
    private SubPersonnelService subPersonnelService;

    /**
     * SubGz - 工资|奖金|经济补偿金  - 报表
     */
    public static final String[] GZ_REPORT_TYPE_LIST = {"1","2","3","4","10","11","12","13"};

    /**
     * SubFund - 五险一金 - 报表
     */
    public static final String[] FUND_REPORT_TYPE_LIST = {"5","6","7","8"};

    /**
     * SubGz - 特殊报表
     */
    public static final String[] SPECIAL_REPORT_TYPE_LIST = {"9"};

    /**
     * SubGz - 报表 - 求平均
     */
    public static final String[] AVG_REPORT_TYPE_LIST = {"1","2","5","7","10","11"};

    /**
     * SubGz - 报表 - 求小计
     */
    public static final String[] TOTAL_REPORT_TYPE_LIST = {"3","4","6","8","12","13"};

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
        if(ObjectUtil.isNotEmpty(subGzPageParam.getType())){
            queryWrapper.lambda().eq(SubGz::getType,subGzPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(subGzPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SubGz::getSn,subGzPageParam.getSearchKey())
                    .or().like(SubGz::getRegion,subGzPageParam.getSearchKey())
                    .or().like(SubGz::getOrgName,subGzPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(subGzPageParam.getSortField(), subGzPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subGzPageParam.getSortOrder());
            queryWrapper.orderBy(true, subGzPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subGzPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SubGz::getSn);
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
        //处理工资明细
        List<SubGzItem> subGzItemList = new ArrayList<>();
//        List<SubPersonnel> subPersonnelList = getSubPersonnelList(subGzParam.getOrgName());
        if(CollUtil.isNotEmpty(subGzParam.getGzList())){
//            subGzParam.getGzList().forEach(item->matchNature(item,subPersonnelList));
            List<SubGzItem> tmpItems = BeanUtil.copyToList(subGzParam.getGzList(), SubGzItem.class);
            tmpItems.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.GZ.getValue());
            });
            subGzItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subGzParam.getJjList())){
//            subGzParam.getJjList().forEach(item->matchNature(item,subPersonnelList));
            List<SubGzItem> tmpItems = BeanUtil.copyToList(subGzParam.getJjList(), SubGzItem.class);
            tmpItems.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.JJ.getValue());
            });
            subGzItemList.addAll(tmpItems);
        }
        if(CollUtil.isNotEmpty(subGzParam.getBcjList())){
            List<SubGzItem> tmpItems = BeanUtil.copyToList(subGzParam.getBcjList(), SubGzItem.class);
            tmpItems.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.BCJ.getValue());
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
        //处理经济补偿金
        if(CollUtil.isNotEmpty(subGzParam.getBcjList())){
            //本次传入的数据
            List<SubGzItem> gzItemList = BeanUtil.copyToList(subGzParam.getBcjList(),SubGzItem.class);
            gzItemList.forEach(x->{
                x.setSubGzId(subGz.getId());
                x.setType(SubGzItemTypeEnum.BCJ.getValue());
            });
            //获取历史数据
            List<SubGzItem> oldGzItemList = subGzItemService.list(new QueryWrapper<SubGzItem>().lambda().eq(SubGzItem::getSubGzId,subGz.getId())
                    .eq(SubGzItem::getType,SubGzItemTypeEnum.BCJ.getValue()));
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
        List<MonthVo> gzResult = getSubGzMonthVoList(searchTotal,"month");
        List<SubGzItemVo> subGzItemDataList = getSubGzItemVoList(searchTotal,"month");
        List<SubGzItemVo> gzItemList = subGzItemDataList.stream().filter(x->SubGzItemTypeEnum.GZ.getValue().equals(x.getType())).collect(Collectors.toList());
        //统计五险一金
        if(CollUtil.isNotEmpty(gzResult)){
            for (MonthVo item : gzResult){
                List<SubGzItemVo> tmpGzItemList = gzItemList.stream().filter(x -> item.getYM().equals(x.getFlwYM()) && item.getRegion().equals(x.getRegion()) && item.getOrgId().equals(x.getOrgId()) && item.getOrgName().equals(x.getFlwOrgName())).collect(Collectors.toList());
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
    public List<YearReportVo> yearReport(SearchTotal searchTotal) {
        if(ObjectUtil.isEmpty(searchTotal.getReportType())){
            throw new CommonException("报表类型必填");
        }
        //数据源
        List<MonthVo> dataList = new ArrayList<>();
        /* 不同的报表 - 数据源不一致 */
        if(CollectionUtil.newArrayList(GZ_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
            dataList = getSubGzMonthVoList(searchTotal,"year");
        } else if (CollectionUtil.newArrayList(FUND_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
            dataList = getSubFundMonthVoList(searchTotal,"year");
        }else if (CollectionUtil.newArrayList(SPECIAL_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
            dataList = getSubGzMonthVoList(searchTotal,"year");
        } else {
            throw new CommonException("不支持的报表类型");
        }
        //构建结果集
        List<YearReportVo> result = new ArrayList<>();
        if(CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(data -> {
                YearReportVo tmp = result.stream().filter(x -> data.getRegion().equals(x.getRegion()) && data.getOrgId().equals(x.getOrgId()) && data.getOrgName().equals(x.getOrgName())).findFirst().orElse(null);
                if (ObjectUtil.isEmpty(tmp)) {
                    YearReportVo tmpYearReportVo = new YearReportVo();
                    tmpYearReportVo.setYM(searchTotal.getYM());
                    tmpYearReportVo.setRegion(data.getRegion());
                    tmpYearReportVo.setOrgId(data.getOrgId());
                    tmpYearReportVo.setOrgName(data.getOrgName());
                    result.add(tmpYearReportVo);
                }
            });
        }
        //数据处理
        if(CollectionUtil.newArrayList(GZ_REPORT_TYPE_LIST).contains(searchTotal.getReportType()) ||
           CollectionUtil.newArrayList(FUND_REPORT_TYPE_LIST).contains(searchTotal.getReportType())){
            /*构造12月数据*/
            List<String> monthList = CollectionUtil.newArrayList(MONTH_LIST);
            for (YearReportVo obj : result) {
                BigDecimal sumNums = BigDecimal.ZERO;
                for (String month : monthList) {
                    String tmpYM = obj.getYM() + "-" + month;
                    MonthVo tmpData = dataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName()) && tmpYM.equals(x.getYM())).findFirst().orElse(null);
                    BigDecimal tmpNums = BigDecimal.ZERO;
                    if(ObjectUtil.isNotEmpty(tmpData)){
                        if("1".equals(searchTotal.getReportType())){
                            tmpNums = SubUtils.intToBigDecimal(tmpData.getGzYfNum());
                        } else if ("2".equals(searchTotal.getReportType())) {
                            tmpNums = SubUtils.intToBigDecimal(tmpData.getGzSfNum());
                        } else if("5".equals(searchTotal.getReportType())){
                            tmpNums = SubUtils.intToBigDecimal(tmpData.getSjWxNum());
                        } else if("7".equals(searchTotal.getReportType())){
                            tmpNums = SubUtils.intToBigDecimal(tmpData.getSjGjjNum());
                        } else if ("3".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getYfGzTotal();
                        } else if ("4".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getSfGzTotal();
                        } else if ("6".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getSjWxTotal();
                        } else if ("8".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getSjGjjTotal();
                        } else if ("10".equals(searchTotal.getReportType())) {
                            tmpNums = SubUtils.intToBigDecimal(tmpData.getBcjYfNum());
                        } else if ("11".equals(searchTotal.getReportType())) {
                            tmpNums = SubUtils.intToBigDecimal(tmpData.getBcjSfNum());
                        } else if ("12".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getYfBcjTotal();
                        } else if ("13".equals(searchTotal.getReportType())) {
                            tmpNums = tmpData.getSfBcjTotal();
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
                if(CollectionUtil.newArrayList(AVG_REPORT_TYPE_LIST).contains(searchTotal.getReportType())) {
                    obj.setAvgTotal(sumNums.divide(new BigDecimal(12),2, RoundingMode.HALF_UP));
                }else if(CollectionUtil.newArrayList(TOTAL_REPORT_TYPE_LIST).contains(searchTotal.getReportType())){
                    obj.setAvgTotal(sumNums);
                }
            }
        }else if(CollectionUtil.newArrayList(SPECIAL_REPORT_TYPE_LIST).contains(searchTotal.getReportType())){
            for (YearReportVo obj : result) {
                List<MonthVo> tmpDataList = dataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName())).collect(Collectors.toList());
                if(CollUtil.isNotEmpty(tmpDataList)){
                    obj.setNum1(SubUtils.intToBigDecimal(SubUtils.sumTotal(tmpDataList.stream().map(x->x.getJjYfNum()).collect(Collectors.toList()))));
                    obj.setNum2(SubUtils.intToBigDecimal(SubUtils.sumTotal(tmpDataList.stream().map(x->x.getJjSfNum()).collect(Collectors.toList()))));
                    obj.setNum3(SubUtils.addTotal(tmpDataList.stream().map(x->x.getYfJjTotal()).collect(Collectors.toList())));
                    obj.setNum4(SubUtils.addTotal(tmpDataList.stream().map(x->x.getJjTaxTotal()).collect(Collectors.toList())));
                    obj.setNum5(SubUtils.addTotal(tmpDataList.stream().map(x->x.getSfJjTotal()).collect(Collectors.toList())));
                }
            }
        }
        result.sort(Comparator.comparing(YearReportVo::getYM).thenComparing(YearReportVo::getRegion).thenComparing(YearReportVo::getOrgName));
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
            fundQueryWrapper.eq("a.ORG_NAME", searchTotal.getOrgName());
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
     * 根据查询条件，获取月度|年度统计源数据 【工资发放】 - 其中，月度统计携带出工资奖金明细(同一条件下)
     * @param searchTotal 查询条件
     * @param type 年度|月度
     * @return
     */
    public List<MonthVo> getSubGzMonthVoList(SearchTotal searchTotal, String type) {
        QueryWrapper<SubGz> gzQueryWrapper = getGzQuery(searchTotal,type);
        return subGzMapper.getMonthVoList(gzQueryWrapper);
    }

    /**
     * 查询工资详情数据
     * @param searchTotal
     * @param type
     * @return
     */
    public List<SubGzItemVo> getSubGzItemVoList(SearchTotal searchTotal, String type){
        QueryWrapper<SubGz> gzQueryWrapper = getGzQuery(searchTotal,type);
        return subGzMapper.getSubGzItemList(gzQueryWrapper);
    }


    /**
     * 生成查询参数
     * @param searchTotal 查询条件
     * @param type 年度|月度
     * @return
     */
    public QueryWrapper<SubGz> getGzQuery(SearchTotal searchTotal, String type){
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
            gzQueryWrapper.eq("a.ORG_NAME", searchTotal.getOrgName());
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
        return gzQueryWrapper;
    }


    /**
     * 年度汇总
     * @param searchTotal
     * @return
     */
    @Override
    public List<YearVo> yearTotal(SearchTotal searchTotal){
        //数据源
        List<MonthVo> gzDataList = getSubGzMonthVoList(searchTotal, "year");
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
                Integer sjGjjNumSum = 0;
                Integer yfBcjNumSum = 0;
                Integer sfBcjNumSum = 0;
                BigDecimal yfGzAmountSum = BigDecimal.ZERO;
                BigDecimal sfGzAmountSum = BigDecimal.ZERO;
                BigDecimal sjWxAmountSum = BigDecimal.ZERO;
                BigDecimal sjGjjAmountSum = BigDecimal.ZERO;
                BigDecimal yfBcjAmountSum = BigDecimal.ZERO;
                BigDecimal sfBcjAmountSum = BigDecimal.ZERO;
                for (String month : monthList) {
                    String tmpYM = obj.getYM() + "-" + month;
                    MonthVo tmpGzData = gzDataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName()) && tmpYM.equals(x.getYM())).findFirst().orElse(null);
                    MonthVo tmpFundData = fundDataList.stream().filter(x -> obj.getRegion().equals(x.getRegion()) && obj.getOrgId().equals(x.getOrgId()) && obj.getOrgName().equals(x.getOrgName()) && tmpYM.equals(x.getYM())).findFirst().orElse(null);
                    Integer tmpYfGzNum = 0;
                    Integer tmpSfGzNum = 0;
                    Integer tmpSjWxNum = 0;
                    Integer tmpSjGjjNum = 0;
                    Integer tmpYfBcjNum = 0;
                    Integer tmpSfBcjNum = 0;
                    BigDecimal tmpYfGzAmount = BigDecimal.ZERO;
                    BigDecimal tmpSfGzAmount = BigDecimal.ZERO;
                    BigDecimal tmpSjWxAmount = BigDecimal.ZERO;
                    BigDecimal tmpSjGjjAmount = BigDecimal.ZERO;
                    BigDecimal tmpYfBcjAmount = BigDecimal.ZERO;
                    BigDecimal tmpSfBcjAmount = BigDecimal.ZERO;
                    if(ObjectUtil.isNotEmpty(tmpGzData)){
                        tmpYfGzNum = tmpGzData.getGzYfNum();
                        tmpSfGzNum = tmpGzData.getGzSfNum();
                        tmpYfGzAmount = tmpGzData.getYfGzTotal();
                        tmpSfGzAmount = tmpGzData.getSfGzTotal();
                        tmpYfBcjNum = tmpGzData.getBcjYfNum();
                        tmpSfBcjNum = tmpGzData.getBcjSfNum();
                        tmpYfBcjAmount = tmpGzData.getYfBcjTotal();
                        tmpSfBcjAmount = tmpGzData.getSfBcjTotal();
                    }
                    if(ObjectUtil.isNotEmpty(tmpFundData)){
                        tmpSjWxNum = tmpFundData.getSjWxNum();
                        tmpSjGjjNum = tmpFundData.getSjGjjNum();
                        tmpSjWxAmount = tmpFundData.getSjWxTotal();
                        tmpSjGjjAmount = tmpFundData.getSjGjjTotal();
                    }
                    yfGzNumSum = yfGzNumSum + tmpYfGzNum;
                    sfGzNumSum = sfGzNumSum + tmpSfGzNum;
                    yfGzAmountSum = yfGzAmountSum.add(tmpYfGzAmount);
                    sfGzAmountSum = sfGzAmountSum.add(tmpSfGzAmount);
                    sjWxNumSum = sjWxNumSum + tmpSjWxNum;
                    sjGjjNumSum = sjGjjNumSum + tmpSjGjjNum;
                    sjWxAmountSum = sjWxAmountSum.add(tmpSjWxAmount);
                    sjGjjAmountSum = sjGjjAmountSum.add(tmpSjGjjAmount);
                    yfBcjNumSum = yfBcjNumSum + tmpYfBcjNum;
                    sfBcjNumSum = sfBcjNumSum + tmpSfBcjNum;
                    yfBcjAmountSum = yfBcjAmountSum.add(tmpYfBcjAmount);
                    sfBcjAmountSum = sfBcjAmountSum.add(tmpSfBcjAmount);

                    switch (month){
                        case "01":
                            obj.setYfGzNum1(tmpYfGzNum);
                            obj.setSfGzNum1(tmpSfGzNum);
                            obj.setYfGzAmount1(tmpYfGzAmount);
                            obj.setSfGzAmount1(tmpSfGzAmount);
                            obj.setSjWxNum1(tmpSjWxNum);
                            obj.setSjWxAmount1(tmpSjWxAmount);
                            obj.setSjGjjNum1(tmpSjGjjNum);
                            obj.setSjGjjAmount1(tmpSjGjjAmount);
                            obj.setYfBcjNum1(tmpYfBcjNum);
                            obj.setSfBcjNum1(tmpSfBcjNum);
                            obj.setYfBcjAmount1(tmpYfBcjAmount);
                            obj.setSfBcjAmount1(tmpSfBcjAmount);
                            break;
                        case "02":
                            obj.setYfGzNum2(tmpYfGzNum);
                            obj.setSfGzNum2(tmpSfGzNum);
                            obj.setYfGzAmount2(tmpYfGzAmount);
                            obj.setSfGzAmount2(tmpSfGzAmount);
                            obj.setSjWxNum2(tmpSjWxNum);
                            obj.setSjWxAmount2(tmpSjWxAmount);
                            obj.setSjGjjNum2(tmpSjGjjNum);
                            obj.setSjGjjAmount2(tmpSjGjjAmount);
                            obj.setYfBcjNum2(tmpYfBcjNum);
                            obj.setSfBcjNum2(tmpSfBcjNum);
                            obj.setYfBcjAmount2(tmpYfBcjAmount);
                            obj.setSfBcjAmount2(tmpSfBcjAmount);
                            break;
                        case "03":
                            obj.setYfGzNum3(tmpYfGzNum);
                            obj.setSfGzNum3(tmpSfGzNum);
                            obj.setYfGzAmount3(tmpYfGzAmount);
                            obj.setSfGzAmount3(tmpSfGzAmount);
                            obj.setSjWxNum3(tmpSjWxNum);
                            obj.setSjWxAmount3(tmpSjWxAmount);
                            obj.setSjGjjNum3(tmpSjGjjNum);
                            obj.setSjGjjAmount3(tmpSjGjjAmount);
                            obj.setYfBcjNum3(tmpYfBcjNum);
                            obj.setSfBcjNum3(tmpSfBcjNum);
                            obj.setYfBcjAmount3(tmpYfBcjAmount);
                            obj.setSfBcjAmount3(tmpSfBcjAmount);
                            break;
                        case "04":
                            obj.setYfGzNum4(tmpYfGzNum);
                            obj.setSfGzNum4(tmpSfGzNum);
                            obj.setYfGzAmount4(tmpYfGzAmount);
                            obj.setSfGzAmount4(tmpSfGzAmount);
                            obj.setSjWxNum4(tmpSjWxNum);
                            obj.setSjWxAmount4(tmpSjWxAmount);
                            obj.setSjGjjNum4(tmpSjGjjNum);
                            obj.setSjGjjAmount4(tmpSjGjjAmount);
                            obj.setYfBcjNum4(tmpYfBcjNum);
                            obj.setSfBcjNum4(tmpSfBcjNum);
                            obj.setYfBcjAmount4(tmpYfBcjAmount);
                            obj.setSfBcjAmount4(tmpSfBcjAmount);
                            break;
                        case "05":
                            obj.setYfGzNum5(tmpYfGzNum);
                            obj.setSfGzNum5(tmpSfGzNum);
                            obj.setYfGzAmount5(tmpYfGzAmount);
                            obj.setSfGzAmount5(tmpSfGzAmount);
                            obj.setSjWxNum5(tmpSjWxNum);
                            obj.setSjWxAmount5(tmpSjWxAmount);
                            obj.setSjGjjNum5(tmpSjGjjNum);
                            obj.setSjGjjAmount5(tmpSjGjjAmount);
                            obj.setYfBcjNum5(tmpYfBcjNum);
                            obj.setSfBcjNum5(tmpSfBcjNum);
                            obj.setYfBcjAmount5(tmpYfBcjAmount);
                            obj.setSfBcjAmount5(tmpSfBcjAmount);
                            break;
                        case "06":
                            obj.setYfGzNum6(tmpYfGzNum);
                            obj.setSfGzNum6(tmpSfGzNum);
                            obj.setYfGzAmount6(tmpYfGzAmount);
                            obj.setSfGzAmount6(tmpSfGzAmount);
                            obj.setSjWxNum6(tmpSjWxNum);
                            obj.setSjWxAmount6(tmpSjWxAmount);
                            obj.setSjGjjNum6(tmpSjGjjNum);
                            obj.setSjGjjAmount6(tmpSjGjjAmount);
                            obj.setYfBcjNum6(tmpYfBcjNum);
                            obj.setSfBcjNum6(tmpSfBcjNum);
                            obj.setYfBcjAmount6(tmpYfBcjAmount);
                            obj.setSfBcjAmount6(tmpSfBcjAmount);
                            break;
                        case "07":
                            obj.setYfGzNum7(tmpYfGzNum);
                            obj.setSfGzNum7(tmpSfGzNum);
                            obj.setYfGzAmount7(tmpYfGzAmount);
                            obj.setSfGzAmount7(tmpSfGzAmount);
                            obj.setSjWxNum7(tmpSjWxNum);
                            obj.setSjWxAmount7(tmpSjWxAmount);
                            obj.setSjGjjNum7(tmpSjGjjNum);
                            obj.setSjGjjAmount7(tmpSjGjjAmount);
                            obj.setYfBcjNum7(tmpYfBcjNum);
                            obj.setSfBcjNum7(tmpSfBcjNum);
                            obj.setYfBcjAmount7(tmpYfBcjAmount);
                            obj.setSfBcjAmount7(tmpSfBcjAmount);
                            break;
                        case "08":
                            obj.setYfGzNum8(tmpYfGzNum);
                            obj.setSfGzNum8(tmpSfGzNum);
                            obj.setYfGzAmount8(tmpYfGzAmount);
                            obj.setSfGzAmount8(tmpSfGzAmount);
                            obj.setSjWxNum8(tmpSjWxNum);
                            obj.setSjWxAmount8(tmpSjWxAmount);
                            obj.setSjGjjNum8(tmpSjGjjNum);
                            obj.setSjGjjAmount8(tmpSjGjjAmount);
                            obj.setYfBcjNum8(tmpYfBcjNum);
                            obj.setSfBcjNum8(tmpSfBcjNum);
                            obj.setYfBcjAmount8(tmpYfBcjAmount);
                            obj.setSfBcjAmount8(tmpSfBcjAmount);
                            break;
                        case "09":
                            obj.setYfGzNum9(tmpYfGzNum);
                            obj.setSfGzNum9(tmpSfGzNum);
                            obj.setYfGzAmount9(tmpYfGzAmount);
                            obj.setSfGzAmount9(tmpSfGzAmount);
                            obj.setSjWxNum9(tmpSjWxNum);
                            obj.setSjWxAmount9(tmpSjWxAmount);
                            obj.setSjGjjNum9(tmpSjGjjNum);
                            obj.setSjGjjAmount9(tmpSjGjjAmount);
                            obj.setYfBcjNum9(tmpYfBcjNum);
                            obj.setSfBcjNum9(tmpSfBcjNum);
                            obj.setYfBcjAmount9(tmpYfBcjAmount);
                            obj.setSfBcjAmount9(tmpSfBcjAmount);
                            break;
                        case "10":
                            obj.setYfGzNum10(tmpYfGzNum);
                            obj.setSfGzNum10(tmpSfGzNum);
                            obj.setYfGzAmount10(tmpYfGzAmount);
                            obj.setSfGzAmount10(tmpSfGzAmount);
                            obj.setSjWxNum10(tmpSjWxNum);
                            obj.setSjWxAmount10(tmpSjWxAmount);
                            obj.setSjGjjNum10(tmpSjGjjNum);
                            obj.setSjGjjAmount10(tmpSjGjjAmount);
                            obj.setYfBcjNum10(tmpYfBcjNum);
                            obj.setSfBcjNum10(tmpSfBcjNum);
                            obj.setYfBcjAmount10(tmpYfBcjAmount);
                            obj.setSfBcjAmount10(tmpSfBcjAmount);
                            break;
                        case "11":
                            obj.setYfGzNum11(tmpYfGzNum);
                            obj.setSfGzNum11(tmpSfGzNum);
                            obj.setYfGzAmount11(tmpYfGzAmount);
                            obj.setSfGzAmount11(tmpSfGzAmount);
                            obj.setSjWxNum11(tmpSjWxNum);
                            obj.setSjWxAmount11(tmpSjWxAmount);
                            obj.setSjGjjNum11(tmpSjGjjNum);
                            obj.setSjGjjAmount11(tmpSjGjjAmount);
                            obj.setYfBcjNum11(tmpYfBcjNum);
                            obj.setSfBcjNum11(tmpSfBcjNum);
                            obj.setYfBcjAmount11(tmpYfBcjAmount);
                            obj.setSfBcjAmount11(tmpSfBcjAmount);
                            break;
                        case "12":
                            obj.setYfGzNum12(tmpYfGzNum);
                            obj.setSfGzNum12(tmpSfGzNum);
                            obj.setYfGzAmount12(tmpYfGzAmount);
                            obj.setSfGzAmount12(tmpSfGzAmount);
                            obj.setSjWxNum12(tmpSjWxNum);
                            obj.setSjWxAmount12(tmpSjWxAmount);
                            obj.setSjGjjNum12(tmpSjGjjNum);
                            obj.setSjGjjAmount12(tmpSjGjjAmount);
                            obj.setYfBcjNum12(tmpYfBcjNum);
                            obj.setSfBcjNum12(tmpSfBcjNum);
                            obj.setYfBcjAmount12(tmpYfBcjAmount);
                            obj.setSfBcjAmount12(tmpSfBcjAmount);
                            break;
                        default:break;
                    }
                }
                obj.setYfGzAvgNum(SubUtils.getDoubleVal(yfGzNumSum/12.0,2));
                obj.setSfGzAvgNum(SubUtils.getDoubleVal(sfGzNumSum/12.0,2));
                obj.setYfGzTotal(yfGzAmountSum);
                obj.setSfGzTotal(sfGzAmountSum);
                obj.setSjWxAvgNum(SubUtils.getDoubleVal(sjWxNumSum/12.0,2));
                obj.setSjWxTotal(sjWxAmountSum);
                obj.setSjGjjAvgNum(SubUtils.getDoubleVal(sjGjjNumSum/12.0,2));
                obj.setSjGjjTotal(sjGjjAmountSum);
                obj.setYfBcjAvgNum(SubUtils.getDoubleVal(yfBcjNumSum/12.0,2));
                obj.setSfBcjAvgNum(SubUtils.getDoubleVal(sfBcjNumSum/12.0,2));
                obj.setYfBcjTotal(yfBcjAmountSum);
                obj.setSfBcjTotal(sfBcjAmountSum);
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

    @Override
    public SubGzParam check(SubGzParam subGzParam) {
        //1. 校验是否可以发起【奖金】
        if(SubGzTypeEnum.JJ.getValue().equals(subGzParam.getType()) || SubGzTypeEnum.GZ_JJ.getValue().equals(subGzParam.getType())){
            QueryWrapper<SubGz> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(SubGz::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
            queryWrapper.lambda().in(SubGz::getType,SubGzTypeEnum.JJ.getValue(),SubGzTypeEnum.GZ_JJ.getValue());
            String year = StrUtil.sub(subGzParam.getYM(),0,4);
            queryWrapper.lambda().likeRight(SubGz::getYM,year);
            queryWrapper.lambda().eq(SubGz::getRegion,subGzParam.getRegion());
            queryWrapper.lambda().eq(SubGz::getOrgId,subGzParam.getOrgId());
            queryWrapper.lambda().eq(SubGz::getOrgName,subGzParam.getOrgName());
            List<SubGz> tmpGzList = this.list(queryWrapper);
            if(CollUtil.isNotEmpty(tmpGzList)){
                throw new CommonException("大区：{}，分公司：{}，年度：{}，已存在奖金数据，不可发起流程！",subGzParam.getRegion(),subGzParam.getOrgName(),year);
            }
        }
        
        //2. 校验工资数据【重复|花名册匹配用工性质|已存在|金额不一致|】
//        List<SubPersonnel> subPersonnelList = getSubPersonnelList(subGzParam.getOrgName());
        Boolean isErr = Boolean.FALSE;  //错误标识字段
        if(CollUtil.isNotEmpty(subGzParam.getGzList())){
            //准备数据
            QueryWrapper<SubGz> gzQueryWrapper = new QueryWrapper<>();
            gzQueryWrapper.in("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
            gzQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
            gzQueryWrapper.eq("a.Y_M",subGzParam.getYM());
            gzQueryWrapper.eq("a.REGION",subGzParam.getRegion());
            gzQueryWrapper.eq("a.ORG_ID",subGzParam.getOrgId());
            gzQueryWrapper.eq("a.ORG_NAME",subGzParam.getOrgName());
            gzQueryWrapper.eq("b.TYPE",SubGzItemTypeEnum.GZ.getValue());
            List<SubGzItemVo> tmpGzItemList = subGzMapper.getSubGzItemList(gzQueryWrapper);

            QueryWrapper<SubFund> fundQueryWrapper = new QueryWrapper<>();
            fundQueryWrapper.in("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
            fundQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
            fundQueryWrapper.eq("a.Y_M",subGzParam.getYM());
            fundQueryWrapper.eq("a.REGION",subGzParam.getRegion());
            fundQueryWrapper.eq("a.ORG_ID",subGzParam.getOrgId());
            fundQueryWrapper.eq("a.ORG_NAME",subGzParam.getOrgName());
            List<SubFundItemVo> tmpFundItemList = subFundMapper.getSubFundItemList(fundQueryWrapper);

            //校验
            List<SubGzItemEditParam> tmpData = subGzParam.getGzList();
            for (SubGzItemEditParam item : subGzParam.getGzList()) {
                String errMsg = "";
                List<SubGzItemEditParam> tmpCFFilter = tmpData.stream().filter(x -> item.getIdCardNumber().equals(x.getIdCardNumber()) && item.getUserName().equals(x.getUserName())).collect(Collectors.toList());
                if(tmpCFFilter.size() > 1){
                    errMsg = "人员信息重复，不可发起！";
                }
//                if(ObjectUtil.isEmpty(errMsg) && ObjectUtil.isEmpty(item.getNature())){
//                    matchNature(item,subPersonnelList);
//                    if(ObjectUtil.isEmpty(item.getNature())){
//                        errMsg = "人员未匹配到用工性质，请补充！";
//                    }
//                }
                if(ObjectUtil.isEmpty(errMsg) && CollUtil.isNotEmpty(tmpGzItemList)){
                    List<SubGzItemVo> tmpCZFilter = tmpGzItemList.stream().filter(x -> item.getIdCardNumber().equals(x.getIdCardNumber()) && item.getUserName().equals(x.getUserName())).collect(Collectors.toList());
                    if(tmpCZFilter.size() > 0){
                        errMsg = "已有人员在审或审核通过数据，不可重复发起！";
                    }
                }
                if(ObjectUtil.isEmpty(errMsg) && CollUtil.isNotEmpty(tmpFundItemList)){
                    SubFundItemVo tmpFundItemIns = tmpFundItemList.stream().filter(x -> SubFundItemTypeEnum.INS.getValue().equals(x.getType()) && item.getIdCardNumber().equals(x.getIdCardNumber()) && item.getUserName().equals(x.getUserName())).findFirst().orElse(null);
                    SubFundItemVo tmpFundItemGjj = tmpFundItemList.stream().filter(x -> SubFundItemTypeEnum.GJJ.getValue().equals(x.getType()) && item.getIdCardNumber().equals(x.getIdCardNumber()) && item.getUserName().equals(x.getUserName())).findFirst().orElse(null);
                    boolean insFlag = (ObjectUtil.isNotEmpty(tmpFundItemIns) && item.getIns().compareTo(tmpFundItemIns.getSjPer()) != 0) ? true : false;  //true 代表不一致
                    boolean gjjFlag = (ObjectUtil.isNotEmpty(tmpFundItemGjj) && item.getGjj().compareTo(tmpFundItemGjj.getSjPer()) != 0) ? true : false;
                    if(insFlag && gjjFlag) errMsg = "五险与公积金（个人部分）不一致！";
                    else if (insFlag) errMsg = "五险（个人部分）不一致！";
                    else if (gjjFlag) errMsg = "公积金（个人部分）不一致！";
                }
                item.setErrMsg(errMsg);
                //错误标识字段
                if(ObjectUtil.isNotEmpty(errMsg)){
                    isErr = true;
                }
            }
        }
        
        //3. 校验奖金数据【重复|匹配用工性质】
        if(CollUtil.isNotEmpty(subGzParam.getJjList())){
            List<SubGzItemEditParam> tmpData = subGzParam.getJjList();
            for (SubGzItemEditParam item : subGzParam.getJjList()) {
                String errMsg = "";
                List<SubGzItemEditParam> tmpCFFilter = tmpData.stream().filter(x -> item.getIdCardNumber().equals(x.getIdCardNumber()) && item.getUserName().equals(x.getUserName())).collect(Collectors.toList());
                if (tmpCFFilter.size() > 1) {
                    errMsg = "人员信息重复，不可发起！";
                }
//                if(ObjectUtil.isEmpty(errMsg) && ObjectUtil.isEmpty(item.getNature())){
//                    matchNature(item,subPersonnelList);
//                    if(ObjectUtil.isEmpty(item.getNature())){
//                        errMsg = "人员未匹配到用工性质，请补充！";
//                    }
//                }
                item.setErrMsg(errMsg);
                //错误标识字段
                if(ObjectUtil.isNotEmpty(errMsg)){
                    isErr = true;
                }
            }
        }
        subGzParam.setIsErr(isErr);
        return subGzParam;
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

                result.setSjWxAmount1(result.getSjWxAmount1().add(item.getSjWxAmount1()));
                result.setSjWxAmount2(result.getSjWxAmount2().add(item.getSjWxAmount2()));
                result.setSjWxAmount3(result.getSjWxAmount3().add(item.getSjWxAmount3()));
                result.setSjWxAmount4(result.getSjWxAmount4().add(item.getSjWxAmount4()));
                result.setSjWxAmount5(result.getSjWxAmount5().add(item.getSjWxAmount5()));
                result.setSjWxAmount6(result.getSjWxAmount6().add(item.getSjWxAmount6()));
                result.setSjWxAmount7(result.getSjWxAmount7().add(item.getSjWxAmount7()));
                result.setSjWxAmount8(result.getSjWxAmount8().add(item.getSjWxAmount8()));
                result.setSjWxAmount9(result.getSjWxAmount9().add(item.getSjWxAmount9()));
                result.setSjWxAmount10(result.getSjWxAmount10().add(item.getSjWxAmount10()));
                result.setSjWxAmount11(result.getSjWxAmount11().add(item.getSjWxAmount11()));
                result.setSjWxAmount12(result.getSjWxAmount12().add(item.getSjWxAmount12()));
                result.setSjWxTotal(result.getSjWxTotal().add(item.getSjWxTotal()));


                result.setSjGjjNum1(result.getSjGjjNum1()+item.getSjGjjNum1());
                result.setSjGjjNum2(result.getSjGjjNum2()+item.getSjGjjNum2());
                result.setSjGjjNum3(result.getSjGjjNum3()+item.getSjGjjNum3());
                result.setSjGjjNum4(result.getSjGjjNum4()+item.getSjGjjNum4());
                result.setSjGjjNum5(result.getSjGjjNum5()+item.getSjGjjNum5());
                result.setSjGjjNum6(result.getSjGjjNum6()+item.getSjGjjNum6());
                result.setSjGjjNum7(result.getSjGjjNum7()+item.getSjGjjNum7());
                result.setSjGjjNum8(result.getSjGjjNum8()+item.getSjGjjNum8());
                result.setSjGjjNum9(result.getSjGjjNum9()+item.getSjGjjNum9());
                result.setSjGjjNum10(result.getSjGjjNum10()+item.getSjGjjNum10());
                result.setSjGjjNum11(result.getSjGjjNum11()+item.getSjGjjNum11());
                result.setSjGjjNum12(result.getSjGjjNum12()+item.getSjGjjNum12());
                result.setSjGjjAvgNum(result.getSjGjjAvgNum()+item.getSjGjjAvgNum());

                result.setSjGjjAmount1(result.getSjGjjAmount1().add(item.getSjGjjAmount1()));
                result.setSjGjjAmount2(result.getSjGjjAmount2().add(item.getSjGjjAmount2()));
                result.setSjGjjAmount3(result.getSjGjjAmount3().add(item.getSjGjjAmount3()));
                result.setSjGjjAmount4(result.getSjGjjAmount4().add(item.getSjGjjAmount4()));
                result.setSjGjjAmount5(result.getSjGjjAmount5().add(item.getSjGjjAmount5()));
                result.setSjGjjAmount6(result.getSjGjjAmount6().add(item.getSjGjjAmount6()));
                result.setSjGjjAmount7(result.getSjGjjAmount7().add(item.getSjGjjAmount7()));
                result.setSjGjjAmount8(result.getSjGjjAmount8().add(item.getSjGjjAmount8()));
                result.setSjGjjAmount9(result.getSjGjjAmount9().add(item.getSjGjjAmount9()));
                result.setSjGjjAmount10(result.getSjGjjAmount10().add(item.getSjGjjAmount10()));
                result.setSjGjjAmount11(result.getSjGjjAmount11().add(item.getSjGjjAmount11()));
                result.setSjGjjAmount12(result.getSjGjjAmount12().add(item.getSjGjjAmount12()));
                result.setSjGjjTotal(result.getSjGjjTotal().add(item.getSjGjjTotal()));


                result.setYfBcjNum1(result.getYfBcjNum1()+item.getYfBcjNum1());
                result.setYfBcjNum2(result.getYfBcjNum2()+item.getYfBcjNum2());
                result.setYfBcjNum3(result.getYfBcjNum3()+item.getYfBcjNum3());
                result.setYfBcjNum4(result.getYfBcjNum4()+item.getYfBcjNum4());
                result.setYfBcjNum5(result.getYfBcjNum5()+item.getYfBcjNum5());
                result.setYfBcjNum6(result.getYfBcjNum6()+item.getYfBcjNum6());
                result.setYfBcjNum7(result.getYfBcjNum7()+item.getYfBcjNum7());
                result.setYfBcjNum8(result.getYfBcjNum8()+item.getYfBcjNum8());
                result.setYfBcjNum9(result.getYfBcjNum9()+item.getYfBcjNum9());
                result.setYfBcjNum10(result.getYfBcjNum10()+item.getYfBcjNum10());
                result.setYfBcjNum11(result.getYfBcjNum11()+item.getYfBcjNum11());
                result.setYfBcjNum12(result.getYfBcjNum12()+item.getYfBcjNum12());
                result.setYfBcjAvgNum(result.getYfBcjAvgNum()+item.getYfBcjAvgNum());

                result.setSfBcjNum1(result.getSfBcjNum1()+item.getSfBcjNum1());
                result.setSfBcjNum2(result.getSfBcjNum2()+item.getSfBcjNum2());
                result.setSfBcjNum3(result.getSfBcjNum3()+item.getSfBcjNum3());
                result.setSfBcjNum4(result.getSfBcjNum4()+item.getSfBcjNum4());
                result.setSfBcjNum5(result.getSfBcjNum5()+item.getSfBcjNum5());
                result.setSfBcjNum6(result.getSfBcjNum6()+item.getSfBcjNum6());
                result.setSfBcjNum7(result.getSfBcjNum7()+item.getSfBcjNum7());
                result.setSfBcjNum8(result.getSfBcjNum8()+item.getSfBcjNum8());
                result.setSfBcjNum9(result.getSfBcjNum9()+item.getSfBcjNum9());
                result.setSfBcjNum10(result.getSfBcjNum10()+item.getSfBcjNum10());
                result.setSfBcjNum11(result.getSfBcjNum11()+item.getSfBcjNum11());
                result.setSfBcjNum12(result.getSfBcjNum12()+item.getSfBcjNum12());
                result.setSfBcjAvgNum(result.getSfBcjAvgNum()+item.getSfBcjAvgNum());

                result.setYfBcjAmount1(result.getYfBcjAmount1().add(item.getYfBcjAmount1()));
                result.setYfBcjAmount2(result.getYfBcjAmount2().add(item.getYfBcjAmount2()));
                result.setYfBcjAmount3(result.getYfBcjAmount3().add(item.getYfBcjAmount3()));
                result.setYfBcjAmount4(result.getYfBcjAmount4().add(item.getYfBcjAmount4()));
                result.setYfBcjAmount5(result.getYfBcjAmount5().add(item.getYfBcjAmount5()));
                result.setYfBcjAmount6(result.getYfBcjAmount6().add(item.getYfBcjAmount6()));
                result.setYfBcjAmount7(result.getYfBcjAmount7().add(item.getYfBcjAmount7()));
                result.setYfBcjAmount8(result.getYfBcjAmount8().add(item.getYfBcjAmount8()));
                result.setYfBcjAmount9(result.getYfBcjAmount9().add(item.getYfBcjAmount9()));
                result.setYfBcjAmount10(result.getYfBcjAmount10().add(item.getYfBcjAmount10()));
                result.setYfBcjAmount11(result.getYfBcjAmount11().add(item.getYfBcjAmount11()));
                result.setYfBcjAmount12(result.getYfBcjAmount12().add(item.getYfBcjAmount12()));
                result.setYfBcjTotal(result.getYfBcjTotal().add(item.getYfBcjTotal()));

                result.setSfBcjAmount1(result.getSfBcjAmount1().add(item.getSfBcjAmount1()));
                result.setSfBcjAmount2(result.getSfBcjAmount2().add(item.getSfBcjAmount2()));
                result.setSfBcjAmount3(result.getSfBcjAmount3().add(item.getSfBcjAmount3()));
                result.setSfBcjAmount4(result.getSfBcjAmount4().add(item.getSfBcjAmount4()));
                result.setSfBcjAmount5(result.getSfBcjAmount5().add(item.getSfBcjAmount5()));
                result.setSfBcjAmount6(result.getSfBcjAmount6().add(item.getSfBcjAmount6()));
                result.setSfBcjAmount7(result.getSfBcjAmount7().add(item.getSfBcjAmount7()));
                result.setSfBcjAmount8(result.getSfBcjAmount8().add(item.getSfBcjAmount8()));
                result.setSfBcjAmount9(result.getSfBcjAmount9().add(item.getSfBcjAmount9()));
                result.setSfBcjAmount10(result.getSfBcjAmount10().add(item.getSfBcjAmount10()));
                result.setSfBcjAmount11(result.getSfBcjAmount11().add(item.getSfBcjAmount11()));
                result.setSfBcjAmount12(result.getSfBcjAmount12().add(item.getSfBcjAmount12()));
                result.setSfBcjTotal(result.getSfBcjTotal().add(item.getSfBcjTotal()));
            });
        }
        return result;
    }

    /**
     * 分公司花名册匹配员工用工性质
     * @param item
     * @param subPersonnelList
     */
    public void matchNature(SubGzItemEditParam item, List<SubPersonnel> subPersonnelList){
        //导入用工性质为空，匹配分公司花名册
        if(CollUtil.isNotEmpty(subPersonnelList) && ObjectUtil.isEmpty(item.getNature())){
            SubPersonnel subPersonnel = subPersonnelList.stream().filter(x -> item.getIdCardNumber().equals(x.getIdNumber())
                    && item.getUserName().equals(x.getName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(subPersonnel)){
                item.setNature(subPersonnel.getEmployNature());
            }
        }
    }

    /**
     * 获取分公司花名册
     * @return
     */
    public List<SubPersonnel> getSubPersonnelList(String orgName){
        QueryWrapper<SubPersonnel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(SubPersonnel::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        queryWrapper.lambda().eq(SubPersonnel::getHrStatus,"0");
        queryWrapper.lambda().like(SubPersonnel::getDept,orgName);
        return subPersonnelService.list(queryWrapper);
    }

}
