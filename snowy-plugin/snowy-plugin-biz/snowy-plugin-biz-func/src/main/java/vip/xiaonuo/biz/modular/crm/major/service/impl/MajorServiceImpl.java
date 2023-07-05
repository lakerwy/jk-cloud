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
package vip.xiaonuo.biz.modular.crm.major.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.major.vo.MajorInfoVo;
import vip.xiaonuo.biz.modular.crm.majorleader.entity.Majorleader;
import vip.xiaonuo.biz.modular.crm.majorleader.param.MajorleaderAddParam;
import vip.xiaonuo.biz.modular.crm.majorleader.service.MajorleaderService;
import vip.xiaonuo.common.enums.CommonSnFormatEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.major.entity.Major;
import vip.xiaonuo.biz.modular.crm.major.mapper.MajorMapper;
import vip.xiaonuo.biz.modular.crm.major.param.MajorAddParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorEditParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorIdParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorPageParam;
import vip.xiaonuo.biz.modular.crm.major.service.MajorService;
import vip.xiaonuo.common.util.CommonSnUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 业务表-CRM-图审专业Service接口实现类
 *
 * @author yong.ye
 * @date  2023/06/30 16:42
 **/

@DS("slave_1")
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Resource
    private MajorleaderService majorleaderService;

    @Resource
    private MajorMapper majorMapper;

    @Override
    public Page<Major> page(MajorPageParam majorPageParam) {
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(majorPageParam.getMajorName())) {
            queryWrapper.lambda().like(Major::getMajorName, majorPageParam.getMajorName());
        }
        if(ObjectUtil.isNotEmpty(majorPageParam.getMajorNo())) {
            queryWrapper.lambda().like(Major::getMajorNo, majorPageParam.getMajorNo());
        }
        if(ObjectUtil.isAllNotEmpty(majorPageParam.getSortField(), majorPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(majorPageParam.getSortOrder());
            queryWrapper.orderBy(true, majorPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(majorPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Major::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(MajorAddParam majorAddParam) {
        Major major = BeanUtil.toBean(majorAddParam, Major.class);
        //处理Sn编号
        Major tmp = this.getOne(new QueryWrapper<Major>().lambda().orderByDesc(Major::getCreateTime).last("limit 1"));
        String newSn = CommonSnUtil.GenerateSn(majorAddParam.getMajorNo(), tmp == null ? "" : tmp.getMajorNo(), CommonSnFormatEnum.SN0001.getValue());
        major.setMajorNo(newSn);
        this.save(major);
        //同时新增专业负责人记录
        List<MajorleaderAddParam> majorleaderAddParams = majorAddParam.getMajorleaders();
        if(CollUtil.isNotEmpty(majorleaderAddParams)){
            List<Majorleader> majorleaders = majorleaderAddParams.stream().map(MajorleaderAddParam -> BeanUtil.toBean(MajorleaderAddParam, Majorleader.class)).collect(Collectors.toList());
            majorleaders.forEach(majorleader -> majorleader.setMajorId(major.getId()));
            majorleaderService.saveBatch(majorleaders);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(MajorEditParam majorEditParam) {
        Major major = this.queryEntity(majorEditParam.getId());
        if(ObjectUtil.isEmpty(major)){
            major = new Major();
        }
        BeanUtil.copyProperties(majorEditParam, major);
        saveOrUpdate(major);
        //处理 专业负责人
        List<Majorleader> majorLeaders = majorEditParam.getMajorleaders();

        //处理 专业负责人
        if(CollUtil.isNotEmpty(majorLeaders)){
            //查询 数据库 获取历史设置
            QueryWrapper<Majorleader> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Majorleader::getMajorId, major.getId());
            List<Majorleader> list = majorleaderService.list(wrapper);
            //处理数据，赋予外键，获取已设置的majorleader主键Id
            Major finalMajor = major;
            majorLeaders.forEach(majorLeader->majorLeader.setMajorId(finalMajor.getId()));
            //主键暂存区（已设置的数据库值）
            Set<String> ids = new HashSet<>();
            if(CollUtil.isNotEmpty(list)){
                ids = list.stream().map(Majorleader::getId).collect(Collectors.toSet());
            }
            //逻辑（新增+更新+删除）
            List<Majorleader> updateMajorLeaders = new ArrayList<>();
            updateMajorLeaders = majorLeaders.stream().filter(majorleader -> StrUtil.isNotBlank(majorleader.getId())).collect(Collectors.toList());
            //排除update的id
            Set<String> collect = updateMajorLeaders.stream().map(Majorleader::getId).collect(Collectors.toSet());
            ids.removeAll(collect);
            majorLeaders.removeAll(updateMajorLeaders);
            //数据更新处理逻辑
            if(CollUtil.isNotEmpty(updateMajorLeaders)){
                majorleaderService.updateBatchById(updateMajorLeaders);
            }
            if(CollUtil.isNotEmpty(majorLeaders)){
                majorleaderService.saveBatch(majorLeaders);
            }
            if(CollUtil.isNotEmpty(ids)){
                majorleaderService.removeByIds(ids);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<MajorIdParam> majorIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(majorIdParamList, MajorIdParam::getId));
        // 删除关联数据
        List<String> list = CollStreamUtil.toList(majorIdParamList, MajorIdParam::getId);
        QueryWrapper<Majorleader> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(Majorleader::getMajorId,list);
        majorleaderService.remove(wrapper);
    }

    @Override
    public MajorInfoVo detail(MajorIdParam majorIdParam) {
        MajorInfoVo result = new MajorInfoVo();
        Major major = this.queryEntity(majorIdParam.getId());
        BeanUtil.copyProperties(major, result);
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("MAJOR_ID",major.getId());
        List<Majorleader> majorleaders = majorleaderService.listByMap(queryMap);
        result.setMajorleaders(majorleaders);
        return result;
    }

    @Override
    public Major queryEntity(String id) {
        Major major = this.getById(id);
        if(ObjectUtil.isEmpty(major)) {
            throw new CommonException("业务表-CRM-图审专业不存在，id值为：{}", id);
        }
        return major;
    }

    @Override
    public IPage<MajorInfoVo> pageList(MajorPageParam majorPageParam) {
        QueryWrapper<Major> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(majorPageParam.getMajorName())) {
            queryWrapper.lambda().like(Major::getMajorName, majorPageParam.getMajorName());
        }
        if(ObjectUtil.isNotEmpty(majorPageParam.getMajorNo())) {
            queryWrapper.lambda().like(Major::getMajorNo, majorPageParam.getMajorNo());
        }
        if(ObjectUtil.isAllNotEmpty(majorPageParam.getSortField(), majorPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(majorPageParam.getSortOrder());
            queryWrapper.orderBy(true, majorPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(majorPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(Major::getId);
        }
        return majorMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
