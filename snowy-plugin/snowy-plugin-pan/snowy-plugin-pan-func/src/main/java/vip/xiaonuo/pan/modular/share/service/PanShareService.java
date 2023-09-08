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
package vip.xiaonuo.pan.modular.share.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.share.entity.PanShare;
import vip.xiaonuo.pan.modular.share.param.*;
import vip.xiaonuo.pan.modular.share.param.dto.SaveShareFileDto;
import vip.xiaonuo.pan.modular.share.param.dto.ShareFileDto;
import vip.xiaonuo.pan.modular.share.vo.ShareFileVo;

import java.util.List;

/**
 * 网盘-分享表Service接口
 *
 * @author yong.ye
 * @date  2023/09/07 15:55
 **/
public interface PanShareService extends IService<PanShare> {

    /**
     * 获取网盘-分享表分页
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    Page<PanShare> page(PanSharePageParam panSharePageParam);

    /**
     * 添加网盘-分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    void add(PanShareAddParam panShareAddParam);

    /**
     * 编辑网盘-分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    void edit(PanShareEditParam panShareEditParam);

    /**
     * 删除网盘-分享表
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    void delete(List<PanShareIdParam> panShareIdParamList);

    /**
     * 获取网盘-分享表详情
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    PanShare detail(PanShareIdParam panShareIdParam);

    /**
     * 获取网盘-分享表详情
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     **/
    PanShare queryEntity(String id);

    /**
     * 网盘-分享-分享文件
     *
     * （分享文件统一接口）
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    ShareFileVo shareFile(ShareFileDto shareFileDto);

    /**
     * 网盘-分享-保存分享文件
     *
     * (用来将别人分享的文件保存到自己的网盘中)
     *
     * @author yong.ye
     * @date  2023/09/07 15:55
     */
    void saveShareFile(SaveShareFileDto saveShareFileDto);
}
