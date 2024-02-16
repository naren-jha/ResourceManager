package com.njha.resourcemanager.resource.allocation.strategy;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class AllocationStrategyConfig {

    private final List<AllocationStrategy> allocationStrategies;

    @Bean
    public Map<StrategyType, AllocationStrategy> AllocationStrategyMap() {
        Map<StrategyType, AllocationStrategy> allocationStrategyMap = new EnumMap<>(StrategyType.class);
        allocationStrategies.forEach(allocationStrategy -> allocationStrategyMap.put(allocationStrategy.strategyType(), allocationStrategy));
        return allocationStrategyMap;
    }
}
