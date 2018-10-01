package org.work.flows.engine;

import org.work.flows.work.WorkReport;
import org.work.flows.workflow.WorkFlow;

/**
 * Interface for workflow engine.
 */
public interface WorkFlowEngine {

    /**
     * Run the given workflow and return its report.
     *
     * @param workFlow to run
     * @return workflow report
     */
    WorkReport run(WorkFlow workFlow);

}
