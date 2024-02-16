package com.njha.resourcemanager.task;

import com.njha.resourcemanager.resource.Resource;
import com.njha.resourcemanager.resource.ResourceType;
import com.njha.resourcemanager.resource.allocation.strategy.StrategyType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Task {
    private Long id;
    private ResourceType requiredResourceType;
    private int requiredCpu;
    private boolean isCompleted;
    private Long startTime;
    private Long endTime;
    private StrategyType strategyType;
    private boolean useMultipleResources;
    private List<Resource> resourcesUsed;
}