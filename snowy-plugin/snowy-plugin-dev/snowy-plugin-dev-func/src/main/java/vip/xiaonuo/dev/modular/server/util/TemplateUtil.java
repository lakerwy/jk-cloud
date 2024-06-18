package vip.xiaonuo.dev.modular.server.util;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import vip.xiaonuo.dev.modular.server.entity.TemplateInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/17 11:45
 */
public class TemplateUtil {
    public static List<TemplateInfo> parseExcel(File excelFilePath) {
        List<TemplateInfo> templateInfoList = new ArrayList<>();

        ExcelReader reader = ExcelUtil.getReader(excelFilePath);
        List<Map<String, Object>> readAll = reader.readAll();

        for (Map<String, Object> row : readAll) {
            String companyName = (String) row.get("公司名称");
            String departmentName = (String) row.get("部门名称");
            String parentDepartmentName = (String) row.get("父部门名称");
            String positionOrRole = (String) row.get("岗位|角色");
            String type = String.valueOf(row.get("类型(0岗位 1角色)"));
            String code = (String) row.get("编码");
            TemplateInfo info = new TemplateInfo(companyName, departmentName, parentDepartmentName, positionOrRole, type, code);
            templateInfoList.add(info);
        }

        return templateInfoList;
    }
}
