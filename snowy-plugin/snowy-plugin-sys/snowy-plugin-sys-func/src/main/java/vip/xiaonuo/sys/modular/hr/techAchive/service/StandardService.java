package vip.xiaonuo.sys.modular.hr.techAchive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.Standard;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.StandardPageParam;

import java.util.List;

/**
 * 标准规范Service接口
 *
 * @author pzh
 * @date  2024/10/17 17:05
 **/
public interface StandardService extends IService<Standard> {

    /**
     * 获取标准规范分页
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    Page<Standard> page(StandardPageParam standardPageParam);

    /**
     * 添加标准规范
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    void add(StandardAddParam standardAddParam);

    /**
     * 编辑标准规范
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    void edit(StandardEditParam standardEditParam);

    /**
     * 删除标准规范
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    void delete(List<StandardIdParam> standardIdParamList);

    /**
     * 获取标准规范详情
     *
     * @author pzh
     * @date  2024/10/17 17:05
     */
    Standard detail(StandardIdParam standardIdParam);

    /**
     * 获取标准规范详情
     *
     * @author pzh
     * @date  2024/10/17 17:05
     **/
    Standard queryEntity(String id);
}
