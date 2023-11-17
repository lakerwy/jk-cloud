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
import vip.xiaonuo.pan.modular.fileRight.param.*;
import vip.xiaonuo.pan.modular.fileRight.vo.PanFileRightVo;
import vip.xiaonuo.pan.modular.fileRight.vo.UserRightVo;

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

    /***
     * 根据 用户文件Id，校验当前用户是否有对应操作权限
     * @param userFileIds 用户文件Id(UserFile.Id)
     * @param checkRight 操作全新(FileRight)
     */
    void checkFileRight(List<String> userFileIds,long checkRight,String type);

    /**
     * 保存网盘权限配置（全量，新增|更新|删除）
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    void save(PanFileRightAddParam panFileRightAddParam);

    /**
     * 保存网盘权限配置[处理当前记录，新增|更新]
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    void saveRight(PanFileRightAddParam panFileRightAddParam);

    /**
     * 删除某个文件，某个对象的权限配置
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    void removeRight(RemoveRightParam removeRightParam);

    /**
     * 清空某个文件及其子文件的权限配置（实现继承上级文件夹权限）
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    void clearRight(ClearRightParam clearRightParam);

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

    /**
     * 根据文件Id，获取当前文件的权限列表（有设置取设置，无设置取继承的）
     *
     * @author yong.ye
     * @date  2023/10/25 14:32
     */
    List<UserRightVo> rightDetail(PanFileRightIdParam panFileRightIdParam);

    /**
     * 根据文件id集合，过滤没有权限的文件
     *
     * 主要针对文件夹中包含文件|文件夹
     *
     * 例如：上层文件夹A有下载权限，子文件夹|文件 无下载权限，需要过滤
     * @param userFileIds
     * @param checkRight
     * @return
     */
    List<String> filterUserFileIds(List<String> userFileIds,long checkRight,String type);


    /***
     * 在列表页过滤的时候，需要排除不可见的权限设置
     * 登录用户 A （属于数字院）
     * 比如：文件A 对于数字院可见  但对 员工A 不可见  需要排除   员工A不可见A文件
     *      文件A 对于数字院不可见  但特定设置对 员工A 可见  不排除  员工A可见A文件
     *      文件A 对于数字院不可见  无其他设置  需要排除   员工A不可见A文件
     *
     *  数字院(权限设置)    可见    可见    不可见   不可见    不可见    可见    X       X       可见      不可见
     *  软件中心(权限设置)   可见    可见   不可见   不可见    不可见    可见    X       X       不可见     可见
     *  员工A (权限设置)    可见   不可见   可见     不可见      X       X    可见    不可见      X        X
     *  员工A（结果）       可见   不可见   可见     不可见    不可见    可见   可见    不可见    不可见     可见
     *
     *  结论： 若针对员工设置了权限，优先看员工配置，无员工配置看组织设置（组织递归）
     *
     * @param userId
     * @param orgIds
     * @return
     */
    List<String> notInUserFileIds(String userId,String orgId,List<String> orgIds);
}
