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
public class PriceEfficientAllocationStrategy implements AllocationStrategy {
    @Override
    public List<Resource> selectResource(List<Resource> inputResources, Task task) {

        Collections.sort(inputResources, (a, b) -> Double.compare(a.getPrice(), b.getPrice()));

        // return inputResources.get(0);
        return pickOneOrMany(inputResources, task);
    }

    @Override
    public StrategyType strategyType() {
        return StrategyType.PRICE_EFFICIENT;
    }
}
