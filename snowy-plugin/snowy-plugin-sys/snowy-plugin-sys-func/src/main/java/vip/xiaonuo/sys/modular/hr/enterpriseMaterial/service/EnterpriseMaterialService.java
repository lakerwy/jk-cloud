package vip.xiaonuo.sys.modular.hr.enterpriseMaterial.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.entity.EnterpriseMaterial;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialAddParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialEditParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialIdParam;
import vip.xiaonuo.sys.modular.hr.enterpriseMaterial.param.EnterpriseMaterialPageParam;

import java.util.List;

/**
 * 企业宣传资料Service接口
 *
 * @author pzh
 * @date  2024/09/18 14:57
 **/
public interface EnterpriseMaterialService extends IService<EnterpriseMaterial> {

    /**
     * 获取企业宣传资料分页
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    Page<EnterpriseMaterial> page(EnterpriseMaterialPageParam enterpriseMaterialPageParam);

    /**
     * 添加企业宣传资料
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    void add(EnterpriseMaterialAddParam enterpriseMaterialAddParam);

    /**
     * 编辑企业宣传资料
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    void edit(EnterpriseMaterialEditParam enterpriseMaterialEditParam);

    /**
     * 删除企业宣传资料
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    void delete(List<EnterpriseMaterialIdParam> enterpriseMaterialIdParamList);

    /**
     * 获取企业宣传资料详情
     *
     * @author pzh
     * @date  2024/09/18 14:57
     */
    EnterpriseMaterial detail(EnterpriseMaterialIdParam enterpriseMaterialIdParam);

    /**
     * 获取企业宣传资料详情
     *
     * @author pzh
     * @date  2024/09/18 14:57
     **/
    EnterpriseMaterial queryEntity(String id);
}
