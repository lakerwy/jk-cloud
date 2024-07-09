package vip.xiaonuo.biz.modular.prj.expense.service;

import vip.xiaonuo.biz.modular.prj.expense.vo.ExcelParam;

import javax.servlet.http.HttpServletResponse;

public interface BizExpenseService {
    void genExcel(); //ExcelParam excelParam, HttpServletResponse response
}
