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
package vip.xiaonuo.sys.modular.hr.registerCert.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.registerCert.entity.ApplyRegCertDataFlow;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowAddParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowEditParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowIdParam;
import vip.xiaonuo.sys.modular.hr.registerCert.param.ApplyRegCertDataFlowPageParam;

import java.util.List;

/**
 * 注册补贴-申请FLW流程表信息Service接口
 *
 * @author ljy
 * @date  2024/03/28 15:48
 **/
public interface ApplyRegCertDataFlowService extends IService<ApplyRegCertDataFlow> {

    /**
     * 获取注册补贴-申请FLW流程表信息分页
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    Page<ApplyRegCertDataFlow> page(ApplyRegCertDataFlowPageParam applyRegCertDataFlowPageParam);

    /**
     * 添加注册补贴-申请FLW流程表信息
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    void add(ApplyRegCertDataFlowAddParam applyRegCertDataFlowAddParam);

    /**
     * 编辑注册补贴-申请FLW流程表信息
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    void edit(ApplyRegCertDataFlowEditParam applyRegCertDataFlowEditParam);

    /**
     * 删除注册补贴-申请FLW流程表信息
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    void delete(List<ApplyRegCertDataFlowIdParam> applyRegCertDataFlowIdParamList);

    /**
     * 获取注册补贴-申请FLW流程表信息详情
     *
     * @author ljy
     * @date  2024/03/28 15:48
     */
    ApplyRegCertDataFlow detail(ApplyRegCertDataFlowIdParam applyRegCertDataFlowIdParam);

    /**
     * 获取注册补贴-申请FLW流程表信息详情
     *
     * @author ljy
     * @date  2024/03/28 15:48
     **/
    ApplyRegCertDataFlow queryEntity(String id);
}
