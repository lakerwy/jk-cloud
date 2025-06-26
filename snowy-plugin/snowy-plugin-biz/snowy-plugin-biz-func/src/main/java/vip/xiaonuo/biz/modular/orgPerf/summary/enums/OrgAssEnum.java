package vip.xiaonuo.biz.modular.orgPerf.summary.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 子公司及生产部门绩效考核指标枚举
 *
 * @author pzh
 * @date  2025/06/26 09:04
 **/
@Getter
public enum OrgAssEnum {

    NEW_CONTRACT_TOTAL("新签合同（自拓）"),

    PRO_VALUE("当年产值金额"),

    INCOME_TOTAL("回款指标"),

    PROFIT_TOTAL("净利润"),

    BUDGET_EXEC_RATE("预算执行率"),
    ;

    private final String value;

    OrgAssEnum(String value) {
        this.value = value;
    }

    public static List<String> getEnumValues(){
        return Arrays.stream(OrgAssEnum.values()).map(OrgAssEnum::getValue).collect(Collectors.toList());
    }

}
