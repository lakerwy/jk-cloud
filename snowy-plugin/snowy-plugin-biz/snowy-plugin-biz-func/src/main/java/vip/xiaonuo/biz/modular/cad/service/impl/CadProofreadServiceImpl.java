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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.entity.CadFileHistory;
import vip.xiaonuo.biz.modular.cad.entity.CadProduct;
import vip.xiaonuo.biz.modular.cad.entity.CadStorage;
import vip.xiaonuo.biz.modular.cad.enums.CadFileEnum;
import vip.xiaonuo.biz.modular.cad.enums.CadProductEnum;
import vip.xiaonuo.biz.modular.cad.enums.CadProofreadTypeEnum;
import vip.xiaonuo.biz.modular.cad.enums.ProofreadAuditStatusEnum;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.CadFileHistoryService;
import vip.xiaonuo.biz.modular.cad.service.CadProductService;
import vip.xiaonuo.biz.modular.cad.service.CadStorageService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadProofread;
import vip.xiaonuo.biz.modular.cad.mapper.CadProofreadMapper;
import vip.xiaonuo.biz.modular.cad.service.CadProofreadService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CAD-提资|校审记录表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/01 15:36
 **/

@DS("slave_1")
@Service
public class CadProofreadServiceImpl extends ServiceImpl<CadProofreadMapper, CadProofread> implements CadProofreadService {

    @Resource
    private CadFileHistoryService cadFileHistoryService;

    @Resource
    private CadStorageService cadStorageService;

    @Resource
    private CadProductService cadProductService;

    @Override
    public Page<CadProofread> page(CadProofreadPageParam cadProofreadPageParam) {
        QueryWrapper<CadProofread> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadProofreadPageParam.getSortField(), cadProofreadPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadProofreadPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadProofreadPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadProofreadPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadProofread::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadProofreadAddParam cadProofreadAddParam) {
        CadProofread cadProofread = BeanUtil.toBean(cadProofreadAddParam, CadProofread.class);
        this.save(cadProofread);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadProofreadEditParam cadProofreadEditParam) {
        CadProofread cadProofread = this.queryEntity(cadProofreadEditParam.getId());
        BeanUtil.copyProperties(cadProofreadEditParam, cadProofread);
        this.updateById(cadProofread);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadProofreadIdParam> cadProofreadIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadProofreadIdParamList, CadProofreadIdParam::getId));
    }

    @Override
    public CadProofread detail(CadProofreadIdParam cadProofreadIdParam) {
        return this.queryEntity(cadProofreadIdParam.getId());
    }

    @Override
    public CadProofread queryEntity(String id) {
        CadProofread cadProofread = this.getById(id);
        if(ObjectUtil.isEmpty(cadProofread)) {
            throw new CommonException("CAD-提资|校审记录表不存在，id值为：{}", id);
        }
        return cadProofread;
    }

    @Override
    public void supply(StartFlwParam startFlwParam) {
        if(CollUtil.isNotEmpty(startFlwParam.getFileHisList())){
            //1. 新增文件历史记录
            List<CadFileHistory> addHisList = BeanUtil.copyToList(startFlwParam.getFileHisList(),CadFileHistory.class);
            cadFileHistoryService.saveBatch(addHisList);
            //2. 构建成品区存储目录
            String newStorageId = cadStorageService.createStorageFold(startFlwParam.getStorageId(), startFlwParam.getOprId());
            //3. 根据存储目录，存放文件
            List<CadStorage> addStorageFileList = new ArrayList<>();
            addHisList.forEach(x->{
                CadStorage cadStorage = new CadStorage();
                cadStorage.setId(IdWorker.getIdStr());   //手动生成新的主键Id
                cadStorage.setParentId(newStorageId);
                cadStorage.setName(x.getFileName());
                cadStorage.setType(CadFileEnum.FILE.getValue());
                cadStorage.setPrjId(startFlwParam.getPrjId());
                cadStorage.setStageId(startFlwParam.getStageId());
                cadStorage.setSubItemId(startFlwParam.getSubItemId());
                cadStorage.setProfessionId(startFlwParam.getProfessionId());
                cadStorage.setHisId(x.getId());
                cadStorage.setHisDownLoadUrl(x.getDownLoadUrl());
                cadStorage.setCreateUser(startFlwParam.getOprId());
                cadStorage.setOldFId(x.getFileId());
                addStorageFileList.add(cadStorage);
            });
            if(CollUtil.isNotEmpty(addStorageFileList)){
                cadStorageService.saveBatch(addStorageFileList);
            }
            //4. 审批记录
            List<CadProofread> addProofreadList = new ArrayList<>();
            addStorageFileList.forEach(x->{
                startFlwParam.getProofreadList().forEach(y->{
                    CadProofread proofread = new CadProofread();
                    proofread.setPrjId(x.getPrjId());
                    proofread.setStageId(x.getStageId());
                    proofread.setSubItemId(x.getSubItemId());
                    proofread.setProfessionId(x.getProfessionId());
                    proofread.setStorageId(x.getId());
                    proofread.setType(CadProofreadTypeEnum.SUPPLY_FLW.getValue());
                    proofread.setCreateUser(startFlwParam.getOprId());
                    proofread.setAuditor(y.getAuditor());
                    proofread.setMemo(y.getMemo());
                    proofread.setStatus(ProofreadAuditStatusEnum.PRE_AUDIT.getValue());
                    proofread.setAuditType(y.getAuditType());
                    addProofreadList.add(proofread);
                });
            });
            if(CollUtil.isNotEmpty(addProofreadList)){
                this.saveBatch(addProofreadList);
            }
        }
    }

    @Override
    public void proofread(StartFlwParam startFlwParam) {
        if(CollUtil.isNotEmpty(startFlwParam.getFileHisList())){
            //1. 新增文件历史记录
            List<CadFileHistory> addHisList = BeanUtil.copyToList(startFlwParam.getFileHisList(),CadFileHistory.class);
            cadFileHistoryService.saveBatch(addHisList);
            //2. 构建成品区存储目录
            String newStorageId = cadStorageService.createStorageFold(startFlwParam.getStorageId(), startFlwParam.getOprId());
            //3. 根据存储目录，存放文件
            List<CadStorage> addStorageFileList = new ArrayList<>();
            addHisList.forEach(x->{
                CadStorage cadStorage = new CadStorage();
                cadStorage.setId(IdWorker.getIdStr());   //手动生成新的主键Id
                cadStorage.setParentId(newStorageId);
                cadStorage.setName(x.getFileName());
                cadStorage.setType(CadFileEnum.FILE.getValue());
                cadStorage.setPrjId(startFlwParam.getPrjId());
                cadStorage.setStageId(startFlwParam.getStageId());
                cadStorage.setSubItemId(startFlwParam.getSubItemId());
                cadStorage.setProfessionId(startFlwParam.getProfessionId());
                cadStorage.setHisId(x.getId());
                cadStorage.setHisDownLoadUrl(x.getDownLoadUrl());
                cadStorage.setCreateUser(startFlwParam.getOprId());
                cadStorage.setOldFId(x.getFileId());
                addStorageFileList.add(cadStorage);
            });
            if(CollUtil.isNotEmpty(addStorageFileList)){
                cadStorageService.saveBatch(addStorageFileList);
            }
            //4. 审批记录
            List<CadProofread> addProofreadList = new ArrayList<>();
            addStorageFileList.forEach(x->{
                startFlwParam.getProofreadList().forEach(y->{
                    CadProofread proofread = new CadProofread();
                    proofread.setPrjId(x.getPrjId());
                    proofread.setStageId(x.getStageId());
                    proofread.setSubItemId(x.getSubItemId());
                    proofread.setProfessionId(x.getProfessionId());
                    proofread.setStorageId(x.getId());
                    proofread.setType(CadProofreadTypeEnum.AUDIT_FLW.getValue());
                    proofread.setCreateUser(startFlwParam.getOprId());
                    proofread.setAuditor(y.getAuditor());
                    proofread.setMemo(y.getMemo());
                    proofread.setStatus(ProofreadAuditStatusEnum.PRE_AUDIT.getValue());
                    proofread.setAuditType(y.getAuditType());
                    addProofreadList.add(proofread);
                });
            });
            if(CollUtil.isNotEmpty(addProofreadList)){
                this.saveBatch(addProofreadList);
            }
        }
    }

    @Override
    public void audit(AuditParam auditParam) {
        CadProofread proofread = this.queryEntity(auditParam.getProofreadId());
        proofread.setStatus(auditParam.getStatus());  //0:待审核 1:通过  2：退回
        proofread.setAuditTime(new Date());
        this.updateById(proofread);
        //判断该文件所有审批阶段，是否均通过
        List<CadProofread> exists = this.list(new QueryWrapper<CadProofread>().lambda()
                .eq(CadProofread::getStorageId,proofread.getStorageId())
                .ne(CadProofread::getStatus,1));
        //自动判断，是否归档到成品区|提资区
        if(CollUtil.isEmpty(exists)){
            CadProduct product = new CadProduct();
            product.setPrjId(proofread.getPrjId());
            product.setStageId(proofread.getStageId());
            product.setSubItemId(proofread.getSubItemId());
            product.setProfessionId(proofread.getProfessionId());
            product.setStorageId(proofread.getStorageId());
            product.setType(CadProofreadTypeEnum.SUPPLY_FLW.getValue().equals(proofread.getType())? CadProductEnum.SUPPLY.getValue():CadProductEnum.PRODUCT.getValue());
            product.setCreateUser(proofread.getCreateUser());
            cadProductService.save(product);
        }
    }
}
