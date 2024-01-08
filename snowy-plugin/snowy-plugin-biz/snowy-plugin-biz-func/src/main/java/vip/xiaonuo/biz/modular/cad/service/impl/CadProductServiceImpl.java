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
import vip.xiaonuo.biz.modular.cad.entity.CadStorage;
import vip.xiaonuo.biz.modular.cad.enums.CadFileEnum;
import vip.xiaonuo.biz.modular.cad.enums.CadProductEnum;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.service.CadFileHistoryService;
import vip.xiaonuo.biz.modular.cad.service.CadStorageService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadProduct;
import vip.xiaonuo.biz.modular.cad.mapper.CadProductMapper;
import vip.xiaonuo.biz.modular.cad.service.CadProductService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * CAD-成品表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/01 15:35
 **/

@DS("slave_1")
@Service
public class CadProductServiceImpl extends ServiceImpl<CadProductMapper, CadProduct> implements CadProductService {

    @Resource
    private CadFileHistoryService cadFileHistoryService;

    @Resource
    private CadStorageService cadStorageService;


    @Override
    public Page<CadProduct> page(CadProductPageParam cadProductPageParam) {
        QueryWrapper<CadProduct> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadProductPageParam.getSortField(), cadProductPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadProductPageParam.getSortOrder());
            queryWrapper.orderBy(true, cadProductPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadProductPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadProduct::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadProductAddParam cadProductAddParam) {
        CadProduct cadProduct = BeanUtil.toBean(cadProductAddParam, CadProduct.class);
        this.save(cadProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadProductEditParam cadProductEditParam) {
        CadProduct cadProduct = this.queryEntity(cadProductEditParam.getId());
        BeanUtil.copyProperties(cadProductEditParam, cadProduct);
        this.updateById(cadProduct);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadProductIdParam> cadProductIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadProductIdParamList, CadProductIdParam::getId));
    }

    @Override
    public CadProduct detail(CadProductIdParam cadProductIdParam) {
        return this.queryEntity(cadProductIdParam.getId());
    }

    @Override
    public CadProduct queryEntity(String id) {
        CadProduct cadProduct = this.getById(id);
        if(ObjectUtil.isEmpty(cadProduct)) {
            throw new CommonException("CAD-成品表不存在，id值为：{}", id);
        }
        return cadProduct;
    }

    @Override
    public void filing(FilingParam filingParam) {
        if(CollUtil.isNotEmpty(filingParam.getFileHisList())){
            //1. 新增文件历史记录
            List<CadFileHistory> addHisList = BeanUtil.copyToList(filingParam.getFileHisList(),CadFileHistory.class);
            cadFileHistoryService.saveBatch(addHisList);
            //2. 构建成品区存储目录
            String newStorageId = cadStorageService.createStorageFold(filingParam.getStorageId(), filingParam.getOprId());
            //3. 根据存储目录，存放文件
            List<CadStorage> addStorageFileList = new ArrayList<>();
            addHisList.forEach(x->{
                CadStorage cadStorage = new CadStorage();
                cadStorage.setId(IdWorker.getIdStr());   //手动生成新的主键Id
                cadStorage.setParentId(newStorageId);
                cadStorage.setName(x.getFileName());
                cadStorage.setType(CadFileEnum.FILE.getValue());
                cadStorage.setPrjId(filingParam.getPrjId());
                cadStorage.setStageId(filingParam.getStageId());
                cadStorage.setSubItemId(filingParam.getSubItemId());
                cadStorage.setProfessionId(filingParam.getProfessionId());
                cadStorage.setHisId(x.getId());
                cadStorage.setHisDownLoadUrl(x.getDownLoadUrl());
                cadStorage.setCreateUser(filingParam.getOprId());
                cadStorage.setOldFId(x.getFileId());
                addStorageFileList.add(cadStorage);
            });
            if(CollUtil.isNotEmpty(addStorageFileList)){
                cadStorageService.saveBatch(addStorageFileList);
            }
            //4. 存放产品成果记录
            List<CadProduct> addProductList = new ArrayList<>();
            addStorageFileList.forEach(x->{
                CadProduct product = new CadProduct();
                product.setPrjId(x.getPrjId());
                product.setStageId(x.getStageId());
                product.setSubItemId(x.getSubItemId());
                product.setProfessionId(x.getProfessionId());
                product.setStorageId(x.getId());
                product.setType(CadProductEnum.PRODUCT.getValue());
                product.setCreateUser(filingParam.getOprId());
                addProductList.add(product);
            });
            if(CollUtil.isNotEmpty(addProductList)){
                this.saveBatch(addProductList);
            }
        }
    }
}
