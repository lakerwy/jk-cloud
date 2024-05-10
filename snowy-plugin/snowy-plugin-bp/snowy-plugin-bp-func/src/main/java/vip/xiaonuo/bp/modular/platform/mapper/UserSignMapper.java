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
package vip.xiaonuo.bp.modular.platform.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.bp.modular.platform.entity.UserSign;
import vip.xiaonuo.bp.modular.platform.vo.UserSignVo;

/**
 * 招采平台 - 用户 - （招采项目）报名记录表Mapper接口
 *
 * @author yong.ye
 * @date  2024/05/10 10:49
 **/
public interface UserSignMapper extends BaseMapper<UserSign> {

    /**
     * 根据用户id 获取我的报名
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<UserSignVo> getMySignList(Page<UserSignVo> page, @Param(Constants.WRAPPER) Wrapper<UserSign> queryWrapper);
}
