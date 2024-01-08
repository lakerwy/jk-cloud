/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.cad.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.entity.*;
import vip.xiaonuo.biz.modular.cad.enums.CadProductEnum;
import vip.xiaonuo.biz.modular.cad.enums.ProofreadAuditStatusEnum;
import vip.xiaonuo.biz.modular.cad.mapper.*;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.*;
import vip.xiaonuo.biz.modular.cad.vo.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CAD-专业表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/11/29 10:36
 **/
@DS("slave_1")
@Service
public class CadProjectServiceImpl extends ServiceImpl<CadProjectMapper, CadProject> implements CadProjectService {

    @Resource
    private CadProjectMapper cadProjectMapper;
    @Resource
    private CadProductMapper cadProductMapper;
    @Resource
    private CadProductService cadProductService;
    @Resource
    private CadStorageMapper cadStorageMapper;
    @Resource
    private CadProofreadMapper cadProofreadMapper;
    @Resource
    private CadFileMapper cadFileMapper;

    @Resource
    private CadStageService cadStageService;

    @Resource
    private CadSubItemService cadSubItemService;

    @Resource
    private CadProfessionService cadProfessionService;

    @Override
    public Page<CadProjectVo> page(CadProjectPageParam cadProjectPageParam) {
        QueryWrapper<CadProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getNo())) {
            queryWrapper.like("a.NO", cadProjectPageParam.getNo());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getName())) {
            queryWrapper.like("a.NAME", cadProjectPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getManager())) {
            queryWrapper.like("b.NAME", cadProjectPageParam.getManager());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getUnit())) {
            queryWrapper.like("a.UNIT", cadProjectPageParam.getUnit());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getAddress())) {
            queryWrapper.like("a.ADDRESS", cadProjectPageParam.getAddress());
        }
        if(ObjectUtil.isNotEmpty(cadProjectPageParam.getSearchKey())){
            queryWrapper.and(q->q.like("a.NO",cadProjectPageParam.getSearchKey())
                    .or().like("a.NAME",cadProjectPageParam.getSearchKey())
                    .or().like("b.NAME",cadProjectPageParam.getSearchKey())
                    .or().like("a.UNIT",cadProjectPageParam.getSearchKey())
                    .or().like("a.ADDRESS",cadProjectPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(cadProjectPageParam.getSortField(), cadProjectPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadProjectPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadProjectPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadProjectPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadProject::getId);
        }
        return cadProjectMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadProjectAddParam cadProjectAddParam) {
        CadProject cadProject = BeanUtil.toBean(cadProjectAddParam, CadProject.class);
        this.save(cadProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadProjectEditParam cadProjectEditParam) {
        CadProject cadProject = this.queryEntity(cadProjectEditParam.getId());
        BeanUtil.copyProperties(cadProjectEditParam, cadProject);
        this.updateById(cadProject);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadProjectIdParam> cadProjectIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadProjectIdParamList, CadProjectIdParam::getId));
    }

    @Override
    public CadProjectVo detail(CadProjectIdParam cadProjectIdParam) {
        return cadProjectMapper.getById(cadProjectIdParam.getId());
    }

    @Override
    public CadProject queryEntity(String id) {
        CadProject cadProject = this.getById(id);
        if(ObjectUtil.isEmpty(cadProject)) {
            throw new CommonException("CAD-专业表不存在，id值为：{}", id);
        }
        return cadProject;
    }

    @Override
    public List<StageSubItemVo> stageSubItem(String prjId) {
        List<StageSubItemVo> result = new ArrayList<>();
        List<CadSubItemVo> subItemVoList = cadProjectMapper.getSubItemByPrjId(prjId);
        Map<String,List<CadSubItemVo>> groupByStage = subItemVoList.stream().sorted(Comparator.comparing(x->x.getStageIndex())).collect(Collectors.groupingBy(x->x.getStageId()));
        for (Map.Entry<String,List<CadSubItemVo>> item:groupByStage.entrySet()) {
            StageSubItemVo stageSubItemVo = new StageSubItemVo();
            stageSubItemVo.setStageId(item.getKey());
            stageSubItemVo.setStageName(item.getValue().get(0).getStageName());
            stageSubItemVo.setSubItemList(item.getValue());
            result.add(stageSubItemVo);
        }
        return result;
    }

    @Override
    public List<CadProjectVo> projectsByUserId(String cadUserId) {
        return cadProjectMapper.getProjectByUserId(cadUserId);
    }

    @Override
    public List<Tree<String>> prjDesignTree(MyFileTreeParam myFileTreeParam) {
        //获取项目目录结构
        QueryWrapper<CadProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.ID",myFileTreeParam.getPrjId());
        queryWrapper.orderByAsc("e.SORT_INDEX").orderByAsc("f.SORT_INDEX");
        List<PrjStageSubProfVo> prjInfoList =  cadProjectMapper.getPrjStageSubProf(queryWrapper);

        //获取该项目下自定义文件目录结构
        QueryWrapper<CadFile> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("file.PRJ_ID",myFileTreeParam.getPrjId());
        fileQueryWrapper.eq("file.DELETE_FLAG","NOT_DELETE");
        List<CadFileVo> prjFileList = cadProjectMapper.getPrjFile(fileQueryWrapper);

        //构建项目+自定义文件目录树状结构
        List<CadFileVo> treeFileList = this.getFileTree(prjInfoList,prjFileList);
        List<TreeNode<String>> treeNodeList = treeFileList.stream().map(fileTree -> {
            // 将对象转为 JSONObject
            JSONObject jsonObject = new JSONObject(BeanUtil.beanToMap(fileTree, false, false));
            // 使用 JSONObject 生成 Map，保留属性为 null 的值
            return new TreeNode<>(fileTree.getFId(), fileTree.getFParentId(), fileTree.getFName(), null)
                    .setExtra(jsonObject);
        }).collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, myFileTreeParam.getPrjId());
    }

    @Override
    public List<Tree<String>> prjSupplyTree(MyFileTreeParam myFileTreeParam) {
        //1. 获取项目目录结构
        QueryWrapper<CadProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.PRJ_ID",myFileTreeParam.getPrjId());
        queryWrapper.eq("a.TYPE", CadProductEnum.SUPPLY.getValue());
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.orderByAsc("c.SORT_INDEX").orderByAsc("e.SORT_INDEX");
        List<PrjStageSubProfVo> prjInfoList =  cadProductMapper.getPrjStageSubProf2(queryWrapper);
        //2. 获取提资文件
        QueryWrapper<CadProduct> query = new QueryWrapper<>();
        query.lambda().eq(CadProduct::getPrjId,myFileTreeParam.getPrjId());
        query.lambda().eq(CadProduct::getType,CadProductEnum.SUPPLY.getValue());
        List<CadProduct> productList = cadProductService.list(query);
        //3. 获取本项目的存储目录集合
        QueryWrapper<CadStorage> storageQuery = new QueryWrapper<>();
        storageQuery.eq("storage.PRJ_ID",myFileTreeParam.getPrjId());
        storageQuery.eq("storage.DELETE_FLAG","NOT_DELETE");
        List<CadFileVo> storageList = cadStorageMapper.getPrjStorage(storageQuery);
        //4. 进一步筛选，获取指定文件的存储目录结构
        List<String> ids = productList.stream().filter(x->ObjectUtil.isNotEmpty(x.getStorageId())).map(x->x.getStorageId()).distinct().collect(Collectors.toList());
        List<CadFileVo> prjFileList = getParentList(storageList,ids,true);
        //5. 构建项目+自定义文件目录树状结构
        List<CadFileVo> treeFileList = this.getFileTree(prjInfoList,prjFileList);
        List<TreeNode<String>> treeNodeList = treeFileList.stream().map(fileTree -> {
            // 将对象转为 JSONObject
            JSONObject jsonObject = new JSONObject(BeanUtil.beanToMap(fileTree, false, false));
            // 使用 JSONObject 生成 Map，保留属性为 null 的值
            return new TreeNode<>(fileTree.getFId(), fileTree.getFParentId(), fileTree.getFName(), null)
                    .setExtra(jsonObject);
        }).collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, myFileTreeParam.getPrjId());
    }

    @Override
    public List<Tree<String>> prjProductTree(MyFileTreeParam myFileTreeParam) {
        //1. 获取项目目录结构
        QueryWrapper<CadProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.PRJ_ID",myFileTreeParam.getPrjId());
        queryWrapper.eq("a.TYPE", CadProductEnum.PRODUCT.getValue());
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.orderByAsc("c.SORT_INDEX").orderByAsc("e.SORT_INDEX");
        List<PrjStageSubProfVo> prjInfoList =  cadProductMapper.getPrjStageSubProf2(queryWrapper);
        //2. 获取成品文件
        QueryWrapper<CadProduct> query = new QueryWrapper<>();
        query.lambda().eq(CadProduct::getPrjId,myFileTreeParam.getPrjId());
        query.lambda().eq(CadProduct::getType,CadProductEnum.PRODUCT.getValue());
        List<CadProduct> productList = cadProductService.list(query);
        //3. 获取本项目的存储目录集合
        QueryWrapper<CadStorage> storageQuery = new QueryWrapper<>();
        storageQuery.eq("storage.PRJ_ID",myFileTreeParam.getPrjId());
        storageQuery.eq("storage.DELETE_FLAG","NOT_DELETE");
        List<CadFileVo> storageList = cadStorageMapper.getPrjStorage(storageQuery);
        //4. 进一步筛选，获取指定文件的存储目录结构
        List<String> ids = productList.stream().filter(x->ObjectUtil.isNotEmpty(x.getStorageId())).map(x->x.getStorageId()).distinct().collect(Collectors.toList());
        List<CadFileVo> prjFileList = getParentList(storageList,ids,true);
        //5. 构建项目+自定义文件目录树状结构
        List<CadFileVo> treeFileList = this.getFileTree(prjInfoList,prjFileList);
        List<TreeNode<String>> treeNodeList = treeFileList.stream().map(fileTree -> {
            // 将对象转为 JSONObject
            JSONObject jsonObject = new JSONObject(BeanUtil.beanToMap(fileTree, false, false));
            // 使用 JSONObject 生成 Map，保留属性为 null 的值
            return new TreeNode<>(fileTree.getFId(), fileTree.getFParentId(), fileTree.getFName(), null)
                    .setExtra(jsonObject);
        }).collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, myFileTreeParam.getPrjId());
    }

    @Override
    public CadAuditInfo prjAuditInfo(MyFileTreeParam myFileTreeParam) {
        CadAuditInfo result = new CadAuditInfo();
        QueryWrapper<CadProofread> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("proofread.PRJ_ID",myFileTreeParam.getPrjId());
        queryWrapper.eq("proofread.AUDITOR",myFileTreeParam.getUserId());
        queryWrapper.eq("proofread.DELETE_FLAG","NOT_DELETE");
        queryWrapper.orderByDesc("proofread.CREATE_TIME");
        List<CadProofreadVo> myProofreadList = cadProofreadMapper.getPrjProofread(queryWrapper);
        result.setReceiveList(myProofreadList.stream().filter(x->ProofreadAuditStatusEnum.PRE_AUDIT.getValue().equals(x.getStatus())).collect(Collectors.toList()));
        result.setDealList(myProofreadList.stream().filter(x->!ProofreadAuditStatusEnum.PRE_AUDIT.getValue().equals(x.getStatus())).collect(Collectors.toList()));
        return result;
    }

    @Override
    public List<Tree<String>> getFileParentTree(String prjId,String stageId,String subItemId,String professionId,String fileId,String includeSelf) {
        //获取项目目录信息
        List<PrjStageSubProfVo> prjInfoList = new ArrayList<>();
        PrjStageSubProfVo prjStageSubProfVo = new PrjStageSubProfVo();
        //存在传入的fileId，不在自定义目录下，属于 项目|阶段|子项|专业(子项Id+专业Id) 层次
        if(ObjectUtil.isNotEmpty(prjId))
        {
            CadProject cadProject = this.getById(prjId);
            prjStageSubProfVo.setPrjId(prjId);
            prjStageSubProfVo.setPrjName(cadProject.getName());
        }
        if(ObjectUtil.isNotEmpty(stageId))
        {
            CadStage cadStage = cadStageService.getById(stageId);
            prjStageSubProfVo.setStageId(stageId);
            prjStageSubProfVo.setStageName(cadStage.getName());
        }
        if(ObjectUtil.isNotEmpty(subItemId))
        {
            CadSubItem cadSubItem = cadSubItemService.getById(subItemId);
            prjStageSubProfVo.setSubItemId(subItemId);
            prjStageSubProfVo.setSubItemName(cadSubItem.getName());
        }
        if(ObjectUtil.isNotEmpty(professionId))
        {
            CadProfession cadProfession = cadProfessionService.getById(professionId);
            prjStageSubProfVo.setProfessionId(professionId);
            prjStageSubProfVo.setProfessionName(cadProfession.getName());
        }
        prjInfoList.add(prjStageSubProfVo);

        //1 获取该项目下自定义文件目录结构【本项目全体】
        QueryWrapper<CadFile> fileQueryWrapper = new QueryWrapper<>();
        fileQueryWrapper.eq("file.PRJ_ID",prjId);
        fileQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        List<CadFileVo> prjAllFileList = cadProjectMapper.getPrjFile(fileQueryWrapper);
        //2 获取指定文件的父级
        List<CadFileVo> prjFileList = getParentList(prjAllFileList, Arrays.asList(fileId),StrUtil.isNotEmpty(includeSelf));

        //构建项目+自定义文件目录树状结构
        List<CadFileVo> treeFileList = this.getFileTree(prjInfoList,prjFileList);
        List<TreeNode<String>> treeNodeList = treeFileList.stream().map(fileTree -> {
            // 将对象转为 JSONObject
            JSONObject jsonObject = new JSONObject(BeanUtil.beanToMap(fileTree, false, false));
            // 使用 JSONObject 生成 Map，保留属性为 null 的值
            return new TreeNode<>(fileTree.getFId(), fileTree.getFParentId(), fileTree.getFName(), null)
                    .setExtra(jsonObject);
        }).collect(Collectors.toList());
        return TreeUtil.build(treeNodeList, prjId);
    }


    /**
     * 树状结构数据格式
     *
     * 项目
     *   -- 阶段
     *        -- 子项
     *             -- 专业
     *                  -- 自定义目录 【构建父级Id】
     *
     * @param fileList -- 自定义目录数据源
     * @param prjInfoList -- 项目信息数据源
     * @return
     */
    public List<CadFileVo> getFileTree(List<PrjStageSubProfVo> prjInfoList,List<CadFileVo> fileList){
        List<CadFileVo> result = new ArrayList<>();
        //处理当前项目的阶段|子项|专业目录
        if (CollUtil.isNotEmpty(prjInfoList)){
            //1. 处理项目阶段 - 【fId = 项目阶段id  fParentId = 项目id fName = 阶段名称】
            List<NewObjectVo> stageList = prjInfoList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getStageId()))
                    .map(x -> new NewObjectVo(x.getPrjId(), x.getStageId(), x.getPrjName(), x.getStageName()))
                    .distinct().collect(Collectors.toList());
            stageList.forEach(x->{
                CadFileVo tmp = BeanUtil.copyProperties(x,CadFileVo.class);
                tmp.setFId(x.getStageId());
                tmp.setFParentId(x.getPrjId());
                tmp.setFName(x.getStageName());
                tmp.setOldFId(tmp.getFId());
                result.add(tmp);
            });
            //2. 处理项目子项 - 【fId = 子项id  fParentId = 阶段id fName = 子项名称】
            List<NewObjectVo> subItemList = prjInfoList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getSubItemId()))
                    .map(x -> new NewObjectVo(x.getPrjId(), x.getStageId(), x.getSubItemId(), x.getPrjName(), x.getStageName(), x.getSubItemName()))
                    .distinct().collect(Collectors.toList());
            subItemList.forEach(x->{
                CadFileVo tmp = BeanUtil.copyProperties(x,CadFileVo.class);
                tmp.setFId(x.getSubItemId());
                tmp.setFParentId(x.getStageId());
                tmp.setFName(x.getSubItemName());
                tmp.setOldFId(tmp.getFId());
                result.add(tmp);
            });
            //3. 处理项目子项下专业 - 【fId = 子项id+专业id  fParentId = 子项id fName = 专业名称】
            List<NewObjectVo> professionList = prjInfoList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getProfessionId()))
                    .map(x -> new NewObjectVo(x.getPrjId(), x.getStageId(), x.getSubItemId(),x.getProfessionId(), x.getPrjName(), x.getStageName(), x.getSubItemName(),x.getProfessionName()))
                    .distinct().collect(Collectors.toList());
            professionList.forEach(x->{
                CadFileVo tmp = BeanUtil.copyProperties(x,CadFileVo.class);
                tmp.setFId(StrUtil.join("", x.getSubItemId(),x.getProfessionId()));
                tmp.setFParentId(x.getSubItemId());
                tmp.setFName(x.getProfessionName());
                tmp.setOldFId(tmp.getFId());
                result.add(tmp);
            });
        }
        //处理自定义目录 --
        if(CollUtil.isNotEmpty(fileList)){
            //4. 处理子项专业下的自定义文件 - 【fId = 文件Id  fParentId = 文件父Id fName = 文件名】
            List<CadFileVo> myFileList = fileList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getFId()))
                    .distinct().collect(Collectors.toList());
            result.addAll(myFileList);
        }
        return result;
    }


    /**
     * 根据原始数据集，获取指定id的父级信息
     * @param originDataList 原始数据集
     * @param ids 指定id
     * @param includeSelf 是否包含自身
     * @return
     */
    public List<CadFileVo> getParentList(List<CadFileVo> originDataList, List<String> ids, boolean includeSelf) {
        List<CadFileVo> resultList = CollectionUtil.newArrayList();
        for (String id:ids) {
            execRecursionFindParent(originDataList, id, resultList);
            if(includeSelf){
                CadFileVo self = originDataList.stream().filter(x->x.getFId().equals(id)).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(self) && !resultList.contains(self)) {
                    resultList.add(self);
                }
            }
        }
        return resultList;
    }

    /**
     * 递归获取父级
     * @param originDataList 数据源
     * @param id 指定id
     * @param resultList 结果集
     */
    public void execRecursionFindParent(List<CadFileVo> originDataList, String id, List<CadFileVo> resultList) {
        originDataList.forEach(item -> {
            if(item.getFId().equals(id)) {
                CadFileVo parent = originDataList.stream().filter(x->x.getFId().equals(item.getFParentId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(parent) && !resultList.contains(parent)) {
                    resultList.add(parent);
                }
                execRecursionFindParent(originDataList, item.getFParentId(), resultList);
            }
        });
    }
}
