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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpAssTotalVo;
import vip.xiaonuo.biz.modular.emp.vo.BizEmpTaskFlwVo;

import java.util.List;

/**
 * 业务表-员工绩效考核-季度任务流程审批单Service接口
 *
 * @author yong.ye
 * @date  2023/09/19 08:48
 **/
public interface BizEmpTaskFlwService extends IService<BizEmpTaskFlw> {

    /**
     * 获取业务表-员工绩效考核-季度任务流程审批单分页
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    Page<BizEmpTaskFlw> page(BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam);

    /**
     * 添加业务表-员工绩效考核-季度任务流程审批单
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    void add(BizEmpTaskFlwAddParam bizEmpTaskFlwAddParam);

    /**
     * 编辑业务表-员工绩效考核-季度任务流程审批单
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    void edit(BizEmpTaskFlwEditParam bizEmpTaskFlwEditParam);

    /**
     * 删除业务表-员工绩效考核-季度任务流程审批单
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    void delete(List<BizEmpTaskFlwIdParam> bizEmpTaskFlwIdParamList);

    /**
     * 获取业务表-员工绩效考核-季度任务流程审批单详情
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    BizEmpTaskFlw detail(BizEmpTaskFlwIdParam bizEmpTaskFlwIdParam);

    /**
     * 获取业务表-员工绩效考核-季度任务流程审批单详情
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     **/
    BizEmpTaskFlw queryEntity(String id);


    /**
     * 获取业务表-员工绩效考核-季度任务流程审批单分页
     *
     * @author yong.ye
     * @date  2023/09/19 08:48
     */
    IPage<BizEmpTaskFlwVo> pageList(BizEmpTaskFlwPageParam bizEmpTaskFlwPageParam);

    /**
     * 统计 年度+季度+组织+考核等级
     * @param empAssTotalParam
     * @return
     */
    BizEmpAssTotalVo total(EmpAssTotalParam empAssTotalParam);
}
