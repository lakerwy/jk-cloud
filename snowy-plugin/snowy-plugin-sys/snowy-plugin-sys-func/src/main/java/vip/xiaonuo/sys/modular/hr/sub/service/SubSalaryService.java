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
package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubSalary;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryAddParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryEditParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryIdParam;
import vip.xiaonuo.sys.modular.hr.sub.param.SubSalaryPageParam;

import java.util.List;

/**
 * HR-分公司-工资条Service接口
 *
 * @author yong.ye
 * @date  2023/11/22 11:20
 **/
public interface SubSalaryService extends IService<SubSalary> {

    /**
     * 获取HR-分公司-工资条分页
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    Page<SubSalary> page(SubSalaryPageParam subSalaryPageParam);

    /**
     * 添加HR-分公司-工资条
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    void add(SubSalaryAddParam subSalaryAddParam);

    /**
     * 编辑HR-分公司-工资条
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    void edit(SubSalaryEditParam subSalaryEditParam);

    /**
     * 删除HR-分公司-工资条
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    void delete(List<SubSalaryIdParam> subSalaryIdParamList);

    /**
     * 获取HR-分公司-工资条详情
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     */
    SubSalary detail(SubSalaryIdParam subSalaryIdParam);

    /**
     * 获取HR-分公司-工资条详情
     *
     * @author yong.ye
     * @date  2023/11/22 11:20
     **/
    SubSalary queryEntity(String id);
}
