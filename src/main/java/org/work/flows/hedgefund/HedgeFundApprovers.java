package org.work.flows.hedgefund;

import org.work.flows.work.DefaultWorkReport;
import org.work.flows.work.Work;
import org.work.flows.work.WorkReport;
import org.work.flows.work.WorkStatus;

/**
 * @author Darshan Kumar  (darshan92kumar@gmail.com)
 *
 */
public class HedgeFundApprovers implements Work {
	private String message;
	
	public HedgeFundApprovers(String message) {
		this.message = message;
	}

	@Override
	public String getName() {
		return "message work";
	}

	@Override
	public WorkReport call() {
		System.out.println(message);
        return new DefaultWorkReport(WorkStatus.COMPLETED);
	}

}
