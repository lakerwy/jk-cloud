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
package vip.xiaonuo.pan.modular.fold.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.dev.modular.file.entity.DevFile;
import vip.xiaonuo.pan.modular.fold.entity.DevFold;
import vip.xiaonuo.pan.modular.fold.param.*;

import java.util.List;

/**
 * 文件主表Service接口
 *
 * @author mayike
 * @date  2023/08/28 10:09
 **/
public interface DevFoldService extends IService<DevFold> {

    /**
     * 获取文件主表分页
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    Page<DevFold> page(DevFoldPageParam devFoldPageParam);

    /**
     * 添加文件主表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    void add(DevFoldAddParam devFoldAddParam);

    /**
     * 编辑文件主表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    void edit(DevFoldEditParam devFoldEditParam);

    /**
     * 删除文件主表
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    void delete(List<DevFoldIdParam> devFoldIdParamList);

    /**
     * 获取文件主表详情
     *
     * @author mayike
     * @date  2023/08/28 10:09
     */
    DevFold detail(DevFoldIdParam devFoldIdParam);

    /**
     * 获取文件主表详情
     *
     * @author mayike
     * @date  2023/08/28 10:09
     **/
    DevFold queryEntity(String id);

    /**
     * 判断文件夹是否存在
     * @param devFold
     */
    Boolean isDirExist(DevFold  devFold);

    List<DevFold> getFolds(DevFold devFold);

    /**
     * MultipartFile文件上传，返回文件信息
     *
     * @author xuyuxiang
     * @date 2022/4/22 15:53
     **/
    List<DevFile> uploadReturnBean(String engine, MultipartFile[] files, String parentId);

    /**
     * 创建个人文件夹根目录
     * @return 文件id
     */
    String createSelfFold();

    /**
     * 创建部门文件夹根目录
     * @return 文件id
     */
    String createGroupFold();

    /**
     * 移动前校验
     * @param devFoldOperateParam
     */
    void checkMove(DevFoldOperateParam devFoldOperateParam);

    Boolean checkFoldLock(String foldId);

    /**
     * 文件夹移动
     * @param devFoldOperateParam
     */
    void moveFold(DevFoldOperateParam devFoldOperateParam);

    Page<DevFold> pageList(DevFoldPageParam devFoldPageParam);

}
