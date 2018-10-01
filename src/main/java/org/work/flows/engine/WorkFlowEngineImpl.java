package org.work.flows.engine;

import org.work.flows.work.WorkReport;
import org.work.flows.workflow.WorkFlow;

import java.util.logging.Level;
import java.util.logging.Logger;

class WorkFlowEngineImpl implements WorkFlowEngine {

    private static final Logger LOGGER = Logger.getLogger(WorkFlowEngineImpl.class.getName());

    public WorkReport run(WorkFlow workFlow) {
        LOGGER.log(Level.INFO, "Running workflow ''{0}''", workFlow.getName());
        return workFlow.call();
    }

}
