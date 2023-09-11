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
package vip.xiaonuo.pan.modular.recoveryfile.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.recoveryfile.entity.PanRecoveryFile;
import vip.xiaonuo.pan.modular.recoveryfile.param.*;
import vip.xiaonuo.pan.modular.recoveryfile.param.dto.BatchDeleteDto;
import vip.xiaonuo.pan.modular.recoveryfile.vo.RecoveryFileVo;

import java.util.List;

/**
 * 网盘-回收站Service接口
 *
 * @author yong.ye
 * @date  2023/09/07 15:54
 **/
public interface PanRecoveryFileService extends IService<PanRecoveryFile> {

    /**
     * 获取网盘-回收站分页
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    Page<PanRecoveryFile> page(PanRecoveryFilePageParam panRecoveryFilePageParam);

    /**
     * 添加网盘-回收站
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    void add(PanRecoveryFileAddParam panRecoveryFileAddParam);

    /**
     * 编辑网盘-回收站
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    void edit(PanRecoveryFileEditParam panRecoveryFileEditParam);

    /**
     * 删除网盘-回收站
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    void delete(List<PanRecoveryFileIdParam> panRecoveryFileIdParamList);

    /**
     * 获取网盘-回收站详情
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    PanRecoveryFile detail(PanRecoveryFileIdParam panRecoveryFileIdParam);

    /**
     * 获取网盘-回收站详情
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     **/
    PanRecoveryFile queryEntity(String id);

    /**
     * 获取网盘-回收站分页(我的回收站列表)
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    IPage<RecoveryFileVo> listPage(PanRecoveryFilePageParam panRecoveryFilePageParam);

    /**
     * 编辑网盘-回收站(支持批量删除)
     *
     * @author yong.ye
     * @date  2023/09/07 15:54
     */
    void batchDelete(BatchDeleteDto batchDeleteDto);

    void deleteUserFileByDeleteBatchNum(String deleteBatchNum);
}
