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
package vip.xiaonuo.biz.modular.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.assets.entity.AssetsBill;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillAddParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillEditParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillIdParam;
import vip.xiaonuo.biz.modular.assets.param.AssetsBillPageParam;
import vip.xiaonuo.biz.modular.assets.vo.AssetsBillVo;

import java.util.List;

/**
 * 固资申购 - 申请单 - 申购详情列表Service接口
 *
 * @author yong.ye
 * @date  2024/04/08 16:18
 **/
public interface AssetsBillService extends IService<AssetsBill> {

    /**
     * 获取固资申购 - 申请单 - 申购详情列表分页
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    Page<AssetsBillVo> page(AssetsBillPageParam assetsBillPageParam);

    /**
     * 添加固资申购 - 申请单 - 申购详情列表
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    void add(AssetsBillAddParam assetsBillAddParam);

    /**
     * 编辑固资申购 - 申请单 - 申购详情列表
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    void edit(AssetsBillEditParam assetsBillEditParam);

    /**
     * 删除固资申购 - 申请单 - 申购详情列表
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    void delete(List<AssetsBillIdParam> assetsBillIdParamList);

    /**
     * 获取固资申购 - 申请单 - 申购详情列表详情
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     */
    AssetsBill detail(AssetsBillIdParam assetsBillIdParam);

    /**
     * 获取固资申购 - 申请单 - 申购详情列表详情
     *
     * @author yong.ye
     * @date  2024/04/08 16:18
     **/
    AssetsBill queryEntity(String id);
}
