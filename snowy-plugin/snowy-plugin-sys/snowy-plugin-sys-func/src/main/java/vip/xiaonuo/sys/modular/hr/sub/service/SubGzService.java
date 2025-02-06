package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGz;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.vo.MonthVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearGzVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearNumVo;

import java.util.List;

/**
 * 分公司-工资发放（流程）Service接口
 *
 * @author yong.ye
 * @date  2025/02/06 09:09
 **/
public interface SubGzService extends IService<SubGz> {

    /**
     * 获取分公司-工资发放（流程）分页
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    Page<SubGz> page(SubGzPageParam subGzPageParam);

    /**
     * 添加分公司-工资发放（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    void add(SubGzParam subGzParam);

    /**
     * 编辑分公司-工资发放（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    void edit(SubGzParam subGzParam);

    /**
     * 删除分公司-工资发放（流程）
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    void delete(List<SubGzIdParam> subGzIdParamList);

    /**
     * 获取分公司-工资发放（流程）详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     */
    SubGz detail(SubGzIdParam subGzIdParam);

    /**
     * 获取分公司-工资发放（流程）详情
     *
     * @author yong.ye
     * @date  2025/02/06 09:09
     **/
    SubGz queryEntity(String id);

    /**
     *
     * @param searchTotal
     * @return
     */
    List<MonthVo> monthTotal(SearchTotal searchTotal);

    /**
     *
     * @param searchTotal
     * @return
     */
    List<YearNumVo> yearNumTotal(SearchTotal searchTotal);

    /**
     *
     * @param searchTotal
     * @return
     */
    List<YearGzVo> yearGzTotal(SearchTotal searchTotal);
}
