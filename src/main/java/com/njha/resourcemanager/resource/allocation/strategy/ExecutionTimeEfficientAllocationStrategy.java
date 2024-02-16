package com.njha.resourcemanager.resource.allocation.strategy;

import com.njha.resourcemanager.resource.Resource;
import com.njha.resourcemanager.task.Task;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@Data
public class ExecutionTimeEfficientAllocationStrategy implements AllocationStrategy {
    @Override
    public List<Resource> selectResource(List<Resource> inputResources, Task task) {

        Collections.sort(inputResources, (a, b) -> {
            double aExecTime = 1.0/a.getCpu();
            double bExecTime = 1.0/b.getCpu();
            return Double.compare(aExecTime, bExecTime);
        });

        // return inputResources.get(0);
        return pickOneOrMany(inputResources, task);
    }

    @Override
    public StrategyType strategyType() {
        return StrategyType.EXECUTION_TIME_EFFICIENT;
    }
}
