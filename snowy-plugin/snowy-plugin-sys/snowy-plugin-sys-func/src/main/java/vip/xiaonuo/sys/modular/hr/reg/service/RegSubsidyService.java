package vip.xiaonuo.sys.modular.hr.reg.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidy;
import vip.xiaonuo.sys.modular.hr.reg.param.*;
import vip.xiaonuo.sys.modular.hr.reg.vo.PersonnelInfoVo;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegCertVo;

import java.util.List;

/**
 * 注册补贴（新版）-审批流Service接口
 *
 * @author yong.ye
 * @date  2025/01/15 09:07
 **/
public interface RegSubsidyService extends IService<RegSubsidy> {

    /**
     * 获取注册补贴（新版）-审批流分页
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    Page<RegSubsidy> page(RegSubsidyPageParam regSubsidyPageParam);

    /**
     * 添加注册补贴（新版）-审批流
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    void add(RegSubsidyAddParam regSubsidyAddParam);

    /**
     * 编辑注册补贴（新版）-审批流
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    void edit(RegSubsidyEditParam regSubsidyEditParam);

    /**
     * 删除注册补贴（新版）-审批流
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    void delete(List<RegSubsidyIdParam> regSubsidyIdParamList);

    /**
     * 获取注册补贴（新版）-审批流详情
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     */
    RegSubsidy detail(RegSubsidyIdParam regSubsidyIdParam);

    /**
     * 获取注册补贴（新版）-审批流详情
     *
     * @author yong.ye
     * @date  2025/01/15 09:07
     **/
    RegSubsidy queryEntity(String id);

    /**
     * 校验
     * @param regParam
     * @return
     */
    Boolean check(RegParam regParam);

    /**
     * 首次申请，带出资质列表信息
     * @param regParam
     * @return
     */
    List<RegCertVo> certList(RegParam regParam);

    /**
     * 花名册人员
     * @param personnelParam
     * @return
     */
    List<PersonnelInfoVo> personnelList(PersonnelParam personnelParam);
}
