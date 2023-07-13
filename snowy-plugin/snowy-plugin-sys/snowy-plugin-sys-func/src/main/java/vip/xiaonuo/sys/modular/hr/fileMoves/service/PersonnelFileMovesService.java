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
package vip.xiaonuo.sys.modular.hr.fileMoves.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesAddParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesEditParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesIdParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesPageParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.vo.PersonnelFileMovesVo;

import java.util.List;

/**
 * 业务表-人员异动主表Service接口
 *
 * @author mayike
 * @date  2023/07/10 16:56
 **/
public interface PersonnelFileMovesService extends IService<PersonnelFileMoves> {

    /**
     * 获取业务表-人员异动主表分页
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    Page<PersonnelFileMoves> page(PersonnelFileMovesPageParam personnelFileMovesPageParam);

    /**
     * 添加业务表-人员异动主表
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    void add(PersonnelFileMovesAddParam personnelFileMovesAddParam);

    /**
     * 编辑业务表-人员异动主表
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    void edit(PersonnelFileMovesEditParam personnelFileMovesEditParam);

    /**
     * 删除业务表-人员异动主表
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    void delete(List<PersonnelFileMovesIdParam> personnelFileMovesIdParamList);

    /**
     * 获取业务表-人员异动主表详情
     *
     * @author mayike
     * @date  2023/07/10 16:56
     */
    PersonnelFileMoves detail(PersonnelFileMovesIdParam personnelFileMovesIdParam);

    /**
     * 获取业务表-人员异动主表详情
     *
     * @author mayike
     * @date  2023/07/10 16:56
     **/
    PersonnelFileMoves queryEntity(String id);

    void deleteByFileId(String id);

    void deleteNewFileId(List<String> ids);

    public Page<PersonnelFileMovesVo> pageList(PersonnelFileMovesPageParam personnelFileMovesPageParam);
}
