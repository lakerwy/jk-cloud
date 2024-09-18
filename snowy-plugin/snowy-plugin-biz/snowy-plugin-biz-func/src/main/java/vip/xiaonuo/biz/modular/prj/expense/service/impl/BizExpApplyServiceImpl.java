package vip.xiaonuo.biz.modular.prj.expense.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizDebitApply;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpCost;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpItem;
import vip.xiaonuo.biz.modular.prj.expense.enums.BizExpTypeEnum;
import vip.xiaonuo.biz.modular.prj.expense.param.*;
import vip.xiaonuo.biz.modular.prj.expense.param.ExcelParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizDebitApplyService;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpCostService;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpItemService;
import vip.xiaonuo.biz.modular.prj.expense.utils.MergeCellHandler;
import vip.xiaonuo.biz.modular.prj.expense.utils.MyDateUtils;
import vip.xiaonuo.biz.modular.prj.expense.utils.MyDecimalUtils;
import vip.xiaonuo.biz.modular.prj.expense.vo.*;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpApply;
import vip.xiaonuo.biz.modular.prj.expense.mapper.BizExpApplyMapper;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpApplyService;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务管理 - 费用报销|差旅费用报销 - 申请流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/07/30 15:26
 **/

@DS("slave_1")
@Service
public class BizExpApplyServiceImpl extends ServiceImpl<BizExpApplyMapper, BizExpApply> implements BizExpApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private BizExpItemService bizExpItemService;
    @Resource
    private BizExpCostService bizExpCostService;
    @Resource
    private BizDebitApplyService bizDebitApplyService;
    @Override
    public Page<BizExpApplyVo> page(BizExpApplyPageParam bizExpApplyPageParam) {
        QueryWrapper<BizExpApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizExpApply::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getExpType())) {
            queryWrapper.lambda().eq(BizExpApply::getExpType, bizExpApplyPageParam.getExpType());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getInitor())) {
            queryWrapper.lambda().like(BizExpApply::getInitor, bizExpApplyPageParam.getInitor());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getExptor())) {
            queryWrapper.lambda().like(BizExpApply::getExptor, bizExpApplyPageParam.getExptor());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getFinCode())) {
            queryWrapper.lambda().like(BizExpApply::getFinCode, bizExpApplyPageParam.getFinCode());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getUnit())) {
            queryWrapper.lambda().like(BizExpApply::getUnit, bizExpApplyPageParam.getUnit());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getDept())) {
            queryWrapper.lambda().like(BizExpApply::getDept, bizExpApplyPageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getStartExpDate()) && ObjectUtil.isNotEmpty(bizExpApplyPageParam.getEndExpDate())) {
            queryWrapper.lambda().between(BizExpApply::getExpDate, bizExpApplyPageParam.getStartExpDate(), bizExpApplyPageParam.getEndExpDate());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getSn())) {
            queryWrapper.lambda().like(BizExpApply::getSn, bizExpApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizExpApply::getStatus, bizExpApplyPageParam.getStatus());
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizExpApply::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizExpApply::getCreateUser, StpUtil.getLoginIdAsString());
        }
        if(ObjectUtil.isNotEmpty(bizExpApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizExpApply::getSn,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getInitor,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getFinCode,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getDept,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getUnit,bizExpApplyPageParam.getSearchKey())
                    .or().like(BizExpApply::getExptor,bizExpApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizExpApplyPageParam.getSortField(), bizExpApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizExpApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizExpApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizExpApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizExpApply::getCreateTime);
        }
        Page<BizExpApply> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<BizExpApplyVo> recordNew = BeanUtil.copyToList(result.getRecords(),BizExpApplyVo.class);
        if(CollUtil.isNotEmpty(recordNew)){
            recordNew.forEach(item->{
                item.setItemList(bizExpItemService.list(new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,item.getId())));
                item.setCostList(bizExpCostService.list(new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,item.getId())));
            });
        }
        Page<BizExpApplyVo> resultVo = new Page<>();
        resultVo.setRecords(recordNew);
        resultVo.setPages(result.getPages());
        resultVo.setTotal(result.getTotal());
        resultVo.setSize(result.getSize());
        resultVo.setCurrent(result.getCurrent());
        return resultVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizExpApplyAddParam bizExpApplyAddParam) {
        BizExpApply bizExpApply = BeanUtil.toBean(bizExpApplyAddParam, BizExpApply.class);

        //走审批流程
        String processInstanceId = bizExpApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizExpApply.setId(id);
            bizExpApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizExpApply.setModelId(modelId);
            bizExpApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_EXP_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizExpApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizExpApply);
        //处理费用明细
        if(CollUtil.isNotEmpty(bizExpApplyAddParam.getItemList())){
            List<BizExpItem> bizExpItemList = bizExpApplyAddParam.getItemList();
            bizExpItemList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            bizExpItemService.saveBatch(bizExpItemList);
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizExpApplyAddParam.getCostList())){
            List<BizExpCost> bizExpCostList = bizExpApplyAddParam.getCostList();
            bizExpCostList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            bizExpCostService.saveBatch(bizExpCostList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizExpApplyEditParam bizExpApplyEditParam) {
        String id = bizExpApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizExpApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizExpApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizExpApplyEditParam.setId(id);
            }
        }
        BizExpApply bizExpApply = this.queryEntity(bizExpApplyEditParam.getId());
        BeanUtil.copyProperties(bizExpApplyEditParam, bizExpApply);
        this.updateById(bizExpApply);
        //处理费用明细
        if(CollUtil.isNotEmpty(bizExpApplyEditParam.getItemList())){
            //本次传入的数据
            List<BizExpItem> bizExpItemList = bizExpApplyEditParam.getItemList();
            bizExpItemList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            //获取历史数据
            List<BizExpItem> oldList = bizExpItemService.list(
                    new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,bizExpApply.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizExpItem> addList = bizExpItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizExpItem> updateList = bizExpItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizExpItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizExpItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizExpItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizExpItemService.removeByIds(removeList);
            }
        }else{
            bizExpItemService.remove(new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,bizExpApply.getId()));
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizExpApplyEditParam.getCostList())){
            //本次传入的数据
            List<BizExpCost> bizExpCostList = bizExpApplyEditParam.getCostList();
            bizExpCostList.forEach(x->{
                x.setBizExpApplyId(bizExpApply.getId());
            });
            //获取历史数据
            List<BizExpCost> oldList = bizExpCostService.list(
                    new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,bizExpApply.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizExpCost> addList = bizExpCostList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizExpCost> updateList = bizExpCostList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizExpCost> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizExpCostService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizExpCostService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizExpCostService.removeByIds(removeList);
            }
        }else{
            bizExpCostService.remove(new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,bizExpApply.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizExpApplyIdParam> bizExpApplyIdParamList) {
        // 待删除的id
        List<String> delIds = CollStreamUtil.toList(bizExpApplyIdParamList, BizExpApplyIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            bizExpItemService.remove(new QueryWrapper<BizExpItem>().lambda().in(BizExpItem::getBizExpApplyId,delIds));
            bizExpCostService.remove(new QueryWrapper<BizExpCost>().lambda().in(BizExpCost::getBizExpApplyId,delIds));
        }
    }

    @Override
    public BizExpApplyVo detail(BizExpApplyIdParam bizExpApplyIdParam) {
        BizExpApply bizExpApply = this.queryEntity(bizExpApplyIdParam.getId());
        BizExpApplyVo bizExpApplyVo = BeanUtil.copyProperties(bizExpApply,BizExpApplyVo.class);
        bizExpApplyVo.setItemList(bizExpItemService.list(new QueryWrapper<BizExpItem>().lambda().eq(BizExpItem::getBizExpApplyId,bizExpApplyVo.getId())));
        bizExpApplyVo.setCostList(bizExpCostService.list(new QueryWrapper<BizExpCost>().lambda().eq(BizExpCost::getBizExpApplyId,bizExpApplyVo.getId())));
        return bizExpApplyVo;
    }

    @Override
    public BizExpApply queryEntity(String id) {
        BizExpApply bizExpApply = this.getById(id);
        if(ObjectUtil.isEmpty(bizExpApply)) {
            throw new CommonException("财务管理 - 费用报销|差旅费用报销 - 申请流程不存在，id值为：{}", id);
        }
        return bizExpApply;
    }

    @Override
    public void genExcel(ExcelParam excelParam ,HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        InputStream inputStream = null;
        try {
            // 定义没有分隔符的日期时间格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            // 将当前日期时间格式化为字符串
            String dateStr = LocalDateTime.now().format(formatter);
            // 定义导出excel文件路径
            String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + excelParam.getReportType() + "_" + dateStr + ".xlsx";
            // 分类处理
            if (BizExpTypeEnum.FEE.getValue().equals(excelParam.getReportType())) {
                ClassPathResource classPathResource = new ClassPathResource("FeeTemplate.xlsx");
                inputStream = classPathResource.getStream();
                FeeExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), FeeExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData)){
                    if(ObjectUtil.isNotEmpty(excelData.getExpDate())) {
                        excelData.setYearMonthDay(MyDateUtils.getYearMonthDay2(excelData.getExpDate()));
                    }
                    excelData.setLowFeeTotalQian(MyDecimalUtils.getQian(excelData.getLowFeeTotal()));
                    excelData.setTaxCostTotalQian(MyDecimalUtils.getQian(excelData.getTaxCostTotal()));
                    excelData.setLowCostTotalQian(MyDecimalUtils.getQian(excelData.getLowCostTotal()));
                    if(!excelData.getExptor().equals(excelData.getInitor())){
                        excelData.setExpSign(excelData.getInitor()+"代"+excelData.getExptor()+"报销");
                    }
                    if(CollUtil.isNotEmpty(excelData.getCostList())){
                        excelData.getCostList().forEach(x->{
                            x.setPrjNameNo(StrUtil.join("+",x.getPrjNo(),x.getPrjName()));
                        });
                    }
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                WriteSheet sheet1 = EasyExcel.writerSheet(0).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.fill(new FillWrapper("data1", excelData.getItemList()), sheet1);
                excelWriter.fill(new FillWrapper("data2", excelData.getCostList()), sheet1);
                excelWriter.finish();

            } else if (BizExpTypeEnum.TRAFFIC_FEE.getValue().equals(excelParam.getReportType())) {
                ClassPathResource classPathResource = new ClassPathResource("TrafficTemplate.xlsx");
                inputStream = classPathResource.getStream();
                TripExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), TripExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData)){
                    if(ObjectUtil.isNotEmpty(excelData.getExpDate())) {
                        excelData.setYearMonthDay(MyDateUtils.getYearMonthDay2(excelData.getExpDate()));
                    }
                    excelData.setLowFeeTotalQian(MyDecimalUtils.getQian(excelData.getLowFeeTotal()));
                    excelData.setTaxCostTotalQian(MyDecimalUtils.getQian(excelData.getTaxCostTotal()));
                    excelData.setLowCostTotalQian(MyDecimalUtils.getQian(excelData.getLowCostTotal()));
                    if(!excelData.getExptor().equals(excelData.getInitor())){
                        excelData.setExpSign(excelData.getInitor()+"代"+excelData.getExptor()+"报销");
                    }
                    if(CollUtil.isNotEmpty(excelData.getCostList())){
                        excelData.getCostList().forEach(x->{
                            x.setPrjNameNo(StrUtil.join("+",x.getPrjNo(),x.getPrjName()));
                        });
                    }
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                WriteSheet sheet1 = EasyExcel.writerSheet(0).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.fill(new FillWrapper("data1", excelData.getItemList()), sheet1);
                excelWriter.fill(new FillWrapper("data2", excelData.getCostList()), sheet1);
                excelWriter.finish();

            } else if (BizExpTypeEnum.DEBIT.getValue().equals(excelParam.getReportType())) {
                ClassPathResource classPathResource = new ClassPathResource("DebitTemplate.xlsx");
                inputStream = classPathResource.getStream();
                DebitExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), DebitExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData)){
                    if(ObjectUtil.isNotEmpty(excelData.getExpDate())) {
                        excelData.setYearMonthDay(MyDateUtils.getYearMonthDay2(excelData.getExpDate()));
                    }
                    excelData.setLowAmountQian(MyDecimalUtils.getQian(excelData.getLowAmount()));
                    if(!excelData.getBorrower().equals(excelData.getInitor())){
                        excelData.setDebitSign(excelData.getInitor()+"代"+excelData.getBorrower()+"借支");
                    }
                    if(ObjectUtil.isNotEmpty(excelData.getReturnDate())){
                        excelData.setReturnDateStr(MyDateUtils.getYearMonthDay2(excelData.getReturnDate()));
                    }
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                //借支单
                WriteSheet sheet1 = EasyExcel.writerSheet(0).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.finish();
            } else {
                throw new CommonException("不支持的报表类型");
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



    public void genExcelOld(ExcelParam excelParam ,HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        InputStream inputStream = null;
        try {
            // 定义没有分隔符的日期时间格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            // 将当前日期时间格式化为字符串
            String dateStr = LocalDateTime.now().format(formatter);
            // 定义导出excel文件路径
            String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + excelParam.getReportType() + "_" + dateStr + ".xlsx";
            // 分类处理
            if (BizExpTypeEnum.FEE.getValue().equals(excelParam.getReportType())) {
                ClassPathResource classPathResource = new ClassPathResource("FeeTemplate_old.xlsx");
                inputStream = classPathResource.getStream();
                FeeExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), FeeExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData)){
                    if(ObjectUtil.isNotEmpty(excelData.getExpDate())) {
                        excelData.setYearMonthDay(MyDateUtils.getYearMonthDay2(excelData.getExpDate()));
                    }
                    excelData.setLowFeeTotalQian(MyDecimalUtils.getQian(excelData.getLowFeeTotal()));
                    excelData.setTaxCostTotalQian(MyDecimalUtils.getQian(excelData.getTaxCostTotal()));
                    excelData.setLowCostTotalQian(MyDecimalUtils.getQian(excelData.getLowCostTotal()));
                    if(!excelData.getExptor().equals(excelData.getInitor())){
                        excelData.setExpSign(excelData.getInitor()+"代"+excelData.getExptor()+"报销");
                    }
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                //费用报销单
                List<MergeCell> feeMergeList = new ArrayList<>();
                //firstRow:模版行（因为模版行已合并）  lastRow:合并结束行
                int firstRow = 3;
                feeMergeList.add(new MergeCell(firstRow, excelData.getItemList().size() + firstRow - 1, 1, 3));
                WriteSheet sheet1 = EasyExcel.writerSheet(0).registerWriteHandler(new MergeCellHandler(feeMergeList)).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.fill(new FillWrapper("data1", excelData.getItemList()), fillConfig, sheet1);
                excelWriter.fill(new FillWrapper("data2", excelData.getCostList()), fillConfig, sheet1);
                excelWriter.finish();

            } else if (BizExpTypeEnum.TRAFFIC_FEE.getValue().equals(excelParam.getReportType())) {
                ClassPathResource classPathResource = new ClassPathResource("TrafficTemplate_old.xlsx");
                inputStream = classPathResource.getStream();
                TripExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), TripExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData)){
                    if(ObjectUtil.isNotEmpty(excelData.getExpDate())) {
                        excelData.setYearMonthDay(MyDateUtils.getYearMonthDay2(excelData.getExpDate()));
                    }
                    excelData.setLowFeeTotalQian(MyDecimalUtils.getQian(excelData.getLowFeeTotal()));
                    excelData.setTaxCostTotalQian(MyDecimalUtils.getQian(excelData.getTaxCostTotal()));
                    excelData.setLowCostTotalQian(MyDecimalUtils.getQian(excelData.getLowCostTotal()));
                    if(!excelData.getExptor().equals(excelData.getInitor())){
                        excelData.setExpSign(excelData.getInitor()+"代"+excelData.getExptor()+"报销");
                    }
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                //差旅费报销单
                List<MergeCell> tripMergeList = new ArrayList<>();
                //firstRow:模版行（因为模版行已合并）  lastRow:合并结束行
                int firstRow = 13 + excelData.getItemList().size() - 1;
                tripMergeList.add(new MergeCell(firstRow, (excelData.getCostList().size() + firstRow - 1), 0, 1));
                tripMergeList.add(new MergeCell(firstRow, (excelData.getCostList().size() + firstRow - 1), 2, 3));
                tripMergeList.add(new MergeCell(firstRow, (excelData.getCostList().size() + firstRow - 1), 4, 8));
                WriteSheet sheet1 = EasyExcel.writerSheet(0).registerWriteHandler(new MergeCellHandler(tripMergeList)).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.fill(new FillWrapper("data1", excelData.getItemList()), fillConfig, sheet1);
                excelWriter.fill(new FillWrapper("data2", excelData.getCostList()), fillConfig, sheet1);
                excelWriter.finish();

            } else if (BizExpTypeEnum.DEBIT.getValue().equals(excelParam.getReportType())) {
                ClassPathResource classPathResource = new ClassPathResource("DebitTemplate_old.xlsx");
                inputStream = classPathResource.getStream();
                DebitExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), DebitExcelData.class);
                if(ObjectUtil.isNotEmpty(excelData)){
                    if(ObjectUtil.isNotEmpty(excelData.getExpDate())) {
                        excelData.setYearMonthDay(MyDateUtils.getYearMonthDay2(excelData.getExpDate()));
                    }
                    excelData.setLowAmountQian(MyDecimalUtils.getQian(excelData.getLowAmount()));
                    if(!excelData.getBorrower().equals(excelData.getInitor())){
                        excelData.setDebitSign(excelData.getInitor()+"代"+excelData.getBorrower()+"借支");
                    }
                    if(ObjectUtil.isNotEmpty(excelData.getReturnDate())){
                        excelData.setReturnDateStr(MyDateUtils.getYearMonthDay2(excelData.getReturnDate()));
                    }
                }
                // 使用 EasyExcel 填充数据
                excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
                //借支单
                WriteSheet sheet1 = EasyExcel.writerSheet(0).build();
                excelWriter.fill(excelData, sheet1);
                excelWriter.finish();
            } else {
                throw new CommonException("不支持的报表类型");
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

    @Override
    public List<BizExpData> totalPage(BizExpTotalSearch search) {
        if(ObjectUtil.isEmpty(search.getType())){
            throw new CommonException("请选择类型");
        }
        LocalDate startDate = null;
        LocalDate endDate = null;
        if(ObjectUtil.isNotEmpty(search.getYearMonth())){
            YearMonth yearMonth = YearMonth.parse(search.getYearMonth(), DateTimeFormatter.ofPattern("yyyy-MM"));
            YearMonth nextYearMonth = yearMonth.plusMonths(1);
            startDate = yearMonth.atDay(1);
            endDate = nextYearMonth.atDay(1);
        }
        //借支单
        if(BizExpTypeEnum.DEBIT.getValue().equals(search.getType())){
            QueryWrapper<BizDebitApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(BizDebitApply::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue());
            if(ObjectUtil.isNotEmpty(search.getYearMonth())){
                queryWrapper.lambda().between(BizDebitApply::getExpDate,startDate,endDate);
            }
            if(ObjectUtil.isNotEmpty(search.getUnit())){
                queryWrapper.lambda().eq(BizDebitApply::getUnit,search.getUnit());
            }
            if(ObjectUtil.isNotEmpty(search.getSearchKey())){
                queryWrapper.lambda().like(BizDebitApply::getBorrower,search.getSearchKey());
            }
            queryWrapper.lambda().orderByAsc(BizDebitApply::getUnit).orderByAsc(BizDebitApply::getBorrower);
            List<BizDebitApply> dataList = bizDebitApplyService.list(queryWrapper);
            //结果集
            List<BizExpData> result = new ArrayList<>();
            if(CollUtil.isNotEmpty(dataList)){
                dataList.forEach(item->{
                    BizExpData bizExpData = new BizExpData();
                    bizExpData.setUnit(item.getUnit());
                    bizExpData.setDept(item.getDept());
                    bizExpData.setExpDate(item.getExpDate());
                    bizExpData.setExptor(item.getBorrower());
                    bizExpData.setLowFeeTotal(item.getLowAmount());
                    BigDecimal empTotal = BigDecimal.ZERO;
                    List<String> tmpEmpList = dataList.stream().filter(x->item.getUnit().equals(x.getUnit()) && item.getDept().equals(x.getDept()) && item.getBorrower().equals(x.getBorrower())).map(x->x.getLowAmount()).collect(Collectors.toList());
                    List<String> tmpUnitList = dataList.stream().filter(x->item.getUnit().equals(x.getUnit())).map(x->x.getLowAmount()).collect(Collectors.toList());
                    bizExpData.setEmpTotal(sumTotal(tmpEmpList).toString());
                    bizExpData.setUnitTotal(sumTotal(tmpUnitList).toString());
                    result.add(bizExpData);
                });
            }
            return result;
        }else{
            //报销单
            QueryWrapper<BizExpApply> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(BizExpApply::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue());
            queryWrapper.lambda().eq(BizExpApply::getExpType,search.getType());
            if(ObjectUtil.isNotEmpty(search.getYearMonth())){
                queryWrapper.lambda().between(BizExpApply::getExpDate,startDate,endDate);
            }
            if(ObjectUtil.isNotEmpty(search.getUnit())){
                queryWrapper.lambda().eq(BizExpApply::getUnit,search.getUnit());
            }
            if(ObjectUtil.isNotEmpty(search.getSearchKey())){
                queryWrapper.lambda().like(BizExpApply::getExptor,search.getSearchKey());
            }
            queryWrapper.lambda().orderByAsc(BizExpApply::getUnit).orderByAsc(BizExpApply::getExptor);
            List<BizExpApply> dataList = this.list(queryWrapper);
            //结果集
            List<BizExpData> result = new ArrayList<>();
            if(CollUtil.isNotEmpty(dataList)){
                dataList.forEach(item->{
                    BizExpData bizExpData = new BizExpData();
                    bizExpData.setUnit(item.getUnit());
                    bizExpData.setDept(item.getDept());
                    bizExpData.setExpDate(item.getExpDate());
                    bizExpData.setExptor(item.getExptor());
                    bizExpData.setLowFeeTotal(item.getLowFeeTotal());
                    BigDecimal empTotal = BigDecimal.ZERO;
                    List<String> tmpEmpList = dataList.stream().filter(x->item.getUnit().equals(x.getUnit()) && item.getDept().equals(x.getDept()) && item.getExptor().equals(x.getExptor())).map(x->x.getLowFeeTotal()).collect(Collectors.toList());
                    List<String> tmpUnitList = dataList.stream().filter(x->item.getUnit().equals(x.getUnit())).map(x->x.getLowFeeTotal()).collect(Collectors.toList());
                    bizExpData.setEmpTotal(sumTotal(tmpEmpList).toString());
                    bizExpData.setUnitTotal(sumTotal(tmpUnitList).toString());
                    result.add(bizExpData);
                });
            }
            return result;
        }
    }

    /**
     * BigDecimal 求和
     * @param data
     * @return
     */
    public BigDecimal sumTotal(List<String> data){
        BigDecimal sum = BigDecimal.ZERO;
        try {
            for (String item:data) {
                BigDecimal tmpAdd = new BigDecimal(item);
                sum = sum.add(tmpAdd);
            }
            return sum;
        }catch (Exception e){
            sum = BigDecimal.ZERO;
        }
        return sum;
    }

    /**
     * 模版填充 FeeReportTemplate.xlsx 各类尝试
     */
    public void genExcelTest() {  //ExcelParam excelParam, HttpServletResponse response

        ClassPathResource classPathResource = new ClassPathResource("FeeReportTemplate.xlsx");
        String templateFileName  = classPathResource.getAbsolutePath();
        String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + "报销测试.xlsx";

        FeeExcelData feeExcel = new FeeExcelData();
        TripExcelData tripExcel = new TripExcelData();
        DebitExcelData debitExcel = new DebitExcelData();

        // 使用 EasyExcel 填充数据
        try {
            ExcelWriter excelWriter = EasyExcel.write(outputFileName).withTemplate(templateFileName).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            //费用报销单
            List<MergeCell> feeMergeList = new ArrayList<>();
            //firstRow:模版行（因为模版行已合并）  lastRow:合并结束行
            int firstRow = 3;
            feeMergeList.add(new MergeCell(firstRow,feeExcel.getItemList().size()+firstRow-1,1,3));
            WriteSheet sheet1 = EasyExcel.writerSheet(1).registerWriteHandler(new MergeCellHandler(feeMergeList)).build();
            excelWriter.fill(feeExcel,sheet1);
            excelWriter.fill(new FillWrapper("data1", feeExcel.getItemList()),fillConfig, sheet1);
            excelWriter.fill(new FillWrapper("data2", feeExcel.getCostList()),fillConfig, sheet1);

            //差旅费报销单
            List<MergeCell> tripMergeList = new ArrayList<>();
            //firstRow:模版行（因为模版行已合并）  lastRow:合并结束行
            firstRow = 13 + tripExcel.getItemList().size()-1;
            tripMergeList.add(new MergeCell(firstRow,(tripExcel.getCostList().size()+firstRow-1),0,1));
            tripMergeList.add(new MergeCell(firstRow,(tripExcel.getCostList().size()+firstRow-1),2,3));
            tripMergeList.add(new MergeCell(firstRow,(tripExcel.getCostList().size()+firstRow-1),4,8));
            WriteSheet sheet2 = EasyExcel.writerSheet(2).registerWriteHandler(new MergeCellHandler(tripMergeList)).build();
            excelWriter.fill(tripExcel,sheet2);
            excelWriter.fill(new FillWrapper("data1", tripExcel.getItemList()),fillConfig, sheet2);
            excelWriter.fill(new FillWrapper("data2", tripExcel.getCostList()),fillConfig, sheet2);

            //借支单
            WriteSheet sheet3 = EasyExcel.writerSheet(3).build();
            excelWriter.fill(debitExcel,sheet3);

            //手机截图
            WriteCellStyle writeCellStyle = new WriteCellStyle();
            writeCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            writeCellStyle.setBorderLeft(BorderStyle.THIN);
            writeCellStyle.setBorderTop(BorderStyle.THIN);
            writeCellStyle.setBorderRight(BorderStyle.THIN);
            writeCellStyle.setBorderBottom(BorderStyle.THIN);
            //设置 自动换行
            writeCellStyle.setWrapped(true);
            // 字体策略
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short) 11);
            contentWriteFont.setFontName("宋体");
            contentWriteFont.setBold(true);
            writeCellStyle.setWriteFont(contentWriteFont);

            /** 图1 **/
            List<ImageData> imageDataList1 = new ArrayList<>();
            ImageData imageData1 = new ImageData();
            imageData1.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\1.png"));
            imageData1.setTop(40);
            imageData1.setBottom(20);
            imageData1.setLeft(10);
            imageData1.setRight(10);
            imageDataList1.add(imageData1);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData1 = new WriteCellData<>();
            writeCellData1.setWriteCellStyle(writeCellStyle);
            writeCellData1.setImageDataList(imageDataList1);
            writeCellData1.setType(CellDataTypeEnum.STRING);
            writeCellData1.setStringValue("说明：张工和昌盛集团小王吃饭");

            /** 图2 **/
            List<ImageData> imageDataList2 = new ArrayList<>();
            ImageData imageData2 = new ImageData();
            imageData2.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\2.jpeg"));
            imageData2.setTop(40);
            imageData2.setBottom(20);
            imageData2.setLeft(10);
            imageData2.setRight(10);
            imageDataList2.add(imageData2);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData2 = new WriteCellData<>();
            writeCellData2.setWriteCellStyle(writeCellStyle);
            writeCellData2.setImageDataList(imageDataList2);
            writeCellData2.setType(CellDataTypeEnum.STRING);
            writeCellData2.setStringValue("说明：张工和昌盛集团小王吃饭张工和昌盛集团小王吃饭");

            /** list 单元格中填充文字和图片 **/
            List<ImgCell> imgList1 = new ArrayList<>();
            imgList1.add(new ImgCell(writeCellData1,writeCellData1));
            imgList1.add(new ImgCell(writeCellData2,writeCellData2));
            imgList1.add(new ImgCell(writeCellData1,writeCellData2));
            WriteSheet sheet4 = EasyExcel.writerSheet(4).build();
            excelWriter.fill(new FillWrapper("data", imgList1),fillConfig, sheet4);

            //发票
            /** 图1 **/
            List<ImageData> imageDataList11 = new ArrayList<>();
            ImageData imageData11 = new ImageData();
            imageData11.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\2.jpeg"));
            imageData11.setTop(40);
            imageData11.setBottom(20);
            imageData11.setLeft(10);
            imageData11.setRight(10);
            imageDataList11.add(imageData11);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData11 = new WriteCellData<>();
            writeCellData11.setWriteCellStyle(writeCellStyle);
            writeCellData11.setImageDataList(imageDataList11);
            writeCellData11.setType(CellDataTypeEnum.STRING);
            writeCellData11.setStringValue("说明：张工和昌盛集团小王吃饭张工和昌盛集团小王吃饭");
            /** 图2 **/
            List<ImageData> imageDataList12 = new ArrayList<>();
            ImageData imageData12 = new ImageData();
            imageData12.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\3.png"));
            imageData12.setTop(40);
            imageData12.setBottom(20);
            imageData12.setLeft(10);
            imageData12.setRight(10);
            imageDataList12.add(imageData12);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData12 = new WriteCellData<>();
            writeCellData12.setWriteCellStyle(writeCellStyle);
            writeCellData12.setImageDataList(imageDataList12);
            writeCellData12.setType(CellDataTypeEnum.STRING);
            writeCellData12.setStringValue("说明：张工和昌盛集团小王吃饭");
            /** list 单元格中填充文字和图片 **/
            List<ImgCell> imgList2 = new ArrayList<>();
            imgList2.add(new ImgCell(writeCellData11,null));
            imgList2.add(new ImgCell(writeCellData12,null));
            imgList2.add(new ImgCell(writeCellData11,null));
            WriteSheet sheet5 = EasyExcel.writerSheet(5).build();
            excelWriter.fill(new FillWrapper("data", imgList2),fillConfig, sheet5);

            excelWriter.finish();

        }catch (Exception e){
            System.console().printf(e.getMessage());

        }
    }

}
