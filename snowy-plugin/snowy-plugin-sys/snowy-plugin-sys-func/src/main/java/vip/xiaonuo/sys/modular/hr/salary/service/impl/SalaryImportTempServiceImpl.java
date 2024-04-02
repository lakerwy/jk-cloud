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
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.sys.modular.hr.salary.entity.*;
import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryImportTempMapper;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.*;
import vip.xiaonuo.sys.modular.hr.salary.service.*;
import vip.xiaonuo.sys.modular.hr.salary.util.AesUtil;
import vip.xiaonuo.sys.modular.hr.salary.util.ComputeUtil;
import vip.xiaonuo.sys.modular.hr.salary.vo.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static vip.xiaonuo.common.consts.CacheConstant.ORG_FIRST_CODE_CACHE_KEY;
import static vip.xiaonuo.sys.modular.hr.salary.util.CommConstant.*;

/**
 * 工资系统-相关数据导入临时表Service接口实现类
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
@Service
public class SalaryImportTempServiceImpl extends ServiceImpl<SalaryImportTempMapper, SalaryImportTemp> implements SalaryImportTempService {
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private SalaryStandardService salaryStandardService;
    @Resource
    private SalaryAttendService salaryAttendService;
    @Resource
    private SalarySubsidyRecordService salarySubsidyRecordService;
    @Resource
    private SalaryInsService salaryInsService;
    @Resource
    private SalaryGjjService salaryGjjService;
    @Resource
    private SalaryTaxService salaryTaxService;
    @Resource
    private SalaryImportTempMapper salaryImportTempMapper;
    @Resource
    private SalaryPreviewService salaryPreviewService;
    @Resource
    private SalaryPayService salaryPayService;

    @Override
    public ImportResult importStandard(ImportStandard input) {
        //1.导入类型
        String type = SalaryTypeEnum.STANDARD.getValue();
        //2.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        //3.校验与导入
        return checkAndImport(type,importData);
    }

    @Override
    public ImportResult importAttend(ImportAttend input) {
        //1.导入类型
        String type = SalaryTypeEnum.ATTEND.getValue();
        //2.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        //3.校验与导入
        return checkAndImport(type,importData);
    }

    @Override
    public ImportResult importSubsidy(ImportSubsidy input) {
        //1.导入类型
        String type = input.getType();
        //2.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        //3.校验与导入
        return checkAndImport(type,importData);
    }

    @Override
    public ImportResult importInsGjjTax(ImportInsGjjTax input) {
        //1.导入类型
        String type = input.getType();
        //2.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        //3.校验与导入
        return checkAndImport(type,importData);
    }

    @Override
    public ImportResult importPay(ImportPay input) {
        //1.导入类型
        String type = SalaryTypeEnum.EMP_BONUS.getValue();
        //2.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        //3.校验与导入
        return checkAndImport(type,importData);
    }

    @Override
    public ImportResult importSalary(ImportSalary input) {
        //1.导入类型
        String type = SalaryTypeEnum.EMP_SALARY.getValue();
        //2.数据转换，数据初始值
        List<SalaryImportTemp> importData = BeanUtil.copyToList(input.getImportList(), SalaryImportTemp.class);
        //3.校验与导入
        return checkAndImport(type,importData);
    }

    /**
     * 导入至正式表
     * @param input
     * @return
     */
    @Override
    public Boolean doImport(DoImport input) {
        //1. 校验请求参数
        if(ObjectUtil.isEmpty(input.getBatchId()) || ObjectUtil.isEmpty(input.getBatchId())){
            throw new CommonException("参数异常");
        }
        //2. 判断本次导入是否有无误数据
        List<SalaryImportTemp> importTempList = this.list(new QueryWrapper<SalaryImportTemp>().lambda()
                .eq(SalaryImportTemp::getBatchId,input.getBatchId()).eq(SalaryImportTemp::getErrorMsg,""));
        if(CollUtil.isEmpty(importTempList)) {
            return true;
        }
        //3. 为导入数据，默认值处理
        List<SalaryImportTemp> importTempData = ComputeUtil.initImportData(input.getType(),importTempList);
        //月工资标准
        if(SalaryTypeEnum.STANDARD.getValue().equals(input.getType())){
            List<SalaryStandard> importData = BeanUtil.copyToList(importTempData,SalaryStandard.class);
            salaryStandardService.saveBatch(importData);
        }else if(SalaryTypeEnum.ATTEND.getValue().equals(input.getType())){
            List<SalaryAttend> importData = BeanUtil.copyToList(importTempData,SalaryAttend.class);
            salaryAttendService.saveBatch(importData);
        }else if(SalaryTypeEnum.REGISTER_SUBSIDY.getValue().equals(input.getType())
                || SalaryTypeEnum.DUTY_SUBSIDY.getValue().equals(input.getType())
                || SalaryTypeEnum.TEACHER_SUBSIDY.getValue().equals(input.getType())
                || SalaryTypeEnum.OUTER_SUBSIDY.getValue().equals(input.getType())){
            List<SalarySubsidyRecord> importData = BeanUtil.copyToList(importTempData,SalarySubsidyRecord.class);
            salarySubsidyRecordService.saveBatch(importData);
        }else if(SalaryTypeEnum.INS.getValue().equals(input.getType())){
            List<SalaryIns> importData = BeanUtil.copyToList(importTempData,SalaryIns.class);
            salaryInsService.saveBatch(importData);
        }else if(SalaryTypeEnum.GJJ.getValue().equals(input.getType())){
            List<SalaryGjj> importData = BeanUtil.copyToList(importTempData,SalaryGjj.class);
            salaryGjjService.saveBatch(importData);
        }else if(SalaryTypeEnum.TAX.getValue().equals(input.getType())){
            List<SalaryTax> importData = BeanUtil.copyToList(importTempData,SalaryTax.class);
            salaryTaxService.saveBatch(importData);
        }else if(SalaryTypeEnum.EMP_BONUS.getValue().equals(input.getType())){
            List<SalaryPay> importData = BeanUtil.copyToList(importTempData,SalaryPay.class);
            salaryPayService.saveBatch(importData);
        }else if(SalaryTypeEnum.EMP_SALARY.getValue().equals(input.getType())){
            List<SalaryPreview> importData = BeanUtil.copyToList(importTempData,SalaryPreview.class);
            salaryPreviewService.saveBatch(importData);
        }
        return true;
    }


    /**
     * 数据初始化，与去除空格
     * @param batchId
     * @param type
     * @param importData
     */
    public void initData(String batchId,String type,List<SalaryImportTemp> importData){
        importData.forEach(x->{
            x.setBatchId(batchId);
            x.setType(type);
            x.setNote("导入时间："+DateUtil.now().toString() +"，导入批次号："+batchId+"，导入类型："+type);
            if(ObjectUtil.isNotEmpty(x.getYearMonth())){
                x.setYearMonth(x.getYearMonth().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getIdCardNumber())){
                x.setIdCardNumber(x.getIdCardNumber().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getUserName())){
                x.setUserName(x.getUserName().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getUserType())){
                x.setUserType(x.getUserType().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getHaveBirthdayPay())){
                x.setHaveBirthdayPay(x.getHaveBirthdayPay().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getHaveSeniorityPay())){
                x.setHaveSeniorityPay(x.getHaveSeniorityPay().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getCompany())){
                x.setCompany(x.getCompany().trim());
            }else{
                x.setCompany("");
            }
            if(ObjectUtil.isNotEmpty(x.getUnitName())){
                x.setUnitName(x.getUnitName().trim());
            }else{
                x.setUnitName("");
            }
            if(ObjectUtil.isNotEmpty(x.getOrgName())){
                x.setOrgName(x.getOrgName().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getFirstOrgName())){
                x.setFirstOrgName(x.getFirstOrgName().trim());
            }
            if(ObjectUtil.isNotEmpty(x.getPosition())){
                x.setPosition(x.getPosition().trim());
            }
        });
    }


    /**
     * 通用校验（必填|数据格式校验）
     * @param importData
     * @return
     */
    public void checkData(List<SalaryImportTemp> importData){
        //第一步：校验必填
        importData.forEach(item->{
            String errMsg = "";
            if(ObjectUtil.isEmpty(item.getYearMonth())){
                errMsg += "所属年月必填；";
            }
            if(ObjectUtil.isEmpty(item.getIdCardNumber())){
                errMsg += "身份证号必填；";
            }
            if(ObjectUtil.isEmpty(item.getUserName())){
                errMsg += "姓名必填；";
            }
            //特殊控制
            if(SalaryTypeEnum.STANDARD.getValue().equals(item.getType())){
                if(ObjectUtil.isEmpty(item.getCompany())) {
                    errMsg += "工资发放公司必填；";
                }
                if(ObjectUtil.isEmpty(item.getUserType())){
                    errMsg += "员工类别必填；";
                }
            }
            if(SalaryTypeEnum.EMP_BONUS.getValue().equals(item.getType())){
                if(ObjectUtil.isEmpty(item.getItemType())) {
                    errMsg += "导入员工福利，福利类别必填；";
                }else{
                    if(SalaryTypeEnum.BIRTHDAY.getValue().equals(item.getItemType())){
                        errMsg += "不可导入生日福利；";
                    }
                }
            }
            if(SalaryTypeEnum.INS.getValue().equals(item.getType())
                    || SalaryTypeEnum.GJJ.getValue().equals(item.getType())
                    || SalaryTypeEnum.TAX.getValue().equals(item.getType())){
                if(ObjectUtil.isEmpty(item.getUnitName())){
                    errMsg += "缴纳单位必填；";
                }
            }
            if(SalaryTypeEnum.EMP_SALARY.getValue().equals(item.getType())){
                if(ObjectUtil.isEmpty(item.getCompany())){
                    errMsg += "工资发放公司必填；";
                }
                if(ObjectUtil.isEmpty(item.getUserType())){
                    errMsg += "员工类别必填；";
                }
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
            if(ObjectUtil.isNotEmpty(item.getIsFull()) && !CollectionUtil.newArrayList(YES_OR_NO_LIST).contains(item.getIsFull().trim())){
                errMsg += "是否全勤必须为：" + StrUtil.join("、",YES_OR_NO_LIST) +"；";
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
        });
        //第二步：校验数据重复性
        List<SalaryImportTemp> checkRepeatData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            String errMsg = "";
            // 所属公司、身份证号 唯一
            if(SalaryTypeEnum.STANDARD.getValue().equals(item.getType())){
                List<SalaryImportTemp> tmpFilterRepeat = checkRepeatData.stream().filter(obj->item.getCompany().equals(obj.getCompany())
                        && item.getIdCardNumber().equals(obj.getIdCardNumber())).collect(Collectors.toList());
                if(tmpFilterRepeat.size() > 1){
                    errMsg += "同一工资发放公司、身份证号存在重复记录";
                }
            }
            // 所属公司、缴纳单位、身份证号 唯一
            if(SalaryTypeEnum.INS.getValue().equals(item.getType())
                    || SalaryTypeEnum.GJJ.getValue().equals(item.getType())
                    || SalaryTypeEnum.TAX.getValue().equals(item.getType())){
                List<SalaryImportTemp> tmpFilterRepeat = checkRepeatData.stream().filter(obj->item.getCompany().equals(obj.getCompany())
                        && item.getUnitName().equals(obj.getUnitName()) && item.getIdCardNumber().equals(obj.getIdCardNumber())).collect(Collectors.toList());
                if(tmpFilterRepeat.size() > 1){
                    errMsg += "同一所属公司、缴纳单位、身份证号存在重复记录";
                }
            }
            // 所属公司、身份证号 唯一
            if(SalaryTypeEnum.ATTEND.getValue().equals(item.getType())){
                List<SalaryImportTemp> tmpFilterRepeat = checkRepeatData.stream().filter(obj->item.getCompany().equals(obj.getCompany())
                        && item.getIdCardNumber().equals(obj.getIdCardNumber())).collect(Collectors.toList());
                if(tmpFilterRepeat.size() > 1){
                    errMsg += "同一所属公司、身份证号存在重复记录";
                }
            }
            item.setErrorMsg(errMsg);
        });
    }


    /**
     * 逻辑校验：员工匹配
     *
     * 导入规则：
     * 月工资标准：根据userType，与系统 总部花名册|分公司花名册 进行匹配，获取信息（原则，若有导入数据，以导入数据为准，若无，根据花名册获取）
     * 补助津贴|考勤|社保|公积金|加计扣除（即除导入月工资标准外），均以工资标准表为基准，进行人员匹配
     *
     * 匹配员工数据
     *          * 第一部分：若导入 月工资标准 ，【总部|分公司】 根据花名册信息匹配信息
     *          * 第二部分 非 月工资标准 ，均按照月工资标准表，匹配用户
     *
     * @param type 导入类型
     * @param importData 导入数据，此时还是明文
     */
    public void matchLogic(String type,List<SalaryImportTemp> importData){
        /**
         * 1. 准备数据【花名册|工资标准数据】
         */
        List<PersonnelInfoVo> personnelAllList = new ArrayList<>();
        List<SalaryStandardVo> standardAllList = new ArrayList<>();
        if(SalaryTypeEnum.STANDARD.getValue().equals(type)
                || SalaryTypeEnum.EMP_SALARY.getValue().equals(type))
        {
            personnelAllList = salaryImportTempMapper.getPersonnelList(); //明文
        }
        if(IMPORT_LIST.contains(type)){
            List<String> yearMonths = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getYearMonth()).distinct().collect(Collectors.toList());
            standardAllList = salaryStandardService.getStandardAllList(yearMonths); //密文
        }
        //加密参数用于匹配
        List<PersonnelInfoVo> finalPersonnelAllList = personnelAllList; //明文
        List<SalaryStandardVo> finalStandardAllList = AesUtil.decryptGZList(standardAllList); //身份证->明文
        /**
         * 2. 匹配员工数据
         * 第一部分：若导入 月工资标准 ，【总部|分公司】 根据花名册信息匹配信息
         * 第二部分 非 月工资标准 ，均按照月工资标准表，匹配用户
         */
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            if(SalaryTypeEnum.STANDARD.getValue().equals(item.getType())
                    || SalaryTypeEnum.EMP_SALARY.getValue().equals(item.getType()))
            {
                if(CollectionUtil.newArrayList(USER_TYPE_CHECK_LIST).contains(item.getUserType())) {
                    List<PersonnelInfoVo> personnelInfoVos = finalPersonnelAllList.stream().filter(m -> item.getUserType().equals(m.getUserType()) && item.getIdCardNumber().equals(m.getIdCardNumber())).collect(Collectors.toList());
                    if (personnelInfoVos.size() == 1) {
                        PersonnelInfoVo personnel = personnelInfoVos.get(0);
                        item.setUserId(personnel.getUserId());
                        item.setPersonnelId(personnel.getId());
                        //日期
                        if (ObjectUtil.isEmpty(item.getHireDate())) {
                            item.setHireDate(DateUtil.formatDate(personnel.getHireDate()));
                        }
                        if (ObjectUtil.isEmpty(item.getPositiveTime())) {
                            item.setPositiveTime(DateUtil.formatDate(personnel.getPositiveTime()));
                        }
                        if (ObjectUtil.isEmpty(item.getBirthDay())) {
                            item.setBirthDay(DateUtil.formatDate(personnel.getBirthDay()));
                        }
                        if (ObjectUtil.isEmpty(item.getLeaveDate())) {
                            item.setLeaveDate(personnel.getLeaveDate());
                        }
                        //部门 职位
                        if (ObjectUtil.isEmpty(item.getOrgName())) {
                            item.setOrgName(personnel.getOrgName());
                        }
                        if (ObjectUtil.isEmpty(item.getPosition())) {
                            item.setPosition(personnel.getPosition());
                        }
                        if (ObjectUtil.isEmpty(item.getFirstOrgName())) {
                            item.setFirstOrgName(ComputeUtil.objToString(commonCacheOperator.get(ORG_FIRST_CODE_CACHE_KEY + "orgName" + personnel.getOrgId())));
                        }
                    } else if (personnelInfoVos.size() == 0) {
                        item.setErrorMsg("花名册未能匹配到用户");
                    } else {
                        item.setErrorMsg("花名册未能唯一匹配用户；");
                    }
                }
            }
            if(IMPORT_LIST.contains(item.getType())){
                // 匹配 月工资标准 人员 【1.未导入所属公司，默认用身份证唯一匹配  2.导入所属公司，根据所属公司+身份证唯一匹配 】
                List<SalaryStandardVo> standardList = new ArrayList<>();
                if(ObjectUtil.isNotEmpty(item.getCompany())){
                    standardList = finalStandardAllList.stream().filter(n->item.getYearMonth().equals(n.getYearMonth()) && item.getCompany().equals(n.getCompany()) && item.getIdCardNumber().equals(n.getIdCardNumber())).collect(Collectors.toList());
                }else{
                    standardList = finalStandardAllList.stream().filter(n->item.getYearMonth().equals(n.getYearMonth()) && item.getIdCardNumber().equals(n.getIdCardNumber())).collect(Collectors.toList());
                }
                if(standardList.size() == 1){
                    SalaryStandardVo tmpVo = standardList.get(0);
                    item.setStandardId(tmpVo.getId());
                    if(SalaryTypeEnum.EMP_BONUS.getValue().equals(type) && ObjectUtil.isEmpty(item.getUnitName())){ //福利发放公司，默认为所属公司
                        item.setUnitName(tmpVo.getCompany());
                    }
                }else if(standardList.size() == 0){
                    item.setErrorMsg("月工资标准中未能匹配到用户");
                }else{
                    item.setErrorMsg("月工资标准中未能唯一匹配用户");
                }
            }
        });
    }



    /**
     * 逻辑校验：员工匹配|唯一性校验 【导入数据变密文】
     *
     * 校验数据唯一性【每月每人一条记录】
     *          * 第一部分 导入月工资标准 校验是否已存在数据（每人每月一条导入记录）--【所属年月+身份证号】
     *          * 第二部分 导入月度考勤|社保|公积金|加计扣除 校验是否已存在数据（每人每月一条导入记录） -- 【月工资标准匹配结果 - standardId】
     *
     * @param type
     * @param importData
     */
    public void checkLogic(String type,List<SalaryImportTemp> importData){
        //明文 -> 密文
        importData = AesUtil.encryptGZList(importData);
        //校验
        if(SalaryTypeEnum.STANDARD.getValue().equals(type)){ //工资发放公司一定有值
            List<String> yearMonths = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getYearMonth()).distinct().collect(Collectors.toList());
            if(CollUtil.isNotEmpty(yearMonths)) {
                //涉及到的年月，看是否已导入过
                List<SalaryStandardVo> oldList = salaryStandardService.getStandardAllList(yearMonths);
                if (CollUtil.isNotEmpty(oldList)) {
                    importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
                        List<SalaryStandardVo> tmpList = oldList.stream().filter(obj -> item.getYearMonth().equals(obj.getYearMonth()) && item.getCompany().equals(obj.getCompany()) && item.getIdCardNumber().equals(obj.getIdCardNumber())).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(tmpList)) {
                            item.setErrorMsg("员工已存在当月当前公司数据；");
                        }
                    });
                }
            }
        }else if(SalaryTypeEnum.ATTEND.getValue().equals(type)){
            List<String> standardIds = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getStandardId()).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(standardIds)) {
                List<SalaryAttend> oldList = salaryAttendService.list(new QueryWrapper<SalaryAttend>().lambda().in(SalaryAttend::getStandardId,standardIds));
                if(CollUtil.isNotEmpty(oldList)){
                    importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                        List<SalaryAttend> tmpList = oldList.stream().filter(obj->item.getStandardId().equals(obj.getStandardId())).collect(Collectors.toList());
                        if(CollUtil.isNotEmpty(tmpList)){
                            item.setErrorMsg("员工已存在当月数据；");
                        }
                    });
                }
            }
        }else if(SalaryTypeEnum.INS.getValue().equals(type)) {  //同一人同一公司同一月一条
            List<String> standardIds = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getStandardId()).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(standardIds)) {
                List<SalaryIns> oldList = salaryInsService.list(new QueryWrapper<SalaryIns>().lambda().in(SalaryIns::getStandardId, standardIds));
                if (CollUtil.isNotEmpty(oldList)) {
                    importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
                        List<SalaryIns> tmpList = oldList.stream().filter(obj -> item.getStandardId().equals(obj.getStandardId()) && item.getUnitName().equals(obj.getUnitName())).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(tmpList)) {
                            item.setErrorMsg("员工已存在当月当前公司数据；");
                        }
                    });
                }
            }
        } else if (SalaryTypeEnum.GJJ.getValue().equals(type)) {  //同一人同一公司同一月一条
            List<String> standardIds = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getStandardId()).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(standardIds)) {
                List<SalaryGjj> oldList = salaryGjjService.list(new QueryWrapper<SalaryGjj>().lambda().in(SalaryGjj::getStandardId, standardIds));
                if (CollUtil.isNotEmpty(oldList)) {
                    importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
                        List<SalaryGjj> tmpList = oldList.stream().filter(obj -> item.getStandardId().equals(obj.getStandardId()) && item.getUnitName().equals(obj.getUnitName())).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(tmpList)) {
                            item.setErrorMsg("员工已存在当月当前公司数据；");
                        }
                    });
                }
            }
        } else if (SalaryTypeEnum.TAX.getValue().equals(type)) { //同一人同一公司同一月一条
            List<String> standardIds = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getStandardId()).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(standardIds)) {
                List<SalaryTax> oldList = salaryTaxService.list(new QueryWrapper<SalaryTax>().lambda().in(SalaryTax::getStandardId, standardIds));
                if (CollUtil.isNotEmpty(oldList)) {
                    importData.stream().filter(x -> ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item -> {
                        List<SalaryTax> tmpList = oldList.stream().filter(obj -> item.getStandardId().equals(obj.getStandardId()) && item.getUnitName().equals(obj.getUnitName())).collect(Collectors.toList());
                        if (CollUtil.isNotEmpty(tmpList)) {
                            item.setErrorMsg("员工已存在当月当前公司数据；");
                        }
                    });
                }
            }
        }
    }

    /**
     * 通用导入：校验+导入到临时表
     * @param type
     * @param importData
     * @return
     */
    public ImportResult checkAndImport(String type,List<SalaryImportTemp> importData){
        //1.定义一个导入批次号
        String batchId = IdWorker.getTimeId();
        //2.初始化部分信息
        initData(batchId,type,importData);
        //3.数据校验（必填项|数据格式|数据重复）
        checkData(importData);
        //4.员工匹配
        matchLogic(type,importData);
        //5.逻辑校验（唯一校验）
        checkLogic(type,importData);
        //6.将数据存放在导入临时表
        this.saveBatch(importData);
        //7.返回结果
        return importResult(batchId,type,importData);
    }

    /**
     * 生成 导入结果
     * @param batchId 导入批次号
     * @param type 导入类型
     * @param importData 导入数据集
     * @return
     */
    public ImportResult importResult(String batchId,String type,List<SalaryImportTemp> importData){
        //密文->明文
        importData = AesUtil.decryptGZList(importData);
        ImportResult result = new ImportResult();
        result.setBatchId(batchId);
        result.setType(type);
        result.setTotal(importData.size());
        List<SalaryImportTemp> errList = importData.stream().filter(x->ObjectUtil.isNotEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setErrCount(errList.size());
        result.setResultList(importData);
        result.setSucCount(result.getTotal() - result.getErrCount());
        return result;
    }
}
