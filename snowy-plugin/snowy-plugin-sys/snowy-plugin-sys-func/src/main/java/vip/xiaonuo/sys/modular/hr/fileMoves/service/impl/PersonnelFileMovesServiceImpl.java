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
package vip.xiaonuo.sys.modular.hr.fileMoves.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.fileMoves.service.PersonnelFileMovesService;
import vip.xiaonuo.sys.modular.hr.fileMoves.mapper.PersonnelFileMovesMapper;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesAddParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesEditParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesIdParam;
import vip.xiaonuo.sys.modular.hr.fileMoves.param.PersonnelFileMovesPageParam;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.sys.modular.hr.fileMoves.vo.PersonnelFileMovesVo;

import java.util.List;

/**
 * 业务表-人员异动主表Service接口实现类
 *
 * @author mayike
 * @date  2023/07/10 16:56
 **/
@Service
public class PersonnelFileMovesServiceImpl extends ServiceImpl<PersonnelFileMovesMapper, PersonnelFileMoves> implements PersonnelFileMovesService {
//    @Resource
//    private FlwCommonApi flwCommonApi;

    @Override
    public Page<PersonnelFileMoves> page(PersonnelFileMovesPageParam personnelFileMovesPageParam) {
        QueryWrapper<PersonnelFileMoves> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(personnelFileMovesPageParam.getSortField(), personnelFileMovesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelFileMovesPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelFileMovesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelFileMovesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(PersonnelFileMoves::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PersonnelFileMovesAddParam personnelFileMovesAddParam) {
        PersonnelFileMoves personnelFileMoves = BeanUtil.toBean(personnelFileMovesAddParam, PersonnelFileMoves.class);
        String modelId = personnelFileMovesAddParam.getModelId();
//        if(ObjectUtil.isNotEmpty(modelId)){
//            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
//            String sn = flwCommonApi.getSnByModelId(modelId);
//            if(ObjectUtil.isNotEmpty(sn)){
//                personnelFileMoves.setSn(sn);
//            }
//        }
        personnelFileMoves.setApplyUser(StpLoginUserUtil.getLoginUser().getName());
        personnelFileMoves.setApplyTime(DateUtil.date());
        this.save(personnelFileMoves);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PersonnelFileMovesEditParam personnelFileMovesEditParam) {
        PersonnelFileMoves personnelFileMoves = this.queryEntity(personnelFileMovesEditParam.getId());
        BeanUtil.copyProperties(personnelFileMovesEditParam, personnelFileMoves);
        this.updateById(personnelFileMoves);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PersonnelFileMovesIdParam> personnelFileMovesIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(personnelFileMovesIdParamList, PersonnelFileMovesIdParam::getId));
    }

    @Override
    public PersonnelFileMoves detail(PersonnelFileMovesIdParam personnelFileMovesIdParam) {
//        return this.queryEntity(personnelFileMovesIdParam.getId());
        return baseMapper.selectById(personnelFileMovesIdParam.getId());
    }

    @Override
    public PersonnelFileMoves queryEntity(String id) {
        PersonnelFileMoves personnelFileMoves = this.getById(id);
        if(ObjectUtil.isEmpty(personnelFileMoves)) {
            throw new CommonException("业务表-人员异动主表不存在，id值为：{}", id);
        }
        return personnelFileMoves;
    }

    @Override
    public void deleteByFileId(String fileId) {
        baseMapper.deleteByFileId(fileId);
    }

    @Override
    public void deleteNewFileId(List<String> ids) {
        QueryWrapper<PersonnelFileMoves> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(PersonnelFileMoves::getFileId,ids);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public Page<PersonnelFileMovesVo> pageList(PersonnelFileMovesPageParam personnelFileMovesPageParam) {
        QueryWrapper<PersonnelFileMoves> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(personnelFileMovesPageParam.getMoveType())){
            queryWrapper.lambda().eq(PersonnelFileMoves::getMoveType,personnelFileMovesPageParam.getMoveType());
        }
        if(ObjectUtil.isNotEmpty(personnelFileMovesPageParam.getSearchKey())){
            queryWrapper.and(wrapper ->wrapper.like("b.`NAME`",personnelFileMovesPageParam.getSearchKey())
                    .or().like("a.ORIG_DEPT",personnelFileMovesPageParam.getSearchKey())
                    .or().like("a.SN",personnelFileMovesPageParam.getSearchKey())
            );
        }

        if(ObjectUtil.isAllNotEmpty(personnelFileMovesPageParam.getSortField(), personnelFileMovesPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(personnelFileMovesPageParam.getSortOrder());
            queryWrapper.orderBy(true, personnelFileMovesPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(personnelFileMovesPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PersonnelFileMoves::getSn);
        }
        return baseMapper.pageList(CommonPageRequest.defaultPage(), queryWrapper);
    }
}
