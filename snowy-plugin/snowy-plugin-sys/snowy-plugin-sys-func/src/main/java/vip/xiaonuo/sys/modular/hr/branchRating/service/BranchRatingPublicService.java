package vip.xiaonuo.sys.modular.hr.branchRating.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchRatingPublic;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicAddParam;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicEditParam;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicIdParam;
import vip.xiaonuo.sys.modular.hr.branchRating.param.BranchRatingPublicPageParam;

import java.util.List;

/**
 * 分公司评级-公示年份和季度Service接口
 *
 * @author pzh
 * @date  2024/09/04 10:21
 **/
public interface BranchRatingPublicService extends IService<BranchRatingPublic> {

    /**
     * 获取分公司评级-公示年份和季度分页
     *
     * @author pzh
     * @date  2024/09/04 10:21
     */
    Page<BranchRatingPublic> page(BranchRatingPublicPageParam branchRatingPublicPageParam);

    /**
     * 添加分公司评级-公示年份和季度
     *
     * @author pzh
     * @date  2024/09/04 10:21
     */
    void add(BranchRatingPublicAddParam branchRatingPublicAddParam);

    /**
     * 编辑分公司评级-公示年份和季度
     *
     * @author pzh
     * @date  2024/09/04 10:21
     */
    void edit(BranchRatingPublicEditParam branchRatingPublicEditParam);

    /**
     * 删除分公司评级-公示年份和季度
     *
     * @author pzh
     * @date  2024/09/04 10:21
     */
    void delete(List<BranchRatingPublicIdParam> branchRatingPublicIdParamList);

    /**
     * 获取分公司评级-公示年份和季度详情
     *
     * @author pzh
     * @date  2024/09/04 10:21
     */
    BranchRatingPublic detail(BranchRatingPublicIdParam branchRatingPublicIdParam);

    /**
     * 获取分公司评级-公示年份和季度详情
     *
     * @author pzh
     * @date  2024/09/04 10:21
     **/
    BranchRatingPublic queryEntity(String id);

    /**
     * 获取分公司评级-当前公示年份和季度
     *
     * @author pzh
     * @date  2024/09/04 10:21
     **/
    BranchRatingPublic getCurPublic();

    /**
     * 获取分公司评级-指定年份和季度的公示状态
     *
     * @author pzh
     * @date  2024/09/04 10:21
     **/
    String getPublic(String year, String quarter);

    /**
     * 修改分公司评级-当前公示年份和季度
     *
     * @author pzh
     * @date  2024/09/04 10:21
     **/
    void setPublic(String year, String quarter);
}
