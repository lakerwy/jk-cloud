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
package vip.xiaonuo.sys.modular.hr.certManage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;
import vip.xiaonuo.sys.modular.hr.certManage.enums.CertTypeEnum;
import vip.xiaonuo.sys.modular.hr.certManage.mapper.CertManageMapper;
import vip.xiaonuo.sys.modular.hr.certManage.param.*;
import vip.xiaonuo.sys.modular.hr.certManage.service.CertManageService;
import vip.xiaonuo.sys.modular.hr.certManage.vo.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 人员资质管理Service接口实现类
 *
 * @author yong.ye
 * @date  2024/01/23 17:23
 **/
@Service
public class CertManageServiceImpl extends ServiceImpl<CertManageMapper, CertManage> implements CertManageService {

    @Resource
    private CertManageMapper certManageMapper;

    @Override
    public Page<CertManageVo> page(CertManagePageParam certManagePageParam) {
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if (ObjectUtil.isNotEmpty(certManagePageParam.getType())) {
            queryWrapper.eq("a.TYPE", certManagePageParam.getType());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getName())) {
            queryWrapper.like("a.NAME", certManagePageParam.getName());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getIdNumber())) {
            queryWrapper.like("a.ID_NUMBER", certManagePageParam.getIdNumber());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getInsUnit())) {
            queryWrapper.like("a.INS_UNIT", certManagePageParam.getInsUnit());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getUnit())) {
            queryWrapper.like("a.UNIT", certManagePageParam.getUnit());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getCertUnit())) {
            queryWrapper.like("a.CERT_UNIT", certManagePageParam.getCertUnit());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getEduMajor())) {
            queryWrapper.like("a.EDU_MAJOR", certManagePageParam.getEduMajor());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getStartIssueDate()) && ObjectUtil.isNotEmpty(certManagePageParam.getEndIssueDate())) {
            queryWrapper.between("a.ISSUE_DATE", certManagePageParam.getStartIssueDate(), certManagePageParam.getEndIssueDate());
        }
        if (ObjectUtil.isNotEmpty(certManagePageParam.getUserType())) {
            queryWrapper.eq("a.USER_TYPE", certManagePageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertStage())){
            queryWrapper.eq("a.certStage", certManagePageParam.getCertStage());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getUserStatus())){
            queryWrapper.eq("a.userStatus",certManagePageParam.getUserStatus());
        }else{
            queryWrapper.eq("a.userStatus","0");
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getDept())){
            queryWrapper.like("a.DEPT",certManagePageParam.getDept());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getCertType())){
            queryWrapper.like("d1.DICT_LABEL",certManagePageParam.getCertType());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getLevel())){
            queryWrapper.like("d2.DICT_LABEL",certManagePageParam.getLevel());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getMajor())){
            queryWrapper.like("a.MAJOR",certManagePageParam.getMajor());
        }
        if(ObjectUtil.isNotEmpty(certManagePageParam.getDepositAddress())){
            queryWrapper.like("a.DEPOSIT_ADDRESS",certManagePageParam.getDepositAddress());
        }
        // 关键字搜索
        if (ObjectUtil.isNotEmpty(certManagePageParam.getSearchKey())) {
            queryWrapper.and(q -> q.like("a.CERT_NO", certManagePageParam.getSearchKey())
                    .or().like("a.MAJOR", certManagePageParam.getSearchKey())
                    .or().like("a.ID_NUMBER", certManagePageParam.getSearchKey())
                    .or().like("a.UNIT", certManagePageParam.getSearchKey())
                    .or().like("a.NAME", certManagePageParam.getSearchKey())
                    .or().like("d1.DICT_LABEL", certManagePageParam.getSearchKey())
                    .or().like("d2.DICT_LABEL", certManagePageParam.getSearchKey())
            );
        }
        if (ObjectUtil.isAllNotEmpty(certManagePageParam.getSortField(), certManagePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(certManagePageParam.getSortOrder());
            queryWrapper.orderBy(true, certManagePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(certManagePageParam.getSortField()));
        } else {
            queryWrapper.orderByAsc("a.TYPE").orderByAsc("d1.SORT_CODE").orderByAsc("d2.SORT_CODE").orderByAsc("a.UNIT").orderByAsc("a.MAJOR").orderByAsc("a.ID_NUMBER");
        }
        return certManageMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(CertManageAddParam certManageAddParam) {
        CertManage certManage = BeanUtil.toBean(certManageAddParam, CertManage.class);
        certManage.setCertStatus("0");  //0:有效 1 无效
        this.save(certManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(CertManageEditParam certManageEditParam) {
        CertManage certManage = this.queryEntity(certManageEditParam.getId());
        BeanUtil.copyProperties(certManageEditParam, certManage);
        this.updateById(certManage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<CertManageIdParam> certManageIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(certManageIdParamList, CertManageIdParam::getId));
    }

    @Override
    public CertManage detail(CertManageIdParam certManageIdParam) {
        CertManageInfo certManageInfo = certManageMapper.getDetail(certManageIdParam.getId());
        CertManage certManage = BeanUtil.copyProperties(certManageInfo,CertManage.class);
        certManage.setCertStatus(certManageInfo.getUserStatus());
        return certManage;
    }

    @Override
    public CertManage queryEntity(String id) {
        CertManage certManage = this.getById(id);
        if (ObjectUtil.isEmpty(certManage)) {
            throw new CommonException("人员资质管理不存在，id值为：{}", id);
        }
        return certManage;
    }

    @Override
    public List<PersonVo> list(PersonParam personParam) {
        return certManageMapper.getPersonList(personParam);
    }

    @Override
    public SearchVo searchPage(CertSearchPageParam certSearchPageParam) {
        SearchVo searchVo = new SearchVo();
        searchVo.setOtherCertList(new ArrayList<>());
        searchVo.setTrCertList(new ArrayList<>());
        searchVo.setReCertList(new ArrayList<>());
        //不开启综合搜索，普通搜索模块
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getType())) {
            queryWrapper.eq("a.TYPE", certSearchPageParam.getType());
        }
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getName())) {
            queryWrapper.like("a.NAME", certSearchPageParam.getName());
        }
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getIdNumber())) {
            queryWrapper.like("a.ID_NUMBER", certSearchPageParam.getIdNumber());
        }
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getInsUnit())) {
            queryWrapper.like("a.INS_UNIT", certSearchPageParam.getInsUnit());
        }
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getEduMajor())) {
            queryWrapper.like("a.EDU_MAJOR", certSearchPageParam.getEduMajor());
        }
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getStartIssueDate()) && ObjectUtil.isNotEmpty(certSearchPageParam.getEndIssueDate())) {
            queryWrapper.between("a.ISSUE_DATE", certSearchPageParam.getStartIssueDate(), certSearchPageParam.getEndIssueDate());
        }
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getUserType())) {
            queryWrapper.eq("a.USER_TYPE", certSearchPageParam.getUserType());
        }
        /* 外部联表查询条件 */
        if(ObjectUtil.isNotEmpty(certSearchPageParam.getCertStage())){
            queryWrapper.eq("a.certStage", certSearchPageParam.getCertStage());
        }
        if(ObjectUtil.isNotEmpty(certSearchPageParam.getUserStatus())){
            queryWrapper.eq("a.userStatus",certSearchPageParam.getUserStatus());
        }else{
            queryWrapper.eq("a.userStatus","0");
        }
        if (ObjectUtil.isNotEmpty(certSearchPageParam.getSearchKey())) {
            queryWrapper.and(q -> q.like("a.CERT_NO", certSearchPageParam.getSearchKey())
                    .or().like("a.ID_NUMBER", certSearchPageParam.getSearchKey())
                    .or().like("a.UNIT", certSearchPageParam.getSearchKey())
                    .or().like("a.NAME", certSearchPageParam.getSearchKey())
            );
        }
        // 三级联动搜索（启用模式）（注册类|培训类|职称类 证书同时满足情况）
        if(ObjectUtil.isNotEmpty(certSearchPageParam.getRegisterSearch())
                || ObjectUtil.isNotEmpty(certSearchPageParam.getTrainSearch())
                || ObjectUtil.isNotEmpty(certSearchPageParam.getTitleSearch()))
        {
            //需要用到的字段
            List<String> ids = getFilterIds(certSearchPageParam,certManageMapper.getMyAliasList(queryWrapper));
            if(CollUtil.isNotEmpty(ids)){
                queryWrapper.in("a.ID",ids);
            }else{
                return searchVo;
            }
        }

        //排序
        queryWrapper.orderByAsc("a.TYPE").orderByAsc("d1.SORT_CODE").orderByAsc("d2.SORT_CODE").orderByAsc("a.UNIT").orderByAsc("a.MAJOR").orderByAsc("a.ID_NUMBER");
        List<CertManageVo> list = certManageMapper.getMyList(queryWrapper);
        if(CollUtil.isEmpty(list)){
            return searchVo;
        }
        searchVo.setReCertList(list.stream().filter(x->CertTypeEnum.REGISTER.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchVo.setTrCertList(list.stream().filter(x->CertTypeEnum.TRAIN.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchVo.setOtherCertList(list.stream().filter(x->CertTypeEnum.OTHER.getValue().equals(x.getType())).collect(Collectors.toList()));
        return searchVo;
    }

    @Override
    public SearchDetailVo searchDetail(String id) {
        CertManage certManage = this.queryEntity(id);
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.ID_NUMBER",certManage.getIdNumber());
        queryWrapper.orderByAsc("a.TYPE").orderByAsc("d1.SORT_CODE").orderByAsc("d2.SORT_CODE").orderByAsc("a.UNIT").orderByAsc("a.MAJOR");
        List<CertManageVo> list = certManageMapper.getMyList(queryWrapper);
        SearchDetailVo searchDetailVo = new SearchDetailVo();
        searchDetailVo.setReCertList(list.stream().filter(x->CertTypeEnum.REGISTER.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchDetailVo.setTrCertList(list.stream().filter(x->CertTypeEnum.TRAIN.getValue().equals(x.getType())).collect(Collectors.toList()));
        searchDetailVo.setOtherCertList(list.stream().filter(x->CertTypeEnum.OTHER.getValue().equals(x.getType())).collect(Collectors.toList()));
        UserInfo userInfo = BeanUtil.copyProperties(certManage,UserInfo.class);
        searchDetailVo.setUserInfo(userInfo);
        return searchDetailVo;
    }

    @Override
    public List<CertManage> listByIdCard(String idCard) {
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CertManage::getIdNumber,idCard);
        queryWrapper.lambda().eq(CertManage::getCertStatus,"0");
        queryWrapper.lambda().eq(CertManage::getType,CertTypeEnum.REGISTER.getValue());
        queryWrapper.lambda().orderByAsc(CertManage::getCertTypeId).orderByAsc(CertManage::getLevelId).orderByAsc(CertManage::getMajor);
        return this.list(queryWrapper);
    }

    /**
     * 职称证书 | 有效 | 未过期
     * @return
     */
    @Override
    public Map<String, Integer> titleTotal() {
        Map<String,Integer> result = new HashMap<>();
        QueryWrapper<CertManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("a.TYPE", CertTypeEnum.OTHER.getValue());
        queryWrapper.eq("a.userStatus","0");
        queryWrapper.ne("a.certStage","已过期");
        queryWrapper.orderByAsc("d1.SORT_CODE");
        List<CertManageVo> certManageVoList = certManageMapper.getMyList(queryWrapper);
        Map<String, List<CertManageVo>> typeGroup = certManageVoList.stream().collect(Collectors.groupingBy(x -> x.getCertTypeDict()));
        if(CollUtil.isNotEmpty(typeGroup)){
            for (Map.Entry<String, List<CertManageVo>> entry : typeGroup.entrySet()) {
                 result.put(entry.getKey(),entry.getValue().size());
            }
        }
        return result;
    }

    /**
     * 当 注册|培训|职称 类 有搜索条件时,获取id范围
     * @param param
     * @param certManageList
     * @return
     */
    public List<String> getFilterIds(CertSearchPageParam param,List<CertManageVo> certManageList){
        List<String> ids = new ArrayList<>();
        //注册 registerList  培训 trainList  职称 titleList
        try {
            //获取三个集合并集
            List<CertManageVo> mergeResource = new ArrayList<>();
            List<CertManageVo> registerList = new ArrayList<>();
            List<CertManageVo> trainList = new ArrayList<>();
            List<CertManageVo> titleList = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(param.getRegisterSearch())) {
                registerList = getFilter(param.getRegisterSearch(), certManageList);
                mergeResource.addAll(registerList);
            }
            if(ObjectUtil.isNotEmpty(param.getTrainSearch())) {
                trainList = getFilter(param.getTrainSearch(), certManageList);
                mergeResource.addAll(trainList);
            }
            if(ObjectUtil.isNotEmpty(param.getTitleSearch())) {
                titleList = getFilter(param.getTitleSearch(), certManageList);
                mergeResource.addAll(titleList);
            }
            List<CertManageVo> tmpFilterSource = mergeResource.stream().distinct().collect(Collectors.toList());
            for (CertManageVo cert:tmpFilterSource) {
                Boolean isAdd = true;
                if(ObjectUtil.isEmpty(cert.getIdNumber())) {
                    isAdd = false;
                }
                if(isAdd && ObjectUtil.isNotEmpty(param.getRegisterSearch()) &&
                        registerList.stream().filter(m1->cert.getIdNumber().equals(m1.getIdNumber())).count()<=0){
                    isAdd = false;
                }
                if(isAdd && ObjectUtil.isNotEmpty(param.getTrainSearch()) &&
                        trainList.stream().filter(m1->cert.getIdNumber().equals(m1.getIdNumber())).count()<=0){
                    isAdd = false;
                }
                if(isAdd && ObjectUtil.isNotEmpty(param.getTitleSearch()) &&
                        titleList.stream().filter(m1->cert.getIdNumber().equals(m1.getIdNumber())).count()<=0){
                    isAdd = false;
                }
                if(isAdd){
                    ids.add(cert.getId());
                }
            }
            return ids;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 启动综合查询开关，框定同时满足 注册类|培训类|职称类 (各类搜索参数) 搜索的结果集 【三个的交集】
     * @param param
     * @return
     */
    public List<CertManageVo> getFilter(HybridSearchParam param,List<CertManageVo> certManageList) throws Exception {
        if(ObjectUtil.isEmpty(param) || CollUtil.isEmpty(certManageList)){
            return certManageList;
        }
        //第一步，查询过滤(不含联动查询参数)
        List<CertManageVo> certList = BeanUtil.copyToList(certManageList,CertManageVo.class);
        if(ObjectUtil.isNotEmpty(param.getType())){
            certList = certList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getType()) && x.getType().equals(param.getType())).collect(Collectors.toList());
        }
        if(ObjectUtil.isNotEmpty(param.getUnit())) {
            certList = certList.stream().filter(x-> ObjectUtil.isNotEmpty(x.getUnit()) && x.getUnit().contains(param.getUnit())).collect(Collectors.toList());
        }
        if(ObjectUtil.isNotEmpty(param.getStartIssueDate()) && ObjectUtil.isNotEmpty(param.getEndIssueDate())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startIssueDate = sdf.parse(param.getStartIssueDate());
            Date endIssueDate = sdf.parse(param.getEndIssueDate());
            certList = certList.stream().filter(x-> ObjectUtil.isNotEmpty(x.getIssueDate())
                    && !x.getIssueDate().before(startIssueDate) && !x.getIssueDate().after(endIssueDate))
                    .collect(Collectors.toList());
        }
        if(ObjectUtil.isNotEmpty(param.getSearchKey())){
            certList = certList.stream().filter(x-> (ObjectUtil.isNotEmpty(x.getName()) &&  x.getName().contains(param.getSearchKey()))
                    || (ObjectUtil.isNotEmpty(x.getCertNo()) && x.getCertNo().contains(param.getSearchKey()))
                    || (ObjectUtil.isNotEmpty(x.getIdNumber()) && x.getIdNumber().contains(param.getSearchKey())))
                    .collect(Collectors.toList());
        }
        //第二步，查询过滤（联动查询参数），获取交集
        return getFilterThree(param.getThreeParams(),certList);
    }


    /**
     * 三级联动搜索： 框定同时满足 注册类|培训类|职称类 搜索的结果集 【三个的交集】
     * @param paramList 三级联动查询条件集合
     *        resource 数据源
     * @return
     */
    public List<CertManageVo> getFilterThree(List<ThreeParam> paramList,List<CertManageVo> resource){
        if(CollUtil.isEmpty(resource) || CollUtil.isEmpty(paramList)){
            return resource;
        }
        //返回交集结果
        List<CertManageVo> result = new ArrayList<>();
        //数据联动查询，交集结果过滤   //map 记录 每组 类别|等级|专业 查询结果
        Map<ThreeParam,List<CertManageVo>> filterMap = new HashMap<>();
        List<CertManageVo> filterResource = new ArrayList<>();
        for (ThreeParam threeParam:paramList) {
            List<CertManageVo> tmpCertList = BeanUtil.copyToList(resource,CertManageVo.class);
            if(ObjectUtil.isNotEmpty(threeParam.getCertTypeId())){
                tmpCertList = tmpCertList.stream().filter(x-> ObjectUtil.isNotEmpty(x.getCertTypeId()) &&  x.getCertTypeId().equals(threeParam.getCertTypeId())).collect(Collectors.toList());
            }
            if(ObjectUtil.isNotEmpty(threeParam.getLevelId())){
                tmpCertList = tmpCertList.stream().filter(x-> ObjectUtil.isNotEmpty(x.getLevelId()) && x.getLevelId().equals(threeParam.getLevelId())).collect(Collectors.toList());
            }
            if(ObjectUtil.isNotEmpty(threeParam.getMajorId())){
                String tmpMajorId = StrUtil.BRACKET_START + threeParam.getMajorId() + StrUtil.BRACKET_END;
                tmpCertList = tmpCertList.stream().filter(x-> ObjectUtil.isNotEmpty(x.getMajorId()) && x.getMajorId().contains(tmpMajorId)).collect(Collectors.toList());
            }
            //存在某个查询组，不存在记录，直接返回空
            if(CollUtil.isEmpty(tmpCertList)){
                return null;
            }else{
                filterResource.addAll(tmpCertList);
            }
            filterMap.put(threeParam,tmpCertList);
        }
        //取所有查询结果的交集[均含]
        filterResource.stream().distinct().forEach(cert->{
            Boolean isAdd = true;
            for (Map.Entry<ThreeParam,List<CertManageVo>> entry: filterMap.entrySet()) {
                List<CertManageVo> tmpList = entry.getValue().stream().filter(value->
                        ObjectUtil.isNotEmpty(value.getIdNumber()) &&  value.getIdNumber().equals(cert.getIdNumber()))
                        .collect(Collectors.toList());
                if(CollUtil.isEmpty(tmpList)) {
                    isAdd = false;
                    break;
                }
            }
            if(isAdd) result.add(cert);
        });
        return result;
    }
}
