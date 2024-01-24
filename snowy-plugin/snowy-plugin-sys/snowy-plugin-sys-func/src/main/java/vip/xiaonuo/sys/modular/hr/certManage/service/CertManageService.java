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
package vip.xiaonuo.sys.modular.hr.certManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;
import vip.xiaonuo.sys.modular.hr.certManage.param.*;
import vip.xiaonuo.sys.modular.hr.certManage.vo.PersonVo;

import java.util.List;

/**
 * 人员资质管理Service接口
 *
 * @author yong.ye
 * @date  2024/01/23 17:23
 **/
public interface CertManageService extends IService<CertManage> {

    /**
     * 获取人员资质管理分页
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    Page<CertManage> page(CertManagePageParam certManagePageParam);

    /**
     * 添加人员资质管理
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    void add(CertManageAddParam certManageAddParam);

    /**
     * 编辑人员资质管理
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    void edit(CertManageEditParam certManageEditParam);

    /**
     * 删除人员资质管理
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    void delete(List<CertManageIdParam> certManageIdParamList);

    /**
     * 获取人员资质管理详情
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     */
    CertManage detail(CertManageIdParam certManageIdParam);

    /**
     * 获取人员资质管理详情
     *
     * @author yong.ye
     * @date  2024/01/23 17:23
     **/
    CertManage queryEntity(String id);

    /**
     * 总部花名册 & 分公司花名册 人员信息搜索
     * @param personParam
     * @return
     */
    List<PersonVo> list(PersonParam personParam);

    /**
     * 普通搜索+综合搜索（均支持）
     * @param certSearchPageParam
     * @return
     */
    Page<CertManage> searchPage(CertSearchPageParam certSearchPageParam);
}
