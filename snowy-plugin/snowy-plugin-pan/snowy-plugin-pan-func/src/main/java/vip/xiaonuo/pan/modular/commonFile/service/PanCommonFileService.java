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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.commonFile.entity.PanCommonFile;
import vip.xiaonuo.pan.modular.commonFile.param.*;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileUserVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileVo;

import java.util.List;

/**
 * 共享文件Service接口
 *
 * @author mayike
 * @date  2023/09/06 21:07
 **/
public interface PanCommonFileService extends IService<PanCommonFile> {

    /**
     * 获取共享文件分页
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    Page<PanCommonFile> page(PanCommonFilePageParam panCommonFilePageParam);

    /**
     * 添加共享文件
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    void add(PanCommonFileAddParam panCommonFileAddParam);

    /**
     * 编辑共享文件
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    void edit(PanCommonFileEditParam panCommonFileEditParam);

    /**
     * 删除共享文件
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    void delete(List<PanCommonFileIdParam> panCommonFileIdParamList);

    /**
     * 获取共享文件详情
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    PanCommonFile detail(PanCommonFileIdParam panCommonFileIdParam);

    /**
     * 获取共享文件详情
     *
     * @author mayike
     * @date  2023/09/06 21:07
     **/
    PanCommonFile queryEntity(String id);

    /**
     * 将文件共享给他人
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    void commonFile(CommonFileParam commonFileParam);

    /**
     * 获取共享空间的全量用户列表
     *
     * @author mayike
     * @date  2023/09/06 21:07
     **/
    List<CommonFileUserVo> commonFileUser();

    /**
     * 获取共享用户的文件列表
     *
     * @author mayike
     * @date  2023/09/06 21:07
     **/
    List<CommonFileVo> commonFileByUser(String userId);

    /**
     * 获取共享空间中某个用户的文件列表
     *
     * @author mayike
     * @date  2023/09/06 21:07
     */
    IPage<CommonFileVo> commonFileList(PanCommonFilePageParam panCommonFilePageParam);
}
