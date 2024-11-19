package vip.xiaonuo.sys.modular.hr.techAchive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.techAchive.entity.NoveltySearch;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchAddParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchEditParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchIdParam;
import vip.xiaonuo.sys.modular.hr.techAchive.param.NoveltySearchPageParam;

import java.util.List;

/**
 * 查新Service接口
 *
 * @author pzh
 * @date  2024/10/18 08:44
 **/
public interface NoveltySearchService extends IService<NoveltySearch> {

    /**
     * 获取查新分页
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    Page<NoveltySearch> page(NoveltySearchPageParam noveltySearchPageParam);

    /**
     * 添加查新
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    void add(NoveltySearchAddParam noveltySearchAddParam);

    /**
     * 编辑查新
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    void edit(NoveltySearchEditParam noveltySearchEditParam);

    /**
     * 删除查新
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    void delete(List<NoveltySearchIdParam> noveltySearchIdParamList);

    /**
     * 获取查新详情
     *
     * @author pzh
     * @date  2024/10/18 08:44
     */
    NoveltySearch detail(NoveltySearchIdParam noveltySearchIdParam);

    /**
     * 获取查新详情
     *
     * @author pzh
     * @date  2024/10/18 08:44
     **/
    NoveltySearch queryEntity(String id);
}
