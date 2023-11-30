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
import org.springframework.web.bind.annotation.RequestBody;
import vip.xiaonuo.biz.modular.cad.entity.CadSubItemUser;
import vip.xiaonuo.biz.modular.cad.param.*;

import javax.validation.Valid;
import java.util.List;

/**
 * CAD-子项-专业-人员关系表Service接口
 *
 * @author yong.ye
 * @date  2023/11/29 10:39
 **/
public interface CadSubItemUserService extends IService<CadSubItemUser> {

    /**
     * 获取CAD-子项-专业-人员关系表分页
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    Page<CadSubItemUser> page(CadSubItemUserPageParam cadSubItemUserPageParam);

    /**
     * 添加CAD-子项-专业-人员关系表
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    void add(CadSubItemUserAddParam cadSubItemUserAddParam);

    /**
     * 编辑CAD-子项-专业-人员关系表
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    void edit(CadSubItemUserEditParam cadSubItemUserEditParam);

    /**
     * 删除CAD-子项-专业-人员关系表
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    void delete(List<CadSubItemUserIdParam> cadSubItemUserIdParamList);

    /**
     * 获取CAD-子项-专业-人员关系表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     */
    CadSubItemUser detail(CadSubItemUserIdParam cadSubItemUserIdParam);

    /**
     * 获取CAD-子项-专业-人员关系表详情
     *
     * @author yong.ye
     * @date  2023/11/29 10:39
     **/
    CadSubItemUser queryEntity(String id);

    /**
     * 保存当前子项人员设置信息 【新增|修改|删除】
     * @param saveSubItemUserParam
     */
    void saveSubItemUser(SaveSubItemUserParam saveSubItemUserParam);
}
