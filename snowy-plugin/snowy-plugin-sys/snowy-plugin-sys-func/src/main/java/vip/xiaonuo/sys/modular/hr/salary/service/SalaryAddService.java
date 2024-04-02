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
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryAdd;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryAddPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.IdsParam;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryAddVo;

import java.util.List;

/**
 * 工资系统-其他费用扣加Service接口
 *
 * @author yong.ye
 * @date  2024/02/26 09:23
 **/
public interface SalaryAddService extends IService<SalaryAdd> {

    /**
     * 获取工资系统-其他费用扣加分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    Page<SalaryAddVo> page(SalaryAddPageParam salaryAddPageParam);

    /**
     * 添加工资系统-其他费用扣加
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    void add(SalaryAddAddParam salaryAddAddParam);

    /**
     * 编辑工资系统-其他费用扣加
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    void edit(SalaryAddEditParam salaryAddEditParam);

    /**
     * 删除工资系统-其他费用扣加
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    void delete(List<SalaryAddIdParam> salaryAddIdParamList);

    /**
     * 获取工资系统-其他费用扣加详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     */
    SalaryAddVo detail(SalaryAddIdParam salaryAddIdParam);

    /**
     * 获取工资系统-其他费用扣加详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:23
     **/
    SalaryAdd queryEntity(String id);

    /**
     * 复制上月
     */
    void copy(IdsParam idsParam);
}
