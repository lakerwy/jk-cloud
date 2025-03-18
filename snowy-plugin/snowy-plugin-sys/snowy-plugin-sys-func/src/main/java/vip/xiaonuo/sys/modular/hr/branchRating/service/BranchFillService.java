package vip.xiaonuo.sys.modular.hr.branchRating.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchFill;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchFillVo;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;

import java.util.List;

/**
 * 分公司评级（二期）- 分公司评级填报Service接口
 *
 * @author yong.ye
 * @date  2025/03/18 08:48
 **/
public interface BranchFillService extends IService<BranchFill> {

    /**
     * 获取分公司评级（二期）- 分公司评级填报分页
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    Page<BranchFill> page(BranchFillPageParam branchFillPageParam);

    /**
     * 添加分公司评级（二期）- 分公司评级填报
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    void add(BranchFillAddParam branchFillAddParam);

    /**
     * 编辑分公司评级（二期）- 分公司评级填报
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    void edit(BranchFillEditParam branchFillEditParam);

    /**
     * 删除分公司评级（二期）- 分公司评级填报
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    void delete(List<BranchFillIdParam> branchFillIdParamList);

    /**
     * 获取分公司评级（二期）- 分公司评级填报详情
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     */
    BranchFill detail(BranchFillIdParam branchFillIdParam);

    /**
     * 获取分公司评级（二期）- 分公司评级填报详情
     *
     * @author yong.ye
     * @date  2025/03/18 08:48
     **/
    BranchFill queryEntity(String id);

    /**
     * 近三年填报季度
     * @return
     */
    List<String> getQuarterList();

    /**
     * 某填报季度下，未填报的分公司
     * @return
     */
    List<PartnerInfo> getBranchList(String fillSeason);

    /**
     * 公示|取消公示
     * @param showParam
     */
    void show(BranchFillShowParam showParam);

    /**
     *
     * @param fillParam
     * @return
     */
    List<BranchFillVo> getBranchFillList(BranchFillParam fillParam);
}
