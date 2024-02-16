package com.njha.resourcemanager.task;

import com.njha.resourcemanager.resource.DataCenter;
import com.njha.resourcemanager.resource.ResourceType;
import com.njha.resourcemanager.resource.allocation.strategy.StrategyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Test
    void shouldTestThatTaskStatusIsFetchedSuccessfully() {
        // task
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
                .resources(null)
                .taskQueue(taskQueue)
                .completedTask(new ArrayList<>())
                .build();

        List<String> taskStatusResultList = taskService.getAllTaskStatus(dataCenter);
        assertThat(taskStatusResultList.size()).isGreaterThan(0);
    }

}