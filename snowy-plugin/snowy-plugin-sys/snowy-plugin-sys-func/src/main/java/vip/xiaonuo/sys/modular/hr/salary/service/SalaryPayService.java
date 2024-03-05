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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPay;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPayAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPayEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPayIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryPayPageParam;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryPayVo;

import java.util.List;

/**
 * 工资系统-工龄工资|生日福利Service接口
 *
 * @author yong.ye
 * @date  2024/02/26 09:34
 **/
public interface SalaryPayService extends IService<SalaryPay> {

    /**
     * 获取工资系统-工龄工资|生日福利分页
     *
     * @author yong.ye
     * @date  2024/02/26 09:34
     */
    Page<SalaryPayVo> page(SalaryPayPageParam salaryPayPageParam);

    /**
     * 添加工资系统-工龄工资|生日福利
     *
     * @author yong.ye
     * @date  2024/02/26 09:34
     */
    void add(SalaryPayAddParam salaryPayAddParam);

    /**
     * 编辑工资系统-工龄工资|生日福利
     *
     * @author yong.ye
     * @date  2024/02/26 09:34
     */
    void edit(SalaryPayEditParam salaryPayEditParam);

    /**
     * 删除工资系统-工龄工资|生日福利
     *
     * @author yong.ye
     * @date  2024/02/26 09:34
     */
    void delete(List<SalaryPayIdParam> salaryPayIdParamList);

    /**
     * 获取工资系统-工龄工资|生日福利详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:34
     */
    SalaryPayVo detail(SalaryPayIdParam salaryPayIdParam);

    /**
     * 获取工资系统-工龄工资|生日福利详情
     *
     * @author yong.ye
     * @date  2024/02/26 09:34
     **/
    SalaryPay queryEntity(String id);
}
