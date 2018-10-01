package org.work.flows.engine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Main entry point to create {@link WorkFlowEngine} instances.
 */
public class WorkFlowEngineBuilder {

    private static final Logger LOGGER = Logger.getLogger(WorkFlowEngineBuilder.class.getName());

    static {
        try {
            if (System.getProperty("java.util.logging.config.file") == null &&
                    System.getProperty("java.util.logging.config.class") == null) {
                LogManager.getLogManager().readConfiguration(WorkFlowEngineBuilder.class.getResourceAsStream("/logging.properties"));
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Unable to load log configuration file", e);
        }
    }

    /**
     * Create a new {@link WorkFlowEngineBuilder}.
     * @return a new {@link WorkFlowEngineBuilder}.
     */
    public static WorkFlowEngineBuilder aNewWorkFlowEngine() {
        return new WorkFlowEngineBuilder();
    }

    private WorkFlowEngineBuilder() {
    }

    /**
     * Create a new {@link WorkFlowEngine}.
     * @return a new {@link WorkFlowEngine}.
     */
    public WorkFlowEngine build() {
        return new WorkFlowEngineImpl();
    }
}
