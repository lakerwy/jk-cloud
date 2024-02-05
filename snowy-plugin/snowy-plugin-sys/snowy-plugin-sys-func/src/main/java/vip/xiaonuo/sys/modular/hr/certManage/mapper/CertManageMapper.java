/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.certManage.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.certManage.entity.CertManage;
import vip.xiaonuo.sys.modular.hr.certManage.param.PersonParam;
import vip.xiaonuo.sys.modular.hr.certManage.vo.CertManageVo;
import vip.xiaonuo.sys.modular.hr.certManage.vo.PersonVo;

import java.util.List;

/**
 * 人员资质管理Mapper接口
 *
 * @author yong.ye
 * @date  2024/01/23 17:23
 **/
public interface CertManageMapper extends BaseMapper<CertManage> {

    /***
     * 总部花名册 & 分公司花名册 人员信息搜索
     * @return
     */
    List<PersonVo> getPersonList(@Param("param") PersonParam param);

    /**
     * 分页查询（自定义）
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<CertManageVo> getPageList(Page<CertManageVo> page, @Param(Constants.WRAPPER) Wrapper<CertManage> queryWrapper);

    /**
     * （自定义）表别名【过滤交集Id】
     * @param queryWrapper
     * @return
     */
    List<CertManageVo> getMyAliasList(@Param(Constants.WRAPPER) Wrapper<CertManage> queryWrapper);

    /**
     * 搜索 - 列表
     * @param queryWrapper
     * @return
     */
    List<CertManageVo> getMyList(@Param(Constants.WRAPPER) Wrapper<CertManage> queryWrapper);

}
