package org.work.flows.workflow;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.work.flows.work.Work;
import org.work.flows.workflow.ParallelFlow;
import org.work.flows.workflow.ParallelFlowExecutor;
import org.work.flows.workflow.ParallelFlowReport;

import java.util.Arrays;
import java.util.List;

public class ParallelFlowTest {

    @Test
    public void call() throws Exception {
        // given
        Work work1 = Mockito.mock(Work.class);
        Work work2 = Mockito.mock(Work.class);
        ParallelFlowExecutor parallelFlowExecutor = Mockito.mock(ParallelFlowExecutor.class);
        List<Work> works = Arrays.asList(work1, work2);
        ParallelFlow parallelFlow = new ParallelFlow("pf", works, parallelFlowExecutor);

        // when
        ParallelFlowReport parallelFlowReport = parallelFlow.call();

        // then
        Assertions.assertThat(parallelFlowReport).isNotNull();
        Mockito.verify(parallelFlowExecutor).executeInParallel(works);
    }

}