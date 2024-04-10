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
package vip.xiaonuo.sys.modular.hr.assetManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.assetManage.entity.JkAssetManage;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageAddParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageEditParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManageIdParam;
import vip.xiaonuo.sys.modular.hr.assetManage.param.JkAssetManagePageParam;

import java.util.List;

/**
 * 建科资产管理表Service接口
 *
 * @author ljy
 * @date  2024/04/10 08:47
 **/
public interface JkAssetManageService extends IService<JkAssetManage> {

    /**
     * 获取建科资产管理表分页
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    Page<JkAssetManage> page(JkAssetManagePageParam jkAssetManagePageParam);

    /**
     * 添加建科资产管理表
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    void add(JkAssetManageAddParam jkAssetManageAddParam);

    /**
     * 编辑建科资产管理表
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    void edit(JkAssetManageEditParam jkAssetManageEditParam);

    /**
     * 删除建科资产管理表
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    void delete(List<JkAssetManageIdParam> jkAssetManageIdParamList);

    /**
     * 获取建科资产管理表详情
     *
     * @author ljy
     * @date  2024/04/10 08:47
     */
    JkAssetManage detail(JkAssetManageIdParam jkAssetManageIdParam);

    /**
     * 获取建科资产管理表详情
     *
     * @author ljy
     * @date  2024/04/10 08:47
     **/
    JkAssetManage queryEntity(String id);
}
