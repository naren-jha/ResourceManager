package com.njha.resourcemanager;

import com.njha.resourcemanager.resource.*;
import com.njha.resourcemanager.resource.allocation.ResourceManager;
import com.njha.resourcemanager.task.Task;
import com.njha.resourcemanager.task.TaskService;
import com.njha.resourcemanager.resource.allocation.strategy.StrategyType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@Slf4j
public class ResourcemanagerApplication {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ResourceManager resourceManager;


	public static void main(String[] args) {
		SpringApplication.run(ResourcemanagerApplication.class, args);
	}

	@PostConstruct
	public void start() throws InterruptedException {
		log.info("started ...");

		// resource
		Resource r0 = Resource.builder()
				.type(ResourceType.SERVER_INSTANCE)
				.cpu(8)
				.price(200.0)
				.isAllocated(false)
				.build();
		resourceService.addResource(r0);
		log.info("Added resource : " + r0);

		Resource r1 = Resource.builder()
				.type(ResourceType.SERVER_INSTANCE)
				.cpu(12)
				.price(300.0)
				.isAllocated(false)
				.build();
		resourceService.addResource(r1);
		log.info("Added resource : " + r1);

		Resource r2 = Resource.builder()
				.type(ResourceType.SERVER_INSTANCE)
				.cpu(16)
				.price(400.0)
				.isAllocated(false)
				.build();
		resourceService.addResource(r2);
		log.info("Added resource : " + r2);

		Resource r3 = Resource.builder()
				.type(ResourceType.SERVER_INSTANCE)
				.cpu(25)
				.price(1400.0)
				.isAllocated(false)
				.build();
		resourceService.addResource(r3);
		log.info("Added resource : " + r3);


		// available resources
		RequestQuery requestQuery = RequestQuery.builder()
				.type(ResourceType.SERVER_INSTANCE)
				.cpu(8)
				.build();
		List<Resource> matchingResources = resourceService.getAvailableResources(requestQuery);
		if (matchingResources.size() > 0) {
			log.info("Available resources for query: " + requestQuery);
			matchingResources.forEach(r -> log.info(r.toString()));
		}
		else {
			log.info("No resources found for query: " + requestQuery);
		}

		// remove resources
		log.info("Removing resource : " + r3);
		resourceService.removeResource(r3);
		// available resources after removal
		requestQuery = RequestQuery.builder()
				.type(ResourceType.SERVER_INSTANCE)
				.cpu(8)
				.build();
		matchingResources = resourceService.getAvailableResources(requestQuery);
		if (matchingResources.size() > 0) {
			log.info("Available resources for query: " + requestQuery);
			matchingResources.forEach(r -> log.info(r.toString()));
		}
		else {
			log.info("No resources found for query: " + requestQuery);
		}

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
				.resources(Arrays.asList(r0, r1, r2))
				.taskQueue(taskQueue)
				.completedTask(new ArrayList<>())
				.build();

		List<String> taskStatusResultList = taskService.getAllTaskStatus(dataCenter);
		log.info("Task status before execution - " );
		taskStatusResultList.forEach(tsr -> log.info(tsr.toString()));

		resourceManager.init(dataCenter);
		resourceManager.startResourceAllocation();

		Thread.sleep(5000);
		taskStatusResultList = taskService.getAllTaskStatus(dataCenter);
		log.info("Task status after execution - " );
		taskStatusResultList.forEach(tsr -> log.info(tsr.toString()));
	}

}
