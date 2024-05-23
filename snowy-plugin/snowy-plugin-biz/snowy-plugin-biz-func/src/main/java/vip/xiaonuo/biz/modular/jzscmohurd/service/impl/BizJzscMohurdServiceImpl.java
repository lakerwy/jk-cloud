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
package vip.xiaonuo.biz.modular.jzscmohurd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.jzscmohurd.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.jzscmohurd.entity.BizJzscMohurd;
import vip.xiaonuo.biz.modular.jzscmohurd.mapper.BizJzscMohurdMapper;
import vip.xiaonuo.biz.modular.jzscmohurd.service.BizJzscMohurdService;
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * BIZ_JZSC_MOHURDService接口实现类
 *
 * @author mayike
 * @date  2024/05/20 17:18
 **/
@Service
@Slf4j
public class BizJzscMohurdServiceImpl extends ServiceImpl<BizJzscMohurdMapper, BizJzscMohurd> implements BizJzscMohurdService {

    @Value("${pythonService.url}")
    private  String pythonServiceUrl;

    @Resource
    private SysUserApi sysUserApi;

    @Resource
    private DevConfigApi devConfigApi;

    private static final String WHITE_KEY = "ZZ_WHITE";

    private  static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final AtomicInteger activeTaskCount = new AtomicInteger(0);

    private static final AtomicReference<Future<HttpResponse>> lastTask = new AtomicReference<>(null);

    @Override
    public Page<BizJzscMohurd> page(BizJzscMohurdPageParam bizJzscMohurdPageParam) {
        QueryWrapper<BizJzscMohurd> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRyName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRyName, bizJzscMohurdPageParam.getRyName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRyInQymc())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRyInQymc, bizJzscMohurdPageParam.getRyInQymc());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRegProfName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRegProfName, bizJzscMohurdPageParam.getRegProfName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getQyName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getQyName, bizJzscMohurdPageParam.getQyName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getRegTypeName())) {
            queryWrapper.lambda().like(BizJzscMohurd::getRegTypeName, bizJzscMohurdPageParam.getRegTypeName());
        }
        if(ObjectUtil.isNotEmpty(bizJzscMohurdPageParam.getCertRegNo())) {
            queryWrapper.lambda().like(BizJzscMohurd::getCertRegNo, bizJzscMohurdPageParam.getCertRegNo());
        }
        if(ObjectUtil.isAllNotEmpty(bizJzscMohurdPageParam.getSortField(), bizJzscMohurdPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizJzscMohurdPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizJzscMohurdPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizJzscMohurdPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizJzscMohurd::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizJzscMohurdAddParam bizJzscMohurdAddParam) {
        BizJzscMohurd bizJzscMohurd = BeanUtil.toBean(bizJzscMohurdAddParam, BizJzscMohurd.class);
        this.save(bizJzscMohurd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizJzscMohurdEditParam bizJzscMohurdEditParam) {
        BizJzscMohurd bizJzscMohurd = this.queryEntity(bizJzscMohurdEditParam.getId());
        BeanUtil.copyProperties(bizJzscMohurdEditParam, bizJzscMohurd);
        this.updateById(bizJzscMohurd);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizJzscMohurdIdParam> bizJzscMohurdIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizJzscMohurdIdParamList, BizJzscMohurdIdParam::getId));
    }

    @Override
    public BizJzscMohurd detail(BizJzscMohurdIdParam bizJzscMohurdIdParam) {
        return this.queryEntity(bizJzscMohurdIdParam.getId());
    }

    @Override
    public BizJzscMohurd queryEntity(String id) {
        BizJzscMohurd bizJzscMohurd = this.getById(id);
        if(ObjectUtil.isEmpty(bizJzscMohurd)) {
            throw new CommonException("BIZ_JZSC_MOHURD不存在，id值为：{}", id);
        }
        return bizJzscMohurd;
    }
    @Override
    public void sync(BizJzscMohurdSyncParam bizJzscMohurdSyncParam) {
        if(activeTaskCount.get()>1){
            throw new CommonException("上次同步暂未结束，请稍后再试");
        }
        String valueByKey = devConfigApi.getValueByKey(WHITE_KEY);
        if(StrUtil.isEmpty(valueByKey)){
            throw  new CommonException("未配置资质白名单");
        }
        JSONArray jsonArray = new JSONArray(valueByKey);
        // 转换为 List
        List<String> whiteList = new ArrayList<>();
        for (Object o : jsonArray) {
            whiteList.add(String.valueOf(o));
        }
        bizJzscMohurdSyncParam.setWhiteList(whiteList);
        if(0==bizJzscMohurdSyncParam.getType()){
            List<Map<String, String>> maps = sysUserApi.syncUserToIm();
            List<BizJzscMohurdSyncParam.JzUser> users = new ArrayList<>();
            BizJzscMohurdSyncParam.JzUser jzUser = null;
            for (Map<String, String> map : maps) {
                String idNumber = map.get("idNumber");
                if(StrUtil.isEmpty(idNumber)){
                    continue;
                }
                jzUser = new BizJzscMohurdSyncParam.JzUser();
                jzUser.setName(map.get("name"));
                jzUser.setIdNumber(map.get("idNumber"));
                users.add(jzUser);
            }
            bizJzscMohurdSyncParam.setUsers(users);
        }else {
            List<BizJzscMohurdSyncParam.JzUser> users = bizJzscMohurdSyncParam.getUsers();
            if(CollUtil.isEmpty(users)){
                throw new CommonException("用户信息列表不能为空");
            }
        }

        Future<HttpResponse> future = executor.submit(()->{
            activeTaskCount.incrementAndGet();
            try {
                String json = JSONUtil.toJsonStr(bizJzscMohurdSyncParam);
                return HttpUtil.createPost(pythonServiceUrl + "/webApi/dataservice/query").body(json).execute(true);
            }finally {
                activeTaskCount.decrementAndGet();
            }
        });
        lastTask.set(future);
        try {
            HttpResponse response = future.get();
            log.info("资质校验查询结果: {}", response.getStatus());
        } catch (InterruptedException | ExecutionException e) {
            throw new CommonException("异步任务执行失败", e);
        }
    }
}
