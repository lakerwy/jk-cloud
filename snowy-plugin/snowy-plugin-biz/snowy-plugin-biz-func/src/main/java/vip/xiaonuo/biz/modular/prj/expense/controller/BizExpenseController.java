package vip.xiaonuo.biz.modular.prj.expense.controller;

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

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.prj.allotment.param.BizPrjAllotmentEditParam;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpenseService;
import vip.xiaonuo.biz.modular.prj.expense.vo.ExcelParam;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 业务表-项目拨付款申请单控制器
 *
 * @author yong.ye
 * @date  2023/09/28 11:30
 */
@Api(tags = "业务表-费用报销控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizExpenseController {

    @Resource
    private BizExpenseService bizExpenseService;

    /**
     * 业务表-费用报销 - 生成报表
     *
     * @author yong.ye
     * @date  2023/09/28 11:30
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("业务表-费用报销 - 生成报表")
    @CommonLog("业务表-费用报销 - 生成报表")
    @GetMapping("/biz/prj/expense/genExcel")
    public void genExcel() { //@Valid ExcelParam excelParam, HttpServletResponse response
        bizExpenseService.genExcel();
    }
}
