package org.work.flows.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.work.flows.work.Work;
import org.work.flows.work.WorkReport;

/**
 * A parallel flow executes a set of works in parallel.
 *
 * The status of a parallel flow execution is defined as:
 *
 * <ul>
 *     <li>COMPLETED: If all works have successfully completed</li>
 *     <li>FAILED: If one of the works has failed</li>
 * </ul>
 */
public class ParallelFlow extends AbstractWorkFlow {

    private List<Work> works = new ArrayList<>();
    private ParallelFlowExecutor workExecutor;

    ParallelFlow(String name, List<Work> works, ParallelFlowExecutor parallelFlowExecutor) {
        super(name);
        this.works.addAll(works);
        this.workExecutor = parallelFlowExecutor;
    }

    /**
     * {@inheritDoc}
     */
    public ParallelFlowReport call() {
        ParallelFlowReport workFlowReport = new ParallelFlowReport();
        List<WorkReport> workReports = workExecutor.executeInParallel(works);
        workFlowReport.addAll(workReports);
        return workFlowReport;
    }

    public static class Builder {

        private String name;
        private List<Work> works;

        private Builder() {
            this.name = UUID.randomUUID().toString();
            this.works = new ArrayList<>();
        }

        public static ParallelFlow.Builder aNewParallelFlow() {
            return new ParallelFlow.Builder();
        }

        public ParallelFlow.Builder named(String name) {
            this.name = name;
            return this;
        }

        public ParallelFlow.Builder execute(Work... works) {
            this.works.addAll(Arrays.asList(works));
            return this;
        }

        public ParallelFlow build() {
            return new ParallelFlow(name, works, new ParallelFlowExecutor());
        }
    }
}
