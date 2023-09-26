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
package vip.xiaonuo.biz.modular.emp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpPerformance;
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpPerformanceVo;

import java.util.List;

/**
 * 业务表-员工绩效考核-季度评分审批流程Service接口
 *
 * @author yong.ye
 * @date  2023/09/19 08:45
 **/
public interface BizEmpPerformanceService extends IService<BizEmpPerformance> {

    /**
     * 获取业务表-员工绩效考核-季度评分审批流程分页
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    Page<BizEmpPerformance> page(BizEmpPerformancePageParam bizEmpPerformancePageParam);

    /**
     * 添加业务表-员工绩效考核-季度评分审批流程
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    void add(BizEmpPerformanceAddParam bizEmpPerformanceAddParam);

    /**
     * 编辑业务表-员工绩效考核-季度评分审批流程
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    void edit(BizEmpPerformanceEditParam bizEmpPerformanceEditParam);

    /**
     * 删除业务表-员工绩效考核-季度评分审批流程
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    void delete(List<BizEmpPerformanceIdParam> bizEmpPerformanceIdParamList);

    /**
     * 获取业务表-员工绩效考核-季度评分审批流程详情
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    BizEmpPerformance detail(BizEmpPerformanceIdParam bizEmpPerformanceIdParam);

    /**
     * 获取业务表-员工绩效考核-季度评分审批流程详情
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     **/
    BizEmpPerformance queryEntity(String id);

    /**
     * 根据 用户Id+考核年度+考核季度 校验当前员工本年本季度是否已经提交过绩效评估
     *
     * @author yong.ye
     * @date  2023/09/19 08:47
     */
    Boolean check(EmpCheckParam empCheckParam);

    /**
     * 获取业务表-员工绩效考核-季度评分审批流程分页（绩效考评统计表）
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    Page<BizEmpPerformanceVo> pageList(BizEmpPerformancePageParam bizEmpPerformancePageParam);

    /**
     * 更新字段
     *
     * @author yong.ye
     * @date  2023/09/19 08:45
     */
    void updateCol(EmpAssUpdateParam empAssUpdateParam);

}
