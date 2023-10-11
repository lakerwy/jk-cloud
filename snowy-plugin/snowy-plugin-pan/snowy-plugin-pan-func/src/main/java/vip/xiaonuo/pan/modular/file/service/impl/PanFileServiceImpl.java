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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.mapper.PanFileMapper;
import vip.xiaonuo.pan.modular.file.param.PanFileAddParam;
import vip.xiaonuo.pan.modular.file.param.PanFileEditParam;
import vip.xiaonuo.pan.modular.file.param.PanFileIdParam;
import vip.xiaonuo.pan.modular.file.param.PanFilePageParam;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.fileCenter.component.AsyncTaskComp;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.FileOperation;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.fileCenter.param.UnzipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
    private UserFileService userFileService;

    @Resource
    private FileFactory fileFactory;


    @Resource
    AsyncTaskComp asyncTaskComp;

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
    public void unzipFile(UnzipFileParam unzipFileParam) {
        UserFile userFile = userFileService.getById(unzipFileParam.getUserFileId());
        if(ObjectUtil.isEmpty(userFile)){
            throw new CommonException("文件不存在，id值为：{}", unzipFileParam.getUserFileId());
        }
        PanFile panFile = getById(userFile.getFileId());
        File destFile = new File(FileCenterUtil.getStaticPath() + "temp" + File.separator + panFile.getFileUrl());


        Downloader downloader = fileFactory.getDownloader(panFile.getStorageType());
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setFileUrl(panFile.getFileUrl());
        InputStream inputStream = downloader.getInputStream(downloadFile);

        try {
            FileUtils.copyInputStreamToFile(inputStream, destFile);
        } catch (IOException e) {
            throw new CommonException("文件转换失败--");
        }


        String extendName = userFile.getExtendName();

        String unzipUrl = FileCenterUtil.getTempFile(panFile.getFileUrl()).getAbsolutePath().replace("." + extendName, "");

        List<String> fileEntryNameList = new ArrayList<>();

        try {
            fileEntryNameList = FileOperation.unzip(destFile, unzipUrl);
        } catch (Exception e) {
            throw new CommonException("解压失败--",e);
        }

        if (destFile.exists()) {
            boolean delete = destFile.delete();
        }

        if (!fileEntryNameList.isEmpty() && unzipFileParam.getUnzipMode() == 1) {
            UserFile userFile1 = new UserFile();
            userFile1.setFileId(userFile.getUserId());
            userFile1.setFileName(userFile.getFileName());
            userFile1.setFilePath(CommonFile.formatPath(userFile.getFilePath()));
            userFile1.setIsDir(1);
            userFile1.setFoldType(userFile.getFoldType());
            userFile1.setOrgId(userFile1.getOrgId());
            userFileService.save(userFile1);
        }
        for (String entryName : fileEntryNameList) {
            asyncTaskComp.saveUnzipFile(userFile, panFile, unzipFileParam.getUnzipMode(), entryName, unzipFileParam.getFilePath());
        }
    }

    @Override
    public void updateFileDetail(String userFileId, String identifier, long fileSize) {
        UserFile userFile = userFileService.getById(userFileId);
        PanFile fileBean = new PanFile();
        fileBean.setIdentifier(identifier);
        fileBean.setFileSize(fileSize);
        fileBean.setId(userFile.getFileId());
        updateById(fileBean);
        userFileService.updateById(userFile);
    }
}
