package org.work.flows.workflow;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.work.flows.work.DefaultWorkReport;
import org.work.flows.work.Work;
import org.work.flows.work.WorkReport;
import org.work.flows.work.WorkStatus;
import org.work.flows.workflow.ParallelFlowExecutor;

import java.util.Arrays;
import java.util.List;

public class ParallelFlowExecutorTest {

    @Test
    public void call() throws Exception {

        // given
        HelloWorldWork work1 = new HelloWorldWork("work1", WorkStatus.COMPLETED);
        HelloWorldWork work2 = new HelloWorldWork("work2", WorkStatus.FAILED);
        ParallelFlowExecutor parallelFlowExecutor = new ParallelFlowExecutor();

        // when
        List<WorkReport> workReports = parallelFlowExecutor.executeInParallel(Arrays.asList(work1, work2));

        // then
        Assertions.assertThat(workReports).hasSize(2);
        Assertions.assertThat(work1.isExecuted()).isTrue();
        Assertions.assertThat(work2.isExecuted()).isTrue();
    }

    class HelloWorldWork implements Work {

        private String name;
        private WorkStatus status;
        private boolean executed;

        HelloWorldWork(String name, WorkStatus status) {
            this.name = name;
            this.status = status;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public WorkReport call() {
            executed = true;
            return new DefaultWorkReport(status);
        }

        public boolean isExecuted() {
            return executed;
        }
    }

}