package vip.xiaonuo.sys.modular.hr.techAchive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.SoftwareCopyright;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.SoftwareCopyrightPageParam;

import java.util.List;

/**
 * 软著信息Service接口
 *
 * @author pzh
 * @date  2024/10/17 10:53
 **/
public interface SoftwareCopyrightService extends IService<SoftwareCopyright> {

    /**
     * 获取软著信息分页
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    Page<SoftwareCopyright> page(SoftwareCopyrightPageParam softwareCopyrightPageParam);

    /**
     * 添加软著信息
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    void add(SoftwareCopyrightAddParam softwareCopyrightAddParam);

    /**
     * 编辑软著信息
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    void edit(SoftwareCopyrightEditParam softwareCopyrightEditParam);

    /**
     * 删除软著信息
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    void delete(List<SoftwareCopyrightIdParam> softwareCopyrightIdParamList);

    /**
     * 获取软著信息详情
     *
     * @author pzh
     * @date  2024/10/17 10:53
     */
    SoftwareCopyright detail(SoftwareCopyrightIdParam softwareCopyrightIdParam);

    /**
     * 获取软著信息详情
     *
     * @author pzh
     * @date  2024/10/17 10:53
     **/
    SoftwareCopyright queryEntity(String id);
}
