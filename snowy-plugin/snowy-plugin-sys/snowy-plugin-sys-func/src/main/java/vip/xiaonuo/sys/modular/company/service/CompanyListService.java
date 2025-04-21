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
package vip.xiaonuo.sys.modular.company.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.company.entity.CompanyList;
import vip.xiaonuo.sys.modular.company.param.*;

import java.util.List;

/**
 * 建科公司列表(子公司、分公司)Service接口
 *
 * @author yong.ye
 * @date  2024/03/07 15:04
 **/
public interface CompanyListService extends IService<CompanyList> {

    /**
     * 获取建科公司列表(子公司、分公司)分页
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    Page<CompanyList> page(CompanyListPageParam companyListPageParam);

    /**
     * 添加建科公司列表(子公司、分公司)
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    void add(CompanyListAddParam companyListAddParam);

    /**
     * 编辑建科公司列表(子公司、分公司)
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    void edit(CompanyListEditParam companyListEditParam);

    /**
     * 删除建科公司列表(子公司、分公司)
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    void delete(List<CompanyListIdParam> companyListIdParamList);

    /**
     * 获取建科公司列表(子公司、分公司)详情
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     */
    CompanyList detail(CompanyListIdParam companyListIdParam);

    /**
     * 获取建科公司列表(子公司、分公司)详情
     *
     * @author yong.ye
     * @date  2024/03/07 15:04
     **/
    CompanyList queryEntity(String id);

    /**
     * 发送年报提醒
     *
     * @author pzh
     * @date  2025/04/21 10:04
     */
    void sendMsg(CompanyListMsgParam companyListMsgParam);
}
