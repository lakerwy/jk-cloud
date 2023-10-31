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
package vip.xiaonuo.pan.modular.fileRight.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.pan.modular.fileRight.entity.PanFileRight;
import vip.xiaonuo.pan.modular.fileRight.param.FileRightParam;
import vip.xiaonuo.pan.modular.fileRight.param.ManagerParam;
import vip.xiaonuo.pan.modular.fileRight.param.PanFileRightAddParam;
import vip.xiaonuo.pan.modular.fileRight.param.PanFileRightIdParam;
import vip.xiaonuo.pan.modular.fileRight.vo.PanFileRightVo;

import javax.validation.Valid;
import java.util.List;

/**
 * 网盘权限配置表Service接口
 *
 * @author yong.ye
 * @date  2023/10/25 14:32
 **/
public interface PanFileRightService extends IService<PanFileRight> {

    /**
     * 获取网盘权限配置表详情
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    List<PanFileRightVo> detail(PanFileRightIdParam panFileRightIdParam);

    /**
     * 获取网盘权限配置表详情
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     **/
    PanFileRight queryEntity(String id);


    /***
     * 根据 用户文件Id，校验当前用户是否有对应操作权限
     * @param userFileId 用户文件Id(UserFile.Id)
     * @param checkRight 操作全新(FileRight)
     *
     *  check 逻辑说明：
     *                1. 若当前用户为userFileId文件所有者，拥有全部操作权限
     *                2. 若当前用户不为userFileId文件所有者，根据权限配置校验权限
     *                   2.1 若当前userFileId，当前用户，存在权限配置，直接根据配置校验
     *                   2.2 若当前userFileId，当前用户，不存在权限配置，递归父级权限（就近原则），校验权限
     */
    boolean checkFileRight(String userFileId,long checkRight);


    /**
     * 保存网盘权限配置
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    void save(PanFileRightAddParam panFileRightAddParam);

    /**
     * 给文件夹|文件设置管理员
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    void setManager(ManagerParam managerParam);


    /**
     * 根据当前用户+所选择的文件，获取拥有的权限(若选中多个文件，则取交集)
     * @param fileRightParam
     * @return
     */
    Long getRightById(FileRightParam fileRightParam);
}
