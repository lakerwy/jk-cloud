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
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryCost;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostAddParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostEditParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostIdParam;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryCostPageParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.ReportParam;
import vip.xiaonuo.sys.modular.hr.salary.param.customer.SearchParam;
import vip.xiaonuo.sys.modular.hr.salary.vo.CostReportVo;

import java.util.List;

/**
 * 工资系统 - 成本划分 - 月度个人统计Service接口
 *
 * @author yong.ye
 * @date  2024/06/19 15:06
 **/
public interface SalaryCostService extends IService<SalaryCost> {

    /**
     * 获取工资系统 - 成本划分 - 月度个人统计分页
     *
     * @author yong.ye
     * @date  2024/06/19 15:06
     */
    Page<SalaryCost> page(SalaryCostPageParam salaryCostPageParam);

    /**
     * 添加工资系统 - 成本划分 - 月度个人统计
     *
     * @author yong.ye
     * @date  2024/06/19 15:06
     */
    void add(SalaryCostAddParam salaryCostAddParam);

    /**
     * 编辑工资系统 - 成本划分 - 月度个人统计
     *
     * @author yong.ye
     * @date  2024/06/19 15:06
     */
    void edit(SalaryCostEditParam salaryCostEditParam);

    /**
     * 删除工资系统 - 成本划分 - 月度个人统计
     *
     * @author yong.ye
     * @date  2024/06/19 15:06
     */
    void delete(List<SalaryCostIdParam> salaryCostIdParamList);

    /**
     * 获取工资系统 - 成本划分 - 月度个人统计详情
     *
     * @author yong.ye
     * @date  2024/06/19 15:06
     */
    SalaryCost detail(SalaryCostIdParam salaryCostIdParam);

    /**
     * 获取工资系统 - 成本划分 - 月度个人统计详情
     *
     * @author yong.ye
     * @date  2024/06/19 15:06
     **/
    SalaryCost queryEntity(String id);


    /**
     * 生成 某年月 个人成本划分数据
     * @param param
     */
    void genPerCost(SearchParam param);

    /**
     * 查询 统计报表
     * @param reportParam
     * @return
     */
    List<CostReportVo> genCostReport(ReportParam reportParam);
}
