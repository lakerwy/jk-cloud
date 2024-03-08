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
package vip.xiaonuo.sys.modular.hr.salary.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryImportTemp;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.*;
import vip.xiaonuo.sys.modular.hr.salary.vo.*;

import java.util.List;

/**
 * 工资系统-相关数据导入临时表Service接口
 *
 * @author yong.ye
 * @date  2024/02/26 09:31
 **/
public interface SalaryImportTempService extends IService<SalaryImportTemp> {

    /**
     * 获取工资系统-相关数据导入临时表分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:31
     */
    Page<SalaryImportTemp> page(SalaryImportTempPageParam salaryImportTempPageParam);

    /**
     * 删除工资系统-相关数据导入临时表
     *
     * @author yong.ye
     * @date  2024/02/26 09:31
     */
    void delete(List<SalaryImportTempIdParam> salaryImportTempIdParamList);

    /**
     * 获取工资系统-相关数据导入临时表详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:31
     */
    SalaryImportTemp detail(SalaryImportTempIdParam salaryImportTempIdParam);

    /**
     * 获取工资系统-相关数据导入临时表详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:31
     **/
    SalaryImportTemp queryEntity(String id);

    /**
     * 【导入】- 社保|公积金|专项扣除数据
     */
    ImportResult importInsGjjTax(ImportInsGjjTax input);

    /**
     * 【导入】- 月工资标准数据
     */
    ImportResult importStandard(ImportStandard input);

    /**
     * 【导入】- 考勤数据
     */
    ImportResult importAttend(ImportAttend input);

    /**
     * 【导入】- 补贴数据
     */
    ImportResult importSubsidy(ImportSubsidy input);

    /**
     * 确认导入 - 正式入库
     * @param input
     * @return
     */
    Boolean doImport(DoImport input);

    /**
     * 【导入】- 员工福利（除生日福利）
     */
    ImportResult importPay(ImportPay input);

    /**
     * 【导入】- 已确认的 员工工资 数据  HR_SALARY
     * @param input
     * @return
     */
    ImportResult importSalary(ImportSalary input);
}
