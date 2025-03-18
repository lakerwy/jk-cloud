package vip.xiaonuo.sys.modular.hr.branchRating.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.branchRating.entity.BranchArea;
import vip.xiaonuo.sys.modular.hr.branchRating.param.*;
import vip.xiaonuo.sys.modular.hr.branchRating.vo.BranchAreaVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.ImportResultT;
import vip.xiaonuo.sys.modular.info.entity.PartnerInfo;

import java.util.List;

/**
 * 分公司评级（二期）- 区域经济修正系数 Service接口
 *
 * @author yong.ye
 * @date  2025/03/18 08:47
 **/
public interface BranchAreaService extends IService<BranchArea> {

    /**
     * 获取分公司评级（二期）- 区域经济修正系数 分页
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    Page<BranchArea> page(BranchAreaPageParam branchAreaPageParam);

    /**
     * 添加分公司评级（二期）- 区域经济修正系数 
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    void add(BranchAreaAddParam branchAreaAddParam);

    /**
     * 编辑分公司评级（二期）- 区域经济修正系数 
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    void edit(BranchAreaEditParam branchAreaEditParam);

    /**
     * 删除分公司评级（二期）- 区域经济修正系数 
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    void delete(List<BranchAreaIdParam> branchAreaIdParamList);

    /**
     * 获取分公司评级（二期）- 区域经济修正系数 详情
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     */
    BranchArea detail(BranchAreaIdParam branchAreaIdParam);

    /**
     * 获取分公司评级（二期）- 区域经济修正系数 详情
     *
     * @author yong.ye
     * @date  2025/03/18 08:47
     **/
    BranchArea queryEntity(String id);

    /**
     * 未填报区域经济修正系数的分公司列表
     * @return
     */
    List<PartnerInfo> getBranchList();

    /**
     * 区域经济调整系数 列表+图表
     * @param branchAreaPageParam
     * @return
     */
    BranchAreaVo getList(BranchAreaPageParam branchAreaPageParam);

    /**
     * 导入
     * @param importParam
     * @return
     */
    ImportResultT doImport(ImportParam importParam);


    /**
     * 从合作伙伴，获取分公司数据
     * @return
     */
    List<PartnerInfo> getPartnerList();

    /**
     *
     * @param branchAreaParam
     * @return
     */
    BranchArea getByBranch(BranchAreaParam branchAreaParam);
}
