package vip.xiaonuo.biz.modular.procure.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.crm.subcontract.enums.ProcureApiEnum;
import vip.xiaonuo.biz.modular.crm.subcontract.param.SupplierPageParam;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.SupplierVo;
import vip.xiaonuo.biz.modular.procure.config.ProcureProperties;
import vip.xiaonuo.biz.modular.procure.service.ProcureApiService;
import vip.xiaonuo.common.exception.CommonException;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ProcureApiServiceImpl implements ProcureApiService {

    @Resource
    private ProcureProperties procureProperties;

    @Override
    public Page<SupplierVo> supplierPage(SupplierPageParam param) {
        Page<SupplierVo> page;
        try {
            // 接口地址
            String url = procureProperties.getPrefixUrl() + ProcureApiEnum.SUPPLIER_PAGE_LIST.getValue();
            // 请求参数
            Map<String, Object> map = new HashMap<>();
            map.put("current",param.getCurrent());
            map.put("size",param.getSize());
            map.put("sortField",param.getSortField());
            map.put("sortOrder",param.getSortOrder());
            map.put("searchKey",param.getSearchKey());
            map.put("companyName",param.getCompanyName());
            map.put("isBlacklist",param.getIsBlacklist());
            map.put("isGood",param.getIsGood());
            map.put("isShow",param.getIsShow());
            map.put("approvedTime",param.getApprovedTime());
            map.put("status",param.getStatus());
            map.put("businessScope",param.getBusinessScope());
            // 发送请求
            String res = HttpUtil.get(url, map);
            com.alibaba.fastjson2.JSONObject json = JSON.parseObject(res);
            // 返回成功
            if("200".equals(json.getString("code"))){
                page = JSONUtil.toBean(json.getString("data"), new TypeReference<Page<SupplierVo>>() {},false);
            }else{
                throw new CommonException("调用招采平台【获取供应商信息分页接口】失败：{}", json.toJSONString());
            }
        } catch (Exception e) {
            if(e instanceof CommonException){
                throw (CommonException)e;
            }
            throw new CommonException("调用招采平台【获取供应商信息分页接口】错误：{}", e.getMessage());
        }
        return page;
    }

}
