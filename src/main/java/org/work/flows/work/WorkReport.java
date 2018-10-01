package org.work.flows.work;

/**
 * Report of work execution.
 */
public interface WorkReport {

    /**
     * Get work execution status.
     * @return execution status
     */
    WorkStatus getStatus();

    /**
     * Get error if any.
     *
     * @return error
     */
    Throwable getError();

}
