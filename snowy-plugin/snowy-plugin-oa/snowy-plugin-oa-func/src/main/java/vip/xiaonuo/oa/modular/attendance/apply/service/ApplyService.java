package vip.xiaonuo.oa.modular.attendance.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.apply.entity.Apply;
import vip.xiaonuo.oa.modular.attendance.apply.entity.ApplyJson;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyAddParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyEditParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyIdParam;
import vip.xiaonuo.oa.modular.attendance.apply.param.ApplyPageParam;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWorkRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;

import java.util.List;

/**
 * 考勤申请管理表Service接口
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
public interface ApplyService<T extends ApplyJson> extends IService<Apply<T>> {

    /**
     * 获取考勤申请管理表分页
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    Page<Apply<T>> page(ApplyPageParam applyPageParam,Class<T> clazz);

    /**
     * 添加考勤申请管理表
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    void add(ApplyAddParam applyAddParam);

    /**
     * 编辑考勤申请管理表
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    void edit(ApplyEditParam applyEditParam);

    /**
     * 删除考勤申请管理表
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    void delete(List<ApplyIdParam> applyIdParamList);

    /**
     * 获取考勤申请管理表详情
     *
     * @author mayike
     * @date  2024/07/15 09:56
     */
    Apply detail(ApplyIdParam applyIdParam,Class<T> clazz);

    /**
     * 获取考勤申请管理表详情
     *
     * @author mayike
     * @date  2024/07/15 09:56
     **/
    Apply queryEntity(String id);

    Rule<OverWorkRule> getOwnerOverWorks();
}
