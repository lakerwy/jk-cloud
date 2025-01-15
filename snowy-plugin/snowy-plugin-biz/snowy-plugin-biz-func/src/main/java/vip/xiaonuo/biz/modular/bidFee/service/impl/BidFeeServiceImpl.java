package vip.xiaonuo.biz.modular.bidFee.service.impl;

import cn.dev33.satoken.stp.StpUtil;
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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.bidFee.param.*;
import vip.xiaonuo.biz.modular.bidFee.vo.BidFeeExcelData;
import vip.xiaonuo.biz.modular.bidFee.vo.BizProjectVo;
import vip.xiaonuo.biz.modular.prj.expense.utils.MergeCellHandler;
import vip.xiaonuo.biz.modular.prj.expense.vo.MergeCell;
import vip.xiaonuo.biz.modular.task.service.SysOrgService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.bidFee.entity.BidFee;
import vip.xiaonuo.biz.modular.bidFee.mapper.BidFeeMapper;
import vip.xiaonuo.biz.modular.bidFee.service.BidFeeService;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.dev.api.DevDictApi;
import vip.xiaonuo.flw.api.FlwCommonApi;
import vip.xiaonuo.flw.api.FlwProcessMonitorApi;
import vip.xiaonuo.sys.api.PartnerInfoApi;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 投标费用审批Service接口实现类
 *
 * @author pzh
 * @date  2024/12/26 16:37
 **/
@DS("slave_1")
@Service
public class BidFeeServiceImpl extends ServiceImpl<BidFeeMapper, BidFee> implements BidFeeService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    FlwCommonApi flwCommonApi;

    @Resource
    private BidFeeMapper bidFeeMapper;

    @Resource
    DevDictApi devDictApi;

    @Resource
    SysOrgService sysOrgService;

    @Resource
    SysUserApi sysUserApi;

    @Resource
    PartnerInfoApi partnerInfoApi;

    @Resource
    FlwProcessMonitorApi flwProcessMonitorApi;

    public static final String DICT_LABEL_CATEGORY = "BIZ";

    public static final String DICT_LABEL_BID_MANAGER = "投标专员";

    @Override
    public Page<BidFee> page(BidFeePageParam bidFeePageParam) {
        QueryWrapper<BidFee> queryWrapper = new QueryWrapper<>();
        // 关键词
        if(ObjectUtil.isNotEmpty(bidFeePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BidFee::getApplyUnit,bidFeePageParam.getSearchKey())
                    .or().like(BidFee::getLeader,bidFeePageParam.getSearchKey())
                    .or().like(BidFee::getDept,bidFeePageParam.getSearchKey()));
        }
        // 资金支付类型
        if(ObjectUtil.isNotEmpty(bidFeePageParam.getPaymentType())){
            queryWrapper.lambda().eq(BidFee::getPaymentType,bidFeePageParam.getPaymentType());
        }
        // 申请时间
        if(ObjectUtil.isNotEmpty(bidFeePageParam.getApplyDate())){
            LocalDate date = LocalDate.parse(bidFeePageParam.getApplyDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            queryWrapper.lambda().between(BidFee::getApplyDate, startOfDay, endOfDay);
        }
        // 默认只展示审核中和已完成的数据
        if(ObjectUtil.isNotEmpty(bidFeePageParam.getStatus())){
            queryWrapper.lambda().eq(BidFee::getStatus,bidFeePageParam.getStatus());
        }else {
            queryWrapper.lambda().in(BidFee::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        }
        if(ObjectUtil.isAllNotEmpty(bidFeePageParam.getSortField(), bidFeePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bidFeePageParam.getSortOrder());
            queryWrapper.orderBy(true, bidFeePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bidFeePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BidFee::getCreateTime);
        }
        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BidFee::getDataScopeId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BidFee::getCreateUser, StpUtil.getLoginIdAsString());
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BidFeeAddParam bidFeeAddParam) {
        BidFee bidFee = BeanUtil.toBean(bidFeeAddParam, BidFee.class);
        //走审批流程
        String processInstanceId = bidFeeAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bidFee.setId(id);
            bidFee.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bidFee.setModelId(modelId);
            bidFee.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_BID_FEE_APPLY.getBizTable(),id,processInstanceId,dbName);
        }else{
            bidFee.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bidFee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BidFeeEditParam bidFeeEditParam) {
        String id = bidFeeEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bidFeeEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bidFeeEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bidFeeEditParam.setId(id);
            }
        }
        BidFee bidFee = this.queryEntity(bidFeeEditParam.getId());
        BeanUtil.copyProperties(bidFeeEditParam, bidFee);
        this.updateById(bidFee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BidFeeIdParam> bidFeeIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bidFeeIdParamList, BidFeeIdParam::getId));
    }

    @Override
    public BidFee detail(BidFeeIdParam bidFeeIdParam) {
        return this.queryEntity(bidFeeIdParam.getId());
    }

    @Override
    public BidFee queryEntity(String id) {
        BidFee bidFee = this.getById(id);
        if(ObjectUtil.isEmpty(bidFee)) {
            throw new CommonException("投标费用审批不存在，id值为：{}", id);
        }
        return bidFee;
    }

    @Override
    public Page<BizProjectVo> projectPage(BizProjectPageParam bizProjectPageParam) {
        QueryWrapper<BidFee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getProjectSn())){
            queryWrapper.like("a.PROJECT_SN",bizProjectPageParam.getProjectSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getName())){
            queryWrapper.like("a.NAME",bizProjectPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSn())){
            queryWrapper.like("a.SN",bizProjectPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizProjectPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NAME", bizProjectPageParam.getSearchKey())
                    .or().like("a.PROJECT_SN", bizProjectPageParam.getSearchKey())
                    .or().like("a.SN", bizProjectPageParam.getSearchKey())
            );
        }
        // 校验数据范围
//        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
//        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
//            queryWrapper.in("b.DATA_SCOPE_ID", loginUserDataScope);
//        } else {
//            queryWrapper.eq("b.CREATE_USER", StpUtil.getLoginIdAsString());
//        }
        Page<BizProjectVo> bizProject = bidFeeMapper.getProjectPage(CommonPageRequest.defaultPage(), queryWrapper);
        Map<String,String> bidManagerMap = getBidManagerMap();
        for(BizProjectVo bizProjectVo : bizProject.getRecords()){
            // 投标专员
            bizProjectVo.setBidMangerId(bidManagerMap.get(bizProjectVo.getBidManger()));
//            // 部门负责人
//            if("总部".equals(bizProjectPageParam.getApplyUnit())){ // 总部
//                BizSysOrgVo sysOrg = sysOrgService.getOrgByName(bizProjectVo.getDept());
//                bizProjectVo.setDeptId(sysOrg.getOrgId());
//                bizProjectVo.setManagerId(sysOrg.getDirectorId());
//            }
//            // 分公司负责人
//            else if("分公司".equals(bizProjectPageParam.getApplyUnit())){ // 分公司
//                String region = bizProjectVo.getRegion();
//                if(ObjectUtil.isNotEmpty(region)){
//                    List<JSONObject> partnerVos = partnerInfoApi.getByRegion(region);
//                    if(ObjectUtil.isNotEmpty(partnerVos)){
//                        JSONObject partnerVo = partnerVos.get(0);
//                        bizProjectVo.setDeptId(partnerVo.getStr("orgId"));
//                        bizProjectVo.setManagerId(partnerVo.getStr("regionalManager"));
//                    }
//                }
//            }
//            if(ObjectUtil.isNotEmpty(bizProjectVo.getManagerId())){
//                JSONObject json = sysUserApi.getUserByIdWithoutException(bizProjectVo.getManagerId());
//                bizProjectVo.setManager(ObjectUtil.isNotEmpty(json) ? json.getStr("name") : null);
//            }
            if(ObjectUtil.isNotEmpty(bizProjectVo.getBidMangerId())){
                JSONObject json = sysUserApi.getUserByIdWithoutException(bizProjectVo.getBidMangerId());
                bizProjectVo.setBidMangerStatus(json.getStr("userStatus"));
            }
        }
        return bizProject;
    }

    private Map<String,String> getBidManagerMap(){
        List<JSONObject> dictList = devDictApi.list("", DICT_LABEL_CATEGORY);
        JSONObject first = dictList.stream().filter(x -> DICT_LABEL_BID_MANAGER.equals(x.getStr("dictLabel"))).findFirst().orElse(null);
        Map<String, String> map = new HashMap<>();
        if(ObjectUtil.isNotNull(first)){
            String parentId = first.getStr("id");
            map = dictList.stream().filter(x -> parentId.equals(x.getStr("parentId"))).collect(Collectors.toMap(
                json -> json.getStr("dictLabel"),
                json -> json.getStr("dictValue"),
                // 解决键冲突（如果有多个相同的 id）
                (existing, replacement) -> existing // 如果有重复的键，保留第一个遇到的值
            ));
        }
        return map;
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
            String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR +  "BID_FEE_" + dateStr + ".xlsx";
            ClassPathResource classPathResource = new ClassPathResource("BidFeeTemplate.xlsx");
            inputStream = classPathResource.getStream();
            BidFeeExcelData excelData = com.alibaba.fastjson2.JSONObject.parseObject(excelParam.getParamJson(), BidFeeExcelData.class);
            excelData.setCommentList(excelParam.getCommentList());
            // 使用 EasyExcel 填充数据
            excelWriter = EasyExcel.write(outputFileName).withTemplate(inputStream).build();
            // 获取评论列表
            setCommentList(excelData);
            // 解析并设置打印数据
            setData(excelData);

            List<MergeCell> feeMergeList = new ArrayList<>();
            int firstRow = 17;
            feeMergeList.add(new MergeCell(firstRow, excelData.getCommentList().size() + firstRow - 1, 0, 2));
            feeMergeList.add(new MergeCell(firstRow, excelData.getCommentList().size() + firstRow - 1, 3, 5));
            feeMergeList.add(new MergeCell(firstRow, excelData.getCommentList().size() + firstRow - 1, 6, 8));
            feeMergeList.add(new MergeCell(firstRow, excelData.getCommentList().size() + firstRow - 1, 9, 11));
            WriteSheet sheet1 = EasyExcel.writerSheet(0).registerWriteHandler(new MergeCellHandler(feeMergeList)).build();
//            WriteSheet sheet1 = EasyExcel.writerSheet(0).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(excelData, sheet1);
            excelWriter.fill(new FillWrapper("commentList", excelData.getCommentList()), fillConfig, sheet1);
            excelWriter.finish();

            // 写入Excel
//            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
//            excelWriter.fill(excelData, sheet1);
//            excelWriter.fill(new FillWrapper("commentList", excelData.getCommentList()), fillConfig, sheet1);
//            excelWriter.finish();

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
    private void setCommentList(BidFeeExcelData excelData) {
        if(CollUtil.isNotEmpty(excelData.getCommentList())){
            return;
        }
        List<BidFeeExcelData.FlwProcessComment> commentList = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(excelData.getProcessInstanceId())){
            commentList = getFlwProcessComments(excelData.getProcessInstanceId());
        }else {
            if(ObjectUtil.isNotEmpty(excelData.getId())){
                BidFee bidFee = this.getById(excelData.getId());
                if(ObjectUtil.isNotEmpty(bidFee)){
                    commentList = getFlwProcessComments(bidFee.getProcessInstanceId());
                }
            }
        }
        excelData.setCommentList(commentList);
    }

    // 获取评论列表
    private List<BidFeeExcelData.FlwProcessComment> getFlwProcessComments(String processInstanceId) {
        List<BidFeeExcelData.FlwProcessComment> commentList = new ArrayList<>();
        // 查询审批评论列表
        if(StrUtil.isNotBlank(processInstanceId)){
            JSONObject jsonObject = flwProcessMonitorApi.monitorDetail(processInstanceId);
            if(ObjectUtil.isNotEmpty(jsonObject)){
                JSONArray jsonArray = jsonObject.getJSONArray("commentList");
                if(CollUtil.isNotEmpty(jsonArray)){
                    commentList = JSONUtil.toList(jsonArray.toString(), BidFeeExcelData.FlwProcessComment.class);
                }
            }
        }
        return commentList;
    }

    // 解析并设置打印数据
    private void setData(BidFeeExcelData excelData) {
        if("公司垫付".equals(excelData.getPaymentType())){
            excelData.setPaymentDesc("-");
        }
        if(CollUtil.isEmpty(excelData.getCommentList())){
            return;
        }
        // 如果投标专员节点为空自动通过，则审批评论显示为"-"
        excelData.getCommentList().forEach(x -> {
            if("投标专员".equals(x.getTaskName()) && "AUTO_COMPLETE".equals(x.getOperateType()) && x.getComment().contains("该节点审批人为空")){
                x.setUserName("-");
            }
        });
        // 过滤出同意申请的评论(包括发起节点、审批同意节点、自动通过节点)
        List<BidFeeExcelData.FlwProcessComment> passCommentList = BeanUtil.copyToList(excelData.getCommentList(),BidFeeExcelData.FlwProcessComment.class);
        passCommentList = passCommentList.stream().filter(x -> "PASS".equals(x.getOperateType())
                || "START".equals(x.getOperateType()) || "AUTO_COMPLETE".equals(x.getOperateType()))
                .collect(Collectors.toList());
        if(CollUtil.isEmpty(passCommentList)){
            return;
        }
        // 将评论转为节点名称->审核人名称的映射
        Map<String, List<BidFeeExcelData.FlwProcessComment>> commentMap = passCommentList.stream()
                .collect(Collectors.groupingBy(BidFeeExcelData.FlwProcessComment::getTaskName));
        Map<String, String> map = commentMap.entrySet().stream()
        .collect(Collectors.toMap(
                Map.Entry::getKey, // 保留原始键
                entry -> entry.getValue().stream()
                        .map(BidFeeExcelData.FlwProcessComment::getUserName)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.joining(","))
        ));
        // 核准人
        // 1.公司垫付类型，则为董事长姓名
        // 2.代收代付类型，则为财务总监姓名
        if("公司垫付".equals(excelData.getPaymentType())){
            excelData.setApprovedBy(map.get("董事长签批"));
        }
        if("代收代付".equals(excelData.getPaymentType())){
            excelData.setApprovedBy(map.get("财务总监"));
        }
        // 会计
        excelData.setAccounting(map.get("核算经理"));
        // 出纳
        excelData.setCashier(map.get("出纳"));
        // 招投标部
        String bidManger = map.get("投标专员");
        bidManger = "-".equals(bidManger) ? null : bidManger;
        String bidDept = Stream.of(bidManger, map.get("招投标部负责人"))
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.joining(","));
        excelData.setBidDept(bidDept);
        // 部门负责人
        excelData.setManager(map.get("部门负责人"));
        // 发起人
        excelData.setInitor(map.get("发起申请"));
    }
}
