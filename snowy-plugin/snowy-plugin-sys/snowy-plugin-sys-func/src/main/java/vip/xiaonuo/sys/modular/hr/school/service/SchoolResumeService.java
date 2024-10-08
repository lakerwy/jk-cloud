package vip.xiaonuo.sys.modular.hr.school.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.sys.modular.hr.school.entity.SchoolResume;
import vip.xiaonuo.sys.modular.hr.school.param.*;
import vip.xiaonuo.sys.modular.hr.school.vo.SchoolResumeVo;

import java.util.List;

/**
 * 校招 - （应聘者）简历库信息Service接口
 *
 * @author yong.ye
 * @date  2024/09/03 10:44
 **/
public interface SchoolResumeService extends IService<SchoolResume> {

    /**
     * 获取校招 - （应聘者）简历库信息分页
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    Page<SchoolResumeVo> page(SchoolResumePageParam schoolResumePageParam);

    /**
     * 添加校招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    void add(SchoolResumeAddParam schoolResumeAddParam);

    /**
     * 编辑校招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    void edit(SchoolResumeEditParam schoolResumeEditParam);

    /**
     * 删除校招 - （应聘者）简历库信息
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    void delete(List<SchoolResumeIdParam> schoolResumeIdParamList);

    /**
     * 获取校招 - （应聘者）简历库信息详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     */
    SchoolResumeVo detail(SchoolResumeIdParam schoolResumeIdParam);

    /**
     * 获取校招 - （应聘者）简历库信息详情
     *
     * @author yong.ye
     * @date  2024/09/03 10:44
     **/
    SchoolResume queryEntity(String id);

    /**
     * 登记
     * @param resumeParam
     */
    void submit(ResumeParam resumeParam);

    /**
     * 页面批量新增简历
     * @param schoolResumeAddParamList
     */
    void batchAdd(List<SchoolResumeAddParam> schoolResumeAddParamList);

    /**
     * 导入简历
     * @param schoolResumeAddParamList
     */
    void batchImport(List<SchoolResumeAddParam> schoolResumeAddParamList);

    /**
     * 附件上传
     * @param resumeUploadParam
     */
    void upload(ResumeUploadParam resumeUploadParam);

    /**
     * 人资初审
     * @param resumeStatusParam
     */
    void status(ResumeStatusParam resumeStatusParam);
}
