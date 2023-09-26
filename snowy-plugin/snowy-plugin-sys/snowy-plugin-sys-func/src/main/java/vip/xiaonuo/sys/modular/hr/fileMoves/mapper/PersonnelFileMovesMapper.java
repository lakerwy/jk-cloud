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
package vip.xiaonuo.sys.modular.hr.fileMoves.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import vip.xiaonuo.sys.modular.hr.fileMoves.entity.PersonnelFileMoves;
import vip.xiaonuo.sys.modular.hr.fileMoves.vo.PersonnelFileMovesVo;
import vip.xiaonuo.sys.modular.hr.personnel.vo.PersonnelListVo;

/**
 * 业务表-人员异动主表Mapper接口
 *
 * @author mayike
 * @date  2023/07/10 16:56
 **/
public interface PersonnelFileMovesMapper extends BaseMapper<PersonnelFileMoves> {

    @Delete("DELETE FROM PERSONNEL_FILE_MOVES WHERE FILE_ID=#{fileId}")
    public void deleteByFileId(String fileId);
    Page<PersonnelFileMovesVo> pageList(Page<PersonnelFileMovesVo> page, @Param(Constants.WRAPPER) Wrapper<PersonnelFileMoves> queryWrapper);

    PersonnelFileMovesVo getInfoById(String id);
}
