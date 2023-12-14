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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.cad.entity.CadFile;
import vip.xiaonuo.biz.modular.cad.service.CadFileService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.cad.entity.CadStorage;
import vip.xiaonuo.biz.modular.cad.mapper.CadStorageMapper;
import vip.xiaonuo.biz.modular.cad.param.CadStorageAddParam;
import vip.xiaonuo.biz.modular.cad.param.CadStorageEditParam;
import vip.xiaonuo.biz.modular.cad.param.CadStorageIdParam;
import vip.xiaonuo.biz.modular.cad.param.CadStoragePageParam;
import vip.xiaonuo.biz.modular.cad.service.CadStorageService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CAD-提资|校审|成品文件存储目录结构Service接口实现类
 *
 * @author yong.ye
 * @date  2023/12/12 15:14
 **/
@Service
public class CadStorageServiceImpl extends ServiceImpl<CadStorageMapper, CadStorage> implements CadStorageService {

    @Resource
    private CadFileService cadFileService;

    @Override
    public Page<CadStorage> page(CadStoragePageParam cadStoragePageParam) {
        QueryWrapper<CadStorage> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(cadStoragePageParam.getSortField(), cadStoragePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(cadStoragePageParam.getSortOrder());
            queryWrapper.orderBy(true, cadStoragePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(cadStoragePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(CadStorage::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CadStorageAddParam cadStorageAddParam) {
        CadStorage cadStorage = BeanUtil.toBean(cadStorageAddParam, CadStorage.class);
        this.save(cadStorage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CadStorageEditParam cadStorageEditParam) {
        CadStorage cadStorage = this.queryEntity(cadStorageEditParam.getId());
        BeanUtil.copyProperties(cadStorageEditParam, cadStorage);
        this.updateById(cadStorage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CadStorageIdParam> cadStorageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(cadStorageIdParamList, CadStorageIdParam::getId));
    }

    @Override
    public CadStorage detail(CadStorageIdParam cadStorageIdParam) {
        return this.queryEntity(cadStorageIdParam.getId());
    }

    @Override
    public CadStorage queryEntity(String id) {
        CadStorage cadStorage = this.getById(id);
        if(ObjectUtil.isEmpty(cadStorage)) {
            throw new CommonException("CAD-提资|校审|成品文件存储目录结构不存在，id值为：{}", id);
        }
        return cadStorage;
    }

    /**
     * 创建存储目录：
     * 根据选择存储的文件夹Id，构建存储路径 文件夹
     * @param storageId 选择存储的文件夹Id - [CadFile.Id]
     * @return 返回存储路径的最下层文件夹id
     */
    @Override
    public String createStorageFold(String storageId,String oprId){
        // 返回新构建的存储目录下的实际存储地址Id
        Map<String,String> newStorageIdMap = new HashMap<>();
        //1. 根据选择的存储路径对应的 设计区 目录Id，获取其父级
        List<CadFile> oldResourceList = cadFileService.getParent(storageId);
        //1.1 根据原始目录结构，构建新目录结构（临时数据）
        List<CadStorage> cadStorageList = new ArrayList<>();
        oldResourceList.forEach(x->{
            CadStorage cadStorage = new CadStorage();
            cadStorage.setId(IdWorker.getIdStr());   //手动生成新的主键Id
            cadStorage.setParentId(x.getParentId()); //设置默认父级Id，主要处理 subItemId+professionId
            cadStorage.setName(x.getName());
            cadStorage.setType(x.getType());
            cadStorage.setPrjId(x.getPrjId());
            cadStorage.setStageId(x.getStageId());
            cadStorage.setSubItemId(x.getSubItemId());
            cadStorage.setProfessionId(x.getProfessionId());
            cadStorage.setOldFId(x.getId());
            cadStorage.setOldFParentId(x.getParentId());
            cadStorage.setCreateUser(oprId);
            cadStorageList.add(cadStorage);
        });
        //1.2 重新构建 子-父级关系
        List<CadStorage> tmpList = cadStorageList;
        cadStorageList.forEach(x->{
            CadStorage parentObj = tmpList.stream().filter(m->m.getOldFId().equals(x.getOldFParentId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(parentObj)){
                x.setParentId(parentObj.getId());
            }
            if(x.getOldFId().equals(storageId)){
                newStorageIdMap.put("newStorageId",x.getId());  //新的存储文件夹Id
            }
        });

        //2. 根据 oldFId对应的目录是否已创建【先根据oldFId,获取历史数据，再对比】
        //新增的集合
        List<CadStorage> addList = new ArrayList<>();
        if(CollUtil.isNotEmpty(cadStorageList)) {
            //2.1 根据目录Id获取所有已创建过的存储目录
            List<String> oldFIds = oldResourceList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<CadStorage> oldStorageList = this.list(new QueryWrapper<CadStorage>().lambda().in(CadStorage::getOldFId,oldFIds));
            //2.2 循环处理选择存储路径的父级文件夹，若已存在，不新增，否则新增
            cadStorageList.forEach(x->{
                //2.2.1 历史oldFileId+文件名name 是否已存在
                CadStorage existsObj = oldStorageList.stream().filter(m->m.getOldFId().equals(x.getOldFId()) && m.getName().equals(x.getName())).findFirst().orElse(null);
                if(ObjectUtil.isEmpty(existsObj)){
                    //2.2.2 判断是否需要更新 父级Id
                    CadFile tmpFileParent = oldResourceList.stream().filter(m->m.getId().equals(x.getOldFParentId())).findFirst().orElse(null);
                    CadStorage tmpStorageParent = oldStorageList.stream().filter(m->m.getOldFId().equals(tmpFileParent.getId()) && m.getName().equals(tmpFileParent.getName())).findFirst().orElse(null);
                    if(ObjectUtil.isNotEmpty(tmpStorageParent)){
                        x.setParentId(tmpStorageParent.getId());
                    }
                    addList.add(x);
                }else{
                    if(x.getOldFId().equals(storageId)){
                        newStorageIdMap.put("newStorageId",existsObj.getId()); //若已存在，获取历史Id
                    }
                }
            });
        }
        if(CollUtil.isNotEmpty(addList)){
            this.saveBatch(addList);
        }
        //生成目录结构后，返回存储路径最底层文件夹Id
        return newStorageIdMap.get("newStorageId");
    }
}
