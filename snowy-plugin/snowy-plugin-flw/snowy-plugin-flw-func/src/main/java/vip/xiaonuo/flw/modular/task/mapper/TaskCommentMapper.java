package vip.xiaonuo.flw.modular.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.camunda.bpm.engine.impl.persistence.entity.CommentEntity;

/**
 * 任务评论 操作 mapper
 */
public interface TaskCommentMapper extends BaseMapper<CommentEntity> {

    void deleteCommentsById(String commentId);
}
