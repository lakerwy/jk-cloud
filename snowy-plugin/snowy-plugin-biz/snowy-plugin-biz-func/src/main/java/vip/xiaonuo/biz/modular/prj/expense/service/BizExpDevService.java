package vip.xiaonuo.biz.modular.prj.expense.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpDev;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevAddParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevEditParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevIdParam;
import vip.xiaonuo.biz.modular.prj.expense.param.BizExpDevPageParam;

import java.util.List;

/**
 * 财务管理 - 费用报销 - 研发项目信息表Service接口
 *
 * @author yong.ye
 * @date  2024/08/01 11:22
 **/
public interface BizExpDevService extends IService<BizExpDev> {

    /**
     * 获取财务管理 - 费用报销 - 研发项目信息表分页
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    Page<BizExpDev> page(BizExpDevPageParam bizExpDevPageParam);

    /**
     * 添加财务管理 - 费用报销 - 研发项目信息表
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    void add(BizExpDevAddParam bizExpDevAddParam);

    /**
     * 编辑财务管理 - 费用报销 - 研发项目信息表
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    void edit(BizExpDevEditParam bizExpDevEditParam);

    /**
     * 删除财务管理 - 费用报销 - 研发项目信息表
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    void delete(List<BizExpDevIdParam> bizExpDevIdParamList);

    /**
     * 获取财务管理 - 费用报销 - 研发项目信息表详情
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     */
    BizExpDev detail(BizExpDevIdParam bizExpDevIdParam);

    /**
     * 获取财务管理 - 费用报销 - 研发项目信息表详情
     *
     * @author yong.ye
     * @date  2024/08/01 11:22
     **/
    BizExpDev queryEntity(String id);
}
