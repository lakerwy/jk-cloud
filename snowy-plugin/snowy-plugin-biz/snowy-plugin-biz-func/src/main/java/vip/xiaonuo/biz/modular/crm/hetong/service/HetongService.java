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
package vip.xiaonuo.biz.modular.crm.hetong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.hetong.param.*;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongInfoVo;

import javax.validation.Valid;
import java.util.List;

/**
 * 业务表-CRM-合同Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 14:49
 **/
public interface HetongService extends IService<Hetong> {

    /**
     * 获取业务表-CRM-合同分页
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    IPage<HetongDetailVo> page(HetongPageParam hetongPageParam);

    /**
     * 添加业务表-CRM-合同
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    void add(HetongAddParam hetongAddParam);

    /**
     * 编辑业务表-CRM-合同
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    void edit(HetongEditParam hetongEditParam);

    /**
     * 删除业务表-CRM-合同
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    void delete(List<HetongIdParam> hetongIdParamList);

    /**
     * 获取业务表-CRM-合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     */
    HetongDetailVo detail(HetongIdParam hetongIdParam);

    /**
     * 获取业务表-CRM-合同详情
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     **/
    Hetong queryEntity(String id);

    /**
     * 获取业务表-CRM-合同列表
     *
     * @author yong.ye
     * @date  2023/06/30 14:49
     **/
    List<HetongInfoVo> getListAll();

    /**
     * 判断合同名称是否重名 （审批中+审核通过状态下）
     * @param checkNameParam
     * @return
     */
    Boolean checkName(CheckNameParam checkNameParam);

}
