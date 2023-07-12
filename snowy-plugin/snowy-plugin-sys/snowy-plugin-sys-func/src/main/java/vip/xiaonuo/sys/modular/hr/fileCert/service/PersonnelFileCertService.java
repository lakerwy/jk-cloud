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
package vip.xiaonuo.sys.modular.hr.fileCert.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.fileCert.entity.PersonnelFileCert;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertAddParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertEditParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertIdParam;
import vip.xiaonuo.sys.modular.hr.fileCert.param.PersonnelFileCertPageParam;

import java.util.List;

/**
 * 业务表-证书信息Service接口
 *
 * @author mayike
 * @date  2023/07/10 16:53
 **/
public interface PersonnelFileCertService extends IService<PersonnelFileCert> {

    /**
     * 获取业务表-证书信息分页
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    Page<PersonnelFileCert> page(PersonnelFileCertPageParam personnelFileCertPageParam);

    /**
     * 添加业务表-证书信息
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    void add(PersonnelFileCertAddParam personnelFileCertAddParam);

    /**
     * 编辑业务表-证书信息
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    void edit(PersonnelFileCertEditParam personnelFileCertEditParam);

    /**
     * 删除业务表-证书信息
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    void delete(List<PersonnelFileCertIdParam> personnelFileCertIdParamList);

    /**
     * 获取业务表-证书信息详情
     *
     * @author mayike
     * @date  2023/07/10 16:53
     */
    PersonnelFileCert detail(PersonnelFileCertIdParam personnelFileCertIdParam);

    /**
     * 获取业务表-证书信息详情
     *
     * @author mayike
     * @date  2023/07/10 16:53
     **/
    PersonnelFileCert queryEntity(String id);
}
