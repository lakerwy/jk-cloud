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
package vip.xiaonuo.pan.modular.commonFile.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.commonFile.param.*;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFilePermissionVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonToMeVo;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFilePermission;

import java.util.List;

/**
 * 共享文件Service接口
 *
 * @author mayike
 * @date  2023/09/06 21:07
 **/
public interface PanCommonFileService extends IService<PanFilePermission> {
    /**
     * 将文件共享给他人
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    void saveCommon(CommonFileParam commonFileParam);

    /**
     * 共享文件，取消某些人的共享权限(支持批量)
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    void removeCommon(RemoveCommonParam removeCommonParam);

    /**
     * 获取某个文件，某个人的权限配置详情
     * @param commonUserParam
     * @return
     */
    CommonFilePermissionVo detailByObjId(CommonUserParam commonUserParam);

    /**
     * 获取某个共享文件的权限配置详情
     * @param userFileId
     * @return
     */
    List<CommonFilePermissionVo> detailByFileId(String userFileId);


    /**
     * 共享给我列表
     *
     * @author mayike
     * @date  2023/09/06 21:07
     **/
    Page<CommonToMeVo> commonToMePage(PanCommonFilePageParam panCommonFilePageParam);

    /**
     * 我的共享列表
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    Page<CommonFileVo> myCommonPage(PanCommonFilePageParam panCommonFilePageParam);
}
