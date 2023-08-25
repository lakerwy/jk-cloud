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
package vip.xiaonuo.dev.modular.deployConf.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.modular.deployConf.entity.HotDeployConf;
import vip.xiaonuo.dev.modular.deployConf.mapper.HotDeployConfMapper;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfAddParam;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfEditParam;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfIdParam;
import vip.xiaonuo.dev.modular.deployConf.param.HotDeployConfPageParam;
import vip.xiaonuo.dev.modular.deployConf.service.HotDeployConfService;
import vip.xiaonuo.dev.modular.file.service.DevFileService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 热部署配置Service接口实现类
 *
 * @author mayike
 * @date  2023/08/10 16:14
 **/
@Service
public class HotDeployConfServiceImpl extends ServiceImpl<HotDeployConfMapper, HotDeployConf> implements HotDeployConfService {

    @Resource
    private DevFileService devFileService;
    @Override
    public Page<HotDeployConf> page(HotDeployConfPageParam hotDeployConfPageParam) {
        QueryWrapper<HotDeployConf> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(hotDeployConfPageParam.getSortField(), hotDeployConfPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(hotDeployConfPageParam.getSortOrder());
            queryWrapper.orderBy(true, hotDeployConfPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(hotDeployConfPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(HotDeployConf::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(HotDeployConfAddParam hotDeployConfAddParam) {
        HotDeployConf hotDeployConf = BeanUtil.toBean(hotDeployConfAddParam, HotDeployConf.class);
        this.save(hotDeployConf);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(HotDeployConfEditParam hotDeployConfEditParam) {
        HotDeployConf hotDeployConf = this.queryEntity(hotDeployConfEditParam.getId());
        BeanUtil.copyProperties(hotDeployConfEditParam, hotDeployConf);
        this.updateById(hotDeployConf);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<HotDeployConfIdParam> hotDeployConfIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(hotDeployConfIdParamList, HotDeployConfIdParam::getId));
    }

    @Override
    public HotDeployConf detail(HotDeployConfIdParam hotDeployConfIdParam) {
        return this.queryEntity(hotDeployConfIdParam.getId());
    }

    @Override
    public HotDeployConf queryEntity(String id) {
        HotDeployConf hotDeployConf = this.getById(id);
        if(ObjectUtil.isEmpty(hotDeployConf)) {
            throw new CommonException("热部署配置不存在，id值为：{}", id);
        }
        return hotDeployConf;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,String> save(String version, MultipartFile file) {
        Map<String,String> map = new HashMap<>();
        String hVersion = version+file.getOriginalFilename();
        int hashValue = HashUtil.rsHash(hVersion);
        HotDeployConf one = this.getOne(new LambdaQueryWrapper<HotDeployConf>().eq(HotDeployConf::getHashvalue, hashValue));
        if(ObjectUtil.isNotEmpty(one)){
            throw new CommonException("该版本包已发布,请重新发布");
        }
        String downUrl = devFileService.uploadReturnUrl(file, "apk","0");
        HotDeployConf hotDeployConf = new HotDeployConf();
        hotDeployConf.setVersion(version);
        hotDeployConf.setPackageUrl(downUrl);
        hotDeployConf.setHashvalue(String.valueOf(hashValue));
        this.save(hotDeployConf);
        map.put("version",version);
        map.put("downUrl",downUrl);
        return map;
    }

    @Override
    public HotDeployConf getInfo() {
        return this.getOne(new LambdaQueryWrapper<HotDeployConf>().orderByDesc(HotDeployConf::getCreateTime).last("limit 1"));
    }
}
