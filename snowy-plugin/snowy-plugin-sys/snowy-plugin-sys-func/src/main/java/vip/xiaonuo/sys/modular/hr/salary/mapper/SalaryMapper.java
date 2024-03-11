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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.salary.entity.*;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryStandardVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SumTypeData;

import java.util.List;

/**
 * 工资系统-工资表Mapper接口
 *
 * @author yong.ye
 * @date  2024/03/01 16:50
 **/
public interface SalaryMapper extends BaseMapper<Salary> {
    /**
     * 月工资报表 - (未确认的)预览数据（自动生成）
     * @return
     */
    List<SalaryVo> getPrePageList(@Param(Constants.WRAPPER) Wrapper<Salary> queryWrapper);

    /**
     * 对指定yyyyMM员工工资，分类统计各类补贴金额
     * @param standardIds
     * @return
     */
    List<SumTypeData> getSubsidyData(List<String> standardIds);

    /**
     * 对指定yyyyMM员工工资，分类统计各类福利金额
     * @param standardIds
     * @return
     */
    List<SumTypeData> getPayData(List<String> standardIds);

    /**
     * 对指定yyyyMM员工工资，获取考勤数据
     * @param standardIds
     * @return
     */
    List<SalaryAttend> getAttendData(List<String> standardIds);

    /**
     * 对指定yyyyMM员工工资，获取社保数据
     * @param standardIds
     * @return
     */
    List<SalaryIns> getInsData(List<String> standardIds);

    /**
     * 对指定yyyyMM员工工资，获取公积金数据
     * @param standardIds
     * @return
     */
    List<SalaryGjj> getGjjData(List<String> standardIds);

    /**
     * 对指定yyyyMM员工工资，获取加计扣除数据
     * @param standardIds
     * @return
     */
    List<SalaryTax> getTaxData(List<String> standardIds);

    /**
     * 对指定yyyyMM员工工资，获取 税前税后 补发补扣 数据
     * @param standardIds
     * @return
     */
    List<SalaryAdd> getAddData(List<String> standardIds);
}
