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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryDict;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryDictAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryDictEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryDictIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryDictPageParam;

import java.util.List;

/**
 * 工资系统-其他费用扣加-数据字典Service接口
 *
 * @author yong.ye
 * @date  2024/02/26 09:27
 **/
public interface SalaryDictService extends IService<SalaryDict> {

    /**
     * 获取工资系统-其他费用扣加-数据字典分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:27
     */
    Page<SalaryDict> page(SalaryDictPageParam salaryDictPageParam);

    /**
     * 添加工资系统-其他费用扣加-数据字典
     *
     * @author yong.ye
     * @date  2024/02/26 09:27
     */
    void add(SalaryDictAddParam salaryDictAddParam);

    /**
     * 编辑工资系统-其他费用扣加-数据字典
     *
     * @author yong.ye
     * @date  2024/02/26 09:27
     */
    void edit(SalaryDictEditParam salaryDictEditParam);

    /**
     * 删除工资系统-其他费用扣加-数据字典
     *
     * @author yong.ye
     * @date  2024/02/26 09:27
     */
    void delete(List<SalaryDictIdParam> salaryDictIdParamList);

    /**
     * 获取工资系统-其他费用扣加-数据字典详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:27
     */
    SalaryDict detail(SalaryDictIdParam salaryDictIdParam);

    /**
     * 获取工资系统-其他费用扣加-数据字典详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:27
     **/
    SalaryDict queryEntity(String id);
}
