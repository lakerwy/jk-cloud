package vip.xiaonuo.biz.modular.prj.exp.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
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
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormBack;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormFee;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormTravel;
import vip.xiaonuo.biz.modular.prj.exp.enums.BizFormCashBackStatusEnum;
import vip.xiaonuo.biz.modular.prj.exp.enums.BizFormCashTypeEnum;
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormBackService;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormFeeService;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelService;
import vip.xiaonuo.biz.modular.prj.exp.vo.*;
import vip.xiaonuo.biz.modular.prj.expense.utils.MergeCellHandler;
import vip.xiaonuo.biz.modular.prj.expense.vo.MergeCell;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormCash;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormCashMapper;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormCashService;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 费用报销（二期）- 借支单Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 **/
@Service
@DS("slave_1")
public class BizFormCashServiceImpl extends ServiceImpl<BizFormCashMapper, BizFormCash> implements BizFormCashService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizFormFeeService bizFormFeeService;

    @Resource
    private BizFormTravelService bizFormTravelService;

    @Resource
    private BizFormBackService bizFormBackService;

    @Override
    public Page<BizFormCashVo> page(BizFormCashPageParam bizFormCashPageParam) {
        //审批通过的 【归还状态筛选 - 暂未处理】
        QueryWrapper<BizFormCash> queryWrapper = getBizFormCashQueryWrapper(bizFormCashPageParam);
        List<BizFormCash> queryList = this.list(queryWrapper);
        //处理数据，并筛选归还状态（可控）
        List<BizFormCashVo> dataList = getBizFormCashVoList(queryList,bizFormCashPageParam.getBackStatus());
        //页面参数-分页
        long current = ObjectUtil.isNotEmpty(bizFormCashPageParam.getCurrent()) ? bizFormCashPageParam.getCurrent() : 1 ;
        long size = ObjectUtil.isNotEmpty(bizFormCashPageParam.getSize()) ? bizFormCashPageParam.getSize() : 10 ;
        int fromIndex = Convert.toInt((current - 1) * size);
        int toIndex = Math.min(fromIndex + Convert.toInt(size), Convert.toInt(dataList.size()));
        List<BizFormCashVo> records = dataList.subList(fromIndex,toIndex);
        Page<BizFormCashVo> result = new Page<>();
        result.setRecords(records);
        result.setTotal(dataList.size());
        result.setSize(size);
        result.setCurrent(current);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormCashAddParam bizFormCashAddParam) {
        BizFormCash bizFormCash = BeanUtil.toBean(bizFormCashAddParam, BizFormCash.class);
        //走审批流程
        String processInstanceId = bizFormCashAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizFormCash.setId(id);
            bizFormCash.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizFormCash.setModelId(modelId);
            bizFormCash.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_FORM_CASH.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizFormCash.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizFormCash);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormCashEditParam bizFormCashEditParam) {
        String id = bizFormCashEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizFormCashEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizFormCashEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizFormCashEditParam.setId(id);
            }
        }
        BizFormCash bizFormCash = this.queryEntity(bizFormCashEditParam.getId());
        BeanUtil.copyProperties(bizFormCashEditParam, bizFormCash);
        this.updateById(bizFormCash);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormCashIdParam> bizFormCashIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFormCashIdParamList, BizFormCashIdParam::getId));
    }

    @Override
    public BizFormCash detail(BizFormCashIdParam bizFormCashIdParam) {
        return this.queryEntity(bizFormCashIdParam.getId());
    }

    @Override
    public BizFormCash queryEntity(String id) {
        BizFormCash bizFormCash = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormCash)) {
            throw new CommonException("费用报销（二期）- 借支单不存在，id值为：{}", id);
        }
        return bizFormCash;
    }

    @Override
    public BizFormCashTotal pageTotal(BizFormCashPageParam bizFormCashPageParam) {
        //审批通过的 【归还状态筛选 - 暂未处理】
        QueryWrapper<BizFormCash> queryWrapper = getBizFormCashQueryWrapper(bizFormCashPageParam);
        List<BizFormCash> queryList = this.list(queryWrapper);
        return getBizFormCashTotal(queryList,bizFormCashPageParam.getBackStatus());
    }

    @Override
    public BizFormCashTotal getBizFormCashTotal(List<BizFormCash> bizFormCashList,String backStatus) {
        BizFormCashTotal result = new BizFormCashTotal();

        //处理数据，并筛选归还状态（可控）
        List<BizFormCashVo> dataList = getBizFormCashVoList(bizFormCashList, backStatus);

        BigDecimal totalAdvanceAmount = dataList.stream().map(BizFormCashVo::getAdvanceAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalBackAmount = dataList.stream().map(BizFormCashVo::getBackAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalNoBackAmount = dataList.stream().map(BizFormCashVo::getNoBackAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalCashBalance = dataList.stream().map(BizFormCashVo::getCashBalance).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

        result.setAdvanceAmountTotal(totalAdvanceAmount);
        result.setCashBalanceTotal(totalCashBalance);
        result.setBackAmountTotal(totalBackAmount);
        result.setNoBackAmountTotal(totalNoBackAmount);
        return result;
    }

    @Override
    public LjNoBackVo ljNoBack(LjNoBackParam ljNoBackParam) {
        LjNoBackVo result = new LjNoBackVo();
        QueryWrapper<BizFormCash> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizFormCash::getAdvanceId,ljNoBackParam.getAdvanceId());
        queryWrapper.lambda().in(BizFormCash::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue());
        List<BizFormCash> queryList = this.list(queryWrapper);
        BizFormCashTotal bizFormCashTotal = getBizFormCashTotal(queryList, null);
        result.setAdvanceId(ljNoBackParam.getAdvanceId());
        result.setAdvanceName(ljNoBackParam.getAdvanceName());
        result.setLjWhAmount(bizFormCashTotal.getNoBackAmountTotal());
        return result;
    }

    @Override
    public void check(CashCheckParam checkParam) {
        //备用金校验(金额限制前端校验)
        if(BizFormCashTypeEnum.BYJ.getValue().equals(checkParam.getType())){
            List<BizFormCash> oldFormCashList = this.list(new LambdaQueryWrapper<BizFormCash>()
                    .eq(BizFormCash::getAdvanceId,checkParam.getAdvanceId())
                    .eq(BizFormCash::getType,BizFormCashTypeEnum.BYJ.getValue())
                    .in(BizFormCash::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue()));
            if(CollUtil.isNotEmpty(oldFormCashList)){
                throw new CommonException("您还有审批中的备用金类型借支单，无法再发起备用金类型借支单，请确认后再提交！");
            }
            List<BizFormCash> tmpFormCashList = this.list(new LambdaQueryWrapper<BizFormCash>()
                    .eq(BizFormCash::getAdvanceId,checkParam.getAdvanceId())
                    .eq(BizFormCash::getType,BizFormCashTypeEnum.BYJ.getValue())
                    .in(BizFormCash::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue()));
            BizFormCashTotal bizFormCashTotal = getBizFormCashTotal(tmpFormCashList, null);
            if(bizFormCashTotal.getCashBalanceTotal().compareTo(BigDecimal.ZERO) != 0){
                throw new CommonException("您还有未冲借支完的备用金类型的借支单，无法提交，请确认后再提交！");
            }
        }
    }

    @Override
    public List<BizFormCashVo> select(CashSelectParam selectParam) {
        QueryWrapper<BizFormCash> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizFormCash::getAdvanceId,selectParam.getAdvanceId());
        queryWrapper.lambda().in(BizFormCash::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(selectParam.getSn())){
            queryWrapper.lambda().like(BizFormCash::getSn,selectParam.getSn());
        }
        List<BizFormCash> queryList = this.list(queryWrapper);
        List<BizFormCashVo> dataList = getBizFormCashVoList(queryList,null);
        List<BizFormCashVo> result = dataList.stream().filter(x->BigDecimal.ZERO.compareTo(x.getCashBalance()) !=0 ).collect(Collectors.toList());
        return result;
    }

    @Override
    public void genExcel(ExcelParam excelParam, HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        InputStream inputStream = null;
        try {
            // 定义没有分隔符的日期时间格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            // 将当前日期时间格式化为字符串
            String dateStr = LocalDateTime.now().format(formatter);
            // 定义导出excel文件路径
            String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + excelParam.getExcelType() + "_" + dateStr + ".xlsx";
            // 分类处理
            if ("CASH".equals(excelParam.getExcelType())) {
                ClassPathResource classPathResource = new ClassPathResource("Cash_Template.xlsx");
                inputStream = classPathResource.getStream();
                CashExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), CashExcelData.class);

                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                List<MergeCell> mergeList = new ArrayList<>(); //合并单元格设置
                WriteSheet sheet1 = EasyExcel.writerSheet(0).registerWriteHandler(new MergeCellHandler(mergeList)).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.fill(new FillWrapper("commentList", excelData.getCommentList()),fillConfig, sheet1);
                excelWriter.finish();

            } else if ("FEE".equals(excelParam.getExcelType())) {
                ClassPathResource classPathResource = new ClassPathResource("Fee_Template.xlsx");
                inputStream = classPathResource.getStream();
                FeeExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), FeeExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData)){
                    if(CollUtil.isNotEmpty(excelData.getItemList())){
                        excelData.getItemList().forEach(item->{
                            if(ObjectUtil.isNotEmpty(item.getSecondExpItem())) item.setExpItem(item.getExpItem()+"/"+item.getSecondExpItem());
                        });
                    }
                    if(CollUtil.isNotEmpty(excelData.getCostList())){
                        excelData.getCostList().forEach(item->{
                            if(ObjectUtil.isNotEmpty(item.getSecondExpItem())) item.setExpItem(item.getExpItem()+"/"+item.getSecondExpItem());
                        });
                    }
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                List<MergeCell> mergeList = new ArrayList<>(); //合并单元格设置
                WriteSheet sheet1 = EasyExcel.writerSheet(0).registerWriteHandler(new MergeCellHandler(mergeList)).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.fill(new FillWrapper("item", excelData.getItemList()),fillConfig, sheet1);
                excelWriter.fill(new FillWrapper("commentList", excelData.getCommentList()),fillConfig, sheet1);

                WriteSheet sheet2 = EasyExcel.writerSheet(1).registerWriteHandler(new MergeCellHandler(mergeList)).build();
                excelWriter.fill(excelData, sheet2);
                excelWriter.fill(new FillWrapper("cost", excelData.getCostList()),fillConfig, sheet2);
                excelWriter.finish();

            } else if ("TRAVEL".equals(excelParam.getExcelType())) {
                ClassPathResource classPathResource = new ClassPathResource("Travel_Template.xlsx");
                inputStream = classPathResource.getStream();
                TravelExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), TravelExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData) && CollUtil.isNotEmpty(excelData.getCostList())){
                    excelData.getCostList().forEach(item->{
                            if(ObjectUtil.isNotEmpty(item.getSecondExpItem())) item.setExpItem(item.getExpItem()+"/"+item.getSecondExpItem());
                    });
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                List<MergeCell> mergeList = new ArrayList<>(); //合并单元格设置
                WriteSheet sheet1 = EasyExcel.writerSheet(0).registerWriteHandler(new MergeCellHandler(mergeList)).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.fill(new FillWrapper("item", excelData.getItemList()),fillConfig, sheet1);
                excelWriter.fill(new FillWrapper("commentList", excelData.getCommentList()),fillConfig, sheet1);

                WriteSheet sheet2 = EasyExcel.writerSheet(1).registerWriteHandler(new MergeCellHandler(mergeList)).build();
                excelWriter.fill(excelData, sheet2);
                excelWriter.fill(new FillWrapper("cost", excelData.getCostList()),fillConfig, sheet2);
                excelWriter.finish();

            } else {
                throw new CommonException("不支持的类型");
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
     * 审批通过的借支单
     * 参数 查询 构造
     * @param bizFormCashPageParam
     * @return
     */
    private static QueryWrapper<BizFormCash> getBizFormCashQueryWrapper(BizFormCashPageParam bizFormCashPageParam) {
        QueryWrapper<BizFormCash> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizFormCash::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getCompany())) {
            queryWrapper.lambda().eq(BizFormCash::getCompany, bizFormCashPageParam.getCompany());
        }
        if (ObjectUtil.isNotEmpty(bizFormCashPageParam.getOrgId())){
            queryWrapper.lambda().eq(BizFormCash::getOrgId, bizFormCashPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getOrgName())) {
            queryWrapper.lambda().like(BizFormCash::getOrgName, bizFormCashPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getStartAdvanceDate()) && ObjectUtil.isNotEmpty(bizFormCashPageParam.getEndAdvanceDate())) {
            queryWrapper.lambda().between(BizFormCash::getAdvanceDate, bizFormCashPageParam.getStartAdvanceDate(), bizFormCashPageParam.getEndAdvanceDate());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getAdvanceId())) {
            queryWrapper.lambda().eq(BizFormCash::getAdvanceId, bizFormCashPageParam.getAdvanceId());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getAdvanceName())) {
            queryWrapper.lambda().like(BizFormCash::getAdvanceName, bizFormCashPageParam.getAdvanceName());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getType())) {
            queryWrapper.lambda().eq(BizFormCash::getType, bizFormCashPageParam.getType());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getStartBackDate()) && ObjectUtil.isNotEmpty(bizFormCashPageParam.getEndBackDate())) {
            queryWrapper.lambda().between(BizFormCash::getBackDate, bizFormCashPageParam.getStartBackDate(), bizFormCashPageParam.getEndBackDate());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getBankUserName())) {
            queryWrapper.lambda().like(BizFormCash::getBankUserName, bizFormCashPageParam.getBankUserName());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getBankName())) {
            queryWrapper.lambda().like(BizFormCash::getBankName, bizFormCashPageParam.getBankName());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getBankNo())) {
            queryWrapper.lambda().like(BizFormCash::getBankNo, bizFormCashPageParam.getBankNo());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getPayType())) {
            queryWrapper.lambda().eq(BizFormCash::getPayType, bizFormCashPageParam.getPayType());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getStartPayDate()) && ObjectUtil.isNotEmpty(bizFormCashPageParam.getEndPayDate())) {
            queryWrapper.lambda().between(BizFormCash::getPayDate, bizFormCashPageParam.getStartPayDate(), bizFormCashPageParam.getEndPayDate());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getSn())) {
            queryWrapper.lambda().like(BizFormCash::getSn, bizFormCashPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizFormCash::getStatus, bizFormCashPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizFormCashPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizFormCash::getSn, bizFormCashPageParam.getSearchKey())
                    .or().like(BizFormCash::getCompany, bizFormCashPageParam.getSearchKey())
                    .or().like(BizFormCash::getOrgName, bizFormCashPageParam.getSearchKey())
                    .or().like(BizFormCash::getAdvanceName, bizFormCashPageParam.getSearchKey())
                    .or().like(BizFormCash::getBankUserName, bizFormCashPageParam.getSearchKey()));
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizFormCash::getOrgId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizFormCash::getAdvanceId, StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(bizFormCashPageParam.getSortField(), bizFormCashPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormCashPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormCashPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormCashPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizFormCash::getAdvanceDate).orderByDesc(BizFormCash::getSn);
        }
        return queryWrapper;
    }


    /**
     * 补充 参数 信息
     * @param dataList
     * @return
     */

    private List<BizFormCashVo> getBizFormCashVoList(List<BizFormCash> dataList,String backStatus) {
        List<BizFormCashVo> result = BeanUtil.copyToList(dataList, BizFormCashVo.class);
        if(CollUtil.isNotEmpty(result)){
           result.forEach(item->{
                //审批通过+审批中
                List<BizFormFee> tmpFeeList = bizFormFeeService.list(new LambdaQueryWrapper<BizFormFee>().eq(BizFormFee::getCashId,item.getId()).in(BizFormFee::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue()));
                List<BizFormTravel> tmpTravelList = bizFormTravelService.list(new LambdaQueryWrapper<BizFormTravel>().eq(BizFormTravel::getCashId,item.getId()).in(BizFormTravel::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue()));
                List<BizFormBack> tmpBackList = bizFormBackService.list(new LambdaQueryWrapper<BizFormBack>().eq(BizFormBack::getCashId,item.getId()).in(BizFormBack::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue()));

                BigDecimal totalFee = tmpFeeList.stream().map(BizFormFee::getOffsetAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal totalTravel = tmpTravelList.stream().map(BizFormTravel::getOffsetAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal totalBack = tmpBackList.stream().map(BizFormBack::getBackAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                //审批通过
                BigDecimal totalPassFee = tmpFeeList.stream().filter(obj->FlwStatusEnum.COMPLETED_STATUS.getValue().equals(obj.getStatus())).map(BizFormFee::getOffsetAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal totalPassTravel = tmpTravelList.stream().filter(obj->FlwStatusEnum.COMPLETED_STATUS.getValue().equals(obj.getStatus())).map(BizFormTravel::getOffsetAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal totalPassBack = tmpBackList.stream().filter(obj->FlwStatusEnum.COMPLETED_STATUS.getValue().equals(obj.getStatus())).map(BizFormBack::getBackAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                item.setBackAmount(totalPassFee.add(totalPassTravel).add(totalPassBack));
                item.setCashBalance(item.getAdvanceAmount().subtract(totalFee).subtract(totalTravel).subtract(totalBack));
                item.setNoBackAmount(item.getAdvanceAmount().subtract(item.getBackAmount()));

                if(item.getNoBackAmount().compareTo(BigDecimal.ZERO) == 0 ){
                    item.setBackStatus(BizFormCashBackStatusEnum.BACK.getValue());
                }else {
                    LocalDate currentDate = LocalDate.now();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = sdf.format(item.getBackDate());
                    LocalDate backDate = LocalDate.parse(dateString);
                    if(currentDate.isAfter(backDate)){
                        item.setBackStatus(BizFormCashBackStatusEnum.OVERDUE.getValue());
                    }else{
                        item.setBackStatus(BizFormCashBackStatusEnum.NO_BACK.getValue());
                    }
                }
            });
        }
        //筛选 归还 状态
        if(ObjectUtil.isNotEmpty(backStatus)){
            return result.stream().filter(x->backStatus.equals(x.getBackStatus())).collect(Collectors.toList());
        }
        return result;
    }
}
