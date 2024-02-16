package com.njha.resourcemanager.resource.allocation;

import com.njha.resourcemanager.resource.DataCenter;
import com.njha.resourcemanager.resource.RequestQuery;
import com.njha.resourcemanager.resource.Resource;
import com.njha.resourcemanager.resource.ResourceService;
import com.njha.resourcemanager.task.Task;
import com.njha.resourcemanager.resource.allocation.strategy.AllocationStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
public class ResourceManager {
    private DataCenter dataCenter;
    private ExecutorService executorService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private AllocationStrategyFactory allocationStrategyFactory;

    public void init(DataCenter dataCenter) {
        this.dataCenter = dataCenter;
        this.executorService = Executors.newFixedThreadPool(5); // Adjust the number of threads as needed
    }

    public void startResourceAllocation() {
        executorService.submit(this::allocateResources);
    }

    private void allocateResources() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (dataCenter.getTaskQueue()) {
                    if (!dataCenter.getTaskQueue().isEmpty()) {
                        Task task = dataCenter.getTaskQueue().peek();

                        // get the resources needed for task execution
                        List<Resource> selectedResources = allocateResource(task);

                        // execute task if resource is available
                        if (selectedResources != null && selectedResources.size() > 0) {
                            executorService.submit(() -> {
                                try {
                                    executeTask(task, selectedResources);
                                } catch (InterruptedException e) {
                                    log.error(Arrays.toString(e.getStackTrace()));
                                }
                            });
                            dataCenter.getTaskQueue().poll(); // Remove the task from the queue
                        }
                    }
                }

                Thread.sleep(1000); // Adjust the sleep time as needed
            }
            catch (InterruptedException e) {
                log.error(Arrays.toString(e.getStackTrace()));
                break;
            }
            catch (Exception e) {
                log.error(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public List<Resource> allocateResource(Task task) {
        // Logic to allocate resource based on criteria
        log.info("Finding suitable resource for task: " + task);
        RequestQuery requestQuery = RequestQuery.builder()
                .type(task.getRequiredResourceType())
                .cpu(task.isUseMultipleResources() ? null : task.getRequiredCpu())
                .build();
        List<Resource> matchingResources = resourceService.getAvailableResources(requestQuery);
        if (matchingResources.size() > 0) {
            log.info("Matching resources for task requirement: " + task);
            matchingResources.forEach(r -> log.info(r.toString()));
        }
        else {
            log.info("No matching resources found for query: " + requestQuery);
            return null;
        }

        List<Resource> selectedResources = allocationStrategyFactory.getAllocationStrategy(task.getStrategyType()).selectResource(matchingResources, task);
        return selectedResources;
    }

    public void executeTask(Task task, List<Resource> resources) throws InterruptedException {
        task.setStartTime(System.currentTimeMillis());

        List<Long> resourceIds = new ArrayList<>();
        for (Resource resource : resources) {
            resource.setAllocated(true);
            resourceIds.add(resource.getId());
        }

        System.out.println("Executing task " + task.getId() + " on resources " + resourceIds);
        Thread.sleep(5); // Adjust the sleep time as needed

        task.setCompleted(true);
        task.setEndTime(System.currentTimeMillis());

        for (Resource resource : resources)
            resource.setAllocated(false);

        task.setResourcesUsed(resources);

        dataCenter.getCompletedTask().add(task);
    }

}
