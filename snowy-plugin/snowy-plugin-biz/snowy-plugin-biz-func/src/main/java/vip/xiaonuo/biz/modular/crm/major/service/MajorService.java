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
package vip.xiaonuo.biz.modular.crm.major.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.crm.major.entity.Major;
import vip.xiaonuo.biz.modular.crm.major.param.MajorAddParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorEditParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorIdParam;
import vip.xiaonuo.biz.modular.crm.major.param.MajorPageParam;
import vip.xiaonuo.biz.modular.crm.major.vo.MajorInfoVo;

import java.util.List;

/**
 * 业务表-CRM-图审专业Service接口
 *
 * @author yong.ye
 * @date  2023/06/30 16:42
 **/
public interface MajorService extends IService<Major> {

    /**
     * 获取业务表-CRM-图审专业分页
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    Page<Major> page(MajorPageParam majorPageParam);

    /**
     * 添加业务表-CRM-图审专业
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    void add(MajorAddParam majorAddParam);

    /**
     * 编辑业务表-CRM-图审专业
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    void edit(MajorEditParam majorEditParam);

    /**
     * 删除业务表-CRM-图审专业
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    void delete(List<MajorIdParam> majorIdParamList);

    /**
     * 获取业务表-CRM-图审专业详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     */
    MajorInfoVo detail(MajorIdParam majorIdParam);

    /**
     * 获取业务表-CRM-图审专业详情
     *
     * @author yong.ye
     * @date  2023/06/30 16:42
     **/
    Major queryEntity(String id);

    /**
     * 获取业务表-CRM-图审专业分页(携带专业负责人)
     *
     * @author yong.ye
     * @date  2023/07/04 16:42
     */
    IPage<MajorInfoVo> pageList(MajorPageParam majorPageParam);
}
