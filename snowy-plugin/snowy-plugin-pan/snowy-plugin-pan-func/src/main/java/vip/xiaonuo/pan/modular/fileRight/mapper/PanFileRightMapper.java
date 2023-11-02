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
package vip.xiaonuo.pan.modular.fileRight.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.xiaonuo.pan.modular.fileRight.entity.PanFileRight;
import vip.xiaonuo.pan.modular.fileRight.vo.PanFileRightVo;
import vip.xiaonuo.pan.modular.fileRight.vo.UserInfoVo;

import java.util.List;

/**
 * 网盘权限配置表Mapper接口
 *
 * @author yong.ye
 * @date  2023/10/25 14:32
 **/
public interface PanFileRightMapper extends BaseMapper<PanFileRight> {

    List<PanFileRightVo> getFileRightByFileId(String userFileId);

    /**
     * 获取 用户userId+orgIds权限配置
     *
     * 用户有设置，以用户设置为准
     * 用户没设置，再逐级看部门权限
     *
     * 查询结果：1.当前用户不可见数据
     *         2.无用户设置，仅有部门设置的权限配置记录（用于逐级筛选）
     *
     * @param userId
     * @param orgIds
     * @return
     */
    List<PanFileRight> getFileRightList(String userId,List<String> orgIds,long checkRight);

    /**
     * 根据顶级文件 群组设置范围 ，获取其所有用户信息
     * @param objIds
     * @return
     */
    List<UserInfoVo> getUserInfos(List<String> objIds);


}
