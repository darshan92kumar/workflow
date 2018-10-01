package org.work.flows.workflow;

import org.junit.Test;
import org.mockito.Mockito;
import org.work.flows.work.Work;
import org.work.flows.work.WorkReportPredicate;
import org.work.flows.workflow.RepeatFlow;

public class RepeatFlowTest {

    @Test
    public void testRepeatUntil() throws Exception {
        // given
        Work work = Mockito.mock(Work.class);
        WorkReportPredicate predicate = WorkReportPredicate.ALWAYS_FALSE;
        RepeatFlow repeatFlow = RepeatFlow.Builder.aNewRepeatFlow()
                .repeat(work)
                .until(predicate)
                .build();

        // when
        repeatFlow.call();

        // then
        Mockito.verify(work, Mockito.times(1)).call();
    }

    @Test
    public void testRepeatTimes() throws Exception {
        // given
        Work work = Mockito.mock(Work.class);
        RepeatFlow repeatFlow = RepeatFlow.Builder.aNewRepeatFlow()
                .repeat(work)
                .times(3)
                .build();

        // when
        repeatFlow.call();

        // then
        Mockito.verify(work, Mockito.times(3)).call();
    }

}