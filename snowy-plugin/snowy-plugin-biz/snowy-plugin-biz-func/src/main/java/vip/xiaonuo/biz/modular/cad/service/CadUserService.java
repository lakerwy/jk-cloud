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
package vip.xiaonuo.biz.modular.cad.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.cad.entity.CadUser;
import vip.xiaonuo.biz.modular.cad.param.*;
import vip.xiaonuo.biz.modular.cad.vo.CadUserVo;

import java.util.List;

/**
 * CAD-用户表Service接口
 *
 * @author yong.ye
 * @date  2023/11/29 10:40
 **/
public interface CadUserService extends IService<CadUser> {

    /**
     * 获取CAD-用户表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    Page<CadUserVo> page(CadUserPageParam cadUserPageParam);

    /**
     * 添加CAD-用户表
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    void add(CadUserAddParam cadUserAddParam);

    /**
     * 编辑CAD-用户表
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    void edit(CadUserEditParam cadUserEditParam);

    /**
     * 删除CAD-用户表
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    void delete(List<CadUserIdParam> cadUserIdParamList);

    /**
     * 获取CAD-用户表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     */
    CadUserVo detail(CadUserIdParam cadUserIdParam);

    /**
     * 获取CAD-用户表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:40
     **/
    CadUser queryEntity(String id);

    /**
     * 批量新增用户
     * @param cadUserBatchAddParam
     * @return
     */
    void addBatchUser(CadUserBatchAddParam cadUserBatchAddParam);

    /**
     * 设置用户专业
     * @param cadUserProfessionParam
     */
    void setProfession(CadUserProfessionParam cadUserProfessionParam);

    /**
     * 根据专业Id，获取当前专业下的所有用户集合
     * @param professionId
     * @return
     */
    List<CadUser> userByProfession(String professionId);
}
