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
package vip.xiaonuo.dev.modular.supervisionPlatform.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.dev.modular.file.service.DevFileService;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBCorpperFormance;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBPersonPerFormance;
import vip.xiaonuo.dev.modular.supervisionPlatform.entity.TBProjectSysRelation;
import vip.xiaonuo.dev.modular.supervisionPlatform.mapper.TBCorpperFormanceMapper;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormanceAddParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormanceEditParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormanceIdParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.param.TBCorpperFormancePageParam;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBCorpperFormanceService;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBPersonPerFormanceService;
import vip.xiaonuo.dev.modular.supervisionPlatform.service.TBProjectSysRelationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 市场监管公共服务平台-企业业绩技术指标信息表Service接口实现类
 *
 * @author ljy
 * @date  2024/01/11 10:57
 **/
@Service
public class TBCorpPerformanceServiceImpl extends ServiceImpl<TBCorpperFormanceMapper, TBCorpperFormance> implements TBCorpperFormanceService {

    @Resource
    DevFileService devFileService;
    @Resource
    TBPersonPerFormanceService tbPersonPerFormanceService;
    @Resource
    TBProjectSysRelationService tbProjectSysRelationService;

    @Override
    public Page<TBCorpperFormance> page(TBCorpperFormancePageParam tbcorpperformancePageParam) {
        QueryWrapper<TBCorpperFormance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(tbcorpperformancePageParam.getPerfNum())) {
            queryWrapper.lambda().eq(TBCorpperFormance::getPerfNum, tbcorpperformancePageParam.getPerfNum());
        }
        if(ObjectUtil.isNotEmpty(tbcorpperformancePageParam.getPrjNuim())) {
            queryWrapper.lambda().eq(TBCorpperFormance::getPrjNuim, tbcorpperformancePageParam.getPrjNuim());
        }
        if(ObjectUtil.isNotEmpty(tbcorpperformancePageParam.getCorpCode())) {
            queryWrapper.lambda().eq(TBCorpperFormance::getCorpCode, tbcorpperformancePageParam.getCorpCode());
        }
        if(ObjectUtil.isAllNotEmpty(tbcorpperformancePageParam.getSortField(), tbcorpperformancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(tbcorpperformancePageParam.getSortOrder());
            queryWrapper.orderBy(true, tbcorpperformancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(tbcorpperformancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(TBCorpperFormance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(TBCorpperFormanceAddParam tbcorpperformanceAddParam) {
        TBCorpperFormance tbcorpperformance = BeanUtil.toBean(tbcorpperformanceAddParam, TBCorpperFormance.class);
        this.save(tbcorpperformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(TBCorpperFormanceEditParam tbcorpperformanceEditParam) {
        TBCorpperFormance tbcorpperformance = this.queryEntity(tbcorpperformanceEditParam.getId());
        BeanUtil.copyProperties(tbcorpperformanceEditParam, tbcorpperformance);
        this.updateById(tbcorpperformance);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<TBCorpperFormanceIdParam> TBCorpperFormanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(TBCorpperFormanceIdParamList, TBCorpperFormanceIdParam::getId));
    }

    @Override
    public TBCorpperFormance detail(TBCorpperFormanceIdParam tbcorpperformanceIdParam) {
        return this.queryEntity(tbcorpperformanceIdParam.getId());
    }

    @Override
    public TBCorpperFormance queryEntity(String id) {
        TBCorpperFormance tbcorpperformance = this.getById(id);
        if(ObjectUtil.isEmpty(tbcorpperformance)) {
            throw new CommonException("市场监管公共服务平台-企业业绩技术指标信息表不存在，id值为：{}", id);
        }
        return tbcorpperformance;
    }

    @Override
    public void downloadImportUserTemplate(HttpServletResponse response,Integer excelType) throws IOException {
        if(ObjectUtil.isEmpty(excelType)){
            log.error("excelType数据不能为空！excelType:"+excelType);
            throw new CommonException("excelType数据不能为空！");
        }
        //模板名称固定
        String excelName = excelType == 1 ? "TBCORPPERFORMANCE.xlsx" : excelType == 2 ?  "TBPERSONPERFORMANCE.xlsx": excelType == 3 ?  "TBPROJECTSYSRELATION.xlsx" : "";
        String outExcelName = excelType == 1 ? "企业业绩技术指标信息导入模板.xlsx" : excelType == 2 ?  "个人业绩信息导入模板.xlsx": excelType == 3 ?  "其他系统项目关联导入模板.xlsx" : "";
        if(StringUtils.isEmpty(excelName)){
            log.error("excelType数据错误！excelType:"+excelType);
            throw new CommonException("excelType数据错误！");
        }
        try {
            InputStream inputStream = POICacheManager.getFile(excelName);
            byte[] bytes = IoUtil.readBytes(inputStream);
            CommonDownloadUtil.download(outExcelName, bytes, response);
        } catch (Exception e) {
            log.error(">>> 下载用户导入模板失败：", e);
            CommonResponseUtil.renderError(response, "下载用户导入模板失败");
        }
    }

    @Override
    public void importRuralData(String fileId,Integer excelType) {
        try {
            DevFile devFile = devFileService.getById(fileId);
            if(ObjectUtil.isEmpty(devFile) || !FileUtil.file(devFile.getStoragePath()).exists()){
                throw new CommonException("文件不存在！fileId："+fileId);
            }
            File file = FileUtil.file(devFile.getStoragePath());
            // 创建临时文件
            File tempFile = FileUtil.writeBytes(getFileBytes(file), FileUtil.file(FileUtil.getTmpDir() +
                    FileUtil.FILE_SEPARATOR + "serverRuralTemplate.xlsx"));
            switch (excelType){
                case 1:
                    // 读取excel  企业业绩技术指标信息表
                    List<TBCorpperFormance> tbCorpperFormanceList =  EasyExcel.read(tempFile).head(TBCorpperFormance.class).sheet()
                            .headRowNumber(2).doReadSync();
                    this.saveBatch(tbCorpperFormanceList);
                    break;
                case 2:
                    // 读取excel  个人业绩信息表
                    List<TBPersonPerFormance> tbPersonPerFormanceList =  EasyExcel.read(tempFile).head(TBPersonPerFormance.class).sheet()
                            .headRowNumber(2).doReadSync();
                    tbPersonPerFormanceService.saveBatch(tbPersonPerFormanceList);
                    break;
                case 3:
                    // 读取excel  与其他系统项目关联信息表
                    List<TBProjectSysRelation> tbProjectSysRelationList =  EasyExcel.read(tempFile).head(TBProjectSysRelation.class).sheet()
                            .headRowNumber(2).doReadSync();
                    tbProjectSysRelationService.saveBatch(tbProjectSysRelationList);
                    break;
                default:
                    throw new CommonException("类别数据错误！");
            }
        } catch (Exception e) {
            log.error(">>> 用户导入失败：", e);
            throw new CommonException("用户导入失败");
        }
    }
    public byte[] getFileBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        }
    }

    public static void main(String[] args) throws IOException{
        File file = FileUtil.file("C:\\Users\\Administrator\\Desktop\\doctest\\TBPROJECTSYSRELATION.xlsx");
        // 创建临时文件
        File tempFile = FileUtil.writeBytes(getFileBytes111(file), FileUtil.file(FileUtil.getTmpDir() +
                FileUtil.FILE_SEPARATOR + "serverRuralTemplate.xlsx"));
        List<TBCorpperFormance> sysUserImportParamList =  EasyExcel.read(tempFile).head(TBCorpperFormance.class).sheet()
                .headRowNumber(2).doReadSync();
        System.out.println("yyyy");
    }

    public static byte[] getFileBytes111(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        }
    }
}
