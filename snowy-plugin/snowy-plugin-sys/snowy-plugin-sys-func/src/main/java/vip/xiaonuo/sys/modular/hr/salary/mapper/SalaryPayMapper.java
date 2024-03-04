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
package vip.xiaonuo.sys.modular.hr.salary.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryPay;
import vip.xiaonuo.sys.modular.hr.salary.vo.PersonnelInfoVo;

import java.util.List;

/**
 * 工资系统-工龄工资|生日福利Mapper接口
 *
 * @author yong.ye
 * @date  2024/02/26 09:34
 **/
public interface SalaryPayMapper extends BaseMapper<SalaryPay> {

    /**
     * 根据 类别：工龄工资  年月 获取本次需要增加的工龄工资人员
     * @param type 工龄工资
     * @param yearMonth 当前年月
     * @return
     */
    List<PersonnelInfoVo> getSeniorityPersonnel(String type, String yearMonth);

    /**
     * 根据 类别：生日福利  年月  月份 获取本月需要增加的生日福利人员
     * @param type 员工福利
     * @param itemType 生日福利
     * @param yearMonth 当前年月
     * @param month 当前月份
     * @return
     */
    List<PersonnelInfoVo> getBirthdayPersonnel(String type,String itemType,String yearMonth,Integer month);

    /**
     * 获取所有 在职 花名册员工
     * @return
     */
    List<PersonnelInfoVo> getPersonnelList();
}
