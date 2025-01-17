package vip.xiaonuo.biz.modular.prjFee.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import vip.xiaonuo.biz.modular.bidFee.entity.BidFee;
import vip.xiaonuo.biz.modular.prjFee.param.ExcelParam;
import vip.xiaonuo.biz.modular.bidFee.vo.BidFeeExcelData;
import vip.xiaonuo.biz.modular.prj.expense.utils.MergeCellHandler;
import vip.xiaonuo.biz.modular.prj.expense.vo.MergeCell;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotmentItem;
import vip.xiaonuo.biz.modular.prjFee.param.*;
import vip.xiaonuo.biz.modular.prjFee.service.PrjFeeAllotmentItemService;
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeAllotmentExcelData;
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeAllotmentVo;
import vip.xiaonuo.biz.modular.prjFee.vo.PrjFeeHisVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotment;
import vip.xiaonuo.biz.modular.prjFee.mapper.PrjFeeAllotmentMapper;
import vip.xiaonuo.biz.modular.prjFee.service.PrjFeeAllotmentService;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.flw.api.FlwProcessMonitorApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工程款拨付审批Service接口实现类
 *
 * @author pzh
 * @date  2025/01/15 09:22
 **/
@Service
@DS("slave_1")
public class PrjFeeAllotmentServiceImpl extends ServiceImpl<PrjFeeAllotmentMapper, PrjFeeAllotment> implements PrjFeeAllotmentService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    FlwCommonApi flwCommonApi;

    @Resource
    PrjFeeAllotmentItemService prjFeeAllotmentItemService;

    @Resource
    FlwProcessMonitorApi flwProcessMonitorApi;

    @Override
    public Page<PrjFeeAllotment> page(PrjFeeAllotmentPageParam prjFeeAllotmentPageParam) {
        QueryWrapper<PrjFeeAllotment> queryWrapper = new QueryWrapper<>();
        // 默认只展示审核中和已完成的数据
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getStatus())){
            queryWrapper.lambda().eq(PrjFeeAllotment::getStatus,prjFeeAllotmentPageParam.getStatus());
        }else {
            queryWrapper.lambda().in(PrjFeeAllotment::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        }
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(PrjFeeAllotment::getSn,prjFeeAllotmentPageParam.getSearchKey())
                    .or().like(PrjFeeAllotment::getProjectName,prjFeeAllotmentPageParam.getSearchKey())
                    .or().like(PrjFeeAllotment::getCompanyOrDepartment,prjFeeAllotmentPageParam.getSearchKey())
                    .or().like(PrjFeeAllotment::getCounterparty,prjFeeAllotmentPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getFeeType())){
            queryWrapper.lambda().like(PrjFeeAllotment::getFeeType,prjFeeAllotmentPageParam.getFeeType());
        }
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getIsMap())){
            queryWrapper.lambda().like(PrjFeeAllotment::getIsMap,prjFeeAllotmentPageParam.getIsMap());
        }
        if(ObjectUtil.isNotEmpty(prjFeeAllotmentPageParam.getFeeDate())){
            LocalDate date = LocalDate.parse(prjFeeAllotmentPageParam.getFeeDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            queryWrapper.lambda().between(PrjFeeAllotment::getFeeDate, startOfDay, endOfDay);
        }
        if(ObjectUtil.isAllNotEmpty(prjFeeAllotmentPageParam.getSortField(), prjFeeAllotmentPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(prjFeeAllotmentPageParam.getSortOrder());
            queryWrapper.orderBy(true, prjFeeAllotmentPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(prjFeeAllotmentPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PrjFeeAllotment::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjFeeAllotmentAddParam prjFeeAllotmentAddParam) {
        PrjFeeAllotment prjFeeAllotment = BeanUtil.toBean(prjFeeAllotmentAddParam, PrjFeeAllotment.class);
        // 走审批流程
        String processInstanceId = prjFeeAllotmentAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            prjFeeAllotment.setId(id);
            prjFeeAllotment.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            prjFeeAllotment.setModelId(modelId);
            prjFeeAllotment.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_PRJ_FEE_ALLOTMENT.getBizTable(),id,processInstanceId,dbName);
        }else{
            prjFeeAllotment.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(prjFeeAllotment);
        // 处理款项拨付
        if(CollUtil.isNotEmpty(prjFeeAllotmentAddParam.getFeeItemList())){
            List<PrjFeeAllotmentItem> feeItemList = prjFeeAllotmentAddParam.getFeeItemList();
            feeItemList.forEach(x->{
                x.setPrjFeeAllotmentId(prjFeeAllotment.getId());
            });
            prjFeeAllotmentItemService.saveBatch(feeItemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjFeeAllotmentEditParam prjFeeAllotmentEditParam) {
        PrjFeeAllotment prjFeeAllotment = this.queryEntity(prjFeeAllotmentEditParam.getId());
        String id = prjFeeAllotmentEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(prjFeeAllotmentEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(prjFeeAllotmentEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                prjFeeAllotmentEditParam.setId(id);
            }
        }
        BeanUtil.copyProperties(prjFeeAllotmentEditParam, prjFeeAllotment);
        this.updateById(prjFeeAllotment);
        // 处理款项拨付
        if(CollUtil.isNotEmpty(prjFeeAllotmentEditParam.getFeeItemList())){
            //本次传入的数据
            List<PrjFeeAllotmentItem> feeItemList = prjFeeAllotmentEditParam.getFeeItemList();
            feeItemList.forEach(x->{
                x.setPrjFeeAllotmentId(prjFeeAllotment.getId());
            });
            //获取历史数据
            List<PrjFeeAllotmentItem> oldList = prjFeeAllotmentItemService.list(
                    new QueryWrapper<PrjFeeAllotmentItem>().lambda().eq(PrjFeeAllotmentItem::getPrjFeeAllotmentId,prjFeeAllotment.getId()));
            List<String> oldIds = oldList.stream().map(PrjFeeAllotmentItem::getId).collect(Collectors.toList());
            //待新增的
            List<PrjFeeAllotmentItem> addList = feeItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<PrjFeeAllotmentItem> updateList = feeItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(PrjFeeAllotmentItem::getId).collect(Collectors.toList());
            List<PrjFeeAllotmentItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                prjFeeAllotmentItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                prjFeeAllotmentItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                prjFeeAllotmentItemService.removeByIds(removeList);
            }
        }else{
            prjFeeAllotmentItemService.remove(new QueryWrapper<PrjFeeAllotmentItem>().lambda().eq(PrjFeeAllotmentItem::getPrjFeeAllotmentId,prjFeeAllotment.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PrjFeeAllotmentIdParam> prjFeeAllotmentIdParamList) {
        // 待删除的id
        List<String> delIds = CollStreamUtil.toList(prjFeeAllotmentIdParamList, PrjFeeAllotmentIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        // 删除款项拨付
        if(CollUtil.isNotEmpty(delIds)){
            prjFeeAllotmentItemService.remove(new QueryWrapper<PrjFeeAllotmentItem>().lambda().in(PrjFeeAllotmentItem::getPrjFeeAllotmentId,delIds));
        }
    }

    @Override
    public PrjFeeAllotmentVo detail(PrjFeeAllotmentIdParam prjFeeAllotmentIdParam) {
        PrjFeeAllotment prjFeeAllotment = this.queryEntity(prjFeeAllotmentIdParam.getId());
        PrjFeeAllotmentVo prjFeeAllotmentVo = BeanUtil.copyProperties(prjFeeAllotment, PrjFeeAllotmentVo.class);
        prjFeeAllotmentVo.setFeeItemList(prjFeeAllotmentItemService.list(new QueryWrapper<PrjFeeAllotmentItem>().lambda().eq(PrjFeeAllotmentItem::getPrjFeeAllotmentId, prjFeeAllotmentVo.getId())));
        return prjFeeAllotmentVo;
    }

    @Override
    public PrjFeeAllotment queryEntity(String id) {
        PrjFeeAllotment prjFeeAllotment = this.getById(id);
        if(ObjectUtil.isEmpty(prjFeeAllotment)) {
            throw new CommonException("工程款拨付审批不存在，id值为：{}", id);
        }
        return prjFeeAllotment;
    }

    @Override
    public PrjFeeHisVo prjFeeHis(PrjFeeHisParam prjFeeHisParam) {
        PrjFeeHisVo prjFeeHisVo = new PrjFeeHisVo();
        // 获取审批中和审批通过的拨付单
        LambdaQueryWrapper<PrjFeeAllotment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PrjFeeAllotment::getHetongId, prjFeeHisParam.getHetongId());
        queryWrapper.in(PrjFeeAllotment::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        List<PrjFeeAllotment> prjFeeAllotmentList = this.list(queryWrapper);
        // 没有查到，已拨付次数视为0，直接返回
        if(CollUtil.isEmpty(prjFeeAllotmentList)) {
            prjFeeHisVo.setDisbursedTimes(0);
            prjFeeHisVo.setPreAllotmentOver(Boolean.TRUE);
            return prjFeeHisVo;
        }
        // queryWrapper.isNotNull(PrjFeeAllotment::getCurDisburseNum);
        queryWrapper.orderByDesc(PrjFeeAllotment::getCurDisburseNum);
        queryWrapper.last("limit 1");
        PrjFeeAllotment prjFeeAllotment = this.getOne(queryWrapper);
        prjFeeHisVo = BeanUtil.copyProperties(prjFeeAllotment, PrjFeeHisVo.class);
        prjFeeHisVo.setDisbursedTimes(prjFeeAllotment.getCurDisburseNum());
        List<PrjFeeAllotment> collect = prjFeeAllotmentList.stream().filter(x -> FlwStatusEnum.ACTIVE_STATUS.getValue().equals(x.getStatus()))
                .collect(Collectors.toList());
        prjFeeHisVo.setPreAllotmentOver(CollUtil.isEmpty(collect)?Boolean.TRUE:Boolean.FALSE);
        return prjFeeHisVo;
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
            String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR +  "PRJ_FEE_ALLOTMENT_" + dateStr + ".xlsx";
            ClassPathResource classPathResource = new ClassPathResource("PrjFeeAllotmentTemplate.xlsx");
            inputStream = classPathResource.getStream();
            PrjFeeAllotmentExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), PrjFeeAllotmentExcelData.class);
            excelData.setCommentList(excelParam.getCommentList());
            if(ObjectUtil.isNotEmpty(excelData.getFeeItemList())) {
                for(int i=0;i<excelData.getFeeItemList().size();i++) {
                    PrjFeeAllotmentExcelData.PrjFeeAllotmentItem item = excelData.getFeeItemList().get(i);
                    item.setSortNum(i+1);// 设置序号
                }
            }
            // 获取评论列表
            setCommentList(excelData);
            List<MergeCell> mergeList = new ArrayList<>();
            int firstRow_feeItemList = 9;
            int feeItemListSize = ObjectUtil.isEmpty(excelData.getFeeItemList()) ? 1 : excelData.getFeeItemList().size();
            int firstRow_commentList = firstRow_feeItemList + feeItemListSize + 4;
            mergeList.add(new MergeCell(firstRow_feeItemList, feeItemListSize + firstRow_feeItemList - 1, 3, 4));
            mergeList.add(new MergeCell(firstRow_feeItemList, feeItemListSize + firstRow_feeItemList - 1, 8, 9));
            mergeList.add(new MergeCell(firstRow_feeItemList, feeItemListSize + firstRow_feeItemList - 1, 10, 11));
            mergeList.add(new MergeCell(firstRow_feeItemList, feeItemListSize + firstRow_feeItemList - 1, 13, 14));
            mergeList.add(new MergeCell(firstRow_feeItemList, feeItemListSize + firstRow_feeItemList - 1, 16, 17));

            mergeList.add(new MergeCell(firstRow_commentList, excelData.getCommentList().size() + firstRow_commentList - 1, 0, 5));
            mergeList.add(new MergeCell(firstRow_commentList, excelData.getCommentList().size() + firstRow_commentList - 1, 6, 7));
            mergeList.add(new MergeCell(firstRow_commentList, excelData.getCommentList().size() + firstRow_commentList - 1, 8, 10));
            mergeList.add(new MergeCell(firstRow_commentList, excelData.getCommentList().size() + firstRow_commentList - 1, 11, 17));
            WriteSheet sheet1 = EasyExcel.writerSheet(0).registerWriteHandler(new MergeCellHandler(mergeList)).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            // 使用 EasyExcel 填充数据
            excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
            excelWriter.fill(excelData, sheet1);
            excelWriter.fill(new FillWrapper("data1", excelData.getFeeItemList()), fillConfig, sheet1);
            excelWriter.fill(new FillWrapper("data2", excelData.getCommentList()), fillConfig, sheet1);
            excelWriter.finish();

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

    // 获取并设置评论列表
    private void setCommentList(PrjFeeAllotmentExcelData excelData) {
        if(CollUtil.isNotEmpty(excelData.getCommentList())){
            return;
        }
        List<PrjFeeAllotmentExcelData.FlwProcessComment> commentList = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(excelData.getProcessInstanceId())){
            commentList = getFlwProcessComments(excelData.getProcessInstanceId());
        }else {
            if(ObjectUtil.isNotEmpty(excelData.getId())){
                PrjFeeAllotment prjFeeAllotment = this.getById(excelData.getId());
                if(ObjectUtil.isNotEmpty(prjFeeAllotment)){
                    commentList = getFlwProcessComments(prjFeeAllotment.getProcessInstanceId());
                }
            }
        }
        excelData.setCommentList(commentList);
    }

    // 获取评论列表
    private List<PrjFeeAllotmentExcelData.FlwProcessComment> getFlwProcessComments(String processInstanceId) {
        List<PrjFeeAllotmentExcelData.FlwProcessComment> commentList = new ArrayList<>();
        // 查询审批评论列表
        if(StrUtil.isNotBlank(processInstanceId)){
            JSONObject jsonObject = flwProcessMonitorApi.monitorDetail(processInstanceId);
            if(ObjectUtil.isNotEmpty(jsonObject)){
                JSONArray jsonArray = jsonObject.getJSONArray("commentList");
                if(CollUtil.isNotEmpty(jsonArray)){
                    commentList = JSONUtil.toList(jsonArray.toString(), PrjFeeAllotmentExcelData.FlwProcessComment.class);
                }
            }
        }
        return commentList;
    }
}
