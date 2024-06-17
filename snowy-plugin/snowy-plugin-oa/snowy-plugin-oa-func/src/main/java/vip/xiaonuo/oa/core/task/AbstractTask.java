package vip.xiaonuo.oa.core.task;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/15 13:11
 */
@Slf4j
public abstract class AbstractTask {
    private String taskName;

    public AbstractTask(String taskName) {
        this.taskName = taskName;
    }

    public void execute() {
        log.info("Starting task {}",taskName);
        try {
            doTask();
        } catch (Exception e) {
            handleException(e);
        }
        log.info("Finished task {}",taskName);
    }

    protected abstract void doTask() throws Exception;

    protected void handleException(Exception e) {
        log.error("Error during task execution: {}",e.getMessage());
    }
}
