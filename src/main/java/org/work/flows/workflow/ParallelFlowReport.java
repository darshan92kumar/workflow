package org.work.flows.workflow;

import java.util.ArrayList;
import java.util.List;

import org.work.flows.work.WorkReport;
import org.work.flows.work.WorkStatus;

/**
 * Aggregate report of all works reports.
 */
public class ParallelFlowReport implements WorkReport {

    private List<WorkReport> reports;

    /**
     * Create a new {@link ParallelFlowReport}.
     */
    public ParallelFlowReport() {
        this(new ArrayList<>());
    }

    /**
     * Create a new {@link ParallelFlowReport}.
     * @param reports of works executed in parallel
     */
    public ParallelFlowReport(List<WorkReport> reports) {
        this.reports = reports;
    }

    /**
     * Get partial reports.
     *
     * @return partial reports
     */
    public List<WorkReport> getReports() {
        return reports;
    }

    void add(WorkReport workReport) {
        reports.add(workReport);
    }

    void addAll(List<WorkReport> workReports) {
        reports.addAll(workReports);
    }

    /**
     * Return the status of the parallel flow.
     *
     * The status of a parallel flow is defined as follows:
     *
     * <ul>
     *     <li>COMPLETED: If all works have successfully completed</li>
     *     <li>FAILED: If one of the works has failed</li>
     * </ul>
     * @return workflow status
     */
    public WorkStatus getStatus() {
        for (WorkReport report : reports) {
            if (report.getStatus().equals(WorkStatus.FAILED)) {
                return WorkStatus.FAILED;
            }
        }
        return WorkStatus.COMPLETED;
    }

    /**
     * Return the first error of partial reports.
     *
     * @return the first error of partial reports.
     */
    @Override
    public Throwable getError() {
        for (WorkReport report : reports) {
            Throwable error = report.getError();
            if (error != null) {
                return error;
            }
        }
        return null;
    }
}
