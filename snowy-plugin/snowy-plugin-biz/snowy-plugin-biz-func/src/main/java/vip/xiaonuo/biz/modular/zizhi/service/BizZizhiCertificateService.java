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
package vip.xiaonuo.biz.modular.zizhi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.zizhi.entity.BizZizhiCertificate;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateAddParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateEditParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificateIdParam;
import vip.xiaonuo.biz.modular.zizhi.param.BizZizhiCertificatePageParam;
import vip.xiaonuo.biz.modular.zizhi.vo.BizZizhiCertificateVo;

import java.util.List;

/**
 * 业务表-资质-企业资质证书表Service接口
 *
 * @author yong.ye
 * @date  2023/09/01 09:56
 **/
public interface BizZizhiCertificateService extends IService<BizZizhiCertificate> {

    /**
     * 获取业务表-资质-企业资质证书表分页
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    Page<BizZizhiCertificateVo> page(BizZizhiCertificatePageParam bizZizhiCertificatePageParam);

    /**
     * 添加业务表-资质-企业资质证书表
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    void add(BizZizhiCertificateAddParam bizZizhiCertificateAddParam);

    /**
     * 编辑业务表-资质-企业资质证书表
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    void edit(BizZizhiCertificateEditParam bizZizhiCertificateEditParam);

    /**
     * 删除业务表-资质-企业资质证书表
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    void delete(List<BizZizhiCertificateIdParam> bizZizhiCertificateIdParamList);

    /**
     * 获取业务表-资质-企业资质证书表详情
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     */
    BizZizhiCertificate detail(BizZizhiCertificateIdParam bizZizhiCertificateIdParam);

    /**
     * 获取业务表-资质-企业资质证书表详情
     *
     * @author yong.ye
     * @date  2023/09/01 09:56
     **/
    BizZizhiCertificate queryEntity(String id);
}
