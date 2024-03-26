package vip.xiaonuo.sys.modular.hr.salary.util;

import vip.xiaonuo.sys.modular.hr.salary.enums.SalaryTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用配置
 */
public class CommConstant {


    /**
     * 员工类别 范围
     */
    public static final String[] USER_TYPE_LIST = {"总部","分公司","其他人员"};

    /**
     * 员工类别 范围 - 总部|分公司 匹配校验
     */
    public static final String[] USER_TYPE_CHECK_LIST = {"总部","分公司"};

    /**
     * 是否有生日福利|工龄工资 范围 - 否|是 匹配校验
     */
    public static final String[] YES_OR_NO_LIST = {"是","否"};

    /**
     * 需要通过 月工资标准表(SalaryStandard) 进行员工匹配的导入类型
     */
    public static final List<String> IMPORT_LIST = new ArrayList<>();
    static {
        IMPORT_LIST.add(SalaryTypeEnum.ATTEND.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.REGISTER_SUBSIDY.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.DUTY_SUBSIDY.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.TEACHER_SUBSIDY.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.OUTER_SUBSIDY.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.INS.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.GJJ.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.TAX.getValue());
        IMPORT_LIST.add(SalaryTypeEnum.EMP_BONUS.getValue());
    }

}
