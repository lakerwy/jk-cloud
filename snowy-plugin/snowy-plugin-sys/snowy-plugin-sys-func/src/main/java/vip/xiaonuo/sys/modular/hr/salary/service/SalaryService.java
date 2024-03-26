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
import vip.xiaonuo.sys.modular.hr.salary.entity.Salary;
import vip.xiaonuo.sys.modular.hr.salary.param.*;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.UpdateFieldParam;

import java.util.List;

/**
 * 工资系统-工资表Service接口
 *
 * @author yong.ye
 * @date  2024/03/01 16:50
 **/
public interface SalaryService extends IService<Salary> {

    /**
     * 获取工资系统-工资表分页
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    Page<Salary> page(SalaryPageParam salaryPageParam);

    /**
     * 删除工资系统-工资表
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    void delete(List<SalaryIdParam> salaryIdParamList);

    /**
     * 获取工资系统-工资表详情
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     */
    Salary detail(SalaryIdParam salaryIdParam);

    /**
     * 获取工资系统-工资表详情
     *
     * @author yong.ye
     * @date  2024/03/01 16:50
     **/
    Salary queryEntity(String id);

    /**
     * 根据 所属年 或 所属年月 获取员工工资条
     * @param
     * @return
     */
    List<Salary> empSalary(SearchParam param);

    /**
     * 删除 工资表 已确认的数据 - 删除某年月数据
     * @param param
     */
    void deleteByMonth(SearchParam param);

    /**
     * 更新 特定 字段
     * @param fieldParam
     */
    void updateField(UpdateFieldParam fieldParam);
}
