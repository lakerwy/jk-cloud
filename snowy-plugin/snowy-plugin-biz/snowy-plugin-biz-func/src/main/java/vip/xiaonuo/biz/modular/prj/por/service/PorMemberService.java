package vip.xiaonuo.biz.modular.prj.por.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.prj.por.entity.PorMember;
import vip.xiaonuo.biz.modular.prj.por.param.*;

import java.util.List;

/**
 * 项目产值台账 - 项目管理 - 项目成员管理Service接口
 *
 * @author yy
 * @date  2025/06/03 09:40
 **/
public interface PorMemberService extends IService<PorMember> {

    /**
     * 获取项目产值台账 - 项目管理 - 项目成员管理分页
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    Page<PorMember> page(PorMemberPageParam porMemberPageParam);

    /**
     * 添加项目产值台账 - 项目管理 - 项目成员管理
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    void add(PorMemberAddParam porMemberAddParam);

    /**
     * 编辑项目产值台账 - 项目管理 - 项目成员管理
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    void edit(PorMemberEditParam porMemberEditParam);

    /**
     * 删除项目产值台账 - 项目管理 - 项目成员管理
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    void delete(List<PorMemberIdParam> porMemberIdParamList);

    /**
     * 获取项目产值台账 - 项目管理 - 项目成员管理详情
     *
     * @author yy
     * @date  2025/06/03 09:40
     */
    PorMember detail(PorMemberIdParam porMemberIdParam);

    /**
     * 获取项目产值台账 - 项目管理 - 项目成员管理详情
     *
     * @author yy
     * @date  2025/06/03 09:40
     **/
    PorMember queryEntity(String id);

    List<PorMember> getListById(PorPrjParam param);
}
