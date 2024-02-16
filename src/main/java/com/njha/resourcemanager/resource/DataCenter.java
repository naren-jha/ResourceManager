package com.njha.resourcemanager.resource;

import com.njha.resourcemanager.task.Task;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Queue;

@Data
@Builder
public class DataCenter {
    private Long id;
    private String location;
    private List<Resource> resources;
    private Queue<Task> taskQueue;
    private List<Task> completedTask;
}
