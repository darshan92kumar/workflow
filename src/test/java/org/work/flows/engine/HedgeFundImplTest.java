package org.work.flows.engine;

import static org.work.flows.engine.WorkFlowEngineBuilder.aNewWorkFlowEngine;
import static org.work.flows.work.WorkReportPredicate.COMPLETED;
import static org.work.flows.workflow.ConditionalFlow.Builder.aNewConditionalFlow;
import static org.work.flows.workflow.ParallelFlow.Builder.aNewParallelFlow;
import static org.work.flows.workflow.SequentialFlow.Builder.aNewSequentialFlow;

import org.junit.Test;
import org.work.flows.engine.WorkFlowEngine;
import org.work.flows.work.DefaultWorkReport;
import org.work.flows.work.Work;
import org.work.flows.work.WorkReport;
import org.work.flows.work.WorkStatus;
import org.work.flows.workflow.WorkFlow;

/**
 * @author Darshan Kumar (darshan92kumar@gmail.com)
 *
 */
public class HedgeFundImplTest {
	
	@Test
    public void defineWorkFlowHedgeFundAndExecuteIt() throws Exception {

		HedgeFundWork work1 = new HedgeFundWork("Trader Raise Fund Request");
		HedgeFundWork work2 = new HedgeFundWork("Research Analyst Approval");
		HedgeFundWork work3 = new HedgeFundWork("Fund Manager Approval");
		HedgeFundWork work4 = new HedgeFundWork("Division Head Approval");
		HedgeFundWork work5 = new HedgeFundWork("Operations Approval");

        WorkFlow workflow = aNewSequentialFlow()
                .execute(aNewSequentialFlow()   // Start workflow with the request
                            .named(work1.getName())  
                            .build())
                .then(aNewConditionalFlow()
                        .execute(aNewParallelFlow()  // Execute approvals parallely
                                    .named(work2.getName()+" and "+work3.getName())
                                    .execute(work2, work3)
                                    .build())
                        .when(COMPLETED)  // when above completes execute next in hierarchy
                        .then(work4)
                        .when(COMPLETED) // when above completes execute next in hierarchy
                        .then(work5)
                        .build())
                .build();

        WorkFlowEngine workFlowEngine = aNewWorkFlowEngine().build();
        WorkReport workReport = workFlowEngine.run(workflow);
        System.out.println("workflow report = " + workReport);
    }
	
	static class HedgeFundWork implements Work {

        private String message;

        public HedgeFundWork(String message) {
            this.message = message;
        }

        public String getName() {
            return "hedge fund message work";
        }        
        
        /* (non-Javadoc)
         * do the work : approve the workflow
         */
        public WorkReport call() {
            System.out.println(message);
            return new DefaultWorkReport(WorkStatus.COMPLETED);
        }

    }

}
