package org.work.flows.workflow;

import java.util.UUID;

import org.work.flows.work.NoOpWork;
import org.work.flows.work.Work;
import org.work.flows.work.WorkReport;
import org.work.flows.work.WorkReportPredicate;

/**
 * A repeat flow executes a work repeatedly until its report is satisfied by a given predicate.
 */
public class RepeatFlow extends AbstractWorkFlow {

    private Work work;
    private WorkReportPredicate predicate;

    RepeatFlow(String name, Work work, WorkReportPredicate predicate) {
        super(name);
        this.work = work;
        this.predicate = predicate;
    }

    /**
     * {@inheritDoc}
     */
    public WorkReport call() {
        WorkReport workReport;
        do {
            workReport = work.call();
        } while (predicate.apply(workReport));
        return workReport;
    }

    public static class Builder {

        private String name;
        private Work work;
        private WorkReportPredicate predicate;

        private Builder() {
            this.name = UUID.randomUUID().toString();
            this.work = new NoOpWork();
            this.predicate = WorkReportPredicate.ALWAYS_FALSE;
        }

        public static RepeatFlow.Builder aNewRepeatFlow() {
            return new RepeatFlow.Builder();
        }

        public RepeatFlow.Builder named(String name) {
            this.name = name;
            return this;
        }

        public RepeatFlow.Builder repeat(Work work) {
            this.work = work;
            return this;
        }

        public RepeatFlow.Builder times(int times) {
            return until(WorkReportPredicate.TimesPredicate.times(times));
        }

        public RepeatFlow.Builder until(WorkReportPredicate predicate) {
            this.predicate = predicate;
            return this;
        }

        public RepeatFlow build() {
            return new RepeatFlow(name, work, predicate);
        }
    }
}
