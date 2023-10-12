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
package vip.xiaonuo.pan.modular.commonFile.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.pan.modular.commonFile.entity.PanCommonFile;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileUserVo;
import vip.xiaonuo.pan.modular.commonFile.vo.CommonFileVo;

import java.util.List;

/**
 * 共享文件Mapper接口
 *
 * @author mayike
 * @date  2023/09/06 21:07
 **/
public interface PanCommonFileMapper extends BaseMapper<PanCommonFile> {

    /** 共享给我 - 共享用户列表 **/
    List<CommonFileUserVo> selectCommonFileUser(@Param("userId") String userId);

    /** 共享给我 - 某人共享的文件列表 **/
    List<CommonFileVo> selectCommonFileByUser(@Param("userId") String userId, @Param("sessionUserId") String sessionUserId);

    /** 我的共享 - 所共享的文件列表 **/
    IPage<CommonFileVo> myCommonFile(Page<CommonFileVo> page, @Param(Constants.WRAPPER) Wrapper<PanCommonFile> queryWrapper);
}
