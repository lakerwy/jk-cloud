package vip.xiaonuo.sys.modular.hr.reg.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidyResult;
import vip.xiaonuo.sys.modular.hr.reg.param.*;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegResultVo;

import java.util.List;

/**
 * 注册补贴（新版）- 结果反馈Service接口
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
public interface RegSubsidyResultService extends IService<RegSubsidyResult> {

    /**
     * 获取注册补贴（新版）- 结果反馈分页
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    Page<RegResultVo> page(RegSubsidyResultPageParam regSubsidyResultPageParam);

    /**
     * 添加注册补贴（新版）- 结果反馈
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    void add(RegSubsidyResultAddParam regSubsidyResultAddParam);

    /**
     * 编辑注册补贴（新版）- 结果反馈
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    void edit(RegSubsidyResultEditParam regSubsidyResultEditParam);

    /**
     * 删除注册补贴（新版）- 结果反馈
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    void delete(List<RegSubsidyResultIdParam> regSubsidyResultIdParamList);

    /**
     * 获取注册补贴（新版）- 结果反馈详情
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     */
    RegSubsidyResult detail(RegSubsidyResultIdParam regSubsidyResultIdParam);

    /**
     * 获取注册补贴（新版）- 结果反馈详情
     *
     * @author yong.ye
     * @date  2025/01/15 09:08
     **/
    RegSubsidyResult queryEntity(String id);

    /**
     * 获取当前员工，已有的证书
     * @param regParam
     * @return
     */
    List<RegSubsidyResult> listById(RegParam regParam);
}
