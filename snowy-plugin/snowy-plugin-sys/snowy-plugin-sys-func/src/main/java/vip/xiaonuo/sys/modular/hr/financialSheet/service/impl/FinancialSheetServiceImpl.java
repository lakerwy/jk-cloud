package vip.xiaonuo.sys.modular.hr.financialSheet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.financialSheet.entity.FinancialSheet;
import vip.xiaonuo.sys.modular.hr.financialSheet.mapper.FinancialSheetMapper;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetAddParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetEditParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetIdParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.param.FinancialSheetPageParam;
import vip.xiaonuo.sys.modular.hr.financialSheet.service.FinancialSheetService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务状况表Service接口实现类
 *
 * @author pzh
 * @date  2024/10/18 15:53
 **/
@Service
public class FinancialSheetServiceImpl extends ServiceImpl<FinancialSheetMapper, FinancialSheet> implements FinancialSheetService {

    @Override
    public Page<FinancialSheet> page(FinancialSheetPageParam financialSheetPageParam) {
        QueryWrapper<FinancialSheet> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(financialSheetPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinancialSheet::getYear,financialSheetPageParam.getSearchKey())
                    .or().like(FinancialSheet::getMainFinancialMetrics,financialSheetPageParam.getSearchKey())
            );
        }
        if(ObjectUtil.isNotEmpty(financialSheetPageParam.getBidEndDate())){
            LocalDate date = LocalDate.parse(financialSheetPageParam.getBidEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<String> years = getRecentThreeYears(date).stream().map(Object::toString).collect(Collectors.toList());
            queryWrapper.lambda().in(FinancialSheet::getYear,years);
        }
        if(ObjectUtil.isAllNotEmpty(financialSheetPageParam.getSortField(), financialSheetPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(financialSheetPageParam.getSortOrder());
            queryWrapper.orderBy(true, financialSheetPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(financialSheetPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinancialSheet::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }


    /**
     * 获取近三年年份
     * 投标截止日如在2024年6月30日以前，则近3年是指上上个年度往前推算的3年，例如投标截止日为2024年5月30日，近3年是指2020年度、2021年度、2022年度
     * 投标截止日如在2024年6月30日以后，则近3年是指上个年度往前推算的3年，例如投标截止日为2024年7月1日，近3年是指2021年度、2022年度、2023年度
     * @param biddingDeadline
     * @return
     */
    private List<Integer> getRecentThreeYears(LocalDate biddingDeadline) {
        // 获取当前年份
        int currentYear = Year.now().getValue();
        List<Integer> recentYears = new ArrayList<>();
        // 判断投标截止日期是在6月30日之前还是之后
        if (biddingDeadline.isBefore(LocalDate.of(biddingDeadline.getYear(), 7, 1))) {
            // 如果投标截止日在6月30日或之前，则从上上个年度开始计算
            for (int i = 0; i < 3; i++) {
                recentYears.add(currentYear - 2 - i);
            }
        } else {
            // 如果投标截止日在6月30日之后，则从上个年度开始计算
            for (int i = 0; i < 3; i++) {
                recentYears.add(currentYear - 1 - i);
            }
        }
        return recentYears;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinancialSheetAddParam financialSheetAddParam) {
        FinancialSheet financialSheet = BeanUtil.toBean(financialSheetAddParam, FinancialSheet.class);
        this.save(financialSheet);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinancialSheetEditParam financialSheetEditParam) {
        FinancialSheet financialSheet = this.queryEntity(financialSheetEditParam.getId());
        BeanUtil.copyProperties(financialSheetEditParam, financialSheet);
        this.updateById(financialSheet);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinancialSheetIdParam> financialSheetIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(financialSheetIdParamList, FinancialSheetIdParam::getId));
    }

    @Override
    public FinancialSheet detail(FinancialSheetIdParam financialSheetIdParam) {
        return this.queryEntity(financialSheetIdParam.getId());
    }

    @Override
    public FinancialSheet queryEntity(String id) {
        FinancialSheet financialSheet = this.getById(id);
        if(ObjectUtil.isEmpty(financialSheet)) {
            throw new CommonException("财务状况表不存在，id值为：{}", id);
        }
        return financialSheet;
    }
}
