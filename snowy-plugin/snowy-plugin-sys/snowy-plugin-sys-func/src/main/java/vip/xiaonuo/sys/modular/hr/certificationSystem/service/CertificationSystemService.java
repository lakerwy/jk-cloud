package vip.xiaonuo.sys.modular.hr.certificationSystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.certificationSystem.entity.CertificationSystem;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemAddParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemEditParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemIdParam;
import vip.xiaonuo.sys.modular.hr.certificationSystem.param.CertificationSystemPageParam;

import java.util.List;

/**
 * 认证体系Service接口
 *
 * @author pzh
 * @date  2024/10/18 10:01
 **/
public interface CertificationSystemService extends IService<CertificationSystem> {

    /**
     * 获取认证体系分页
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    Page<CertificationSystem> page(CertificationSystemPageParam certificationSystemPageParam);

    /**
     * 添加认证体系
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    void add(CertificationSystemAddParam certificationSystemAddParam);

    /**
     * 编辑认证体系
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    void edit(CertificationSystemEditParam certificationSystemEditParam);

    /**
     * 删除认证体系
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    void delete(List<CertificationSystemIdParam> certificationSystemIdParamList);

    /**
     * 获取认证体系详情
     *
     * @author pzh
     * @date  2024/10/18 10:01
     */
    CertificationSystem detail(CertificationSystemIdParam certificationSystemIdParam);

    /**
     * 获取认证体系详情
     *
     * @author pzh
     * @date  2024/10/18 10:01
     **/
    CertificationSystem queryEntity(String id);
}
