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
package vip.xiaonuo.pan.modular.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.mapper.PanFileMapper;
import vip.xiaonuo.pan.modular.file.param.*;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.file.utils.CommonFile;
import vip.xiaonuo.pan.modular.file.vo.UploadFileVo;
import vip.xiaonuo.pan.modular.fold.entity.DevFold;
import vip.xiaonuo.pan.modular.fold.service.DevFoldService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 文件Service接口实现类
 *
 * @author mayike
 * @date  2023/09/06 21:15
 **/
@Service
public class PanFileServiceImpl extends ServiceImpl<PanFileMapper, PanFile> implements PanFileService {

    @Resource
    private PanFileMapper panFileMapper;

    @Resource
    private DevFoldService devFoldService;

    public static Executor exec = Executors.newFixedThreadPool(20);

    @Override
    public Page<PanFile> page(PanFilePageParam panFilePageParam) {
        QueryWrapper<PanFile> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(panFilePageParam.getSortField(), panFilePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panFilePageParam.getSortOrder());
            queryWrapper.orderBy(true, panFilePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panFilePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanFile::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileAddParam panFileAddParam) {
        PanFile panFile = BeanUtil.toBean(panFileAddParam, PanFile.class);
        this.save(panFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileEditParam panFileEditParam) {
        PanFile panFile = this.queryEntity(panFileEditParam.getId());
        BeanUtil.copyProperties(panFileEditParam, panFile);
        this.updateById(panFile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanFileIdParam> panFileIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panFileIdParamList, PanFileIdParam::getId));
    }

    @Override
    public PanFile detail(PanFileIdParam panFileIdParam) {
        return this.queryEntity(panFileIdParam.getId());
    }

    @Override
    public PanFile queryEntity(String id) {
        PanFile panFile = this.getById(id);
        if(ObjectUtil.isEmpty(panFile)) {
            throw new CommonException("文件不存在，id值为：{}", id);
        }
        return panFile;
    }

    @Override
    public UploadFileVo uploadFileSpeed(UploadFileParam uploadFileParam) {
        UploadFileVo uploadFileVo = new UploadFileVo();
        Map<String, Object> param = new HashMap<>();
        param.put("identifier", uploadFileParam.getIdentifier());
        List<PanFile> panFiles = panFileMapper.selectByMap(param);

        String filePath = uploadFileParam.getFilePath();
        String relativePath = uploadFileParam.getRelativePath();


        CommonFile commonFile = null;
        if (relativePath.contains("/")) {
            commonFile = new CommonFile(filePath, relativePath, false);
        } else {
            commonFile = new CommonFile(filePath, uploadFileParam.getFilename(), false);
        }

        if (CollUtil.isNotEmpty(panFiles)) {
            PanFile file = panFiles.get(0);
            DevFold fold = new DevFold();
            fold.setFileId(file.getId());
            fold.setFilePath(commonFile.getParent());
            fold.setFileName(commonFile.getNameNotExtend());
            fold.setIsDir(commonFile.isDirectory()?1:0);
            fold.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
            fold.setParentId(uploadFileParam.getParentId());
            DevFold devFold = devFoldService.getById(uploadFileParam.getParentId());
            fold.setFoldType(devFold.getFoldType());
            devFoldService.save(fold);
            //todo 加入文件搜索es

            //文件夹上传
            if (relativePath.contains("/")) {
                CommonFile finalCommonFile = commonFile;
                exec.execute(()->{
                    //fileDealComp.restoreParentFilePath(finalQiwenFile, SessionUtil.getUserId());
                });

            }

            uploadFileVo.setSkipUpload(true);
        } else {
//            uploadFileVo.setSkipUpload(false);
//
//            List<Integer> uploaded = uploadTaskDetailMapper.selectUploadedChunkNumList(uploadFileDTO.getIdentifier());
//            if (uploaded != null && !uploaded.isEmpty()) {
//                uploadFileVo.setUploaded(uploaded);
//            } else {
//
//                LambdaQueryWrapper<UploadTask> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//                lambdaQueryWrapper.eq(UploadTask::getIdentifier, uploadFileDTO.getIdentifier());
//                List<UploadTask> rslist = uploadTaskMapper.selectList(lambdaQueryWrapper);
//                if (rslist == null || rslist.isEmpty()) {
//                    UploadTask uploadTask = new UploadTask();
//                    uploadTask.setIdentifier(uploadFileDTO.getIdentifier());
//                    uploadTask.setUploadTime(DateUtil.getCurrentTime());
//                    uploadTask.setUploadStatus(UploadFileStatusEnum.UNCOMPLATE.getCode());
//                    uploadTask.setFileName(qiwenFile.getNameNotExtend());
//                    uploadTask.setFilePath(qiwenFile.getParent());
//                    uploadTask.setExtendName(qiwenFile.getExtendName());
//                    uploadTask.setUserId(SessionUtil.getUserId());
//                    uploadTaskMapper.insert(uploadTask);
//                }
//            }

        }
        return uploadFileVo;
    }
}
