package vip.xiaonuo.sys.modular.hr.emp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.emp.entity.HrEmpAttend;
import vip.xiaonuo.sys.modular.hr.emp.mapper.HrEmpAttendMapper;
import vip.xiaonuo.sys.modular.hr.emp.param.*;
import vip.xiaonuo.sys.modular.hr.emp.service.HrEmpAttendService;
import vip.xiaonuo.sys.modular.hr.emp.vo.ImportResult;
import vip.xiaonuo.sys.modular.hr.salary.enums.UserTypeEnum;
import vip.xiaonuo.sys.modular.hr.salary.mapper.SalaryImportTempMapper;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelInfoVo;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工月度考勤表 - 单独 - 非工资系统Service接口实现类
 *
 * @author yong.ye
 * @date  2024/11/06 16:53
 **/
@Service
public class HrEmpAttendServiceImpl extends ServiceImpl<HrEmpAttendMapper, HrEmpAttend> implements HrEmpAttendService {

    @Resource
    private SalaryImportTempMapper salaryImportTempMapper;

    @Override
    public Page<HrEmpAttend> page(HrEmpAttendPageParam hrEmpAttendPageParam) {
        QueryWrapper<HrEmpAttend> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getYearMonth())) {
            queryWrapper.lambda().eq(HrEmpAttend::getYearMonth, hrEmpAttendPageParam.getYearMonth());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getUserType())) {
            queryWrapper.lambda().eq(HrEmpAttend::getUserType, hrEmpAttendPageParam.getUserType());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getIdCardNumber())) {
            queryWrapper.lambda().like(HrEmpAttend::getIdCardNumber, hrEmpAttendPageParam.getIdCardNumber());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getUserName())) {
            queryWrapper.lambda().like(HrEmpAttend::getUserName, hrEmpAttendPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getCompany())) {
            queryWrapper.lambda().like(HrEmpAttend::getCompany, hrEmpAttendPageParam.getCompany());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getUserId())) {
            queryWrapper.lambda().eq(HrEmpAttend::getUserId, hrEmpAttendPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getFirstOrgName())) {
            queryWrapper.lambda().like(HrEmpAttend::getFirstOrgName, hrEmpAttendPageParam.getFirstOrgName());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getOrgName())) {
            queryWrapper.lambda().like(HrEmpAttend::getOrgName, hrEmpAttendPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(hrEmpAttendPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(HrEmpAttend::getUserName,hrEmpAttendPageParam.getSearchKey())
                    .or().like(HrEmpAttend::getOrgName,hrEmpAttendPageParam.getSearchKey())
                    .or().like(HrEmpAttend::getPosition,hrEmpAttendPageParam.getSearchKey())
                    .or().like(HrEmpAttend::getIdCardNumber,hrEmpAttendPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(hrEmpAttendPageParam.getSortField(), hrEmpAttendPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hrEmpAttendPageParam.getSortOrder());
            queryWrapper.orderBy(true, hrEmpAttendPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hrEmpAttendPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(HrEmpAttend::getYearMonth).orderByAsc(HrEmpAttend::getIdCardNumber);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HrEmpAttendAddParam hrEmpAttendAddParam) {
        HrEmpAttend hrEmpAttend = BeanUtil.toBean(hrEmpAttendAddParam, HrEmpAttend.class);
        hrEmpAttend.setNote(DateUtil.now()+"：页面接口add新增；");
        this.save(hrEmpAttend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HrEmpAttendEditParam hrEmpAttendEditParam) {
        HrEmpAttend hrEmpAttend = this.queryEntity(hrEmpAttendEditParam.getId());
        BeanUtil.copyProperties(hrEmpAttendEditParam, hrEmpAttend);
        hrEmpAttend.setNote(DateUtil.now()+"：页面接口edit编辑；"+hrEmpAttend.getNote());
        this.updateById(hrEmpAttend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HrEmpAttendIdParam> hrEmpAttendIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hrEmpAttendIdParamList, HrEmpAttendIdParam::getId));
    }

    @Override
    public HrEmpAttend detail(HrEmpAttendIdParam hrEmpAttendIdParam) {
        return this.queryEntity(hrEmpAttendIdParam.getId());
    }

    @Override
    public HrEmpAttend queryEntity(String id) {
        HrEmpAttend hrEmpAttend = this.getById(id);
        if(ObjectUtil.isEmpty(hrEmpAttend)) {
            throw new CommonException("员工月度考勤表 - 单独 - 非工资系统不存在，id值为：{}", id);
        }
        return hrEmpAttend;
    }

    @Override
    public ImportResult importCheck(ImportParam importParam) {
        List<EmpAttendImportParam> importData = importParam.getImportData();
        checkData(importData);
        ImportResult result = new ImportResult();
        result.setTotal(importData.size());
        List<EmpAttendImportParam> errList = importData.stream().filter(x->ObjectUtil.isNotEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setErrCount(errList.size());
        result.setResultList(importData);
        result.setSucCount(result.getTotal() - result.getErrCount());
        return result;
    }

    @Override
    public Boolean doImport(ImportParam importParam) {
        List<EmpAttendImportParam> importData = importParam.getImportData().stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        if(CollUtil.isEmpty(importData)){
            throw new CommonException("无有效导入数据");
        }
        List<HrEmpAttend> hrEmpAttendList = BeanUtil.copyToList(importData, HrEmpAttend.class);
        hrEmpAttendList.forEach(x->x.setNote(DateUtil.now()+"：页面导入；"));
        this.saveBatch(hrEmpAttendList);
        return true;
    }

    /**
     * 通用校验（必填|数据格式校验|匹配）
     * @param importData
     * @return
     */
    public void checkData(List<EmpAttendImportParam> importData){
        //校验格式与必填
        importData.forEach(item->{
            if(ObjectUtil.isEmpty(item.getUserType())){
                item.setUserType(UserTypeEnum.ZB.getValue());
            }
            String errMsg = "";
            if(ObjectUtil.isEmpty(item.getYearMonth())){
                errMsg += "所属年月必填；";
            }
            if(ObjectUtil.isEmpty(item.getIdCardNumber())){
                errMsg += "身份证号码必填；";
            }
            if(ObjectUtil.isEmpty(item.getUserName())){
                errMsg += "员工姓名必填；";
            }
            item.setErrorMsg(errMsg);
        });
        List<EmpAttendImportParam> checkRepeatData = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<EmpAttendImportParam> tmpList = checkRepeatData.stream().filter(obj -> item.getYearMonth().equals(obj.getYearMonth()) && item.getIdCardNumber().equals(obj.getIdCardNumber()) && item.getUserName().equals(obj.getUserName())).collect(Collectors.toList());
            if(tmpList.size() > 1){
                item.setErrorMsg("导入数据中，所属年月+身份证号+姓名存在重复；");
            }
        });
        List<PersonnelInfoVo> personnelAllList = salaryImportTempMapper.getPersonnelList();
        importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
            List<PersonnelInfoVo> tmpPersonnelInfoList = personnelAllList.stream().filter(x->item.getUserType().equals(x.getUserType()) && item.getIdCardNumber().equals(x.getIdCardNumber()) && item.getUserName().equals(x.getUserName())).collect(Collectors.toList());
            if(CollUtil.isEmpty(tmpPersonnelInfoList)){
                item.setErrorMsg("身份证号+姓名未能匹配用户");
            }else if(tmpPersonnelInfoList.size()>1){
                item.setErrorMsg("身份证号+姓名未能唯一匹配用户");
            }else{
                PersonnelInfoVo tmpPersonnelInfo = tmpPersonnelInfoList.get(0);
                item.setUserId(tmpPersonnelInfo.getUserId());
                item.setPersonnelId(tmpPersonnelInfo.getId());
                if(ObjectUtil.isEmpty(item.getFirstOrgName())){
                    item.setFirstOrgName(tmpPersonnelInfo.getFirstOrgName());
                }
                if(ObjectUtil.isEmpty(item.getOrgName())){
                    item.setOrgName(tmpPersonnelInfo.getOrgName());
                }
                if(ObjectUtil.isEmpty(item.getPosition())){
                    item.setPosition(tmpPersonnelInfo.getPosition());
                }
            }
        });
        List<String> yearMonthList = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getYearMonth()).distinct().collect(Collectors.toList());
        if(CollUtil.isNotEmpty(yearMonthList)){
            List<HrEmpAttend> oldList = this.list(new LambdaQueryWrapper<HrEmpAttend>().in(HrEmpAttend::getYearMonth,yearMonthList));
            importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                List<HrEmpAttend> tmpList = oldList.stream().filter(obj -> item.getYearMonth().equals(obj.getYearMonth()) && item.getIdCardNumber().equals(obj.getIdCardNumber()) && item.getUserName().equals(obj.getUserName())).collect(Collectors.toList());
                if(tmpList.size() > 0){
                    item.setErrorMsg("所属年月+身份证号+姓名已存在；");
                }
            });
        }
    }
}
