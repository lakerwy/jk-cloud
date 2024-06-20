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
package vip.xiaonuo.sys.modular.hr.salary.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryStandard;
import vip.xiaonuo.sys.modular.hr.salary.enums.UserTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryStandardMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.*;
import vip.xiaonuo.sys.modular.hr.salary.service.SalaryStandardService;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.util.EntityUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static vip.xiaonuo.sys.modular.hr.salary.util.CommConstant.*;

/**
 * 工资系统-月度工资标准Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:36
 **/
@Service
public class SalaryStandardServiceImpl extends ServiceImpl<SalaryStandardMapper, SalaryStandard> implements SalaryStandardService {

    @Resource
    private SalaryStandardMapper salaryStandardMapper;

    @Override
    public Page<SalaryStandard> page(SalaryStandardPageParam salaryStandardPageParam) {
        QueryWrapper<SalaryStandard> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getUserType())){
            queryWrapper.lambda().eq(SalaryStandard::getUserType,salaryStandardPageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(SalaryStandard::getYearMonth, salaryStandardPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(SalaryStandard::getIdCardNumber, AesUtil.encrypt(salaryStandardPageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getUserName())) {
            queryWrapper.lambda().like(SalaryStandard::getUserName, AesUtil.encrypt(salaryStandardPageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(SalaryStandard::getFirstOrgName, AesUtil.encrypt(salaryStandardPageParam.getFirstOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getOrgName())) {
            queryWrapper.lambda().like(SalaryStandard::getOrgName, AesUtil.encrypt(salaryStandardPageParam.getOrgName()));
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getPosition())) {
            queryWrapper.lambda().like(SalaryStandard::getPosition, AesUtil.encrypt(salaryStandardPageParam.getPosition()));
        }
        if(ObjectUtil.isNotEmpty(salaryStandardPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SalaryStandard::getUserName,AesUtil.encrypt(salaryStandardPageParam.getSearchKey()))
                    .or().like(SalaryStandard::getIdCardNumber,AesUtil.encrypt(salaryStandardPageParam.getSearchKey()))
                    .or().like(SalaryStandard::getOrgName,AesUtil.encrypt(salaryStandardPageParam.getSearchKey()))
                    .or().like(SalaryStandard::getFirstOrgName,AesUtil.encrypt(salaryStandardPageParam.getSearchKey())));
        }
        if(ObjectUtil.isAllNotEmpty(salaryStandardPageParam.getSortField(), salaryStandardPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(salaryStandardPageParam.getSortOrder());
            queryWrapper.orderBy(true, salaryStandardPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(salaryStandardPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SalaryStandard::getYearMonth).orderByDesc(SalaryStandard::getUserType).orderByAsc(SalaryStandard::getCompany);
        }
        Page<SalaryStandard> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<SalaryStandard> records = AesUtil.decryptGZList(result.getRecords());
        records.sort(Comparator.comparing(SalaryStandard::getFirstOrgName,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setRecords(records);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SalaryStandardAddParam salaryStandardAddParam) {
        //同一公司，同一年月，同一身份证，只存在一条记录
        List<SalaryStandard> oldList = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getYearMonth,salaryStandardAddParam.getYearMonth())
                .eq(SalaryStandard::getCompany,salaryStandardAddParam.getCompany())
                .eq(SalaryStandard::getIdCardNumber,AesUtil.encrypt(salaryStandardAddParam.getIdCardNumber())));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryStandardAddParam.getYearMonth(),salaryStandardAddParam.getUserName());
        }
        SalaryStandard salaryStandard = BeanUtil.toBean(AesUtil.encryptGZ(salaryStandardAddParam), SalaryStandard.class);
        this.save(salaryStandard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SalaryStandardEditParam salaryStandardEditParam) {
        //同一工资类别，同一年月，同一身份证，只存在一条记录
        List<SalaryStandard> oldList = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getYearMonth,salaryStandardEditParam.getYearMonth())
                .eq(SalaryStandard::getCompany,salaryStandardEditParam.getCompany())
                .eq(SalaryStandard::getIdCardNumber,AesUtil.encrypt(salaryStandardEditParam.getIdCardNumber()))
                .ne(SalaryStandard::getId,salaryStandardEditParam.getId()));
        if(CollUtil.isNotEmpty(oldList)){
            throw new CommonException("{}年月{}已存在记录",salaryStandardEditParam.getYearMonth(),salaryStandardEditParam.getUserName());
        }
        SalaryStandard salaryStandard = this.queryEntity(salaryStandardEditParam.getId());
        BeanUtil.copyProperties(AesUtil.encryptGZ(salaryStandardEditParam), salaryStandard);
        this.updateById(salaryStandard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SalaryStandardIdParam> salaryStandardIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(salaryStandardIdParamList, SalaryStandardIdParam::getId));
    }

    @Override
    public SalaryStandard detail(SalaryStandardIdParam salaryStandardIdParam) {
        SalaryStandard salaryStandard = this.queryEntity(salaryStandardIdParam.getId());
        return AesUtil.decryptGZ(salaryStandard);
    }

    @Override
    public SalaryStandard queryEntity(String id) {
        SalaryStandard salaryStandard = this.getById(id);
        if(ObjectUtil.isEmpty(salaryStandard)) {
            throw new CommonException("工资系统-月度工资标准不存在，id值为：{}", id);
        }
        return salaryStandard;
    }

    @Override
    public void copy(CopyParam copyParam) {
        // 当前年月
        String currentYearMonth = copyParam.getToYearMonth();
        if(ObjectUtil.isEmpty(currentYearMonth)) {
            currentYearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        }
        String previousYearMonth = copyParam.getFromYearMonth();
        if(ObjectUtil.isEmpty(previousYearMonth)) {
            previousYearMonth = ComputeUtil.getPreviousYM(currentYearMonth);
        }

        //1. 判断当前年月是否已存在工资标准数据（存在，则不可复制）
        List<SalaryStandard> currentData = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getYearMonth,currentYearMonth));
        if(CollUtil.isNotEmpty(currentData)){
            throw new CommonException("已存在{}月工资标准数据",currentYearMonth);
        }
        //2. 判断上年月是否存在工资标准数据（不存在，则无法复制）
        List<SalaryStandard> previousData = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .eq(SalaryStandard::getYearMonth,previousYearMonth));
        if(CollUtil.isEmpty(previousData)){
            throw new CommonException("不存在{}月工资标准数据",previousYearMonth);
        }
        //3. 复制数据
        String finalCurrentYearMonth = currentYearMonth;
        previousData.forEach(item->{
            item.setId(null);
            item.setYearMonth(finalCurrentYearMonth);
            item.setCreateTime(null);
            item.setCreateUser(null);
            item.setUpdateTime(null);
            item.setUpdateUser(null);
        });
        this.saveBatch(previousData);
    }

    @Override
    public List<PersonnelInfoVo> personnelList(PersonnelParam personnelParam) {
        return salaryStandardMapper.getPersonnelList(personnelParam);
    }

    @Override
    public List<SalaryStandardVo> listByYearMonth(SearchParam param) {
        QueryWrapper<SalaryStandard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SalaryStandard::getYearMonth,param.getYearMonth());
        List<SalaryStandard> standardList = this.list(queryWrapper);
        List<SalaryStandardVo> result = BeanUtil.copyToList(standardList,SalaryStandardVo.class);
        return AesUtil.decryptGZList(result);
    }

    @Override
    public List<SalaryStandardVo> getStandardAllList(List<String> yearMonths) {
        if(CollUtil.isEmpty(yearMonths)){
            return null;
        }
        List<SalaryStandard> standardList = this.list(new QueryWrapper<SalaryStandard>().lambda()
                .in(SalaryStandard::getYearMonth,yearMonths));
        return BeanUtil.copyToList(standardList,SalaryStandardVo.class);
    }

    @Override
    public ThreeSalaryStandardVo threePageList(ThreePageParam threePageParam) {
        ThreeSalaryStandardVo result = new ThreeSalaryStandardVo();
        if(ObjectUtil.isEmpty(threePageParam.getYearMonth())){
            throw new CommonException("所属年月必传");
        }
        QueryWrapper<SalaryStandard> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SalaryStandard::getYearMonth,threePageParam.getYearMonth());
        if(ObjectUtil.isNotEmpty(threePageParam.getUserType())){
            queryWrapper.lambda().eq(SalaryStandard::getUserType,threePageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getIdCardNumber())){
            queryWrapper.lambda().like(SalaryStandard::getIdCardNumber,AesUtil.encrypt(threePageParam.getIdCardNumber()));
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getOrgName())){
            queryWrapper.lambda().like(SalaryStandard::getOrgName,AesUtil.encrypt(threePageParam.getOrgName()));
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getFirstOrgName())){
            queryWrapper.lambda().like(SalaryStandard::getFirstOrgName,AesUtil.encrypt(threePageParam.getFirstOrgName()));
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getUserName())){
            queryWrapper.lambda().like(SalaryStandard::getUserName,AesUtil.encrypt(threePageParam.getUserName()));
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getIsSpec())){
            queryWrapper.lambda().eq(SalaryStandard::getIsSpec,threePageParam.getIsSpec());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getIsTry())){
            queryWrapper.lambda().eq(SalaryStandard::getIsTry,threePageParam.getIsTry());
        }
        if(ObjectUtil.isNotEmpty(threePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(SalaryStandard::getUserName,AesUtil.encrypt(threePageParam.getSearchKey()))
                    .or().like(SalaryStandard::getIdCardNumber,AesUtil.encrypt(threePageParam.getSearchKey()))
                    .or().like(SalaryStandard::getOrgName,AesUtil.encrypt(threePageParam.getSearchKey()))
                    .or().like(SalaryStandard::getFirstOrgName,AesUtil.encrypt(threePageParam.getSearchKey())));
        }
        queryWrapper.lambda().orderByDesc(SalaryStandard::getYearMonth);
        List<SalaryStandard> standardList = AesUtil.decryptGZList(this.list(queryWrapper));
        standardList.sort(Comparator.comparing(SalaryStandard::getFirstOrgName,Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(SalaryStandard::getIdCardNumber,Comparator.nullsLast(Comparator.naturalOrder())));
        result.setZbList(standardList.stream().filter(x-> UserTypeEnum.ZB.getValue().equals(x.getUserType())).collect(Collectors.toList()));
        result.setFgsList(standardList.stream().filter(x-> UserTypeEnum.FGS.getValue().equals(x.getUserType())).collect(Collectors.toList()));
        result.setWpList(standardList.stream().filter(x-> UserTypeEnum.WP.getValue().equals(x.getUserType())).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void updateField(UpdateFieldParam fieldParam) {
        SalaryStandard salaryStandard = this.queryEntity(fieldParam.getId());
        if("memo".equals(fieldParam.getFieldName())){
            salaryStandard.setMemo(fieldParam.getFieldValue());
        }
        if("monthChange".equals(fieldParam.getFieldName())){
            salaryStandard.setMemo(fieldParam.getFieldValue());
        }
        if("isSpec".equals(fieldParam.getFieldName())){
            salaryStandard.setIsSpec(fieldParam.getFieldValue());
        }
        if("isTry".equals(fieldParam.getFieldName())){
            salaryStandard.setIsTry(fieldParam.getFieldValue());
        }
        if("meritRate".equals(fieldParam.getFieldName())){
            salaryStandard.setMeritRate(Float.valueOf(fieldParam.getFieldValue()));
        }
        if("costJson".equals(fieldParam.getFieldName())){
            salaryStandard.setCostJson(fieldParam.getFieldValue());
        }
        this.updateById(salaryStandard);
    }

    @Override
    public ImportResultT importUpdate(UpdateFieldBatch fieldBatch) {
        if(CollUtil.isEmpty(fieldBatch.getFieldList())){
            throw new CommonException("更新字段不可为空");
        }
        if(CollUtil.isEmpty(fieldBatch.getInfoList())){
            throw new CommonException("更新信息不可为空");
        }
        String checkMsg = EntityUtil.checkFields(SalaryStandard.class,fieldBatch.getFieldList());
        if(ObjectUtil.isNotEmpty(checkMsg)){
            throw new CommonException("请检查字段名："+ checkMsg);
        }
        //数据校验
        for (UpdateInfo item:fieldBatch.getInfoList()) {
            String errMsg = "";
            if(fieldBatch.getFieldList().contains("yearMonth") || fieldBatch.getFieldList().contains("idCardNumber")){
                errMsg += "所属年月、身份证号不可更新；";
            }
            if(ObjectUtil.isEmpty(item.getYearMonth())){
                errMsg += "所属年月必填；";
            }
            if(ObjectUtil.isEmpty(item.getIdCardNumber())){
                errMsg += "身份证号必填；";
            }
            //格式校验
            if(ObjectUtil.isNotEmpty(item.getUserType()) && !CollectionUtil.newArrayList(USER_TYPE_LIST).contains(item.getUserType().trim())){
                errMsg += "员工类别必须为：" + StrUtil.join("、",USER_TYPE_LIST) +"；";
            }
            if(ObjectUtil.isNotEmpty(item.getHaveBirthdayPay()) && !CollectionUtil.newArrayList(YES_OR_NO_LIST).contains(item.getHaveBirthdayPay().trim())){
                errMsg += "是否有生日福利必须为：" + StrUtil.join("、",YES_OR_NO_LIST) +"；";
            }
            if(ObjectUtil.isNotEmpty(item.getHaveSeniorityPay()) && !CollectionUtil.newArrayList(YES_OR_NO_LIST).contains(item.getHaveSeniorityPay().trim())){
                errMsg += "是否有工龄工资必须为：" + StrUtil.join("、",YES_OR_NO_LIST) +"；";
            }
            if(ObjectUtil.isNotEmpty(item.getIsTry()) && !CollectionUtil.newArrayList(YES_OR_NO_LIST).contains(item.getIsTry().trim())){
                errMsg += "是否实习生必须为：" + StrUtil.join("、",YES_OR_NO_LIST) +"；";
            }
            if(ObjectUtil.isNotEmpty(item.getIsSpec()) && !CollectionUtil.newArrayList(YES_OR_NO_LIST).contains(item.getIsSpec().trim())){
                errMsg += "是否标记必须为：" + StrUtil.join("、",YES_OR_NO_LIST) +"；";
            }
            if(ObjectUtil.isNotEmpty(item.getBirthDay()) && !ComputeUtil.isValidDate(item.getBirthDay().trim())){
                errMsg += "生日不是合法日期格式；";
            }
            if(ObjectUtil.isNotEmpty(item.getHireDate()) && !ComputeUtil.isValidDate(item.getHireDate().trim())){
                errMsg += "入职日期不是合法日期格式；";
            }
            if(ObjectUtil.isNotEmpty(item.getPositiveTime()) && !ComputeUtil.isValidDate(item.getPositiveTime().trim())){
                errMsg += "转正日期不是合法日期格式；";
            }
            if(ObjectUtil.isNotEmpty(item.getLeaveDate()) && !ComputeUtil.isValidDate(item.getLeaveDate().trim())){
                errMsg += "离职日期不是合法日期格式；";
            }
            item.setErrorMsg(errMsg);
        }
        //数据匹配
        List<String> yearMonths = fieldBatch.getInfoList().stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getYearMonth()).distinct().collect(Collectors.toList());
        List<SalaryStandardVo> standardAllList = AesUtil.decryptGZList(getStandardAllList(yearMonths)); //密文->明文
        fieldBatch.getInfoList().stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<SalaryStandardVo> tmpList = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(item.getCompany())){
                tmpList = standardAllList.stream().filter(n->item.getYearMonth().equals(n.getYearMonth()) && item.getCompany().equals(n.getCompany()) && item.getIdCardNumber().equals(n.getIdCardNumber())).collect(Collectors.toList());
            }else{
                tmpList = standardAllList.stream().filter(n->item.getYearMonth().equals(n.getYearMonth()) && item.getIdCardNumber().equals(n.getIdCardNumber())).collect(Collectors.toList());
            }
            if(tmpList.size() == 1){
                item.setId(tmpList.get(0).getId());
            }else if(tmpList.size() == 0){
                item.setErrorMsg("月工资标准中未能匹配到用户；");
            }else{
                item.setErrorMsg("月工资标准中未能唯一匹配用户；");
            }
        });
        //导入数据 - 校验结果
        ImportResultT result = new ImportResultT();
        result.setTotal(fieldBatch.getInfoList().size());
        List<UpdateInfo> errList = fieldBatch.getInfoList().stream().filter(x->ObjectUtil.isNotEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setErrCount(errList.size());
        result.setResultList(fieldBatch.getInfoList());
        result.setSucCount(result.getTotal() - result.getErrCount());
        return result;
    }

    @Override
    public void doUpdate(UpdateFieldBatch fieldBatch) {
        List<UpdateInfo> tmpUpdateInfos = fieldBatch.getInfoList().stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        List<UpdateInfo> updateInfoList = AesUtil.encryptGZList(tmpUpdateInfos);
        if(CollUtil.isEmpty(fieldBatch.getFieldList()) || CollUtil.isEmpty(updateInfoList)){
            throw new CommonException("更新字段为空或有效更新数据为空");
        }
        //涉及到更新的历史数据
        List<String> ids = updateInfoList.stream().map(x->x.getId()).collect(Collectors.toList());
        List<SalaryStandard> oldList = this.listByIds(ids);
        //更新需要忽略的字段集合
        List<String> ignoreFields = EntityUtil.copyIgnoreFields(SalaryStandard.class,fieldBatch.getFieldList());
        for (SalaryStandard item:oldList){
            UpdateInfo tmpInfo = updateInfoList.stream().filter(x->x.getId().equals(item.getId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(tmpInfo)) {
                BeanUtil.copyProperties(tmpInfo, item, ignoreFields.toArray(new String[0]));
            }
        }
        this.updateBatchById(oldList);
    }
}
