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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.salary.entity.*;
import vip.xiaonuo.sys.modular.hr.salary.vo.SalaryPreviewVo;
import vip.xiaonuo.sys.modular.hr.salary.vo.SumEmpBonusData;
import vip.xiaonuo.sys.modular.hr.salary.vo.SumTypeData;

import java.util.List;

/**
 * 工资系统-工资预览表Mapper接口
 *
 * @author yong.ye
 * @date  2024/03/22 10:26
 **/
public interface SalaryPreviewMapper extends BaseMapper<SalaryPreview> {

    /**
     * 系统自动生成某月工资数据 - 确认后进入 - 工资预览数据
     * @return
     */
    List<SalaryPreviewVo> getAutoGZData(@Param(Constants.WRAPPER) Wrapper<SalaryPreview> queryWrapper);

    /**
     * 对指定yyyyMM员工工资，分类统计各类补贴金额
     * @param standardIds
     * @return
     */
    List<SumTypeData> getSubsidyData(List<String> standardIds);

    /**
     * 工龄工资
     * @param standardIds
     * @return
     */
    List<SumTypeData> getSeniorityData(List<String> standardIds);

    /**
     * 员工福利（生日福利、其他福利）--发放公司维度
     * @param standardIds
     * @return
     */
    List<SumEmpBonusData> getEmpBonusData(List<String> standardIds);

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

    /**
     * 工资预览表 - 删除某月(未封存)预览数据
     * @return
     */
    List<String> getDelIdByMonth(String yearMonth);

    /**
     * 根据年月获取待封存数据： (未封存)工资数据 - 封存后 进入工资表
     * @return
     */
    List<SalaryPreview> getArchiveData(String yearMonth);

    /**
     * 工资预览表 - (未封存)工资数据 - 分页展示
     * @return
     */
    Page<SalaryPreviewVo> getPreviewPage(Page<SalaryPreview> page, @Param(Constants.WRAPPER) Wrapper<SalaryPreview> queryWrapper);
}
