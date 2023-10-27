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
package vip.xiaonuo.pan.modular.fileRight.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.pan.modular.file.constant.FileRight;
import vip.xiaonuo.pan.modular.fileRight.entity.PanFileRight;
import vip.xiaonuo.pan.modular.fileRight.mapper.PanFileRightMapper;
import vip.xiaonuo.pan.modular.fileRight.param.*;
import vip.xiaonuo.pan.modular.fileRight.service.PanFileRightService;
import vip.xiaonuo.pan.modular.fileRight.vo.PanFileRightVo;
import vip.xiaonuo.pan.modular.fileRight.vo.UserFileRightVo;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 网盘权限配置表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/10/25 14:32
 **/
@Service
public class PanFileRightServiceImpl extends ServiceImpl<PanFileRightMapper, PanFileRight> implements PanFileRightService {

    @Resource
    private UserFileService userFileService;
    @Resource
    private PanFileRightMapper panFileRightMapper;

//    @Override
//    public Page<PanFileRight> page(PanFileRightPageParam panFileRightPageParam) {
//        QueryWrapper<PanFileRight> queryWrapper = new QueryWrapper<>();
//        if(ObjectUtil.isAllNotEmpty(panFileRightPageParam.getSortField(), panFileRightPageParam.getSortOrder())) {
//            CommonSortOrderEnum.validate(panFileRightPageParam.getSortOrder());
//            queryWrapper.orderBy(true, panFileRightPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
//                    StrUtil.toUnderlineCase(panFileRightPageParam.getSortField()));
//        } else {
//            queryWrapper.lambda().orderByAsc(PanFileRight::getId);
//        }
//        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
//    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PanFileRightAddParam panFileRightAddParam) {
        List<PanFileRight> panFileRightList = BeanUtil.copyToList(panFileRightAddParam.getObjectRightList(),PanFileRight.class);
        panFileRightList.forEach(x->{x.setUserFileId(panFileRightAddParam.getUserFileId());});
        this.saveBatch(panFileRightList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PanFileRightEditParam panFileRightEditParam) {
        //最新设置
        List<PanFileRight> newPanFileRights = BeanUtil.copyToList(panFileRightEditParam.getObjectRightList(),PanFileRight.class);
        newPanFileRights.forEach(x->{
            x.setUserFileId(panFileRightEditParam.getUserFileId());
        });

        //根据文件Id获取历史权限设置
        List<PanFileRight> oldFileRights = this.list(new QueryWrapper<PanFileRight>().lambda().eq(PanFileRight::getUserFileId,panFileRightEditParam.getUserFileId()));
        List<String> oldIds = oldFileRights.stream().map(PanFileRight::getId).collect(Collectors.toList());

        //新增 | 更新 | 删除
        //新增
        List<PanFileRight> addList = newPanFileRights.stream().filter(x -> StrUtil.isEmpty(x.getId())).collect(Collectors.toList());
        //更新
        List<PanFileRight> updateList = newPanFileRights.stream().filter(x -> StrUtil.isNotEmpty(x.getId())).collect(Collectors.toList());
        //删除
        List<String> updateIds = updateList.stream().map(PanFileRight::getId).collect(Collectors.toList());
        oldIds.removeAll(updateIds);
        if(CollUtil.isNotEmpty(addList)) this.saveBatch(addList);
        if(CollUtil.isNotEmpty(updateList)) this.updateBatchById(updateList);
        if(CollUtil.isNotEmpty(oldIds)) this.removeByIds(oldIds);
    }

//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public void delete(List<PanFileRightIdParam> panFileRightIdParamList) {
//        // 执行删除
//        this.removeByIds(CollStreamUtil.toList(panFileRightIdParamList, PanFileRightIdParam::getId));
//    }

    @Override
    public List<PanFileRightVo> detail(PanFileRightIdParam panFileRightIdParam) {
        return panFileRightMapper.getFileRightByFileId(panFileRightIdParam.getUserFileId());

    }

    @Override
    public PanFileRight queryEntity(String id) {
        PanFileRight panFileRight = this.getById(id);
        if(ObjectUtil.isEmpty(panFileRight)) {
            throw new CommonException("网盘权限配置表不存在，id值为：{}", id);
        }
        return panFileRight;
    }


    /***
     * 根据 用户文件Id，校验当前用户是否有对应操作权限
     * @param userFileId 用户文件Id(UserFile.Id)
     * @param checkRight 操作权限(FileRight)
     *
     *  check 逻辑说明：
     *                1. 若当前用户为userFileId文件所有者，拥有全部操作权限
     *                2. 若当前用户不为userFileId文件所有者，根据权限配置校验权限
     *                   2.1 若当前userFileId，当前用户，存在权限配置，直接根据配置校验
     *                   2.2 若当前userFileId，当前用户，不存在权限配置，递归父级权限（就近原则），校验权限
     */
    @Override
    public boolean checkFileRight(String userFileId, long checkRight) {
        //1. 获取当前用户
        String userId = StpLoginUserUtil.getLoginUser().getId();

        //2. 获取文件相关信息
        UserFile userFile = userFileService.getById(userFileId);

        //todo
        //3. check 1. 若当前用户为userFileId文件所有者，拥有全部操作权限   2、若当前用户是文件管理员 --- 拥有所有操作权限
        if(ObjectUtil.isNotEmpty(userFile) && userFile.getUserId().equals(userId))
            return true;

        //4. check 2. 若当前用户不为userFileId文件所有者，根据权限配置校验权限
        PanFileRight fileRight = this.getOne(new QueryWrapper<PanFileRight>().lambda()
                .eq(PanFileRight::getUserFileId,userFileId).eq(PanFileRight::getObjectId,userId));

        //5. check 2.1 若当前userFileId，当前用户，存在权限配置，直接根据配置校验
        if(ObjectUtil.isNotEmpty(fileRight)) {
            if(ObjectUtil.isNotEmpty(fileRight.getRightValue()) &&
                    FileRight.hasRight(fileRight.getRightValue(),checkRight))
                return true;
            else
                throw new CommonException("无操作权限");
        }
        //5. check 2.2 若当前userFileId，当前用户，不存在权限配置，递归父级权限（就近原则），校验权限
        else{
            UserFileRightVo rightVo = getParentRight(userFile.getParentId(),userId);
            if(ObjectUtil.isNotEmpty(rightVo) &&
                    ObjectUtil.isNotEmpty(rightVo.getRightValue()) &&
                    FileRight.hasRight(rightVo.getRightValue(),checkRight))
                return true;
            else
                throw new CommonException("无操作权限");
        }
    }


    /***
     * 递归获取，就近权限
     * @param userFileId
     * @param userId
     * @return
     */
    public UserFileRightVo getParentRight(String userFileId,String userId){
        //获取某个userFile及权限信息
        UserFileRightVo rightVo = panFileRightMapper.getFileRight(userFileId,userId);
        if(ObjectUtil.isEmpty(rightVo)) return null;

        //就近，若有权限配置信息，直接返回，否则递归
        if(ObjectUtil.isNotEmpty(rightVo.getRightId()))
            return rightVo;
        else
            return getParentRight(rightVo.getParentId(),userId);
    }

}
