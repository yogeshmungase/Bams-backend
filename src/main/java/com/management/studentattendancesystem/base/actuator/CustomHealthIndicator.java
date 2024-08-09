package com.management.studentattendancesystem.base.actuator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            long heapMemoryUsed = getHeapMemoryUsage();
            String threadAnalysis = analyzeThreadsForDeadlocks();

            final long MAX_HEAP_MEMORY_USAGE = 500 * 1024 * 1024; // 500 MB
            final int WARN_THRESHOLD = 10000; // Warn if blocked time exceeds 10 seconds

            if (heapMemoryUsed > MAX_HEAP_MEMORY_USAGE || threadAnalysis.contains("Blocked by: " + WARN_THRESHOLD)) {
                return Health.down()
                        .withDetail("memory.usage", heapMemoryUsed)
                        .withDetail("thread.analysis", threadAnalysis)
                        .build();
            } else {
                return Health.up().build();
            }
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }

    private long getHeapMemoryUsage() {
        MemoryMXBean memMXBean = ManagementFactory.getMemoryMXBean();
        long heapMemoryUsed = memMXBean.getHeapMemoryUsage().getUsed();
        return heapMemoryUsed;
    }

    private String analyzeThreadsForDeadlocks() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> threadInfoList = new ArrayList<>();

        for (long threadId : threadIds) {
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
            if (threadInfo != null) {
                Map<String, Object> threadInfoMap = new HashMap<>();
                threadInfoMap.put("Thread ID", threadId);
                threadInfoMap.put("Name", threadInfo.getThreadName());
                threadInfoMap.put("Blocked by", threadInfo.getBlockedTime());
                threadInfoMap.put("Waited for", threadInfo.getWaitedTime());
                threadInfoList.add(threadInfoMap);
            }
        }

        String json = "";
        try {
            json = mapper.writeValueAsString(threadInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }
}
