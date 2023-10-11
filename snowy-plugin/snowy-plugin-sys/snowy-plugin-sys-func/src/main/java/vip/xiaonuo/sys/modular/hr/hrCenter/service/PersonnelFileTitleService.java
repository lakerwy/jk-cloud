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
package vip.xiaonuo.sys.modular.hr.hrCenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.hrCenter.entity.PersonnelFileTitle;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileTitleAddParam;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileTitleEditParam;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileTitleIdParam;
import vip.xiaonuo.sys.modular.hr.hrCenter.param.PersonnelFileTitlePageParam;

import java.util.List;

/**
 * 业务表-职称信息Service接口
 *
 * @author mayike
 * @date  2023/07/10 16:57
 **/
public interface PersonnelFileTitleService extends IService<PersonnelFileTitle> {

    /**
     * 获取业务表-职称信息分页
     *
     * @author mayike
     * @date  2023/07/10 16:57
     */
    Page<PersonnelFileTitle> page(PersonnelFileTitlePageParam personnelFileTitlePageParam);

    /**
     * 添加业务表-职称信息
     *
     * @author mayike
     * @date  2023/07/10 16:57
     */
    void add(PersonnelFileTitleAddParam personnelFileTitleAddParam);

    /**
     * 编辑业务表-职称信息
     *
     * @author mayike
     * @date  2023/07/10 16:57
     */
    void edit(PersonnelFileTitleEditParam personnelFileTitleEditParam);

    /**
     * 删除业务表-职称信息
     *
     * @author mayike
     * @date  2023/07/10 16:57
     */
    void delete(List<PersonnelFileTitleIdParam> personnelFileTitleIdParamList);

    /**
     * 获取业务表-职称信息详情
     *
     * @author mayike
     * @date  2023/07/10 16:57
     */
    PersonnelFileTitle detail(PersonnelFileTitleIdParam personnelFileTitleIdParam);

    /**
     * 获取业务表-职称信息详情
     *
     * @author mayike
     * @date  2023/07/10 16:57
     **/
    PersonnelFileTitle queryEntity(String id);

    void deleteByFileId(String id);

    void deleteNewFileId(List<String> ids);
}
