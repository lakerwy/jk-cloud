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
package vip.xiaonuo.pan.modular.share.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.list.TransformedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.pan.modular.fold.entity.DevFold;
import vip.xiaonuo.pan.modular.fold.service.DevFoldService;
import vip.xiaonuo.pan.modular.share.entity.PanShare;
import vip.xiaonuo.pan.modular.share.enums.PanShareStatusEnum;
import vip.xiaonuo.pan.modular.share.enums.PanShareTypeEnum;
import vip.xiaonuo.pan.modular.share.mapper.PanShareMapper;
import vip.xiaonuo.pan.modular.share.param.*;
import vip.xiaonuo.pan.modular.share.param.dto.SaveShareFileDto;
import vip.xiaonuo.pan.modular.share.param.dto.ShareFileDto;
import vip.xiaonuo.pan.modular.share.service.PanShareService;
import vip.xiaonuo.pan.modular.share.vo.ShareFileVo;
import vip.xiaonuo.pan.modular.sharefile.entity.PanShareFile;
import vip.xiaonuo.pan.modular.sharefile.service.PanShareFileService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 网盘-分享表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/07 15:55
 **/
@Service
public class PanShareServiceImpl extends ServiceImpl<PanShareMapper, PanShare> implements PanShareService {

    @Resource
    PanShareFileService panShareFileService;

    @Resource
    DevFoldService devFoldService;

    @Override
    public Page<PanShare> page(PanSharePageParam panSharePageParam) {
        QueryWrapper<PanShare> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(panSharePageParam.getTenantId())) {
            queryWrapper.lambda().eq(PanShare::getTenantId, panSharePageParam.getTenantId());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getUserId())) {
            queryWrapper.lambda().eq(PanShare::getUserId, panSharePageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getStartShareTime()) && ObjectUtil.isNotEmpty(panSharePageParam.getEndShareTime())) {
            queryWrapper.lambda().between(PanShare::getShareTime, panSharePageParam.getStartShareTime(), panSharePageParam.getEndShareTime());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getShareBatchNum())) {
            queryWrapper.lambda().eq(PanShare::getShareBatchNum, panSharePageParam.getShareBatchNum());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getShareType())) {
            queryWrapper.lambda().eq(PanShare::getShareType, panSharePageParam.getShareType());
        }
        if(ObjectUtil.isNotEmpty(panSharePageParam.getShareStatus())) {
            queryWrapper.lambda().eq(PanShare::getShareStatus, panSharePageParam.getShareStatus());
        }
        if(ObjectUtil.isAllNotEmpty(panSharePageParam.getSortField(), panSharePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(panSharePageParam.getSortOrder());
            queryWrapper.orderBy(true, panSharePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(panSharePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PanShare::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanShareAddParam panShareAddParam) {
        PanShare panShare = BeanUtil.toBean(panShareAddParam, PanShare.class);
        this.save(panShare);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanShareEditParam panShareEditParam) {
        PanShare panShare = this.queryEntity(panShareEditParam.getId());
        BeanUtil.copyProperties(panShareEditParam, panShare);
        this.updateById(panShare);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PanShareIdParam> panShareIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(panShareIdParamList, PanShareIdParam::getId));
    }

    @Override
    public PanShare detail(PanShareIdParam panShareIdParam) {
        return this.queryEntity(panShareIdParam.getId());
    }

    @Override
    public PanShare queryEntity(String id) {
        PanShare panShare = this.getById(id);
        if(ObjectUtil.isEmpty(panShare)) {
            throw new CommonException("网盘-分享表不存在，id值为：{}", id);
        }
        return panShare;
    }

    @Override
    public ShareFileVo shareFile(ShareFileDto shareFileDto) {
        //返回结果
        ShareFileVo shareFileVo = new ShareFileVo();
        //用户Id
        String userId  = StpLoginUserUtil.getLoginUser().getId();
        //批次号
        String batchId = UUID.randomUUID().toString().replace("-","");
        //当前分享批次记录
        PanShare panShare = new PanShare();
        BeanUtil.copyProperties(shareFileDto,panShare);
        panShare.setShareTime(DateTime.now());
        panShare.setUserId(userId);
        panShare.setShareStatus(PanShareStatusEnum.NORMAL.getValue());
        //私密分享需要设置密码
        if(shareFileDto.getShareType().equals(PanShareTypeEnum.PRIVATE.getValue())){
            panShare.setExtractionCode(RandomUtil.randomNumbers(6));
            shareFileVo.setExtractionCode(panShare.getExtractionCode());
        }
        panShare.setShareBatchNum(batchId);
        this.save(panShare);

        //处理分享的文件集
        List<PanShareFile> panShareFileList = new ArrayList<>();
        for (String userFileId:shareFileDto.getUserFileIds()) {
//            /** UserFile <--> DevFold **/
//            DevFold userFile = devFoldService.getById(userFileId);
//            if (userFile.getUserId().compareTo(userId) != 0) {
//                throw new CommonException("您只能分享自己的文件");
//            }
//            if (userFile.getIsDir() == 1) {
//                QiwenFile qiwenFile = new QiwenFile(userFile.getFilePath(), userFile.getFileName(), true);
//                List<UserFile> userfileList = userFileService.selectUserFileByLikeRightFilePath(qiwenFile.getPath(), userId);
//                for (UserFile userFile1 : userfileList) {
//                    PanShareFile shareFile1 = new PanShareFile();
//                    shareFile1.setUserFileId(userFile1.getUserFileId());
//                    shareFile1.setShareBatchNum(batchId);
//                    shareFile1.setShareFilePath(userFile1.getFilePath().replaceFirst(userFile.getFilePath().equals("/") ? "" : userFile.getFilePath(), ""));
//                    panShareFileList.add(shareFile1);
//                }
//            }
            PanShareFile shareFile = new PanShareFile();
            shareFile.setUserFileId(userFileId);
            shareFile.setShareFilePath("/");
            shareFile.setShareBatchNum(batchId);
            panShareFileList.add(shareFile);
        }
        panShareFileService.saveBatch(panShareFileList);
        shareFileVo.setShareBatchNum(batchId);
        return shareFileVo;
    }

    @Override
    public void saveShareFile(SaveShareFileDto saveShareFileDto) {
        String userId = StpLoginUserUtil.getLoginUser().getId();
        String savefilePath = saveShareFileDto.getFilePath();
        /**
        //用户文件集合
        List<UserFile> saveUserFiles = new ArrayList<>();
        for (String userFileId:saveShareFileDto.getUserFileIds()) {
            //获取文件信息
            UserFile userFile = userFileService.getById(userFileId);
            String fileName = userFile.getFileName();
            String filePath = userFile.getFilePath();
            //当前文件
            UserFile userFile2 = new UserFile();
            BeanUtil.copyProperties(userFile, userFile2);
            //定义文件名
            String savefileName = fileDealComp.getRepeatFileName(userFile, savefilePath);
            //判断是否为文件夹
            if (userFile.getIsDir() == 1) {
                //当前文件夹
                PanShareFile shareFile = panShareFileService.getOne(new QueryWrapper<PanShareFile>().lambda().eq(PanShareFile::getUserFileId, userFileId).eq(PanShareFile::getShareBatchNum, saveShareFileDto.getShareBatchNum()));
                //当前文件夹下的文件
                List<PanShareFile> shareFileList = panShareFileService.list(new QueryWrapper<PanShareFile>().lambda().eq(PanShareFile::getShareBatchNum, saveShareFileDto.getShareBatchNum()).likeRight(PanShareFile::getShareFilePath, QiwenFile.formatPath(shareFile.getShareFilePath() +"/"+ fileName)));
                for (PanShareFile shareFile1 : shareFileList) {
                    UserFile userFile1 = userFileService.getById(shareFile1.getUserFileId());
                    userFile1.setUserId(userId);
                    userFile1.setFilePath(userFile1.getFilePath().replaceFirst(QiwenFile.formatPath(filePath + "/" + fileName), QiwenFile.formatPath(savefilePath + "/" + savefileName)));
                    saveUserFiles.add(userFile1);
                }
            }
            userFile2.setUserId(userId);
            userFile2.setFilePath(savefilePath);
            userFile2.setFileName(savefileName);
            saveUserFiles.add(userFile2);
        }
        userFileService.saveBatch(saveUserFileList);
         */
    }


}
