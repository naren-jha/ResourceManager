package com.njha.resourcemanager.resource.allocation.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AllocationStrategyFactory {

    @Autowired
    private Map<StrategyType, AllocationStrategy> allocationStrategies;

    public AllocationStrategy getAllocationStrategy(StrategyType type) {
        return allocationStrategies.get(type);
    }
}
