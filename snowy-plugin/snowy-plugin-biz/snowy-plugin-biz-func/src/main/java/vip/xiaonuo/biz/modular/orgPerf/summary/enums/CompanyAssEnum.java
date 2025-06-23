package vip.xiaonuo.biz.modular.orgPerf.summary.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公司绩效考核指标枚举
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
@Getter
public enum CompanyAssEnum {

    NEW_CONTRACT_TOTAL("新签合同总额"),

    INCOME_TOTAL("收入总额"),

    PRO_VALUE("产值"),

    PROFIT_TOTAL("利润总额"),
    ;

    private final String value;

    CompanyAssEnum(String value) {
        this.value = value;
    }

    public static List<String> getEnumValues(){
        return Arrays.stream(CompanyAssEnum.values()).map(CompanyAssEnum::getValue).collect(Collectors.toList());
    }

    public static String getEnumValue(CompanyAssEnum companyAssEnum){
        return companyAssEnum.getValue();
    }

}
