package org.work.flows.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.work.flows.work.Work;
import org.work.flows.work.WorkReport;

/**
 * @author Darshan Kumar (darshan92kumar@gmail.com)
 *
 */
class ParallelFlowExecutor {

    private static final Logger LOGGER = Logger.getLogger(ParallelFlowExecutor.class.getName());
    private ExecutorService workExecutor;

    ParallelFlowExecutor() {
        this.workExecutor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());;
    }

    List<WorkReport> executeInParallel(List<Work> works) {
        // re-init in case it has been shut down in a previous run (See question 3)
        if(workExecutor.isShutdown()) {
            workExecutor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        }

        // submit works to be executed in parallel
        Map<Work, Future<WorkReport>> reportFutures = new HashMap<>();
        for (Work work : works) {
            Future<WorkReport> reportFuture = workExecutor.submit(work);
            reportFutures.put(work, reportFuture);
        }

        // poll for work completion
        int finishedWorks = works.size();
        while (finishedWorks > 0) {
            for (Future<WorkReport> future : reportFutures.values()) {
                if (future != null && future.isDone()) {
                        finishedWorks--;
                }
            }
        }

        // gather reports
        List<WorkReport> workReports = new ArrayList<>();
        for (Map.Entry<Work, Future<WorkReport>> entry : reportFutures.entrySet()) {
            try {
                workReports.add(entry.getValue().get());
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.log(Level.WARNING, "Unable to get work report of work ''{0}''", entry.getKey().getName());
            }
        }

        workExecutor.shutdown(); // because if not, the workflow engine may run forever.. 
        return workReports;
    }
}
