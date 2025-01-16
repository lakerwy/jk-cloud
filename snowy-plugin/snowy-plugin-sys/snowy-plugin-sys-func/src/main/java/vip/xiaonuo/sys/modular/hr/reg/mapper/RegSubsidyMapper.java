package vip.xiaonuo.sys.modular.hr.reg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.xiaonuo.sys.modular.hr.reg.entity.RegSubsidy;
import vip.xiaonuo.sys.modular.hr.reg.param.PersonnelParam;
import vip.xiaonuo.sys.modular.hr.reg.vo.PersonnelInfoVo;
import vip.xiaonuo.sys.modular.hr.reg.vo.RegCertVo;

import java.util.List;

/**
 * 注册补贴（新版）-审批流Mapper接口
 *
 * @author yong.ye
 * @date  2025/01/15 09:07
 **/
public interface RegSubsidyMapper extends BaseMapper<RegSubsidy> {


    /**
     * 根据花名册主键id,获取有效的证书
     * @param personnelId
     * @return
     */
    List<RegCertVo> getCertList(String personnelId);

    /**
     * 花名册人员筛选
     * @param personnelParam
     * @return
     */
    List<PersonnelInfoVo> getPersonnelList(PersonnelParam personnelParam);

}
