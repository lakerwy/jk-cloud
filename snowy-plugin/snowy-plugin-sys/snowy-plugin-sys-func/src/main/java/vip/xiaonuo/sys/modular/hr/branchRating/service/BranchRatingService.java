package vip.xiaonuo.sys.modular.hr.branchRating.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchRating;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchRatingVo;

import java.util.List;

/**
 * 分公司评级Service接口
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
public interface BranchRatingService extends IService<BranchRating> {

    /**
     * 获取分公司评级分页
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    Page<BranchRating> page(BranchRatingPageParam branchRatingPageParam);

    /**
     * 添加分公司评级
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    void add(BranchRatingAddParam branchRatingAddParam);

    /**
     * 编辑分公司评级
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    void edit(BranchRatingEditParam branchRatingEditParam);

    /**
     * 删除分公司评级
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    void delete(List<BranchRatingIdParam> branchRatingIdParamList);

    /**
     * 获取分公司评级详情
     *
     * @author pzh
     * @date  2024/09/03 10:03
     */
    BranchRating detail(BranchRatingIdParam branchRatingIdParam);

    /**
     * 获取分公司评级详情
     *
     * @author pzh
     * @date  2024/09/03 10:03
     **/
    BranchRating queryEntity(String id);

    /**
     * 分公司评级 - 导入
     *
     * @author pzh
     * @date  2024/09/03 10:03
     **/
    void importData(ImportBranchRating input);

    /**
     * 分公司评级 - 设置公示状态
     *
     * @author pzh
     * @date  2024/09/03 10:03
     **/
    void setStatus(BranchRatingStatusEditParam branchRatingStatusEditParam);

    /**
     * 获取分公司评级列表
     *
     * @author pzh
     * @date 2024/09/03 10:03
     **/
    List<BranchRatingVo> getList(BranchRatingListParam branchRatingListParam);
}
