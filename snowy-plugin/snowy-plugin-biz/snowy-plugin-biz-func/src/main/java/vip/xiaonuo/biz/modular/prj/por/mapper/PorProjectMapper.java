package vip.xiaonuo.biz.modular.prj.por.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.biz.modular.crm.hetong.vo.HetongDetailVo;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.prj.fin.entity.FinDataPpc;
import vip.xiaonuo.biz.modular.prj.por.entity.PorProject;
import vip.xiaonuo.biz.modular.prj.por.vo.PorProjectVo;

import java.util.List;

/**
 * 项目产值台账 - 项目管理Mapper接口
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
public interface PorProjectMapper extends BaseMapper<PorProject> {

    Page<PorProjectVo> getPageList(Page<PorProjectVo> page, @Param(Constants.WRAPPER) Wrapper<PorProject> queryWrapper);

    PorProjectVo getDetail(String id);

    /**
     * 根据立项id,获取 审批中|审批通过 合同
     * @param bizProjectId
     * @return
     */
    List<HetongDetailVo> getHtListByPrjId(String bizProjectId);

    /**
     * 根据立项id,获取项目回款记录
     * @param bizProjectId
     * @return
     */
    List<FinDataPpc> getPpcListByPrjId(String bizProjectId);

    /**
     * 根据立项id,获取分包
     * @param bizProjectId
     * @return
     */
    List<Subcontract> getSubListByPrjId(String bizProjectId);
}
