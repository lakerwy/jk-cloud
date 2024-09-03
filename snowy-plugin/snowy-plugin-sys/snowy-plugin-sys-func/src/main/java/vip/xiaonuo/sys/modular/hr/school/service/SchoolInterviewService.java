package vip.xiaonuo.sys.modular.hr.school.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolInterview;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewAddParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewEditParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewIdParam;
import vip.xiaonuo.sys.modular.hr.school.param.SchoolInterviewPageParam;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolInterviewVo;

import java.util.List;

/**
 * 校招 - （应聘者）- 面试入职流程Service接口
 *
 * @author yong.ye
 * @date  2024/09/03 10:46
 **/
public interface SchoolInterviewService extends IService<SchoolInterview> {

    /**
     * 获取校招 - （应聘者）- 面试入职流程分页
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    Page<SchoolInterviewVo> page(SchoolInterviewPageParam schoolInterviewPageParam);

    /**
     * 添加校招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    void add(SchoolInterviewAddParam schoolInterviewAddParam);

    /**
     * 编辑校招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    void edit(SchoolInterviewEditParam schoolInterviewEditParam);

    /**
     * 删除校招 - （应聘者）- 面试入职流程
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    void delete(List<SchoolInterviewIdParam> schoolInterviewIdParamList);

    /**
     * 获取校招 - （应聘者）- 面试入职流程详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     */
    SchoolInterviewVo detail(SchoolInterviewIdParam schoolInterviewIdParam);

    /**
     * 获取校招 - （应聘者）- 面试入职流程详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:46
     **/
    SchoolInterview queryEntity(String id);
}
