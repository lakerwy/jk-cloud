package vip.xiaonuo.sys.modular.hr.goods.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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
import vip.xiaonuo.common.util.CommonResponseUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsApply;
import vip.xiaonuo.sys.modular.hr.goods.entity.GoodsItem;
import vip.xiaonuo.sys.modular.hr.goods.enums.GoodsTypeEnum;
import vip.xiaonuo.sys.modular.hr.goods.mapper.GoodsApplyMapper;
import vip.xiaonuo.sys.modular.hr.goods.param.*;
import vip.xiaonuo.sys.modular.hr.goods.service.GoodsApplyService;
import vip.xiaonuo.sys.modular.hr.goods.service.GoodsItemService;
import vip.xiaonuo.sys.modular.hr.goods.util.MyFileUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 物资领用申请Service接口实现类
 *
 * @author yy
 * @date  2025/05/23 14:34
 **/
@Service
public class GoodsApplyServiceImpl extends ServiceImpl<GoodsApplyMapper, GoodsApply> implements GoodsApplyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private GoodsItemService goodsItemService;

    @Override
    public Page<GoodsApply> page(GoodsApplyPageParam goodsApplyPageParam) {
        QueryWrapper<GoodsApply> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(GoodsApply::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getDeptId())) {
            queryWrapper.lambda().eq(GoodsApply::getDeptId, goodsApplyPageParam.getDeptId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getDeptName())) {
            queryWrapper.lambda().like(GoodsApply::getDeptName, goodsApplyPageParam.getDeptName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getOptId())) {
            queryWrapper.lambda().eq(GoodsApply::getOptId, goodsApplyPageParam.getOptId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getOptName())) {
            queryWrapper.lambda().like(GoodsApply::getOptName, goodsApplyPageParam.getOptName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getStartOptTime()) && ObjectUtil.isNotEmpty(goodsApplyPageParam.getEndOptTime())) {
            queryWrapper.lambda().between(GoodsApply::getOptTime, goodsApplyPageParam.getStartOptTime(), goodsApplyPageParam.getEndOptTime());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getDescMemo())) {
            queryWrapper.lambda().like(GoodsApply::getDescMemo, goodsApplyPageParam.getDescMemo());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getConfirmType())) {
            queryWrapper.lambda().eq(GoodsApply::getConfirmType, goodsApplyPageParam.getConfirmType());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getStartConfirmTime()) && ObjectUtil.isNotEmpty(goodsApplyPageParam.getEndConfirmTime())) {
            queryWrapper.lambda().between(GoodsApply::getConfirmTime, goodsApplyPageParam.getStartConfirmTime(), goodsApplyPageParam.getEndConfirmTime());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getBackId())) {
            queryWrapper.lambda().eq(GoodsApply::getBackId, goodsApplyPageParam.getBackId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getBackName())) {
            queryWrapper.lambda().like(GoodsApply::getBackName, goodsApplyPageParam.getBackName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getVerifyId())) {
            queryWrapper.lambda().eq(GoodsApply::getVerifyId, goodsApplyPageParam.getVerifyId());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getVerifyName())) {
            queryWrapper.lambda().like(GoodsApply::getVerifyName, goodsApplyPageParam.getVerifyName());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getSn())) {
            queryWrapper.lambda().like(GoodsApply::getSn, goodsApplyPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getStatus())) {
            queryWrapper.lambda().eq(GoodsApply::getStatus, goodsApplyPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(goodsApplyPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(GoodsApply::getDeptName,goodsApplyPageParam.getSearchKey())
                    .or().like(GoodsApply::getSn,goodsApplyPageParam.getSearchKey())
                    .or().like(GoodsApply::getGoodsApplyJson,goodsApplyPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(goodsApplyPageParam.getSortField(), goodsApplyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(goodsApplyPageParam.getSortOrder());
            queryWrapper.orderBy(true, goodsApplyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(goodsApplyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(GoodsApply::getSn);
        }
        Page<GoodsApply> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        if(CollUtil.isNotEmpty(result.getRecords())){
            List<String> ids = result.getRecords().stream().map(GoodsApply::getId).collect(Collectors.toList());
            List<GoodsItem> itemList = goodsItemService.list(new LambdaQueryWrapper<GoodsItem>().in(GoodsItem::getGoodsApplyId,ids));
            result.getRecords().forEach(item->{
                List<String> applyList = itemList.stream().filter(x->item.getId().equals(x.getGoodsApplyId()) && GoodsTypeEnum.GOODS_APPLY.getValue().equals(x.getType())).map(x->x.getGoodName()).collect(Collectors.toList());
                List<String> backList = itemList.stream().filter(x->item.getId().equals(x.getGoodsApplyId()) && GoodsTypeEnum.GOODS_BACK.getValue().equals(x.getType())).map(x->x.getGoodName()).collect(Collectors.toList());
                item.setGoodsApplyStr(StrUtil.join("，",applyList));
                item.setGoodsBackStr(StrUtil.join("，",backList));
                item.setGoodsApplyJson(null);
                item.setGoodsBackJson(null);
            });
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(GoodsApplyAddParam goodsApplyAddParam) {
        GoodsApply goodsApply = BeanUtil.toBean(goodsApplyAddParam, GoodsApply.class);

        //走审批流程
        String processInstanceId = goodsApplyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            goodsApply.setId(id);
            goodsApply.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            goodsApply.setModelId(modelId);
            goodsApply.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            flwCommonApi.save(modelId, BizCommonEnum.HR_GOODS_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            goodsApply.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }

        goodsApply.setOptTime(new Date());
        goodsApply.setGoodsApplyJson(JSONUtil.toJsonStr(goodsApplyAddParam.getGoodsApplyList()));
        goodsApply.setGoodsBackJson(JSONUtil.toJsonStr(goodsApplyAddParam.getGoodsBackList()));
        this.save(goodsApply);

        //处理物资清单
        List<GoodsItem> goodsItemList = new ArrayList<>();
        if(CollUtil.isNotEmpty(goodsApplyAddParam.getGoodsApplyList())){
            List<GoodsItem> goodsApplyList = BeanUtil.copyToList(goodsApplyAddParam.getGoodsApplyList(), GoodsItem.class);
            goodsApplyList.forEach(x->{
                x.setType(GoodsTypeEnum.GOODS_APPLY.getValue());
                x.setGoodsApplyId(goodsApply.getId());
            });
            goodsItemList.addAll(goodsApplyList);
        }
        if(CollUtil.isNotEmpty(goodsApplyAddParam.getGoodsBackList())){
            List<GoodsItem> goodsBackList = BeanUtil.copyToList(goodsApplyAddParam.getGoodsBackList(), GoodsItem.class);
            goodsBackList.forEach(x->{
                x.setType(GoodsTypeEnum.GOODS_BACK.getValue());
                x.setGoodsApplyId(goodsApply.getId());
            });
            goodsItemList.addAll(goodsBackList);
        }
        if(CollUtil.isNotEmpty(goodsItemList)) {
            goodsItemService.saveBatch(goodsItemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(GoodsApplyEditParam goodsApplyEditParam) {
        String id = goodsApplyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(goodsApplyEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(goodsApplyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                goodsApplyEditParam.setId(id);
            }
        }

        GoodsApply goodsApply = this.queryEntity(goodsApplyEditParam.getId());
        BeanUtil.copyProperties(goodsApplyEditParam, goodsApply);
        goodsApply.setGoodsApplyJson(JSONUtil.toJsonStr(goodsApplyEditParam.getGoodsApplyList()));
        goodsApply.setGoodsBackJson(JSONUtil.toJsonStr(goodsApplyEditParam.getGoodsBackList()));
        this.updateById(goodsApply);

        //处理物资清单
        if(CollUtil.isNotEmpty(goodsApplyEditParam.getGoodsApplyList())){
            //本次传入的数据
            List<GoodsItem> goodsItemList = BeanUtil.copyToList(goodsApplyEditParam.getGoodsApplyList(),GoodsItem.class);
            goodsItemList.forEach(x->{
                x.setGoodsApplyId(goodsApply.getId());
                x.setType(GoodsTypeEnum.GOODS_APPLY.getValue());
            });
            //获取历史数据
            List<GoodsItem> oldItemList = goodsItemService.list(new QueryWrapper<GoodsItem>().lambda().eq(GoodsItem::getGoodsApplyId,goodsApply.getId())
                    .eq(GoodsItem::getType,GoodsTypeEnum.GOODS_APPLY.getValue()));
            List<String> oldIds = oldItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<GoodsItem> addList = goodsItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<GoodsItem> updateList = goodsItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<GoodsItem> removeList = oldItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                goodsItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                goodsItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                goodsItemService.removeByIds(removeList);
            }
        }
        //处理退回清单
        if(CollUtil.isNotEmpty(goodsApplyEditParam.getGoodsBackList())){
            //本次传入的数据
            List<GoodsItem> goodsItemList = BeanUtil.copyToList(goodsApplyEditParam.getGoodsBackList(),GoodsItem.class);
            goodsItemList.forEach(x->{
                x.setGoodsApplyId(goodsApply.getId());
                x.setType(GoodsTypeEnum.GOODS_BACK.getValue());
            });
            //获取历史数据
            List<GoodsItem> oldItemList = goodsItemService.list(new QueryWrapper<GoodsItem>().lambda().eq(GoodsItem::getGoodsApplyId,goodsApply.getId())
                    .eq(GoodsItem::getType,GoodsTypeEnum.GOODS_BACK.getValue()));
            List<String> oldIds = oldItemList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<GoodsItem> addList = goodsItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<GoodsItem> updateList = goodsItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<GoodsItem> removeList = oldItemList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                goodsItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                goodsItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                goodsItemService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<GoodsApplyIdParam> goodsApplyIdParamList) {
        List<String> delIds = CollStreamUtil.toList(goodsApplyIdParamList, GoodsApplyIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            goodsItemService.remove(new LambdaQueryWrapper<GoodsItem>().in(GoodsItem::getGoodsApplyId,delIds));
        }
    }

    @Override
    public GoodsApply detail(GoodsApplyIdParam goodsApplyIdParam) {
        return this.queryEntity(goodsApplyIdParam.getId());
    }

    @Override
    public GoodsApply queryEntity(String id) {
        GoodsApply goodsApply = this.getById(id);
        if(ObjectUtil.isEmpty(goodsApply)) {
            throw new CommonException("物资领用申请不存在，id值为：{}", id);
        }
        return goodsApply;
    }

    @Override
    public void download(DownLoadParam param, HttpServletResponse response) {
        try {
            String dateTmpStr = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN);
            String fileName = StrUtil.join("_",param.getSn(),dateTmpStr+".pdf");
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("sn",param.getSn());
            paramMap.put("deptName",param.getDeptName());
            paramMap.put("optName",param.getOptName());
            paramMap.put("optTime",DateUtil.format(param.getOptTime(),DatePattern.NORM_DATETIME_PATTERN));
            // 取实际审批人签字
            dealSign(param);
            paramMap.put("deptLeader",param.getDeptLeader());
            paramMap.put("xzLeader",param.getXzLeader());
            String itemStr = "";
            for (GoodsItemEditParam item : param.getGoodsApplyList()) {
                itemStr += item.getGoodName() + "（数量：" + item.getGoodNum() + "，单价："+ item.getGoodPrice() + "元，总价：" + item.getGoodTotal() + "元）；";
            }
            paramMap.put("itemStr",itemStr);
            byte[] bytes = MyFileUtil.genPdfFile(paramMap,"goodsTemplate.docx");

//            //参数转换
//            List<Map<String, String>> mapList = param.getCommentList().stream()
//                    .map(comment -> {
//                        Map<String, String> map = new HashMap<>();
//                        BeanUtils.copyProperties(comment,map); // 注意参数顺序
//                        return map;
//                    })
//                    .collect(Collectors.toList());

            byte[] newBytes = addTableToPdf(bytes,param.getCommentList());
            //下载
            CommonDownloadUtil.download(fileName, newBytes, response);
        } catch (Exception e) {
            log.error(">>> 下载物资领用申请单失败：", e);
            try {
                CommonResponseUtil.renderError(response, "下载物资领用申请单失败");
            } catch (IOException ex) {
                log.error("异常："+ex.getMessage());
            }
        }
    }






    private void dealSign(DownLoadParam param) {
        List<FlwComment> commentList = param.getCommentList();
        if(CollUtil.isEmpty(commentList)) {
            return;
        }
        // 仅保留发起节点、审批同意节点、自动通过节点的评论
        commentList = commentList.stream().filter(x -> "PASS".equals(x.getOperateType())
                        || "START".equals(x.getOperateType()) || "AUTO_COMPLETE".equals(x.getOperateType()))
                .collect(Collectors.toList());
        if(CollUtil.isEmpty(commentList)){
            return;
        }
        // 将评论转为节点名称->审核人名称的映射（多个审批人用逗号分隔）
        Map<String, List<FlwComment>> commentMap = commentList.stream().collect(Collectors.groupingBy(FlwComment::getTaskName));
        Map<String, String> map = commentMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(FlwComment::getUserName)
                                .filter(Objects::nonNull)
                                .distinct()
                                .collect(Collectors.joining(","))
                ));
        if(ObjectUtil.isNotEmpty(map.get("部门负责人"))){
            param.setDeptLeader(map.get("部门负责人"));
        }
        if(ObjectUtil.isNotEmpty(map.get("行政经理"))){
            param.setXzLeader(map.get("行政经理"));
        }
    }


    /**
     * pdf 添加 table
     * @param pdfBytes
     * @param commentList
     * @return
     */
    public byte[] addTableToPdf(byte[] pdfBytes, List<FlwComment> commentList){
        if(CollUtil.isEmpty(commentList)) return pdfBytes;
        try {
            ByteArrayOutputStream newPdfOutputStream = new ByteArrayOutputStream();

            // 1. 打开已有 PDF
            com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(new ByteArrayInputStream(pdfBytes));
            PdfWriter writer = new PdfWriter(newPdfOutputStream);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);
            Document document = new Document(pdfDoc);

            // 2. 添加表格（7列）
            Table table = new Table(UnitValue.createPercentArray(7)).setWidth(520);

            InputStream fontStream = POICacheManager.getFile("SIMSUN.ttf");
            PdfFont font = PdfFontFactory.createFont(IoUtil.readBytes(fontStream), PdfEncodings.IDENTITY_H);
            table.setFont(font);
            table.setTextAlignment(TextAlignment.CENTER);

            // 表头
            table.addHeaderCell("节点名称");
            table.addHeaderCell("操作人");
            table.addHeaderCell("操作类型");
            table.addHeaderCell("意见");
            table.addHeaderCell("耗时");
            table.addHeaderCell("附件");
            table.addHeaderCell("操作时间");

            // 添加数据行
            for (FlwComment row : commentList) {
                table.addCell(ObjectUtil.isNotEmpty(row.getTaskName()) ? row.getTaskName() : "");
                table.addCell(ObjectUtil.isNotEmpty(row.getUserName()) ? row.getUserName() : "");
                table.addCell(ObjectUtil.isNotEmpty(row.getOperateText()) ? row.getOperateText() : "");
                table.addCell(ObjectUtil.isNotEmpty(row.getComment()) ? row.getComment() : "");
                table.addCell(ObjectUtil.isNotEmpty(row.getApproveExpendHours()) ? row.getApproveExpendHours() : "");
                table.addCell("无");
                table.addCell(ObjectUtil.isNotEmpty(row.getApproveTime()) ? row.getApproveTime() : "");
            }

            // 3. 插入到文档末尾
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            document.add(table);

            // 4. 关闭资源
            document.close();

            return newPdfOutputStream.toByteArray();
        }catch (Exception e){
            log.error(e.getMessage());
            return pdfBytes;
        }
    }
}
