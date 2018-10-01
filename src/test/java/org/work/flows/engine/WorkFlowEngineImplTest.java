package org.work.flows.engine;

import org.junit.Test;
import org.mockito.Mockito;
import org.work.flows.engine.WorkFlowEngine;
import org.work.flows.engine.WorkFlowEngineImpl;
import org.work.flows.work.DefaultWorkReport;
import org.work.flows.work.Work;
import org.work.flows.work.WorkReport;
import org.work.flows.work.WorkStatus;
import org.work.flows.workflow.*;

import static org.work.flows.engine.WorkFlowEngineBuilder.aNewWorkFlowEngine;
import static org.work.flows.work.WorkReportPredicate.COMPLETED;
import static org.work.flows.workflow.ConditionalFlow.Builder.aNewConditionalFlow;
import static org.work.flows.workflow.ParallelFlow.Builder.aNewParallelFlow;
import static org.work.flows.workflow.RepeatFlow.Builder.aNewRepeatFlow;
import static org.work.flows.workflow.SequentialFlow.Builder.aNewSequentialFlow;

public class WorkFlowEngineImplTest {

    private WorkFlowEngine workFlowEngine = new WorkFlowEngineImpl();

    @Test
    public void run() throws Exception {
        // given
        WorkFlow workFlow = Mockito.mock(WorkFlow.class);

        // when
        workFlowEngine.run(workFlow);

        // then
        Mockito.verify(workFlow).call();
    }

    /**
     * The following tests are not really unit tests, but serve as examples of how to create a workflow and execute it
     */

    @Test
    public void composeWorkFlowFromSeparateFlowsAndExecuteIt() throws Exception {

        PrintMessageWork work1 = new PrintMessageWork("foo");
        PrintMessageWork work2 = new PrintMessageWork("hello");
        PrintMessageWork work3 = new PrintMessageWork("world");
        PrintMessageWork work4 = new PrintMessageWork("done");

        RepeatFlow repeatFlow = aNewRepeatFlow()
                .named("print foo 3 times")
                .repeat(work1)
                .times(3)
                .build();

        ParallelFlow parallelFlow = aNewParallelFlow()
                .named("print 'hello' and 'world' in parallel")
                .execute(work2, work3)
                .build();

        ConditionalFlow conditionalFlow = aNewConditionalFlow()
                .execute(parallelFlow)
                .when(COMPLETED)
                .then(work4)
                .build();

        SequentialFlow sequentialFlow = aNewSequentialFlow()
                .execute(repeatFlow)
                .then(conditionalFlow)
                .build();

        WorkFlowEngine workFlowEngine = aNewWorkFlowEngine().build();
        WorkReport workReport = workFlowEngine.run(sequentialFlow);
        System.out.println("workflow report = " + workReport);
    }

    @Test
    public void defineWorkFlowInlineAndExecuteIt() throws Exception {

        PrintMessageWork work1 = new PrintMessageWork("foo");
        PrintMessageWork work2 = new PrintMessageWork("hello");
        PrintMessageWork work3 = new PrintMessageWork("world");
        PrintMessageWork work4 = new PrintMessageWork("done");

        WorkFlow workflow = aNewSequentialFlow()
                .execute(aNewRepeatFlow()
                            .named("print foo 3 times")
                            .repeat(work1)
                            .times(3)
                            .build())
                .then(aNewConditionalFlow()
                        .execute(aNewParallelFlow()
                                    .named("print 'hello' and 'world' in parallel")
                                    .execute(work2, work3)
                                    .build())
                        .when(COMPLETED)
                        .then(work4)
                        .build())
                .build();

        WorkFlowEngine workFlowEngine = aNewWorkFlowEngine().build();
        WorkReport workReport = workFlowEngine.run(workflow);
        System.out.println("workflow report = " + workReport);
    }

    static class PrintMessageWork implements Work {

        private String message;

        public PrintMessageWork(String message) {
            this.message = message;
        }

        public String getName() {
            return "print message work";
        }

        public WorkReport call() {
            System.out.println(message);
            return new DefaultWorkReport(WorkStatus.COMPLETED);
        }

    }
}