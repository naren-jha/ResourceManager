package com.njha.resourcemanager.resource.allocation.strategy;

import com.njha.resourcemanager.resource.Resource;
import com.njha.resourcemanager.task.Task;

import java.util.ArrayList;
import java.util.List;

public interface AllocationStrategy {

    List<Resource> selectResource(List<Resource> inputResources, Task task);
    StrategyType strategyType();

    default List<Resource> pickOneOrMany(List<Resource> inputResources, Task task) {
        List<Resource> selectedResources = new ArrayList<>();
        if (task.isUseMultipleResources()) {
            // multiple resource can be used
            int cpuAllocated = 0;
            for (int i = 0; i < inputResources.size() && cpuAllocated < task.getRequiredCpu(); ++i) {
                cpuAllocated += inputResources.get(i).getCpu();
                selectedResources.add(inputResources.get(i));
            }

            // even with all resources not feasible
            if (cpuAllocated < task.getRequiredCpu()) {
                selectedResources = new ArrayList<>();
            }
        }
        else {
            // single resource needed
            selectedResources.add(inputResources.get(0));
        }

        return selectedResources;
    }
}
