package vip.xiaonuo.dev.modular.job.service;

/**
 * @author yong.ye
 * @date 2023/8/16 15:25
 */
public interface BizTaskJobService {

    /**
     * 系统自动计算 部门绩效考核 - 任务分 - 完成进度分系数
     */
    void autoUpdateProgressRate();
}
