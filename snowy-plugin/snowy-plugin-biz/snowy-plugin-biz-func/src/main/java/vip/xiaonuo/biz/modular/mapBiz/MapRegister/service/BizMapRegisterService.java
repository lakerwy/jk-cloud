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
package vip.xiaonuo.biz.modular.mapBiz.MapRegister.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.entity.BizMapRegister;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterAddParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterEditParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterIdParam;
import vip.xiaonuo.biz.modular.mapBiz.MapRegister.param.BizMapRegisterPageParam;

import java.util.List;

/**
 * 业务表-出图登记记录表Service接口
 *
 * @author mayike
 * @date  2023/07/03 11:46
 **/
public interface BizMapRegisterService extends IService<BizMapRegister> {

    /**
     * 获取业务表-出图登记记录表分页
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    Page<BizMapRegister> page(BizMapRegisterPageParam bizMapRegisterPageParam);

    /**
     * 添加业务表-出图登记记录表
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    void add(BizMapRegisterAddParam bizMapRegisterAddParam);

    /**
     * 编辑业务表-出图登记记录表
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    void edit(BizMapRegisterEditParam bizMapRegisterEditParam);

    /**
     * 删除业务表-出图登记记录表
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    void delete(List<BizMapRegisterIdParam> bizMapRegisterIdParamList);

    /**
     * 获取业务表-出图登记记录表详情
     *
     * @author mayike
     * @date  2023/07/03 11:46
     */
    BizMapRegister detail(BizMapRegisterIdParam bizMapRegisterIdParam);

    /**
     * 获取业务表-出图登记记录表详情
     *
     * @author mayike
     * @date  2023/07/03 11:46
     **/
    BizMapRegister queryEntity(String id);
}
