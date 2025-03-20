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
package vip.xiaonuo.web.core.provider.sys.sys;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.sys.feign.SubFilingFeign;
import vip.xiaonuo.sys.modular.hr.sub.provider.SubFilingApiProvider;

/**
 * 分包单位备案API Feign接口实现类
 *
 * @author pzh
 * @date  2025/03/12 08:50
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SubFilingFeignProvider implements SubFilingFeign {

    private final SubFilingApiProvider subFilingApiProvider;

    /**
     * 更新分包单位备份明细
     *
     * @author pzh
     * @date  2025/03/12 08:50
     **/
    @Override
    @RequestMapping("/feign/sys/subFiling/updateSubFilingItem")
    public void updateSubFilingItem(@RequestParam(value = "id",required = false) String id,
                                    @RequestParam(value = "sealDeptId",required = false) String sealDeptId,
                                    @RequestParam(value = "subFilingItemList",required = false) String subFilingItemList) {
        subFilingApiProvider.updateSubFilingItem(id, sealDeptId, subFilingItemList);
    }
}