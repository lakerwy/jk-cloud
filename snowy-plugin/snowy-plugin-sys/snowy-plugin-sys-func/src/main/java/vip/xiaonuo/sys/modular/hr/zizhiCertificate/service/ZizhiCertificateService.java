package vip.xiaonuo.sys.modular.hr.zizhiCertificate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.entity.ZizhiCertificate;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateAddParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateEditParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificateIdParam;
import vip.xiaonuo.sys.modular.hr.zizhiCertificate.param.ZizhiCertificatePageParam;

import java.util.List;

/**
 * 资质证照Service接口
 *
 * @author pzh
 * @date  2024/10/16 16:39
 **/
public interface ZizhiCertificateService extends IService<ZizhiCertificate> {

    /**
     * 获取资质证照分页
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    Page<ZizhiCertificate> page(ZizhiCertificatePageParam zizhiCertificatePageParam);

    /**
     * 添加资质证照
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    void add(ZizhiCertificateAddParam zizhiCertificateAddParam);

    /**
     * 编辑资质证照
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    void edit(ZizhiCertificateEditParam zizhiCertificateEditParam);

    /**
     * 删除资质证照
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    void delete(List<ZizhiCertificateIdParam> zizhiCertificateIdParamList);

    /**
     * 获取资质证照详情
     *
     * @author pzh
     * @date  2024/10/16 16:39
     */
    ZizhiCertificate detail(ZizhiCertificateIdParam zizhiCertificateIdParam);

    /**
     * 获取资质证照详情
     *
     * @author pzh
     * @date  2024/10/16 16:39
     **/
    ZizhiCertificate queryEntity(String id);
}
