package org.work.flows.workflow;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.work.flows.work.Work;
import org.work.flows.workflow.SequentialFlow;

public class SequentialFlowTest {

    @Test
    public void call() throws Exception {
        // given
        Work work1 = Mockito.mock(Work.class);
        Work work2 = Mockito.mock(Work.class);
        Work work3 = Mockito.mock(Work.class);
        SequentialFlow sequentialFlow = SequentialFlow.Builder.aNewSequentialFlow()
                .execute(work1)
                .then(work2)
                .then(work3)
                .build();

        // when
        sequentialFlow.call();

        // then
        InOrder inOrder = Mockito.inOrder(work1, work2, work3);
        inOrder.verify(work1, Mockito.times(1)).call();
        inOrder.verify(work2, Mockito.times(1)).call();
        inOrder.verify(work3, Mockito.times(1)).call();
    }

}