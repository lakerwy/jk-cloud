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
package vip.xiaonuo.sys.modular.hr.salary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryCost;
import vip.xiaonuo.sys.modular.hr.salary.vo.CostReportVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryCostVo;

import java.util.List;

/**
 * 工资系统 - 成本划分 - 月度个人统计Mapper接口
 *
 * @author yong.ye
 * @date  2024/06/19 15:06
 **/
public interface SalaryCostMapper extends BaseMapper<SalaryCost> {

    /**
     * 【封存工资-数据源】根据年月 获取成本划分基础数据【成本划分-汇总-个人】
     * @param yearMonth
     * @return
     */
    List<SalaryCostVo> getSalaryCost(String yearMonth);

    /**
     * 【预览工资-数据源】根据年月 获取成本划分基础数据【成本划分-汇总-个人】
     * @param yearMonth
     * @return
     */
    List<SalaryCostVo> getPreviewCost(String yearMonth);

    /**
     * 生成各维度的成本报表
     * @param yearMonth 年月
     * @param reportType 报表维度
     * @return
     */
    List<CostReportVo> getCostReport(String yearMonth,String reportType,String dataSource);
}
