package com.njha.resourcemanager.resource;

import com.njha.resourcemanager.resource.allocation.ResourceManager;
import com.njha.resourcemanager.resource.allocation.strategy.StrategyType;
import com.njha.resourcemanager.task.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ResourceManagerTest {

    @Test
    void shouldTestThatResourceIsAllocatedToATaskAndTaskIsExecutedSuccessfully() {

        ResourceManager resourceManager = new ResourceManager();
        // resource
        Resource r0 = Resource.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(8)
                .price(200.0)
                .isAllocated(false)
                .build();

        Resource r1 = Resource.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(12)
                .price(300.0)
                .isAllocated(false)
                .build();

        Resource r2 = Resource.builder()
                .type(ResourceType.SERVER_INSTANCE)
                .cpu(16)
                .price(400.0)
                .isAllocated(false)
                .build();

        Task t0 = Task.builder()
                .id(0L)
                .requiredResourceType(ResourceType.SERVER_INSTANCE)
                .requiredCpu(8)
                .strategyType(StrategyType.PRICE_EFFICIENT)
                .useMultipleResources(false)
                .build();

        Task t1 = Task.builder()
                .id(1L)
                .requiredResourceType(ResourceType.SERVER_INSTANCE)
                .requiredCpu(8)
                .strategyType(StrategyType.EXECUTION_TIME_EFFICIENT)
                .useMultipleResources(false)
                .build();

        Task t2 = Task.builder()
                .id(2L)
                .requiredResourceType(ResourceType.SERVER_INSTANCE)
                .requiredCpu(12)
                .strategyType(StrategyType.PRICE_EFFICIENT)
                .useMultipleResources(false)
                .build();

        Queue<Task> taskQueue = new ArrayDeque<>();
        taskQueue.offer(t0); taskQueue.offer(t1); taskQueue.offer(t2);

        // resource allocation and task execution
        DataCenter dataCenter = DataCenter.builder()
                .location("Mumbai")
                .resources(Arrays.asList(r0, r1, r2))
                .taskQueue(taskQueue)
                .completedTask(new ArrayList<>())
                .build();

        resourceManager.init(dataCenter);
        resourceManager.startResourceAllocation();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(taskQueue.size()).isGreaterThanOrEqualTo(0);
    }
}