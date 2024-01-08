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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.entity.*;
import vip.xiaonuo.biz.modular.cad.enums.CadFileEnum;
import vip.xiaonuo.biz.modular.cad.mapper.CadStorageMapper;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.*;
import vip.xiaonuo.biz.modular.cad.vo.FilePathVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.mapper.CadFileMapper;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * CAD-自定义文件目录表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/01 15:34
 **/

@DS("slave_1")
@Service
public class CadFileServiceImpl extends ServiceImpl<CadFileMapper, CadFile> implements CadFileService {

    @Resource
    private CadFileMapper cadFileMapper;

    @Resource
    private CadStorageMapper cadStorageMapper;

    @Resource
    private CadProjectService cadProjectService;

    @Resource
    private CadStageService cadStageService;

    @Resource
    private CadSubItemService cadSubItemService;

    @Resource
    private CadProfessionService cadProfessionService;

    @Override
    public Page<CadFile> page(CadFilePageParam cadFilePageParam) {
        QueryWrapper<CadFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadFilePageParam.getSortField(), cadFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, cadFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadFileAddParam cadFileAddParam) {
        CadFile cadFile = BeanUtil.toBean(cadFileAddParam, CadFile.class);
        this.save(cadFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadFileEditParam cadFileEditParam) {
        CadFile cadFile = this.queryEntity(cadFileEditParam.getId());
        BeanUtil.copyProperties(cadFileEditParam, cadFile);
        this.updateById(cadFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadFileIdParam> cadFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadFileIdParamList, CadFileIdParam::getId));
    }

    @Override
    public CadFile detail(CadFileIdParam cadFileIdParam) {
        return this.queryEntity(cadFileIdParam.getId());
    }

    @Override
    public CadFile queryEntity(String id) {
        CadFile cadFile = this.getById(id);
        if(ObjectUtil.isEmpty(cadFile)) {
            throw new CommonException("CAD-自定义文件目录表不存在，id值为：{}", id);
        }
        return cadFile;
    }

    @Override
    public void reName(CadFileReName cadFileReName) {
        CadFile cadFile = this.queryEntity(cadFileReName.getId());
        cadFile.setName(cadFileReName.getNewName());
        this.updateById(cadFile);
    }

    @Override
    public List<CadFile> getParent(String fileId) {
        return cadFileMapper.getParent(fileId);
    }

    @Override
    public List<CadFile> getChildren(String prjId,String stageId,String subItemId,String professionId,String fileId, String type) {
        if(fileId.equals(prjId) || fileId.equals(stageId) || fileId.equals(subItemId) || (fileId.contains(subItemId) && fileId.contains(professionId))){
             QueryWrapper<CadFile> queryWrapper = new QueryWrapper<>();
             if(ObjectUtil.isNotEmpty(prjId)) queryWrapper.lambda().eq(CadFile::getPrjId,prjId);
             if(ObjectUtil.isNotEmpty(stageId)) queryWrapper.lambda().eq(CadFile::getStageId,stageId);
             if(ObjectUtil.isNotEmpty(subItemId)) queryWrapper.lambda().eq(CadFile::getSubItemId,subItemId);
             if(ObjectUtil.isNotEmpty(professionId)) queryWrapper.lambda().eq(CadFile::getProfessionId,professionId);
             queryWrapper.lambda().eq(CadFile::getType,type);
             return this.list(queryWrapper);
        }else{
            return cadFileMapper.getChildren(fileId,type);
        }
    }

    @Override
    public void updateFileStatus(UpdateTagStatusParam updateTagStatusParam) {
        // 0:未修改  1：修改中 2：已修改 -100：已删除
        if(updateTagStatusParam.getStatus().equals("-100")){
            this.removeById(updateTagStatusParam.getFileId());
        }else {
            CadFile cadFile = this.queryEntity(updateTagStatusParam.getFileId());
            cadFile.setStatus(updateTagStatusParam.getStatus());
            this.updateById(cadFile);
        }
    }

    @Override
    public FilePathVo getFilePathById(String part,String prjId,String stageId,String subItemId,String professionId, String fileId) {
        FilePathVo filePathVo = new FilePathVo();
        String fileName = "";
        String filePathDir = "";

        //存在传入的fileId，不在自定义目录下，属于 项目|阶段|子项|专业(子项Id+专业Id) 层次
        if(ObjectUtil.isNotEmpty(prjId))
        {
            CadProject cadProject = cadProjectService.getById(prjId);
            filePathVo.setPrjId(prjId);
            filePathVo.setPrjName(cadProject.getName());
        }
        if(ObjectUtil.isNotEmpty(stageId))
        {
            CadStage cadStage = cadStageService.getById(stageId);
            filePathVo.setStageId(stageId);
            filePathVo.setStageName(cadStage.getName());
        }
        if(ObjectUtil.isNotEmpty(subItemId))
        {
            CadSubItem cadSubItem = cadSubItemService.getById(subItemId);
            filePathVo.setSubItemId(subItemId);
            filePathVo.setSubItemName(cadSubItem.getName());
        }
        if(ObjectUtil.isNotEmpty(professionId))
        {
            CadProfession cadProfession = cadProfessionService.getById(professionId);
            filePathVo.setProfessionId(professionId);
            filePathVo.setProfessionName(cadProfession.getName());
        }
        //设计区
        if(part.equals("DESIGN")){
            //获取某个目录的父级
            List<CadFile> cadFileList = cadFileMapper.getParent(fileId);
            //拼接自定目录
            if(CollUtil.isNotEmpty(cadFileList)) {
                String tmpFileId = fileId;   //变量值，循环找父级
                while (ObjectUtil.isNotEmpty(tmpFileId)) {
                    String finalTmpFileId = tmpFileId;
                    CadFile cadFile = cadFileList.stream().filter(x->x.getId().equals(finalTmpFileId)).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(cadFile)){
                        if(cadFile.getId().equals(fileId) && cadFile.getType().equals(CadFileEnum.FILE.getValue())){
                            fileName = cadFile.getName();
                        }else {
                            filePathDir = cadFile.getName() + (ObjectUtil.isNotEmpty(filePathDir) ? File.separator + filePathDir : filePathDir);
                        }
                        tmpFileId = cadFile.getParentId();
                    }else{
                        tmpFileId = "";
                    }
                }
            }
        }else{
            //校审区|提资区|成品区
            List<CadStorage> cadStorageList = cadStorageMapper.getParent(fileId);
            //拼接自定目录
            if(CollUtil.isNotEmpty(cadStorageList)) {
                String tmpFileId = fileId;   //变量值，循环找父级
                while (ObjectUtil.isNotEmpty(tmpFileId)) {
                    String finalTmpFileId = tmpFileId;
                    CadStorage cadStorage = cadStorageList.stream().filter(x->x.getId().equals(finalTmpFileId)).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(cadStorage)){
                        if(cadStorage.getId().equals(fileId)){
                            fileName = cadStorage.getName();
                        }else {
                            filePathDir = cadStorage.getName() + (ObjectUtil.isNotEmpty(filePathDir) ? File.separator + filePathDir : filePathDir);
                        }
                        tmpFileId = cadStorage.getParentId();
                    }else{
                        tmpFileId = "";
                    }
                }
            }
        }
        filePathVo.setFileName(fileName);
        filePathVo.setFilePathDir(filePathDir);
        return filePathVo;
    }

    @Override
    public void delFile(CadFileIdParam cadFileIdParam) {
        List<CadFile> childrenList = cadFileMapper.getChildren(cadFileIdParam.getId(),null);
        this.removeByIds(childrenList);
    }
}
