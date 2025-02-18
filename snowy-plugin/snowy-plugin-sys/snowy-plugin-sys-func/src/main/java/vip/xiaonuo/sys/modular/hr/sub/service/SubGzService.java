package vip.xiaonuo.sys.modular.hr.sub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.sub.entity.SubGz;
import vip.xiaonuo.sys.modular.hr.sub.param.*;
import vip.xiaonuo.sys.modular.hr.sub.vo.MonthVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearReportVo;
import vip.xiaonuo.sys.modular.hr.sub.vo.YearVo;

import javax.servlet.http.HttpServletResponse;
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
    List<YearReportVo> yearReport(SearchTotal searchTotal);


    /**
     * 导出打印报表
     * @param searchTotal
     * @param response
     */
    void genExcel(SearchTotal searchTotal, HttpServletResponse response);

    /**
     * 年度汇总
     * @param searchTotal
     * @return
     */
    List<YearVo> yearTotal(SearchTotal searchTotal);

    /**
     * 数据校验
     * @param subGzParam
     * @return
     */
    SubGzParam check(SubGzParam subGzParam);

}
