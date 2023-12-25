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
package vip.xiaonuo.biz.modular.teambition.taskInfoComment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.entity.BizTaskComment;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.mapper.BizTaskCommentMapper;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentAddParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentEditParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentIdParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.param.BizTaskCommentPageParam;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.service.BizTaskCommentService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.api.DbsApi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 任务管理平台-任务评论Service接口实现类
 *
 * @author ljy
 * @date  2023/12/13 17:51
 **/
@DS("slave_1")
@Service
public class BizTaskCommentServiceImpl extends ServiceImpl<BizTaskCommentMapper, BizTaskComment> implements BizTaskCommentService {

    @Override
    public Page<BizTaskComment> page(BizTaskCommentPageParam bizTaskCommentPageParam) {
        QueryWrapper<BizTaskComment> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskCommentPageParam.getTaskInfoId())) {
            queryWrapper.lambda().eq(BizTaskComment::getTaskInfoId, bizTaskCommentPageParam.getTaskInfoId());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskCommentPageParam.getSortField(), bizTaskCommentPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskCommentPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskCommentPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskCommentPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizTaskComment::getId);
        }
        Page<BizTaskComment> page = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<BizTaskComment> commentList = setBizTaskCommentList(page.getRecords());
        page.setRecords(commentList);
        return page;
    }

    private List<BizTaskComment> setBizTaskCommentList(List<BizTaskComment> commentList){
        if(CollectionUtil.isEmpty(commentList)){
            return commentList;
        }
        List<BizTaskComment> list = new ArrayList<>();
        commentList.forEach(comment -> {
            if(comment.getParentId().equals("0")){
                for(BizTaskComment bizTaskComment : commentList){
                    if(comment.getId().equals(bizTaskComment.getParentId())){
                        List<BizTaskComment> comments = CollectionUtil.isNotEmpty(comment.getBizTaskCommentList())? comment.getBizTaskCommentList() : new ArrayList<>();
                        comments.add(bizTaskComment);
                        comment.setBizTaskCommentList(comments);
                    }
                }
                list.add(comment);
            }
        });
        return list;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskCommentAddParam bizTaskCommentAddParam) {
        BizTaskComment bizTaskComment = BeanUtil.toBean(bizTaskCommentAddParam, BizTaskComment.class);
        bizTaskComment.setParentId("0");
        if(StringUtils.isNotEmpty(bizTaskCommentAddParam.getParentId())){
            Map<String,String> map = newHandleSqlIdExecution(bizTaskCommentAddParam.getParentId());
            if(map.get("commentId") != null){
                bizTaskComment.setParentId(map.get("commentId"));
            }
        }
        this.save(bizTaskComment);
    }

    private Map<String,String> newHandleSqlIdExecution(String commentId){
        if(ObjectUtil.isNotEmpty(commentId)) {
            Connection conn;
            try {
                DbsApi dbsApi = SpringUtil.getBean(DbsApi.class);
                conn = dbsApi.getCurrentDataSource().getConnection();
            } catch (SQLException e) {
                throw new CommonException(e.getMessage());
            }
            try {
                AtomicReference<Map<String,String>> result = new AtomicReference<>();
                conn.setAutoCommit(false);
                String finalSql = "SELECT t2.ID AS commentId FROM " +
                        "(SELECT @r AS _id,( SELECT @r := PARENT_ID FROM BIZ_TASK_COMMENT WHERE ID = _id ) AS parent_id, @l := @l + 1 AS lvl " +
                        "FROM ( SELECT @r := ?, @l := 0 ) vars, BIZ_TASK_COMMENT AS h " +
                        "WHERE  @r != '0' and @r is not null) t1 JOIN BIZ_TASK_COMMENT t2 ON t1._id = t2.ID " +
                        "ORDER BY t1.lvl DESC  LIMIT 1;";
                if(ObjectUtil.isNotEmpty(finalSql)) {
                    try {
                        result.set(SqlExecutor.query(conn, finalSql, new RsHandler<Map<String,String>>() {
                            @Override
                            public Map<String,String> handle(ResultSet resultSet) throws SQLException {
                                //获取
                                Map<String, String> result = new HashMap<>();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                if (resultSet.next()) {
                                    for (int i = 1; i <= columnCount; i++) {
                                        String columnName = metaData.getColumnLabel(i);
                                        String value = resultSet.getString(i);
                                        result.put(columnName, value);
                                    }
                                }
                                return result;
                            }},commentId));
                    } catch (SQLException e) { throw new CommonException(e.getMessage()); }
                }
                conn.commit();
                return result.get();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } finally {
                DbUtil.close(conn);
            }
        }
        return new HashMap<>();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskCommentEditParam bizTaskCommentEditParam) {
        BizTaskComment bizTaskComment = this.queryEntity(bizTaskCommentEditParam.getId());
        BeanUtil.copyProperties(bizTaskCommentEditParam, bizTaskComment);
        this.updateById(bizTaskComment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskCommentIdParam> bizTaskCommentIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskCommentIdParamList, BizTaskCommentIdParam::getId));
    }

    @Override
    public BizTaskComment detail(BizTaskCommentIdParam bizTaskCommentIdParam) {
        return this.queryEntity(bizTaskCommentIdParam.getId());
    }

    @Override
    public BizTaskComment queryEntity(String id) {
        BizTaskComment bizTaskComment = this.getById(id);
        if(ObjectUtil.isEmpty(bizTaskComment)) {
            throw new CommonException("任务管理平台-任务评论不存在，id值为：{}", id);
        }
        return bizTaskComment;
    }
}
