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
import vip.xiaonuo.sys.modular.hr.registerCert.entity.ApplyRegCertData;
import vip.xiaonuo.sys.modular.hr.registerCert.param.*;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelRegInfoVo;

import java.util.List;

/**
 * 注册补贴-申请基本信息Service接口
 *
 * @author ljy
 * @date  2024/03/27 15:28
 **/
public interface ApplyRegCertDataService extends IService<ApplyRegCertData> {

    /**
     * 获取注册补贴-申请基本信息分页
     *
     * @author ljy
     * @date  2024/03/27 15:28
     */
    Page<ApplyRegCertData> page(ApplyRegCertDataPageParam applyRegCertDataPageParam);

    /**
     * 添加注册补贴-申请基本信息
     *
     * @author ljy
     * @date  2024/03/27 15:28
     */
    void add(ApplyRegCertDataAddParam applyRegCertDataAddParam);

    /**
     * 编辑注册补贴-申请基本信息
     *
     * @author ljy
     * @date  2024/03/27 15:28
     */
    void edit(ApplyRegCertDataEditParam applyRegCertDataEditParam);

    /**
     * 删除注册补贴-申请基本信息
     *
     * @author ljy
     * @date  2024/03/27 15:28
     */
    void delete(List<ApplyRegCertDataIdParam> applyRegCertDataIdParamList);

    /**
     * 获取注册补贴-申请基本信息详情
     *
     * @author ljy
     * @date  2024/03/27 15:28
     */
    ApplyRegCertData detail(ApplyRegCertDataIdParam applyRegCertDataIdParam);

    /**
     * 获取注册补贴-申请基本信息详情
     *
     * @author ljy
     * @date  2024/03/27 15:28
     **/
    ApplyRegCertData queryEntity(String id);

    /**
     * 获取注册补贴-获取基本信息和已有证书数据
     *
     * @author ljy
     * @date  2024/03/27 15:28
     **/
    List<PersonnelRegInfoVo> alwAndCertData(AlwAndCertParam alwAndCertParam);
}
