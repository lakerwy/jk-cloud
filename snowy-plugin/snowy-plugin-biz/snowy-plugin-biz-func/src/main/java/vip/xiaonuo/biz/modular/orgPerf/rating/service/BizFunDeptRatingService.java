package vip.xiaonuo.biz.modular.orgPerf.rating.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.rating.entity.BizFunDeptRating;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingAddParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingEditParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingIdParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingPageParam;

import java.util.List;

/**
 * 职能部门-绩效评分Service接口
 *
 * @author pzh
 * @date  2025/06/30 16:37
 **/
public interface BizFunDeptRatingService extends IService<BizFunDeptRating> {

    /**
     * 获取职能部门-绩效评分分页
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    Page<BizFunDeptRating> page(BizFunDeptRatingPageParam bizFunDeptRatingPageParam);

    /**
     * 添加职能部门-绩效评分
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    void add(BizFunDeptRatingAddParam bizFunDeptRatingAddParam);

    /**
     * 编辑职能部门-绩效评分
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    void edit(BizFunDeptRatingEditParam bizFunDeptRatingEditParam);

    /**
     * 删除职能部门-绩效评分
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    void delete(List<BizFunDeptRatingIdParam> bizFunDeptRatingIdParamList);

    /**
     * 获取职能部门-绩效评分详情
     *
     * @author pzh
     * @date  2025/06/30 16:37
     */
    BizFunDeptRating detail(BizFunDeptRatingIdParam bizFunDeptRatingIdParam);

    /**
     * 获取职能部门-绩效评分详情
     *
     * @author pzh
     * @date  2025/06/30 16:37
     **/
    BizFunDeptRating queryEntity(String id);
}
